1. `build.gradle`의 repositories 블록에 GitHub Packages 저장소 추가 <br/><br/>
    ```
    repositories {
        ...
        maven {
            name = "GitHubPackages"
            url = uri(System.getenv("PACKAGES_URL") ?: findProperty("PACKAGES_URL"))
            credentials {
                username = System.getenv("USERNAME") ?: findProperty("USERNAME")
                password = System.getenv("TOKEN") ?: findProperty("TOKEN")
            }
        }
    }
    ```

<br/>

2. `build.gradle`의 dependencies 블록에 Common Module 의존성 추가 <br/><br/>
    ```
    dependencies {
        ...
        implementation 'com.palja:common-module:x.x.x'
    }
    ```
   > **최신 버전 확인** https://github.com/palja-good-team/common/packages/2752652

<br/>

3. 각 MSA 서비스 패키지 최상위에 `gradle.properties` 추가 (팀 노션 → 개발 환경 → gradle.properties)

<br/>

4. `.gitignore`에 gradle.properties가 포함되어 있는지 확인

<br/>

5. @SpringBootApplication이 붙은 클래스에 @ComponentScan 추가 <br/><br/>
    ```
    @ComponentScan(basePackages = {"...", "com.palja.common"})
    ```
