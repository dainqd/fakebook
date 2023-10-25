$(document).ready(function () {
    $('#submitUpload').on('click', function (e) {
        e.preventDefault();
        uploadImageMain('uploadThumbnail').then(function (response) {
            $('#btnSaveAvt').removeClass('d-none')
            $('.imgUserAvatar').addClass('d-none')
            $('.imageDemo').removeClass('d-none')
            $('.imgUserAvatarDemo').attr("src", response);
            $('#inputAvtUploaded').val(response);
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });

    $('#submitUploadThumbnail').on('click', function (e) {
        e.preventDefault();
        uploadImageMain('uploadImage').then(function (response) {
            $('#btnSaveAvt').removeClass('d-none')
            $('#btnEditCoverPhoto').addClass('d-none')
            $('#btnSaveCoverPhoto').removeClass('d-none')
            $('#imgUserThumbnail').attr("src", response);
            $('#inputThumbnailUploaded').val(response);
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });

    $('#btnSaveAvt').on('click', function () {
        changeAvt();
    })

    $('#btnSaveCoverPhoto').on('click', function () {
        changeThumbnail();
    })
});

let myToken = getCookieValue('accessToken');

async function changeAvt() {
    try {
        let urlChangeAvt = `/api/v1/user/change-avt/` + localStorage.getItem('user_id');
        let requestData = $('#inputAvtUploaded').val();

        fetch(urlChangeAvt, {
            method: 'PUT',
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ` + `${myToken}`
            },
            body: requestData,

        })
            .then(response => {
                alert('Change avatar success')
                window.location.reload();
            })
            .catch(error => console.log(error));
    } catch (error) {
        console.error('Error:', error);
    }
}

async function changeThumbnail() {
    try {
        let urlChangeThumbnail = `/api/v1/user/change-thumbnail/` + localStorage.getItem('user_id');
        let requestData = $('#inputThumbnailUploaded').val();

        fetch(urlChangeThumbnail, {
            method: 'PUT',
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ` + `${myToken}`
            },
            body: requestData,

        })
            .then(response => {
                alert('Change cover photo success!')
                window.location.reload();
            })
            .catch(error => console.log(error));
    } catch (error) {
        console.error('Error:', error);
    }
}