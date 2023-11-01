let userID = getCookieValue('userId');
let tokenUser = getCookieValue('accessToken')

async function appendListFriend(response) {
    let res = JSON.parse(response.body);
    let html = ``;
    let sup = ``;
    for (let i = 0; i < res.length; i++) {
        sup = `<sup class="text-danger">new</sup>`;
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
            html = html + `<li class="userConnected" onclick="connectedUser(${item.sender.id})" data-id="${item.sender.id}"><figure><img src="${item.sender.avt}" alt="">` +
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
            html = html + `<li class="userConnected" onclick="connectedUser(${item.receiver.id})" data-id="${item.receiver.id}"><figure><img src="${item.receiver.avt}" alt="">` +
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

async function appendListFollower(response) {
    let res = JSON.parse(response.body);
    let html = ``;
    let follower = ``;
    let follownig = ``;
    for (let i = 0; i < res.length; i++) {
        let item = res[i];
        html = html + ` <li><figure><img src="${item.avt}" alt=""></figure>
                                                <div class="friend-meta">
                                                    <h4><a href="#" title="">${item.username}</a></h4>
                                                    <div class="d-flex">
                                                    <button onclick="applyFriends(${item.id});" id="btnApplyFriend_${item.id}" class="underline small mr-2" style="background: #0a98dc; color: #FFFFFF">Apply</button>
                                                    <button onclick="removeFriends(${item.id});" id="btnRemoveFriend_${item.id}" class="underline small" style="background: #cccccc; color: #FFFFFF">Remove</button>
                                                </div>
                                                </div>
                                            </li>`;
        follower = follower + `<li> <div class="nearly-pepls">
                                                            <figure>
                                                                <a href="#" title=""><img src="${item.avt}" alt=""></a>
                                                            </figure>
                                                            <div class="pepl-info">
                                                                <h4><a href="#" title="">${item.username}</a></h4>
                                                                <span>${item.email}</span>
                                                                <a style="cursor: pointer" onclick="removeFriends(${item.id});" title="" class="add-butn more-action" data-ripple="">delete Request</a>
                                                                <a style="cursor: pointer" onclick="applyFriends(${item.id});" title="" class="add-butn" data-ripple="">Confirm</a>
                                                            </div>
                                                        </div>
                                                    </li>`;
        follownig = follownig + `<li><figure><img src="${item.avt}" alt=""></figure>
                                                <div class="friend-meta">
                                                    <h4><a href="#" title="">${item.username}</a></h4>
                                                    <a style="cursor: pointer" onclick="applyFriends(${item.id});" title="" class="underline">Confirm</a>
                                                </div>
                                            </li>`;


    }
    $('#listFollower').empty().append(html);
    $('#countListFollow').text(res.length);
    $('#mainListFollow').empty().append(follower);
    $('#follownig').empty().append(follownig);
}

async function appendListUser(response) {
    let res = JSON.parse(response.body);
    let html = ``;
    let allUser = ``;
    for (let i = 0; i < res.length; i++) {
        let item = res[i];
        html = html + `<li><figure><img src="${item.avt}" alt=""></figure>
                                                <div class="friend-meta">
                                                    <h4><a href="#" title="">${item.username}</a></h4>
                                                     <button onclick="addFriends(${item.id});" id="btnApplyFriend_${item.id}" class="underline small mr-2" style="background: #0a98dc; color: #FFFFFF">Add Friend</button>
                                                </div>
                                            </li>`;
        allUser = allUser + `<li><div class="nearly-pepls">
                                                            <figure>
                                                                <a href="#" title=""><img src="${item.avt}" alt=""></a>
                                                            </figure>
                                                            <div class="pepl-info">
                                                                <h4><a href="#" title="">${item.username}</a></h4>
                                                                <span>${item.email}</span>
                                                                <a style="cursor: pointer" onclick="addFriends(${item.id});" title="" class="add-butn" data-ripple="">add friend</a>
                                                            </div>
                                                        </div>
                                                    </li>`;

    }
    $('#listUserNoFriend').empty().append(html);
    $('#countListUserAll').text(res.length)
    $('#mainListUserAll').empty().append(allUser);
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
                if (stompClient) {
                    stompClient.send(`/app/friend.getList/${userID}`)
                    stompClient.send(`/app/friend.getAllUser/${userID}`)
                    stompClient.send(`/app/friend.getAllFriendPending/${userID}`)
                    stompClient.send(`/app/friend.getAllFriendApproved/${userID}`)


                    stompClient.send(`/app/friend.getList/${receiverID}`)
                    stompClient.send(`/app/friend.getAllUser/${receiverID}`)
                    stompClient.send(`/app/friend.getAllFriendPending/${receiverID}`)
                    stompClient.send(`/app/friend.getAllFriendApproved/${receiverID}`)
                }
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

    await fetchUrl(url, formData, 100)
}

async function removeFriends(senderID) {
    let url = `api/v1/friendships/unFriends`;

    let formData = {
        id: senderID
    }

    await fetchUrl(url, formData, 0)
}

async function fetchUrl(url, data, cal) {
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
                if (stompClient) {
                    stompClient.send(`/app/friend.getList/${userID}`)
                    stompClient.send(`/app/friend.getAllUser/${userID}`)
                    stompClient.send(`/app/friend.getAllFriendPending/${userID}`)
                    stompClient.send(`/app/friend.getAllFriendApproved/${userID}`)

                    if (cal == 0) {
                        stompClient.send(`/app/friend.getList/${data.id}`)
                        stompClient.send(`/app/friend.getAllUser/${data.id}`)
                        stompClient.send(`/app/friend.getAllFriendPending/${data.id}`)
                        stompClient.send(`/app/friend.getAllFriendApproved/${data.id}`)
                    } else {
                        stompClient.send(`/app/friend.getList/${data.sender.id}`)
                        stompClient.send(`/app/friend.getAllUser/${data.sender.id}`)
                        stompClient.send(`/app/friend.getAllFriendPending/${data.sender.id}`)
                        stompClient.send(`/app/friend.getAllFriendApproved/${data.sender.id}`)
                    }
                }

            } else {
                alert("Error! Please try again");
            }
        })
        .catch(error => console.log(error));
}

async function supAppendFriend(response) {
    let res = JSON.parse(response.body);
    let html = ``;
    let friend = ``;
    for (let i = 0; i < res.length; i++) {
        let item = res[i];
        html = html + `<li><div class="nearly-pepls"><figure>
                                                                <a href="#" title=""><img src="${item.avt}" alt=""></a>
                                                            </figure>
                                                            <div class="pepl-info">
                                                                <h4><a href="#" title="">${item.username}</a></h4>
                                                                <span>${item.email}</span>
                                                                <a href="#" title="" class="add-butn more-action" data-ripple="">unfriend</a>
                                                            </div>
                                                        </div>
                                                    </li>`;
        friend = friend + `<li><figure><img src="${item.avt}" alt="">
                                                    <span class="status f-online"></span>
                                                </figure>
                                                <div class="friendz-meta">
                                                    <a href="#">${item.username}</a>
                                                </div>
                                            </li>`;
    }
    $('#countListFriend').text(res.length);
    $('#mainListFriend').empty().append(html);
    $('#people-list').empty().append(friend);
}