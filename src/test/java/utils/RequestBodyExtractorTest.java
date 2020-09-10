package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyExtractorTest {

    @DisplayName("HTTP request에서 body를 정확하게 추출하는지 확인")
    @Test
    void extractBodyTest() throws IOException {
        String expectedBody = "userId=javajigi&password=password&name=%EB%B0%95%EC&email=javajigi%40slipp.net";

        String httpRequest = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 78\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                expectedBody;

        InputStream is = new ByteArrayInputStream(httpRequest.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String resultBody = RequestBodyExtractor.extractBody(br);

        assertThat(resultBody).isEqualTo(expectedBody);
    }
}