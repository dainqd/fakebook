// Khởi tạo kết nối WebSocket
var socket = new WebSocket('ws://localhost:8080/ws'); // Thay đổi URL và cổng tùy theo cài đặt của bạn

// Xử lý khi kết nối mở
socket.addEventListener('open', (event) => {
    console.log('Đã kết nối tới server WebSocket');
});

// Xử lý khi nhận tin nhắn từ server
socket.addEventListener('message', (event) => {
    const message = JSON.parse(event.data);
    console.log('Nhận tin nhắn từ server:', message);

    // Xử lý tin nhắn ở đây
    if (message.image) {
        // Nếu tin nhắn chứa hình ảnh
    } else {
        // Nếu tin nhắn không phải hình ảnh
    }
});

// Hàm để gửi tin nhắn đến server
function sendMessage(type, content) {
    const message = {
        type: type,
        content: content
    };
    socket.send(JSON.stringify(message));
}

// Gửi tin nhắn hình ảnh (đây chỉ là một ví dụ, bạn cần có cách xác định tin nhắn là hình ảnh)
function sendImageMessage(binaryImage) {
    sendMessage('image', binaryImage);
}

// Gửi tin nhắn văn bản
function sendTextMessage(text) {
    sendMessage('text', text);
}
