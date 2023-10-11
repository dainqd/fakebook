package com.example.fakebook.config.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Được gọi khi một kết nối WebSocket mới được thiết lập
        // Thông thường, bạn sẽ thực hiện các tác vụ khởi tạo ở đây

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Được gọi khi một tin nhắn văn bản được nhận từ máy khách
        String payload = message.getPayload(); // payload chứa nội dung của tin nhắn
        // Xử lý tin nhắn ở đây
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Được gọi khi kết nối bị đóng
        // Thông thường, bạn sẽ thực hiện các tác vụ dọn dẹp ở đây
    }
}


