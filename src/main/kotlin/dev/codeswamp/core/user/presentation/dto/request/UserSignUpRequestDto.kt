package dev.codeswamp.core.user.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserSignUpRequestDto (
    @field:NotBlank
    @field:Size(min = 2, max = 12)
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$")
    val username: String,

    @field:NotBlank
    @field:Size(min = 2, max = 12)
    @field:Pattern(regexp = "^[a-zA-Z0-9가-힣]+$")
    val nickname: String,

    val profileImageUrl: String? = null
)
