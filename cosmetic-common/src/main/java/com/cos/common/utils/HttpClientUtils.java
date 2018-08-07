package com.cos.common.utils;

import com.alibaba.fastjson.JSON;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpClientUtils {

    private static RequestConfig requestConfig;

    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(50000);
        configBuilder.setSocketTimeout(50000);
        configBuilder.setConnectionRequestTimeout(50000);
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    private static Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", createSSLConnSocketFactory())
            .build();

    private static PoolingHttpClientConnectionManager collectionManager = new PoolingHttpClientConnectionManager(registry) {
        {
            this.setMaxTotal(200);
            this.setDefaultMaxPerRoute(this.getMaxTotal());
        }
    };

    public static String doPost(String url, Map<String, String> param) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (param != null) {
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.defaultCharset()));
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(collectionManager)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            log.error("Do post error.\nUrl is {}.\nData is {}.",
                    JSON.toJSONString(url), JSON.toJSONString(param));
            log.error("异常信息:", e);
            throw new RuntimeException("Post error.");
        } finally {
            IOUtils.closeQuietly(response);
        }
    }

    /**
     * post发送 json
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(collectionManager)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            log.error("Do post error.\nUrl is {}.\nData is {}.",
                    JSON.toJSONString(url), json);
            log.error("异常信息:", e);
            throw new RuntimeException("Post error.");
        } finally {
            IOUtils.closeQuietly(response);
        }
    }

    /**
     * post发送 json
     * @param url
     * @param json
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }


    public static HttpPost createDefaultHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        return httpPost;
    }

   
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            return new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Create ConnFactory error.", e);
        }
    }

}
