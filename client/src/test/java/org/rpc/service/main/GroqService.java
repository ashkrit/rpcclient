package org.rpc.service.main;

import org.rpc.http.XBody;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.RpcReply;
import org.rpc.service.main.model.Conversation;
import org.rpc.service.main.model.ConversationReply;

public interface GroqService {

    @XPOST("/openai/v1/chat/completions")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<ConversationReply> ask(@XHeader("Authorization") String apiKey, @XBody Conversation conversation);
}
