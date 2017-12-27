package c4q.com.cutepuppyapp.puppyPackage.backend;

import c4q.com.cutepuppyapp.puppyPackage.RandoPuppy;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by D on 12/18/17.
 */

public interface PuppyService {

    @GET("api/breeds/image/random")// Tell sprogram what your getting.
    Call<RandoPuppy> getPuppy();
}
