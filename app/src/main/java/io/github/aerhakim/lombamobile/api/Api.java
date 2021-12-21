package io.github.aerhakim.lombamobile.api;




import io.github.aerhakim.lombamobile.model.GetToken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

//    @GET("ewallet.php")
//    Call<GetEwallet> fetchAllUsers();
//
//    @GET("promo.php")
//    Call<GetPromo> promo();

    @FormUrlEncoded
    @POST("token.php")
    Call<GetToken> register(
            @Field("token") String token
    );
}
