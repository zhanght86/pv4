package com.rmi.test;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2008-8-7 21:50:02 
* ����һ��Զ�̽ӿڣ�����̳�Remote�ӿڣ�������ҪԶ�̵��õķ��������׳�RemoteException�쳣 
*/ 
public interface IChat extends Remote { 

    /** 
     * �򵥵ķ��ء�Hello World��"���� 
     * @return ���ء�Hello World��"���� 
     * @throws java.rmi.RemoteException 
     */ 
    public String helloWorld() throws RemoteException; 

    /** 
     * һ���򵥵�ҵ�񷽷������ݴ��������������Ӧ���ʺ��� 
     * @param someBodyName  ���� 
     * @return ������Ӧ���ʺ��� 
     * @throws java.rmi.RemoteException 
     */ 
    public String recMsg(Map<String,String> Msgs) throws RemoteException; 
}