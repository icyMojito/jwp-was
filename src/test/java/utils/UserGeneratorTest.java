package utils;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserGeneratorTest {

    @DisplayName("HTTP request header의 첫 번째 줄에서 요청 URL의 파라미터로 User 클래스를 생성하는지 확인")
    @ParameterizedTest
    @CsvSource(value = {
            "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC&email=javajigi%40slipp.net," +
                    "javajigi, password, %EB%B0%95%EC, javajigi%40slipp.net",
            "/user/create?userId=coollime&password=starbucks&name=쿨라임&email=coollime@woowa.tech," +
                    "coollime, starbucks, 쿨라임, coollime@woowa.tech"
    })
    void createUserTest(String requestHeaderFirstLine, String expectedId, String expectedPassword,
                        String expectedName, String expectedEmail) {
        User user = UserGenerator.createUser(requestHeaderFirstLine);

        assertThat(user.getUserId()).isEqualTo(expectedId);
        assertThat(user.getPassword()).isEqualTo(expectedPassword);
        assertThat(user.getName()).isEqualTo(expectedName);
        assertThat(user.getEmail()).isEqualTo(expectedEmail);
    }
}