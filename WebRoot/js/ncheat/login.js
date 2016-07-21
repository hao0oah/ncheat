/**
 * login.js
 */
$(function() {
    // 表单校验
    // 密码登录表单
    var passwordForm = $("#passwordLoginForm");
    var passwordValidator = passwordForm.validate({
        errorPlacement: function(error, element) {
            $("div#passwordLoginInfo").text($(error).text());
        },
        rules: {
            userName: {
                required: true,
                rangelength: [1, 10]
            },
            password: {
                required: true,
                rangelength: [1, 15]
            }
        },
        messages: {
            userName: {
                required: "请输入柜员号",
                rangelength: "柜员号字段长度必须介于{0}和{1}之间"
            },
            password: {
                required: "请输入密码",
                rangelength: "密码字段长度必须介于{0}和{1}之间"
            }
        },
        
        // 提交表单
        submitHandler: function(form) {
            $.ajax({
                type: "POST",
                async : false,
                url: "login",
                dataType: "JSON",
                data: $(form).serializeJson(),
                //contentType : "application/json",//如果设置成这样，在后台 getParamter()和@RequestParam 都获取不到数据
                success: function(data){
                	if(data.suc){
                		location.href="./index";
                	}else{
                		$("div#passwordLoginInfo").text(data.msg);
                		$("#passwordLoginSubmit").attr("disabled", false);
                	}
                },
        		error : function(){
        			alert("登陆出错！");
        			$("#passwordLoginSubmit").attr("disabled", false);
        		}
            });
            //form.submit();
        }
    });
 
    $("#loginTabs").tabs();
    
    // 回车登录
    $(document).keyup(function(event){
    	if(event.keyCode != 13)
    		return;

    	var selectedLi = $("#loginTabs").find("li[aria-selected='true']");
    	switch(selectedLi.attr("aria-controls")){
    	case "passwordTab":			//密码登录
    		if (passwordForm.valid()) {
    			$("#passwordLoginSubmit").attr("disabled", true);
    			$("#passwordLoginInfo").text("");
    			passwordForm.submit();
    	    }
    		break;
    	}
    });

    // 密码页面
    // 登录按钮
    $("#passwordLoginSubmit").button().click(function() {
    	makeEnterEvent();
    });
    
    // 重置按钮
    $("#passwordLoginReset").button().click(function() {
    	$("#passwordLoginInfo").text("");
    	$("#passwordLoginSubmit").attr("disabled", false);
        passwordForm[0].reset();
    });
    
    //点击输入框，隐藏错误提示消息
    $("input").focus(function(){
    	$("#passwordLoginInfo").text("");
    });

});

//模拟一个键盘“回车”事件
function makeEnterEvent(){
	var e = jQuery.Event("keyup");
    e.keyCode =13;					//keyCode=13是回车
    $(document).trigger(e);
}

//输入框输完回车换行
function handleEnter (field, event) {
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if (keyCode == 13) {
		var i=0;
		for (; i < field.form.elements.length; i++){
			if (field == field.form.elements[i]) break;
		}

		i = (i + 1) % field.form.elements.length;
		field.form.elements[i].focus();
		return false;
	} else return true;
}
