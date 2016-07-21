/**
 * ls100405.js
 */

$(function() {
	
	//加载菜单
	$(".navBarDiv").load("navBar");
	
    //表单
    var form = $("#queryForm");
    var validator = form.validate();
    
    //点击输入框，隐藏错误提示消息
    $("input").focus(function(){
    	$("#queryInfo").text("");
    });
    
    // 查询按钮
    $("#querySubmit").button().click(function() {
        // 提交查询
        queryPreFlow(0);
    });
    
    // 取消按钮
    $("#queryReset").button().click(function() {
        validator.resetForm();
        $("#queryInfo").text("");
        $("#queryResult").css("display","none");
    });

    // 分页按钮
    // 首页按钮
    $("#fristPage").click(function() {
        queryPreFlow(0);
    });

    // 上一页按钮
    $("#previous").click(function() {
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

// 查询操作记录
function queryPreFlow(begin) {

    var limit = 10;
    var data = $("#queryForm").serializeJson();
    data.transDate = data.transDate.replace(/\-/g,"");
    data.begin = begin;
    data.limit = limit;

    $.ajax({
        type: "POST",
        url: "getOptRecord",
        dataType: "JSON",
        data: JSON.stringify(data),
        contentType : "application/json",
        success: queryPreFlowCallBack,
		error : function(){
			$("#wait").hide();
			$("#queryInfo").text("");
			alert("查询出错！");
			$("#querySubmit,.pageBtn").removeAttr("disabled");
		}
    });
    $("#querySubmit,.pageBtn").attr("disabled", "true");
 	$("#wait").show();
	$("#queryInfo").text("正在查询...");
}

// 查询操作记录回调函数
function queryPreFlowCallBack(data) {
	$("#querySubmit,.pageBtn").removeAttr("disabled");
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
        
        var userid = item.userid;
        var optdate = item.optdate;
        var opttime = item.opttime;
        var type = null;
        switch(item.type){
        case 0: type = "定时上报";
        case 1: type = "界面上报";
        case 2: type = "定时生成报文";
        case 3: type = "界面生成报文";
        case 4: type = "管理员批量上报";
        case 5: type = "柜员提交筛选";
        case 10: type = "其他操作";
        default: type = "未知类型"
        }
        var feature = null;
        switch(item.feature){
        case 1001: feature = "频繁开户";
        case 1002: feature = "累计开户";
        case 3001: feature = "诈骗未遂";
        case 3002: feature = "受害人举报疑似诈骗";
        case 3003: feature = "银行举报疑似诈骗";
        case 3006: feature = "分散入集中出";
        case 3007: feature = "集中入分散出";
        case 3008: feature = "连续多笔交易";
        case 55555: feature = "柜员登陆";
        case 55556: feature = "退出登陆";
        case 55557: feature = "修改密码";
        default: feature = "其他操作";
        }
        
        var dataArray = new Array(index + 1, userid, optdate, opttime, type,feature);
        var row = showDateInRow(dataArray);
        
        row.appendTo(table);
    });
}