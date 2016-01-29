package com.lncosie.zigbee.todo

public class SSL12306 {
    val CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";
    //    OkHttpClientManager.getInstance().setCertificates(new Buffer()
    //    .writeUtf8(CER_12306)
    //    .inputStream());
    //

    //    fun ssl(){
    //        client = new OkHttpClient();
    //        SSLContext sslContext = sslContextForTrustedCertificates(trustedCertificatesInputStream());
    //        client.setSslSocketFactory(sslContext.getSocketFactory());
    //
    //        try {
    //            KeyStore trusted = KeyStore.getInstance("BKS");
    //            InputStream in = context.getResources().openRawResource(R.raw.mytruststore);
    //            trusted.load(in, TRUST_STORE_PASSWORD.toCharArray());
    //            SSLContext sslContext = SSLContext.getInstance("TLS");
    //            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
    //                    TrustManagerFactory.getDefaultAlgorithm());
    //            trustManagerFactory.init(trusted);
    //            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
    //            return sslContext.getSocketFactory();
    //        } catch (Exception e) {
    //            Log.e("MyApp", e.getMessage(), e);
    //        }
    //
    //    }
    //OkHttpClient client = new OkHttpClient();
    //    KeyStore keyStore = readKeyStore(); //your method to obtain KeyStore
    //    SSLContext sslContext = SSLContext.getInstance("SSL");
    //    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    //    trustManagerFactory.init(keyStore);
    //    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    //    keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
    //    sslContext.init(keyManagerFactory.getKeyManagers(),trustManagerFactory.getTrustManagers(), new SecureRandom());
    //    client.setSslSocketFactory(sslContext.getSocketFactory());

    //    fun getUnsafeOkHttpClient() {
    //        try {
    //            // Create a trust manager that does not validate certificate chains
    //            final TrustManager[] trustAllCerts = new TrustManager[] {
    //                new X509TrustManager() {
    //                    @Override
    //                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
    //                    }
    //
    //                    @Override
    //                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
    //                    }
    //
    //                    @Override
    //                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    //                    return null;
    //                }
    //                }
    //            };
    //
    //            // Install the all-trusting trust manager
    //            final SSLContext sslContext = SSLContext.getInstance("SSL");
    //            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    //            // Create an ssl socket factory with our all-trusting manager
    //            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    //
    //            OkHttpClient okHttpClient = new OkHttpClient();
    //            okHttpClient.setSslSocketFactory(sslSocketFactory);
    //            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
    //                @Override
    //                public boolean verify(String hostname, SSLSession session) {
    //                    return true;
    //                }
    //            });
    //
    //            return okHttpClient;
    //        } catch (Exception e) {
    //            throw new RuntimeException(e);
    //        }
    //    }
}