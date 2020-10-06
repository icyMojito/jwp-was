package webserver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.request.HttpUri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerTest {
    private static final String NEW_LINE = System.lineSeparator();

    @DisplayName("HTTP Request가 회원 가입 요청일 때 담당 Controller에서 알맞은 응답 메세지를 생성")
    @Test
    void createHttpResponseMessageForUserCreationTest() throws IOException {
        HttpRequestMessage httpRequestMessage = createHttpRequestMessage("./src/test/resources/CreateUserRequest");
        HttpResponseMessage httpResponseMessage = createHttpResponseMessage(httpRequestMessage);
        String actualMessage = httpResponseMessage.toHttpMessage();

        String expectedMessage = "HTTP/1.1 302 Found" + NEW_LINE +
                "Location: /index.html" + NEW_LINE +
                NEW_LINE;

        assertEquals(actualMessage, expectedMessage);
    }

    @DisplayName("HTTP Request가 존재하는 resource 요청일 때 알맞은 응답 메세지를 생성")
    @Test
    void createHttpResponseMessageForResourceTest() throws IOException {
        HttpRequestMessage httpRequestMessage = createHttpRequestMessage("./src/test/resources/ResourceRequest");
        HttpResponseMessage httpResponseMessage = createHttpResponseMessage(httpRequestMessage);
        String actualMessage = httpResponseMessage.toHttpMessage();

        String expectedMessage = "HTTP/1.1 200 OK" + NEW_LINE +
                "Content-Length: 7065" + NEW_LINE +
                "Content-Type: text/css;charset=utf-8" + NEW_LINE +
                NEW_LINE;

        assertThat(actualMessage).contains(expectedMessage);
    }

    @DisplayName("HTTP Request가 존재하지 않는 resource 요청일 때 알맞은 응답 메세지를 생성")
    @Test
    void createHttpResponseMessageForNotFoundResourceTest() throws IOException {
        HttpRequestMessage httpRequestMessage = createHttpRequestMessage("./src/test/resources" +
                                                                                 "/NotFoundResourceRequest");
        HttpResponseMessage httpResponseMessage = createHttpResponseMessage(httpRequestMessage);
        String actualMessage = httpResponseMessage.toHttpMessage();

        String expectedMessage = "HTTP/1.1 404 Not Found" + NEW_LINE +
                "Content-Length: 1365" + NEW_LINE +
                "Content-Type: text/html;charset=utf-8" + NEW_LINE +
                NEW_LINE;

        assertThat(actualMessage).contains(expectedMessage);
    }

    private HttpRequestMessage createHttpRequestMessage(String pathName) throws IOException {
        FileReader fileReader = new FileReader(new File(pathName));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        return HttpRequestMessage.from(bufferedReader);
    }

    private HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpUri httpUri = httpRequestMessage.getRequestLine().getHttpUri();
        Controller controller = httpUri.findController();

        return controller.createHttpResponseMessage(httpRequestMessage);
    }
}
