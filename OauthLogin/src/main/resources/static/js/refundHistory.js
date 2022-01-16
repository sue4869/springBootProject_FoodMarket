let listSize = $(".listSize").val();

for(let i = 1 ; i <= parseInt(listSize); i++){
    let pay = $('#cancelProductPrice'+String(i)).val();
    let realpay = pay.replace(/[^\d]+/g, '');
    let amount = (parseInt($('#cancelProductCount'+String(i)).val())*parseInt(realpay))-1000;
    console.log(parseInt($('#cancelProductCount'+String(i)).val())*parseInt(realpay));
    console.log($('#cancelProductCount'+String(i)).val());
    console.log($('#cancelProductCount'+i).val());
    console.log($('#cancelProductPrice'+String(i)).val());
    console.log($('#cancelProductPrice'+i).val());
    console.log(String(i));
    console.log(parseInt($('#cancelProductCount'+String(i)).val()));
    console.log(parseInt($('#cancelProductPrice'+String(i)).val()));
    console.log(amount);
    $('#refundAmount'+String(i)).text(amount);
}

$('.cancleDetail').on('click', function () {
    let str = $(this).attr('id');
    let id = str.substring(12);


    let pay = $('#price'+id).text();
    let realpay = pay.replace(/[^\d]+/g, '');
    // let sendId =  $('#cancelProductId'+id).val();

    console.log($('#cancelProductId'+id).val());
    console.log("+$('#refundAmount'+id).text() :" + $('#refundAmount'+id).text());

    location.href = "/refundHistoryDetail?cancelDate="+ $('#cancelDate'+id).text()+
        "&orderDate="+$('#orderDate'+id).text() +
        "&sendNum="+$('#sendNum'+id).text()+
        "&refundAmount="+$('#refundAmount'+id).text()+
        "&goodName="+$('#goodName'+id).text()+
        "&count="+$('#count'+id).text()+
        "&price="+realpay+
        "&cancelProductId="+$('#cancelProductId'+id).val()

    // $.ajax({
    //     url : '/cancelHistoryDetail/'+sendId,
    //     type : 'get',
    //     data : {
    //         "cancelDate" : $('#cancelDate'+id).text(),
    //         "orderDate" : $('#orderDate'+id).text(),
    //         "sendNum" : $('#sendNum'+id).text(),
    //         "refundAmount" : $('#refundAmount'+id).text(),
    //         "goodName" : $('#goodName'+id).text(),
    //         "count" : $('#count'+id).text(),
    //         "price" : realpay,
    //         "cancelProductId" : $('#cancelProductId'+id).val(),
    //         "refundAmount" : $('#refundAmount'+id).text()
    //     },
    //     success : function () {
    //         console.log("상세페이지로 이동 성공");
    //     },
    //     error : function () {
    //         console.log("상세페이지로 이동 실패");
    //     }
    // });
});