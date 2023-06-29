## Farmer-BE 입니다.

# BE 기술스택

- Java
- Spring
- JPA
- Querydsl
- MySql
- AWS EC2
- AWS S3
- AWS RDS
- 그 외 github action CI/CD pipeline

<!-- # 코딩 컨벤션 -->

# Git Convention

# **브랜치 머지 전략 📌**

- Git Flow 전략
- 기본적으로 master, feature, hotfix 등의 브랜치를 사용
- feature 브랜치를 생성할 때는 ex) feature/login, feature/register → feature/기능(camel case)
- feature 브랜치에서 작업을 하기 전에는, main 브랜치에서 최신 변경 내용을 pull 받아와서 feature 브랜치를 생성합니다.
- hotfix 브랜치에서는 긴급하게 수정해야 할 버그를 수정하고, main브랜치로 병합합니다.

1. feature 브랜치에서의 작업이 완료되면, main 브랜치 PR을 올립니다.
2. feature 브랜치에서는 QA 및 코드 리뷰를 진행하고, 필요한 경우 버그를 수정합니다.

# Commit Convention

### Commit Type

타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자로 한다.
**`태그 : 제목`의 형태이며, `:`뒤에만 space가 있음에 유의한다.**

### Subject

- 제목은 최대 50글자가 넘지 않도록 하고 마침표 및 특수기호는 사용하지 않는다.
- 영문으로 표기하는 경우 동사(원형)를 가장 앞에 두고 첫 글자는 대문자로 표기한다.(과거 시제를 사용하지 않는다.)
- 제목은 **개조식 구문**으로 작성한다. --> 완전한 서술형 문장이 아니라, 간결하고 요점적인 서술을 의미.

| 태그             | 설명                                                                      |
| ---------------- | ------------------------------------------------------------------------- |
| feat             | 새로운 기능을 추가할 경우                                                 |
| fix              | 버그를 고친 경우                                                          |
| design           | CSS 등 사용자 UI 디자인 변경                                              |
| style            | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우                     |
| refactor         | 프로덕션 코드 리팩토링                                                    |
| comment          | 필요한 주석 추가 및 변경                                                  |
| docs             | 문서를 수정한 경우                                                        |
| test             | 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)                        |
| chore            | 빌드 태스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X) |
| rename           | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                        |
| remove           | 파일을 삭제하는 작업만 수행한 경우                                        |
| !BREAKING CHANGE | 커다란 API 변경의 경우                                                    |
| !HOTFIX          | 급하게 치명적인 버그를 고쳐야하는 경우                                    |

# PR 규칙 🔗

⇒ tag: subject (반드시 확인 후 merge)
