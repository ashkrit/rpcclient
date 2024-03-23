package org.rpc.service.main.search;

import java.util.List;

public class GoogleSearchResult {

    public List<SearchResult> items;


    public static class SearchResult {
        public String title;
        public String link;

        @Override
        public String toString() {
            return "SearchResult{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    '}';

        }
    }

}
