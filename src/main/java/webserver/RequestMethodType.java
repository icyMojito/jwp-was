package webserver;

import java.util.Arrays;

public enum RequestMethodType {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String name;

    RequestMethodType(String name) {
        this.name = name;
    }

    public static RequestMethodType findRequestMethodType(String token) {
        return Arrays.stream(RequestMethodType.values())
                .filter(type -> type.name.equals(token))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("처리할 수 없는 Request Method입니다 -> " + token));
    }

    public static boolean isNotRequestMethodType(String token) {
        for (RequestMethodType type : RequestMethodType.values()) {
            if (type.name.equals(token)) {
                return false;
            }
        }
        return true;
    }
}
