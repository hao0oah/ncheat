var sel = {};

$(function() {
	
	//加载菜单
	$(".navBarDiv").load("navBar");
	
	$.ajax({
		type : "POST",
		url  : "tasklist",
		dataType : "JSON",
		contentType : "application/json",
		success : selectFlowCallback,
		error : function(){
			alert("查询定时任务出错！");
		}
	});
	
	// 重置操作
	$("#resetBtn").button().click(function() {
		validator.resetForm();
		$("#itemTbody").empty();
	});
	
	$(".switch-input").change(function(){
		var type = false;
		var check = $(".switch-input").attr("checked");
		if(check == 'checked'){
			type = true;
		}
		showWaitMsg();
		$.ajax({
			async : false,
			dataType : "JSON",
			cache : false,
			url : "changeType",
			data : {
				type : type
			},
			success : function(data) {
				hideWaitMsg();
				if (data.suc) {
					location.reload();
				} else {
					alert(data.msg);
				}
			}
		});

	});

})


// 查询流水 反调函数
function selectFlowCallback(data){
	if(data.suc){
		var debug = data.data.debugType;
		if(debug){
			$(".switch-input").attr("checked","checked");
			$("#runType").text("手工模式");
		} else {
			$(".switch-input").removeAttr("checked");
			$("#runType").text("自动模式");
		}
		
		
		var list = data.data.tasklist;
		
		var table = $("#flowbody");
		table.empty();
		
		$(list).each(function(index) {
	        var item = list[index];

	        var jobName = item.jobName;
	        var jobStatus = (item.jobStatus==1?"<span style='color:green;'>运行</span>":"<span style='color:red;'>停止</span>");
	        var cron = item.cronExpression;
	        var desc = item.description;
	        var isConcurrent = (item.isConcurrent==1?'同步':'不同步');
	        var springId = item.springId;
	        var methodName = item.methodName;
	        var params = item.params;
	        
	        var op;
	        if(item.jobStatus==1){
	        	op = "<a href='#' onclick=\"changeJobStatus('"+item.jobId+"','"+"stop"+"')\">停止</a>";
	        }else{
	        	op = "<a href='#' onclick=\"changeJobStatus('"+item.jobId+"','"+"start"+"')\">启动</a>";
	        }
	        op+= "&nbsp;&nbsp;&nbsp;&nbsp;<a href='#'  onclick=\"updateCron('"+item.jobId+"','"+cron+"','"+params+"')\">更新</a>";
	        op+= "&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' style='width:auto;overflow:visible;' value='立即执行' onclick=\"runnow(this,'"+methodName+"','"+params+"')\"/>";
	        
			var dataArray = new Array(jobName,jobStatus,cron,desc,isConcurrent,springId,methodName,params,op);
	        var row = showDateInRow(dataArray);
	        row.css("background", "#eeeeee");
	        row.appendTo(table);
	    });
	}else{
		alert(data.msg);
	}
}

function validateAdd() {
	if ($.trim($('#jobName').val()) == '') {
		alert('name不能为空！');
		$('#jobName').focus();
		return false;
	}
	if ($.trim($('#jobGroup').val()) == '') {
		alert('group不能为空！');
		$('#jobGroup').focus();
		return false;
	}
	if ($.trim($('#cronExpression').val()) == '') {
		alert('cron表达式不能为空！');
		$('#cronExpression').focus();
		return false;
	}
	if ($.trim($('#beanClass').val()) == '' && $.trim($('#springId').val()) == '') {
		$('#beanClass').focus();
		alert('类路径和spring id至少填写一个');
		return false;
	}
	if ($.trim($('#methodName').val()) == '') {
		$('#methodName').focus();
		alert('方法名不能为空！');
		return false;
	}
	return true;
}

function add() {
	if (validateAdd()) {
		showWaitMsg();
		$.ajax({
			type : "POST",
			async : false,
			dataType : "JSON",
			cache : false,
			url : "taskadd",
			data : $("#addForm").serialize(),
			success : function(data) {
				hideWaitMsg();
				if (data.suc) {

					location.reload();
				} else {
					alert(data.msg);
				}

			}//end-callback
		});//end-ajax
	}
}

function changeJobStatus(jobId,cmd) {

	showWaitMsg();
	$.ajax({
		type : "POST",
		async : false,
		dataType : "JSON",
		cache : false,
		url : "changeJobStatus",
		data : {
			jobId : jobId,
			cmd : cmd
		},
		success : function(data) {
			hideWaitMsg();
			if (data.suc) {
				location.reload();
			} else {
				alert(data.msg);
			}

		}//end-callback
	});//end-ajax
}

function updateCron(jobId,cron,param) {
	sel.jobId = jobId;
	sel.cron = cron;
	sel.param = param;
	
	// 更新对话框
    $("#updateDiv").dialog({
        autoOpen: false,
        title: "更新定时任务对话框",
        width: 400,
        height: 240,
        modal: true,
        buttons: {
        	"取消":function(){
        		$(this).dialog("close");
        	},
        	"更新": function() {
        		var cron = $("#cron").val();
        		var param = $("#param").val();
        		
        		showWaitMsg();
        		
				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : "updateCron",
					data : {
						jobId : sel.jobId,
						cron : cron,
						param : param
					},
					success : function(data) {
						hideWaitMsg();
						if (data.suc) {
							location.reload();
						} else {
							alert(data.msg);
						}
					}
				});
            	$(this).dialog("close");
            }
        },
        open: function(event, ui) {
        	$("#cron").val(sel.cron=='null'?'':sel.cron);
        	$("#param").val(sel.param=='null'?'':sel.param);
        	//$("#jobid").val(item.jobId);
        },
        close: function(event, ui) {
        	$("#updateDiv").hide();
        	sel = {};
        }
    });
	$("#updateDiv").dialog("open");
}

function runnow(btn,method,param){
	
	$.ajax({
		type : "POST",
		dataType : "JSON",
		cache : false,
		url : "runJust",
		data : {
			method : method,
			param : param=="null"?"{}":param
		},
		success : function(data) {
			if(!data.suc){
				alert(data.msg);
			}
		}
	});
	
	
	$(btn).attr("disabled", "true");
	var endline = 1*61;
	var i = 1;
	var interv = window.setInterval(function(){
    	if(i==endline){
    		$(btn).val("立即执行");
    		$(btn).removeAttr("disabled");
    		window.clearInterval(interv);
    	}else{
    		var min = parseInt((endline-i)/60);
    		min = min<10?("0"+min):min;
    		var sec = (endline-i)%60;
    		sec = sec<10?("0"+sec):sec;
    		$(btn).val("("+min+":"+sec+")后可用");
    	}
    	i++;
    }, 1000);

}


function showWaitMsg(msg) {
	if (msg) {

	} else {
		msg = '正在处理，请稍候...';
	}
	var panelContainer = $("body");
	$("<div id='msg-background' class='datagrid-mask' style=\"display:block;z-index:10006;\"></div>").appendTo(panelContainer);
	var msgDiv = $("<div id='msg-board' class='datagrid-mask-msg' style=\"display:block;z-index:10007;left:50%\"></div>").html(msg).appendTo(panelContainer);
	msgDiv.css("marginLeft", -msgDiv.outerWidth() / 2);
}

function hideWaitMsg() {
	$('.datagrid-mask').remove();
	$('.datagrid-mask-msg').remove();
}