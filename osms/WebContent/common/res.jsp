<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="${pageContext.request.contextPath}/assets/css/bootstrap/bootstrap.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/bootstrap/bootstrap-responsive.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/jquery_ui/jquery-ui-1.10.0.custom.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap_switch/bootstrap-switch.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/xeditable/bootstrap-editable.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/select2/select2.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/datatables/bootstrap-datatable.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap_datetimepicker/bootstrap-datetimepicker.min.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/bootstrap_daterangepicker/bootstrap-daterangepicker.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/plugins/slider_nav/slidernav.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/style.css" id="color-settings-body-color" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/assets/css/update.css" media="all" rel="stylesheet" type="text/css" />
  
  


</head>
<body>


  <script src="${pageContext.request.contextPath}/assets/js/jquery/jquery.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/mobile_events/jquery.mobile-events.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-migrate.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/jquery_ui/jquery-ui.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/datatables/jquery.dataTables.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/datatables/jquery.dataTables.columnFilter.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/common/wysihtml5.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/common/bootstrap-wysihtml5.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/select2/select2.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap_colorpicker/bootstrap-colorpicker.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/mention/mention.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/modernizr/modernizr.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/fileupload/tmpl.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/fileupload/load-image.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/fileupload/canvas-to-blob.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/fileupload/jquery.iframe-transport.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/timeago/jquery.timeago.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/autosize/jquery.autosize-min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/charCount/charCount.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/naked_password/naked_password-0.2.4.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/nestable/jquery.nestable.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/tabdrop/bootstrap-tabdrop.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap_datetimepicker/bootstrap-datetimepicker.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap_daterangepicker/moment.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap_daterangepicker/bootstrap-daterangepicker.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/bootstrap_maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/plugins/slider_nav/slidernav-min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/nav.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/tables.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/theme.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/assets/js/layer/layer.js" type="text/javascript"></script>
  
</body>
</html>