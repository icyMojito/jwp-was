package webserver;

import java.util.Arrays;

public enum HttpContentType {
    HTML(".html", "text/html", false),
    CSS(".css", "text/css", true),
    JS(".js", "text/javascript", true),
    TTF(".ttf", "text/font", true),
    WOFF(".woff", "text/font", true);

    private final String fileExtension;
    private final String contentType;
    private final boolean isStaticFile;

    HttpContentType(String fileExtension, String contentType, boolean isStaticFile) {
        this.fileExtension = fileExtension;
        this.contentType = contentType;
        this.isStaticFile = isStaticFile;
    }

    public static String findContentType(String url) {
        return Arrays.stream(HttpContentType.values())
                .filter(type -> type.isSameExtension(url))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("처리할 수 없는 확장자 파일입니다 -> " + url))
                .contentType;
    }

    public static boolean isStaticFile(String url) {
        return Arrays.stream(HttpContentType.values())
                .filter(type -> type.isSameExtension(url))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("처리할 수 없는 확장자 파일입니다 -> " + url))
                .isStaticFile;
    }

    private boolean isSameExtension(String url) {
        return url.endsWith(this.fileExtension);
    }
}
