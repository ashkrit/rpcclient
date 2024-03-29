package org.rpc.http.client;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ApacheHTTPClient implements XHttpClient {

    private final Logger logger = LoggerFactory.getLogger(ApacheHTTPClient.class);


    @Override
    public void get(String url, Map<String, String> headers, XHttpClientCallback callback) {

        logger.info("Calling {}", url);
        HttpGet method = new HttpGet(url);
        headers.forEach(method::addHeader);

        _execute(callback, method);
    }

    private void _execute(XHttpClientCallback callback, HttpUriRequest method) {

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(method)) {

            int code = response.getCode();
            String reply = EntityUtils.toString(response.getEntity());
            if (code == CODE_OK) {
                callback.onComplete(XHttpResponse.success(reply));
            } else {
                callback.onComplete(XHttpResponse.error(code, reply));
            }

        } catch (Exception e) {
            logger.error("Failed", e);
            callback.onComplete(XHttpResponse.error(e));
        }
    }

    @Override
    public void post(String url, Map<String, String> headers, Object body, XHttpClientCallback callback) {

        logger.info("Calling {}", url);

        HttpPost postMethod = new HttpPost(url);
        headers.forEach(postMethod::addHeader);
        postMethod.setEntity(new StringEntity(new Gson().toJson(body)));

        _execute(callback, postMethod);
    }
}
