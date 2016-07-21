/**
 * navMenu.js
 */

$(function() {
	//从cookie中获取菜单
	var data = $.cookie("MENU_DATA");
	if(data == "undefined" || data == null){
		//从服务器获取菜单数据
		$.ajax({
	        type: "POST",
	        url: "getmenu",
	        dataType: "JSON",
	        success: function(data){
	        	if(!data.suc){
	        		location.href="./";
	        	}else{
	        		$.cookie("MENU_DATA",JSON.stringify(data.data));
	        		showData(data.data);
	        	}
	        },
			error : function(){
				alert("获取菜单出错！");
				location.href="./";
			}
	    });
	} else {
		var duri = decodeURI($.cookie("MENU_DATA"), "utf-8");
		showData(JSON.parse(duri));
	}
	
	//点击菜单
	$(".subMenuLink").click(function(){
		var title = $(this).text();
	    $.cookie("MENU_CLICKED",title);//把最后一次打开的菜单名称记录在 cookie里
	   	return true;
	});

    //注销按钮
    $("#logout").click(function() {
        if (confirm("确认退出系统?")) {
        	$.removeCookie("MENU_DATA");
        	$.removeCookie("MENU_CLICKED");
        	return true;
        } else {
            return false;
        }
    });

});

/**
 * 显示数据及菜单
 */
function showData(data){
	var user = data.user;
	var menus = data.menu;
	
    $("#userId").text(user.userid);
    $("#userName").text(user.username);
    
    var clicked_menu = $.cookie("MENU_CLICKED");
    
    $(menus).each(function(index){
    	var menu = menus[index];
    	var menudiv = makeMenu(menu);
    	
    	if(clicked_menu != "undefined" && clicked_menu != null && menu.title == clicked_menu){
    		//TODO: 想在这里加上点击菜单后的样式区别，但是不管用!!!
    		$(menudiv).addClass("menuClicked");
    	}
    });
    
    $('#navMenu').menu();
}

/**
 * 生成菜单项
 */
function makeMenu(menu){
	var menudiv = "<div class='title'><a href='"+menu.url+"' class='subMenuLink'>"+menu.title+"</a></div>";
	$("#navMenu").append(menudiv);
	return menudiv;
}

//点击菜单(暂时没有用到)这里的this取不到数据
function clickMenu(url){
	var title = $(this).text();
    $.cookie("MENU_CLICKED",title);
    location.href=url;
}
