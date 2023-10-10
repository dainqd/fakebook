package com.example.fakebook.utils;

import javax.json.Json;

public class JsonUtil {
    public static String formatMessage(String message, long userSend, long userReceive) {
        return Json.createObjectBuilder()
                .add("message", message)
                .add("senderId", userSend)
                .add("receiverId", userReceive)
                .build().toString();
    }
}
