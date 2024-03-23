package org.rpc.service.main.search;

import org.rpc.http.XGET;
import org.rpc.http.XHeaders;
import org.rpc.http.XQuery;
import org.rpc.service.RpcReply;

import java.util.Map;

public interface DuckDuckGoService {
    @XGET("/search.json")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Map<String, Object>> search(@XQuery("api_key") String apiKey, @XQuery("engine") String engine, @XQuery(value = "q", encoded = true) String searchTerm);

    @XGET("/search.json")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<DuckDuckGoSearchResult> query(@XQuery("api_key") String apiKey, @XQuery("engine") String engine, @XQuery(value = "q", encoded = true) String searchTerm);
}
