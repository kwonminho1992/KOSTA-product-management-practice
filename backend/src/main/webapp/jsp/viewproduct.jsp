<%@page import="com.my.dto.Product"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>view product</title>
	<link rel="stylesheet" href="./css/css_layout.css"> <!-- 웹브라우저에서 실행되므로 back project를 기준으로 경로 찾기 -->
</head>
<body>
<% Product p = (Product)request.getAttribute("p"); %>
	<jsp:include page="../html/header.html"></jsp:include>	<!-- 서버에서 실행되므로 jsp파일을 기준으로 경로 찾기 -->

 	<section>
        <!--본문-->
		<article>
			<div class="viewproduct">
				<img src="/front/images/<%=p.getProductNo()%>.jpg" alt="<%=p.getProductName()%>">
				<div class="detail">
					<ul>
						<li>상품번호 : <%=p.getProductNo()%></li>
						<li>상품이름 : <%=p.getProductName()%></li>
						<li>가격 : <%=p.getProductPrice(                                                                                                                                                       )%></li>						
						<li>상품상세 : <%=p.getProductInfo()%></li>
						<li>제조일자 : <%=p.getProductMfd()%></li>
						<li>수량 : <input type="number" max="9" min="1" value="1"></li>
                		<li><button>장바구니에 넣기</button></li>
					</ul>
				</div>
			</div>
		</article>
		<article>
			<span class="article_content">본문내용2</span>
		</article>

    </section>
	<jsp:include page="../html/aside.html"></jsp:include>
	<jsp:include page="../html/footer.html"></jsp:include>
</body>
</html>