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
						&emsp;&emsp; Your visa will expire soon. <b>41</b> days.
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
							<p>Coming soon.</p>
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
							<p>国际交流学院前身为“海外教育中心”，是学校国际交流的重要窗口和阵地。国交学院秉承“开拓、进取、创新、服务”的宗旨，自1999年即开始与澳大利亚南悉尼学院合作招收专科国际商务专业双文凭学生,向社会输送了千余名国际商务专业优秀毕业生，历年毕业生就业率保持95%以上。2011年又创新模式与美国特拉华州立大学合作招收本科会计学专业双文凭学生，使国交学院中外合作办学水平跃居浙江省前列。</p>
							<p>国际交流学院拥有一支富有先进国际教育理念、教学经验丰富的中外教学团队，中方专任教师100%具有接受国外教学和培训的经历。学院组建了由上海财经大学、首都经济贸易大学的教授、著名企业家和银行家等组成的专业教学指导委员会，指导学生实现学与用相结合。学院积极营造国际化教育交流氛围，每年由中外师生共同演绎的“海外风”文化品牌和暑期的大学生赴国外“国际文化交流”项目充分凸现学院的文化特色。学院一贯以来形成的严谨的治学态度和丰富多彩的校园文化有效地诠释了“知行合一”的校训。</p>
							<p>“以生为本，服务社会”是我们办学的宗旨。锤炼大学生核心竞争力增强毕业生就业竞争力是我们的教育目的。以“外语水平高，专业技能精”为两翼努力打造专业品牌。学生的英语四、六级通过率在全校各专业中始终处于领先位置，每年有一批学生获得省、市级各类科技创新奖，学生良好的学习风气和文明举止受到学校高度评价。</p>
							<p>近几年来，学院积极开展了与英国、法国、美国和韩国等高校的多种国际高等教育合作项目，有中美“1+2+1”、“2+2”；中英“2+2”等涉外合作教育项目以及同美国韦恩州立大学、特拉华州立大学、北亚利桑那大学、左治亚西南州立大学合作的本升硕项目，并广泛吸收世界各地留学生来学院学习，目前在校留学生100余名，来自20余个国家和地区，形成了缤纷的特色校园文化。学院还积极为全校师生出国交流与学习提供各种语言培训和涉外事务办理等服务。</p>
							<p>宁波工程学院国际交流学院热忱欢迎你！</p>
						</div>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	 <script>
      $("#visaAlert").click(function(){
        layer.msg("<b>41days</b>");
      });
    </script>
</body>
</html>