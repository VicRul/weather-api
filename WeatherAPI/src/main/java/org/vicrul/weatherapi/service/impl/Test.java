package org.vicrul.weatherapi.service.impl;

public class Test {

	public static void main(String[] args) {
		String str = "(()())))((";
		char[] elements = str.toCharArray();
		
		int count = 0;
		for (char element : elements) {
			if(element == '(') {
				count++;
			} else {
				count--;
			}
		}
		System.out.println(count);
	}
}
