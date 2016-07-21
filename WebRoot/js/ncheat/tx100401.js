var sel = null;			//临时存放“选择”的对象
var confirmFlag = false;//转账确认标志

$(function() {

	// 加载菜单
	$(".navBarDiv").load("navBar");
	
	var idtypes = {
		'01':'身份证',
		'02':'驾驶证',
		'03':'护照',
		'04':'军官证',
		'05':'士兵证',
		'06':'户口本',
		'07':'港澳居民往来内地通行证',
		'08':'警官证',
		'09':'社会保障号',
		'10':'台湾通报来往内地通行证',
		'11':'临时身份证',
		'12':'外国人居住证',
		'13':'组织机构代码(对公)',
		'14':'统一社会信用代码(对公)',
		'15':'营业执照号(对公)',
		'16':'(原)工商注册号',
		'99':'其他'
	};
	//加载证件类型
	$("#victimIDType").selectJsonData(idtypes);
	
	// 表单
	var applyForm = $("#applyInputForm");
	var validator = applyForm.validate({
		errorPlacement: function(error, element) {
            $("#queryInfo").text($(error).text());
        },
        rules: {
        	victimName: {
                required: true
            },
            victimPhoneNumber: {
                required: true
            },
            victimIDNumber: {
            	required: true
            }
        },
        messages: {
        	victimName: {
                required: "请输入举报人姓名"
            },
            victimPhoneNumber: {
                required: "请输入举报人电话"
            },
            victimIDNumber: {
            	required: "请输入举报人身份证号"
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
		$("#queryInfo").text("");
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
	            	var mval = $("input:radio[name='flow']:checked").next("input[name='mval']").val();

	            	//如果没选数据
	            	if(mval == null || mval == ""){
	            		alert("请选择一条数据！");
	            		return;
	            	}
	            	var mm = JSON.parse(mval);
	            	sel.find("input[name='mtransflow']").val(mm.id);
	            	sel.find("input[name='mtransdate']").val(dateChange(mm.time.substring(0,8)));
	            	sel.find("input[name='mtype']").val(mm.type);
	            	sel.find("input[name='mamount']").val(mm.transferAmount);
	            	sel.find("input[name='mpayname']").val(mm.transferOutAccountName);
	            	sel.find("input[name='mpayno']").val(mm.transferOutAccountNumber);
	            	sel.find("input[name='mpeybank']").val(mm.transferInBankName);
	            	sel.find("input[name='mpeyname']").val(mm.transferInAccountName);
	            	sel.find("input[name='mpeyno']").val(mm.transferInAccountNumber);
	            	sel.find("input[name='mhide']").val(mval);
	            	
	            	//TODO: 如果已经添加过该流水，需要给出不能重复添加提示？
	            	$(this).dialog("close");
	            }
	        },
	        open: function(event, ui) {
	        	// 初始化数据
	    	    var table = $("#flowbody");
	    	    table.empty();
	    	    $("#queryFlowDiv input[type='text']").val("");
	        },
	        close: function(event, ui) {
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
		url  : "listTrans",
		data : JSON.stringify(data),
		dataType : "JSON",
		contentType : "application/json",
		success : selectFlowCallback,
		error : function(){
			$("#wait2").hide();
			$(".pageBtn").removeAttr("disabled");
		    $("#searchFlow").removeAttr("disabled");
			alert("查询交易流水出错！");
		}
	});
	$("#wait2").show();
    $(".pageBtn").attr("disabled", "true");
    $("#searchFlow").attr("disabled", "true");
    
}


// 查询流水 反调函数
function selectFlowCallback(data){
	$("#wait2").hide();
	$(".pageBtn").removeAttr("disabled");
	$("#searchFlow").removeAttr("disabled");
	
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
	        
	        var id = item.id;
	        var time = item.time;
	        var type = item.type;
	        //var currency = item.currency;
	        var transferAmount = item.transferAmount;
	        var transferOutAccountName = item.transferOutAccountName;
	        var transferOutAccountNumber = item.transferOutAccountNumber;
	        var transferInAccountName = item.transferInAccountName;
	        var transferInAccountNumber = item.transferInAccountNumber;
	        
	        var col = "<input type='radio' name='flow'><input type='hidden' name='mval' value='"+JSON.stringify(item)+"'>";
			var dataArray = new Array(col,id,time,type,transferAmount,transferOutAccountName,transferOutAccountNumber,transferInAccountName,transferInAccountNumber);
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
	$("#queryInfo").text("");
	var len = $("#itemTbody tr").length;
	if(len>=10){
		alert("最大可填写10条记录");
		return;
	}
	$("#itemTbody").append(
			"<tr><td><input type='text' name='mtransflow' placeholder='选择或填写' onclick='selectFlow(this);'></td>" +
			"<td><input type='text' name='mtransdate' onclick='WdatePicker();'></td>" +
			"<td><input type='text' name='mtype'></td>" +
			"<td><input type='text' name='mamount'></td>" +
			"<td><input type='text' name='mpayname'></td>" +
			"<td><input type='text' name='mpayno'></td>" +
			"<td><input type='text' name='mpeybank'></td>" +
			"<td><input type='text' name='mpeyname'></td>" +
			"<td><input type='text' name='mpeyno'></td>" +
			"<td><img alt='删除' src='image/cancel.png' onclick='delRow(this);' title='删除'/><input type='hidden' name='mhide'></td></tr>");

}


//删除一行数据
function delRow(obj){
	$(obj).closest("tr").remove();
}


//案件举报 提交表单
function submitApplyInputForm() {

	var data = $("#applyInputForm").serializeJson();
	data.transactionList = tableToJson();
	if(data.transactionList.length==0){
		$("#queryInfo").text("请填写或选择交易信息");
		return;
	}
	
	$.ajax({
		type : "POST",
		url  : "reportCase",
		dataType : "JSON",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : confirmCallback,
		error : function(){
			$("#applyBtn").removeAttr("disabled");
			$("#wait").hide();
			$("#queryInfo").text("提交案件出错");
		}
	});
	$("#applyBtn").attr("disabled", "true");
	$("#wait").show();
	$("#queryInfo").text("正在提交...");
}


function confirmCallback(data){
	$("#applyBtn").removeAttr("disabled");
	$("#wait").hide();
	
	if(data.suc){
		alert(data.msg);
		//初始化所有数据
		$("#resetBtn").click();
	}else{
		$("#queryInfo").text(data.msg);
	}
}


//将table中的数据转成json数组
function tableToJson(){
	var trs = $("#itemTbody").find("tr");
	var trans = new Array();
	
	for(var i=0; i<trs.length;i++){
		var item = {};
		var mhide = $(trs[i]).find("input[name='mhide']").val();
		if(mhide!=null && mhide!=''){
			item = JSON.parse(mhide);
		}
		
		item.id=$(trs[i]).find("input[name='mtransflow']").val();
		var ttime=$(trs[i]).find("input[name='mtransdate']").val();
		item.time = ttime.replace(/\-/g,"").rpad(14,"0");
		item.type=$(trs[i]).find("input[name='mtype']").val(); 
		item.currency=(item.currency==null||item.currency=="")?"CNY":item.currency;
		item.transferAmount=$(trs[i]).find("input[name='mamount']").val();
		item.transferOutAccountName=$(trs[i]).find("input[name='mpayname']").val();
		item.transferOutAccountNumber=$(trs[i]).find("input[name='mpayno']").val();
		item.transferInBankName=$(trs[i]).find("input[name='mpeybank']").val();
		item.transferInAccountName= $(trs[i]).find("input[name='mpeyname']").val();
		item.transferInAccountNumber= $(trs[i]).find("input[name='mpeyno']").val();

		trans[i] = item;
	}
	
	return trans;
}


function dateChange(value){
	var regex = "-";
	if(value.match(regex)){
		return value.replace(new RegExp(regex,"gm"),"");
	}else{
		return value.substr(0,4)+"-"+value.substr(4,2)+"-"+value.substr(6,2);
	}
}

