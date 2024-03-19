package org.rpc.service;

import java.util.Optional;

public interface RpcReply<T> {

    T value();

    void execute();

    int statusCode();

    boolean isSuccess();

    Optional<String> reply();

    Optional<String> error();

    Optional<Exception> exception();

}
