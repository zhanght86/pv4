--1
-- 建立 设备状态表
create table ZZT_DEVSSTATE
(
  DEVNO      VARCHAR2(10) not null,--设备编号
  statecode  VARCHAR2(2) not null,--设备状态编码
  Dveice_State	VARCHAR2(2) not null,--设备状态  	DS
  Prt_State	VARCHAR2(2) not null,--打印机状态 	  	PS
  IdCard_State	VARCHAR2(2) not null,--二代证状态	ICS
  Bill_State	VARCHAR2(2) not null,--凭条打印机状态	BS
  BrushCard_State	VARCHAR2(2) not null,--刷卡器状态	BCS
  bdate       timestamp default sysdate--开始日期
)


--2
--设备状态插入  存储过程
CREATE OR REPLACE PROCEDURE DEVSTATEADD_PROC(
I_DEVNO    IN  VARCHAR2,--设备编号
I_statecode IN VARCHAR2,--设备状态编码
I_Dveice_State IN	VARCHAR2,--设备状态  	DS
I_Prt_State IN	VARCHAR2,--打印机状态 	  	PS
I_IdCard_State IN	VARCHAR2,--二代证状态	ICS
I_Bill_State IN	VARCHAR2,--凭条打印机状态	BS
I_BrushCard_State IN	VARCHAR2,--刷卡器状态	BCS
O_RESULT OUT VARCHAR2
)
AS
BEGIN
  SELECT DEVNO INTO O_RESULT  FROM ZZT_DEVSSTATE 
  WHERE DEVNO=I_DEVNO ;
  
   IF SQL%FOUND THEN
     dbms_output.put_line('FOUND！');
     DELETE FROM ZZT_DEVSSTATE WHERE DEVNO=I_DEVNO;
     INSERT INTO ZZT_DEVSSTATE(DEVNO,statecode,Dveice_State,Prt_State,IdCard_State,Bill_State,BrushCard_State) 
     VALUES(I_DEVNO,I_statecode,I_Dveice_State,I_Prt_State,I_IdCard_State,I_Bill_State,I_BrushCard_State);
    COMMIT;
    O_RESULT:='UPDATE';
    ELSE 
      dbms_output.put_line('NOT FOUND！');
   END IF;
EXCEPTION
  WHEN no_data_found THEN
  INSERT INTO ZZT_DEVSSTATE(DEVNO,statecode,Dveice_State,Prt_State,IdCard_State,Bill_State,BrushCard_State) 
  VALUES(I_DEVNO,I_statecode,I_Dveice_State,I_Prt_State,I_IdCard_State,I_Bill_State,I_BrushCard_State);
  COMMIT;
  O_RESULT:='INSERT';
  dbms_output.put_line('未找到相应的记录！');
END;
 
INSERT INTO ZZT_DEVSSTATE(DEVNO,statecode,Dveice_State,Prt_State,IdCard_State,Bill_State,BrushCard_State) 
VALUES('TD002','1','1','1','1','1','1') 


--3
--建立序列
--主键序列
create sequence TD_seq increment by 1 start with 1 MAXVALUE 999999
NOCYCLE NOCACHE;  
select TD_seq.currval   from dual
select TD_seq.NEXTVAL   from dual

--4
--填单交易记录表
create table ZZT_TDRECORD
(
  TD_ID  Number(6)   NOT NULL PRIMARY KEY, --主键ID
  USR_NM  VARCHAR2(80) not null,--用户姓名
  USR_ID	VARCHAR2(20) not null,--用户身份证号
  XML_TYPE_CODE VARCHAR2(20) not null,--单据类型
  TD_MSG	CLOB not null,--详细报文
  TD_DATE      date default sysdate--报文日期
)

INSERT INTO ZZT_TDRECORD  
VALUES(TD_seq.NEXTVAL,'武坤鹏','370123456789876543','AP0001','00000157ENTITY.XML_TYPE_NAME=DEVICE_STATE;Ref_No=0100000000;Dveice_Code=TD001;Pro_Ver=10.0.1;Dveice_State=1;Prt_State=1;IdCard_State=1;Bill_State=1;BrushCard_State=1;',sysdate); 

SELECT * FROM ZZT_TDRECORD

--5
--交易报文插入  存储过程
CREATE OR REPLACE PROCEDURE TDRECORDS_PROC(
I_USR_NM IN  VARCHAR2 ,--用户姓名
I_USR_ID IN	VARCHAR2 ,--用户身份证号
I_XML_TYPE_CODE IN VARCHAR2 ,--单据类型
I_TD_MSG IN	CLOB,--交易报文
O_RESULT OUT VARCHAR2
)
AS
BEGIN
   INSERT INTO ZZT_TDRECORD(TD_ID,USR_NM,USR_ID,XML_TYPE_CODE,TD_MSG) 
   VALUES(TD_seq.NEXTVAL,I_USR_NM,I_USR_ID,I_XML_TYPE_CODE,I_TD_MSG);
   COMMIT;
   O_RESULT:='INSERT SUCCESS ID==>'||I_USR_ID;
END;

call   CHECK_TDRECORDS_PROC()

--6
--交易历史查询  存储过程
CREATE OR REPLACE PROCEDURE CHECK_TDRECORDS_PROC(
I_USR_ID IN	VARCHAR2 ,--用户身份证号
I_XML_TYPE_CODE IN VARCHAR2 ,--单据类型
O_TD_MSG OUT	CLOB,--交易报文
O_RESULT OUT VARCHAR2
)
AS
BEGIN
    SELECT TD_MSG INTO O_TD_MSG  FROM 
    (SELECT TD_MSG FROM ZZT_TDRECORD 
    WHERE USR_ID=I_USR_ID AND XML_TYPE_CODE=I_XML_TYPE_CODE  
    ORDER BY TD_DATE DESC)
    where rownum=1;
    O_RESULT:='FOUND';
    COMMIT;
EXCEPTION
  WHEN no_data_found THEN
  O_RESULT:='UNFOUND';
  COMMIT;
  dbms_output.put_line('未找到相应的记录！');
END;






--7
--查询近期填单历史记录  包

drop package TD_TRADE_PACKKAGE;
commit;

create or replace package TD_TRADE_PACKKAGE is 
  --定义游标 
  type TD_ARRY_CUR is REF CURSOR; 
  --存储过程声明 
  procedure TD_ARRY_proc(I_USR_ID IN	VARCHAR2 ,I_XML_TYPE_CODE IN VARCHAR2 ,p_cur IN OUT TD_ARRY_CUR); 
end TD_TRADE_PACKKAGE; 


-- 创建包体  --在包体中定义存储过程 和游标

create or replace package body TD_TRADE_PACKKAGE is 
procedure TD_ARRY_proc (
  I_USR_ID IN  VARCHAR2 ,--用户身份证号
  I_XML_TYPE_CODE IN VARCHAR2 ,--单据类型
  p_cur in out TD_ARRY_CUR) 
as 
begin 

open p_cur for 
  SELECT TD_ID,USR_NM,USR_ID,TD_DATE FROM 
  (SELECT TD_ID,USR_NM,USR_ID,TD_DATE FROM ZZT_TDRECORD
  WHERE USR_ID=I_USR_ID AND XML_TYPE_CODE=I_XML_TYPE_CODE
  ORDER BY TD_DATE DESC) WHERE ROWNUM<=15;
end TD_ARRY_proc; 
end TD_TRADE_PACKKAGE ; 

commit;


select * from (select * from ZZT_TDRECORD order by TD_DATE DESC) where rownum<=17;



--8
--交易交易详细记录    存储过程
CREATE OR REPLACE PROCEDURE CHECK_TDRECORDS_MX_PROC(
I_TD_ID IN	VARCHAR2 ,--用户身份证号
O_TD_MSG OUT	CLOB,--交易报文
O_RESULT OUT VARCHAR2--查询结果
)
AS
BEGIN
    SELECT TD_MSG INTO O_TD_MSG  FROM ZZT_TDRECORD
    WHERE TD_ID=I_TD_ID ;
    O_RESULT:='FOUND';
    COMMIT;
EXCEPTION
  WHEN no_data_found THEN
  O_RESULT:='UNFOUND';
  COMMIT;
  dbms_output.put_line('未找到相应的记录！');
END;





