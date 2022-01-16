if($('#basicTable > tbody tr').length == 1 ){
    $('#basicTable').width('1000px');

    $('#check_all').prop("checked" , false);
    // $('#basicTable').height("30px");
}

// 마이너스 버튼 눌렀을 때
$('.btnMinusCount').on('click' , function () {

    let StrId = $(this).attr('id');
    let id = StrId.substring(8);
    let count = $('#countNum'+id).text();
    let pay = $('#pay'+id).text();
    let amount = $('#payAmount').text();
    $('#paySum').val(amount);
    // var proId = $('#productId'+id).val();

    let realpay = pay.replace(/[^\d]+/g, '');

    if($('#countNum'+id).text() <= 1){
        // $('.btnMinusCount').attr('disabled' , true);
    }
    else{
        $('#countNum'+id).text(parseInt(count)-1);
        $('#countForSend'+id).val(parseInt(count)-1);
        if($('#check'+id).is(":checked") == true){
            amount = amount - realpay;

            $('#payAmount').text(amount);
            $('#paySum').val($('#payAmount').text(amount));
        }
        console.log('minus 누름');
        $.ajax({

            url : '/countMinus',
            type : 'POST',
            data : {"count" : parseInt(count)-1,
                "userId" : $('.userId').val(),
                "productId" : $('#productId'+id).val()
            },
            success : function () {
                console.log("minus 성공");
            },
            error : function (error) {
                console.log("minus 실패");
            }

        });
    }


});


// 플러스 버튼 눌렀을 때
$('.btnPlusCount').on('click' , function () {

    let strId = $(this).attr('id');
    let id = strId.substring(7);
    let count = $('#countNum'+id).text();
    let pay = $('#pay'+id).text();
    let amount = $('#payAmount').text();
    $('#paySum').val(amount);
    // var proId = $('#productId'+id).val();

    let realpay = pay.replace(/[^\d]+/g, '');

    $('#countNum'+id).text(parseInt(count) + 1);
    $('#countForSend'+id).val(parseInt(count) + 1);
    if($('#check'+id).is(":checked") == true){
        amount = parseInt(amount) + parseInt(realpay);

        $('#payAmount').text(amount);
        $('#paySum').val(amount);
    }

    $.ajax({

        url : '/countPlus',
        type : 'POST',
        data : {"count" : parseInt(count)+1,
            "userId" : $('.userId').val(),
            "productId" : $('#productId'+id).val()
        },
        success : function () {
            console.log("minus 성공");
        },
        error : function (error) {
            console.log("minus 실패");
        }

    });
});

// 상품 모두 선택 / 해제
const agreeChkAll = document.querySelector('input[name=check_all]');
agreeChkAll.addEventListener('change', (e) => {
    let agreeChk = document.querySelectorAll('input[name=check]');
    let flag = true;
    let productIds = [];
    for(let i = 0; i < agreeChk.length; i++) {

        let strId = agreeChk[i].getAttribute('id');
        let id = strId.substring(5);

        let btnCount = $('#countNum' + id).text();


        let pay = $('#pay' + id).text();
        let amount = $('#payAmount').text();
        $('#paySum').val(amount);
        // var proId = $('#productId'+id).val();

        let realpay = pay.replace(/[^\d]+/g, '');

        console.log($('#check' + id).is(":checked"));

        // if($('#check'+id).is(":checked")){ // 개별개별 체크가 됬을때
        console.log("$('#check_all').is : " + $('#check_all').is(":checked"));
        console.log("$('#check' + id).is : " + $('#check' + id).is(":checked"));

        if ($('#check_all').is(":checked") == true && $('#check' + id).is(":checked") == true) { // 전체체크가 안되잇엇고, 개개인 체크가 체크되잇을때

        }
        else if(($('#check_all').is(":checked") == false && $('#check' + id).is(":checked") == false)){ // 전체체크가 체크되잇엇고, 개개인 체크가 안되잇을때

        }
        // }
        // else{ // 개별개별 체크가 안됫을때

        else if ($('#check_all').is(":checked") == true && $('#check' + id).is(":checked") == false) { // 전체 체크가 안되잇엇고, 개별체크가 안되어엇을때

            amount = parseInt(amount) + (parseInt(realpay) * parseInt(btnCount));

            $('#payAmount').text(amount);
            $('#paySum').val(amount);
            flag = true;
            productIds.push(parseInt(id));
            $('#check' + id).attr('checked', true);
            $('#checkForSend'+id).val(1);
            $('#flagNum' + id).val(true);
        }
        else if ($('#check_all').is(":checked") == false && $('#check' + id).is(":checked") == true) { // 전체 체크가 되어잇엇고, 개별체크가 되어잇을때
            amount = parseInt(amount) - (parseInt(realpay) * parseInt(btnCount));

            $('#check' + id).attr('checked', false);
            $('#checkForSend'+id).val(0);
            $('#payAmount').text(amount);
            $('#paySum').val(amount);
            flag = false;
            productIds.push(parseInt(id));
            $('#flagNum' + id).val(false);
        }

        agreeChk[i].checked = e.target.checked;

        // }
    }
    $.ajax({
        url : '/basketFlags',
        type : 'post',
        data : {"flag" : flag,
            "userId" : $('.userId').val(),
            "productIds" : productIds
        } ,
        success : function () {
            console.log("한번에 체크박스후 총금액 전송성공");
        },
        error : function () {
            console.log("한번에 체크박스후 총금액 전송실패");
        }
    });
});


// check box 하나씩 눌렀을때
$('.personalCheck').on('change' , function () {

    let strId = $(this).attr('id');
    let id = strId.substring(5);

    let btnCount = $('#countNum'+id).text();


    let pay = $('#pay'+id).text();
    let amount = $('#payAmount').text();
    $('#paySum').val(amount);
    // var proId = $('#productId'+id).val();

    let realpay = pay.replace(/[^\d]+/g, '');

    console.log($('#check'+id).is(":checked"));

    if($('#check'+id).is(":checked")){
        amount = parseInt(amount) + (parseInt(realpay)*parseInt(btnCount));

        $('#payAmount').text(amount);
        $('#paySum').val(amount);
        $('#check'+id).attr('checked' , true);
        $('#checkForSend'+id).val(1);
        $('#flagNum'+id).val(true);
        let flag = true;
        $.ajax({
            url : '/basketFlag',
            type : 'post',
            data : {"flag" : flag,
                "userId" : $('.userId').val(),
                "productId" : $('#productId'+id).val()
            } ,
            success : function () {
                console.log("하나씩 체크박스후 총금액 전송성공");
            },
            error : function () {
                console.log("하나씩 체크박스후 총금액 전송실패");
            }
        });
    }else{
        $('#flagNum'+id).val(false);
        amount = parseInt(amount) - (parseInt(realpay)*parseInt(btnCount));
        $('#check'+id).attr('checked' , false);
        $('#checkForSend'+id).val(0);
        $('#payAmount').text(amount);
        $('#paySum').val(amount);
        let flag = false;
        $.ajax({
            url : '/basketFlag',
            type : 'post',
            data : {"flag" : flag,
                "userId" : $('.userId').val(),
                "productId" : $('#productId'+id).val()
            } ,
            success : function () {
                console.log("하나씩 체크박스후 총금액 전송성공");
            },
            error : function () {
                console.log("하나씩 체크박스후 총금액 전송실패");
            }
        });
    }
})

// 전부 삭제
$('.allDelete').on('click' , function () {

    var removeCheck = confirm("정말 삭제하시겠습니까?")

    if(removeCheck == true) {
        // let checkboxes = document.querySelectorAll('input[name=check]');
        let checkArray = [];
        let id = 0;
        // for (let i = 0; i < checkboxes.length; i++) {
        //     if (checkboxes[i].checked) {
        //         let str = checkboxes[i].getAttribute('id');
        //         id = str.substring(5);
        //         checkArray.push(parseInt(id));
        //     }
        // }

        let flagInputs = document.querySelectorAll('input[name=checkNumFlag]');

        for( let i = 0 ; i < flagInputs.length; i++){
            let str = flagInputs[i].getAttribute('id');
            id = str.substring(7);
            if($('#flagNum'+id).val() == "true"){
                checkArray.push(parseInt(id));
            }
        }


        $.ajax({
            url: "/deleteAll",
            type: 'post',
            data: {
                "checkedList": checkArray,
                "userId": $('.userId').val()
            },
            success: function () {
                console.log("박스선택된 상품들 삭제 성공!");

                let agreeChk = document.querySelectorAll('input[name=check]');
                for(let i = 0; i < agreeChk.length; i++){

                    let strId =  agreeChk[i].getAttribute('id');
                    let id = strId.substring(5);

                    let btnCount = $('#countNum'+id).text();


                    let pay = $('#pay'+id).text();
                    let amount = $('#payAmount').text();
                    $('#paySum').val(amount);
                    // var proId = $('#productId'+id).val();

                    let realpay = pay.replace(/[^\d]+/g, '');
                    console.log($('#check'+id).is(":checked"));

                    if($('#check'+id).is(":checked")){
                        amount = parseInt(amount) - (parseInt(realpay)*parseInt(btnCount));

                        $('#payAmount').text(amount);
                        $('#paySum').val(amount);
                    }
                }

                for (let i = 0; i < checkArray.length; i++) {
                    $('#tr' + checkArray[i]).remove();

                }
                if ($('#basicTable > tbody tr').length == 1) {
                    $('#basicTable').width('1000px');
                    $('#check_all').prop("checked" , false);
                    // $('#basicTable').height("30px");
                }


                $('.spanTitle').text(parseInt($('.spanTitle').text()) -checkArray.length);
                location.reload(true);

            },
            error: function () {
                console.log("전체 삭제 실패");
            }
        });
    }
});



// 단일 삭제
$('.remove').on('click' , function () {

    var removeCheck = confirm("정말 삭제하시겠습니까?")

    if(removeCheck == true){
        var strId = $(this).attr('id');
        var id = strId.substring(6);

        $.ajax({
            url : '/removeOne',
            type : 'post',
            data : {"productId" : $('#productId'+id).val(),
                "userId" : $('.userId').val()
            },
            success : function () {
                console.log("삭제 성공!");
                $('#tr'+id).remove();
                if($('#basicTable > tbody tr').length == 1 ){
                    $('#basicTable').width('1000px');
                    $('#check_all').prop("checked" , false);
                    // $('#basicTable').height("30px");
                }
                $('.spanTitle').text(parseInt($('.spanTitle').text()) -1);
                location.reload(true);
            },
            error : function (error) {
                console.log("삭제 실패~");
            }
        });
    }

});


// 결제하기 버튼 눌럿을때
$('.payment').on('click' , function () {


    // 결제할 금액이 0원이면 submit 막기
    if(parseInt($("#payAmount").text()) == 0 ){
        alert("결제할 상품이없습니다.");
        $("#payForm").attr("onsubmit" , "event.preventDefault();")
    }else{
        // $.ajax({
        //     url : "/moveToPayment",
        //     type : "post",
        //     data : {"data" : "1"} ,
        //     success : function () {
        //         console.log("결제하러간당?");
        //         location.replace("/paymentPage");
        //     },
        //     error : function () {
        //         console.log("결제하러가지마~");
        //     }
        // });
        // location.href='/paymentPage';
    }
});