package com.tobby.dailyapp.blog

import com.tobby.dailyapp.blog.dto.UnZipBlogResponse
import com.tobby.dailyapp.blog.dto.ZipFileListResponse
import com.tobby.dailyapp.blog.mapper.BlogMapper
import org.springframework.stereotype.Service

@Service
class GetServiceImpl(
    private val mapper: BlogMapper
) : BlogService {
    override fun insertBlog(title: String, content: String, category: String, tags: List<String>?) {
        mapper.insertBlog(title, category, content)
    }

    override fun insertBlogFileName(names: List<String>): Int {
        return mapper.insertBlogFile(names)
    }

    override fun getBlogFileName(size : Int?): List<ZipFileListResponse> {
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
        val list = getBlogFileName(1)
        val fileName = list[0].name
        return UnZipBlogResponse("git merge 사용법 – rebase와 차이점, --no-ff/--squash 옵션까지","<h2>git merge 사용법과 옵션 정리</h2>" +
                "<p>이 글은 초급~중급 개발자가 실무에서 바로 쓰는 기준으로 git merge의 개념, 자주 겪는 문제, 그리고 비교·선택 기준을 함께 정리합니다.</p>" +
                "" +
                "<img src=\"https://wac-cdn.atlassian.com/dam/jcr%3A4639eeb8-e417-434a-a3f8-a972277fc66a/02%20Merging%20main%20into%20the%20feature%20branh.svg?cdnVersion=3218\" alt=\"git merge 브랜치 구조 예시\">" +
                "<p>브랜치 구조 예시는 여러 Git 관련 블로그에서 공통적으로 사용되는 형태이다</p>" +
                "" +
                "" +
                "<h3>git merge란 무엇인가?</h3>" +
                "<p>git merge는 한 브랜치의 변경을 다른 브랜치로 통합하는 명령어입니다. 통합 결과가 fast-forward로 가능하면 브랜치 포인터만 이동하고, 그렇지 않으면 머지 커밋이 생성됩니다.</p>" +
                "" +
                "<h3>git merge의 기본 동작 원리</h3>" +
                "<p>머지는 두 히스토리를 “합친 결과”를 만들며, 충돌이 없다면 자동으로 3-way merge를 수행합니다. 충돌이 있으면 사용자가 해결한 뒤 머지 커밋을 완성합니다.</p>" +
                "" +
                "" +
                "" +
                "<h3>옵션 상세 설명</h3>" +
                "<ul>" +
                "  <li><p>--no-ff: fast-forward가 가능해도 머지 커밋을 강제로 생성합니다. “기능 브랜치를 통합했다”는 이벤트를 히스토리에 남기고 싶을 때 사용합니다.</p></li>" +
                "  <li><p>--ff-only: fast-forward로만 머지합니다. 머지 커밋이 필요한 상황이면 실패합니다(보호된 메인 브랜치 정책에서 자주 사용).</p></li>" +
                "  <li><p>--squash: 대상 브랜치의 여러 커밋을 하나로 뭉쳐서 작업 트리에 적용합니다. 이후 별도의 commit이 필요하며, 원본 커밋 히스토리는 메인에 직접 남지 않습니다.</p></li>" +
                "</ul>" +
                "<pre><code>git checkout main" +
                "git merge feature/login" +
                "" +
                "git merge --no-ff feature/login" +
                "git merge --squash feature/login</code></pre>" +
                "" +
                "" +
                "" +
                "<h3>git merge 충돌이 발생하는 이유</h3>" +
                "<p>같은 파일의 같은 줄(또는 인접 영역)을 서로 다른 커밋에서 수정하면 충돌이 납니다. 특히 장기간 브랜치가 갈라져 있거나, 리네임/대규모 리팩토링이 있으면 빈도가 올라갑니다.</p>" +
                "" +
                "<h3>충돌 해결 실무 팁</h3>" +
                "<ul>" +
                "  <li><p>머지 전에 git fetch 후, main 최신을 feature에 자주 반영하면 충돌 규모가 줄어듭니다.</p></li>" +
                "  <li><p>충돌 해결 후에는 빌드/테스트를 반드시 실행합니다(컴파일은 되는데 런타임이 깨지는 경우가 있습니다).</p></li>" +
                "</ul>" +
                "<pre><code># 충돌 해결 후" +
                "git add -A" +
                "git commit</code></pre>" +
                "" +
                "" +
                "" +
                "<h3>git merge vs git rebase 차이점</h3>" +
                "<ul>" +
                "  <li><p>merge: 기존 커밋을 그대로 두고 “통합 커밋”을 추가합니다(히스토리 보존).</p></li>" +
                "  <li><p>rebase: 커밋을 새 기반 위로 재작성해 히스토리를 선형으로 만듭니다(히스토리 변경).</p></li>" +
                "</ul>" +
                "" +
                "<h3>fast-forward merge와 --no-ff merge 비교</h3>" +
                "<ul>" +
                "  <li><p>fast-forward: 커밋이 직선으로 이어져 머지 커밋이 생기지 않음.</p></li>" +
                "  <li><p>--no-ff: 기능 단위 통합을 명확히 남김(릴리즈/핫픽스 추적에 유리).</p></li>" +
                "</ul>" +
                "" +
                "<h3>어떤 상황에서 어떤 방식을 선택해야 할까?</h3>" +
                "<p>팀에서 “머지 커밋을 남기는 정책”이면 --no-ff가 자주 쓰입니다. 반대로 메인 브랜치를 항상 선형으로 유지하려면 --ff-only 또는 스쿼시 머지(리뷰 도구의 squash merge 포함)를 선택합니다.</p>" +
                "" +
                "" +
                "" +
                "<hr>" +
                "<p>이 명령어는 git fetch와 함께 사용되는 경우가 많다. 관련해서 git rebase, git cherry-pick 명령어도 같이 알아두면 좋다.</p>","ㅂㅈㄷㅂㅈㄷ, asdasda, 123123")
    }
}