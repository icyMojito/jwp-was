package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUrlExtractorTest {

    @DisplayName("HTTP request header의 첫 번째 줄에서 요청 URL을 정확하게 추출하는지 확인")
    @ParameterizedTest
    @CsvSource(value = {"GET /index.html HTTP/1.1, /index.html",
            "POST /user/create HTTP/1.1, /user/create",
            "GET ./css/style.css HTTP/1.1, ./css/style.css"})
    void extractUrlTest(String requestHeaderFirstLine, String expectedUrl) {
        String resultUrl = RequestUrlExtractor.extractUrl(requestHeaderFirstLine);

        assertThat(resultUrl).isEqualTo(expectedUrl);
    }
}