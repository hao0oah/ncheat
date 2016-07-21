/**
 * public.js
 */

$(function() {
	
	// 进度条
	var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

	progressbar.progressbar({
		value : false,
		change : function() {
			progressLabel.text(progressbar.progressbar("value") + "%");
		}
	});

	// 金额字段
	$(".amount").blur(function() {
		var amount = $(this).val();
		if (amount.length > 0) {
			$(this).val($.formatAmount(amount));
		}

	});

	// 所有字段禁用复制、粘贴、剪切
	$(".founder_TextNo").bind("copy cut paste", function(e) {
		return false;
	});

});

/*进度条*/
function progress() {
	var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

	var val = progressbar.progressbar("value") || 0;

	progressbar.progressbar("value", val + 2);

	if (val < 99) {
		setTimeout(progress, 80);
	}
}

// 将json中数据放入form
function putJsonValueToForm(jsonObj, fromId) {
	for ( var key in jsonObj) {
		var value = jsonObj[key];
		$("#" + fromId + " input[name='" + key + "']").val(value);
	}
}

/*jquery插件*/
(function($) {
	/*序列化表单为json对象*/
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
			function() {
				if (serializeObj[this.name]) {
					if ($.isArray(serializeObj[this.name])) {
						serializeObj[this.name].push(this.value);
					} else {
						serializeObj[this.name] = [
							serializeObj[this.name], this.value ];
					}
				} else {
					serializeObj[this.name] = this.value;
				}
			});
		return serializeObj;
	};

	/*select对象加载json数据*/
	$.fn.selectJsonData = function(data) {
		var selectDom = $(this);
		var a = [];
		$.each(data,function(key){a[a.length]=key});
		a.sort();
		$.each(a, function(index) {
			var key = a[index];
			var text = data[key];
			var option = "<option value='" + key + "'>" + text + "</option>";
			selectDom.append(option);
		});

		return false;
	};
	
	/*金额格式化*/
	$.extend({
		formatAmount : function(s) {
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
			var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
			t = "";
			for (var i = 0; i < l.length; i++) {
				t += l[i]
						+ ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}
			return t.split("").reverse().join("") + "." + r;
		}
	});
	
	$.extend({
		unformatAmount : function(s) {
			return parseFloat(s.replace(/[^\d\.-]/g, ""));
		}
	});
})(jQuery);


/**
 * 检查元素值非空
 */
function checkNullData(domId) {
	return $.trim($(domId).val()).length > 0;
}

/**
 * 将空字符串，未定义字符串及空白字符串转成*
 */
function handleNullStr(str) {
	if(str == null)
		return "*";
	else if(typeof(str) == "undefined")
		return "*";
	else if(str.trim() == "")
		return "*";
	else return str;
}

/*在表格中显示数据*/
function showDateInRow(dataList) {
	var row = $("<tr></tr>");

	for ( var i in dataList) {
		var cell = $("<td></td>");
		cell.append(dataList[i]);
		cell.appendTo(row);
	}

	return row;
}

/*获取当前日期 格式yyyymmdd*/
function getTodayDate() {
	var today = new Date();
	var year = today.getFullYear() + "";
	var month = (today.getMonth() + 1) + "";
	if (month.length == 1) {
		month = "0" + month;
	}
	var date = today.getDate() + "";
	if (date.length == 1) {
		date = "0" + date;
	}

	return year + month + date;
}

/*
 * 数字金额格式化
 * 12345格式化为12,345.00 
 * 12345.6格式化为12,345.60 
 * 12345.67格式化为 12,345.67 
 * 只留两位小数。 
 * */
function fmoney(s, n)   
{   
    if (typeof(s) == "undefined") { 
    	s = "*";
    }
	if(s=='*' || s==''){
	   return "*";
   }
   n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
} 

/*金额转换成大写
 num : 金额
 return： 大写金额
 */
function translateAmount(currencyDigits) {
	// Constants: 
	var MAXIMUM_NUMBER = 99999999999.99;
	// Predefine the radix characters and currency symbols for output: 
	var CN_ZERO = "零";
	var CN_ONE = "壹";
	var CN_TWO = "贰";
	var CN_THREE = "叁";
	var CN_FOUR = "肆";
	var CN_FIVE = "伍";
	var CN_SIX = "陆";
	var CN_SEVEN = "柒";
	var CN_EIGHT = "捌";
	var CN_NINE = "玖";
	var CN_TEN = "拾";
	var CN_HUNDRED = "佰";
	var CN_THOUSAND = "仟";
	var CN_TEN_THOUSAND = "万";
	var CN_HUNDRED_MILLION = "亿";
	var CN_SYMBOL = "";
	var CN_DOLLAR = "元";
	var CN_TEN_CENT = "角";
	var CN_CENT = "分";
	var CN_INTEGER = "整";

	// Variables: 
	var integral; // Represent integral part of digit number. 
	var decimal; // Represent decimal part of digit number. 
	var outputCharacters; // The output result. 
	var parts;
	var digits, radices, bigRadices, decimals;
	var zeroCount;
	var i, p, d;
	var quotient, modulus;

	// Validate input string: 
	if (currencyDigits == null){
//		alert("金额为空！");
		return "";
	}
	currencyDigits = currencyDigits.toString();
	if (currencyDigits == "") {
//		alert("请输入金额！");
		return "";
	}
	if (currencyDigits.match(/[^,.\d]/) != null) {
		alert("金额中含有无效字符！");
		return "";
	}
	if ((currencyDigits)
			.match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		alert("金额的格式不正确！");
		return "";
	}

	// Normalize the format of input digits: 
	currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters. 
	currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning. 
	// Assert the number is not greater than the maximum number. 
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		alert("金额过大，应小于1000亿元！");
		return "";
	}

	// Process the coversion from currency digits to characters: 
	// Separate integral and decimal parts before processing coversion: 
	parts = currencyDigits.split(".");
	if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
		// Cut down redundant decimal digits that are after the second. 
		decimal = decimal.substr(0, 2);
	} else {
		integral = parts[0];
		decimal = "";
	}
	// Prepare the characters corresponding to the digits: 
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE,
			CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// Start processing: 
	outputCharacters = "";
	// Process integral part if it is larger than 0: 
	if (Number(integral) > 0) {
		zeroCount = 0;
		for (i = 0; i < integral.length; i++) {
			p = integral.length - i - 1;
			d = integral.substr(i, 1);
			quotient = p / 4;
			modulus = p % 4;
			if (d == "0") {
				zeroCount++;
			} else {
				if (zeroCount > 0) {
					outputCharacters += digits[0];
				}
				zeroCount = 0;
				outputCharacters += digits[Number(d)] + radices[modulus];
			}
			if (modulus == 0 && zeroCount < 4) {
				outputCharacters += bigRadices[quotient];
				zeroCount = 0;
			}
		}
		outputCharacters += CN_DOLLAR;
	}
	// Process decimal part if there is: 
	if (decimal != "") {
		for (i = 0; i < decimal.length; i++) {
			d = decimal.substr(i, 1);
			if (d != "0") {
				outputCharacters += digits[Number(d)] + decimals[i];
			}
		}
	}
	// Confirm and return the final output string: 
	if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	if (decimal == "") {
		outputCharacters += CN_INTEGER;
	}
	outputCharacters = CN_SYMBOL + outputCharacters;
	return outputCharacters;
}

/**
 * 校验规则 : 只能输入数字和字符
 */
$.validator.addMethod("digitsAndChar", function(value, element) {
	// value 是元素的值，element 是元素本身，param 是参数
	value = $.trim(value); // 去除前后的空格
	if (value.length == 0) {
		return true;
	}

	var regex = "^[A-Za-z0-9]+$";
	var r = value.match(regex);
	return r != null;

}, "只能输入数字和字符");

/**
 * 校验规则 校验文件格式，只能输入指定格式的文件
 */
$.validator.addMethod("fileExtension", function(value, element, params) {
	var extension = value.split(".")[1];
	if (extension == null) {
		return false;
	}
	if (params.indexOf(extension) != -1) {
		return true;
	} else {
		return false;
	}

}, "文件格式不正确");

/**
 * 校验规则，只能输入规定的长度
 */
$.validator.addMethod("fixLength", function(value, element, params) {
	var length = value.length;
	if (length == params || length - 0 == 0) {
		return true;
	}
	return false;

}, "只能输入规定的长度");

/**
 * 校验规则，日期不能大于当前日期
 */
$.validator.addMethod("dateValueCheck", function(value, elements) {
	// 格式化日期值
	if (value == null) {
		return true;
	}
	var _date = value.substring(0, 4) + "/" + value.substring(4, 6) + "/"
			+ value.substring(6, 8);
	var date = new Date(_date);
	var now = new Date();
	if (date > now) {
		return false;
	}
	return true;

}, "日期值错误");

/**
 * 校验规则，只能输入汉字
 */
$.validator.addMethod("chineseSimple", function(value, elements) {
	if (value == null) {
		return true;
	}

	var regex = "^[\u4e00-\u9fa5]+$";
	value = $.trim(value); // 去除前后的空格
	var r = value.match(regex);
	return r != null;
}, "只能输入简体中文汉字");

/**
 * 校验规则，只能输入汉字、数字和字母
 */
$.validator.addMethod("characterAndSymbol", function(value, elements) {
	if (value == null) {
		return true;
	}

	var regex = "^[\u4e00-\u9fa5A-Za-z0-9]+$";
	value = $.trim(value); // 去除前后的空格
	var r = value.match(regex);
	return r != null;
}, "只能输入汉字、数字和字母");

// 字符串trim()方法
String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g,"");
};
String.prototype.ltrim = function() {
    return this.replace(/^\s+/,"");
};
String.prototype.rtrim = function() {
    return this.replace(/\s+$/,"");
};
String.prototype.rpad = function(len,ch) {
	len = len-this.length;
	if(len>0){
		return this.concat(new Array(len+1).join(ch));
	}
    return this;
};
String.prototype.lpad = function(len,ch) {
	len = len-this.length;
	if(len>0){
		return new Array(len+1).join(ch).concat(this);
	}
    return this;
};
