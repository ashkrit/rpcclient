package org.rpc.service.main.search;

import org.rpc.http.XGET;
import org.rpc.http.XHeaders;
import org.rpc.http.XQuery;
import org.rpc.service.RpcReply;

import java.util.Map;

public interface GoogleSearchService {

    @XGET("/customsearch/v1")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Map<String, Object>> search(@XQuery("key") String apiKey, @XQuery("cx") String context, @XQuery(value = "q", encoded = true) String searchTerm);

    @XGET("/customsearch/v1")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<GoogleSearchResult> find(@XQuery("key") String apiKey, @XQuery("cx") String context, @XQuery(value = "q", encoded = true) String searchTerm);


}
