package org.rpc.service;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rpc.http.client.XHttpClient;
import org.rpc.service.main.EmbeddingService;
import org.rpc.service.main.model.ModelInfo;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceProxyTest {

    @Test
    public void test_get_method() {

        ModelInfo modelInfo = new ModelInfo();
        modelInfo.models = new ArrayList<>();
        modelInfo.models.add(new ModelInfo.Model("sentence-transformer", "local"));
        modelInfo.models.add(new ModelInfo.Model("bert-base-uncased", "local"));
        modelInfo.models.add(new ModelInfo.Model("bert-base-cased", "local"));


        RpcBuilder builder = new RpcBuilder()
                .serviceUrl("http://nohost.com/")
                .client(new XHttpClient() {
                    @Override
                    public void get(String url, Map<String, String> headers, XHttpClientCallback callback) {
                        callback.onComplete(XHttpResponse.success(new Gson().toJson(modelInfo)));
                    }

                    @Override
                    public void post(String url, Map<String, String> headers, Object body, XHttpClientCallback callback) {

                    }
                });

        EmbeddingService service = builder.create(EmbeddingService.class);

        RpcReply<ModelInfo> reply = service.list();

        reply.execute();

        Assertions.assertAll(
                () -> assertTrue(reply.isSuccess())
        );


    }
}
