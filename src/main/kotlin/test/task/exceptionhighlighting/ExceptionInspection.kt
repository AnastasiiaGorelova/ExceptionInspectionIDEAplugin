package test.task.exceptionhighlighting

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.*


class ExceptionInspection : AbstractKotlinInspection() {

    override fun buildVisitor(
            holder: ProblemsHolder,
            isOnTheFly: Boolean
    ): KtVisitorVoid {
        return namedFunctionVisitor { ktNamedFunction ->
            val throwExpression = ktNamedFunction.analyzeExceptions()
            throwExpression?.let {
                holder.registerProblem(
                        it,
                        "Exception is thrown here!"
                )
            }
        }
    }

    private fun KtNamedFunction.analyzeExceptions(): KtThrowExpression? {
        val bodyExpression = this.bodyExpression
        return findThrowExpression(bodyExpression)
    }

    private fun findThrowExpression(element: PsiElement?): KtThrowExpression? {
        if (element is KtThrowExpression)
            return element
        element?.children?.forEach { e ->
            val foundThrowExpression = findThrowExpression(e)
            foundThrowExpression?.let { return it }
        }
        return null
    }

}
