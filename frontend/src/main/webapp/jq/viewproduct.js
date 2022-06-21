$(function(){
    let queryString = location.search.substring(1);
    console.log(queryString);
    $.ajax({ // 비동기처리
        url: '/backend/viewproduct',
        method: 'get',
        data: queryString,
        success: function(jsonObj){
            console.log('status : ' + jsonObj.status);
            console.log('jsonObj.p.productNo : ' + jsonObj.p.productNo);
            if(jsonObj.status == 1) {
                let product_no = jsonObj.p.productNo;
                let product_name = jsonObj.p.productName;
                let product_price = jsonObj.p.productPrice;
                let product_info = jsonObj.p.productInfo;
                let product_mfd = jsonObj.p.productMfd;
                console.log(product_no);
                $('div.viewproduct>div.detail ul>li>span.product_no').html(product_no);
                $('div.viewproduct>div.detail ul>li>span.product_name').html(product_name);
                $('div.viewproduct>div.detail ul>li>span.product_price').html(product_price);
                $('div.viewproduct>div.detail ul>li>span.product_info').html(product_info);
                $('div.viewproduct>div.detail ul>li>span.product_mfd').html(product_mfd);
                $('div.viewproduct>img').attr('src', '../images/' + product_no + '.jpg').attr('alt', product_name);
            } else {
                alert(jsonObj.message);
            }
        },
        error: function(jqXHR) {
            console.log('현위치 : ' + document.location.href);
            alert('error code : ' + jqXHR.status);
        }
    });

    //장바구니 버튼 click
    $('div.viewproduct ul>li>button.shopping_basket').click(function(){
        let $product_no = $('div.viewproduct form>ul>li>span.product_no').html();

        let $quantity = $('div.viewproduct ul>li>input[name=quantity]').val();
        $.ajax({ // 비동기처리
            url: '/backend/addcart',
            method: 'get',
            data: {"product_no":$product_no, "quantity":$quantity},
            success: function(jsonObj){
                alert($product_no + " / " + $quantity + "개가 장바구니에 추가되었습니다.");
                $('div.viewproduct>div.result').show();
            },
            error: function(jqXHR) {
                console.log('현위치 : ' + document.location.href);
                alert('error code : ' + jqXHR.status);
            }
        });
        return false;
    });
    
    $('nav>a[href="productlist.html"]').click(function(){
        location.href = 'productlist.html';
    });


    $('div.result>button.productlist').click(function(){
        console.log('접근완료');
        $('nav>a[href="productlist.html"]').trigger('click');
    });
});