package org.rpc.service.main.model;

import java.util.List;

public class GoogleEmbedding {
    public final String model;
    public final ModelContent content;

    public GoogleEmbedding(String model, ModelContent content) {
        this.model = model;
        this.content = content;
    }


    public static final class ModelPart {
        public final String text;

        public ModelPart(String text) {
            this.text = text;
        }
    }

    public static final class ModelContent {
        public final List<ModelPart> parts;

        public ModelContent(List<ModelPart> parts) {
            this.parts = parts;
        }
    }

    public static class GoogleEmbeddingReply {

        public EmbeddingValue embedding;

        public static class EmbeddingValue {
            public float[] values;
        }
    }
}
