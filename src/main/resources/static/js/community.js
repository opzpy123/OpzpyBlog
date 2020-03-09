/**
 * 提交回复
 */
function post() {
    var questionId = $("#questionId").val();
    var content = $("#textArea").val();
    comment2target(questionId,1,content);

}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content)
}

function likeCount(e) {
    let commentId = e.getAttribute("id");
    $.ajax({
        type: "POST",
        url: "/likeCount/"+commentId,
    });
    window.location.reload();


    console.log(e);

}



/**
 * 封装的post请求
 */
function comment2target(targetId,type,content){

    if(!content){
        alert("回复不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            parentId: targetId,
            content: content,
            type: type
        }),
        dataType: "JSON",
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                alert("回复成功");
            } else {
                alert(response.message);
            }
        }
    })

}


/**
 * 展开二级评论
 *
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<img/>", {
                        "class": "pull-left media-object img-rounded",
                        "style":"width: 25px;height: 25px ",
                        "src": '/'+comment.user.avatarUrl
                    })).append($("<h5/>", {
                        "class": "media-heading",
                        "style":"margin-top: 3px",
                        "html": comment.user.username+' :',
                    })).append($("<h4/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>",{
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY/MM/DD HH:MM'),
                    })));

                    var mediaFootElement = $("<hr/>");

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaBodyElement).append(mediaFootElement);

                    var commentElement = $("<div/>", {
                        "class": "col-12 "
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
