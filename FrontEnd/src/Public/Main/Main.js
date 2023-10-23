import $ from "jquery";

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

export default uploadImageMain