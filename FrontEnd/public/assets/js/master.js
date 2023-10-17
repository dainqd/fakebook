function saveValue() {
    var url_string = window.location;
    var url = new URL(url_string);
    var accessToken = url.searchParams.get("accessToken");
    var username = url.searchParams.get("username");
    var id = url.searchParams.get("id");
    if (accessToken) {
        sessionStorage.setItem('accessToken', accessToken);
        sessionStorage.setItem('username', username);
        sessionStorage.setItem('id', id);
        console.log(accessToken)
        window.location.href = `/dashboard`;
    }
}

saveValue();