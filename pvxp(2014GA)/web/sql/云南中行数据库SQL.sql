--1
-- ���� �豸״̬��
create table ZZT_DEVSSTATE
(
  DEVNO      VARCHAR2(10) not null,--�豸���
  statecode  VARCHAR2(2) not null,--�豸״̬����
  Dveice_State	VARCHAR2(2) not null,--�豸״̬  	DS
  Prt_State	VARCHAR2(2) not null,--��ӡ��״̬ 	  	PS
  IdCard_State	VARCHAR2(2) not null,--����֤״̬	ICS
  Bill_State	VARCHAR2(2) not null,--ƾ����ӡ��״̬	BS
  BrushCard_State	VARCHAR2(2) not null,--ˢ����״̬	BCS
  bdate       timestamp default sysdate--��ʼ����
)


--2
--�豸״̬����  �洢����
CREATE OR REPLACE PROCEDURE DEVSTATEADD_PROC(
I_DEVNO    IN  VARCHAR2,--�豸���
I_statecode IN VARCHAR2,--�豸״̬����
I_Dveice_State IN	VARCHAR2,--�豸״̬  	DS
I_Prt_State IN	VARCHAR2,--��ӡ��״̬ 	  	PS
I_IdCard_State IN	VARCHAR2,--����֤״̬	ICS
I_Bill_State IN	VARCHAR2,--ƾ����ӡ��״̬	BS
I_BrushCard_State IN	VARCHAR2,--ˢ����״̬	BCS
O_RESULT OUT VARCHAR2
)
AS
BEGIN
  SELECT DEVNO INTO O_RESULT  FROM ZZT_DEVSSTATE 
  WHERE DEVNO=I_DEVNO ;
  
   IF SQL%FOUND THEN
     dbms_output.put_line('FOUND��');
     DELETE FROM ZZT_DEVSSTATE WHERE DEVNO=I_DEVNO;
     INSERT INTO ZZT_DEVSSTATE(DEVNO,statecode,Dveice_State,Prt_State,IdCard_State,Bill_State,BrushCard_State) 
     VALUES(I_DEVNO,I_statecode,I_Dveice_State,I_Prt_State,I_IdCard_State,I_Bill_State,I_BrushCard_State);
    COMMIT;
    O_RESULT:='UPDATE';
    ELSE 
      dbms_output.put_line('NOT FOUND��');
   END IF;
EXCEPTION
  WHEN no_data_found THEN
  INSERT INTO ZZT_DEVSSTATE(DEVNO,statecode,Dveice_State,Prt_State,IdCard_State,Bill_State,BrushCard_State) 
  VALUES(I_DEVNO,I_statecode,I_Dveice_State,I_Prt_State,I_IdCard_State,I_Bill_State,I_BrushCard_State);
  COMMIT;
  O_RESULT:='INSERT';
  dbms_output.put_line('δ�ҵ���Ӧ�ļ�¼��');
END;
 
INSERT INTO ZZT_DEVSSTATE(DEVNO,statecode,Dveice_State,Prt_State,IdCard_State,Bill_State,BrushCard_State) 
VALUES('TD002','1','1','1','1','1','1') 


--3
--��������
--��������
create sequence TD_seq increment by 1 start with 1 MAXVALUE 999999
NOCYCLE NOCACHE;  
select TD_seq.currval   from dual
select TD_seq.NEXTVAL   from dual

--4
--����׼�¼��
create table ZZT_TDRECORD
(
  TD_ID  Number(6)   NOT NULL PRIMARY KEY, --����ID
  USR_NM  VARCHAR2(80) not null,--�û�����
  USR_ID	VARCHAR2(20) not null,--�û����֤��
  XML_TYPE_CODE VARCHAR2(20) not null,--��������
  TD_MSG	CLOB not null,--��ϸ����
  TD_DATE      date default sysdate--��������
)

INSERT INTO ZZT_TDRECORD  
VALUES(TD_seq.NEXTVAL,'������','370123456789876543','AP0001','00000157ENTITY.XML_TYPE_NAME=DEVICE_STATE;Ref_No=0100000000;Dveice_Code=TD001;Pro_Ver=10.0.1;Dveice_State=1;Prt_State=1;IdCard_State=1;Bill_State=1;BrushCard_State=1;',sysdate); 

SELECT * FROM ZZT_TDRECORD

--5
--���ױ��Ĳ���  �洢����
CREATE OR REPLACE PROCEDURE TDRECORDS_PROC(
I_USR_NM IN  VARCHAR2 ,--�û�����
I_USR_ID IN	VARCHAR2 ,--�û����֤��
I_XML_TYPE_CODE IN VARCHAR2 ,--��������
I_TD_MSG IN	CLOB,--���ױ���
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
--������ʷ��ѯ  �洢����
CREATE OR REPLACE PROCEDURE CHECK_TDRECORDS_PROC(
I_USR_ID IN	VARCHAR2 ,--�û����֤��
I_XML_TYPE_CODE IN VARCHAR2 ,--��������
O_TD_MSG OUT	CLOB,--���ױ���
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
  dbms_output.put_line('δ�ҵ���Ӧ�ļ�¼��');
END;






--7
--��ѯ�������ʷ��¼  ��

drop package TD_TRADE_PACKKAGE;
commit;

create or replace package TD_TRADE_PACKKAGE is 
  --�����α� 
  type TD_ARRY_CUR is REF CURSOR; 
  --�洢�������� 
  procedure TD_ARRY_proc(I_USR_ID IN	VARCHAR2 ,I_XML_TYPE_CODE IN VARCHAR2 ,p_cur IN OUT TD_ARRY_CUR); 
end TD_TRADE_PACKKAGE; 


-- ��������  --�ڰ����ж���洢���� ���α�

create or replace package body TD_TRADE_PACKKAGE is 
procedure TD_ARRY_proc (
  I_USR_ID IN  VARCHAR2 ,--�û����֤��
  I_XML_TYPE_CODE IN VARCHAR2 ,--��������
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
--���׽�����ϸ��¼    �洢����
CREATE OR REPLACE PROCEDURE CHECK_TDRECORDS_MX_PROC(
I_TD_ID IN	VARCHAR2 ,--�û����֤��
O_TD_MSG OUT	CLOB,--���ױ���
O_RESULT OUT VARCHAR2--��ѯ���
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
  dbms_output.put_line('δ�ҵ���Ӧ�ļ�¼��');
END;





