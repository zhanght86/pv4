package com.rmi.test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


/** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2008-8-7 22:21:07 
* 客户端测试，在客户端调用远程对象上的远程方法，并返回结果。 
*/ 
public class HelloClient {
    public static void main(String args[]){ 
        try {
            //在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法 
            IChat rhello =(IChat) Naming.lookup("rmi://10.14.12.103:1099/RHello"); 
           
            Map<String,String> Msgs=new HashMap<String,String>();
            
            Msgs.put("remotePath",  "/home");
            Msgs.put("fileName", "Firefox-latest.tar.bz2");
            Msgs.put("localPath", "d:\\Firefox-latest.tar.bz2");
            
            
            System.out.println(rhello.recMsg(Msgs)); 
        } catch (NotBoundException e) { 
        	System.out.println("NotBoundException=="+e);
            e.printStackTrace(); 
        } catch (MalformedURLException e) { 
        	System.out.println("MalformedURLException=="+e);
            e.printStackTrace(); 
        } catch (RemoteException e) {
        	System.out.println("RemoteException=="+e);
            e.printStackTrace();
        }
    } 
}