package dev.codeswamp.domain.comment.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles/{articleId}/comments")
class CommentController {
    // 코멘트 달기        POST

    // 코멘트 수정       PATCH /{id}

    // 코멘트 삭제       DELETE /{id}
}