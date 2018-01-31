package com.almin.retrofitlibrary.errorhandlecomponent;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(){
        super("token is invalid.");
    }
}