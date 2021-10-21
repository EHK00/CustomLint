package com.ekh.mylint.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ImageSrcDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(ImageSrcDetector.ISSUE)

    override fun getDetector(): Detector = ImageSrcDetector()


    @Test
    fun expectFail() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
<ImageView 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/testDrawable"
    />
"""
                )
            )
            .allowMissingSdk()
            .run()
            .expectClean()
    }
}