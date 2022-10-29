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
        return classVisitor { ktClass ->
            if (ktClass.isData()) {
                // check if serialized name annotation is required.
                val param = ktClass.isSerializedNameAnnotationMissing()
                param?.let {
                    holder.registerProblem(
                            it as PsiElement,
                            "Missing SerializedName annotation"
                    )
                }
            }
        }
    }
}

fun KtClass.isSerializedNameAnnotationMissing(): KtParameter? {
    this.getPrimaryConstructorParameterList()?.parameters?.forEach { param ->
        if (!param.annotationEntries.any {
                    it.shortName?.asString() == "SerializedName"
                }
        ) {
            return param
        }
    }
    return null
}



//class ExceptionInspection: AbstractKotlinInspection() {
//    override fun getDisplayName(): String {
//        return "Use CamelCase naming"
//    }
//
////    override fun getGroupDisplayName(): String { //TODO
////        return GroupNames.STYLE_GROUP_NAME
////    }
//
//    override fun getShortName(): String {
//        return "Camelcase"
//    }
//
//    override fun buildVisitor(holder: ProblemsHolder,
//                              isOnTheFly: Boolean): KtVisitorVoid {
//        return namedDeclarationVisitor { declaredName ->
//            if (declaredName.name?.isDefinedCamelCase() == false) {
//                println(
//                        "Non CamelCase Name Detected for ${declaredName.name}")
//                holder.registerProblem(
//                        declaredName.nameIdentifier as PsiElement,
//                        "Please use CamelCase for #ref #loc")
//            }
//        }
//    }
//
//    private fun String.isDefinedCamelCase(): Boolean {
//        val toCharArray = toCharArray()
//        return toCharArray
//                .mapIndexed { index, current ->
//                    current to toCharArray.getOrNull(index + 1) }
//                .none { it.first.isUpperCase() &&
//                        it.second?.isUpperCase() ?: false }
//    }
//
//    override fun isEnabledByDefault(): Boolean {
//        return true
//    }
//
//}

/*
class CamelcaseInspection : AbstractKotlinInspection() {

    override fun getDisplayName(): String {
        return "Use CamelCase naming"
    }

    override fun getGroupDisplayName(): String {
        return GroupNames.STYLE_GROUP_NAME
    }

    override fun getShortName(): String {
        return "Camelcase"
    }

    override fun buildVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean): KtVisitorVoid {
        return namedDeclarationVisitor { declaredName ->
            if (declaredName.name?.isDefinedCamelCase() == false) {
                System.out.println(
                        "Non CamelCase Name Detected for
                        ${declaredName.name}")
                holder.registerProblem(
                        declaredName.nameIdentifier as PsiElement,
                        "Please use CamelCase for #ref #loc")
            }
        }
    }

    private fun String.isDefinedCamelCase(): Boolean {
        val toCharArray = toCharArray()
        return toCharArray
                .mapIndexed { index, current ->
                    current to toCharArray.getOrNull(index + 1) }
                .none { it.first.isUpperCase() &&
                        it.second?.isUpperCase() ?: false }
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }
}
*/