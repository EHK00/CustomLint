package com.ekh.mylint.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

@Suppress("unused")
class IssueRegistry : IssueRegistry() {

    override val api: Int = CURRENT_API

    override val issues: List<Issue> = listOf(
        ImageSrcDetector.ISSUE,
        SetContentViewDetector.ISSUE,
    )
}
