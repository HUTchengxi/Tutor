package org.framework.tutor.controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class test {

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.out.println(UUID.randomUUID().toString().replaceAll("-","").substring(0,8));
    }
}
