function goto(obj){
  $("html, body").animate({
    scrollTop: $(obj).offset().top
  }, 250);
}

function stuPicWatch(form, id, type, target){

  //type:  entry 入境  passport 护照  visa 签证

  $.ajax({
    type: "POST",
    url: "./studentMgr.html",
    data: {
      id : id,
      type: type,
      date: target
    },
    dataType: "json",
    success: function(data){
      $(form).find(".imger").attr("src", data.src);
    },
    error: function(jqXHR){
       layer.msg(jqXHR.status);
    },
  });
}

$(".foreignInfo").click(function(){
	  var id = $(this).find("input").val();
	  // {"country":"china","birthPlace":"Ningbo","address":"zj nb yz hx lgqhy20h","overseasPhone":"13245678911","acad":"primary","marital":"yes","brithday":"1997-01-25"}
	  $.ajax({
	    type: "POST",
	    url: "./studentMgr.html",
	    data: {
	    	'type':'findIdentity',
	    	'id' : id
	    },
	    success: function(data){
	      $("#country").html(data.nationality.cName);
	      $("#birthPlace").html(data.birthplace);
	      $("#address").html(data.homeAddress);
	      $("#overseasPhone").html(data.phone);
	      //$("#acad").html(data.educationOnUser.educationType.cName);
	      $("#marital").html(data.isMarried === 2 ? "未婚" : "已婚");
	      $("#birthday").html(data.birthday);
	      var index = layer.open({
	        type: 1,
	        anim: 0,
	        resize: false,
	        zIndex: 100,
	        area: ['50%', 'auto'], //宽高
	        content: $("#foreignInfo"),
	      });
	    },
	    error: function(jqXHR){
	       layer.msg("发生错误：" + jqXHR.status);
	    },
	  });
	});