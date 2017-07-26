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
 * CheckStringFormat( int flag, String vlaue ) ������ݸ�ʽ�� ������ flag ��ʽ��־ 1 ����0-9�� 2
 * ������0-9�� 3 �����ո�����Ʊ�����س����С� 4 �������ո�����Ʊ�����س����С� 5 ����A-Z,a-z,0-9�� 6
 * ������A-Z,a-z,0-9�� value ��Ҫ��������
 * 
 * ����ֵ true ���ϼ�������� false �����ϼ��������
 * 
 * ��̫�� 2005.3.28
 ******************************************************************************/
function CheckStringFormat( flag, value ){

	if( value == "" ) {
		alert( "��Ҫ��������Ϊ�գ�" );
		return false;
	}

	var re1 = /\d/;	// ����0-9��
	var re2 = /\D/;	// ������0-9��
	var re3 = /\s/;	// �����ո�����Ʊ�����س����С�
	var re4 = /\S/;	// �������ո�����Ʊ�����س����С�
	var re5 = /\w/;	// ����A-Z,a-z,0-9��
	var re6 = /\W/;	// ������A-Z,a-z,0-9��
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
 * isNumber( String vlaue ) ��������Ƿ�ȫΪ���֡� ������ value ��Ҫ��������
 * 
 * ����ֵ true ȫ�����֡� false ��������û�����֡�
 * 
 * ��̫�� 2005.3.28
 ******************************************************************************/
function isNumber( value ){
	if( value == "" ) {
		alert( "��Ҫ��������Ϊ�գ�" );
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
 * isLetter( String vlaue ) ��������Ƿ�ȫΪ��ĸ�� ������ value ��Ҫ��������
 * 
 * ����ֵ true ȫ����ĸ�� false ��������û����ĸ��
 * 
 * ��̫�� 2005.3.28
 ******************************************************************************/
function isLetter( value ){
	if( value == "" ) {
		alert( "��Ҫ��������Ϊ�գ�" );
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
 * isChinese( String vlaue ) ��������Ƿ�ȫΪ���֡� ������ value ��Ҫ��������
 * 
 * ����ֵ true ȫ�Ǻ��֡� false ��������û�к��֡�
 * 
 * ��̫�� 2005.3.28
 ******************************************************************************/
function isChinese( value ){
	if( value == "" ) {
		alert( "��Ҫ��������Ϊ�գ�" );
		return false;
	}

	var arg1 = CheckStringFormat( 6, value );
	var arg2 = CheckStringFormat( 3, value );

	if( ( arg1 == true ) && ( arg2 != true ) ) {
		return true;
	} else {
		return false;
	}



// У���Ƿ�Ϊ�Ϸ�8λ����
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

// ���ܣ����IP��ַ�Ƿ���Ч������
// ������ip --- IP��ַ
// ���أ�0 -- ��ַ��Ч��1 -- ��ʽ��������2 -- ��ʽ����
function checkip(ip)
{
	var len = ip.length;

	// �ж�IP��ַ��ʽ�Ƿ���Ч
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
 * LTrim(string):ȥ����ߵĿո�
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
 * RTrim(string):ȥ���ұߵĿո�
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
 * Trim(string):ȥ��ǰ��ո�
 * 
 * ==================================================================
 * 
 */

// �����쳣��Ϣ
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