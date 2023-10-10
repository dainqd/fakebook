package com.example.fakebook.entities.message;

import com.example.fakebook.entities.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        message.setContent(jsonObject.getString("message"));
        message.setSenderId((long) jsonObject.getInt("sender"));
        message.setReceiverId((long) jsonObject.getInt("sender"));
        return message;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }

}
