function isSelectedMultiBox(CheckBox) {
  if(isNaN(CheckBox.length)) {
    if (CheckBox.checked == true) {
      return true;
    } else {
      return false;
    }
  } else {
    for(var i=0;i<CheckBox.length;i++) {
      if(CheckBox[i].checked == true) {
        return true;
      }
    }
    return false;
  }
}

/*
 * function isSelectedMultiBox(CheckBox) { try { if(isNaN(CheckBox.length)) { if
 * (CheckBox.checked == true) { return true; } else { return false; } } else {
 * for(var i=0;i<CheckBox.length;i++) { if(CheckBox[i].checked == true) {
 * return true; } } return false; } } catch(ex) {} }
 */

/*******************************************************************************
 * CheckStringFormat( int flag, String vlaue ) 检查数据格式。 输入项 flag 格式标志 1 包括0-9。 2
 * 不包括0-9。 3 包括空格符、制表符、回车换行。 4 不包括空格符、制表符、回车换行。 5 包括A-Z,a-z,0-9。 6
 * 不包括A-Z,a-z,0-9。 value 需要检查的数据
 * 
 * 返回值 true 符合检查条件。 false 不符合检查条件。
 * 
 * 刘太沛 2005.3.28
 ******************************************************************************/
function CheckStringFormat( flag, value ){

	if( value == "" ) {
		alert( "需要检查的数据为空！" );
		return false;
	}

	var re1 = /\d/;	// 包括0-9。
	var re2 = /\D/;	// 不包括0-9。
	var re3 = /\s/;	// 包括空格符、制表符、回车换行。
	var re4 = /\S/;	// 不包括空格符、制表符、回车换行。
	var re5 = /\w/;	// 包括A-Z,a-z,0-9。
	var re6 = /\W/;	// 不包括A-Z,a-z,0-9。
	var result = "";

	switch( flag ) {
		case 1:
			result = re1.exec( value );
			break;
		case 2:
			result = re2.exec( value );
			break;
		case 3:
			result = re3.exec( value );
			break;
		case 4:
			result = re4.exec( value );
			break;
		case 5:
			result = re5.exec( value );
			break;
		case 6:
			result = re6.exec( value );
			break;
		default:
			result = null;
	}

	if( result != null ) {
		return true;
	} else {
		return false;
	}
}

/*******************************************************************************
 * isNumber( String vlaue ) 检查数据是否全为数字。 输入项 value 需要检查的数据
 * 
 * 返回值 true 全是数字。 false 包含或者没有数字。
 * 
 * 刘太沛 2005.3.28
 ******************************************************************************/
function isNumber( value ){
	if( value == "" ) {
		alert( "需要检查的数据为空！" );
		return false;
	}

	var arg1 = CheckStringFormat( 6, value );
	var arg2 = CheckStringFormat( 2, value );

	if( ( arg1 != true ) && ( arg2 == false ) ) {
		return true;
	} else {
		return false;
	}
}

/*******************************************************************************
 * isLetter( String vlaue ) 检查数据是否全为字母。 输入项 value 需要检查的数据
 * 
 * 返回值 true 全是字母。 false 包含或者没有字母。
 * 
 * 刘太沛 2005.3.28
 ******************************************************************************/
function isLetter( value ){
	if( value == "" ) {
		alert( "需要检查的数据为空！" );
		return false;
	}

	var arg1 = CheckStringFormat( 5, value );
	var arg2 = CheckStringFormat( 1, value );
	var arg3 = CheckStringFormat( 6, value );

	if( ( arg1 == true ) && ( arg2 == false ) && ( arg3 == false ) ) {
		return true;
	} else {
		return false;
	}
}

/*******************************************************************************
 * isChinese( String vlaue ) 检查数据是否全为汉字。 输入项 value 需要检查的数据
 * 
 * 返回值 true 全是汉字。 false 包含或者没有汉字。
 * 
 * 刘太沛 2005.3.28
 ******************************************************************************/
function isChinese( value ){
	if( value == "" ) {
		alert( "需要检查的数据为空！" );
		return false;
	}

	var arg1 = CheckStringFormat( 6, value );
	var arg2 = CheckStringFormat( 3, value );

	if( ( arg1 == true ) && ( arg2 != true ) ) {
		return true;
	} else {
		return false;
	}



// 校验是否为合法8位日期
function chkdate8(datestr) {
 var lthdatestr
 if (datestr != "")
 lthdatestr= datestr.length ;
 else
 lthdatestr=0;
 var tmpy="";
 var tmpm="";
 var tmpd="";
 // var datestr;
 var status;
 status=0;
 if ( lthdatestr== 0)
 return false;
if (lthdatestr!=8) return false;
tmpy=datestr.substring(0,4);
tmpm=datestr.substring(4,6);
tmpd=datestr.substring(6,8);
 year=new String (tmpy);
 month=new String (tmpm);
 day=new String (tmpd)
 if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2)) {
 return false;
 }
 if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) ) {
 return false;
 }
 if (!((year % 4)==0) && (month==2) && (day==29)) {
 return false;
 }
 if ((month<=7) && ((month % 2)==0) && (day>=31)) {
 return false;
 }
 if ((month>=8) && ((month % 2)==1) && (day>=31)) {
 return false;
 }
 if ((month==2) && (day==30)) {
 return false;
 }
 return true;
}
}

// 功能：检查IP地址是否有效，完整
// 参数：ip --- IP地址
// 返回：0 -- 地址有效；1 -- 格式不完整；2 -- 格式错误
function checkip(ip)
{
	var len = ip.length;

	// 判断IP地址格式是否有效
	begin = 0;
	end = 0;

	end = ip.indexOf('.',begin);
	if(end==-1) return 1;
	part1 = ip.substring(begin,end);
	if((part1>255)||(part1<0)) return 2;
	begin = end+1;

	end = ip.indexOf('.',begin);
	if(end==-1) return 1;
	part2 = ip.substring(begin,end);
	if((part2>255)||(part2<0)) return 2;
	begin = end+1;

	end = ip.indexOf('.',begin);
	if(end==-1) return 1;
	part3 = ip.substring(begin,end);
	if((part3>255)||(part3<0)) return 2;
	begin = end+1;
	part4 = ip.substring(begin,len);
	if((part4>255)||(part4<0)) return 2;
}


/*
 * 
 * ==================================================================
 * 
 * LTrim(string):去除左边的空格
 * 
 * ==================================================================
 * 
 */

function ltrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1)
    {
        var j=0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
        {
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}

 

/*
 * 
 * ==================================================================
 * 
 * RTrim(string):去除右边的空格
 * 
 * ==================================================================
 * 
 */

function rtrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
    {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
        {
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}

 

/*
 * 
 * ==================================================================
 * 
 * Trim(string):去除前后空格
 * 
 * ==================================================================
 * 
 */

// 处理异常信息
function displayErrorMsg(e) {
	tmp = "<hr>";
	for (x in e) {
		tmp += "" + x + "=>" + e[x] + "<br/>/n";
	}

	alert(tmp);
}


function trim(str)
{
    return rtrim(ltrim(str));
}