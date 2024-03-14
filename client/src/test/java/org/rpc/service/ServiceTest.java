package org.rpc.service;

import org.rpc.processor.RpcBuilder;

public class ServiceTest {

    public static void main(String[] args) {

        RpcBuilder builder = new RpcBuilder()
                .serviceUrl("http://localhost:9000");

        SampleService s = builder.create(SampleService.class);

        System.out.println(s);

        System.out.println(s.list());
        System.out.println(s.embeddings("100","99999" , new Embedding("sentence","what is LLM")));
        System.out.println(s.search("google"));
    }
}
