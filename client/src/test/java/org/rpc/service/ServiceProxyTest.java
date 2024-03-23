package org.rpc.service;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rpc.http.XBody;
import org.rpc.http.XGET;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.http.client.XHttpClient;
import org.rpc.service.main.model.Embedding;
import org.rpc.service.main.model.ModelInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceProxyTest {

    @Test
    public void test_get_with_no_params() {

        ModelInfo modelInfo = new ModelInfo();
        modelInfo.models = new ArrayList<>();
        modelInfo.models.add(new ModelInfo.Model("sentence-transformer", "local"));
        modelInfo.models.add(new ModelInfo.Model("bert-base-uncased", "local"));
        modelInfo.models.add(new ModelInfo.Model("bert-base-cased", "local"));

        Map<String, String> callParams = new HashMap<>();

        RpcBuilder builder = new RpcBuilder()
                .serviceUrl("http://nohost.com/")
                .client(new XHttpClient() {
                    @Override
                    public void get(String url, Map<String, String> headers, XHttpClientCallback callback) {
                        callParams.putAll(headers);
                        callParams.put("url", url);

                        callback.onComplete(XHttpResponse.success(new Gson().toJson(modelInfo)));
                    }

                    @Override
                    public void post(String url, Map<String, String> headers, Object body, XHttpClientCallback callback) {

                    }
                });

        SuperService service = builder.create(SuperService.class);

        RpcReply<ModelInfo> reply = service.list();

        reply.execute();

        assertAll(
                () -> assertEquals("http://nohost.com/list", callParams.get("url")),
                () -> assertEquals("application/json", callParams.get("Content-Type")),
                () -> assertTrue(reply.isSuccess()),
                () -> Assertions.assertEquals(modelInfo.models.get(0).name, reply.value().models.get(0).name)
        );


    }

    public interface SuperService {
        @XGET("/list")
        @XHeaders({"Content-Type: application/json"})
        RpcReply<ModelInfo> list();


        @XPOST("/embedding")
        @XHeaders({"Content-Type: application/json"})
        RpcReply<Embedding.EmbeddingReply> embedding(@XBody Embedding embedding);
    }
}
