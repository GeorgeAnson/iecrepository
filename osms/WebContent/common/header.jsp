<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<header>
	<c:choose>
		<c:when test="${user.userTypeId==4}">
			<div class="navbar">
	        <div class="navbar-inner">
	          <div class="container-fluid">
	            <a class="brand" href="${pageContext.request.contextPath}/index.html">
	              <i class="fa fa-user"></i>
	              <span class="hidden-phone">NBUT - Hello ${user.fullName}</span>
	            </a>
	            <a class="toggle-nav btn pull-left" href="#">
	              <i class="icon-reorder"></i>
	            </a>
	            <ul class="nav pull-right">
	              <li class="dropdown dark user-menu">
	              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	              <span class="user-name hidden-phone">${user.fullName}</span>
	              <b class="caret"></b>
	              </a>
	              <ul class="dropdown-menu">
	                <li>
	                  <a href="${pageContext.request.contextPath}/studentInfo.html?type=init&id=${user.userId}">
	                    <i class="icon-user"></i>Personal information
	                  </a>
	                </li>
	                <li>
	                  <a href="${pageContext.request.contextPath}/pwd.html?type=init">
	                    <i class="icon-cog"></i>Settings
	                  </a>
	                </li>
	                <li class="divider"></li>
	                <li>
	                  <a href="${pageContext.request.contextPath}/logout.html">
	                    <i class="icon-signout"></i>Sign out
	                  </a>
	                </li>
	              </ul>
	              </li>
	            </ul>
	          </div>
	        </div>
	      </div>
		</c:when>
		<c:otherwise>
			<div class="navbar">
	        <div class="navbar-inner">
	          <div class="container-fluid">
	            <a class="brand" href="${pageContext.request.contextPath}/index.html">
	              <i class="fa fa-user"></i>
	              <span class="hidden-phone">NBUT - Hello ${user.fullName}</span>
	            </a>
	            <a class="toggle-nav btn pull-left" href="#">
	              <i class="icon-reorder"></i>
	            </a>
	            <ul class="nav pull-right">
	              <li class="dropdown dark user-menu">
	              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	              <span class="user-name hidden-phone">${user.fullName}</span>
	              <b class="caret"></b>
	              </a>
	              <ul class="dropdown-menu">
	              <!-- 
	              <li>
	                    <a href="${pageContext.request.contextPath}/studentInfo.html?type=init">
	                    <i class="icon-user"></i>个人信息
	                  </a>
	                </li>
	                
	               -->
	                <li>
	                  <a href="${pageContext.request.contextPath}/pwd.html?type=init">
	                    <i class="icon-cog"></i>设置
	                  </a>
	                </li>
	                <li class="divider"></li>
	                <li>
	                  <a href="${pageContext.request.contextPath}/logout.html">
	                    <i class="icon-signout"></i>退出
	                  </a>
	                </li>
	              </ul>
	              </li>
	            </ul>
	          </div>
	        </div>
	      </div>
		</c:otherwise>
	</c:choose>      
    </header>
   
</body>
</html>