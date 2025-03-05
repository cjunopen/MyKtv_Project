package com.cj.myktv.lib_netapi.api;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/4 22:50
 */
public class BaseApi<T> {

    private Retrofit mRetrofit;

    private String mBaseUrl;

    protected T mApi;

    protected String tag;

    @SuppressWarnings("unchecked")
    public T getApi() {
        if (mApi == null) {
            Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            Class<T> tClass = (Class<T>) types[0];
            mApi = getRetrofit().create(tClass);
            String name = tClass.getName();
            tag = name.substring(name.lastIndexOf(".") + 1, name.lastIndexOf("$"));
        }
        return mApi;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            setBaseUrl("http://localhost");
        }
        return mRetrofit;
    }

    private Retrofit createRetrofit(String url) {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        String myDate = json.getAsString();
                        return new Date(Long.parseLong(myDate));
                    }
                })
                .registerTypeAdapter(Date.class, new DateTypeAdapter());

        GsonConverterFactory factory = GsonConverterFactory.create(builder.create());
        OkHttpClient.Builder okhttpBuilder = createOkhttpBuilder();
        addDefaultInterceptor(okhttpBuilder);
        mRetrofit = new Retrofit.Builder()
                // 设置网络请求的Url地址
                .baseUrl(url)
                // 设置数据解析器
                .addConverterFactory(factory)
                // 设置网络请求适配器，使其支持RxJava与RxAndroid
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okhttpBuilder.build())
                .build();
        return mRetrofit;
    }

    protected HttpLoggingInterceptor.Level getLogLevel() {
        return HttpLoggingInterceptor.Level.BODY;
    }

    /**
     * @return 默认okhttp builder
     */
    protected OkHttpClient.Builder createOkhttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .callTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        return builder;
    }

    protected void addDefaultInterceptor(OkHttpClient.Builder builder){
        Interceptor interceptor = createLogInterceptor();
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }

        interceptor = createAddHeaderInterceptor();
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
    }

    private Interceptor createAddHeaderInterceptor(){
        return new Interceptor() {
            @Override
            public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                addHeader(builder);
                return chain.proceed(builder.build());
            }
        };
    }

    protected void addHeader(Request.Builder builder){
        builder.addHeader("User-agent", "Mozilla/4.0")
                .addHeader("Connection", "close");
    }

    /**
     * 设置拦截器 打印日志
     *
     * @return
     */
    protected Interceptor createLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Timber.tag(tag).i(s);
            }
        });
        // 显示日志
        interceptor.setLevel(getLogLevel());
        return interceptor;
    }

    public void setBaseUrl(String baseUrl) {
        if (baseUrl == null) {
            return;
        }
        mBaseUrl = baseUrl;
        createRetrofit(baseUrl);
        mApi = null;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }
}
