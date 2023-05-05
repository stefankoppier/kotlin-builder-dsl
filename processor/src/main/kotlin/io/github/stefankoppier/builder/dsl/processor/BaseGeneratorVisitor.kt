package io.github.stefankoppier.builder.dsl.processor

import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor

abstract class BaseGeneratorVisitor<T> : KSEmptyVisitor<GeneratorContext, T>() {
    override fun defaultHandler(node: KSNode, data: GeneratorContext): T {
        throw IllegalStateException("Wrong node type handled")
    }
}
