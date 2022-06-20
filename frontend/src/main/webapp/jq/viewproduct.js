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
                $('div.viewproduct>div.detail>ul>li>span.producet_no').html(product_no);
                $('div.viewproduct>div.detail>ul>li>span.producet_name').html(product_name);
                $('div.viewproduct>div.detail>ul>li>span.producet_price').html(product_price);
                $('div.viewproduct>div.detail>ul>li>span.producet_info').html(product_info);
                $('div.viewproduct>div.detail>ul>li>span.producet_mfd').html(product_mfd);
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
});