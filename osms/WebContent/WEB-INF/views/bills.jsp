<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%	String path = request.getContextPath();	String basePath = request.getScheme() + "://"			+ request.getServerName() + ":" + request.getServerPort()			+ path + "/"; %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><!DOCTYPE html><html>  <head>    <c:choose>    	<c:when test="${user.userTypeId==4}">    		<title>${loginBean.eName} - Bills management</title>    	</c:when>    	<c:otherwise><title>${loginBean.cName} - 账单管理</title></c:otherwise>    </c:choose>    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" />    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />    <base href="<%=basePath%>" />  </head>  <body class="contrast-red">    <%@ include file="/common/res.jsp" %>    <!-- 引入header头部 -->    <%@ include file="/common/header.jsp" %>     <div id="wrapper">      <!-- 引入左侧导航栏 -->      <%@ include file="/common/nav.jsp" %>      <section id="content">        <div class="container-fluid">          <div class="row-fluid" id="content-wrapper">            <div class="span12">              <div class="page-header">                <h1 class="pull-left">                  <i class="icon-money"></i>                   <c:choose>				    	<c:when test="${user.userTypeId==3 or user.userTypeId==4}">				    		<span>Bills management</span>				    	</c:when>				    	<c:otherwise><span>账单管理</span></c:otherwise>				    </c:choose>                </h1>              </div>                            <c:if test="${user.userTypeId ==1 or user.userTypeId==2}">              <div class="row-fluid" id="" style="">                <div class="span12 box">                  <div class="box-header purple-background">                    <div class="title">                      <div class="icon-pencil"></div>                      <b>选择查询条件</b>&nbsp;<span style="color:red;"">${error_msg}</span>                    </div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content">                    <form id="" accept-charset="UTF-8" action="${pageContext.request.contextPath}/bills.html" class="form form-horizontal" method="post" style="margin-bottom: 0;" />                    <div style="margin:0;padding:0;display:inline">                    <input name="type" type="hidden" value="search" />                    <input name="page" type="hidden" value="0" />                    </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择学院</label>                        <div class="controls">                          <select id="" name="academyId" class='select2 input-block-level' style="width: 216px;">                          <option value=""></option>                            <c:forEach items="${academyMap}" var="academy">                              <option value="${academy.key}">${academy.value.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择专业</label>                        <div class="controls">                          <select id="" name="majorId" class='select2 input-block-level' style="width: 216px;">                          <option value=""></option>                            <c:forEach items="${majorMap}" var="major">                              <option value="${major.key}">${major.value.majorChineseName}</option>                            </c:forEach>                            </option>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择班级</label>                        <div class="controls">                          <select id="" name="cclassId" class='select2 input-block-level' style="width: 216px;">                          <option value=""></option>                            <c:forEach items="${classMap}" var="amc">                             <option value="${amc.value.cclass.classId}">${amc.value.cclass.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">学年</label>                        <div class="controls">                          <select id="" name="schoolYear" class='select2 input-block-level' style="width: 216px;">                            <option value="2017-2018">2017-2018</option>                            <option value="2018-2019">2018-2019</option>                            <option value="2020-2021">2020-2021</option>                            <option value="2011-2022">2011-2022</option>                            <option value="2022-2023">2022-2023</option>                            <option value="2023-2024">2023-2024</option>                            <option value="2024-2025">2024-2025</option>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label">学期</label>                        <div class="controls">                          <label class="checkbox inline">                            <input class="selTerm" checked="checked" type="checkbox" name="theSemester" value="1" class="moneySrc">                            <span>第一学期</span>                          </label>                          <label class="checkbox inline">                            <input class="selTerm" type="checkbox" name="theSemester" value="2" class="moneySrc">                            <span>第二学期</span>                          </label>                        </div>                      </div>                      <hr class='hr-normal' />                      <div class="control-group">                        <div class="controls">                          <button class="btnNisal" style="margin: 0px;" type="submit" id="">查询</button>                        </div>                      </div>                    </form>                  </div>                </div>                <hr class='hr-double' />              </div>              </c:if>                                                        <div class="row-fluid">                <div class="span12 box bordered-box orange-border" style="margin-bottom:0;">                  <div class="box-header purple-background">                    <div class="title">                    <c:choose>				    	<c:when test="${user.userTypeId==3 or user.userTypeId==4}">				    		<b>Bills Informations</b>				    	</c:when>				    	<c:otherwise><b>账单信息</b></c:otherwise>				    </c:choose>                                        </div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content box-no-padding">                    <div class="responsive-table">                      <div class="scrollable-area">                        <table class="table table-bordered table-striped" style="margin-bottom:0;">                          <thead>                          <c:choose>                            <c:when test="${user.userTypeId==1 or user.userTypeId==2}">                             <tr>                              <th>缴费人</th>                              <th>联系方式</th>                              <th>时间</th>                              <th>应缴金额</th>                              <th>实缴金额</th>                              <th>操作人</th>                              <th>缴费描述</th>                              <c:if test="${user.userTypeId==1}"><th>继续缴费</th></c:if>                              <th>查看详情</th>                            </tr>                            </c:when>                            <c:otherwise>                              <tr>                              <th>Payer</th>                              <th>Phone</th>                              <th>Date</th>                              <th>Needs to pay</th>                              <th>Paid</th>                              <th>Operator</th>                              <th>describe</th>                              <c:if test="${user.userTypeId==1}"><th>continue to pay</th></c:if>                              <th>check detail</th>                            </tr>                            </c:otherwise>                          </c:choose>                                                      </thead>                          <tbody>                          <c:forEach items="${payments}" var="payment">                           <tr>                              <td><a href="${pageContext.request.contextPath}/studentInfo.html?type=init&id=${payment.user.userId}" target="_blank">${payment.user.fullName}</a></td>                              <td class="phoner">${payment.user.phone}</td>                              <td class="assessment">${payment.schoolYear} 第${payment.theSemester}学期</td>                              <td class="">${payment.totalMoney}</td>                              <td class="">${payment.money}</td>                              <td>${payment.oprUser.fullName}</td>                              <td>${payment.describle}</td>                              <c:choose>                              	<c:when test="${user.userTypeId==1 or user.userTypeId==2}">                              	 <c:if test="${user.userTypeId==1}">                              	 <td class="updateBill">继续缴费</td>                              	 </c:if>                              	 <td class="billInfo">详情<input type="hidden" name="id" value="${payment.user.userId}"></td>                              	</c:when>                              	<c:otherwise>                                 <td class="billInfo">detail<input type="hidden" name="id" value="${payment.user.userId}"></td>                              	</c:otherwise>                              </c:choose>                            </tr>                          </c:forEach>                          </tbody>                        </table>                      </div>                    </div>                  </div>                  <div class="paging">                    <c:choose>                      <c:when test="${user.userTypeId==1 or user.userTypeId==2}">                      <a class="prev" href="${pageContext.request.contextPath}/bills.html?type=search&limit=10&academyId=${searchForm.academyId}&majorId=${searchForm.majorId}&cclassId=${searchForm.cclassId}&schoolYear=${searchForm.schoolYear}&theSemester=${searchForm.thsSemester}&page=${searchForm.page}">上一页</a>                      <a class="next" href="${pageContext.request.contextPath}/bills.html?type=search&limit=10&academyId=${searchForm.academyId}&majorId=${searchForm.majorId}&cclassId=${searchForm.cclassId}&schoolYear=${searchForm.schoolYear}&theSemester=${searchForm.thsSemester}&page=${searchForm.page}">下一页</a>                      </c:when>                      <c:otherwise>                      <a class="prev" href="${pageContext.request.contextPath}/bills.html?type=search&limit=10&academyId=${searchForm.academyId}&majorId=${searchForm.majorId}&cclassId=${searchForm.cclassId}&schoolYear=${searchForm.schoolYear}&theSemester=${searchForm.thsSemester}&page=${searchForm.page}">PREV</a>                      <a class="next" href="${pageContext.request.contextPath}/bills.html?type=search&limit=10&academyId=${searchForm.academyId}&majorId=${searchForm.majorId}&cclassId=${searchForm.cclassId}&schoolYear=${searchForm.schoolYear}&theSemester=${searchForm.thsSemester}&page=${searchForm.page}">NEXT</a>                      </c:otherwise>                    </c:choose>                    <input onkeydown="onlyNum();" type="text" name="page" value="" placeholder="page">                    <span class="nowPage">${searchForm.page}</span>/<span class="maxPage">${searchForm.pages}</span>                    <c:choose>                      <c:when test="${user.userTypeId==1 or user.userTypeId==2}"><a class="trun" href="">跳转</a></c:when>                      <c:otherwise><a class="trun" href="">TURN</a></c:otherwise>                    </c:choose>                  </div>                </div>              </div>                            <hr class="hr-double">                            <c:if test="${user.userTypeId==1 or user.userTypeId==2 or user.userTypeId==4}">              <div class="row-fluid" style="display: none;" id="stuBillInfo">                <div class="span12 box bordered-box orange-border" style="margin-bottom:0;">                  <div class="box-header purple-background">                    <div class="title"><b>个人账单详情</b></div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content box-no-padding">                    <div class="responsive-table">                      <div class="scrollable-area">                        <table class="table table-bordered table-striped" style="margin-bottom:0;">                          <thead>                            <tr>                              <td>缴费人</td>                              <td>缴费项目</td>                              <td>应缴金额</td>                              <td>已缴金额</td>                              <td>操作人</td>                              <td>缴费日期</td>                            </tr>                          </thead>                          <tbody id="billInfoer">                          </tbody>                        </table>                      </div>                    </div>                  </div>                </div>              </div>              </c:if>            </div>          </div>        </div>      </section>    </div>            <!-- 添加个人账单 -->    <div style="display: none;" class="" id="updateBill">      <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/bills.html" class="form form-horizontal" method="post" style="margin-bottom: 0;" />      <div style="margin:0;padding:0;display:inline">      <input name="type" type="hidden" value="add" />	  <input name="op" type="hidden" value="bills" />      </div>        <input type="hidden" class="updateBillPerId" name="" value="">        <div class="control-group" style="margin-top: 20px;">          <label class="control-label" for="">缴费人手机号或邮箱</label>          <div class="controls">            <input name="condition" id="conditioner" readonly="readonly" placeholder="缴费人手机号或邮箱" type="text" value=""/>          </div>        </div>                <div class="control-group">          <label class="control-label" for="">缴费项目</label>          <div class="controls">            <select name="paymentTypeId" class='select2 input-block-level' style="width: 216px;" id="">              <option value="">学费</option>            </select>          </div>        </div>        <div class="control-group" style="">          <label class="control-label" for="">应缴金额</label>          <div class="controls">            <input name="totalNeeds" id="" placeholder="应缴金额" type="text" />          </div>        </div>        <div class="control-group" style="">          <label class="control-label" for="">本次金额</label>          <div class="controls">            <input name="money" id="" placeholder="本次金额" type="text" />          </div>        </div>        <div class="control-group">          <label class="control-label" for="inputSelect">学年</label>          <div class="controls">            <select name="schoolYear" id="" class='select2 input-block-level' style="width: 216px;">              <option value="2017-2018">2017-2018</option>			  <option value="2018-2019">2018-2019</option>	          <option value="2020-2021">2020-2021</option>	          <option value="2011-2022">2011-2022</option>	          <option value="2022-2023">2022-2023</option>	          <option value="2023-2024">2023-2024</option>	          <option value="2024-2025">2024-2025</option>            </select>          </div>        </div>        <div class="control-group">          <label name="" class="control-label" for="inputSelect">学期</label>          <div class="controls">             <label class="checkbox-inline">              <input type="radio" name="theSemester" id="" value="1" checked> &nbsp;第一学期            </label>            <label class="checkbox-inline">              <input type="radio" name="theSemester" id="" value="2"> &nbsp;第二学期            </label>          </div>        </div>        <div class="control-group" style="">          <label class="control-label" for="">描述</label>          <div class="controls">            <input name="describe" id="" placeholder="描述" type="text" value=""/>          </div>        </div>        <hr class='hr-normal' />        <div class="control-group">          <div class="controls">            <button class="btnNisal" style="margin: 0px;" type="button">添加</button>          </div>        </div>      </form>    </div>   <script>      function onlyNum() {        if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))        if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))        event.returnValue=false;      }      $(document).ready(function(){    	  $('.selTerm').click(function(){    		  $('.selTerm').each(function(){    			  $(this).removeAttr("checked");    		  });    	  	  $(this).attr("checked", "checked");    	  });          var nowPage = parseInt($(".nowPage").html());          $(".prev").click(function(){            if(nowPage <= 1){              layer.msg("已经是第一页了");              return false;            }             window.location.href = window.location.href.replace(/\d{1,}$/g, nowPage - 1);          });          $(".next").click(function(){            if(nowPage >= parseInt($(".maxPage").html())){              layer.msg("已经是最后一页了");              return false;            }             window.location.href = window.location.href.replace(/\d{1,}$/g, nowPage + 1);          });        $(".updateBill").click(function(){          $("#conditioner").val($(this).parent().find('.phoner').html());          var index = layer.open({            type: 1,            anim: 0,            zIndex: 100,            title: "添加账单",            resize: false,            shade: 0.3,            area: ["80%", "auto"],            content: $("#updateBill"),          });        });        $(".paging .trun").click(function(){            var page = $(".paging input").val();            if(page === ""){              layer.msg("请填入页码");              return false;            }            var link = window.location.href;            var src  = link.replace(/\d{1,}$/g, page);            window.location.href = src;            return false;          });        //[{"name":"nisal","item":"xxx","assessment":"123","paid":"20","opPer":"nisal","date":"1997-1-1"},{"name":"nisal","item":"xxx","assessment":"123","paid":"20","opPer":"nisal","date":"1997-1-1"},{"name":"nisal","item":"xxx","assessment":"123","paid":"20","opPer":"nisal","date":"1997-1-1"}]        $(".billInfo").click(function(){          var id = $(this).find("input").val();          $.ajax({            type: "POST",            url: "./bills.html?type=detail&id="+id,            data: {              id : id            },            dataType: "json",            success: function(data){              $("#stuBillInfo").slideDown();              layer.msg("读取成功");              var billInfo = "";              for(i in data){                var isEnough = parseInt(data[i].money) < parseInt(data[i].totalMoney) ? "<td style='color: #ff0000;'>" + data[i].money + "</td>" : "<td>" + data[i].money + "</td>";                billInfo += "<tr><td>" + data[i].user.fullName + "</td><td>" + data[i].paymentType.cName + "</td><td>" + data[i].totalMoney + "</td>" + isEnough + "<td>" + data[i].oprUser.fullName + "</td><td>" + data[i].payDate + "</td></tr>";              }              $("#billInfoer").append(billInfo);            },            error: function(jqXHR){               layer.msg("发生错误：" + jqXHR.status);            },          });        });      });    </script>
  </body></html>