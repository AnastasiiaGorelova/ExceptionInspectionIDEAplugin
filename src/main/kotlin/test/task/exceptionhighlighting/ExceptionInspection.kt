package test.task.exceptionhighlighting

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.idea.util.isAnonymousFunction
import org.jetbrains.kotlin.psi.*


class ExceptionInspection : AbstractKotlinInspection() {

    override fun buildVisitor(
            holder: ProblemsHolder,
            isOnTheFly: Boolean
    ): KtVisitorVoid {
//        return buildVisitor(holder, isOnTheFly).visitThrowExpression()
        return namedFunctionVisitor { ktNamedFunction ->
            val param = ktNamedFunction.isExceptionThrown()
            param?.let {
                holder.registerProblem(
                        it as PsiElement,
                        "Exception is thrown here!"
                )
            }
        }
    }

//    override fun buildVisitor(
//            holder: ProblemsHolder,
//            isOnTheFly: Boolean
//    ): KtVisitorVoid {
//        return classVisitor { ktClass ->
//            if (ktClass.isData()) {
//                // check if serialized name annotation is required.
//                val param = ktClass.isSerializedNameAnnotationMissing()
//                param?.let {
//                    holder.registerProblem(
//                            it as PsiElement,
//                            "Exception is thrown here!"
//                    )
//                }
//            }
//        }
//    }

}

fun KtNamedFunction.isExceptionThrown(): KtThrowExpression? {
    val bodyExpression = this.bodyExpression
    if (bodyExpression is KtThrowExpression)
        return bodyExpression


//    bodyExpression?.children?.forEach { expression ->  //TODO check for anonymous function
//        if (expression is KtThrowExpression)
//            return expression
//    }
//
//    return null


    return helper(bodyExpression)
}

private fun helper(element: PsiElement?): KtThrowExpression? {
    element?.children?.forEach { expression ->  //TODO check for anonymous function
        if (expression is KtThrowExpression)
            return expression
        else {
            val res = helper(expression)
            if(res != null) {
                return res
            }
        }
    }
    return null
}

//fun KtClass.isSerializedNameAnnotationMissing(): KtParameter? {
//    this.getPrimaryConstructorParameterList()?.parameters?.forEach { param ->
//        if (!param.annotationEntries.any {
//                    it.shortName?.asString() == "SerializedName"
//                }
//        ) {
//            return param
//        }
//    }
//    return null
//}

