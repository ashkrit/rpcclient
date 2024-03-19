package org.rpc.http;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.rpc.http.client.ApacheHTTPClient;
import org.rpc.http.client.XHttpClient;
import org.rpc.service.model.Conversation;
import org.rpc.service.model.Embedding;
import org.rpc.service.model.OpenAIEmbedding;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientApp {

    public static void main(String[] args) throws IOException, ParseException {


        // _local();
        //openAI();

        //_get();
        _post();


    }

    private static void _post() {
        XHttpClient client = new ApacheHTTPClient();
        Conversation conversation = new Conversation("llama2-70b-4096", 0.5f, false);
        conversation.append("user", "What is prompt Engineering");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + System.getenv("gorq_key"));

        client.post("https://api.groq.com/openai/v1/chat/completions", headers, conversation, c -> {
            System.out.println(c.statusCode);
            if (c.statusCode == 200) {
                System.out.println(c.reply().get());
            } else {
                System.out.println(c.error());
                System.out.println(c.exception());
            }
        });
    }

    private static void _get() {
        XHttpClient client = new ApacheHTTPClient();
        client.get("http://127.0.0.1:9090/list", Collections.emptyMap(), c -> {
            System.out.println(c.statusCode);
            if (c.statusCode == 200) {
                System.out.println(c.reply().get());
            } else {
                System.out.println(c.error());
                System.out.println(c.exception());
            }
        });
    }

    private static void openAI() throws IOException, ParseException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost postMethod = new HttpPost("https://api.openai.com/v1/embeddings");
        postMethod.setHeader("Content-Type", "application/json");
        postMethod.setHeader("Authorization", "Bearer " + System.getenv("gpt_key"));

        OpenAIEmbedding openAIEmbedding = new OpenAIEmbedding("text-embedding-3-small", "How are you");

        postMethod.setEntity(new StringEntity(new Gson().toJson(openAIEmbedding)));


        try (CloseableHttpResponse response = httpclient.execute(postMethod);) {
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            System.out.println(response.getCode());
        }
    }

    private static void _local() throws IOException, ParseException {
        HttpGet method = new HttpGet("http://127.0.0.1:9090/list");

        CloseableHttpClient httpclient = HttpClients.createDefault();


        try (CloseableHttpResponse response = httpclient.execute(method);) {
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            System.out.println(response.getCode());
        }


        HttpPost postMethod = new HttpPost("http://127.0.0.1:9090/embedding");
        postMethod.setHeader("Content-Type", "application/json");

        Embedding e = new Embedding("google", "embedding-001", "How are you");

        postMethod.setEntity(new StringEntity(new Gson().toJson(e)));


        try (CloseableHttpResponse response = httpclient.execute(postMethod);) {
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            System.out.println(response.getCode());
        }
    }


}
