package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestMethodTypeTest {

    @DisplayName("처리할 수 있는 Request Method 문자열에 맞는 RequestMethodType을 반환")
    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE"})
    void findRequestMethodTypeTest(String token) {
        assertThat(RequestMethodType.findRequestMethodType(token)).isInstanceOf(RequestMethodType.class);
    }

    @DisplayName("처리할 수 없는 Request Method 문자열일 경우 예외 발생 확인")
    @ParameterizedTest
    @ValueSource(strings = {"Get", "poSt", "put", " delete", " "})
    void failToFindRequestMethodTypeTest(String token) {
        assertThatThrownBy(() -> RequestMethodType.findRequestMethodType(token))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("처리할 수 없는 Request Method입니다 -> " + token);
    }

    @DisplayName("처리할 수 있는 Request Method 문자열에는 false를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE"})
    void isNotRequestMethodTypeFalseTest(String token) {
        assertThat(RequestMethodType.isNotRequestMethodType(token)).isFalse();
    }

    @DisplayName("처리할 수 없는 Request Method 문자열에는 true를 반환")
    @ParameterizedTest
    @ValueSource(strings = {"Get", "poSt", "put", " delete", " "})
    void isNotRequestMethodTypeTrueTest(String token) {
        assertThat(RequestMethodType.isNotRequestMethodType(token)).isTrue();
    }
}