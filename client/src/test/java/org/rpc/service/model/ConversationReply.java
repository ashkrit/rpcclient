package org.rpc.service.model;

import org.rpc.service.model.Conversation.Message;

import java.util.List;

public class ConversationReply {

    public final List<ReplyMessage> choices;

    public ConversationReply(List<ReplyMessage> choices) {
        this.choices = choices;
    }


    public static class ReplyMessage {
        public final int index;
        public final Message message;

        public ReplyMessage(int index, Message message) {
            this.index = index;
            this.message = message;
        }
    }

}
