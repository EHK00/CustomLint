package com.ekh.mylint.lint

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import org.w3c.dom.Attr
import org.w3c.dom.Element

class ImageSrcDetector : Detector(), XmlScanner {
    companion object {
        val ISSUE = Issue.create(
            id = "SrcAttrImageViewLayout",
            briefDescription = "Prohibits android:src attribute",
            explanation = "android:src attributes should be replaced app:srcCompat",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                ImageSrcDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean =
        folderType == ResourceFolderType.LAYOUT


    override fun getApplicableElements(): Collection<String>? {
        return setOf("ImageView")
    }

    override fun visitElement(context: XmlContext, element: Element) {
        val attrs = element.attributes
        for(i in 0 until attrs.length) {
            val attr = attrs.item(i) as? Attr ?: continue
            val name = attr.name

            if(!name.equals("android:src")){
                continue
            }

            val srcCompatFix = LintFix.create()
                .name("Use app:srcCompat")
                .replace()
                .text("android:src")
                .with("app:srcCompat")
                .robot(false)
                .independent(true)
                .build()

            context.report(
                issue = ISSUE,
                scope = attr,
                location = context.getLocation(attr),
                message = "벡터이미지 하위호환성을 위해 android:src 대신 app:srcCompat을 이용할 것",
                quickfixData = srcCompatFix
            )
            return
        }
    }
}