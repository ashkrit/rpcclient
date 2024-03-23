package org.rpc.service.main.model;

public class Embedding {
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
