var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

connect();

function onConnected() {
    let id = getCookieValue('userId');
    let user = {
        id: id
    };

    stompClient.subscribe(`/topic/publicNotification/${id}`, load_all_notification);

    stompClient.send(`/app/notify.getAllNotification/${id}`)
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function load_all_notification(res) {
    let data = JSON.parse(res.body);
    let html = '';

    let value = 0;
    for (let i = 0; i < data.length; i++) {
        let string = '';

        if (data[i].status == 'UNSEEN') {
            value = value + 1;
            string = `<span class="tag green">New</span>`
        }

        html = html + `<li><a href="#" title="">
                                            <div class="mesg-meta">
                                                <h6>${data[i].title}</h6>
                                                <span>${data[i].content}</span>
                                                <i>${data[i].createdAt}</i>
                                            </div>
                                        </a>
                                        ${string} </li>`;
    }

    let noti = ``;
    if (value > 0) {
        noti = `<span>${value} New Notifications</span>`;
    }

    console.log()

    let mainHtml = `<a href="#" title="Notification" data-ripple="">
                                <i class="ti-bell"></i><span>${data.length}</span>
                            </a>
                            <div class="dropdowns">
                                ${noti}
                                <ul class="drops-menu">
                                        ${html}
                                </ul>
                            </div>`;

    $('#tabsNotifications').empty().append(mainHtml);
}

async function sendNotification(userID, data) {
    let user = {
        id: userID
    }

    let notification = {
        user: user,
        title: "A user just commented on your post!",
        content: data,
        status: 'UNSEEN',
    };

    await createNotification(userID, notification);
}

async function createNotification(id, user) {
    try {
        let createNotifications = `/api/v1/notifications`;

        await fetch(createNotifications, {
            method: 'POST',
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ` + `${myToken}`
            },
            body: JSON.stringify(user),

        })
            .then(response => {
                if (response.status == 200) {
                    if (stompClient) {
                        stompClient.send(`/app/notify.getAllNotification/${id}`)
                    }
                } else {
                    alert("Error! Please try again");
                }
            })
            .catch(error => console.log(error));
    } catch (error) {
        console.error('Error:', error);
    }
}


function convertDate(data) {
    const targetTime = new Date(data);
    return calculateTimeDifference(targetTime);
}

function calculateTimeDifference(targetTime) {
    const currentTime = new Date();

    const timeDifferenceInMilliseconds = targetTime - currentTime;

    const timeDifferenceInDays = Math.floor(timeDifferenceInMilliseconds / (1000 * 60 * 60 * 24));
    const timeDifferenceInHours = Math.floor(timeDifferenceInMilliseconds / (1000 * 60 * 60));
    const timeDifferenceInMinutes = Math.floor(timeDifferenceInMilliseconds / (1000 * 60));

    if (timeDifferenceInDays > 0) {
        return `${timeDifferenceInDays} ngày`;
    } else if (timeDifferenceInHours > 0) {
        return `${timeDifferenceInHours} giờ`;
    } else {
        return `${timeDifferenceInMinutes} phút`;
    }
}