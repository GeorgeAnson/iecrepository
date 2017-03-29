function goto(obj){
  $("html, body").animate({
    scrollTop: $(obj).offset().top
  }, 250);
}
