package dev.codeswamp.user.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class NicknameChangeRequestDto(
    @field:NotBlank
    @field:Size(min = 2, max = 12)
    @field:Pattern(regexp = "^[a-zA-Z0-9가-힣]+$")
    val nickname: String
)
