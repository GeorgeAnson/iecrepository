<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%	String path = request.getContextPath();	String basePath = request.getScheme() + "://"			+ request.getServerName() + ":" + request.getServerPort()			+ path + "/"; %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><!DOCTYPE html><html>  <head>    <title>${loginBean.cName} - 学生信息登记</title>    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" />    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />    <base href="<%=basePath%>" />  </head>  <body class="contrast-red">    <%@ include file="/common/res.jsp" %>    <!-- 引入header头部 -->    <%@ include file="/common/header.jsp" %>     <div id="wrapper">      <!-- 引入左侧导航栏 -->      <%@ include file="/common/nav.jsp" %>            <section id="content">        <div class="container-fluid">          <div class="row-fluid" id="content-wrapper">            <div class="span12">              <div class="page-header">                <h1 class="pull-left">                  <i class="icon-money"></i>                  <span>添加账单</span>                </h1>              </div>              <div class="row-fluid">                <div class="span12 box bordered-box orange-border" style="margin-bottom:0;">                  <div class="box-header purple-background">                    <div class="title"><b>批量添加</b>&nbsp;<span style="color:red;"">${error_msg}</span></div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content box-no-padding">                    <form id="addBillForm" accept-charset="UTF-8" action="${pageContext.request.contextPath}/bills.html" class="form form-horizontal" method="post" style="margin-bottom: 0;" />                    	<input type="hidden" id="paymentIds" name="paymentTypeIds" value="1;">	                    <div style="margin:0;padding:0;display:inline">		                    <input name="type" type="hidden" value="add" />		                    <input name="op" type="hidden" value="addbills" />	                    </div>                      <div class="control-group" style="margin-top: 20px;">                        <label class="control-label" for="inputSelect">学院</label>                        <div class="controls">                          <select id="coggle" name="academyId" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${academyMap}" var="academy">                              <option value="${academy.key}">${academy.value.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label id="" class="control-label" for="inputSelect">专业</label>                        <div class="controls">                          <select id="major" name="majorId" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${majorMap}" var="major">                             <option value="${major.key}">${major.value.majorChineseName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">班级</label>                        <div class="controls">                          <select id="klass" name="cclassId" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${classMap}" var="cclass">                             <option value="${cclass.key}">${cclass.value.cclass.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">学年</label>                        <div class="controls">                          <select id="studyYear" name="schoolYear" class='select2 input-block-level' style="width: 216px;">                            <option value="2017-2018">2017-2018</option>                            <option value="2018-2019">2018-2019</option>                            <option value="2020-2021">2020-2021</option>                            <option value="2011-2022">2011-2022</option>                            <option value="2022-2023">2022-2023</option>                            <option value="2023-2024">2023-2024</option>                            <option value="2024-2025">2024-2025</option>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label">学期</label>                        <div class="controls">                          <label class="checkbox inline">                            <input type="checkbox" name="theSemester" value="1" class="moneySrc">                            <span>第一学期</span>                          </label>                          <label class="checkbox inline">                            <input type="checkbox" name="theSemester" value="2" class="moneySrc">                            <span>第二学期</span>                          </label>                        </div>                      </div>                                                                  <c:forEach items="${paymentTypes}" var="paymentType">                       <div class="paymentItemGroup control-group">                        <label class="control-label" for="inputPassword4">缴费项目</label>                        <div class="controls">                          <input type="checkbox" value="${paymentType.paymentTypeId}" class="paymentItem" style="">                          <input id="" name="totalNeeds" placeholder="${paymentType.cName} - 应缴金额" readonly type="text" class="paymentItemInput" />                          <input id="" name="money" placeholder="${paymentType.cName} - 本次缴费金额" readonly type="text" class="paymentItemInput" />                        </div>                      </div>                      </c:forEach>                                            <div class="control-group">                        <label class="control-label">描述</label>                        <div class="controls">                          <input id="describe" type="text" placeholder="本次缴费描述" name="describe" value="">                        </div>                      </div>                                            <div class="control-group">                        <label class="control-label" for="inputPassword4"></label>                        <div class="controls">                          <button type="button" class="btnNisal" id="addGroupBills">添加</button>                        </div>                      </div>                    </form>                  </div>                </div>              </div>                            <hr class="hr-double">                        </div>          </div>        </div>      </section>          <script type="text/javascript">      $(document).ready(function(){    	  $(".paymentItem").click(function(){              if($(this).attr("checked") === "checked") $(this).parent().find(".paymentItemInput").removeAttr("readonly");              if($(this).attr("checked") != "checked"){                $(this).parent().find(".paymentItemInput").attr("readonly", "readonly");                $(this).parent().find(".paymentItemInput").val("");              }            });        $(".moneySrc").click(function(){          $(".moneySrc").each(function(){            $(this).removeAttr("checked");          });          $(this).attr("checked", "checked");        });        $("#addGroupBills").click(function(){          // paymentIds          var str = "";          $(".paymentItem").each(function(){            if($(this).attr("checked") === "checked") str = str + $(this).val() + ";";           });          var coggle    = $("#coggle").val();          var major     = $("#major").val();          var klass     = $("#klass").val();          var studyYear = $("#studyYear").val();          var describe  = $("#describe").val();                    if(coggle === "" || major === "" || klass === "" || studyYear === "" || describe === ""){            layer.msg("请填写所有信息");            return false;          }          var checkedNum = 0;          $(".paymentItem").each(function(){            if($(this).attr("checked") === "checked") checkedNum ++;          });          if(!checkedNum){            layer.msg("请至少选择一项缴费项目");            return false;          }          var flag = 0;          var itemGroups = $(".paymentItemGroup");          itemGroups.each(function(){            if($(this).find(".paymentItem").attr("checked") === "checked"){              $(this).find(".paymentItemInput").each(function(){                if($(this).val() === ""){                  layer.msg("请填写所有信息");                  flag = 1;                  return false;                }              });              if(flag) return false;            }          });          if(flag) return;		  $("#paymentsIds").val(str);          $("#addBillForm").submit();        });      });    </script>  </body></html>