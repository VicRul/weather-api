package org.vicrul.weatherapi.service.api;

public class Test {

	public static void main(String[] args) {
		
		int[] apiData = {0,1,2,3,4,5,6,7,8,9};
		
		for (int metric = 0; metric < apiData.length && metric % 2 == 0; metric++) {
			System.out.println(metric);
		}
	}
}
