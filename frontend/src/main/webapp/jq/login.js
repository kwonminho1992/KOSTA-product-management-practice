$(function(){
	// // load 방식 (get 방식만 사용가능)
	let $form = $('form.login');
	let url = "/backend/login";
	$form.submit(function() {
		let $inputId = $('input[name=id]');
		let $inputPwd = $("input[name=pwd]");
		let inputIdValue, inputPwdValue;
		inputIdValue = $inputId.val();
		inputPwdValue = $inputPwd.val();
		let data = 'id=' + inputIdValue + '&pwd=' + inputPwdValue;
    //     $('section>article:first').load(url, data,
	//     function(responseText, statusText, xhr){
	//         if(statusText != 'success') {
	//             alert(xhr.status + ':' + xhr.statusText);
	//         } else {
    //             let jsonObj = JSON.parse(responseText); // {'status' : 1}를 json객체로 바꿈
    //             if(jsonObj.status == 1) { // 로그인 성공
			
    //             } else if(jsonObj.status == 2) { // 로그인 실패
    //                 alert('로그인 실패');
    //             }
    //         }
	//     });

		$.ajax({
			url: url,
			method: "post",
			data: data,
//			success: function(responseText) {
//				let jsonObj = JSON.parse(responseText); // {'status' : 1}를 json객체로 바꿈
			success: function(jsonObj) {
				if (jsonObj.status == 1) { // 로그인 성공
					location.href = '';
				} else { // 로그인 실패
					alert('로그인 실패');
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				errorThrown = "로그인에 실패하였습니다.";
				alert(errorThrown + " 사유 : " + jqXHR.status);
			}
		});
		return false; // submit 기본처리 방지 꼭 해줄것
	});
});