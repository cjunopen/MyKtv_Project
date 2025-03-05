package com.cj.myktv.lib_netapi.datacenter.okhttp;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 21:58
 */
public class OkhttpFactory {

    public static OkHttpClient.Builder getBuilder(){
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .allEnabledCipherSuites()
                .build();

        TrustNoCheckValidityManagerWrapper trustNoCheckValidityManagerWrapper = new TrustNoCheckValidityManagerWrapper();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(10 * 1000,TimeUnit.MILLISECONDS)
                .sslSocketFactory(createSSLSocketFactory(trustNoCheckValidityManagerWrapper), trustNoCheckValidityManagerWrapper);
        return builder;
    }

    private static SSLSocketFactory createSSLSocketFactory(TrustManager trustManager) {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustManager},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }
}
