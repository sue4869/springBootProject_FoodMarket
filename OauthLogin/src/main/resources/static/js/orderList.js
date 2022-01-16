$('.btnSearch').on('click' , function () {

    console.log($('.find').val());

    // var data = {
    //     "fields" : ["goodName"],
    //     "searchTerm" : $('.find').val(),
    //     "sortBy" : "id",
    //     "order" : "DESC"
    // }

    // $.ajax({
    //     url : '/api/orderProduct/search',
    //     type : 'post',
    //     dataType: 'JSON',
    //     contentType: "application/json; charset=utf-8",
    //     data :
    //         JSON.stringify(data),
    //         // opSearchList: "opSearchList"
    //     success : function (result) {
    //         console.log("서치 성공!");
    //         console.log(result);
    //     },
    //     error : function (error){
    //         console.log("서치 실패!");
    //         console.log(error);
    //     }
    // })

// 찾기 마지막 구현
    // $.ajax({
    //     url : '/api/orderProduct/search',
    //     type : 'get',
    //     contentType: "application/json; charset=utf-8",
    //     data : {
    //         "goodName" : $('.find').val()
    //     },
    //     // opSearchList: "opSearchList"
    //     success : function (result) {
    //         console.log("서치 성공!");
    //         console.log(result);
    //     },
    //     error : function (error){
    //         console.log("서치 실패!");
    //         console.log(error);
    //     }
    // })

});

// var data = {
//     "id" : $("input[name=productIdForSend]").eq(i).val(),
//     "price" : $("input[name=price]").eq(i).val(),
//     "goodName" :  $("input[name=goodName]").eq(i).val(),
//     "image" : $("input[name=images]").eq(i).val(),
//     "count" : $("input[name=count]").eq(i).val()
//     // "regDate" : Date.now()
// };
// // var jsonData = JSON.stringify(productItem);
// //
// // console.log(jsonData);
//
// // console.log("FormData : " , FormData);
// console.log(data);
//
// $.ajax({
//     url : "/api/orderProduct",
//     type : 'post',
//     dataType: 'JSON',
//     contentType : "application/json; charset=utf-8",
//     data : JSON.stringify(data),
//     success : function (){
//         console.log("서치를위한 상품등록 성공");
//     },
//     error : function () {
//         console.log("서치를위한 상픔등록 실패");
//     }
// });

$('.delivery').on('click' , function () {
    // $.ajax({
    //     url : '/selectDelivery02',
    //     type : 'get',
    //     success : function () {
    //         console.log("배송조회로 가기 성공~");
    //     },
    //     error : function () {
    //         console.log("배송조회로 가기 실패~")
    //     }
    //
    // });
    // var url_str = "/selectDelivery?" +
    //     "deliveryStatus=" + $('#deliveryStatus'+id).text() +
    //     // "&regDate=" + $('#regDateForSend'+id).text() +
    //     "&image=" + $('#image'+id).text() +
    //     "&goodName=" + $('#goodName'+id).text() +
    //     "&price=" + $('#price'+id).text() +
    //     "&count=" + $('#count'+id).text();

    // console.log(url_str);
    // location.herf = url_str;

    let str =  $(this).attr('id');
    let id = str.substring(14);


    console.log($('#deliveryStatus'+id).text());
    console.log($('#orderProductIdForSend'+id).val());

    location.href = "/selectDelivery?deliveryStatus="+$('#deliveryStatus'+id).text() +
        "&image=" + $('#image'+id).attr('src') +
        "&goodName=" + $('#goodName'+id).text() +
        "&price=" + $('#price'+id).text() +
        "&count=" + $('#count'+id).text() +
        "&regDateNotFormat=" + $('#regDateNotFormat'+id).val() +
        "&requestMessage=" + $('#requestMessage'+id).val() +
        "&orderProductIdForSend=" +$('#orderProductIdForSend'+id).val();
});

$('.change').on('click' , function () {

    let str = $(this).attr('id');
    let id = str.substring(12);

    // "productId="+$('#orderProductIdForSend'+id).val()+
    // "&count="+$('#count'+id).text() +
    // "&orderRegDate="+$('#regDateForSend' + id).text();

    location.href="/refund01?goodName=" +$('#goodName'+id).text()+
        "&price="+$('#price'+id).text()+
        "&count="+$('#count'+id).text()+
        "&orderProductIdForSend=" + $('#orderProductIdForSend'+id).val()+
        "&orderRegDate=" + $('#regDateForSend' + id).text() +
        "&sendNum="+ $('#orderProductSendNum'+id).val() +
        "&userId=" + $('.userIdForSend').val();

});

$('.cancel').on('click' , function () {

    let str = $(this).attr('id');
    let id = str.substring(6);

    // console.log($('.deliveryStatusForCancel').text());
    if($('#deliveryStatus'+id).text() == "주문접수") {
        // $('.cancel').attr("disabled" , false);

        $.ajax({

            url : "/orderListRemove" ,
            type : "post",
            data : {
                "orderProductId" : $('#orderProductIdForSend'+id).val()
            },
            success : function () {
                console.log("삭제 성공");
                alert(" 취소 되었습니다!");
                location.reload();
            },
            error : function () {
                console.log("삭제 실패");
            }


        });
        // 취소 접수일
        // 주문일
        // 주문번호
        // 환불금액
        // 상품명
        // 갯수
        // 가격
        $.ajax({
            url : "/orderListAddCancel",
            type: "post",
            data : {
                "goodName" : $('#goodName'+id).text(),
                "price" : $('#price'+id).text(),
                "count" : $('#count'+id).text(),
                "orderProductIdForSend" : $('#orderProductIdForSend'+id).val(),
                "orderRegDate" : $('#regDateForSend' + id).text(),
                "sendNum" : $('#orderProductSendNum'+id).val(),
                "userId" : $('.userIdForSend').val()
            },
            success : function () {
                console.log("취소내역에 추가 성공");
            },
            error : function () {
                console.log("취소내역에 추가 실패")
            }
        })
    }else{
        // $('.cancel').attr("disabled" , true);
        alert("주문접수 예외일때는 상품을 취소할수없습니다.");
    }

});