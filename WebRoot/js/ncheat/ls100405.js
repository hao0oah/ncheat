/**
 * ls100405.js
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
        	transDate: {
                required: true
            },
            transType: {
                required: true
            }
        },
        messages: {
        	transDate: {
                required: "请选择交易日期"
            },
            transType: {
                required: "请选择事件类型"
            }
        }
    });
    
    //点击输入框，隐藏错误提示消息
    $("input,select").focus(function(){
    	$("#queryInfo").text("");
    });
    $("select").change(function(){
    	if($(this).val()!=null||$(this).val()!="")
    		$("#queryInfo").text("");
    });
    
    // 查询按钮
    $("#querySubmit").button().click(function() {
    	// 校验必填项是否填写
    	if (!form.valid()) return;
        // 提交查询
        queryPreFlow(0);
    });
    

    // 提交按钮
    $("#btnSubmit").button().click(function(){
    	 if (form.valid()) {
             var data = $("#queryForm").serializeJson();
             data.transDate = data.transDate.replace(/\-/g,"");
             
         	$.ajax({
                 type: "POST",
                 url: "optReport",
                 dataType: "JSON",
                 data: JSON.stringify(data),
                 contentType : "application/json",
                 success: function(data){
                	 $("#btnSubmit").removeAttr("disabled");
                	 $("#wait").hide();
                	 $("#queryInfo").text("");
                 	alert(data.msg);
                 	if(data.suc){
                 		init();
                 	}
                 },
         		error : function(){
         			$("#btnSubmit").removeAttr("disabled");
         			$("#wait").hide();
                 	$("#queryInfo").text("");
         			alert("提交出错！");
         		}
             });
         	$("#btnSubmit").attr("disabled", "true");
         	$("#wait").show();
         	$("#queryInfo").text("正在提交，请稍后...");
             }
        });


    //上报按钮
    $("#upall").button().click(function(){
    	 if (form.valid()) {
             
             var data = $("#queryForm").serializeJson();
             data.transDate = data.transDate.replace(/\-/g,"");
             
         	$.ajax({
                 type: "POST",
                 url: "batchReport",
                 dataType: "JSON",
                 data: JSON.stringify(data),
                 contentType : "application/json",
                 success: function(data){
                	 $("#upall").removeAttr("disabled");
                	 $("#wait").hide();
                	 $("#queryInfo").text("");
                 	alert(data.msg);
                 	if(data.suc){
                 		init();
                 	}
                 },
         		error : function(){
         			$("#upall").removeAttr("disabled");
         			$("#wait").hide();
                	$("#queryInfo").text("");
         			alert("上报出错！");
         		}
             });
         	$("#upall").attr("disabled", "true");
         	$("#wait").show();
        	$("#queryInfo").text("正在上报");
         }
    });
    

    // 重置按钮
    $("#queryReset").button().click(function() {
        validator.resetForm();
        $("#queryInfo").text("");
        $("#queryResult").hide();
    	$("#detailDialog").hide();
    	$("#wait").hide();
    });

    // 分页按钮
    // 首页按钮
    $("#fristPage").click(function() {
    	// 校验必填项是否填写
    	if (!form.valid()) return;
        queryPreFlow(0);
    });

    // 上一页按钮
    $("#previous").click(function() {
    	// 校验必填项是否填写
    	if (!form.valid()) return;
    	
        var pageSize = 10; // 分页大小
        var currentPage = $("#curentPage").text(); // 当前页
        var begin = pageSize * (currentPage - 0 - 2);

        if (currentPage - 1 > 0) {
            queryPreFlow(begin);
        } else {
            $(this).attr("disabled", "true");
        }
    });

    // 下一页按钮
    $("#next").click(function() {
    	// 校验必填项是否填写
    	if (!form.valid()) return;
    	
        var pageSize = 10; // 分页大小
        var totalPage = $("#totalPage").text();
        var currentPage = $("#curentPage").text(); // 当前页
        var begin = pageSize * (currentPage - 0);

        if (totalPage - currentPage > 0) {
            queryPreFlow(begin);
        } else {
            $(this).attr("disabled", "true");
        }
    });

    // 尾页按钮
    $("#lastPage").click(function() {
    	// 校验必填项是否填写
    	if (!form.valid()) return;
    	
        var pageSize = 10; // 分页大小
        var totalPage = $("#totalPage").text(); // 总页数

        var begin = (totalPage - 1) * pageSize;
        queryPreFlow(begin);
    });
    
    init();
});

// 初始化页面
function init() {
	$("#queryReset").click();
}

// 查询Item
function queryPreFlow(begin) {

    var limit = 10;
    var data = $("#queryForm").serializeJson();
    data.transDate = data.transDate.replace(/\-/g,"");
    data.begin = begin;
    data.limit = limit;

    $.ajax({
        type: "POST",
        url: "listItems",
        dataType: "JSON",
        data: JSON.stringify(data),
        contentType : "application/json",
        success: queryPreFlowCallBack,
		error : function(){
			$("#wait").hide();
			$("#queryInfo").text("");
			alert("查询出错！");
			$(".pageBtn").removeAttr("disabled");
		    $("#querySubmit").removeAttr("disabled");
		}
    });
    $(".pageBtn").attr("disabled", "true");
    $("#querySubmit").attr("disabled", "true");
 	$("#wait").show();
	$("#queryInfo").text("正在查询...");
}

//查询Item回掉函数
function queryPreFlowCallBack(data) {
	$(".pageBtn").removeAttr("disabled");
    $("#querySubmit").removeAttr("disabled");
 	$("#wait").hide();
 	$("#queryInfo").text("");
 	
    if (!data.suc) {
        $("#queryInfo").text(data.msg);
        $("#queryResult").css("display","none");
        return false;
    }

    var retrunData = data.data;
    var totalPage = retrunData.totalPage;
    var currentPage = retrunData.currentPage;
    $("#totalPage").text(totalPage);
    $("#curentPage").text(currentPage);

    // 展示数据
    var table = $("#resultList");
    table.empty();
    
    var dataList = retrunData.beanList;
    if(dataList.length==0){
    	$("#queryInfo").text("没有查到记录");
    	return false;
    }
    
	$("#queryResult").css("display","block");
    
    $(dataList).each(function(index) {
        var item = dataList[index];
        
        var accountName = item.accountName;
        var accountNumber = item.accountNumber;
        var cardNumber = item.cardNumber;
        var payCount = item.payCount;
        var payAmount = item.payAmount.toFixed(2);
        var peyCount = item.peyCount;
        var peyAmount = item.peyAmount.toFixed(2);
        
        var getDetail = "<a href='javascript:void(0)' onclick='detail("+item.id+")'>明细</a>&nbsp;&nbsp;&nbsp;&nbsp;"
        if(item.status==0){
        	getDetail += "<input name='itemStat' type='checkbox' checked='checked' onclick='chgStat(this,"+item.id+")'>是否上传"
        } else {
        	getDetail += "<input name='itemStat' type='checkbox' onclick='chgStat(this,"+item.id+")'>是否上传"
        }
        
        var dataArray = new Array(index + 1, accountName, accountNumber, cardNumber, payCount,
        		payAmount,peyCount,peyAmount,getDetail);
        var row = showDateInRow(dataArray);
        /*if (index % 2 == 0) {
            row.css("background", "rgb(192,192,192)");
        }*/
        row.appendTo(table);
    });
}

/*更改状态*/
function chgStat(obj,id){
	var data = {id:id};
	$.ajax({
        type: "POST",
        async : false,
        url: "chgItemStat",
        dataType: "JSON",
        data: JSON.stringify(data),
        contentType : "application/json",
        success: function(data){
        	if(data.suc){
        		if(data.data==0){
        			$(obj).attr("checked","checked");
        		}else{
        			$(obj).removeAttr("checked","checked");
        		}
        	}
        	alert(data.msg);
        },
		error : function(){
			alert("查询出错！");
		}
    });
}

/*查询明细*/
function detail(item) {
	var data = {id:item};
	$.ajax({
        type: "POST",
        async : false,
        url: "listDetail",
        dataType: "JSON",
        data: JSON.stringify(data),
        contentType : "application/json",
        success: detailCallBack,
		error : function(){
			alert("查询流水明细出错！");
		}
    });
    
}

// 查询Detail回调函数
function detailCallBack(data){
	if(data.suc){
	    // 明细页面
	    $("#detailDialog").dialog({
	        autoOpen: false,
	        title: "交易流水明细",
	        width: 900,
	        height: 500,
	        modal: true,
	        buttons: {
	            Ok: function() {
	                $(this).dialog("close");
	            }
	        },
	        open: function(event, ui) {
	            $("select").css("display", "none");
	        },
	        close: function(event, ui) {
	            $("select").css("display", "inline");
	        }
	    });

		// 打开结果页面
	    $("#detailDialog").dialog("open");
	    
	    $("#transSucc").css("display", "block");
	    $("#transFail").css("display", "none");

	    var dataList = data.data;
	    
	    if(dataList.length==0){
	    	alert("没有查到记录");
	    	return false;
	    }
	    
	    // 展示数据
	    var table = $("#flowbody");
	    table.empty();
	    
	    $(dataList).each(function(index) {
	        var item = dataList[index];
	        
	        var trscode = item.transFlow;
	        var acctno = item.acctNo;
	        var cardno = item.cardNo;
	        var transtime = item.transTime;
	        var transtype = item.trsType;
	        var trsamount = parseFloat(item.trsAmount).toFixed(2);
	        var dfacctname = item.opAcctName;
	        var dfacctno = item.opAcctNo;
	        var dfbankname = item.opBankName;
	        
	        var dataArray = new Array(index + 1, trscode, acctno, cardno, transtime,transtype,
	        		trsamount,dfacctname,dfacctno,dfbankname);
	        var row = showDateInRow(dataArray);
	        row.appendTo(table);
	    });
	} else {
		alert(data.msg)
	}
}