package io.github.aerhakim.lombamobile.api;




import io.github.aerhakim.lombamobile.model.GetAgenda;
import io.github.aerhakim.lombamobile.model.GetHeros;
import io.github.aerhakim.lombamobile.model.GetJimpitan;
import io.github.aerhakim.lombamobile.model.GetNotifikasi;
import io.github.aerhakim.lombamobile.model.GetSampah;
import io.github.aerhakim.lombamobile.model.GetToken;
import io.github.aerhakim.lombamobile.model.PostPutDelHeros;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @GET("agenda")
    Call<GetAgenda> agenda();

    @GET("notifikasi")
    Call<GetNotifikasi> notifikasi();

    @GET("sampah")

    Call<GetSampah> sampah();
    @GET("jimpitan")
    Call<GetJimpitan> jimpitan();

    @FormUrlEncoded
    @POST("token")
    Call<GetToken> register(
            @Field("token") String token
    );

}
