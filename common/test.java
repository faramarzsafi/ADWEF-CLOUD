package common;

import java.awt.Toolkit;



public class test {

	public static void main(String[] args) {
		for (int i=0; i<10000; i++)
		Toolkit.getDefaultToolkit().beep();
	}
}


