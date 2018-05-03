$(function() {
    $(".nav-item").hover(function() {
        $(this).children("ul.child-nav").css("display","block");
    }, function() {
        $(this).children("ul.child-nav").css("display","none");
    });
});