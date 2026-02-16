package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.UnZipBlogResponse
import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.blog.mapper.BlogMapper
import com.tobby.dailyapp.common.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import tools.jackson.databind.JsonNode
import tools.jackson.databind.ObjectMapper

@Service
class GetServiceImpl(
    private val mapper: BlogMapper,
    private val restClient: RestClient,
    private val objectMapper: ObjectMapper,
    @Value("\${blog.jsonBaseUrl}") private val jsonBaseUrl: String
) : BlogService {
    val log = logger<GetServiceImpl>()

    data class BlogJson(
        val title: String = "",
        val content: String = "",
        val tags: List<String> = emptyList()
    )
    override fun insertBlog(title: String, content: String, category: String, tags: List<String>?) {
        mapper.insertBlog(title, category, content)
    }

    override fun insertBlogFileName(names: List<String>): Int {
        return mapper.insertBlogFile(names)
    }

    override fun getBlogFileName(size: Int?): List<ZipFileListResponse> {
        val limit = size ?: 50
        val response = mapper.getZipFile(limit)
        val sortedRes = response
            .sortedBy { it.id }

        return sortedRes
    }

    override fun updateDoneFile(id: Int): Int {
        return mapper.updateDoneFile(id)
    }

    override fun getOneUnZip(): UnZipBlogResponse {
        val fileName = getBlogFileName(1).first().name
        val url = "$jsonBaseUrl/$fileName"

        val blog = restClient.get()
            .uri(url)
            .retrieve()
            .body(BlogJson::class.java)
            ?: throw IllegalStateException("Empty response from $url")

        return UnZipBlogResponse(
            blog.title,
            blog.content,
            blog.tags
        )
    }
}
