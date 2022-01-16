if($('.checkForDeliveryStatus').val()=="주문접수"){
    let insertTr = "";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>주문접수</td>"
    insertTr += "</tr>";
    $('#makeTable').append(insertTr);
}
else if ($('.checkForDeliveryStatus').val() == "배송준비"){

    let insertTr = "";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>주문접수</td>"
    insertTr += "</tr>";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>배송준비</td>"
    insertTr += "</tr>";

    $('#makeTable').append(insertTr);

}
else if ($('.checkForDeliveryStatus').val() == "배송중"){

    let insertTr = "";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>주문접수</td>"
    insertTr += "</tr>";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>배송준비</td>"
    insertTr += "</tr>";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>배송중</td>"
    insertTr += "</tr>";

    $('#makeTable').append(insertTr);
}
else if ($('.checkForDeliveryStatus').val() == "배송완료"){

    let insertTr = "";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>주문접수</td>"
    insertTr += "</tr>";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>배송준비</td>"
    insertTr += "</tr>";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>배송중</td>"
    insertTr += "</tr>";
    insertTr += "<tr>";
    insertTr += "<td class='td03'>배송완료</td>"
    insertTr += "</tr>";

    $('#makeTable').append(insertTr);
}

$('.back').on('click' , function () {

    location.href = '/orderList';

});

// window.onbeforeunload = function (){
//
//     $.ajax({
//
//         url : '/sendDeliveryStatus' ,
//         type : 'post',
//         data : {
//             deliveryStatus : "deliveryStatus" ,
//             "productId" : $('.productIdForReload').val()
//         },
//         success : function (data) {
//             $('.checkForDeliveryStatus').val(data.deliveryStatus);
//             console.log("성공");
//         },
//         error : function () {
//             console.log("실패");
//         }
//
//     });
//
// }