const baseUrl = `http://localhost:`;

function getCookieValue(cookieName) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
        if (cookie.startsWith(`${cookieName}=`)) {
            return cookie.substring(cookieName.length + 1, cookie.length);
        }
    }
    return null;
}

function setCookie(name, value, days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

var token = getCookieValue('accessToken');
var username = getCookieValue('username');
var userIdLogined = 0;

$(document).ready(function () {
    let currentUrl = $(location).attr('href');
    let array = null;
    array = currentUrl.split('/');
    let item = array[array.length - 1];

    let navbarUser = $('.navbarUser');

    switch (item) {
        case 'message':
            navbarUser.removeClass('active');
            $('.navbarUserMessage').addClass('active');
            break;
        case 'blog':
            navbarUser.removeClass('active');
            $('.navbarUserBlog').addClass('active');
            break;
        case 'photos':
            navbarUser.removeClass('active');
            $('.navbarUserPhoto').addClass('active');
            break;
        case 'friends':
            navbarUser.removeClass('active');
            $('.navbarUserFriend').addClass('active');
            break;
        case 'groups':
            navbarUser.removeClass('active');
            $('.navbarUserGroup').addClass('active');
            break;
        case 'notifications':
            navbarUser.removeClass('active');
            $('.navbarUserNotifications').addClass('active');
            break;
        default:
            navbarUser.removeClass('active');
    }
})

function checkLogin() {
    if (!token || !username) {
        window.location.href = '/login';
    }
}

checkLogin();

function deleteCookie(cookieName) {
    document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
}

function logout() {
    deleteCookie('accessToken');
    deleteCookie('username');
    localStorage.clear();
    window.location.href = '/login';
}

function messageUrl() {
    window.location.href = `/message`;
}

var isAdmin = false;

async function checkAdmin() {
    await fetch('http://localhost:8888/api/v1/user/find/user-by-username/' + username, {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    })
        .then(response => {
            if (response.status == 200) {
                return response.json();
            }
        })
        .then((response) => {
            let listRole = response.roles;
            for (let i = 0; i < listRole.length; i++) {
                if (listRole[i].name == 'ADMIN') {
                    isAdmin = true;
                }
            }

            $(".avtCurrentUser").attr("src", response.avt);
            userIdLogined = response.id;
            setCookie(`userId`, response.id, 7),
                localStorage.setItem('user_id', response.id)
            $(".likeUser").text(response.likes);
            $(".viewUser").text(response.views);
            userOpen();
            adminOpen();
        })
        .catch(error => console.log(error));
}

checkAdmin();

async function findUserId() {
    await fetch('/api/v1/user/' + localStorage.getItem('user_id'), {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    })
        .then(response => {
            if (response.status == 200) {
                return response.json();
            }
        })
        .then((response) => {

            $(".likeUser").text(response.likes);
            $(".viewUser").text(response.views);
            $('.imgUserThumbnail').attr("src", response.thumbnail);
            $('.imgUserAvatar').attr("src", response.avt);

            $('.fullName').text(username);
            $('.username').text(response.email);

        })
        .catch(error => console.log(error));
}

findUserId();

async function adminOpen() {
    let admin = $('#myAdmin');
    if (isAdmin) {
        await admin.removeClass('d-none');
        await admin.on('click', function () {
            let port = `3000`;
            const adminUrl = `${baseUrl}${port}/?accessToken=${token}&username=${username}&id=${userIdLogined}`;
            // window.location.href = adminUrl;
            window.open(adminUrl, "_blank");
        })
    } else {
        await admin.addClass('d-none');
    }
}

async function userOpen() {
    let profile = $('#myProfile');
    await profile.on('click', function () {
        let port = `3000`;
        const profileUrl = `${baseUrl}${port}/?accessToken=${token}&username=${username}&id=${userIdLogined}`;
        window.open(profileUrl, "_blank");
    })
}

function uploadImageMain(idInput) {
    return new Promise(function (resolve, reject) {
        const urlUpload = 'http://127.0.0.1:8000/upload-image';
        const formData = new FormData();
        const photo = $('#' + idInput)[0].files[0];

        formData.append('thumbnail', photo);

        $.ajax({
            url: urlUpload,
            type: 'POST',
            data: formData,
            contentType: false,
            cache: false,
            processData: false,
            success: function (response) {
                resolve(response); // Trả về response khi thành công
            },
            error: function (error) {
                reject(error); // Trả về error khi có lỗi
            }
        });
    });
}

async function getUserDetail(id) {
    return fetch('/api/v1/user/' + id, {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error('Error fetching user detail');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            throw error;
        });
}

//advertisment-box
async function getMarketing() {
    return fetch('/api/v1/marketing', {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error('Error fetching user detail');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            throw error;
        });
}

async function generateMarketing() {
    await getMarketing()
        .then(response => {
            let marketingArray = response.content;
            let count = marketingArray.length;
            let number = getRandomInt(count);
            let marketing = marketingArray[number];
            let number2 = getRandomInt(count);
            let marketing2 = marketingArray[number2];
            showMarketing(marketing, marketing2);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

generateMarketing();

function showMarketing(marketing, marketing2) {
    let html = `<h4 class="">${marketing.content}</h4>
                                        <figure>
                                            <a href="#" title="Advertisment"><img
                                                    src="${marketing.thumbnail}"
                                                    alt=""></a>
                                        </figure>`;
    let html2 = `<h4 class="">${marketing2.content}</h4>
                                        <figure>
                                            <a href="#" title="Advertisment"><img
                                                    src="${marketing2.thumbnail}"
                                                    alt=""></a>
                                        </figure>`;
    document.querySelectorAll('.marketing-item')[0].innerHTML = html;
    document.querySelectorAll('.marketing-item')[1].innerHTML = html2;
}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

