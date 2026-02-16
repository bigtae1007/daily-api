package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.UnZipBlogResponse
import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.blog.mapper.BlogMapper
import com.tobby.dailyapp.common.exception.BadRequestException
import com.tobby.dailyapp.common.exception.ExternalApiException
import com.tobby.dailyapp.common.exception.NotFoundException
import com.tobby.dailyapp.slack.MessageType
import com.tobby.dailyapp.slack.SlackMessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException

@Service
class GetServiceImpl(
    private val mapper: BlogMapper,
    private val restClient: RestClient,
    @Value("\${blog.jsonBaseUrl:}") private val jsonBaseUrl: String,
    private val slackMessageService: SlackMessageService
) : BlogService {

    data class BlogJson(
        val title: String = "",
        val content: String = "",
        val tags: List<String> = emptyList(),
    )

    @Transactional
    override fun insertBlogFileName(names: List<String>): Int {
        return mapper.insertBlogFile(names)
    }

    @Transactional(readOnly = true)
    override fun getBlogFileName(size: Int): List<ZipFileListResponse> {
        return mapper.getZipFile(size).sortedBy { it.id }
    }

    @Transactional
    override fun updateDoneFile(id: Int, uploaded: Boolean): Int {
        return mapper.updateDoneFile(id = id, uploaded = uploaded)
    }

    @Transactional(readOnly = true)
    override fun getOneUnZip(): UnZipBlogResponse {
        slackMessageService.sendMessage("#server-api-alarm","조회를 시작합니다", type = MessageType.INFO)
        if (jsonBaseUrl.isBlank()) {
            throw BadRequestException("blog.jsonBaseUrl 설정이 필요합니다.")
        }

        val firstFile = getBlogFileName(1).firstOrNull()
            ?: throw NotFoundException("처리할 블로그 파일이 없습니다.")
        val url = "$jsonBaseUrl/${firstFile.name}"

        slackMessageService.sendMessage("#server-api-alarm","${firstFile.name} 블로그 업로드 시작합니다.", type = MessageType.INFO)
        val blog = try {
            restClient.get()
                .uri(url)
                .retrieve()
                .body(BlogJson::class.java)
        } catch (e: RestClientException) {
            throw ExternalApiException("블로그 원문을 불러오지 못했습니다.", e.message)
        } ?: throw ExternalApiException("블로그 원문 응답이 비어 있습니다.", "url=$url")

        return UnZipBlogResponse(
            id = firstFile.id,
            title = blog.title,
            content = blog.content,
            tags = blog.tags,
        )
    }
}
