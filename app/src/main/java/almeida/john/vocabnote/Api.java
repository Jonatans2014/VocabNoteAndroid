package almeida.john.vocabnote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by John on 02/11/2017.
 */

public interface Api {
    String BASE_URL = "http://jonatans.pythonanywhere.com/";

    @GET("Wordlist/")
    Call<List<UserInfo>> getUserList();
}
