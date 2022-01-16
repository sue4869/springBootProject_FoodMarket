// 확인 ////////////////////////////
// <select name='deliveryText' className="input04" id="deliverySelect">
//   <option value='default' selected> -- 배송시 요청사항을 입력해주세요 --</option>
//   <option value='1'>경비실에 맡겨주세요.</option>
//   <option value='2'>집문앞에 놔주세요.</option>
//   <option value='3'>직접 수령하겠습니다.</option>
//   <option value="4">요청사항 직접입력하기</option>
// </select>

// 동의 모두선택 / 해제
const agreeChkAll = document.querySelector('input[name=agree_all]');
agreeChkAll.addEventListener('change', (e) => {
    let agreeChk = document.querySelectorAll('input[name=agree]');
    for(let i = 0; i < agreeChk.length; i++){
        agreeChk[i].checked = e.target.checked;
    }
});

$(function () {

    var FindaddressFlag = "false";

    console.log("?????? : " + $('#deliverySelect option:selected').val());
    // if($('#deliverySelect').val() == 4){
    //   console.log("요청사항 선택");
    // }

    // <input type="hidden" className="input06" id="requestInput" placeholder="요청사항을 입력해주세요.">

    // selectBox 체크
    var selectCheck = false;
    var inputNameForm = true;
    var inputPhoneForm = true;
    var inputAddressForm = true;
    var inputDetailAddressForm = true;
    var requestInput = true;
    var checkboxCheck = true;

    // var text = "";
    // 실시간으로 값 대입하기

    // var requestInputCheck = true;
    $('#requestInput').on("input" , function () {
        // requestInputCheck = true;
        // console.log("requestInput change!!!");
        //
        // var text = $(this).val();
        // console.log("text" , text);
        // alert("hi");
        if($("#requestInput").val() != ""){
            requestInput = true;
        }else{
            requestInput = false;
        }
        // $("#requestInput").val(text);

        // $("#inputNameForm").val(text);
    });

    $("#deliverySelect").on("change", function(){
        //selected value
        console.log($(this).val());
        // $(this).val();
        if($(this).val() == 4){
            selectCheck = true;

            $("#requestInput").attr("type" ,"text");
            if($('#requestInput').val() == ""){
              // alert("요청사항을 입력해주세요");
              requestInput = false;
            }
            console.log("4선택, selectChekck : " + selectCheck);
        }else if($(this).val() == "default"){
            selectCheck = false;
            requestInput = true;
            console.log("default 선택 , selectChekck : " + selectCheck);
        }
        else{
            $("#requestInput").attr("type" ,"hidden");
            selectCheck = true;
            requestInput = true;
            console.log("1,2 ,3 선택 , selectChekck : " + selectCheck)
        }
        // $("option:selected", this).attr("value");
        // //selected option element
        // $("option:selected", this);
        // $("option:selected", this).text();
        // $(this).find("option:selected").text();
    });

    // 결제하기 누르면
    $('.pay').click(function () {
        // var keepPay = false;


        // var selectCheckText = true;

        // 결제하기전 필수목록 체크

        // name
        if($('#inputNameForm').val() == ""){
            alert("이름을 입력해주세요");
            inputNameForm = false;
        }
        // phone
        if($('#inputPhoneForm').val() == ""){
            alert("핸드폰번호를 입력해주세요");
            inputPhoneForm = false;
        }
        // address
        if($('#inputAddressForm').val() == ""){
            alert("주소를 입력해주세요");
            inputAddressForm = false;
        }
        // detailAddress
        if($('#inputDetailAddressFrom').val() == "") {
            alert("상세주소를 입력해주세요");
            inputDetailAddressForm = false;
        }

        if(selectCheck == false){
            alert("배송 메모를 골라주세요")
        }
        // if($('#requestInput').val() == ""){
        //   alert("요청사항을 ")
        //   selectCheckText = false;
        // }
        // if(requestInputCheck == false){
        if(requestInput == false){
            alert("요청사항을 입력해주세요");
        }
        // }


        // checkBoxCheck
        let agreeChk = document.querySelectorAll('input[name=agree]');
        let count = 0;
        for(let i = 0  ; i < agreeChk.length; i++){
            if(agreeChk[i].checked){
                count++;
            }
        }
        if(count >= 2){
            count = 0;
            checkboxCheck = true;
        }else if(count < 2){
            alert("이용약관동의 의 필수 사항은 모두 체크되어야 합니다. ");
            checkboxCheck = false;
            count = 0;
        }

        // var inputNameForm = true;
        // var inputPhoneForm = true;
        // var inputAddressForm = true;
        // var inputDetailAddressForm = true;
        // var requestInput = true;
        // var checkboxCheck = true;

        if(inputNameForm != false && inputPhoneForm != false && inputAddressForm != false
            && inputDetailAddressForm != false && requestInput != false
            && checkboxCheck != false && selectCheck != false){

            // console.log("input[name=goodName] : " + document.querySelectorAll('input[name=goodName]').length);
            // let name = document.querySelectorAll('input[name=goodName]');
            // for(let i = 0 ; i  < name.length; i++){
            //     name[i].getAttribute('value');
            // }

            let nameList = []; let imageList = []; let priceList = [];
            let countList = []; let regDateList = []; let detailContextList = [];
            let productIdList = []; let requestMessage;

            if($('#requestInput').val() == null || $('#requestInput').val() == ""){
                 requestMessage =  $("#deliverySelect option:selected").text();
            }
            else{
                requestMessage = $('#requestInput').val();
            }

            console.log(requestMessage);
            // var productData = new Object();
            // var arrProductItem = new Array();

            let nameLength = $("input[name=goodName]").length;
            for(let i = 0; i < nameLength; i++) {
                nameList.push($("input[name=goodName]").eq(i).val());
                imageList.push($("input[name=images]").eq(i).val());
                priceList.push($("input[name=price]").eq(i).val());
                countList.push(parseInt($("input[name=count]").eq(i).val()));
                regDateList.push($("input[name=regDate]").eq(i).val());
                detailContextList.push($("input[name=detailContext]").eq(i).val());
                productIdList.push($("input[name=productIdForSend]").eq(i).val());
            }



            $('#dialog-message').dialog({
                modal: true,
                buttons: {
                    "예": function() {
                        $(this).dialog('close');
                        $.ajax({
                            url : "/kakaoPayCheck" ,
                            data : {
                                "paySum" : $('.payAmount').text(),
                                "userId" : parseInt($('.userIdForSend').val()),
                                "requestMessage" : requestMessage,
                                "productIdList" : productIdList,
                                "nameList" : nameList,
                                "imageList" : imageList,
                                "priceList" : priceList,
                                "countList" : countList,
                                "regDateList" : regDateList,
                                "detailContextList" : detailContextList
                            },
                            dataType : 'JSON',
                            type : 'POST' ,
                            success : function (data) {
                                console.log(data);
                                var box = data.next_redirect_pc_url;
                                window.open(box);

                                // for(let i = 0; i < nameLength; i++){
                                //     var sendData = {
                                //         // "id" : $("input[name=productIdForSend]").eq(i).val(),
                                //         // "price" : $("input[name=price]").eq(i).val(),
                                //         // "goodName" :  $("input[name=goodName]").eq(i).val(),
                                //         // "image" : $("input[name=images]").eq(i).val(),
                                //         // "count" : $("input[name=count]").eq(i).val(),
                                //         // "orderedProduct" : $("input[name=productIdForSend]").eq(i).val(),
                                //         // "userId" : $(".userIdForSend").val()
                                //         "userId" : parseInt($('.userIdForSend').val()),
                                //         "requestMessage" : requestMessage[i],
                                //         "goodName" : nameList[i],
                                //         "image" : imageList[i],
                                //         "price" : priceList[i],
                                //         "count" : countList[i]
                                //     };
                                //     console.log(data);
                                //
                                //     $.ajax({
                                //         url : "/api/orderProduct",
                                //         type : 'post',
                                //         dataType: 'JSON',
                                //         contentType : "application/json; charset=utf-8",
                                //         data : JSON.stringify(sendData),
                                //         success : function (){
                                //             console.log("서치를위한 상품등록 성공");
                                //         },
                                //         error : function () {
                                //             console.log("서치를위한 상픔등록 실패");
                                //         }
                                //     });
                                // }
                            },
                            error : function (error) {
                            }
                        });
                    },
                    "아니요": function() {
                        $(this).dialog('close');
                        // keepPay = false;
                    }
                }
            });

        }
        else{
            $.ajax({
                url : "/payRedirect",
                type : 'get',
                success : function () {
                    console.log("리다이렉트 성공");
                },
                error : function (error) {
                    console.log(error);
                }
            });
        }
        ///////////////////////////////////

        // console.log("keepPay" + keepPay);
    });


    $("#inputNameForm").keyup(function(){

        // 2. #out 공간에 #data의 내용이 출력된다.
        console.log("keyup!!");
        $("#inputNameForm").val($("#inputNameForm").val());

    });

    $("input:radio[name=Address]").click(function() {
        console.log($(this).val());
        // 신규 배송지 눌렀을때
        if ($(this).val() == "1") {
            // $("input:text[class=input01]").attr();
            $("input:text[id=inputNameForm]").attr("placeholder", "이름을 입력하세요");
            // $("input:text[id=inputNameForm]").attr("value", "");
            $("#inputNameForm").val('');
            $("input:text[id=inputNameForm]").attr("readonly", false);

            console.log($("input:text[class=input01]").val());
            $("input:text[id=inputPhoneForm]").attr("placeholder", "수령인 전화번호를 입력하세요");
            // $("input:text[id=inputPhoneForm]").attr("value", "");
            $("#inputPhoneForm").val('');
            $("input:text[id=inputPhoneForm]").attr("readonly", false);
            // $("input:text[class=input02]").attr("value", "수령인 전화번호를 입력하세요");

            $("input:text[id=inputAddressForm]").attr("placeholder", "주소 검색");
            // $("input:text[id=inputAddressForm]").attr("value", "");
            $("#inputAddressForm").val('');
            $("input:text[id=inputAddressForm]").attr("readonly", false);
            $("input:text[id=inputAddressForm]").removeClass('input03');
            $("input:text[id=inputAddressForm]").addClass('input031');
            // console.log($("input:text[class=input03]").removeClass('input03'));




            $("input:text[id=inputDetailAddressFrom]").attr("placeholder" ,"상세 주소 입력해주세요.");
            // $("input:text[id=inputDetailAddressFrom]").attr("value" , "");
            $("#inputDetailAddressFrom").val('');
            $("input:text[id=inputDetailAddressFrom]").attr("readonly" ,false);

            $("#deliverySelect").val('default');
            $("#requestInput").attr("type" , "hidden");
            $("#requestInput").val("");
            // $("input:text[class=input031]").attr("value", );
            FindaddressFlag = "true";
            selectCheck = false;
            requestInput = true;
        }
        // 집 눌렀을 때
        else{

            $("input:text[id=inputNameForm]").attr("placeholder" , "");
            // $("input:text[id=inputNameForm]").attr("value", $('.getNameFromModel').val());
            $("#inputNameForm").val($('.getNameFromModel').val());
            $("input:text[id=inputNameForm]").attr("readonly" , true);

            $("input:text[id=inputPhoneForm]").attr("placeholder" ,"");
            // $("input:text[id=inputPhoneForm]").attr("value" ,$('.getPhoneFromModel').val());
            $("#inputPhoneForm").val($('.getPhoneFromModel').val());
            $("input:text[id=inputPhoneForm]").attr("readonly" ,true);

            $("input:text[id=inputAddressForm]").attr("placeholder" ,"");
            // $("input:text[id=inputAddressForm]").attr("value" ,$('.getBigAddressFromModel').val());
            $("#inputAddressForm").val($('.getBigAddressFromModel').val());
            $("input:text[id=inputAddressForm]").attr("readonly" ,true);

            $("input:text[id=inputDetailAddressFrom]").attr("placeholder" ,"");
            // $("input:text[id=inputDetailAddressFrom]").attr("value" ,$('.getDetailAddressFromModel').val());
            $("#inputDetailAddressFrom").val($('.getDetailAddressFromModel').val());
            $("input:text[id=inputDetailAddressFrom]").attr("readonly" ,true);

            $("input:text[id=inputAddressForm]").addClass('input03');
            $("input:text[id=inputAddressForm]").removeClass('input031');

            $("#deliverySelect").val('default');
            $("#requestInput").attr("type" , "hidden");
            $("#requestInput").val("");
            FindaddressFlag = "false";
            selectCheck = false;
            requestInput = true;
        }
    });

    // $('.input031').on('click' , function () {
    //     new daum.Postcode({
    //         oncomplete: function (data) { //선택시 입력값 세팅
    //             document.querySelector(".input031").value = data.address; // 주소 넣기
    //             document.querySelector("input[class=input05]").focus(); //상세입력 포커싱
    //         }
    //     }).open();
    // })


        document.querySelector("#inputAddressForm").addEventListener("click", function () { //주소입력칸을 클릭하면
            //카카오 지도 발생
            if(FindaddressFlag == "true"){
                new daum.Postcode({
                    oncomplete: function (data) { //선택시 입력값 세팅
                        document.querySelector("#inputAddressForm").value = data.address; // 주소 넣기
                        document.querySelector("input[class=input05]").focus(); //상세입력 포커싱
                    }
                }).open();
            }
        });


});
