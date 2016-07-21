/**
 * main.js
 */

$(function(){
	
	// 首页加载菜单，移除cookie中的缓存
	// TODO: 如果加上这两句，在main界面中，点击菜单会达不到预想效果
	$.removeCookie("MENU_DATA");
	$.removeCookie("MENU_CLICKED");
	
	$(".navBarDiv").load("navBar");
});
