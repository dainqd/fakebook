var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

connect();

function onConnected() {
    let userID = getCookieValue('userId');
    let user = {
        id: userID
    };

    $(document).ready(function () {
        $('.userConnected').on('click', function () {
            let receiverId = $(this).data('id');
            let data = {
                senderId: userID,
                receiverId: receiverId
            };

            stompClient.subscribe(`/topic/publicChatRoom`, load_chat_history);
            stompClient.send(`/app/chat.loadMessageHistory`, {}, JSON.stringify(data));
        })

        // $('#btnSendMessage').on('click', function () {
        //     let receiverId = $(this).data('id');
        //     sendMessage(receiverId);
        // })
    })
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

async function load_chat_history(res) {
    let userID = getCookieValue('userId');
    let data = JSON.parse(res.body);
    let message = data[0];

    let mainHtml = ` `;

    for (let i = 0; i < data.length; i++) {
        let item = data[i];
        if (item.content) {
            let htmlYou = ' <li class="you">' +
                '<figure>' +
                '<img src="images/resources/userlist-2.jpg" alt="">' +
                '</figure>' +
                '<p>' +
                item.content +
                '</p>' +
                '</li>';

            let htmlMe = ' <li class="me">' +
                '<figure>' +
                '<img src="images/resources/userlist-2.jpg" alt="">' +
                '</figure>' +
                '<p>' +
                item.content +
                '</p>' +
                '</li>';

            if (item.senderId == userID) {
                mainHtml = mainHtml + htmlMe;
            } else {
                mainHtml = mainHtml + htmlYou;
            }
        }

    }

    $('#chatting-area').empty().append(mainHtml);

    if (data.length > 0) {
        await loadCurrentUserChat(data[0]);
        await showFormSendMessage(data[0]);
    }
    scroll_top();
}

async function loadCurrentUserChat(data) {
    let currentUser;
    let userID = getCookieValue('userId');
    if (data.receiverId != userID) {
        currentUser = data.receiverId;
    } else {
        currentUser = data.senderId;
    }
    await getUserDetail(currentUser)
        .then(response => {
            let state = `offline`;
            if (response.state != null) {
                state = `online`;
            }
            let html = `<figure><img src="${response.avt}" alt=""></figure>
                                <span>${response.username} <i>${state}</i></span>`;
            $('#chatUserCurrent').empty().append(html);
        })
        .catch(error => {
            console.error('Error:', error);
        });

}

async function showFormSendMessage(data) {
    let currentUser;
    let userID = getCookieValue('userId');
    if (data.receiverId != userID) {
        currentUser = data.receiverId;
    } else {
        currentUser = data.senderId;
    }
    let html = `<form id="sendMessage" data-id="${currentUser}">
                                <textarea id="messageInput"></textarea>
                                <button id="btnSendMessage" onclick="sendMessage(${currentUser})" type="button" title="send" data-id="${currentUser}"><i class="fa fa-paper-plane"></i></button>
                        </form>`;
    $('#formSendMessage').empty().append(html);
}

async function sendMessage(receiverId) {
    let messageInput = $('#messageInput');
    let messageContent = messageInput.val();
    let userID = getCookieValue('userId');
    if (messageContent && stompClient) {
        let chatMessage = {
            senderId: userID,
            receiverId: receiverId,
            content: messageContent,
        };
        stompClient.send(`/app/chat.sendMessage`, {}, JSON.stringify(chatMessage));
        messageInput.val('');
    }
    scroll_top();
}

function scroll_top() {
    document.querySelector('#chatting-area').scrollTop = document.querySelector('#chatting-area').scrollHeight;
}

