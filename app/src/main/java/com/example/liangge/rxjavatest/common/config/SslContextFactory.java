package com.example.liangge.rxjavatest.common.config;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by guhongliang on 2017/3/29.
 * https证书设置
 */

public class SslContextFactory {
    private static final String CLIENT_TRUST_PASSWORD = "changeit";//信任证书密码，该证书默认密码是changeit
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
    SSLContext sslContext = null;

    public SSLContext getSslSocket() {
        //取得SSL的SSLContext实例
        try {
            sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        addSslCertificate();
        return sslContext;
    }

    /**
     * 添加证书
     */
    private void addSslCertificate() {
//        try {
//            //取得TrustManagerFactory的X509密钥管理器实例
//            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
//            //取得BKS密库实例
//            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
//            InputStream is = MyApplication.getInstance().getResources().openRawResource(R.raw.suplcerts);
//            try {
//                tks.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
//            } finally {
//                is.close();
//            }
//            //初始化密钥管理器
//            trustManager.init(tks);
//            //初始化SSLContext
//            sslContext.init(null, trustManager.getTrustManagers(), null);
//        } catch (Exception e) {
//            Log.e("SslContextFactory", e.getMessage());
//        }
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
        mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        return mBuilder.build();
    }


    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance(CLIENT_AGREEMENT);
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            Log.e("SSLSocketFactory", e.toString());
        }
        return sSLSocketFactory;
    }

    public static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
