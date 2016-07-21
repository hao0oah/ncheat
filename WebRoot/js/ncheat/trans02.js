/**
 * trans02.js
 */

$(function() {
    //表单
    var form = $("#queryForm");
    var validator = form.validate({
        errorPlacement: function(error, element) {
            $("#queryInfo").text($(error).text());
        },
        rules: {
        	multfile: {
                required: true
            }
        },
        messages: {
        	multfile: {
                required: "请选择上传文件"
            }
        }
    });
    
    // 导出按钮
    $("#querySubmit").button().click(function() {
        if (form.valid()) {
            $("#queryInfo").text("");
            reportFlow();
        }
    });

    // 重置按钮
    $("#queryReset").button().click(function() {
        validator.resetForm();
        $("#queryInfo").text("");
    });
    
    //点击输入框，隐藏错误提示消息
    $("input").focus(function(){
    	$("#queryInfo").text("");
    });

});


function reportFlow() {
	var type = $("#type").val();
	var start = $("#start").val();
	var end = $("#end").val();
	
	if(start == null || start.trim() == ""){
		$("#queryInfo").text("请输入数据开始行");
		return;
	}
	if(end == null || end.trim() == ""){
		$("#queryInfo").text("请输入数据结束行");
		return;
	}
	if(parseInt(start)>parseInt(end)){
		$("#queryInfo").text("数据开始行不可大于结束行");
		return;
	}

	$("#wait").show();
	$("#queryInfo").text("正在处理，请稍后...");
    $.ajaxFileUpload({
    	url: "fileupload",
    	fileElementId: "multfile",
        dataType: "json",
        data: {'type':type,'start':start,'end':end},
        success: function(data,status){
        	$("#wait").hide();
        	$("#queryInfo").text(data.msg);
        	if(data.suc){
        		window.open('filedown?filepath='+data.data);
        	}
        },
		error : function(data,status,e){
			$("#wait").hide();
			$("#queryInfo").text("系统错误["+e+"]，请联系开发人员！");
		}
    });
}
