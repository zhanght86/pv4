		// Str - TD�Ķ����ַ���
		// Prop - ����ֵ��������colspan��rowspan��border��width����ֵ�����Ե�����
		// ����ֵ�����Ե�ֵ
		// ����TD�Ķ���Ϊ<td width=100>xx</td>
		// ��getPropValue("td width=100>xx</td>", "width")����100
		function getIntPropValue(Str, Prop){
			var s = Str.toUpperCase(Str);
			var p = Prop.toUpperCase(Prop);
			var idx = s.indexOf(p);
			var PropValue = "";
			if (idx >= 0){
				var x = Prop.length;
				while ((Str.charAt(idx + x) < "0") || (Str.charAt(idx + x) > "9")){
					x++;
				}
				while ((Str.charAt(idx + x) >= "0") && (Str.charAt(idx + x) <= "9")){
					PropValue = PropValue + Str.charAt(idx + x);
					x++;
				}
				return PropValue;
			}
			else
				return "";
		}

		// ��ǿ�͵ĵ���HTML��Table��Excel
		function PrintTableToExcelEx(objTab){
			try {
				var xls = new ActiveXObject( "Excel.Application" );
			}catch(e){
				alert( "Ҫ��ӡ�ñ������밲װExcel���ӱ�������ͬʱ�������ʹ�á�ActiveX �ؼ��������������������ִ�пؼ��� �������������˽���������÷�����");
				return false;
			}
			xls.visible = true;
			var xlBook  = xls.Workbooks.Add;
			var xlsheet = xlBook.Worksheets(1);
			
			//�޸ı���ʽΪ�ı�
			xls.Cells.Select();
			xls.Selection.NumberFormatLocal = "@";
			xls.Range("A1").Select();   
			
			var CurX = 1;
			var CurY = 1;
			var ColSpanStart = 0;
			var ColSpanEnd   = 0;
			var RowSpanStart = 0;
			var RowSpanEnd   = 0;
			for (var i = 0; i < objTab.rows.length; i++){
				for (var j = 0; j < objTab.rows[i].cells.length; j++){
					s = objTab.rows[i].cells[j].outerHTML;
					
					s = s.toUpperCase(s);
					var tmp = objTab.rows[i].cells[j].innerHTML;
					if(tmp=="&nbsp;"){
						tmp = "";
					}
					
					var pos = 0;
					pos = tmp.indexOf("&lt;");
					if(pos!=-1){
						tmp = tmp.substring(0,pos)+"<"+tmp.substring(pos+4);
					}
					
					pos = tmp.indexOf("&lt;");
					if(pos!=-1){
						tmp = tmp.substring(0,pos)+"<"+tmp.substring(pos+4);
					}
					
					pos = tmp.indexOf("&gt;");
					if(pos!=-1){
						tmp = tmp.substring(0,pos)+">"+tmp.substring(pos+4);
					}
					
					//������ݾ���
					xlsheet.Cells(CurX, CurY).HorizontalAlignment = 3;
					xlsheet.Cells(CurX, CurY).Value = tmp+" ";
					//xlsheet.Cells(CurX, CurY).Interior.color = 0x00FF66;
					xlsheet.Cells(CurX, CurY).Borders.LineStyle = 1;
					SpanCol = getIntPropValue(s, "colspan");
					if (SpanCol != ""){
				  		ColSpanStart = CurY;
				  		ColSpanEnd   = parseInt(CurY) + parseInt(SpanCol) - 1;
				  		var R = xls.Range(xls.Cells(CurX, ColSpanStart), xls.Cells(CurX, ColSpanEnd));
				  		R.MergeCells = true;
				  		R.Borders.LineStyle = 1;
				  	}
					SpanRow = getIntPropValue(s, "rowspan");
					if (SpanRow != ""){
						RowSpanStart = CurX;
						RowSpanEnd   = parseInt(CurX) + parseInt(SpanRow) - 1;
						var R = xls.Range(xls.Cells(RowSpanStart, CurY), xls.Cells(RowSpanEnd, CurY));
						R.MergeCells = true;
						R.Borders.LineStyle = 1;
					 }
					CurY++;
				}
				ColSpanStart = 0;
				ColSpanEnd   = 0;
				CurX++;
				if (parseInt(CurX) > parseInt(RowSpanEnd)){
					RowSpanStart = 0;
					RowSpanEnd   = 0;
					CurY = 1;
				}else{
					CurY = 2;
				}
			}
			xlsheet.Columns.AutoFit; //�Զ���Ӧ��С
			return;
		}
		
function PrintTableToExcelEx1(objTab){
	try{
		var xls = new ActiveXObject( "Excel.Application" );
	}catch(e) {
		alert( "�����밲װExcel���ӱ�������ͬʱ�������ʹ�á�ActiveX �ؼ��������������������ִ�пؼ��� �������������˽���������÷�����");
		return false;
	}
	xls.visible = true;

	var xlBook = xls.Workbooks.Add;
	var xlsheet = xlBook.Worksheets(1);

	//�޸ı���ʽΪ�ı�
	xls.Cells.Select();
	xls.Selection.NumberFormatLocal = "@";
	xls.Range("A1").Select(); 
	
	var CurX = 1;
	var CurY = 1;
	
	for (var i = 0; i < objTab.rows.length; i++){
		for (var j = 0; j < objTab.rows[i].cells.length; j++){
			var cell = objTab.rows[i].cells[j];
			var v = cell.outerText;
			
			if ( cell.children != null && cell.children.length != 0 ){
				v = "";
				for ( var k = 0; k < cell.children.length; k++ ){
					var cc = cell.children[k];
					
					switch (cc.type){
						case "select-one":
							for ( var l = 0; l < cc.options.length; l++ )
							if ( cc.options[l].selected ) v += cc.options[l].text;
							break;
						case "text":
							v += cc.value;
							break;
						case "checkbox":
							v += cc.checked ? "��" : "��";
							break;
						case undefined:
							v += cell.outerText;
							break;
						case "hidden":
							break;
						default:
							//v += cc.id + "/";
							//v += cc.tagname + "/";
							//v += cc.type + "/"
							v += cc.value;
						break; 
					}
				}
			}
	
			while ( xlsheet.Cells(CurX,CurY).MergeCells ) CurY++;
			
			xlsheet.Cells(CurX, CurY).HorizontalAlignment = 3; 
			var align = cell.getAttribute("align");
			switch ( align ){
				case "left":
					xlsheet.Cells(CurX, CurY).HorizontalAlignment = 2;//XlHalign.xlHalignLeft;
					break;
				case "center":
					xlsheet.Cells(CurX, CurY).HorizontalAlignment = 3;;//XlHalign.xlHalignCenter; 
					break;
				case "right":
					xlsheet.Cells(CurX, CurY).HorizontalAlignment = 4;//XlHalign.xlHalignRight;
					break;
			}
			xlsheet.Cells(CurX, CurY).WrapText = true;
			xlsheet.Cells(CurX, CurY).VerticalAlignment = 2;
			xlsheet.Cells(CurX, CurY).Value = v;
			xlsheet.Cells(CurX, CurY).Borders.LineStyle = 1;
				
			var rowSpan = cell.getAttribute("RowSpan");
			var colSpan = cell.getAttribute("ColSpan");
			if ( rowSpan >= 2 || colSpan >= 2 ){
				var R = xls.Range(xls.Cells(CurX, CurY), xls.Cells(CurX + rowSpan - 1, CurY + colSpan - 1));
				R.MergeCells = true;
				R.Borders.LineStyle = 1;
			}
			CurY++; 
		}
			
		CurX++;
		CurY = 1;
	}
	xlsheet.Columns.AutoFit; //�Զ���Ӧ��С
	return;
}

