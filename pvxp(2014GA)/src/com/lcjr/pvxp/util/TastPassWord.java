package com.lcjr.pvxp.util;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TastPassWord extends TestCase {

	@Test
	public void instanceSpring() {
		String beanStr = new String("beans.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(beanStr);

	}
}
