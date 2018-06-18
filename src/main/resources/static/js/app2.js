$(function() {
    $(".nav-item").hover(function() {
        $(this).children("ul.child-nav").css("display","block");
    }, function() {
        $(this).children("ul.child-nav").css("display","none");
    });
});

function submitExamCheck(count) {
    var radios = $('.userAns:radio:checked');
    if(radios.size() !== count) {
        alert('题目不能留空！');
        return;
    }

    var ansArr = [];
    radios.each(function(index,dom){
        ansArr.push($(dom).val());
    });

    $('#userAns').val(ansArr.join(','));
    $('#echeckForm').submit();
}