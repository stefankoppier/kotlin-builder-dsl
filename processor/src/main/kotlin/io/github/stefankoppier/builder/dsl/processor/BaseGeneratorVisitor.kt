import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import io.github.stefankoppier.builder.dsl.processor.GeneratorContext

abstract class BaseGeneratorVisitor<T> : KSEmptyVisitor<GeneratorContext, T?>() {
    override fun defaultHandler(node: KSNode, data: GeneratorContext): T? {
        return null
    }
}