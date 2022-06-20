$(function(){
    $.ajax({ // 비동기처리
        url:'/backend/productlist',
        success: function(jsonObj){
            //jsonObj = array
            //div.td 객체를 DOM tree에서 찾아서 복사
            //복사한 div.td의 하위객체 중 img 객체의 href 속성의 값 = productNo.jpg, alt 속성의 값 = productName
            let $tdObj = $('div.td');
            $(jsonObj).each(function(index, item) {
                console.log(item.productNo + ":" + item.productName + ":" + item.productPrice);
                let $copyObj = $tdObj.clone(); // tdObj의 복제본 만들기
                let $imgObj = $copyObj.find("img");
                $imgObj.attr("src", "../images/" + item.productNo + ".jpg");
                $imgObj.attr("alt", item.productName);
                $copyObj.find("li.product_name").html(item.productName);
                $('div.table').append($copyObj); //div.table의 막내 자식으로 $copyObj를 추가
            });
            $tdObj.hide();

        },
        error: function(jqXHR) {
            alert('error code : ' + jqXHR.status);
        }
    });

    //div.td 객체 찾기
    let $tableObj = $('div.table');
    $tableObj.on('click', 'div.td', function(){ // on() : DOM tree에 객체가 추가될때 실행되도록 하는 method
        let src = $(this).find('img').attr('src');
        let arr = src.split('/');
        let productNo = arr[arr.length - 1].split('.')[0];
        //form태그의 value를 상품번호로 설정
        $('form[name=product_no]').attr('value', productNo);
        console.log('src : ' + src + ' / arr : ' + arr + ' / productNo : ' + productNo);
        let url = "viewproduct.html?product_no=" + productNo;
        $('div.productlist').load(url, function(responseText, statusText, xhr){
            if (statusText != 'success') {
                if(xhr.status == 404) {
					let msg = title + ' 자원을 찾을 수 없습니다.';
					alert(msg);
				}
            }
        });
    });
    
});