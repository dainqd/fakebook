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
    window.location.href = '/login';
}
