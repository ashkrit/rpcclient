package org.rpc.http.client;

import java.util.Map;
import java.util.Optional;

public interface XHttpClient {

    int CODE_OK = 200;

    void get(String url, Map<String, String> headers, XHttpClientCallback callback);

    void post(String url, Map<String, String> headers, Object body, XHttpClientCallback callback);


    interface XHttpClientCallback {
        void onComplete(XHttpResponse response);
    }

    class XHttpResponse {
        public final int statusCode;
        public final String reply;
        public final String error;
        public final Exception exception;

        public XHttpResponse(int statusCode, String reply, String error, Exception exception) {
            this.statusCode = statusCode;
            this.reply = reply;
            this.error = error;
            this.exception = exception;
        }

        public static XHttpResponse success(String reply) {
            return new XHttpResponse(CODE_OK, reply, null, null);
        }

        public static XHttpResponse error(int code, String error) {
            return new XHttpResponse(code, null, error, null);
        }

        public static XHttpResponse error(Exception e) {
            return new XHttpResponse(-1, null, null, e);
        }

        public Optional<String> reply() {
            return Optional.ofNullable(reply);
        }

        public Optional<String> error() {
            return Optional.ofNullable(error);
        }

        public Optional<Exception> exception() {
            return Optional.ofNullable(exception);
        }
    }
}
