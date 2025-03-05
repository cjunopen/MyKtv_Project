package com.cj.myktv.lib_netapi.datacenter.okhttp;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @Description:
 * @Author: CJ
 * @CreateDate: 2025/3/5 21:55
 */
public class TrustNoCheckValidityManagerWrapper implements X509TrustManager {

    X509TrustManager manager;

    public TrustNoCheckValidityManagerWrapper() {
        try {
            TrustManagerFactory e = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            e.init((KeyStore)null);
            TrustManager[] trustManagers = e.getTrustManagers();
            if(trustManagers.length == 1 && trustManagers[0] instanceof X509TrustManager) {
                this.manager = (X509TrustManager)trustManagers[0];
            } else {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
        } catch (GeneralSecurityException var3) {
            throw new AssertionError();
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        X509Certificate[] certificates = new X509Certificate[x509Certificates.length];
        for(int i=0;i<x509Certificates.length;i++){
            certificates[i] = new NoCheckValidityX509CertificateWrapper(x509Certificates[i]);
        }
        manager.checkClientTrusted(certificates,s);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        X509Certificate[] certificates = new X509Certificate[x509Certificates.length];
        for(int i=0;i<x509Certificates.length;i++){
            certificates[i] = new NoCheckValidityX509CertificateWrapper(x509Certificates[i]);
        }
        manager.checkClientTrusted(certificates,s);
    }


    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return manager.getAcceptedIssuers();
    }
}