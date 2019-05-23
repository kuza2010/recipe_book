package com.example.myapplication.framework.dagger.modules;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.squareup.picasso.OkHttp3Downloader;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import butterknife.internal.Utils;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.BASE_URL;
import static com.example.myapplication.RecepiesConstant.CACHE_CONTROL_HEADER;
import static com.example.myapplication.RecepiesConstant.CACHE_HTTP_SIZE;
import static com.example.myapplication.RecepiesConstant.CACHE_IMAGE_SIZE;
import static com.example.myapplication.RecepiesConstant.CALL_TIMEOUT;
import static com.example.myapplication.RecepiesConstant.CONNECTION_TIMEOUT;
import static com.example.myapplication.RecepiesConstant.Mb;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    public OkHttpClient provideRetrofitClient(@Named("cache-interceptor") Interceptor cacheInterceptor, @Named("retrofit-cache") Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(cacheInterceptor)
                .connectTimeout(CONNECTION_TIMEOUT,TimeUnit.SECONDS)
                .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Named("cache-interceptor")
    @Singleton
    @Provides
    public Interceptor provideCacheInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Timber.d("===   Cache interceptor begin   ===");

                Timber.d("Intercept request! Method %s, url %s", originalRequest.method().toUpperCase(), originalRequest.url());
                Timber.d("Request header: %s", originalRequest.headers());
                Timber.d("Start requests....");

                Response response = chain.proceed(originalRequest);
                Timber.d("Getting response! Response cache header %s", response.header(CACHE_CONTROL_HEADER));
                Timber.d("Response sent time %d", response.sentRequestAtMillis());
                Timber.d("Response received time %d", response.receivedResponseAtMillis());

                Timber.d("===   Cache interceptor end   ===");
                return response;

            }
        };

        Timber.d("Cache interceptor created!");
        return interceptor;
    }

    @Named("picasso-image-interceptor")
    @Singleton
    @Provides
    public Interceptor providePicassoInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Timber.d("===   Picasso interceptor begin   ===");
                Timber.d("Intercept request! Method %s, url %s", originalRequest.method().toUpperCase(), originalRequest.url());
                Timber.d("Start requests....");

                Response response = chain.proceed(originalRequest);
                Timber.d("Getting response!");
                Timber.d("Response sent time %d", response.sentRequestAtMillis());
                Timber.d("Response received time %d", response.receivedResponseAtMillis());

                Timber.d("Decode response!");
                MediaType contentType = response.body().contentType();
                String base64String = response.body().string().replace("\"", "");
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                Timber.d("Create a correct response body.");
                ResponseBody body = ResponseBody.create(contentType, decodedString);

                Timber.d("===   Cache interceptor end   ===");
                return response
                        .newBuilder()
                        .body(body)
                        .build();
            }
        };
    }

    @Named("picasso-client")
    @Singleton
    @Provides
    public OkHttpClient providePicassoClient(@Named("picasso-image-interceptor") Interceptor picassoInterceptor, @Named("picasso-cache") Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(picassoInterceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(CONNECTION_TIMEOUT,TimeUnit.SECONDS)
                .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
                //.addInterceptor(cacheInterceptor)
                .cache(cache)
                .build();
    }

    @Named("picasso-cache")
    @Singleton
    @Provides
    public Cache providePicassoCache(Context context) {
        return new Cache(context.getCacheDir(), CACHE_IMAGE_SIZE);
    }

    @Named("retrofit-cache")
    @Provides
    public Cache provideRetrofitCache(Context context) {
        return new Cache(context.getCacheDir(), CACHE_HTTP_SIZE);
    }
}