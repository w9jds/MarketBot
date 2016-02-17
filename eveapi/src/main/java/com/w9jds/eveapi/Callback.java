package com.w9jds.eveapi;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public interface Callback<T> {

    void success(T t);

    void failure(String error);

}
