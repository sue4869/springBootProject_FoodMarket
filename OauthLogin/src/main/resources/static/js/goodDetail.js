$(function Pageload() { // 페이지 로드 되었을때

    ////////////////////메뉴바//////////////////////////
    // $(document).ready(function(){
    //
    //     $(".submenu").hide();
    //
    //     $(".catagory").mouseover(function(){
    //         $(".submenu").slideDown();
    //     });
    //
    //     $(".catagory").mouseleave(function(){
    //         $(".submenu").hide('slow');
    //     });
    //
    //     $(".search").hide();
    //
    //     $(".searchIcon").mouseover(function(){
    //         $(".search").slideDown();
    //     });
    // });

    ///////////////////사고자하는 수량 증가, 감소///////////////////////////
    // 처음에 저장해놓아야 한다. 클릭할때마다 가져오면 안된다
    var price = $(".goodPrice").text();
    var totalprice  = price.replace(",",""); // 천단위마다 , 없애기

    //더하기
    $(".addbutton").on("click", function () { // 언제 그 코드를 실행할지의 시점을 정해줌: function
        var max = Number($(".totalamount").val()); //총 재고수량
        var num = $(".numberarea").val();
        var plusNum = Number(num) + 1;

        var resultprice = plusNum * totalprice;
        // console.log(resultpreice);

        if(plusNum >= max) {
            $(".numberarea").val(num);
        }else {
            $(".numberarea").val(plusNum);
            $(".goodPrice").text(resultprice.toLocaleString());
        }
    });

    //빼기
    $(".minusbutton").click( function () {
        var num = $(".numberarea").val();
        var minusNum = Number(num) - 1;
        var resultprice = minusNum * totalprice;

        if(minusNum <= 0) {
            $(".numberarea").val(num);
        }else {
            $(".numberarea").val(minusNum);
            $(".goodPrice").text(resultprice.toLocaleString());
        }
    });


    ///////////////////////후기 펼쳐보기 + 조회///////////////////////////

    $(".inner_review").hide()
    $(".reviewtable_usercontent").click(function (){
        console.log("들어오나");
        var reviewWap = $(this).attr("id");
        var reviewWapId = reviewWap.substring(11);
        var close = $(this).attr("value");
        var nowclick = Number($("#reviewclicknumber"+reviewWapId).text());
        var plusclick = nowclick + 1 ;

        if(close == "true") {
            $("#innerreview"+reviewWapId).show();
            $(this).attr("value", "false");
        } else if(close == "false") { //밑에 내용이 펼쳐져있을때
            $("#innerreview"+reviewWapId).hide();
            $(this).attr("value", "true");
        }
        $.ajax({
            type : "Post",
            url : "/reviewClick",
            data : {
                "close" : close,//211031
                "reviewId" : reviewWapId,
                reviewCountNumber : "reviewCountNumber"
            },
            success: function (data) {
                if(data.reviewCountNumber == true) {
                    console.log("true");

                    $("#reviewclicknumber"+reviewWapId).text(plusclick);
                } else if(data.reviewCountNumber == false) {
                    console.log("false"); //211031
                    $("#reviewclicknumber"+reviewWapId).text(nowclick);
                }
            },
            error : function () {
                console.log("실패");
            }
        })


    });

    ////////////////////////////후기쓰기///////////////////////////


    // $(".reviewWriting").on("click",function (){
    //
    //     $.ajax({
    //         type: "Post",
    //         url: "/review",
    //         data : {
    //             movePageReview: "movePageReview"
    //         },
    //         success : function (data) {
    //             if(data.movePageReview == "/login") {
    //                 alert("로그인이 필요합니다.");
    //                 location.href=data.movePageReview;
    //             } else {
    //                 // alert(data.movePageReview);
    //                 location.href=data.movePageReview;
    //             }
    //         },
    //         error : function () {
    //             console.log("실패");
    //         }
    //     })
    // });



    /////////////////////////후기 정렬////////////////////////////
    $(".sortcontents").change(function (){
        var sort = ""
        var goodId =  $('.goodDetail').attr("id");
        console.log(goodId);
        if($(this).val() == 1) { // 최근 등록순
            location.href = "/goodDetail/1/"+goodId+"?page=1#good_review";
            // $(".reviewWrap_display_regDate_none").attr("class","reviewWrap_display_regDate");
            // $(".reviewWrap_display_goodnumber").attr("class","reviewWrap_display_goodnumber_none");
            // $(".reviewWrap_display_clicknumber").attr("class","reviewWrap_display_clicknumber_none");



        } else if($(this).val() == 2) { //좋아요 많은 순
            location.href = "/goodDetail/2/"+goodId+"?page=1#good_review";
            // $(".reviewWrap_display_regDate").attr("class","reviewWrap_display_regDate_none");
            // $(".reviewWrap_display_goodnumber_none").attr("class","reviewWrap_display_goodnumber");
            // $(".reviewWrap_display_clicknumber").attr("class","reviewWrap_display_clicknumber_none");


        } else if($(this).val() == 3) { //조회 많은 순
            location.href = "/goodDetail/3/"+goodId+"?page=1#good_review";
            // $(".reviewWrap_display_regDate").attr("class","reviewWrap_display_regDate_none");
            // $(".reviewWrap_display_goodnumber").attr("class","reviewWrap_display_goodnumber_none");
            // $(".reviewWrap_display_clicknumber_none").attr("class","reviewWrap_display_clicknumber");


        }

    });

});

// 장바구니를 눌렀을때
$('.gobasket').on('click' , function () {

    console.log("$('.goodIdForAjax').val() : " + $('.goodIdForAjax').val());
    console.log("$('.userIdForAjax').val() : " + $('.userIdForAjax').val());
    console.log("$('.numberarea').val() : " + $('.numberarea').val());
    console.log("$('#logined').text() : " + $('#logined').text());
    // 로그인 안햇을 때
    if($('#unlogined').text() == "로그인"){
        // dialog 창 가져오기
        $('#dialog-message').dialog({
            modal: true,
            buttons: {
                // 예 눌렀을 때
                "예": function() {
                    $(this).dialog('close');
                    location.href="/loginForm02";
                },
                // 아니요 눌렀을 때
                "아니요": function() {
                    $(this).dialog('close');
                    // keepPay = false;
                }
            }
        });
    }
    else if($('#logined').text() != ""){
        $.ajax({

            url : '/basketProductForDetail',
            type : 'post',
            data : {
                "goodId" : $('.goodIdForAjax').val(),
                "userId" : $('.userIdForAjax').val(),
                "count" : $('.numberarea').val(),
                message : "message"
            },
            success : function (result) {
                console.log("상품 장바구니에 담기 성공");
                // alert(result.message);
                if(result.message == "장바구니에 상품이 담겼습니다!"){
                    toastr.options = {
                        "closeButton": true, // 닫힘 버튼 보이게
                        "debug": false, // 콘솔창에 토스트 관련 메시지를 띄울 것인가?
                        "newestOnTop": false, // "새로운 팝업창을 띄울 때 위로 뜰지 말지 , 위로 true , 아래로 false"
                        "progressBar" : true, // 닫히기 까지의 프로그레스바 표시( true)
                        "positionClass" : "toast-bottom-full-width", // 팝업창 위치
                        /*
                        Top Right
                        Bottom Right
                        Bottom Left
                        Top Left
                        Top Full Width
                        Bottom Full Width
                        Top Center
                        Bottom Center
                         */
                        "preventDuplicates": true, // 중복 실행 방지, false일 경우 여러번 실행 가능 , true 면 하나만 띄운다(없어지면 다시 띄우기 가능)              }
                        "showDuration": 500, // 등장에 걸리는 시간. 공식 홈에서 문자로 되어있지만 숫자로 해야 먹힌다.
                        "hideDuration": 1000, // 사라질 때 걸리는 시간, 공식 홈에서 문자로 되어있지만 숫자로 해야 먹힌다.
                        "timeOut": 5000, // 자동으로 토스트가 사라지는 시간
                        "extendedTimeOut": 1000, // 토스트에 호버링 했다 땠을 때 사라지는 시간(마우스 올렸다가 땟을 때)
                        "showEasing": "swing", // 보일 때 애니메이션, siwng(끝부분에 살짝 드려짐) / linear ( 일정한 속도)
                        "hideEasing": "linear", // 사라질 때 애니메이션 swing, linear
                        "showMethod": "fadeIn", // 보일 때 효고 종류 show(그냥 보이게) , fadeIn( 천천히 해당위치에서 등장) , slideDown(위에서 아래로 펼치기)
                        "hideMethod": "fadeOut"  // 사라질 때 효과 종류 hide , fadeOut(천천히 사라짐) , slideUp(아래에서 위로 사라짐)
                    },
                        toastr.success("<div class='divToast01'><span>장바구니에 상품이 담겼습니다</span></div><div class='divToast02'><a href='/basket' class='linkForBasket'>장바구니로 이동</a></div>");
                }
                else{
                    toastr.options = {
                        "closeButton": true, // 닫힘 버튼 보이게
                        "debug": false, // 콘솔창에 토스트 관련 메시지를 띄울 것인가?
                        "newestOnTop": false, // "새로운 팝업창을 띄울 때 위로 뜰지 말지 , 위로 true , 아래로 false"
                        "progressBar" : true, // 닫히기 까지의 프로그레스바 표시( true)
                        "positionClass" : "toast-bottom-full-width", // 팝업창 위치
                        /*
                        Top Right
                        Bottom Right
                        Bottom Left
                        Top Left
                        Top Full Width
                        Bottom Full Width
                        Top Center
                        Bottom Center
                         */
                        "preventDuplicates": true, // 중복 실행 방지, false일 경우 여러번 실행 가능 , true 면 하나만 띄운다(없어지면 다시 띄우기 가능)              }
                        "showDuration": 500, // 등장에 걸리는 시간. 공식 홈에서 문자로 되어있지만 숫자로 해야 먹힌다.
                        "hideDuration": 1000, // 사라질 때 걸리는 시간, 공식 홈에서 문자로 되어있지만 숫자로 해야 먹힌다.
                        "timeOut": 5000, // 자동으로 토스트가 사라지는 시간
                        "extendedTimeOut": 1000, // 토스트에 호버링 했다 땠을 때 사라지는 시간(마우스 올렸다가 땟을 때)
                        "showEasing": "swing", // 보일 때 애니메이션, siwng(끝부분에 살짝 드려짐) / linear ( 일정한 속도)
                        "hideEasing": "linear", // 사라질 때 애니메이션 swing, linear
                        "showMethod": "fadeIn", // 보일 때 효고 종류 show(그냥 보이게) , fadeIn( 천천히 해당위치에서 등장) , slideDown(위에서 아래로 펼치기)
                        "hideMethod": "fadeOut"  // 사라질 때 효과 종류 hide , fadeOut(천천히 사라짐) , slideUp(아래에서 위로 사라짐)
                    },
                        toastr.success("<div class='divToast01'><span>이미 장바구니에 있는 상품입니다.</span></div><div class='divToast02'><a href='/basket' class='linkForBasket'>장바구니로 이동</a></div>");
                }

            },
            error : function () {
                console.log("상품 장바구니에 담기 실패");
            }

        });
    }


});

// 1. 결제하기 눌렀을때 form 을 막고
// 2. 로그인체크먼저하고 로그인햇으면 결제하러가기 보여주기 아니면 로그인창
// 3. 결제하러가기 '예' 누르면 결제창 가기 '아니면' 취소
// form submit 누르면 [결제하기 누르기]
$('#payForm').submit(function (event) {
    // let x = document.forms["myForm"]["loginCheckForm"].value;
    let x = $('#unlogined').text();
    if (x == "로그인") {
        // 자꾸 다이아로그를 안거치고 강제로 폼이넘어간다 그걸막기위해서 추가
        event.preventDefault();
        $('#dialog-message02').dialog({
            modal: true,
            buttons: {
                // 예 눌렀을 때
                "예": function() {
                    $(this).dialog('close');
                    location.href="/loginForm02";
                },
                // 아니요 눌렀을 때
                "아니요": function() {
                    $(this).dialog('close');
                }
            }
        });
    }else{
        event.preventDefault();
        $('#dialog-message03').dialog({
            modal: true,
            buttons: {
                // 예 눌렀을 때
                "예": function() {
                    $(this).dialog('close');
                    $('#payForm').unbind(); // event.preventDefault(); 를 풀어준다
                    $('#payForm').submit(); // event 를 풀었으니 강제로 submit 시켜준다.
                },
                // 아니요 눌렀을 때
                "아니요": function() {
                    $(this).dialog('close');

                }
            }
        });
    }
});

// 리뷰쓰기 눌렀을 때
$('.reviewWriting').on('click' , function () {

    let x = $('#unlogined').text();
    if (x == "로그인") {
        // 리뷰를 쓰기 위해선 로그인이 필요합니다. 로그인창으로 이동하시겠습니까?
        $('#dialog-message04').dialog({
            modal: true,
            buttons: {
                // 예 눌렀을 때
                "예": function() {
                    $(this).dialog('close');
                    location.href="/loginForm02";
                },
                // 아니요 눌렀을 때
                "아니요": function() {
                    $(this).dialog('close');
                }
            }
        });
    }else{
        // 정말 리뷰를 작성하실건가요?
        $('#dialog-message05').dialog({
            modal: true,
            buttons: {
                // 예 눌렀을 때
                "예": function() {
                    $(this).dialog('close');

                    $.ajax({
                        url : "/userReviewCheck",
                        type : "post",
                        data : {
                            "userId" : $('.userIdForAjax').val(),
                            "productId" : $('.goodIdForAjax').val()
                        },
                        success : function (reviewCheck) {

                            if(reviewCheck == "nopass"){ // 현재 상품에대한 현재 접속중인 유저가 리뷰가 없다면
                                // ajax ㄱㄱ
                                $.ajax({
                                    url : "/userProductBuyCheck",
                                    type : "post" ,
                                    data : {
                                        "userId" : $('.userIdForAjax').val(),
                                        "productId" : $('.goodIdForAjax').val()
                                    },
                                    success : function (result) {
                                        console.log("리뷰를 쓰기위한 상품 구매체크 전송 성공");
                                        console.log(result);
                                        if(result == "pass"){
                                            // $.ajax({
                                            //     url : "/reviewWrite" ,
                                            //     type : "get",
                                            //     data : {
                                            //         "image" : $('input[name=images]').val(),
                                            //         "goodName" : $('input[name=goodName]').val(),
                                            //         "detailContext" : $('input[name=detailContext]').val(),
                                            //         "price" : $('input[name=price]').val(),
                                            //         "productId" : $('input[name=productIdForSend]').val()
                                            //     },
                                            //     success : function () {
                                            //         console.log("리뷰 페이지로 이동 성공");
                                            //     },
                                            //     error : function () {
                                            //         console.log("리뷰 페이지로 이동 실패");
                                            //     }
                                            // });

                                            var sendUrl = encodeURI($('input[name=detailContext]').val());

                                            location.href="/reviewWrite?image="+$('input[name=images]').val()+
                                                "&goodName="+$('input[name=goodName]').val()+
                                                "&detailContext="+sendUrl +
                                                "&price="+$('input[name=price]').val()+
                                                "&productId="+$('input[name=productIdForSend]').val();
                                        }else if(result == "nopass"){
                                            alert("상품을 구매하지않았거나 리뷰를 쓸수 있는 허용 기간이 지났습니다.");
                                        }else if(result == "nopassStatus"){
                                            alert("아직 배송이 완료가 안됬습니다.");
                                        }
                                    },
                                    error : function (error) {
                                        console.log("리뷰를 쓰기위한 상품 구매체크 전송 실패");
                                        console.log(error);
                                    }

                                });
                            }else{ // 현재 상품에대한 현재 접속중인 유저가 리뷰가 있다면
                                alert("해당 상품에 리뷰는 한개씩만 작성할수 있습니다.");
                            }
                        },
                        error : function (error) {
                            console.log(error);
                        }

                    })


                    // $.ajax({
                    //     url : "/userProductBuyCheck",
                    //     type : "post" ,
                    //     data : {
                    //         "userId" : $('.userIdForAjax').val(),
                    //         "productId" : $('.goodIdForAjax').val()
                    //     },
                    //     success : function (result) {
                    //         console.log("리뷰를 쓰기위한 상품 구매체크 전송 성공");
                    //         console.log(result);
                    //         if(result == "pass"){
                    //             // $.ajax({
                    //             //     url : "/reviewWrite" ,
                    //             //     type : "get",
                    //             //     data : {
                    //             //         "image" : $('input[name=images]').val(),
                    //             //         "goodName" : $('input[name=goodName]').val(),
                    //             //         "detailContext" : $('input[name=detailContext]').val(),
                    //             //         "price" : $('input[name=price]').val(),
                    //             //         "productId" : $('input[name=productIdForSend]').val()
                    //             //     },
                    //             //     success : function () {
                    //             //         console.log("리뷰 페이지로 이동 성공");
                    //             //     },
                    //             //     error : function () {
                    //             //         console.log("리뷰 페이지로 이동 실패");
                    //             //     }
                    //             // });
                    //
                    //             var sendUrl = encodeURI($('input[name=detailContext]').val());
                    //
                    //             location.href="/reviewWrite?image="+$('input[name=images]').val()+
                    //                 "&goodName="+$('input[name=goodName]').val()+
                    //                 "&detailContext="+sendUrl +
                    //                 "&price="+$('input[name=price]').val()+
                    //                 "&productId="+$('input[name=productIdForSend]').val();
                    //         }else if(result == "nopass"){
                    //             alert("상품을 구매하지않았거나 리뷰를 쓸쑤 있는 허용 기간이 지났습니다.");
                    //         }
                    //     },
                    //     error : function (error) {
                    //         console.log("리뷰를 쓰기위한 상품 구매체크 전송 실패");
                    //         console.log(error);
                    //     }
                    //
                    // })
                },
                // 아니요 눌렀을 때
                "아니요": function() {
                    $(this).dialog('close');
                    // keepPay = false;
                    return false;
                }
            }
        });
    }

});

$('.reviewLike_button').on('click', function () {

    let str = $(this).attr('id');
    let id = str.substring(10);

    let x = $('#unlogined').text();
    if (x == "로그인") {
        //     로그인이 필요합니다. 로그인창으로 이동하시겠습니까?
        $('#dialog-message06').dialog({
            modal: true,
            buttons: {
                // 예 눌렀을 때
                "예": function() {
                    $(this).dialog('close');
                    location.href="/loginForm02";
                },
                // 아니요 눌렀을 때
                "아니요": function() {
                    $(this).dialog('close');
                }
            }
        });
    }
    else{
        $.ajax({
            url : '/reviewHelp',
            type : 'post',
            data : {
                "userId" : $('.userIdForAjax').val(),
                "reviewId" : id
            },
            success : function (result) {
                console.log("review 좋아요 성공");
                if(result == "goHelp"){
                    $('#reviewGoodNumber'+id).text(parseInt($('#reviewGoodNumber'+id).text())+1);
                }
                else if(result == "alreadyHelp"){
                    alert("이미 좋아요한 리뷰입니다.");
                }
            },
            error : function () {
                console.log("review 좋아요 실패");
            }

        });
    }


});