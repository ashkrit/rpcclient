package org.rpc.service.main;

import org.rpc.service.*;
import org.rpc.service.main.model.*;

import org.rpc.service.main.model.ConversationReply.ReplyMessage;
import org.rpc.service.main.model.GoogleEmbedding.GoogleEmbeddingReply;
import org.rpc.service.main.model.GoogleEmbedding.ModelContent;
import org.rpc.service.main.model.GoogleEmbedding.ModelPart;
import org.rpc.service.main.model.OpenAIEmbedding.OpenAIEmbeddingReply;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServiceTest {

    public static void main(String[] args) {

        //_local();
        //_google();
        //_openAI();
        _groq("llama2-70b-4096");
        _groq("mixtral-8x7b-32768");
        _groq("gemma-7b-it");

    }

    private static void _groq(String modelName) {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://api.groq.com/");
        GroqService service = builder.create(GroqService.class);

        Conversation conversation = new Conversation(modelName, 1.0f, false);
        conversation.append("user", "What is prompt Engineering");

        RpcReply<ConversationReply> reply = service.ask("Bearer " + System.getenv("gorq_key"), conversation);
        reply.execute();

        if (reply.isSuccess()) {
            List<ReplyMessage> choices = reply.value().choices;
            System.out.println(choices.get(0).message.content);
        } else {
            System.out.println(reply.error());
            System.out.println(reply.exception());
        }
    }

    private static void _openAI() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://api.openai.com/");
        OpenAIService service = builder.create(OpenAIService.class);

        OpenAIEmbedding openAIEmbedding = new OpenAIEmbedding("text-embedding-3-small", "How are you");
        String apiKey = "Bearer " + System.getenv("gpt_key");
        RpcReply<OpenAIEmbeddingReply> reply = service.embedding(apiKey, openAIEmbedding);

        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(Arrays.toString(reply.value().data.get(0).embedding));
        } else {
            System.out.println(reply.exception());
            System.out.println(reply.error());
        }


    }

    private static void _google() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://generativelanguage.googleapis.com");
        GoogleAIService service = builder.create(GoogleAIService.class);

        RpcReply<GoogleEmbeddingReply> reply = service
                .embedding(System.getenv("gemma_key"), createGEmbeddings("how are you", "models/embedding-001"));

        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(Arrays.toString(reply.value().embedding.values));
        } else {
            System.out.println(reply.error());
            System.out.println(reply.exception());
        }
    }

    private static GoogleEmbedding createGEmbeddings(String text, String modelName) {
        ModelContent content = new ModelContent(Collections.singletonList(new ModelPart(text)));
        return new GoogleEmbedding(modelName, content);
    }

    private static void _local() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("http://localhost:9090/");

        EmbeddingService service = builder.create(EmbeddingService.class);

        RpcReply<ModelInfo> list = service.list();
        list.execute();

        System.out.println(list.value().models);

        Embedding e = new Embedding("google", "embedding-001", "How are you");
        RpcReply<Embedding.EmbeddingReply> embedding = service.embedding(e);
        embedding.execute();

        System.out.println(Arrays.toString(embedding.value().embedding));
    }
}
