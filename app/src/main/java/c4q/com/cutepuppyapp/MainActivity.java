package c4q.com.cutepuppyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import c4q.com.cutepuppyapp.puppyPackage.RandoPuppy;
import c4q.com.cutepuppyapp.puppyPackage.backend.PuppyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "JSON?";
    private PuppyService puppyService;
    private ImageView imageView;
    private Button newPuppy;
    private String puppyUrl;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.puppy_imageview);
        newPuppy = (Button) findViewById(R.id.new_puppy_button);

        Retrofit retrofit = new Retrofit.Builder() //This Starts the builder process.
                .baseUrl("https://dog.ceo")//this is our base url, or gateway to JSON. Our Full End point.
                .addConverterFactory(GsonConverterFactory.create())//does JSON Serialization/Deserialization.
                .build();//ends transaction and returns us to full retrofit instance.

        // creating the service so we can use it to make request:
        puppyService = retrofit.create(PuppyService.class);

        newPuppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call<RandoPuppy> puppy = puppyService.getPuppy();
                puppy.enqueue(new Callback<RandoPuppy>() {
                    @Override
                    public void onResponse(Call<RandoPuppy> call, Response<RandoPuppy> response) {
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                        puppyUrl = response.body().getMessage();
                        Picasso.with(getApplicationContext())//using the current content
                                .load(response.body().getMessage())//load the image being pointed to in the link
                                .into(imageView);// into the ImageView you want
                    }

                    @Override
                    public void onFailure(Call<RandoPuppy> call, Throwable t) {
                        Log.d(TAG, "onResponse: " + t.toString());
                    }
                });
            }
        });

        if (savedInstanceState != null) {
            String savedPuppy = savedInstanceState.getString("puppyUrl");
            puppyUrl = savedPuppy;
            Picasso.with(getApplicationContext())
                    .load(savedPuppy)
                    .into(imageView);
        } else {
            newPuppy.callOnClick();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("puppyUrl", puppyUrl);
    }
}