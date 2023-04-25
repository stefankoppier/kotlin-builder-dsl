package io.github.stefankoppier.builder.dsl.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import io.github.stefankoppier.builder.dsl.annotations.DataGenerator

class Processor(val generator: CodeGenerator, val logger: KSPLogger) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(DataGenerator::class.qualifiedName!!)
        symbols
            .filter { it is KSClassDeclaration && it.validate() }
            .forEach { it.accept(ClassGeneratorVisitor(resolver, generator, logger), Unit) }
        return symbols.filter { !it.validate() }.toList()
    }
}
