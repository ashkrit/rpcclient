package org.rpc.service;

import java.util.List;

public class ModelInfo {

    public List<Model> models;

    public static class Model {
        public final String name;
        public final String provider;

        public Model(String name, String provider) {
            this.name = name;
            this.provider = provider;
        }

        @Override
        public String toString() {
            return name + " (" + provider + ")";

        }
    }
}
