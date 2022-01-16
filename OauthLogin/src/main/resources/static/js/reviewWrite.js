// $('.uploadBtn').on('click' , function () {
//
//     e.preventDefault();
//     // 실제 업로드 부분
//     // upload ajax
//     $.ajax({
//         url: '/uploadAjax',
//         processData: false,
//         contentType: false,
//         data: formData,
//         type: 'POST',
//         dataType: 'json',
//         success: function (result) {
//             console.log(result);
//
//         },
//         error: function (jqXHR, testStatus, errorThrown){
//             console.log(testStatus);
//         }
//     });
//
//
// });

// 사진 올리면 사진을 다시 보여주는 함수
$('#chooseFile').change(function () {

    readInputFile(this);

    function readInputFile(input) {
        if(input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                // $('#preview').html("<img src="+ e.target.result +">");
                $('#img').attr("src" , e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    // var formData = new FormData();
    //
    // var inputFile = $("input[type='file']");
    //
    // var files = inputFile[0].files;
    //
    // for(var i = 0 ;  i < files.length; i++){
    //     console.log(files[i]);
    //     formData.append("uploadFiles", files[i]);
    // }

    // 실제 업로드 부분
    // upload ajax
    // $.ajax({
    //     url: '/uploadAjax',
    //     processData: false,
    //     contentType: false,
    //     data: formData,
    //     type: 'POST',
    //     dataType: 'json',
    //     success: function (result) {
    //         console.log(result);
    //
    //     },
    //     error: function (jqXHR, testStatus, errorThrown){
    //         console.log(testStatus);
    //     }
    // });
});

    // function showUploadedImages(arr) {
    //
    //     console.log(arr);
    //
    //     // var divArea = $(".uploadResult");
    //
    //     // var str = "";
    //
    //     for(var i = 0 ; i < arr.length; i++){
    //         // str += "<div>";
    //         // str += "<img src='/display?fileName="+arr[i].thumbnailURL+"'>";
    //         // str += "<button class='removeBtn' data-name='"+arr[i].imageURL+"'>REMOVE</button>"
    //         // str += "</div>"
    //         console.log(arr[i].imageURL);
    //
    //         console.log("goal : " + "/images/review/"+arr[i].uuid+"_"+arr[i].fileName);
    //
    //
    //
    //         // console.log(arr[i].thumbnailURL);
    //         // $('#img').attr("src" , '/display?fieName='+arr[i].imageURL);
    //         // $('#img').attr("src" , arr[i].imageURL);
    //         // $('#img').attr("src" , "/images/review/"+arr[i].uuid+"_"+arr[i].fileName);
    //
    //
    //         let strUuid = String(arr[i].uuid);
    //         let strFileName = String(arr[i].fileName);
    //         let decodedStrFileName = decodeURI(strFileName);
    //         $('#img').attr("src" , "/images/review/"+strUuid+"_"+decodedStrFileName);
    //
    //
    //         // $('#img').attr("src" , "/images/review/"+"8b5a6981-1a60-4943-8265-e355ca72db22"+"_"+"12page.jpg");
    //         // $('#img').attr("src" , "%2Fimages%2Freview%2Fa65f37a1-be88-4910-bae7-070bec4253aa_08page.jpg");
    //
    //         var reader = new FileReader();
    //
    //         reader.onload = function(event) {
    //             // var img = document.createElement("img");
    //             // img.setAttribute("src", event.target.result);
    //             // document.querySelector("div#image_container").appendChild(img);
    //
    //             $('#img').attr("src" , event.target.result);
    //         };
    //
    //         reader.readAsDataURL(event.target.files[0]);
    //
    //
    //     // divArea.append("<img src='/display?fileName="+arr[i].thumbnailURL+"'>");
    //     }
    //     // divArea.append(str);
    // }

$('#reviewForm').submit(function (event) {

    event.preventDefault();

    var textareaText = $("#reviewText").text();

    if(textareaText != ""){
        alert("후기 글을 작성해주세요");
    }else{

        var formData = new FormData();

        var inputFile = $("input[type='file']");

        var files = inputFile[0].files;

        for(var i = 0 ;  i < files.length; i++){
            console.log(files[i]);
            formData.append("uploadFiles", files[i]);
        }

        // 실제 업로드 부분
        // upload ajax
        $.ajax({
            url: '/uploadAjax',
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            dataType: 'json',
            success: function (result) {
                console.log(result);

                for(let i = 0 ; i < result.length; i++){
                    let strUuid = result[i].uuid;
                    let strFileName = result[i].fileName;

                    // $("#imageNameForInsert").val("/images/review/"+strUuid+"_"+strFileName);
                    $("#imageNameForInsert").val(strUuid+"_"+strFileName);
                }

                $("#reviewForm").unbind();
                $("#reviewForm").submit();

            },
            error: function (jqXHR, testStatus, errorThrown){
                console.log(testStatus);
            }
        });

    }
});