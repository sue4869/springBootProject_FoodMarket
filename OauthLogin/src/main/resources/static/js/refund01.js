var countBtn  = 0;
$("input:radio[name=check]").click(function () {

    console.log($('.nextBtn'));
    if($(this).val() == "1"){
        $("#payDiv").show();
        $('.nextBtn').remove();
        countBtn = 0;
    }
    if($(this).val() == "2" && countBtn == 0){
        $("#payDiv").hide();
        $('.div02').after("<button type='button' class='nextBtn' onclick='move()'>다음</button>");
        countBtn = 1;
    }
});

function move(){
    if($('#buyer01 option:selected').val() == "default" && $('#seller01 option:selected').val() == "default"){
        alert("구매자사유 또는 판매자사유를 선택해주세요");
    }else{

        $.ajax({
           url : '/refundAfterDelete',
           type : 'post',
           data : {
               "productId" : $('.productId').val(),
               "productSendNum" : $('.productSendNum').val(),
               "userId" : $('.userIdForSend').val(),
               "goodName" : $('.refundForGoodName').val(),
               "price" : $('.refundForPrice').val(),
               "sendNum" : $('.refundForSendNum').val(),
               // "requestMessage" : requestMessage,
               // "productIdList" : productIdList,
               // "nameList" : nameList,
               // "imageList" : imageList,
               // "priceList" : priceList,
               "count" : $('.refundForCount').val(),
               "regDate" : $('.refundForOrderReDate').val()
           },
           success : function () {
               console.log("다음 버튼 누르고 환불 성공");
               location.href="/refund02?productId="+$('.productId').val()+
                   "&productSendNum="+$('.productSendNum').val();
           },
           error : function () {
               console.log("다음 버튼 누르고 환불 실패");
           }
        });
    }
}
$('.div04button02').on('click', function () {

    if($('#buyer01 option:selected').val() == "default" && $('#seller01 option:selected').val() == "default"){
        alert("구매자사유 또는 판매자사유를 선택해주세요");
    }
    else{

        // if($('input:radio[name="check"]:checked').val() == "1"){
        $('#dialog-message').dialog({
            modal: true,
            buttons: {
                "예": function() {
                    $(this).dialog('close');
                    $.ajax({
                        url : "/kakaoPayCheckForRefund" ,
                        data : {

                            // "goodName" : $('#goodName'+id).text(),
                            // "price" : $('#price'+id).text(),
                            // "count" : $('#count'+id).text(),
                            // "orderProductIdForSend" : $('#orderProductIdForSend'+id).val(),
                            // "orderRegDate" : $('#regDateForSend' + id).text(),
                            // "sendNum" : $('#orderProductSendNum'+id).val(),
                            // "userId" : $('.userIdForSend').val()

                            "paySum" : $('.price').val(),
                            "productId" : $('.productId').val(),
                            "productSendNum" : $('.productSendNum').val(),
                            "userId" : $('.userIdForSend').val(),
                            "goodName" : $('.refundForGoodName').val(),
                            "price" : $('.refundForPrice').val(),
                            "sendNum" : $('.refundForSendNum').val(),
                            // "requestMessage" : requestMessage,
                            // "productIdList" : productIdList,
                            // "nameList" : nameList,
                            // "imageList" : imageList,
                            // "priceList" : priceList,
                            "count" : $('.refundForCount').val(),
                            "regDate" : $('.refundForOrderReDate').val()
                            // "detailContextList" : detailContextList
                        },
                        dataType : 'JSON',
                        type : 'POST' ,
                        success : function (data) {
                            console.log(data);
                            var box = data.next_redirect_pc_url;
                            window.open(box);
                        },
                        error : function (error) {
                            console.log(error);
                        }
                    });
                },
                "아니요": function() {
                    $(this).dialog('close');
                    // keepPay = false;
                }
            }
        });
        // }
        // else{
        //
        // }

    }
});
