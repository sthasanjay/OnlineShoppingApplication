package API;

import java.util.List;

import model.ImageResponse;
import model.Items;
import model.LoginSignupResponse;
import model.Users;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @POST("user")
    Call<Void> insertUser(@Body Users user);

    @POST("item")
    Call<Void> addItem(@Body Items item);


    @POST("user/login")
    Call<LoginSignupResponse> loginUser(@Body Users user);


    @Multipart
    @POST("item/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("item")
    Call<List<Items>> getAllItems();

}
