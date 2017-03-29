<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%	String path = request.getContextPath();	String basePath = request.getScheme() + "://"			+ request.getServerName() + ":" + request.getServerPort()			+ path + "/"; %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><!DOCTYPE html><html>  <head>    <title>${loginBean.cName} - 教师管理</title>    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" />    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />    <base href="<%=basePath%>" />  </head>  <body class="contrast-red">    <%@ include file="/common/res.jsp" %>    <!-- 引入header头部 -->    <%@ include file="/common/header.jsp" %>     <div id="wrapper">      <!-- 引入左侧导航栏 -->      <%@ include file="/common/nav.jsp" %>      <section id="content">        <div class="container-fluid">          <div class="row-fluid" id="content-wrapper">            <div class="span12">              <div class="page-header">                <h1 class="pull-left">                  <i class="icon-edit"></i>                  <span>教师管理</span>                </h1>              </div>              <div class="row-fluid" style="">                <button class="btnNisal" type="button" style="margin-top: 0px;" id="siftBtn">筛选</button>              </div>              <hr class='hr-double' />              <div class="row-fluid" id="sift" style="display: none;">                <div class="span12 box">                  <div class="box-header purple-background">                    <div class="title">                      <div class="icon-pencil"></div>                      <b>筛选</b>                    </div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content">                    <form id="" accept-charset="UTF-8" action="#" class="form form-horizontal" method="post" style="margin-bottom: 0;" /><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="CFC7d00LWKQsSahRqsfD+e/mHLqbaVIXBvlBGe/KP+I=" /></div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">选择学院</label>                        <div class="controls">                          <select id="" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${academyMap}" var="academy">                              <option value="${academy.key}">${academy.value.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">系</label>                        <div class="controls">                          <select id="" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${majorMap}" var="major">                              <option value="${major.key}">${major.value.majorChineseName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">班级</label>                        <div class="controls">                          <select id="" class='select2 input-block-level' style="width: 216px;">                            <c:forEach items="${classMap}" var="cclass">                             <option value="${cclass.value.cclass.classId}">${cclass.value.cclass.cName}</option>                            </c:forEach>                          </select>                        </div>                      </div>                      <hr class='hr-normal' />                      <div class="control-group">                        <div class="controls">                          <button class="btnNisal" style="margin: 0px;" type="button" id="">查询</button>                        </div>                      </div>                    </form>                  </div>                </div>                <hr class='hr-double' />              </div>              <div class="row-fluid" id="addTeacher">                <div class="span12 box">                  <div class="box-header purple-background">                    <div class="title">                      <div class="icon-user"></div>                      <b>添加教师</b>                    </div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content">                    <form id="addTeacherForm" accept-charset="UTF-8" action="#" class="form form-horizontal" method="post" style="margin-bottom: 0;" /><div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;" /><input name="authenticity_token" type="hidden" value="CFC7d00LWKQsSahRqsfD+e/mHLqbaVIXBvlBGe/KP+I=" /></div>                      <input type="hidden" id="classInfo" name="" value="">                      <div class="control-group">                        <label class="control-label" for="">教师姓名</label>                        <div class="controls">                          <input class="" id="teacherName" placeholder="教师名称" type="text" />                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">教师工号</label>                        <div class="controls">                          <input class="" id="teacherId" placeholder="教师工号" type="text" />                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">手机号</label>                        <div class="controls">                          <input class="" id="teacherTel" placeholder="手机号" type="text" />                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">邮箱</label>                        <div class="controls">                          <input class="" id="teacherMail" placeholder="邮箱" type="text" />                        </div>                      </div>                      <div class="control-group">                        <label class="control-label">性别</label>                        <div class="controls">                          <label class="radio inline">                            <input type="radio" value="1" name="sex" checked="checked" />                              男                          </label>                          <label class="radio inline">                            <input type="radio" value="2" name="sex"/>                              女                          </label>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">学院</label>                        <div class="controls">                          <select id="teacherCoggle" class='select2 input-block-level ' style="width: 216px;">                            <option value="1">1</option>                          </select>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">专业</label>                        <div class="controls">                          <input id="teacherMajor" class="" id="" placeholder="系" type="text" />                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">职称</label>                        <div class="controls">                          <input id="teacherProfe" class="" id="" placeholder="职称" type="text" />                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="">所授班级</label>                        <div class="controls">                          <div>                            <label class="checkbox-inline">                              <input class="proClasses" type="checkbox" id="" value="1">班级1                            </label>                            <label class="checkbox-inline">                              <input class="proClasses" type="checkbox" id="" value="2">班级1                            </label>                            <label class="checkbox-inline">                              <input class="proClasses" type="checkbox" id="" value="3">班级1                            </label>                                                      </div>                        </div>                      </div>                      <div class="control-group">                        <label class="control-label" for="inputSelect">教师用户类型</label>                        <div class="controls">                          <select id="teacherType" class='select2 input-block-level ' style="width: 216px;">                            <option value="1">教师</option>                            <option value="2">管理员</option>                            <option value="3">系统管理员</option>                          </select>                        </div>                      </div>                      <hr class='hr-normal' />                      <div class="control-group">                        <div class="controls">                          <button id="addTeacherBtn" class="btnNisal" style="margin: 0px;" type="button">添加</button>                        </div>                      </div>                    </form>                  </div>                </div>                <hr class='hr-double' />              </div>              <div class="row-fluid">                <div class="span12 box bordered-box orange-border" style="margin-bottom:0;">                  <div class="box-header purple-background">                    <div class="title"><b>教师管理</b></div>                    <div class="actions">                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>                    </div>                  </div>                  <div class="box-content box-no-padding">                    <div class="responsive-table">                      <div class="scrollable-area">                        <table class="data-table table table-bordered table-striped" style="margin-bottom:0;">                          <thead>                            <tr>                              <th>教师姓名</th>                              <th>教师性别</th>                              <th>教师工号</th>                              <th>学院</th>                              <th>系</th>                              <th>职称</th>                            </tr>                          </thead>                          <tbody>                            <tr>                              <td><a href="">Nisal</a></td>                              <td>1</td>                              <td>1548778459</td>                              <td>电信学院</td>                              <td>？？</td>                              <td>小学生</td>                            </tr>                          </tbody>                        </table>                      </div>                    </div>                  </div>                </div>              </div>            </div>          </div>        </div>      </section>    </div>        <script type="text/javascript">      $(document).ready(function(){        $("#siftBtn").click(function(){          $("#sift").slideToggle();        });        $("#addTeacherBtn").click(function(){          var str = "";          var classer = $(".proClasses");          var flag = 0;          classer.each(function(){            if($(this).attr("checked") === "checked"){              str = str + $(this).val() + ";";              flag = 1;            }           });          if(!flag){            layer.msg("请填写所有信息")            return false;          }          var teacherName   = $("#teacherName").val();                    var teacherId     = $("#teacherId").val();                    var teacherTel    = $("#teacherTel").val();                    var teacherMail   = $("#teacherMail").val();                    var teacherCoggle = $("#teacherCoggle").val();                    var teacherMajor  = $("#teacherMajor").val();                    var teacherProfe  = $("#teacherProfe").val();                    var teacherType   = $("#teacherType").val();                      if(teacherName === "" || teacherId === "" || teacherTel === ""||             teacherMail === "" || teacherCoggle === "" || teacherMajor === "" ||             teacherProfe === "" || teacherType === ""          ){            layer.msg("请填写所有信息")            return false;          }          $("#classInfo").val(str);          $("#addTeacherForm").submit();        });      });    </script>
  </body></html>