<div class="modal-dialog">
    <div class="modal-content">

        <form action="{{route('user.upload.image')}}" method="post" enctype="multipart/form-data">
            @csrf
            <div class="modal-header">
                <h4 class="modal-title">Upload Image</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <div class="modal-body">
                <input type="file" name="thumbnail" id="uploadThumbnail" required accept="image/*">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-success" id="submitUpload">Submit</button>
            </div>
        </form>

    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $('#submitUpload').on('click', function () {
            uploadImage();
        })
    })

    async function uploadImage() {
        try {
            const url = `{{route('user.upload.image')}}`;

            const fileInput = document.getElementById('uploadThumbnail');
            const file = fileInput.files[0];

            const csrfToken = ` {{ csrf_token() }}`;

            if (!file) {
                console.error('No file selected');
                return;
            }

            const requestData = {
                _token: csrfToken,
                quantity: file,
            };

            $.ajax({
                url: url,
                method: 'POST',
                thumbnail: requestData,
            })
                .done(function (response) {
                    console.log(response);
                })
                .fail(function (_, textStatus) {
                });

        } catch (error) {
            console.error('Error:', error);
        }
    }
</script>
