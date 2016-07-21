var sel = null;			//临时存放“选择”的对象

$(function() {

	//加载菜单
	$(".navBarDiv").load("navBar");

	// 表单
	var applyForm = $("#applyInputForm");
	var validator = applyForm.validate({
		errorPlacement: function(error, element) {
            $("#queryInfo").text($(error).text());
        },
        rules: {
        	AccountName: {
                required: true
            },
            cardNumber: {
                required: true
            }
        },
        messages: {
        	AccountName: {
                required: "请输入账户名称"
            },
            cardNumber: {
                required: "请输入账户号"
            }
        },
		submitHandler : function(applyForm) {
			submitApplyInputForm();
		}
	});
	
    // 分页按钮
    // 首页按钮
    $("#fristPage").click(function() {
        queryFlow(0);
    });
    // 上一页按钮
    $("#previous").click(function() {
        var pageSize = 5; // 分页大小
        var currentPage = $("#curentPage").text(); // 当前页
        var begin = pageSize * (currentPage - 0 - 2);
        if (currentPage - 1 > 0) {
            queryFlow(begin);
        } else {
            $(this).attr("disabled", "true");
        }
    });
    // 下一页按钮
    $("#next").click(function() {
        var pageSize = 5; // 分页大小
        var totalPage = $("#totalPage").text();
        var currentPage = $("#curentPage").text(); // 当前页
        var begin = pageSize * (currentPage - 0);
        if (totalPage - currentPage > 0) {
            queryFlow(begin);
        } else {
            $(this).attr("disabled", "true");
        }
    });
    // 尾页按钮
    $("#lastPage").click(function() {
        var pageSize = 5; // 分页大小
        var totalPage = $("#totalPage").text(); // 总页数
        var begin = (totalPage - 1) * pageSize;
        queryFlow(begin);
    });
    
    
	// 重置操作
	$("#resetBtn").button().click(function() {
		validator.resetForm();
		$("#itemTbody").empty();
	});

	//提交操作
	$("#applyBtn").button().click(function() {
        // 提交表单
        if (applyForm.valid()) {
            applyForm.submit();
        }
	});
	
    //查询按钮点击
    $("#searchFlow").button().click(function(){
    	queryFlow(0);
    });
    
    // 鼠标移过Table整行变色，点击选择该行
    $("#flowbody tr").live({
    	mouseover:function(){
    		$(this).css("background-color","#c0c0c0");},
    	mouseout:function(){
    		$(this).css("background-color","#eeeeee");},
    	click:function(){
    		$(this).find(":radio").attr("checked","checked");
    	}
    });
    
    //用于防止readonly输入框按backspace返回上一页
	$("input[readOnly]").keydown(function(e) {
		e.preventDefault();
	});
    
	//点击输入框，隐藏错误提示消息
    $("input").focus(function(){
    	$("#queryInfo").text("");
    });

});

//点击交易流水输入框事件
function selectFlow(td){
	var trs = $(td).val();
	
	//如果输入框有值，则不可再选择流水
	if(trs!=null && trs.trim()!=""){
		return;
	}else{
		sel = $(td).closest("tr");
		// 查询流水对话框
	    $("#selectFlowDiv").dialog({
	        autoOpen: false,
	        title: "查询流水对话框",
	        width: 1000,
	        height: 500,
	        modal: true,
	        buttons: {
	        	"手工输入":function(){
	        		sel.find("input")[0].focus();
	        		$(this).dialog("close");
	        	},
	        	"确认选择": function() {
	            	//被选的那一行表格记录
	            	var mval = $("input:radio[name='flow']:checked").closest("tr").find("input[name='mval']").val();
	
	            	//如果没选数据
	            	if(mval == null || mval == ""){
	            		alert("请选择一条数据！");
	            		return;
	            	}
	            	var mm = JSON.parse(mval);
	            	sel.find("input[name='mtransflow']").val(mm.transactionSerial);
	            	sel.find("input[name='mtransdate']").val(mm.transactionTime);
	            	sel.find("input[name='mtype']").val(mm.transactionType);
	            	sel.find("input[name='mamount']").val(mm.transactionAmount);
	            	sel.find("input[name='mpayno']").val($("#cardNumber").val());
	            	sel.find("input[name='mpeyno']").val(mm.opponentAccountNumber);
	            	//TODO: 如果已经添加过该流水，则给出不能重复添加提示
	
	            	//关闭对话框
	            	$(this).dialog("close");
	            }
	        },
	        open: function(event, ui) {
	        	// 初始化数据
	    	    var table = $("#flowbody");
	    	    table.empty();
	    	    $("#queryFlowDiv input[type='text']").val("");
	    	    
	//    	    $("#input:radio[name='check']").removeAttr("checked");
	//        	$("#waitcheckDiv").show();
	        },
	        close: function(event, ui) {
	//        	$("#selectFlowDiv").hide();
	        	$("#flowtableDiv").hide();
	        	sel = null;
	        }
	    });
		$("#selectFlowDiv").dialog("open");
	}
}

//流水查询
function queryFlow(begin) {
	var data = {};
    data.begin = begin;
    data.limit = 5;
    
	var transflow = $("#stransflow").val().trim();
	//如果有流水号，只查询流水号就可以
	if(transflow != null && transflow != ''){
		data.transflow = transflow;
	}
	//没有流水号，应包括收款/付款账号、日期、金额中的几个
	var transdate = $("#stransdate").val();
	if(transdate != null && transdate != ''){
		data.transdate = dateChange(transdate);
	}else{
		alert("查询条件应包含交易日期");
		return;
	}
	var payno = $("#spayno").val().trim();
	var peyno = $("#speyno").val().trim();
	if((payno!=null&&payno!='')||(peyno!=null&&peyno!='')){
		data.payno = payno;
		data.peyno = peyno;
	}else{
		alert("查询条件应包含收款账号或付款账号");
		return;
	}
	var amount = $("#samount").val();
	data.amount = amount;

	$.ajax({
		type : "POST",
		url  : "list405Trans",
		data : JSON.stringify(data),
		dataType : "JSON",
		contentType : "application/json",
		success : selectFlowCallback,
		error : function(){
			$(".pageBtn").removeAttr("disabled");
			alert("查询交易流水出错！");
		}
	});
    $("#wait2").show();
    $(".pageBtn").attr("disabled", "true");
    $("#searchFlow").attr("disabled", "true");
}


// 查询流水 反调函数
function selectFlowCallback(data){
	$(".pageBtn").removeAttr("disabled");
	$("#searchFlow").removeAttr("disabled");
	$("#wait2").hide();
	
	if(data.suc){
		$("#flowtableDiv").show();
	    var retrunData = data.data;
	    var totalPage = retrunData.totalPage;
	    var currentPage = retrunData.currentPage;
	    $("#totalPage").text(totalPage);
	    $("#curentPage").text(currentPage);
	    
		var list = retrunData.beanList;
		var table = $("#flowbody");
		table.empty();
		
		$(list).each(function(index) {
	        var item = list[index];
	        
	        var id = item.transactionSerial;
	        var time = item.transactionTime;
	        var type = item.transactionType;
	        var transferAmount = item.transactionAmount;
	        var transferInAccountNumber = item.opponentAccountNumber;
	        
	        var col = "<input type='radio' name='flow'><input type='hidden' name='mval' value='"+JSON.stringify(item)+"'>";
			var dataArray = new Array(col,id,time,type,transferAmount,transferInAccountNumber);
	        var row = showDateInRow(dataArray);
	        row.css("background", "#eeeeee");
	        row.appendTo(table);
	    });
	}else{
		alert(data.msg);
	}
}


//添加一行表格
function addRow(){
	var len = $("#itemTbody tr").length;
	if(len>=1){
		alert("只可填写1条记录");
		return;
	}
	$("#itemTbody").append(
			"<tr><td><input type='text' name='mtransflow' placeholder='选择或填写' onclick='selectFlow(this);'></td>" +
			"<td><input type='text' name='mtransdate' onclick='WdatePicker();'></td>" +
			"<td><input type='text' name='mtype'></td>" +
			"<td><input type='text' name='mamount'></td>" +
			"<td><input type='text' name='mpayno'></td>" +
			"<td><input type='text' name='mpeyno'></td>" +
			"<td><img alt='删除' src='image/cancel.png' onclick='delRow(this);' title='删除'/></td></tr>");
}


//删除一行数据
function delRow(obj){
	$(obj).closest("tr").remove();
}


//案件举报 提交表单
function submitApplyInputForm() {
	var data = $("#applyInputForm").serializeJson();

	
	$.ajax({
		type : "POST",
		url  : "report405Case",
		dataType : "JSON",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : confirmCallback,
		error : function(){
			$("#applyBtn").removeAttr("disabled");
			$("#wait").hide();
			$("#queryInfo").text("");
			alert("提交案件出错！");
		}
	});
	// 禁用收款按钮，防止重复点击
	$("#applyBtn").attr("disabled", "true");
	$("#wait").show();
	$("#queryInfo").text("正在提交...");
}

function confirmCallback(data){
	$("#applyBtn").removeAttr("disabled");
	$("#wait").hide();
	$("#queryInfo").text("");
	alert(data.msg);
	if(data.suc){
		$("#resetBtn").click();
	}
}


function dateChange(value){
	var regex = "-";
	if(value.match(regex)){
		return value.replace(new RegExp(regex,"gm"),"");
	}else{
		return value.substr(0,4)+"-"+value.substr(4,2)+"-"+value.substr(6,2);
	}
}

// 	校验只能输入数字
$.validator.addMethod("onlyNumber", function(value, elements) {
    if (value == null) {
        return true;
    }
    var regex = "^[1-9]\d*$";
    value = $.trim(value); // 去除前后的空格
    var r = value.match(regex);
    return r != null;
}, "只能输入数字");
