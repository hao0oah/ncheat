/**
 * trans01.js
 */

$(function() {
    //表单
    var form = $("#queryForm");
    var validator = form.validate({
        errorPlacement: function(error, element) {
            $("#queryInfo").text($(error).text());
        },
        rules: {
        	account: {
                required: true
            }
        },
        messages: {
        	account: {
                required: "请输入账号"
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
    var data = $("#queryForm").serializeJson();
//    data.transDate = data.transDate.replace(/\-/g,"");//替换所有的'-'
    $("#wait").show();
    $("#queryInfo").text("正在处理，请稍后...");
    $.ajax({
        type: "POST",
        url: "transReport",
        dataType: "JSON",
        data: JSON.stringify(data),
        contentType : "application/json",
        success: function(data){
        	$("#wait").hide();
        	$("#queryInfo").text(data.msg);
        	if(data.suc){
        		window.open('filedown?filepath='+data.data);
        	}
        },
		error : function(){
			$("#wait").hide();
			$("#queryInfo").text("系统错误，请联系开发人员！");
		}
    });
}
