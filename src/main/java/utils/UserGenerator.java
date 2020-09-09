package utils;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UserGenerator {
    private static final String PARAMETER_DELIMITER = "\\?";
    private static final String USER_DATA_DELIMITER = "&";
    private static final String USER_DATA_VALUE_DELIMITER = "=";
    private static final int USER_DATA_INDEX = 1;
    private static final int USER_DATA_LABEL_INDEX = 0;
    private static final int USER_DATA_VALUE_INDEX = 1;
    private static final String USER_ID_LABEL = "userId";
    private static final String PASSWORD_LABEL = "password";
    private static final String NAME_LABEL = "name";
    private static final String EMAIL_LABEL = "email";

    public static User createUser(String url) {
        Map<String, String> userDatas = new HashMap<>();

        String[] tokens = url.split(PARAMETER_DELIMITER);
        StringTokenizer st = new StringTokenizer(tokens[USER_DATA_INDEX], USER_DATA_DELIMITER);
        while (st.hasMoreTokens()) {
            String data = st.nextToken();
            String[] dataTokens = data.split(USER_DATA_VALUE_DELIMITER);
            String userDataLabel = dataTokens[USER_DATA_LABEL_INDEX];
            String userDataValue = dataTokens[USER_DATA_VALUE_INDEX];
            userDatas.put(userDataLabel, userDataValue);
        }

        return new User(userDatas.get(USER_ID_LABEL), userDatas.get(PASSWORD_LABEL),
                        userDatas.get(NAME_LABEL), userDatas.get(EMAIL_LABEL));
    }
}
