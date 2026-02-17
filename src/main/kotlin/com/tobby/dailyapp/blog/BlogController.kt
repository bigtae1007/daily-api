package com.tobby.dailyapp.blog


import com.tobby.dailyapp.blog.dto.DoneRequest
import com.tobby.dailyapp.blog.dto.UnZipBlogResponse
import com.tobby.dailyapp.blog.dto.UploadRequest
import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.common.ApiResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/blogs")
class BlogController(
    private val blogService: BlogService,
) {
    @PostMapping("/files")
    fun upload(
        @Valid @RequestBody request: UploadRequest,
    ): ApiResponse<Map<String, Int>> {
        val inserted = blogService.insertBlogFileName(request.names)
        return ApiResponse.ok(mapOf("inserted" to inserted))
    }

    @GetMapping("/files")
    fun getBlogZip(
        @RequestParam(defaultValue = "50")
        @Min(1)
        @Max(200)
        size: Int,
    ): ApiResponse<List<ZipFileListResponse>> {
        return ApiResponse.ok(blogService.getBlogFileName(size))
    }

    @PatchMapping("/files/{id}/done")
    fun done(
        @PathVariable id: Int,
    ): ApiResponse<Map<String, Int>> {
        val updated = blogService.updateDoneFile(id = id, uploaded = true)
        return ApiResponse.ok(mapOf("updated" to updated))
    }

    @GetMapping("/unzipped/next")
    fun getOneUnzip(): ApiResponse<UnZipBlogResponse> {
        return ApiResponse.ok(blogService.getOneUnZip())
    }
}
