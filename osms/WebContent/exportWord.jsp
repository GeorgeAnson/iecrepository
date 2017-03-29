<%@ page language="java" contentType="text/html; charset=utf=8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setHeader("Content-disposition","attachment;filename=exportWord.doc");
	String strd=(String)request.getAttribute("htmltable");
	out.print(strd);
%>