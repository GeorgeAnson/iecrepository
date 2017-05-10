<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%	String path = request.getContextPath();	String basePath = request.getScheme() + "://"			+ request.getServerName() + ":" + request.getServerPort()			+ path + "/"; %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><!DOCTYPE html><html>  <head>    <title>${loginBean.cName} - 学生管理</title>    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" />    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />    <base href="<%=basePath%>" />  </head>  <body class="contrast-red">    <%@ include file="/common/res.jsp" %>    <!-- 引入header头部 -->    <%@ include file="/common/header.jsp" %>     <div id="wrapper">      <!-- 引入左侧导航栏 -->      <%@ include file="/common/nav.jsp" %>      <section id="content">        <div class="container-fluid">          <div class="row-fluid" id="content-wrapper">            <div class="span12">              <div class="page-header">                <h1 class="pull-left">                  <i class="icon-search"></i>                  <span>学生信息</span>                </h1>              </div>              <div class="row-fluid" id="" style="">                <div class="span12 box">                  <div class="box-header purple-background">                    <div class="title">                      <div class="icon-pencil"></div>                      <b>选择班级</b>                    </div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content">                    <form id="" accept-charset="UTF-8" action="${pageContext.request.contextPath}/studentMgr.html" class="form form-horizontal" method="post" style="margin-bottom: 0;" />                    <div style="margin:0;padding:0;display:inline">                      <input name="type" type="hidden" value="search" />                      <input name="page" type="hidden" value="0" />                    </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择学院</label>                        <div class="controls">                          <select id="academy" name="academyId" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${academyMap}" var="academy">                              <option value=""></option>                              <option value="${academy.key}">${academy.value.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择专业</label>                        <div class="controls">                          <select id="major" name="majorId" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${majorMap}" var="major">                              <option value=""></option>                            	<option value="${major.key}">${major.value.majorChineseName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择班级</label>                        <div class="controls">                          <select id="clazz" name="cclassId" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${classMap}" var="amc">                              <option value=""></option>                              <option value="${amc.key}">${amc.value.cclass.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <hr class='hr-normal' />                      <div class="control-group">                        <div class="controls">                          <button class="btnNisal" style="margin: 0px; width: 106px" type="submit" id="">查询</button>                          <button class="btnNisal" style="margin: 0px; width: 106px" type="button" id="exportBtn">导出</button>                        </div>                      </div>                    </form>                  </div>                </div>                <hr class='hr-double' />              </div>              <div class="row-fluid">                <div class="span12 box bordered-box orange-border" style="margin-bottom:0;">                  <div class="box-header purple-background">                    <div class="title"><b>学生信息管理</b>&nbsp;<span style="color:red;">${error_msg}</span></div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content box-no-padding">                    <div class="responsive-table">                      <div class="scrollable-area">                        <table class="table table-bordered table-striped" style="margin-bottom:0;">                          <thead>                            <tr>                              <th>姓名</th>                              <th>护照号</th>                              <th>国内电话</th>                              <th>邮箱</th>                              <th>学籍状态</th>                              <th>外籍信息</th>                              <th>删除</th>                            </tr>                          </thead>                          <tbody>                            <c:forEach items="${students}" var="student">                               <tr>                                <td><a href="${pageContext.request.contextPath}/studentInfo.html?type=init&id=${student.userId}" target="_blank">${student.fullName}</a></td>                                <td>                                <c:choose>                                 <c:when test="${student.schoolRoll.studentTypeId==1}">                                 	<c:if test="${not empty student.passports and not empty student.passports.get(0)}">                                  		${student.passports.get(0).passportNumber}                                  	</c:if>                                 </c:when>                                 <c:otherwise>未填写</c:otherwise>                                </c:choose>                                </td>                                <td>                                <c:choose>                                 <c:when test="${not empty student.phone}">                                 ${student.phone}                                 </c:when>                                 <c:otherwise>未填写</c:otherwise>                                </c:choose>                                </td>                                <td>${student.email}</td>                                <td>${student.schoolRoll.rollStatusType.cName}</td>                                <td class="foreignInfo">                                <c:choose>                                 <c:when test="${student.schoolRoll.studentTypeId==1}">                               		  查看<input type="hidden" class="privateId" value="${student.userId}">                                 </c:when>                                 <c:otherwise>未填写</c:otherwise>                                </c:choose>                                </td>                                <td class="delete">删除<input type="hidden" value="${student.userId}"></td>                              </tr>                            </c:forEach>                          </tbody>                        </table>                      </div>                    </div>                  </div>                  <div class="paging">                    <a class="prev" href="${pageContext.request.contextPath}/studentMgr.html?type=search&academyId=${searchForm.academyId}&majorId=${searchForm.majorId}&cclassId=${searchForm.cclassId}&page=${searchForm.page}">上一页</a>                    <a class="next" href="${pageContext.request.contextPath}/studentMgr.html?type=search&academyId=${searchForm.academyId}&majorId=${searchForm.majorId}&cclassId=${searchForm.cclassId}&page=${searchForm.page}">下一页</a>                    <input type="text" name="" value="" placeholder="页码">                    <span class="nowPage">${searchForm.page}</span>/<span class="maxPage">${searchForm.pages}</span>                    <a class="trun" href="">跳转</a>                  </div>                </div>              </div>            </div>          </div>        </div>      </section>    </div>       <!-- 外籍信息弹出框 -->    <div style="display: none;" class="" id="foreignInfo">      <div class="">        <span class="studentInfoItemTitle">国籍&emsp;&emsp;&emsp;&emsp;</span>        <span class="foreighInfoSpan" id="country"></span>      </div>      <div class="">        <span class="studentInfoItemTitle">出生地&emsp;&emsp;&emsp;</span>        <span class="foreighInfoSpan" id="birthPlace"></span>      </div>      <div class="">        <span class="studentInfoItemTitle">家庭住址&emsp;&emsp;</span>        <span class="foreighInfoSpan" id="address"></span>      </div>      <div class="">        <span class="studentInfoItemTitle">国外联系电话</span>        <span class="foreighInfoSpan" id="overseasPhone"></span>      </div>      <div class="">        <span class="studentInfoItemTitle">是否已婚&emsp;&emsp;</span>        <span class="foreighInfoSpan" id="marital"></span>      </div>      <div class="">        <span class="studentInfoItemTitle">出生日期&emsp;&emsp;</span>        <span class="foreighInfoSpan" id="birthday"></span>      </div>    </div>    <!-- SCRIPTS -->    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/update.js"></script>    <script>      var nowPage = parseInt($(".nowPage").html());      $(".prev").click(function(){        if(nowPage <= 1){          layer.msg("已经是第一页了");          return false;        }         window.location.href = window.location.href.replace(/\d{1,}$/g, nowPage - 1);      });      $(".next").click(function(){        if(nowPage >= parseInt($(".maxPage").html())){          layer.msg("已经是最后一页了");          return false;        }         window.location.href = window.location.href.replace(/\d{1,}$/g, nowPage + 1);      });      function onlyNum() {        if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))        if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))        event.returnValue=false;      }      $(document).ready(function(){        $("#updateForm").hide();        $(".update").click(function(){          layer.msg("<span style='font-size: 18px;'>请到学生信息详情页修改学生详细信息</span>");        });        $(".paging .trun").click(function(){          var page = $(".paging input").val();          if(page === ""){            layer.msg("请填入页码");            return false;          }          var link = window.location.href;          var src  = link.replace(/\d{1,}$/g, page);          window.location.href = src;          return false;        });      });      $('.delete').click(function(){    	  var id = $(this).find('input').val();    	  $.ajax({    		type:'POST',    		url:'./studentMgr.html',    		data:{    			'type':'del',    			'id':id    		},    		dataType:'json',    		success:function(data){    			if(data.success){    				window.location.reload();     			}    		},    		error:function(jqXHR){    			layer.msg("发生错误："+jqXHR.status);    		}    	  })      });         </script>
  </body>
</html>
