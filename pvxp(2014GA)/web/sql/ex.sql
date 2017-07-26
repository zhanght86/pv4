

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
oracle10g的导入导出表：
*********************************************************************/
1、导出zzt_devinfo表为devinfo.dmp文件

   exp lccx2002/bcm2002@cqdl file=devinfo.dmp tables=zzt_devinfo

2、导入devinfo.dmp文件到导出时相应的表中（导入之前要删除原有的表）

   imp lccx2002/bcm2002@cqdl fromuser=lccx2002 touser=lccx2002 file=devinfo.dmp


/*********************************************************************
oracle10g的导入导出整个用户库：
*********************************************************************/

1 数据导出：
      将数据库cqdl完全导出,用户名lccx2002 密码bcm2002 导出到cqdl.dmp中

      exp lccx2002/bcm2002@cqdl file=cqdl.dmp full=y


2 数据导入: 
      将cqdl.dmp 中的数据导入 cqdl 数据库中。

      imp lccx2002/bcm2002@cqdl full=y file=cqdl.dmp ignore=y

      因为有的表已经存在，会报错，对该表就不会进行导入。在后面加上 ignore=y 就可以全部导入了。












