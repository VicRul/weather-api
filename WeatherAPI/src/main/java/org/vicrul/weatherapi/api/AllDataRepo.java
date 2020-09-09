package org.vicrul.weatherapi.api;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class AllDataRepo {
	
	public List<String> getData(String dataStart, String dataEnd) throws IOException {
		
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://pogoda.atpm-air.ru/")
				.addConverterFactory(GsonConverterFactory.create()).build();
		GetAllData allData = retrofit.create(GetAllData.class);
		Call<List<String>> responseData = allData.getTemperature(dataStart, dataEnd);
		Response<List<String>> execute = responseData.execute();
		List<String> result = execute.body();
		return result;
	}
}
