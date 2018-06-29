package com.sskj.bfex.common;

/**
 * Created by Administrator on 2018/5/11.
 */

final class QuitCockroachException extends RuntimeException {
    public QuitCockroachException(String message) {
        super(message);
    }
}