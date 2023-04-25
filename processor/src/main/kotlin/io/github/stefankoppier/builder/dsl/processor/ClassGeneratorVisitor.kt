package io.github.stefankoppier.builder.dsl.processor

import BaseGeneratorVisitor
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.stefankoppier.builder.dsl.annotations.DataGenerator
import io.github.stefankoppier.builder.dsl.generators.EnumGenerator
import io.github.stefankoppier.builder.dsl.generators.primitives.*

class ClassGeneratorVisitor(val resolver: Resolver, val generator: CodeGenerator, val logger: KSPLogger) :
    KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val annotation = classDeclaration.getDataGeneratorAnnotation()
        val postfix = annotation.getDataGeneratorPostfix()
        val packageName = classDeclaration.packageName.asString()
        val originalClassName = ClassName.bestGuess(classDeclaration.simpleName.asString())
        val className = ClassName.bestGuess("${originalClassName}${postfix}")
        val context = GeneratorContext(originalClassName, className)

        val file =
            FileSpec.builder(packageName, className.simpleName)
                .addFunction(
                    FunSpec.builder(originalClassName.camelCase())
                        .addParameter("init", LambdaTypeName.get(returnType = className, receiver = className))
                        .addStatement("return $className().apply { init() }.invoke()")
                        .build())
                .addType(
                    resolve(
                        when (classDeclaration.classKind) {
                            ClassKind.CLASS -> classDeclaration.accept(ClassClassGeneratorVisitor(), context)
                            ClassKind.ENUM_CLASS -> classDeclaration.accept(EnumClassGeneratorVisitor(), context)
                            else -> TODO("Generation of only 'class' or 'enum class' is supported yet.")
                        }))

        file.build().writeTo(generator, Dependencies(true))
    }

    inner class ClassClassGeneratorVisitor : BaseGeneratorVisitor<TypeSpec>() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: GeneratorContext): TypeSpec {
            require(classDeclaration.classKind == ClassKind.CLASS)

            val parameters = resolve(classDeclaration.primaryConstructor).parameters

            return TypeSpec.classBuilder(data.className)
                .addProperty(
                    PropertySpec.builder("constant", data.originalClassName.copy(nullable = true))
                        .addModifiers(KModifier.PRIVATE)
                        .mutable(true)
                        .initializer("null")
                        .build())
                .addProperties(
                    parameters.map { valueParameter ->
                        val name = resolve(valueParameter.name).asString()
                        val generatorType = resolve(valueParameter.accept(GeneratorTypeVisitor(), data))
                        PropertySpec.builder(name, generatorType)
                            .initializer(CodeBlock.of("${generatorType.simpleName}()"))
                            .addModifiers(KModifier.PRIVATE)
                            .mutable(true)
                            .build()
                    })
                .addFunction(
                    FunSpec.builder("invoke")
                        .addModifiers(KModifier.OPERATOR)
                        .returns(data.originalClassName)
                        .addStatement(
                            """
                            |return ${data.originalClassName}(
                            |${parameters.joinToString { "${resolve(it.name).asString()} = ${resolve(it.name).asString()}.invoke()" } }
                            |)"""
                                .trimMargin())
                        .build())
                .addFunction(
                    FunSpec.builder("constant")
                        .returns(data.className)
                        .addParameter("value", data.originalClassName)
                        .addStatement(
                            """
                            |this.constant = value
                            |return this
                            """
                                .trimMargin())
                        .build())
                .addFunctions(parameters.map { it.accept(AssignerFunctionGeneratorVisitor(), data) })
                .build()
        }
    }

    class EnumClassGeneratorVisitor : BaseGeneratorVisitor<TypeSpec>() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: GeneratorContext): TypeSpec {
            require(classDeclaration.classKind == ClassKind.ENUM_CLASS)

            return TypeSpec.classBuilder(data.className)
                .superclass(EnumGenerator::class.asClassName().parameterizedBy(data.originalClassName, data.className))
                .addFunction(
                    FunSpec.builder("allValues")
                        .addModifiers(KModifier.OVERRIDE)
                        .addStatement("return ${data.originalClassName}.values()")
                        .build())
                .build()
        }
    }

    inner class AssignerFunctionGeneratorVisitor : BaseGeneratorVisitor<FunSpec>() {
        override fun visitValueParameter(valueParameter: KSValueParameter, data: GeneratorContext): FunSpec {
            val name = resolve(valueParameter.name).asString()
            val generatorType = resolve(valueParameter.accept(GeneratorTypeVisitor(), data))

            return FunSpec.builder(name)
                .addParameter(
                    ParameterSpec.builder(
                            "transform", LambdaTypeName.get(returnType = generatorType, receiver = generatorType))
                        .defaultValue("{ ${generatorType.simpleName}() }")
                        .build())
                .returns(data.className)
                .addStatement("$name = transform(${generatorType.simpleName}())")
                .addStatement("return this")
                .build()
        }
    }

    inner class GeneratorTypeVisitor : BaseGeneratorVisitor<ClassName>() {
        override fun visitValueParameter(valueParameter: KSValueParameter, data: GeneratorContext): ClassName {
            return when (val type = resolve(valueParameter.type.resolve().declaration.qualifiedName).asString()) {
                String::class.qualifiedName -> StringGenerator::class.asClassName()
                Char::class.qualifiedName -> CharGenerator::class.asClassName()
                Boolean::class.qualifiedName -> BooleanGenerator::class.asClassName()
                Byte::class.qualifiedName -> ByteGenerator::class.asClassName()
                Short::class.qualifiedName -> ShortGenerator::class.asClassName()
                Int::class.qualifiedName -> IntGenerator::class.asClassName()
                Long::class.qualifiedName -> LongGenerator::class.asClassName()
                Float::class.qualifiedName -> FloatGenerator::class.asClassName()
                Double::class.qualifiedName -> DoubleGenerator::class.asClassName()
                else -> generatorForComplexType(type)
            }
        }

        private fun generatorForComplexType(typeName: String): ClassName {
            val postfix =
                resolve(resolver.getClassDeclarationByName(resolver.getKSNameFromString(typeName)))
                    .getDataGeneratorAnnotation()
                    .getDataGeneratorPostfix()
            return ClassName.bestGuess("${typeName}${postfix}")
        }
    }

    private fun KSClassDeclaration.getDataGeneratorAnnotation(): KSAnnotation {
        return requireNotNull(annotations.firstOrNull { it.shortName.asString() == DATA_GENERATOR_NAME }) {
            val typeName = simpleName.asString()
            val message = "Failed to resolve '$typeName'. Make sure this class is annotated with '$DATA_GENERATOR_NAME"
            throw NoSuchElementException(message)
        }
    }

    private fun KSAnnotation.getDataGeneratorPostfix(): String {
        return arguments.first { it.name == resolver.getKSNameFromString("postfix") }.value as String
    }

    companion object {
        val DATA_GENERATOR_NAME: String = DataGenerator::class.simpleName!!
    }
}
