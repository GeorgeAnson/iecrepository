<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<c:choose>
		<c:when test="${user.userTypeId==4}">
			<div class="page-header">
				<h1 class="pull-left">
					<i class="icon-desktop"></i> <span>Home</span>
				</h1>
			</div>

			<div class="row-fluid">
				<div class="span12 box" style="margin-bottom: 0" id="visaAlert">
					<div class="alert alert-warning">
						<h4>
							<i class="icon-exclamation-sign"></i>&emsp;Warning.
						</h4>
						
						&emsp;&emsp; Your visa will expire soon. <b><span id="timeLine"></span></b> days. (<span id="timelinedate">${invalidTime}</span>)
					</div>
				</div>
			</div>

			<div class="row-fluid">
				<div class="span6 box" style="margin-bottom: 0">
					<div class="box-header box-header-small blue-background">
						<div class="title" style="font-size: 20px;">
							<b>School</b>
						</div>
						<div class="actions">
							<a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>
						</div>
					</div>
					<div class="box-content">
						<div class="carousel slide carousel-without-caption"
							id="myCarousel" style="margin-bottom: 0;">
							<ol class="carousel-indicators">
								<li class="active" data-slide-to="0" data-target="#myCarousel"></li>
								<li data-slide-to="1" data-target="#myCarousel"></li>
								<li data-slide-to="2" data-target="#myCarousel"></li>
							</ol>
							<!-- Carousel items -->
							<div class="carousel-inner">
								<div class="active item">
									<img alt="" style="width: 100%; max-height: 201px;"
										src="${pageContext.request.contextPath}/assets/images/slide1.jpg" />
								</div>
								<div class="item">
									<img alt="" style="width: 100%; max-height: 201px;"
										src="${pageContext.request.contextPath}/assets/images/slide2.jpg" />
								</div>
								<div class="item">
									<img alt="" style="width: 100%; max-height: 201px;"
										src="${pageContext.request.contextPath}/assets/images/slide3.jpg" />
								</div>
							</div>
							<!-- Carousel nav -->
							<a class="carousel-control left" data-slide="prev"
								href="#myCarousel">‹</a> <a class="carousel-control right"
								data-slide="next" href="#myCarousel">›</a>
						</div>
					</div>
				</div>

				<div class="span6 box">
					<div class="box-header box-header-small blue-background">
						<div class="title" style="font-size: 20px;">
							<b>College Introduction</b>
						</div>
						<div class="actions">
							<a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>
						</div>
					</div>
					<div class="box-content">
						<div style="text-indent: 2em; font-size: 16px;" class="scrollable"
							data-scrollable-height="200" data-scrollable-start="top">
							${introduce}
						</div>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="page-header">
				<h1 class="pull-left">
					<i class="icon-desktop"></i> <span>首页</span>
				</h1>
			</div>

			<div class="row-fluid">
				<div class="span6 box" style="margin-bottom: 0">
					<div class="box-header box-header-small blue-background">
						<div class="title" style="font-size: 20px;">
							<b>校园</b>
						</div>
						<div class="actions">
							<a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>
						</div>
					</div>
					<div class="box-content">
						<div class="carousel slide carousel-without-caption"
							id="myCarousel" style="margin-bottom: 0;">
							<ol class="carousel-indicators">
								<li class="active" data-slide-to="0" data-target="#myCarousel"></li>
								<li data-slide-to="1" data-target="#myCarousel"></li>
								<li data-slide-to="2" data-target="#myCarousel"></li>
							</ol>
							<!-- Carousel items -->
							<div class="carousel-inner">
								<div class="active item">
									<img alt="" style="width: 100%; max-height: 201px;"
										src="${pageContext.request.contextPath}/assets/images/slide1.jpg" />
								</div>
								<div class="item">
									<img alt="" style="width: 100%; max-height: 201px;"
										src="${pageContext.request.contextPath}/assets/images/slide2.jpg" />
								</div>
								<div class="item">
									<img alt="" style="width: 100%; max-height: 201px;"
										src="${pageContext.request.contextPath}/assets/images/slide3.jpg" />
								</div>
							</div>
							<!-- Carousel nav -->
							<a class="carousel-control left" data-slide="prev"
								href="#myCarousel">‹</a> <a class="carousel-control right"
								data-slide="next" href="#myCarousel">›</a>
						</div>
					</div>
				</div>

				<div class="span6 box">
					<div class="box-header box-header-small blue-background">
						<div class="title" style="font-size: 20px;">
							<b>学院介绍</b>
						</div>
						<div class="actions">
							<a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>
						</div>
					</div>
					<div class="box-content">
						<div style="text-indent: 2em; font-size: 16px;" class="scrollable"
							data-scrollable-height="200" data-scrollable-start="top">
							${introduce}
						</div>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	 <script>
      
      var dater = $('#timelinedate').html();
      $('#timeLine').html(getDays(getNowFormatDate(), dater));
  
      function getDays(strDateStart,strDateEnd){
          var strSeparator = "-"; 
          var oDate1;
          var oDate2;
          var iDays;
          oDate1= strDateStart.split(strSeparator);
          oDate2= strDateEnd.split(strSeparator);
          var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
          var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
          iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数
          return iDays ;
       }
       function getNowFormatDate() {
           var date = new Date();
           var seperator1 = "-";
           var seperator2 = ":";
           var month = date.getMonth() + 1;
           var strDate = date.getDate();
           if (month >= 1 && month <= 9) {
               month = "0" + month;
           }
           if (strDate >= 0 && strDate <= 9) {
               strDate = "0" + strDate;
           }
           var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
           return currentdate;
       }
    </script>
    <script type="text/javascript">



     document.getElementById("dayer").innerHTML = getDays("2016-12-13",getNowFormatDate());
</script>
</body>
</html>