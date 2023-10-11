const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);
const YOUR_USER_ID = localStorage.getItem('user_id');

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/messages', function (message) {
        const receivedMessage = JSON.parse(message.body);
        console.log('Received Message: ', receivedMessage);
        // displayMessage(receivedMessage);
    });

    stompClient.subscribe(`/topic/friends/${YOUR_USER_ID}`, function (response) {
        const friends = JSON.parse(response.body);
        displayFriend(friends);
    });

    stompClient.subscribe(`/topic/follower/${YOUR_USER_ID}`, function (response) {
        const followers = JSON.parse(response.body);
        displayFollower(followers);
    });

    let RECEIVER_ID = 3;

    // stompClient.subscribe(`/topic/messages/${YOUR_USER_ID}/${RECEIVER_ID}`, function (response) {
    //     const message = JSON.parse(response.body);
    //     displayMessage(message);
    // });


    stompClient.send(`/app/getFriends/${YOUR_USER_ID}`);
    stompClient.send(`/app/getFollower/${YOUR_USER_ID}`);
});

//Get list friend
function displayFriend(friends) {
    let string = '';
    for (let i = 0; i < friends.length; i++) {
        if (friends[i].sender.id != YOUR_USER_ID) {
            string = string + `<li onclick="myFriendClick(${friends[i].sender.id});"><figure><img src="${friends[i].sender.avt}" alt="">
                                                        <span class="status f-online"></span>
                                                    </figure>
                                                    <div class="people-name">
                                                        <span>${friends[i].sender.username}</span>
                                                    </div></li>`;
        }
    }

    $('#listFriend').empty().append(string);
}

//Get list follow
function displayFollower(followers) {
    let string = '';
    for (let i = 0; i < followers.length; i++) {
        string = string + `<li><figure><img src="${followers[i].sender.avt}" alt=""></figure>
                                                <div class="friend-meta">
                                                    <h4><a href="time-line.html" title="">${followers[i].sender.username}</a></h4>
                                                    <a href="#" title="" class="underline">Add Friend</a>
                                                </div>
                                            </li>`;
    }

    $('#listFollower').empty().append(string);
}

//Send message
function sendMessage() {
    document.getElementById('sendMessageForm').addEventListener('submit', function (e) {
        e.preventDefault();
        const messageContent = document.getElementById('messageContent').value;
        const receiverId = $('#sendMessageForm').data('id');

        const messageDto = {
            content: messageContent,
            senderId: localStorage.getItem('user_id'),
            receiverId: receiverId
        };

        myFriendClick(receiverId)

        stompClient.send("/app/chat", {}, JSON.stringify(messageDto));

        // Reset input
        document.getElementById('messageContent').value = '';
    });
}

// Get list message
function displayMessage(message) {
    let chatUser = '';
    for (let i = 0; i < message.length; i++) {
        if (message[i].senderId == YOUR_USER_ID) {
            chatUser = chatUser + `<li class="me"><figure><img class="chat-message-img" src="http://localhost:8888/main/images/resources/ad-widget.jpg" alt="">
                                                    </figure>
                                                    <p>${message[i].content}</p>
                                                </li>`;
        } else {
            chatUser = chatUser + `<li class="you"><figure><img class="chat-message-img" src="http://localhost:8888/main/images/resources/ad-widget.jpg" alt="">
                                                    </figure>
                                                    <p>${message[i].content}</p>
                                                </li>`;
        }
    }
    console.log('chat ' + chatUser)
    $('#chatContainer').empty().append(chatUser);

}

function callMessage(RECEIVER_ID) {
    stompClient.subscribe(`/topic/messages/${YOUR_USER_ID}/${RECEIVER_ID}`, function (response) {
        const message = JSON.parse(response.body);
        displayMessage(message);
    });

    stompClient.send(`/app/chat/${YOUR_USER_ID}/${RECEIVER_ID}`);
}

// Click friend detail
function myFriendClick(userID) {
    showFormChat(userID);
    getUserDetail(userID)
        .then(response => {
            showUserChat(response);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    callMessage(userID);
}

// Form chat message
function showFormChat(userID) {
    $('#mainFormChat').removeClass('d-none');
    let string = `<form id="sendMessageForm" data-id="${userID}">
                                                        <textarea id="messageContent"></textarea>
                                                        <button onclick="sendMessage();" title="send" type="submit"><i
                                                                class="fa fa-paper-plane"></i></button>
                                                    </form>`;
    $('#mainFormChat').empty().append(string);
}

// Show user current chat
function showUserChat(user) {
    let titleUser = ` <figure><img id="imageChatUser" src="${user.avt}" alt="" style="width: 45px; height: 45px">
                                                    </figure>
                                                    <span id="nameChatUser">${user.username} <i class="statusChatUser">online</i></span>`;

    $('#chatUserCurrent').empty().append(titleUser);
}

async function addFriend(id) {
    let addFriend = `/api/v1/friendships/addFriends`;

    let sender = {
        id: YOUR_USER_ID,
    }

    let receiver = {
        id: id,
    }

    const data = {
        receiver: receiver,
        sender: sender,
    };

    await fetch(addFriend, {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${myToken}`
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (response.status == 200) {
                return response.json();
            }
        })
        .then(response => {
            alert('success')
        })
        .catch(error => console.log(error));

}
