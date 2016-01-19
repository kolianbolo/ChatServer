package ru.bolobanov.chatserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bolobanov Nikolay on 21.12.15.
 */
public class ErrorMessages {
    
    public static final String ERROR_CODE = "error_code";
    public static final String ERROR_MESSAGE = "error_message";

    public static final int OK = 0;
    public static final int INCORRECT_REQUEST = 1;
    public static final int INCORRECT_PASSWORD = 2;
    public static final int BAD_SESSION = 3;
    public static final int RECIPIENT_NOT_EXISTS = 4;
    public static final int UNEXPECTED_ERROR = 5;

    public static final Map<Integer, String> messages = new HashMap<Integer, String>();

    static {
        messages.put(INCORRECT_REQUEST, "Incorrect request");
        messages.put(INCORRECT_PASSWORD, "Incorrect login or password");
        messages.put(BAD_SESSION, "Bad session");
        messages.put(RECIPIENT_NOT_EXISTS, "the recipient does not exist");
        messages.put(UNEXPECTED_ERROR, "unexpected error");
    }
}
