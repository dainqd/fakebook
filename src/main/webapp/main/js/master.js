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

const token = getCookieValue('accessToken');
const username = getCookieValue('username');

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
            localStorage.setItem('user_id', response.id)
            $(".likeUser").text(response.likes);
            $(".viewUser").text(response.views);
            adminOpen();
        })
        .catch(error => console.log(error));
}

checkAdmin();

async function findUserId(id) {
    await fetch('/api/v1/user/' + id, {
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
            let name;
            if (!response.firstName) {
                name = 'Default';
            } else {
                name = response.firstName;
            }
            if (!response.lastName) {
                name = name + ' ' + 'Default';
            } else {
                name = name + ' ' + response.lastName;
            }
            $('.fullName').text(name);
            $('.username').text(response.username);

        })
        .catch(error => console.log(error));
}

findUserId(localStorage.getItem('user_id'));

async function adminOpen() {
    let admin = $('#myAdmin');
    if (isAdmin) {
        await admin.removeClass('d-none');
        await admin.on('click', function () {
            window.location.href = 'http://localhost:3000';
        })
    } else {
        await admin.addClass('d-none');
    }
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

function getUserDetail(id) {
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