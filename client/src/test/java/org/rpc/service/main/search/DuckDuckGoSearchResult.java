package org.rpc.service.main.search;

import java.util.List;

public class DuckDuckGoSearchResult {

    public List<DuckSearchResult> organic_results;

    public static class DuckSearchResult {
        public String title;
        public String link;

        @Override
        public String toString() {
            return "DuckSearchResult{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    '}';

        }
    }
}
