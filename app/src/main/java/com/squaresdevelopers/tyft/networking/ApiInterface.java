package com.squaresdevelopers.tyft.networking;


import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.squaresdevelopers.tyft.dataModels.customerSignUpModels.CustomerSignUpResponseModel;
import com.squaresdevelopers.tyft.dataModels.editProfileModels.EditProfileResponseModel;
import com.squaresdevelopers.tyft.dataModels.fogotDataModel.ChangePasswordModel;
import com.squaresdevelopers.tyft.dataModels.fogotDataModel.ForgotModel;
import com.squaresdevelopers.tyft.dataModels.fogotDataModel.VerifyCodeModel;
import com.squaresdevelopers.tyft.dataModels.loginModels.LoginResponseModel;
import com.squaresdevelopers.tyft.dataModels.sellerProfileModels.SellerProfileResponseModel;
import com.squaresdevelopers.tyft.dataModels.sellerSignUpModels.SellerSignUpResponseModel;
import com.squaresdevelopers.tyft.dataModels.tyftUserDataModels.TyftUserResponseModel;
import com.squaresdevelopers.tyft.dataModels.updatePictureModels.UpdatePictureResponeModel;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eapple on 29/08/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login_user1")
    Call<LoginResponseModel> userLogin(@Field("email") String email,
                                       @Field("password") String password,
                                       @Field("user_type") String userType);

    @FormUrlEncoded
    @POST("register_user2")
    Call<CustomerSignUpResponseModel> customerSignUp(@Field("username") String username,
                                                     @Field("email") String email,
                                                     @Field("password") String password);


    @Multipart
    @POST("register_user1")
    Call<SellerSignUpResponseModel> sellerSignUp(@Part("email") RequestBody email,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("image1") RequestBody fileName,
                                                 @Part MultipartBody.Part photo2,
                                                 @Part("image2") RequestBody fileName2,
                                                 @Part("password") RequestBody name,
                                                 @Part("text_field") RequestBody textfield);

    @FormUrlEncoded
    @POST("edit-profile")
    Call<EditProfileResponseModel> sellerEditProfile(@Field("api_token") String password,
                                                     @Field("email") String username,
                                                     @Field("text_field") String email);


    @Multipart
    @POST("updateprofile")
    Call<UpdatePictureResponeModel> sellerUpdateProfile(@Part("api_token") RequestBody token,
                                                        @Part MultipartBody.Part photo,
                                                        @Part("image1") RequestBody fileName,
                                                        @Part MultipartBody.Part photo2,
                                                        @Part("image2") RequestBody fileName2);

    @POST("own-profile/{version}")
    Call<SellerProfileResponseModel> getUser2Profile(@Path("version") int id);

    @GET("getUser1")
    Call<TyftUserResponseModel> getUser1();


    @FormUrlEncoded
    @POST("forgot-password")
    Call<ForgotModel> sentEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("verify")
    Call<VerifyCodeModel> verifyCode(@Field("email") String email,
                                     @Field("verification_code") String code);

    @FormUrlEncoded
    @POST("change-password")
    Call<ChangePasswordModel> changePassword(@Field("password") String password,
                                             @Field("api_token") String apiToken,
                                             @Field("password_confirmation") String confirm);
}
