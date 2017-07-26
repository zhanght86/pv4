package com.lcjr.mail;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;



/**
 * ½ø¶ÈÌõ
 * @author  
 * @version pvxp(2014GA)
 * @date 2014-11-20
 */
public class Test extends JFrame {
	
	public Test() {
		super();
		setSize(100, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		JProgressBar progressBar = new JProgressBar();
		getContentPane().add(progressBar, BorderLayout.NORTH);
		progressBar.setStringPainted(true);
		for (int i = 0; i < 50; i++) {
			progressBar.setValue(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Test();
	}
}