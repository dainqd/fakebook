let userID = getCookieValue('userId');
let tokenUser = getCookieValue('accessToken')

$(document).ready(function () {
    loadUserFriend();
    loadUserFollower();
    loadAllUser();
})

async function loadUserFriend() {
    let url = `api/v1/friendships/getFriend/` + userID;

    await $.ajax({
        url: url,
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${tokenUser}`
        },
    })
        .done(function (response) {
            appendListFriend(response);
        })
        .fail(function (_, textStatus) {
            console.log(textStatus)
        });
}

async function loadUserFollower() {
    let url = `api/v1/friendships/getFollower/` + userID;

    await $.ajax({
        url: url,
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${tokenUser}`
        },
    })
        .done(function (response) {
            appendListFollower(response);
        })
        .fail(function (_, textStatus) {
            console.log(textStatus)
        });
}

async function appendListFriend(res) {
    let html = ``;
    for (let i = 0; i < res.length; i++) {
        let item = res[i];
        let state = null;
        if (item.receiver.id == userID) {
            if (item.sender.state && item.sender.state == 'ONLINE') {
                state = `<span class="status f-online"></span> `
            } else {
                state = `<span class="status off-online"></span> `
            }
            let userItem = JSON.stringify(item.sender);
            userItem = "!!!" + userItem + "!!!";
            html = html + `<li onclick="renderMessage(${userItem})"><figure><img src="${item.sender.avt}" alt="">` +
                state +
                `</figure>
                                                    <div class="people-name">
                                                        <span>${item.sender.username}</span>
                                                    </div>
                                                </li>`;
        } else {
            if (item.receiver.state && item.receiver.state == 'ONLINE') {
                state = `<span class="status f-online"></span> `
            } else {
                state = `<span class="status off-online"></span> `
            }
            let userItem = JSON.stringify(item.receiver);
            userItem = "!!!" + userItem + "!!!";
            html = html + `<li onclick="renderMessage(${userItem})"><figure><img src="${item.receiver.avt}" alt="">` +
                state +
                `</figure>
                                                    <div class="people-name">
                                                        <span>${item.receiver.username}</span>
                                                    </div>
                                                </li>`;
        }

    }
    $('#listFriends').empty().append(html);
}

async function appendListFollower(res) {
    let html = ``;
    for (let i = 0; i < res.length; i++) {
        let item = res[i];
        html = html + ` <li><figure><img src="${item.sender.avt}" alt=""></figure>
                                                <div class="friend-meta">
                                                    <h4><a href="#" title="">${item.sender.username}</a></h4>
                                                    <div class="d-flex">
                                                    <button onclick="applyFriends(${item.sender.id});" id="btnApplyFriend_${item.sender.id}" class="underline small mr-2" style="background: #0a98dc; color: #FFFFFF">Apply</button>
                                                    <button onclick="removeFriends(${item.id});" id="btnRemoveFriend_${item.sender.id}" class="underline small" style="background: #cccccc; color: #FFFFFF">Remove</button>
                                                </div>
                                                </div>
                                            </li>`;

    }
    $('#listFollower').empty().append(html);
}

async function loadAllUser() {
    let url = `api/v1/user/getUser/` + userID;

    await $.ajax({
        url: url,
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${tokenUser}`
        },
    })
        .done(function (response) {
            appendListUser(response);
        })
        .fail(function (_, textStatus) {
            console.log(textStatus)
        });
}

async function appendListUser(res) {
    let html = ``;
    for (let i = 0; i < res.length; i++) {
        let item = res[i];
        html = html + `<li><figure><img src="${item.avt}" alt=""></figure>
                                                <div class="friend-meta">
                                                    <h4><a href="#" title="">${item.username}</a></h4>
                                                     <button onclick="addFriends(${item.id});" id="btnApplyFriend_${item.id}" class="underline small mr-2" style="background: #0a98dc; color: #FFFFFF">Add Friend</button>
                                                </div>
                                            </li>`;

    }
    $('#listUserNoFriend').empty().append(html);
}

async function addFriends(receiverID) {
    let url = `api/v1/friendships/addFriends`;

    let formData = {
        receiver: {
            id: receiverID
        },
        sender: {
            id: userID
        }
    }

    await fetch(url, {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${tokenUser}`
        },
        body: JSON.stringify(formData),

    })
        .then(response => {
            if (response.status == 200) {
                loadAllUser();
                loadUserFriend();
                loadUserFollower();
            } else {
                alert("Error! Please try again");
            }
        })
        .catch(error => console.log(error));
}

async function applyFriends(senderID) {
    let url = `api/v1/friendships/applyFriends`;

    let formData = {
        receiver: {
            id: userID
        },
        sender: {
            id: senderID
        }
    }

    await fetchUrl(url, formData)
}

async function removeFriends(senderID) {
    let url = `api/v1/friendships/unFriends`;

    // let formData = {
    //     receiver: {
    //         id: userID
    //     },
    //     sender: {
    //         id: senderID
    //     }
    // }

    let formData = {
        id: senderID
    }

    await fetchUrl(url, formData)
}

async function fetchUrl(url, data) {
    await fetch(url, {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${tokenUser}`
        },
        body: JSON.stringify(data),

    })
        .then(response => {
            if (response.status == 200) {
                loadUserFriend();
                loadUserFollower();
            } else {
                alert("Error! Please try again");
            }
        })
        .catch(error => console.log(error));
}

function renderMessage(user) {
    let html = ``;
    let state = ``;
    let sender = JSON.parse(user);
    if (sender.state && sender.state == 'ONLINE') {
        state = `<i>online</i>`
    } else {
        state = `<i>offline</i> `
    }
    html = ` <figure><img src="${sender.avt}" alt="">
                                                    </figure>
                                                    <span>${sender.username} ${state} </span>`;
    $('#chatUserCurrent').empty().append(html);
}