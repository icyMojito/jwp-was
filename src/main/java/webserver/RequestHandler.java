package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.RequestBodyExtractor;
import utils.RequestUrlExtractor;
import utils.UserGenerator;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_PATH = "./templates";
    private static final String CSS_PATH = "./static";
    private static final String POST_METHOD = "POST";
    private static final String EMPTY_BODY = "";
    private static final String STYLESHEET_FILE_EXTENSION = ".css";
    private static final String STYLESHEET_TYPE = "text/css";
    private static final String HTML_TYPE = "text/html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                     connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String requestHeaderFirstLine = br.readLine();

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = EMPTY_BODY.getBytes();

            if (requestHeaderFirstLine.startsWith(POST_METHOD)) {
                String requestBody = RequestBodyExtractor.extractBody(br);
                User user = UserGenerator.createUser(requestBody);
                logger.debug("New User Created! User Id : {}", user.getUserId());

                String redirectUrl = "/index.html";
                response302Header(dos, redirectUrl);
            } else {
                String url = RequestUrlExtractor.extractUrl(requestHeaderFirstLine);
                String filepath = findFilePath(url);
                String contentType = findContentType(url);

                body = FileIoUtils.loadFileFromClasspath(filepath);
                response200Header(dos, contentType, body.length);
            }

            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String findFilePath(String url) {
        if (url.endsWith(STYLESHEET_FILE_EXTENSION)) {
            return CSS_PATH + url;
        }
        return TEMPLATES_PATH + url;
    }

    private String findContentType(String url) {
        if (url.endsWith(STYLESHEET_FILE_EXTENSION)) {
            return STYLESHEET_TYPE;
        }
        return HTML_TYPE;
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
