<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<section id="content">
        <div class="container-fluid">
          <div class="row-fluid" id="content-wrapper">
            <div class="span12">
              <div class="page-header">
                <h1 class="pull-left">
                  <i class="icon-cogs"></i>
                  <c:choose>
                  	<c:when test="${user.userTypeId==4}"><span>Password Management</span></c:when>
                  	<c:otherwise><span>密码管理</span></c:otherwise>
                  </c:choose>
                  
                </h1>
              </div>

              <div class="row-fluid" id="">
                <div class="span12 box">
                  <div class="box-header blue-background">
                    <div class="title">
                      <div class="icon-key"></div>
                      <c:choose>
                      	<c:when test="${user.userTypeId==4}"><b>Change Password</b><span style="color:red">&nbsp;${error_msg}</span></c:when>
                      	<c:otherwise><b>修改密码</b><span style="color:red">&nbsp;${error_msg}</span></c:otherwise>
                      </c:choose>
                    </div>
                    <div class="actions">
                      <a href="#" class="btn box-collapse btn-mini btn-link"><i></i></a>
                    </div>
                  </div>
                  <div class="box-content">
                    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/pwd.html" class="form form-horizontal" method="post" style="margin-bottom: 0;" />
                    <div style="margin:0;padding:0;display:inline">
                    	<input name="id" type="hidden" value="${user.userId}" />
                    	<input name="type" type="hidden" value="update" />
                    </div>
                      <div class="control-group">
                      	<c:choose>
                      		<c:when test="${user.userTypeId==4}"><label class="control-label" for="">Old password</label></c:when>
                      		<c:otherwise><label class="control-label" for="">旧密码</label></c:otherwise>
                      	</c:choose>
                        <div class="controls">
                          <input id="oldPassword" name="oldPwd" placeholder="Old password" type="text" />
                        </div>
                      </div>
                      <div class="control-group">
                        <c:choose>
                      		<c:when test="${user.userTypeId==4}"><label class="control-label" for="">New password</label></c:when>
                      		<c:otherwise><label class="control-label" for="">新密码</label></c:otherwise>
                      	</c:choose>
                        <div class="controls">
                          <input id="newPassword" name="newPwd" placeholder="New password" type="password" />
                        </div>
                      </div>
                      <div class="control-group">
                        <c:choose>
                      		<c:when test="${user.userTypeId==4}"><label class="control-label" for="">Confirm password</label></c:when>
                      		<c:otherwise><label class="control-label" for="">确认密码</label></c:otherwise>
                      	</c:choose>
                        <div class="controls">
                          <input id="confirmPassword" name="confirmPwd" placeholder="Confirm password" type="password" />
                          <span style="margin-left: 10px; color: #ff4949;" id="confirmPasswordMsg"></span>
                        </div>
                      </div>
                      <hr class='hr-normal' />
                      <div class="control-group">
                        <div class="controls">
                          <c:choose>
                          	<c:when test="${user.userTypeId==4}">
                          		<button class="btnNisal" style="margin: 0px;" id="changePassword" type="submit">Update</button>
                          	</c:when>
                          	<c:otherwise>
                          		<button class="btnNisal" style="margin: 0px;" id="changePassword" type="submit">修改</button>
                          	</c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
                <hr class='hr-double' />
              </div>
            </div>
          </div>
        </div>
      </section>
      
       <script>

      $("#changePassword").click(function(){
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();

        if(oldPassword === "" || newPassword === "" || confirmPassword === ""){
          layer.msg("请填写信息(There is an empty input box)");
        }
        if(newPassword !== confirmPassword){
          $("#confirmPasswordMsg").html("两次输入密码不同(Different password)");
        }
      });

      $("#confirmPassword").keyup(function(){
        chkPassword();
      });

      $("#newPassword").keyup(function(){
        chkPassword();
      });

      function chkPassword(){
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();
        if(newPassword !== confirmPassword){
          $("#confirmPasswordMsg").html("两次输入密码不同(Different password)");
        }else{
          $("#confirmPasswordMsg").html('<span style="color: #2d802d" class="fa fa-check"></span>');
        }
      }

    </script>
</body>
</html>