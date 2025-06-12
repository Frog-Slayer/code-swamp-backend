package dev.codeswamp.global.auth.infrastructure.security.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


class FilterSkipMatcher(skipList: List<String>) : RequestMatcher {
    private val negatedMatcher: NegatedRequestMatcher = NegatedRequestMatcher(
        OrRequestMatcher (
            skipList.map { AntPathRequestMatcher(it) }
        )
    )

    override fun matches(request: HttpServletRequest?): Boolean {
        return negatedMatcher.matches(request)
    }
}