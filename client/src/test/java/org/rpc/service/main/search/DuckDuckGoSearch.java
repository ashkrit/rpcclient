package org.rpc.service.main.search;

import org.rpc.http.XGET;
import org.rpc.http.XHeaders;
import org.rpc.http.XQuery;
import org.rpc.service.RpcReply;

import java.util.List;
import java.util.Map;

public interface DuckDuckGoSearch {
    @XGET("/ac")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<List<Map<String, Object>>> suggestions(@XQuery(value = "q", encoded = true) String searchTerm);

    @XGET("/html")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<String> search(@XQuery(value = "q", encoded = true) String searchTerm);

}
