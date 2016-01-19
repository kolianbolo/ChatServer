package ru.bolobanov.chatserver.entities.request;

/**
 * Created by Bolobanov Nikolay on 19.01.16.
 */


//        "message":"чмоги чмоги",
//        "session":"373a4f27-01e7-499b-a30d-f3e3cd0cb33b",
//        "recipient":"Василий"

public class SendEntity {
    private String message;
    private String session;
    private String recipient;

    public String getRecipient() {
        return recipient;
    }

    public String getSession() {
        return session;
    }

    public String getMessage() {
        return message;
    }

}
