package org.vicrul.weatherapi.repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetAllData {

	@GET("data.aspx?action=temperature&dat1=Tue+Sep+08+2020&dat2=Wed+Sep+09+2020&comparison=0")
	Call<List<String>> getTemperature();
}
