package webserver;

import exception.InvalidHttpVersionException;
import utils.StringUtils;

public class HttpVersion {
    private static final String HTTP_VERSION_PREFIX = "HTTP/";

    private final String version;

    public HttpVersion(String version) {
        StringUtils.validateNonNullAndNotEmpty(version);
        validateIsStartsWithVersionPrefix(version);

        this.version = version;
    }

    private void validateIsStartsWithVersionPrefix(String version) {
        if (!version.startsWith(HTTP_VERSION_PREFIX)) {
            throw new InvalidHttpVersionException(version);
        }
    }
}
