function post() {
    let questionId = $("#questionId").val();
    let content = $("#textArea").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            parentId: questionId,
            content: content,
            type: 1
        }),
        dataType: "JSON",
        success: function (response) {
                if (response.code == 200) {
                    window.location.reload();
                    // $("#comment-section").hide();
                } else {
                    alert(response.message);
                }
            }
    })

}

function likeCount() {
    let commentId = $("#commentId").val();
   let likeCount = $("#likeCountButton").val();
   $.ajax({
       type: "POST",
       url: "/likeCount",
       contentType: "application/json",
       data:JSON.stringify({
           likeCount:likeCount,
           commentId:commentId,
       }),

       success: function(dto){
               $("#likeCountButton").text(dto.likeCount++);

       }

   })
}