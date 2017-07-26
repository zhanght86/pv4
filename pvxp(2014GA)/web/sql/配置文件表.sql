
--Ʊ������
create sequence properties_seq increment by 1 start with 1 MAXVALUE 999999
NOCYCLE NOCACHE;  

select properties_seq.nextval  from dual


--����������Ϣ��
create   table zzt_properties(
 noID  Number(4)   NOT NULL PRIMARY KEY, --����ID
 filename varchar2(30),--�ļ�����
 isection varchar2(30) not null,--section
 keyname varchar2(30) not null,--������
 keyvalue varchar2(60) not null,--��ֵ
 cdate date default sysdate,--����
 remark1  varchar2(50) --��Ч��ʶ
); 


--�鵥������
CREATE OR REPLACE PROCEDURE Properties_proc(
i_filename IN VARCHAR2,--�ļ�����
i_isection IN VARCHAR2,--section
i_keyname IN VARCHAR2,--������
o_reslut OUT VARCHAR2
)
AS
BEGIN

   SELECT keyvalue INTO o_reslut FROM zzt_properties
   WHERE  filename=i_filename and isection=i_isection and  keyname=i_keyname ;

EXCEPTION
  WHEN no_data_found THEN
    o_reslut:='null';
    dbms_output.put_line('δ�ҵ���Ӧ�ļ�¼��');
END;


 
--��ѯ����������Ϣ ��
 
commit;

create or replace package Properties_PACKKAGE is 
  --�����α� 
  type MAP_CUR is REF CURSOR; 
  --�洢�������� 
  procedure Properties_proc(p_cur IN OUT MAP_CUR); 
end Properties_PACKKAGE; 


-- ��������  --�ڰ����ж���洢���� ���α�

create or replace package body Properties_PACKKAGE is 
procedure Properties_proc (
  p_cur in out MAP_CUR) 
as 
begin 

open p_cur for 
   SELECT filename,isection,keyname,keyvalue FROM zzt_properties;
end Properties_proc; 
end Properties_PACKKAGE ; 

commit;
 

--����zzt_properties������
--DevStateCode.ini
insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','CodeNum','4');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code1','000,����,0');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code2','001,ͨѶʧ��,2');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code3','002,���˿�ʧ��,2');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code4','003,д�˿�ʧ��,2');

commit;


--DevStateNo.ini
insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateNo.ini','StateNo','NoNum','15');


insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo1','Z000,ϵͳ��ʾ');


insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo2','Z001,ͨ��״̬');
---
insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo3','Z002,ϵͳ��Դ');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo4','Z003,�̿�����');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo5','ZZ01,������');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo6','ZZ02,Ʊ�ݴ�ӡ��');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo7','ZZ03,���д�ӡ��');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo8','ZZ04,���۲��ǻ�');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo9','ZZ05,��Ʊ��ӡ��');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo10','ZZ06,IC����');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo11','ZZ0H,ɨ��ǹ(�豸)');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo12','ZZ0I,ָ����');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo13','ZZ0Z,������');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo14','ZZ0F,����֤�Ķ���');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo15','ZZ0N,����������');

commit;
  
