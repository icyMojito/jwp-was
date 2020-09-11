package model;

import java.util.Arrays;

public enum HttpContentType {
    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "text/javascript"),
    TTF(".ttf", "text/font"),
    WOFF(".woff", "text/font");

    private final String fileExtension;
    private final String contentType;

    HttpContentType(String fileExtension, String contentType) {
        this.fileExtension = fileExtension;
        this.contentType = contentType;
    }

    public static String findContentType(String url) {
        return Arrays.stream(HttpContentType.values())
                .filter(type -> type.isSameExtension(url))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("처리할 수 없는 확장자 파일입니다."))
                .contentType;
    }

    private boolean isSameExtension(String url) {
        return url.endsWith(this.fileExtension);
    }
}
