/**
 * trans01.js
 */

$(function() {
	
	//加载菜单
	$(".navBarDiv").load("navBar");
	
    //表单
    var form = $("#queryForm");
    
    var validator = form.validate({
        errorPlacement: function(error, element) {
            $("#queryInfo").text($(error).text());
        },
        rules: {
        	password0: {
                required: true
            },
            password1: {
                required: true
            }
        },
        messages: {
        	password0: {
                required: "请输入原密码"
            },
            password1: {
                required: "请输入新密码"
            }
        }
    });
    
    // 修改按钮
    $("#querySubmit").button().click(function() {
        if (form.valid()) {
            $("#queryInfo").text("");
            chgpwd();
        }
    });

    // 重置按钮
    $("#queryReset").button().click(function() {
        validator.resetForm();
        $("#queryInfo").text("");
    });
    
    // 点击输入框，隐藏错误提示消息
    $("input").focus(function(){
    	$("#queryInfo").text("");
    });

});


//修改密码
function chgpwd() {
    var data = $("#queryForm").serializeJson();
    
    $("#wait").show();
    $("#queryInfo").text("正在处理，请稍后...");
    $.ajax({
        type: "POST",
        url: "changePwd",
        dataType: "JSON",
        data: JSON.stringify(data),
        contentType : "application/json",
        success: function(data){
        	alert(data.msg);
        	$("#wait").hide();
        	if(data.suc){
        		$("#queryReset").click();
        	}else{
        		$("#queryInfo").text(data.msg);
        	}
        },
		error : function(){
			$("#wait").hide();
			$("#queryInfo").text("系统错误，请联系开发人员！");
		}
    });
}
