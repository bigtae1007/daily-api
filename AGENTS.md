# Repository Guidelines

## Project Structure & Module Organization
This is a Kotlin + Spring Boot service built with Gradle.
- Source code: `src/main/kotlin/com/tobby/dailyapp`
- Domain modules: `todo`, `blog`, `batch`, `slack`, `gemini`, `startup`, plus shared code under `common` and `config`
- MyBatis XML mappers: `src/main/resources/mybatis`
- App config: `src/main/resources/application.yaml`
- Tests: `src/test/kotlin/com/tobby/dailyapp`

Keep new features inside the closest domain package, and place DTOs/mappers near their domain (for example, `todo/dto`, `todo/mapper`).

## Build, Test, and Development Commands
Use the Gradle wrapper so tool versions stay consistent.
- `./gradlew bootRun`: run the API locally
- `./gradlew test`: run JUnit 5 tests
- `./gradlew build`: compile, test, and package
- `./gradlew clean`: remove build outputs

Run `./gradlew build` before opening a PR.

## Coding Style & Naming Conventions
- Follow Kotlin conventions: 4-space indentation, no tabs, clear null-safety usage
- Class names: `PascalCase` (`TodoController`)
- Functions/variables: `camelCase` (`createSubTodo`)
- Package names: lowercase (`com.tobby.dailyapp.todo`)
- Request/response models should end with `Request`/`Response`
- Keep controller-service-repository (or mapper) boundaries explicit

## Testing Guidelines
- Frameworks: Spring Boot Test + JUnit 5 (`useJUnitPlatform()`)
- Test files should end with `*Tests.kt` and mirror source packages
- Add focused tests for service logic, controller behavior, and mapper-integrated paths when changing persistence
- Minimum expectation: all new/changed code paths covered by at least one test

Run tests with: `./gradlew test`.

## Commit & Pull Request Guidelines
Recent history uses short, action-first commit messages (often starting with `update`, `add`, `delete`, `fix`). Keep that style and scope each commit to one logical change.

For PRs include:
- What changed and why
- Related issue/ticket (if any)
- API examples or screenshots for endpoint/response changes
- Notes for config or migration impacts

## Security & Configuration Tips
Do not commit real secrets (DB passwords, Slack tokens, Gemini keys). Use environment variables or local-only override files, and keep `application.yaml` free of production credentials.
