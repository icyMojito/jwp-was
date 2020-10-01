# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)



## 1단계 - 웹 서버 구현

1. http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

2. “회원가입” 메뉴에서 회원가입을 하기 위해 사용자가 입력한 값이 URL로 서버에 전달되면, 이 값을 파싱해 model.User 클래스에 저장한다.

3. http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

4. “회원가입”을 완료하면 redirect 방식처럼 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

5. Stylesheet 파일을 지원하도록 구현한다.

## 2단계 - HTTP 웹 서버 리팩토링

WAS 기능과 HTTP 요청/응답 처리 기능을 재사용이 가능한 구조가 되도록 한다.

- WAS 기능
  - 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
  - Thread Pool을 적용해 일정 수의 사용자를 동시에 처리가 가능하도록 한다.
- HTTP 요청/응답 처리 기능
  - HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
    - 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
  - 추가 요구사항이나 변경이 발생하는 경우의 처리를 연습해 본다.
    - HTTP에서 POST 방식으로 데이터를 전달할 때 body를 통한 데이터 전달뿐만 아니라 Query String을 활용한 데이터 전달도 지원해야 한다.