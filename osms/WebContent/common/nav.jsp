<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div id="main-nav-bg"></div>
	<c:choose>
		<c:when test="${user.userTypeId==4}">
			<nav class="" id="main-nav">
			<div class="navigation">
				<ul class="nav nav-stacked">
					<li class="active"><a
						href="${pageContext.request.contextPath}/index.html"> <i
							class="icon-desktop"></i> <span>Home</span>
					</a></li>

					<li class=""><a class="dropdown-collapse" href="#"> <i
							class="icon-info"></i> <span>Information</span> <i
							class="icon-angle-down angle-down"></i>
					</a>
						<ul class="nav nav-stacked">
							<!-- not released
	              <li class="">
	                  <a href="testArrange.html">
	                    <i class="icon-caret-right"></i>
	                    <span>Examination arrangements</span>
	                  </a>
	                </li>
	                <li class="">
	                  <a href="resultsQuery.html">
	                    <i class="icon-caret-right"></i>
	                    <span>Results Query</span>
	                  </a>
	               </li>
	              
	               -->
							<li class=""><a
								href="${pageContext.request.contextPath}/bills.html?type=init&op=manage">
									<i class="icon-caret-right"></i> <span>Bills Management</span>
							</a></li>
						</ul></li>

					<li class=""><a class="dropdown-collapse" href="#"> <i
							class="icon-bullhorn"></i> <span>Notice</span> <i
							class="icon-angle-down angle-down"></i>
					</a>
						<ul class="nav nav-stacked">
							<li class=""><a
								href="${pageContext.request.contextPath}/notice.html?type=init">
									<i class="icon-caret-right"></i> <span>Latest Notice</span>
							</a></li>
							<!-- 
	                not released
	                <li class="">
	                  <a href="eduAnnouncment.html">
	                    <i class="icon-caret-right"></i>
	                    <span>Educational Announcement</span>
	                  </a>
	                </li>
	                 -->
							<li class=""><a href="${pageContext.request.contextPath}/visa.html?type=init"> <i
									class="icon-caret-right"></i> <span>Visa Information</span>
							</a></li>
						</ul></li>

					<!-- 
	            
	             <li class="">
	              <a class="dropdown-collapse" href="#">
	                <i class="icon-table"></i>
	                <span>Course</span>
	                <i class="icon-angle-down angle-down"></i>
	              </a>
	              <ul class="nav nav-stacked">
	                <li class="">
	                  <a href="currSchedule.html">
	                    <i class="icon-caret-right"></i>
	                    <span>Curriculum schedule</span>
	                  </a>
	                </li>
	                <li class="">
	                  <a href="teacherEva.html">
	                    <i class="icon-caret-right"></i>
	                    <span>Teacher evaluations</span>
	                  </a>
	                </li>
	              </ul>
	            </li>
	             -->

					<li class=""><a
						href="${pageContext.request.contextPath}/pwd.html?type=init"> <i
							class="icon-cogs"></i> <span>Settings</span>
					</a>
					</li>
				</ul>
			</div>
			</nav>
		</c:when>
		<c:otherwise>
			<nav class="" id="main-nav">
			<div class="navigation">
				<div class="search">
					<form accept-charset="UTF-8" action="search_results.html"
						method="get" />
					<div style="margin: 0; padding: 0; display: inline">
						<input name="utf8" type="hidden" value="&#x2713;" />
					</div>
					<div class="search-wrapper">
						<input autocomplete="off" class="search-query" id="q" name="q"
							placeholder="Search..." type="text" value="" />
						<button class="btn btn-link icon-search" name="button"
							type="submit"></button>
					</div>
					</form>
				</div>
				<ul class="nav nav-stacked">
					<li class=""><a
						href="${pageContext.request.contextPath}/index.html"> <i
							class="icon-desktop"></i> <span>首页</span>
					</a></li>
					<c:if test="${user.userTypeId==1 or user.userTypeId==2}">
						<li class="active"><a class="dropdown-collapse" href="#">
								<i class="icon-edit"></i> <span>信息管理</span> <i
								class="icon-angle-down angle-down"></i>
						</a>
							<ul class="nav nav-stacked">
								<li class=""><a
									href="${pageContext.request.contextPath}/config.html?type=init">
										<i class="icon-caret-right"></i> <span>综合信息管理</span>
								</a></li>
								<li class=""><a
									href="${pageContext.request.contextPath}/addStudent.html?type=init">
										<i class="icon-caret-right"></i> <span>学生信息登记</span>
								</a></li>
								<li class=""><a
									href="${pageContext.request.contextPath}/studentMgr.html?type=init">
										<i class="icon-caret-right"></i> <span>学生信息修改</span>
								</a></li>
								<li class=""><a
									href="${pageContext.request.contextPath}/teacherMgr.html?type=init">
										<i class="icon-caret-right"></i> <span>教师管理</span>
								</a></li>
							</ul></li>
					</c:if>
					<!-- only to root -->
					<c:if test="${user.userTypeId==1 or user.userTypeId==2}">
						<li class="">
						<a class="dropdown-collapse" href="#">
						 <i	class="icon-money"></i> <span>学生缴费</span>
						 <i	class="icon-angle-down angle-down"></i>
						</a>
							<ul class="nav nav-stacked">
							<c:if test="${user.userTypeId==1}">
							<li class="">
								<a href="${pageContext.request.contextPath}/bills.html?type=init&op=add">
								 <i class="icon-caret-right"></i> <span>添加账单</span>
								</a>
							</li>
							</c:if>
								<li class="">
								<a href="${pageContext.request.contextPath}/bills.html?type=init&op=manage">
									<i class="icon-caret-right"></i> <span>账单管理</span>
								</a>
								</li>
							</ul>
						</li>
					</c:if>
					<li class=""><a class="dropdown-collapse" href="#"> <i
							class="icon-print"></i> <span>签证</span> <i
							class="icon-angle-down angle-down"></i>
					</a>
						<ul class="nav nav-stacked">
							<li class=""><a
								href="${pageContext.request.contextPath}/visa.html?type=init">
									<i class="icon-caret-right"></i> <span>签证即将到期学生</span>
							</a></li>
						</ul></li>

					<li class=""><a class="dropdown-collapse" href="#"> <i
							class="icon-bullhorn"></i> <span>通知管理</span> <i
							class="icon-angle-down angle-down"></i>
					</a>
						<ul class="nav nav-stacked">
							<li class=""><a
								href="${pageContext.request.contextPath}/notice.html?type=init">
									<i class="icon-caret-right"></i> <span>当前公告</span>
							</a></li>
							<c:if test="${user.userTypeId==1 or user.userTypeId==2}">
								<li class=""><a
									href="${pageContext.request.contextPath}/notice.html?type=edit">
										<i class="icon-caret-right"></i> <span>教务公告</span>
								</a></li>
							</c:if>
						</ul></li>

					<li class=""><a
						href="${pageContext.request.contextPath}/pwd.html?type=init"> <i
							class="icon-cogs"></i> <span>密码修改</span>
					</a></li>
				</ul>
			</div>
			</nav>
		</c:otherwise>
	</c:choose>
</body>
</html>