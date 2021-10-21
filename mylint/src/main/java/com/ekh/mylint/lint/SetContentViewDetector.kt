package com.ekh.mylint.lint

import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope.Companion.JAVA_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import com.intellij.psi.PsiMethod
import java.util.Arrays

class SetContentViewDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames(): List<String>? {
        return Arrays.asList("setContentView")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (context.evaluator.isMemberInClass(method, "androidx.databinding.DataBindingUtil")) {
            return
        }
        context.report(
            ISSUE,
            node,
            context.getLocation(node),
            "Use DataBindingUtil.setContentView() instead"
        )
    }

    companion object {
        val ISSUE: Issue = Issue.create(
            SetContentViewDetector::class.java.simpleName,
            "Prohibits usages of setContentView()",
            "Prohibits usages of setContentView(), use DataBindingUtil.setContentView() instead",
            CORRECTNESS,
            5, Severity.ERROR,
            Implementation(SetContentViewDetector::class.java, JAVA_FILE_SCOPE)
        )
    }
}