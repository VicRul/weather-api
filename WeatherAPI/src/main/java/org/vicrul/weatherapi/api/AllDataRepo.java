package org.vicrul.weatherapi.api;

import java.util.List;

import org.springframework.stereotype.Component;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class AllDataRepo {
	
	private GetAllData retrofitInit() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://pogoda.atpm-air.ru/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();		
		return retrofit.create(GetAllData.class);
	}
	
	private List<String> getMetrics(Call<List<String> > searchData) throws Exception {
		Response<List<String> > execute = searchData.execute();
		System.out.println(execute);
		List<String>  result = execute.body();
		return result;
	}

	public List<String> getTemperature(String dateStart, String dateEnd) throws Exception {
		Call<List<String>> temperatures = retrofitInit().getTemperature(dateStart, dateEnd);
		return getMetrics(temperatures);
	}
	
	public List<String> getRadiation(String dateStart, String dateEnd) throws Exception {
		Call<List<String>> radiationValues = retrofitInit().getRadiation(dateStart, dateEnd);
		return getMetrics(radiationValues);
	}
}
