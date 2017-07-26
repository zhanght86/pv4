
--票号序列
create sequence properties_seq increment by 1 start with 1 MAXVALUE 999999
NOCYCLE NOCACHE;  

select properties_seq.nextval  from dual


--建表配置信息表
create   table zzt_properties(
 noID  Number(4)   NOT NULL PRIMARY KEY, --主键ID
 filename varchar2(30),--文件名称
 isection varchar2(30) not null,--section
 keyname varchar2(30) not null,--键名称
 keyvalue varchar2(60) not null,--键值
 cdate date default sysdate,--日期
 remark1  varchar2(50) --有效标识
); 


--查单条数据
CREATE OR REPLACE PROCEDURE Properties_proc(
i_filename IN VARCHAR2,--文件名称
i_isection IN VARCHAR2,--section
i_keyname IN VARCHAR2,--键名称
o_reslut OUT VARCHAR2
)
AS
BEGIN

   SELECT keyvalue INTO o_reslut FROM zzt_properties
   WHERE  filename=i_filename and isection=i_isection and  keyname=i_keyname ;

EXCEPTION
  WHEN no_data_found THEN
    o_reslut:='null';
    dbms_output.put_line('未找到相应的记录！');
END;


 
--查询所有配置信息 包
 
commit;

create or replace package Properties_PACKKAGE is 
  --定义游标 
  type MAP_CUR is REF CURSOR; 
  --存储过程声明 
  procedure Properties_proc(p_cur IN OUT MAP_CUR); 
end Properties_PACKKAGE; 


-- 创建包体  --在包体中定义存储过程 和游标

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
 

--插入zzt_properties表数据
--DevStateCode.ini
insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','CodeNum','4');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code1','000,正常,0');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code2','001,通讯失败,2');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code3','002,读端口失败,2');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateCode.ini','StateCode','Code4','003,写端口失败,2');

commit;


--DevStateNo.ini
insert into zzt_properties(noID,filename,isection,keyname,keyvalue) 
values(properties_seq.nextval,'DevStateNo.ini','StateNo','NoNum','15');


insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo1','Z000,系统提示');


insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo2','Z001,通信状态');
---
insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo3','Z002,系统电源');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo4','Z003,吞卡张数');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo5','ZZ01,读卡机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo6','ZZ02,票据打印机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo7','ZZ03,宽行打印机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo8','ZZ04,存折补登机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo9','ZZ05,发票打印机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo10','ZZ06,IC卡机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo11','ZZ0H,扫描枪(设备)');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo12','ZZ0I,指纹仪');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo13','ZZ0Z,发卡机');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo14','ZZ0F,二代证阅读器');

insert into zzt_properties(noID,filename,isection,keyname,keyvalue) values(properties_seq.nextval,
'DevStateNo.ini','StateNo','StateNo15','ZZ0N,发卡机卡箱');

commit;
  
