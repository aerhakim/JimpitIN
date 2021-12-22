package io.github.aerhakim.lombamobile.api;




import io.github.aerhakim.lombamobile.model.GetAgenda;
import io.github.aerhakim.lombamobile.model.GetNotifikasi;
import io.github.aerhakim.lombamobile.model.GetToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("agenda")
    Call<GetAgenda> agenda();

    @GET("notifikasi")
    Call<GetNotifikasi> notifikasi();

    @FormUrlEncoded
    @POST("application/controllers/token.php")
    Call<GetToken> register(
            @Field("token") String token
    );

}
