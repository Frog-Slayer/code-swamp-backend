package dev.codeswamp.articlecommand.domain.article.service

import dev.codeswamp.articlecommand.domain.article.model.VersionState
import dev.codeswamp.articlecommand.domain.article.model.Article
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import org.springframework.stereotype.Service

@Service
class ArticleVersionTransitionSideEffectHandler(
    private val versionRepository: VersionRepository,
) {
    suspend fun handlePublishSideEffect(beforeUpdate: Article, afterUpdate: Article) {
        val beforeVersion = beforeUpdate.currentVersion
        val afterVersion = afterUpdate.currentVersion

        if (beforeVersion.state == VersionState.PUBLISHED) {//이전 버전이 발행본인 경우
            if (beforeVersion.id != afterVersion.id) versionRepository.save(beforeVersion.archive()) // 새 버전이 생성된 경우 -> 대체
            else return // 버전 생성 없이 메타데이터만 바뀐 경우
        }
        else versionRepository.findPublishedVersionByArticleId(afterUpdate.id)?.let {  //이외의 경우 DB에서 발행본을 찾고
            versionRepository.save(it.archive()) // ARCHIVED 상태로 변경 -> 발행본 버전을 유일하게 유지
        }
   }

    suspend fun handleDraftSideEffect(beforeUpdate: Article, afterUpdate: Article) {
        val beforeVersion = beforeUpdate.currentVersion
        val afterVersion = afterUpdate.currentVersion

        if (beforeVersion.id != afterVersion.id) {//새 버전이 생성된 경우: DRAFT의 경우 새 버전이 생성되는 경우만 허용되지만 도메인에서 방어적으로 처리
            if ( beforeVersion.state == VersionState.DRAFT) versionRepository.save(beforeVersion.archive())
        }
    }
}