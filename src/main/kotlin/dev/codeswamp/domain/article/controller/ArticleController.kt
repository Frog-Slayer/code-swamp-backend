package dev.codeswamp.domain.article.controller

import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController {
    // 글 가져오기        GET /@{사용자명}/{slug}

    // 글 발행           POST /articles

    // 문서 삭제         DELETE /articles/{id}

    // 메타 정보 수정     PATCH /articles/{id}

    // 본문 수정         PUT /articles/{id}

    // 코멘트 달기        POST /articles/{id}/comments

    // 코멘트 수정       PATCH /comments/{id}

    // 코멘트 삭제       DELETE /comments/{id}

    // 글 이모지 달기     POST /articles/{id}/emoji

    // 글 이모지 삭제     DELETE /articles/{articleId}/emoji/{{emojiId}

    // 코멘트 이모지 달기   POST /comments/{id}/emoji

    // 코멘트 이모지 삭제   DELETE /comments/{id}/emoji/{id}
}