$(document).ready(function () {
    $('.commentShow').on('click', function () {
        let id = $(this).data('id');
        let element = $('#listComment-' + id);
        loadComment(element, id);
        viewBlog(id);
    })

    $('#btnCreateBlog').on('click', function () {
        postBlog();
    })

    $('.likeBlog').on('click', function () {
        let id = $(this).data('id');
        let check = $(this).data('value');
        likeBlog(id, check);
    })

    $('#submitUpload').on('click', function (e) {
        e.preventDefault();
        uploadImageMain('uploadThumbnail').then(function (response) {
            console.log(response)
            $('#imgDemoUpload').removeClass('d-none')
            $('#imgDemoUpload').attr("src", response);
            $('#inputThumbnail').val(response);
        }).catch(function (error) {
            console.error('Error:', error);
        });
    });
})

async function processCreateComment(id) {
    if (event.keyCode === 13) {
        await createComment(id);
    }
}

async function processCreateCommentChild(id, blog) {
    if (event.keyCode === 13) {
        await createCommentChild(id, blog);
    }
}

async function loadComment(element, id) {
    await renderBlog(element, id);
}

async function renderBlog(element, id) {
    let url = `/api/v1/comments/list/` + id;
    let urlUserImg = $('.avtCurrentUser').attr('src');

    await $.ajax({
        url: url,
        method: 'GET',
    })
        .done(function (response) {
            let commentList = response.content;
            let comments = '';
            let blogId = null;
            for (let i = 0; i < commentList.length; i++) {
                let commentChilds = commentList[i].commentsChild;
                blogId = commentList[i].blog.id;
                let item = ``;
                for (let j = 0; j < commentChilds.length; j++) {
                    item = item + `<li>
                                                                <div class="comet-avatar">
                                                                    <img src="${commentChilds[j].userId.avt}" alt="">
                                                                </div>
                                                                <div class="we-comment">
                                                                    <div class="coment-head">
                                                                        <h5><a href="time-line.html" title="">${commentChilds[j].userId.username}</a></h5>
                                                                        <span>${commentChilds[j].createdAt}</span>
                                                                    </div>
                                                                    <p>${commentChilds[j].content}</p>
                                                                </div>
                                                            </li>`
                }
                comments = comments + `<li>
                                                        <div class="comet-avatar">
                                                            <img src="${commentList[i].userId.avt}" alt="">
                                                        </div>
                                                        <div class="we-comment">
                                                            <div class="coment-head">
                                                                <h5><a href="time-line.html" title="">${commentList[i].userId.username}</a>
                                                                </h5>
                                                                <span>${commentList[i].createdAt}</span>
                                                                <a class="we-reply replyComment" onclick="showChildCommentPost(${commentList[i].id}, ${blogId})" data-id="${commentList[i].id}" title="Reply"><i
                                                                        class="fa fa-reply"></i></a>
                                                            </div>
                                                            <p>${commentList[i].content}</p>
                                                        </div>
                                                        <ul>
                                                         ${item}
                                                        </ul>
                                                    </li>
                                                    <li class="post-comment d-none" id="childCommentPost_${commentList[i].id}">
                                                                <div class="comet-avatar"><img src="${urlUserImg}" alt=""></div>
                                                                <div class="post-comt-box">
                                                                    <form>
                                                                        <textarea id="contentCommentChild_${commentList[i].id}" placeholder="Post your comment" onkeypress="processCreateCommentChild(${commentList[i].id}, ${blogId})"></textarea>
                                                                    </form>
                                                                </div>
                                                    </li>`;
            }
            let mainComment = `<ul class="we-comet">
                                                    ${comments}
                                                    <li class="post-comment" id="postComment_${blogId}">
                                                        <div class="comet-avatar">
                                                            <img src="${urlUserImg}" alt="">
                                                        </div>
                                                        <div class="post-comt-box">
                                                            <form>
                                                                <textarea id="contentComment-${id}" placeholder="Post your comment" onkeypress="processCreateComment(${id});" data-id="${id}"></textarea>
                                                            </form>
                                                        </div>
                                                    </li>
                        </ul> `
            element.empty().append(mainComment);
        })
        .fail(function (_, textStatus) {
            console.log(textStatus)
        });
}

myToken = getCookieValue('accessToken');

async function postBlog() {
    try {
        let createBlog = `/api/v1/blogs`;

        let content = $('#contentBlog').val();
        let inputThumbnail = $('#inputThumbnail').val();
        console.log(inputThumbnail);
        if (!inputThumbnail) {
            inputThumbnail = `https://www.techsmith.com/blog/wp-content/uploads/2021/02/video-thumbnails-social.png`;
        }


        let user = {
            id: localStorage.getItem('user_id')
        };

        let requestData = {
            comments: 0,
            content: content,
            likes: 0,
            shares: 0,
            status: `ACTIVE`,
            thumbnail: inputThumbnail,
            user: user,
            views: 0,
        };

        if (content) {
            await fetch(createBlog, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    'Authorization': `Bearer ` + `${myToken}`
                },
                body: JSON.stringify(requestData),

            })
                .then(response => {
                    if (response.status == 200) {
                        window.location.reload();
                    } else {
                        alert("Error! Please try again");
                    }
                })
                .catch(error => console.log(error));

        } else {
            alert('Please enter the content of blog!')
        }

    } catch (error) {
        console.error('Error:', error);
    }
}

async function createComment(id) {
    try {
        let createComment = `/api/v1/comments`;

        let content = $('#contentComment-' + id).val();

        let user = {
            id: localStorage.getItem('user_id')
        };

        let requestData = {
            blogId: id,
            content: content,
            likes: 0,
            status: `ACTIVE`,
            userId: user,
        };

        if (content) {
            await fetch(createComment, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    'Authorization': `Bearer ` + `${myToken}`
                },
                body: JSON.stringify(requestData),

            })
                .then(response => {
                    if (response.status == 200) {
                        let element = $('#listComment-' + id);
                        loadComment(element, id);
                    } else {
                        alert("Error! Please try again");
                    }
                })
                .catch(error => console.log(error));

        } else {
            alert('Please enter the content of blog!')
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function likeBlog(id, check) {
    let likeUrl = `/api/v1/blogs/` + id + `?check=` + check;
    await fetch(likeUrl, {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${myToken}`
        },
    })
        .then(response => {
            if (response.status == 200) {
                return response.json();
            }
        })
        .then(response => {
            if (check == 1) {
                $('#like-' + id).addClass('d-none');
                $('#dislike-' + id).removeClass('d-none');
            } else {
                $('#like-' + id).removeClass('d-none');
                $('#dislike-' + id).addClass('d-none');
            }
            let user = response.user.id;
            findUserId(user);
            $('.likeQty-' + id).text(response.likes);
        })
        .catch(error => console.log(error));
}

async function viewBlog(id) {
    let likeUrl = `/api/v1/blogs/view/` + id;
    await fetch(likeUrl, {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ` + `${myToken}`
        },
    })
        .then(response => {
            if (response.status == 200) {
                return response.json();
            }
        })
        .then(response => {
            let user = response.user.id;
            findUserId(user);
            $('.viewQty-' + id).text(response.views);
        })
        .catch(error => console.log(error));
}

async function showChildCommentPost(id, blogId) {
    await $('#postComment_' + blogId).addClass('d-none');
    await $('.post-comment').addClass('d-none');
    await $('#childCommentPost_' + id).removeClass('d-none');
}

async function createCommentChild(id, blogID) {
    try {
        let createComment = `/api/v1/comments`;

        let content = $('#contentCommentChild_' + id).val();

        let user = {
            id: localStorage.getItem('user_id')
        };

        let requestData = {
            commentParent: id,
            content: content,
            likes: 0,
            status: `ACTIVE`,
            userId: user,
        };

        if (content) {
            await fetch(createComment, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json',
                    'Authorization': `Bearer ` + `${myToken}`
                },
                body: JSON.stringify(requestData),

            })
                .then(response => {
                    if (response.status == 200) {
                        let element = $('#listComment-' + blogID);
                        loadComment(element, blogID);
                    } else {
                        alert("Error! Please try again");
                    }
                })
                .catch(error => console.log(error));

        } else {
            alert('Please enter the content of blog!')
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function isElementVisible(element) {
    var top = element.offset().top;
    var bottom = top + element.outerHeight();

    var viewportTop = $(window).scrollTop();
    var viewportBottom = viewportTop + $(window).height();

    return (bottom > viewportTop && top < viewportBottom);
}