<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!DOCTYPE html><html><head><meta	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"	name="viewport" /><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head><body class="contrast-red">	<div class="row-fluid">		<div class="span12 box bordered-box orange-border"			style="margin-bottom: 0;">			<div class="box-header purple-background">				<div class="title">					<c:choose>						<c:when test="${user.userTypeId==4}">							<b>History Notice</b>						</c:when>						<c:otherwise>							<b>历史公告</b>						</c:otherwise>					</c:choose>					<span style="color:red;">${error_msg}</span>				</div>				<div class="actions">					<a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>				</div>			</div>			<div class="box-content box-no-padding">				<div class="responsive-table">					<div class="scrollable-area">						<table class="data-table table table-bordered table-striped"							style="margin-bottom: 0;">							<thead>								<tr>									<c:choose>										<c:when test="${user.userTypeId==4}">											<th>Date</th>											<th>Title</th>											<th>Author</th>											<th>Type</th>											<th>Operation</th>										</c:when>										<c:otherwise>											<th>通知发布日期</th>											<th>公告标题</th>											<th>发布人</th>											<th>公告类型</th>											<th>操作</th>										</c:otherwise>									</c:choose>								</tr>							</thead>							<tbody>								<c:forEach items="${notices}" var="notice">								<tr>									<td>${notice.publishDate}</td>									<td>${notice.title}</td>									<td>${notice.writer}</td>									<c:choose>										<c:when test="${user.userTypeId==4}">											<td>${notice.noticeType.eName}</td>										</c:when>										<c:otherwise>											<td>${notice.noticeType.cName}</td>										</c:otherwise>									</c:choose>									<c:choose>										<c:when test="${user.userTypeId==4}">											<td class=""><a href="${pageContext.request.contextPath}/notice.html?type=search&id=${notice.id}" >Check</a></td>										</c:when>										<c:otherwise>											<td class=""><a href="${pageContext.request.contextPath}/notice.html?type=search&id=${notice.id}" >查看</a></td>										</c:otherwise>									</c:choose>								</tr>								</c:forEach>							</tbody>						</table>					</div>				</div>			</div>		</div>	</div></body></html>