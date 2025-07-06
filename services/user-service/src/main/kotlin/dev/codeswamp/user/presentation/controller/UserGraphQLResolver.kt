package dev.codeswamp.user.presentation.controller

import dev.codeswamp.user.application.usecase.profile.GetUserPublicProfileUseCase
import dev.codeswamp.user.presentation.dto.response.UserPublicProfileResponse
import graphql.schema.DataFetchingEnvironment
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class UserGraphQLResolver(
    private val userPublicProfileUseCase: GetUserPublicProfileUseCase
) {
    private val logger  =  LoggerFactory.getLogger(UserGraphQLResolver::class.java)

    @QueryMapping
    suspend fun userProfile(
        @Argument username: String,
        env: DataFetchingEnvironment
    ) :UserPublicProfileResponse {
        val fields = env.selectionSet.fields.map { it.name }.toSet()
        logger.warn("username: {}, fields:{}", username, fields)
        val userProfile  = userPublicProfileUseCase.getUserPublicProfile(username, fields)

        logger.warn(userProfile.toString())

        return UserPublicProfileResponse.from(userProfile)
    }
}