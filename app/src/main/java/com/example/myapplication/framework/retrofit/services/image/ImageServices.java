package com.example.myapplication.framework.retrofit.services.image;

import android.content.Context;
import android.net.Uri;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.BASE_URL;
import static com.example.myapplication.RecepiesConstant.IMAGE_API_URL;
import static com.example.myapplication.RecepiesConstant.IMAGE_PARAMETER;

@Singleton
public class ImageServices {

    private final Picasso picasso;

    @Inject
    public ImageServices(Context context, @Named("picasso-client") OkHttpClient picassoClient) {
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(picassoClient))
                .indicatorsEnabled(true)
                //.loggingEnabled(true)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Timber.e("Picasso loading image failed URL: %s with exception %s", uri, exception.getMessage());
                    }
                })
                .build();

        Picasso.setSingletonInstance(picasso);
    }

    //Simple url: https://vy5trnewne.execute-api.eu-central-1.amazonaws.com/Prod/api/images/getimage?imageId=1
    public static String getUrlForImage(int imageId) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_URL);
        builder.append(IMAGE_API_URL);
        builder.append(IMAGE_PARAMETER + imageId);

        Timber.d("ImageService: create url: %s", builder.toString());
        return builder.toString();
    }

    public Picasso getPicasso() {
        return picasso;
    }
}
