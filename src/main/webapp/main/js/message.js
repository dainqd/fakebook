var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', function (message) {
        // Xử lý tin nhắn nhận được
        var receivedMessage = JSON.parse(message.body);
        console.log('Received Message: ', receivedMessage);
        // Hiển thị tin nhắn lên giao diện người dùng
    });
});


var messageDto = {
    content: "Nội dung tin nhắn",
    senderId: 1,
    receiverId: 2
};

stompClient.send("/app/chat", {}, JSON.stringify(messageDto));

function displayMessage(message) {
    var messageContainer = document.getElementById('messageContainer');

    var messageElement = document.createElement('div');
    messageElement.classList.add('message');
    messageElement.innerHTML = `
        <p><strong>Sender ID:</strong> ${message.senderId}</p>
        <p><strong>Content:</strong> ${message.content}</p>
    `;

    messageContainer.appendChild(messageElement);
}

document.getElementById('sendMessageForm').addEventListener('submit', function (e) {
    e.preventDefault();
    var messageContent = document.getElementById('messageContent').value;

    // Gửi tin nhắn tới server
    var messageDto = {
        content: messageContent,
        senderId: 1, // ID của người gửi
        receiverId: 2 // ID của người nhận
    };

    stompClient.send("/app/chat", {}, JSON.stringify(messageDto));

    // Hiển thị tin nhắn ngay lập tức (không cần đợi response từ server)
    displayMessage(messageDto);

    // Reset input
    document.getElementById('messageContent').value = '';
});