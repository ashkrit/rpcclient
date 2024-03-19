package org.rpc.service.model;

import java.util.ArrayList;
import java.util.List;

public class Conversation {

    public final List<Message> messages = new ArrayList<>();
    public final String model;
    public final float temperature;
    public final int max_tokens = 1024;
    public final int top_p = 1;
    public final boolean stream;

    public Conversation(String model, float temperature, boolean stream) {
        this.model = model;
        this.temperature = temperature;
        this.stream = stream;
    }


    public void append(String role, String content) {
        this.messages.add(new Message(role, content));
    }


    public static class Message {
        public final String role;
        public final String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
