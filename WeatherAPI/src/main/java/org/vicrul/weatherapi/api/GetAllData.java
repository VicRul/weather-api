package org.vicrul.weatherapi.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetAllData {

	@GET("data.aspx?action=temperature&comparison=0")
	Call<List<String>> getTemperature(@Query("dat1") String dataStart, @Query("dat2") String dataEnd);
}
