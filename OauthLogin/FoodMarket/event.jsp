<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
    <title>행사목록</title>
    <link href="eventList.css" rel="stylesheet"> <!--link : 웹페이지에 다른 파일 추가시키기-->
</head>
<body>
	<div id = "main">
		<div class = "pageTitle">
			<h1 class = "tit">오늘의 행사</h1>
		</div>
		<div class = "pageContents">
			<form action="#" method="post" class="eventList">
				<ul class="list">
					<oi>
						<a href= "../src/main/resources/templates/goodListForevent1.html">
							<img src="images/eventList1.jpg" alt="행사1" width="1000">
						</a> 
					</oi>
				</ul>
				<ul class="list2">
					<oi>
						<a href= "#">
							<img src="images/eventList2.jpg" alt="행사2" width="1000">
						</a>
					</oi>
				</ul>
				<ul class="list3">
					<oi>
						<a href= "#">
							<img src="images/eventList3.jpg" alt="행사3" width="1000"> 
						</a>
					</oi>
				</ul>
				<ul class="list4">
					<oi>
						<a href= "#">
							<img src="images/eventList4.jpg" alt="행사4" width="1000"> 
						</a>
					</oi>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>
