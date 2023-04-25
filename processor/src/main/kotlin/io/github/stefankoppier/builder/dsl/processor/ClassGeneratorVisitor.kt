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
import io.github.stefankoppier.builder.dsl.generators.EnumGenerator
import io.github.stefankoppier.builder.dsl.generators.primitives.*

class ClassGeneratorVisitor(val resolver: Resolver, val generator: CodeGenerator, val logger: KSPLogger) : KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val packageName = classDeclaration.packageName.asString()
        val originalClassName = classDeclaration.simpleName.asString()
        val className = "${originalClassName}Generator"
        val context = GeneratorContext(originalClassName, className)

        val file = FileSpec.builder(packageName, className)
            .addFunction(FunSpec.builder(originalClassName).addParameter("init", LambdaTypeName.get(returnType = ClassName.bestGuess(className), receiver = ClassName.bestGuess(className)))
                .addStatement("return $className().apply { init() }.invoke()")
                .build())
            .addType(resolve(when(classDeclaration.classKind) {
                ClassKind.CLASS -> classDeclaration.accept(ClassClassGeneratorVisitor(), context)
                ClassKind.ENUM_CLASS -> classDeclaration.accept(EnumClassGeneratorVisitor(), context)
                else -> TODO("Handling of class declarations not of type 'class' or 'enum class' is not supported yet.")
        }))

        file.build().writeTo(generator, Dependencies(false))
    }

    class ClassClassGeneratorVisitor : BaseGeneratorVisitor<TypeSpec>() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: GeneratorContext): TypeSpec {
            require(classDeclaration.classKind == ClassKind.CLASS)

            val parameters = resolve(classDeclaration.primaryConstructor).parameters

            return TypeSpec.classBuilder(data.className)
                .addProperties(parameters.map { valueParameter ->
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
                        .returns(ClassName.bestGuess(data.originalClassName))
                        .addStatement("return ${data.originalClassName}(" +
                                parameters.joinToString { valueParameter ->
                                    val name = resolve(valueParameter.name).asString()
                                    "$name = $name.invoke()"
                                } + ")"
                        )
                        .build()
                )
                .addFunctions(parameters.mapNotNull { it.accept(AssignerFunctionGeneratorVisitor(), data) })
                .build()
        }
    }

    class EnumClassGeneratorVisitor : BaseGeneratorVisitor<TypeSpec>() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: GeneratorContext): TypeSpec {
            require(classDeclaration.classKind == ClassKind.ENUM_CLASS)

            return TypeSpec.classBuilder(data.className)
                .superclass(
                    EnumGenerator::class.asClassName()
                    .parameterizedBy(ClassName.bestGuess(data.originalClassName), ClassName.bestGuess(data.className)))
                .addFunction(FunSpec.builder("allValues")
                    .addModifiers(KModifier.OVERRIDE)
                    .addStatement("return ${data.originalClassName}.values()")
                    .build())
                .build()
        }
    }

    class AssignerFunctionGeneratorVisitor : BaseGeneratorVisitor<FunSpec>() {
        override fun visitValueParameter(valueParameter: KSValueParameter, data: GeneratorContext): FunSpec {
            val name = resolve(valueParameter.name).asString()
            val generatorType =  resolve(valueParameter.accept(GeneratorTypeVisitor(), data))

            val argumentType = LambdaTypeName
                .get(returnType = generatorType, receiver = generatorType)

            return FunSpec.builder(name)
                .addParameter(ParameterSpec.builder("transform", argumentType)
                    .defaultValue("{ ${generatorType.simpleName}() }")
                    .build())
                .returns(ClassName.bestGuess(data.className))
                .addStatement("$name = transform(${generatorType.simpleName}())")
                .addStatement("return this")
                .build()
        }
    }

    class GeneratorTypeVisitor : BaseGeneratorVisitor<ClassName>() {
        override fun visitValueParameter(valueParameter: KSValueParameter, data: GeneratorContext): ClassName {
            return when(val type = resolve(valueParameter.type.resolve().declaration.qualifiedName).asString()) {
                String::class.qualifiedName -> StringGenerator::class.asClassName()
                Char::class.qualifiedName -> CharGenerator::class.asClassName()
                Boolean::class.qualifiedName -> BooleanGenerator::class.asClassName()
                Byte::class.qualifiedName -> ByteGenerator::class.asClassName()
                Short::class.qualifiedName -> ShortGenerator::class.asClassName()
                Int::class.qualifiedName -> IntGenerator::class.asClassName()
                Long::class.qualifiedName -> LongGenerator::class.asClassName()
                Float::class.qualifiedName -> FloatGenerator::class.asClassName()
                Double::class.qualifiedName -> DoubleGenerator::class.asClassName()
                else -> ClassName.bestGuess("${type}Generator")
            }
        }
    }
}