var TableManaged = function () {

    return {

        //main function to initiate the module
        init: function () {
            
            if (!jQuery().dataTable) {
                return;
            }
            //checkbox group
            jQuery('.model-list-table .group-checkable').change(function () {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                    } else {
                        $(this).attr("checked", false);
                    }
                });
                jQuery.uniform.update(set);
            });

            jQuery('.del-batch').click(function() {
                var set = $(".model-list-table .group-checkable").attr("data-set");
                var ids = new Array();
                $(set).each(function(){
                    if($(this).is(":checked")){
                        ids.push($(this).val());
                    }
                });

                if(ids.length > 0){
                    if(!confirm("确定要删除吗？")){
                        return;
                    }
                    var url = $(this).attr("tarUrl");
                    $.post(url, {ids:ids}, function (data) {
                        window.location.href = data;
                    });
                } else {
                    alert("请选择要删除的记录！");
                }

            });

            var oLanguage = {
                "sProcessing": "处理中...",
                "sZeroRecords": "没有匹配结果",
                "sEmptyTable": "无数据",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "sSearch": "搜索:",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                }
            };

            // news table
            $('#news-table').dataTable({
                "aoColumns": [
                    { "bSortable": false },
                    null,
                    null,
                    null,
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 4, "desc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0,5]
                    }
                ]
            });

            //news class table
            $('#news-class-table').dataTable({
                "aoColumns": [
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 0, "asc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
            });

            // article table
            $('#article-table').dataTable({
                "aoColumns": [
                    { "bSortable": false },
                    null,
                    null,
                    null,
                    null,
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 4, "desc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0,6]
                }
                ]
            });

            //article class table
            $('#article-class-table').dataTable({
                "aoColumns": [
                    null,
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 1, "desc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
            });

            //slideshow table
            $('#slideshow-table').dataTable({
                "aoColumns": [
                    { "bSortable": false },
                    null,
                    null,
                    null,
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 3, "desc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0,5]
                }
                ]
            });

            //comment table
            $('#comment-table').dataTable({
                "aoColumns": [
                    { "bSortable": false },
                    null,
                    null,
                    null,
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 4, "desc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0,5]
                }
                ]
            });

            //link table
            $('#link-table').dataTable({
                "aoColumns": [
                    { "bSortable": false },
                    null,
                    null,
                    { "bSortable": false }
                ],
                "order": [[ 1, "asc" ]],
                "aLengthMenu": [
                    [10, 15, 20, -1],
                    [10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "bFilter": false,
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": oLanguage,
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0,3]
                }
                ]
            });

        }

    };

}();