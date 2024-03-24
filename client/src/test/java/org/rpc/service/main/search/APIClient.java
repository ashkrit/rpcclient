package org.rpc.service.main.search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.rpc.service.RpcBuilder;
import org.rpc.service.RpcReply;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIClient {

    public static void main(String[] args) throws UnsupportedEncodingException {

        //_google();
        //_ddg();
        _rawddg();

    }

    private static void _rawddg() {
        RpcBuilder searchBuilder = new RpcBuilder().serviceUrl("https://duckduckgo.com");
        DuckDuckGoSearch duckGoService = searchBuilder.create(DuckDuckGoSearch.class);

        RpcReply<List<Map<String, Object>>> r = duckGoService.suggestions("large language model");
        r.execute();
        System.out.println(r.value());


        RpcReply<String> sr = duckGoService.search("large language model");
        sr.execute();
        Document doc = Jsoup.parse(sr.value());
        Elements items = doc.getElementsByClass("result__title");
        items
                .forEach(e -> {
                    String link = e.getElementsByClass("result__a").attr("href");
                    System.out.println(e.text() + "->" + _processLinkURL(link));
                });
    }

    private static String _processLinkURL(String link) {
        String regex = "uddg=(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(link);
        if (matcher.find()) {
            String[] decode = URLDecoder.decode(matcher.group(1)).split("&");
            return decode[0];
        }
        return "";
    }

    private static void _ddg() {
        String key = System.getenv("duckduck_go");

        RpcBuilder searchBuilder = new RpcBuilder().serviceUrl("https://serpapi.com");
        DuckDuckGoService duckGoService = searchBuilder.create(DuckDuckGoService.class);


        RpcReply<Map<String, Object>> duckSearch = duckGoService.search(key, "duckduckgo", "large language model");
        duckSearch.execute();
        System.out.println(duckSearch.value());

        RpcReply<DuckDuckGoSearchResult> duckFind = duckGoService.query(key, "google", "large language model");
        duckFind.execute();
        System.out.println(duckFind.value().organic_results);
    }

    private static void _google() {
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
