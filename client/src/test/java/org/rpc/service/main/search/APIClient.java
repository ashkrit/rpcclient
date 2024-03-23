package org.rpc.service.main.search;

import org.rpc.service.RpcBuilder;
import org.rpc.service.RpcReply;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class APIClient {

    public static void main(String[] args) throws UnsupportedEncodingException {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://www.googleapis.com");
        GoogleSearchService service = builder.create(GoogleSearchService.class);

        String key = System.getenv("google_search");

        RpcReply<Map<String, Object>> r = service.search(key, "61368983a3efc4386", "large language model");
        r.execute();
        Map<String, Object> value = r.value();
        List<Map<String, Object>> searchResult = (List<Map<String, Object>>) value.get("items");

        searchResult.forEach(v -> {
            System.out.println(v.get("title") + " -> " + v.get("link"));
        });

        RpcReply<GoogleSearchResult> searchResults = service.find(key, "61368983a3efc4386", "large language model");

        searchResults.execute();

        searchResults.value().items.forEach(System.out::println);



    }
}
