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

// async function checkAdmin(token) {
//     let url = ``;
//
//     let headers = {
//         'content-type': 'application/json',
//         'Authorization': `Bearer ${token}`
//     };
//
//     await
//         $.ajax({
//             url: url,
//             method: 'GET',
//             data: headers,
//         })
//             .done(function (response) {
//                 return response.json();
//             })
//             .fail(function (_, textStatus) {
//
//                 console.log(textStatus)
//             });
//
// }
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

            $(".avtCurrentUser").attr("src",response.avt);
            localStorage.setItem('user_id', response.id)

            adminOpen();
        })
        .catch(error => console.log(error));
}

checkAdmin();

async function adminOpen() {
    let admin = $('#myAdmin');
    if (isAdmin){
        await admin.removeClass('d-none');
        await admin.on('click', function () {
            window.location.href = 'http://localhost:3000';
        })
    } else {
        await admin.addClass('d-none');
    }
}