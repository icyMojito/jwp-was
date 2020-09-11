package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.HttpContentType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpContentTypeTest {

    @DisplayName("처리할 수 있는 URL에 맞는 Content-Type 값을 반환")
    @ParameterizedTest
    @CsvSource(value = {"/index.html, text/html", "./css/style.css, text/css", "./js/scripts.js, text/javascript"})
    void successToFindContentTypeTest(String url, String expected) {
        String resultContentType = HttpContentType.findContentType(url);

        assertThat(resultContentType).isEqualTo(expected);
    }

    @DisplayName("처리할 수 없는 URL일 경우 예외 발생 확인")
    @ParameterizedTest
    @ValueSource(strings = {"./fonts/font-regular.svg", "./fonts/font.eot", "./images/80-text.png"})
    void failToFindContentTypeTest(String url) {
        assertThatThrownBy(() -> HttpContentType.findContentType(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("처리할 수 없는 확장자 파일입니다 -> " + url);
    }

    @DisplayName("Static File URL에는 true를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"./css/style.css", "./js/scripts.js", "./fonts/regular.ttf", "./fonts/regular.woff"})
    void isStaticFileTrueTest(String url) {
        assertThat(HttpContentType.isStaticFile(url)).isTrue();
    }

    @DisplayName("Static File이 아닌 URL에는 false를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"/index.html", "/user/form.html"})
    void isStaticFileFalseTest(String url) {
        assertThat(HttpContentType.isStaticFile(url)).isFalse();
    }

    @DisplayName("처리할 수 없는 URL일 경우 Static File 여부와 상관없이 예외 발생 확인")
    @ParameterizedTest
    @ValueSource(strings = {"./fonts/font-regular.svg", "./fonts/font.eot", "./images/80-text.png"})
    void isStaticFileExceptionTest(String url) {
        assertThatThrownBy(() -> HttpContentType.isStaticFile(url))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("처리할 수 없는 확장자 파일입니다 -> " + url);
    }
}