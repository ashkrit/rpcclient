package org.rpc.service;

import org.rpc.http.XBody;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.model.Conversation;
import org.rpc.service.model.ConversationReply;

public interface GroqService {

    @XPOST("/openai/v1/chat/completions")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<ConversationReply> ask(@XHeader("Authorization") String apiKey, @XBody Conversation conversation);
}
