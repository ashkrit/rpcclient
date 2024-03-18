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

import java.io.IOException;
import java.util.List;

public class ClientApp {

    public static void main(String[] args) throws IOException, ParseException {


        // _local();
        //openAI();


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

    public static class Embedding {
        public final String provider;
        public final String model;
        public final String text;

        public Embedding(String provider, String model, String text) {
            this.provider = provider;
            this.model = model;
            this.text = text;
        }

        public static class EmbeddingReply {
            public int len;
            public float[] embedding;
        }

    }

    public static class OpenAIEmbedding {
        public final String model;
        public final String input;


        public OpenAIEmbedding(String model, String input) {
            this.model = model;
            this.input = input;
        }

        public static class OpenAIEmbeddingReply {

            public final List<EmbeddingValue> data;

            public OpenAIEmbeddingReply(List<EmbeddingValue> data) {
                this.data = data;
            }

            public static class EmbeddingValue {
                public final float[] embedding;

                public EmbeddingValue(float[] embedding) {
                    this.embedding = embedding;
                }
            }
        }
    }


}
