package utils;

public class RequestUrlExtractor {
    private static final String HEADER_DELIMITER = " ";
    private static final int REQUEST_URL_INDEX = 1;

    public static String extractUrl(String requestHeaderFirstLine) {
        String[] tokens = requestHeaderFirstLine.split(HEADER_DELIMITER);

        return tokens[REQUEST_URL_INDEX];
    }
}
