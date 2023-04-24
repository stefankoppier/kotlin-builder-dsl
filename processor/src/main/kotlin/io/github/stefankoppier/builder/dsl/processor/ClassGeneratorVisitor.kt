package io.github.stefankoppier.builder.dsl.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class ClassGeneratorVisitor(val resolver: Resolver, val generator: CodeGenerator, val logger: KSPLogger) : KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val packageName = classDeclaration.packageName.asString()
        val originalClassName = classDeclaration.simpleName.asString()
        val className = "${originalClassName}Generator"
        val parameters = resolve(classDeclaration.primaryConstructor).parameters
        val context = GeneratorContext(originalClassName, className)

        val file = FileSpec.builder(packageName, className)
            .addImport("io.github.stefankoppier.builder.dsl.primitives",
                "StringGenerator", "IntGenerator", "BooleanGenerator", "ByteGenerator", "DoubleGenerator",
                "FloatGenerator", "LongGenerator")
            .addFunction(FunSpec.builder(originalClassName)
                .addParameter("init", LambdaTypeName.get(returnType = ClassName.bestGuess(className), receiver = ClassName.bestGuess(className)))
                .addStatement("return $className().apply { init() }.invoke()")
                .build())
            .addType(
                TypeSpec.classBuilder(className)
                    .addProperties(parameters.map { valueParameter ->
                        val name = resolve(valueParameter.name).asString()
                        val generatorType = resolve(valueParameter.accept(GeneratorTypeVisitor(), context))
                        PropertySpec.builder(name, generatorType)
                            .initializer(CodeBlock.of("${generatorType.simpleName}()"))
                            .addModifiers(KModifier.PRIVATE)
                            .mutable(true)
                            .build()
                    })
                    .addFunction(FunSpec.builder("invoke")
                        .addModifiers(KModifier.OPERATOR)
                        .returns(ClassName.bestGuess(originalClassName))
                        .addStatement("return $originalClassName(" +
                            parameters.map { valueParameter ->
                                val name =  resolve(valueParameter.name).asString()   
                                "$name = $name.invoke()"
                            }.joinToString() + ")")
                        .build())
                    .addFunctions(parameters.mapNotNull { it.accept(AssignerFunctionGeneratorVisitor(), context) })
                    .build()
            )

        file.build().writeTo(generator, Dependencies(false))
    }

    inner class AssignerFunctionGeneratorVisitor : KSEmptyVisitor<GeneratorContext, FunSpec?>() {
        override fun visitValueParameter(valueParameter: KSValueParameter, data: GeneratorContext): FunSpec {
            val name = resolve(valueParameter.name).asString()
            val generatorType =  resolve(valueParameter.accept(GeneratorTypeVisitor(), data))

            val argumentType = LambdaTypeName
                .get(returnType = generatorType, receiver = generatorType)

            return FunSpec.builder(name)
                .addParameter(ParameterSpec.builder("transform", argumentType)
                    .defaultValue("{ $generatorType() }")
                    .build())
                .returns(ClassName.bestGuess(data.className))
                .addStatement("$name = transform($generatorType())")
                .addStatement("return this")
                .build()
        }

        override fun defaultHandler(node: KSNode, data: GeneratorContext): FunSpec? {
            return null
        }
    }

    inner class GeneratorTypeVisitor : KSEmptyVisitor<GeneratorContext, ClassName?>() {
        override fun visitValueParameter(valueParameter: KSValueParameter, data: GeneratorContext): ClassName {
            val type = resolve(valueParameter.type.resolve())
            return ClassName.bestGuess("${type.declaration.simpleName.asString()}Generator")
//            val type = resolve(valueParameter.type.resolve())
//
//            val mapping = mapOf(
//                Pair(resolver.builtIns.stringType, ClassName.bestGuess("StringGenerator")),
//                Pair(resolver.builtIns.intType, ClassName.bestGuess("IntGenerator"))
//            )
//
//            return mapping
//                .filter { type.isAssignableFrom(it.key) }
//                .map { it.value }
//                .find { true }
//                ?: type.toClassName()
////                TODO("Unknown type ${type.declaration.qualifiedName!!.asString()}")
        }

        override fun defaultHandler(node: KSNode, data: GeneratorContext): ClassName? {
            return null
        }
    }
}