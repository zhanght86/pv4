

select * from zzt_devinfo;



select * from zzt_devstate;


select * from zzt_devstate,zzt_devinfo 
where zzt_devinfo.devno=zzt_devstate.devno 
and  zzt_devinfo.devno='LCWY0009'



select * from zzt_devstatelog,zzt_devinfo 
where zzt_devinfo.devno=zzt_devstatelog.devno 
and  zzt_devinfo.devno='LCWY0009'




delete zzt_devstate where devno='LCWY0009'

commit




select count(*)  from zzt_devstate


select count(*) from zzt_devtjday;

select *  from zzt_devstatelog;

select * from zzt_devstatelog


imp lccx2002/bcm2002@peixun fromuser=lccx2002 touser=lccx2002 file=devinfo.dmp

select * from zzt_bankinfo
drop table zzt_bankinfo
drop table zzt_devinfo

drop table zzt_devstate

drop table zzt_devtype

drop table ZZT_DEVSTATELOG
commit


select * from zzt_devinfo order by devno












/*********************************************************************
oracle10g�ĵ��뵼����
*********************************************************************/
1������zzt_devinfo��Ϊdevinfo.dmp�ļ�

   exp lccx2002/bcm2002@cqdl file=devinfo.dmp tables=zzt_devinfo

2������devinfo.dmp�ļ�������ʱ��Ӧ�ı��У�����֮ǰҪɾ��ԭ�еı�

   imp lccx2002/bcm2002@cqdl fromuser=lccx2002 touser=lccx2002 file=devinfo.dmp


/*********************************************************************
oracle10g�ĵ��뵼�������û��⣺
*********************************************************************/

1 ���ݵ�����
      �����ݿ�cqdl��ȫ����,�û���lccx2002 ����bcm2002 ������cqdl.dmp��

      exp lccx2002/bcm2002@cqdl file=cqdl.dmp full=y


2 ���ݵ���: 
      ��cqdl.dmp �е����ݵ��� cqdl ���ݿ��С�

      imp lccx2002/bcm2002@cqdl full=y file=cqdl.dmp ignore=y

      ��Ϊ�еı��Ѿ����ڣ��ᱨ���Ըñ�Ͳ�����е��롣�ں������ ignore=y �Ϳ���ȫ�������ˡ�












