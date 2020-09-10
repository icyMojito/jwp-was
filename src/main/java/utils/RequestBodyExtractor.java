package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBodyExtractor {
    private static final String BODY_SEPARATE_LINE = "";
    private static final String CONTENT_LENGTH_FIELD_NAME = "Content-Length";
    private static final String HEADER_FIELD_DELIMITER = ":";
    private static final int HEADER_FIELD_VALUE_INDEX = 1;

    public static String extractBody(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        int contentLength = 0;
        while (!requestLine.equals(BODY_SEPARATE_LINE)) {
            if (requestLine.startsWith(CONTENT_LENGTH_FIELD_NAME)) {
                String[] tokens = requestLine.split(HEADER_FIELD_DELIMITER);
                contentLength = Integer.parseInt(tokens[HEADER_FIELD_VALUE_INDEX].trim());
            }
            requestLine = br.readLine();
        }

        return IOUtils.readData(br, contentLength);
    }
}
