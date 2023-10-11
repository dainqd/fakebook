//package com.example.fakebook.controller.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@Controller
//public class WebSocketController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ChatRequestRepository chatRequestRepository;
//
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public Message handleMessage(Message message) {
//        // Kiểm tra xem message có chứa ký tự không nằm trong khoảng ASCII từ 32 đến 126
//        if (message.getContent().matches(".*[^\\x20-\\x7E\\t\\r\\n].*")) {
//            // Nhận hình ảnh dưới dạng chuỗi nhị phân và lưu vào tệp
//            String imageName = System.currentTimeMillis() + ".jpg";
//            try {
//                FileOutputStream fos = new FileOutputStream(new File("images/chat/" + imageName));
//                fos.write(message.getContent().getBytes());
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Gửi thông điệp chứa tên hình ảnh đến client
//            Message responseMessage = new Message();
//            responseMessage.setImage(imageName);
//
//            return responseMessage;
//        }
//
//        // Xử lý các loại tin nhắn khác tại đây
//        if ("request_load_unconnected_user".equals(message.getType())) {
//            // Xử lý request_load_unconnected_user
//            // ...
//        } else if ("request_search_user".equals(message.getType())) {
//            // Xử lý request_search_user
//            // ...
//        }
//        // Các xử lý tin nhắn khác
//
//        // Lưu tin nhắn vào cơ sở dữ liệu
//        messageRepository.save(message);
//
//        return message;
//    }
//}
