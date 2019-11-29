package com.myproject.demo.TestPage;//package cn.com.yto56.coresystem.module.logic.smsup.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Main1 {


    /**
     * xmw
     * @param dc
     * @param source
     * @return
     */
    public static String decryption(int dc, String source) {
        if(source == null) return null;
        try {
            if(dc == 15) return new String(Hex.decodeHex(source.toCharArray()),"GBK");
            if((dc & 0x0C) == 0x08) return new String(Hex.decodeHex(source.toCharArray()),"UnicodeBigUnmarked");
            return new String(Hex.decodeHex(source.toCharArray()),"ISO8859-1");
        } catch (DecoderException e) {
            System.out.println("The source decryption error:" + source);
            System.out.println(e.getMessage());
            return null;
        } catch (UnsupportedEncodingException e) {
            System.out.println("The source decryption error:" + source);
            System.out.println(e.getMessage()+"234");
            return null;
        }
    }
    /**
     * 	xmw
     * @param dc 2
     * @param source 2
     * @return 2
     */
    public static String encryption(int dc, String source) {
        if(source == null) return null;
        try {
            if(dc == 15) return new String(Hex.encodeHex(source.getBytes("GBK")));
            if((dc & 0x0C) == 0x08) return new String(Hex.encodeHex(source.getBytes("UnicodeBigUnmarked")));
            return new String(Hex.encodeHex(source.getBytes("ISO8859-1")));
        } catch (UnsupportedEncodingException e) {
            System.out.println("The source encryption error:" + source);
            System.out.println(e.getMessage()+"");
            return null;
        }
    }


    public static void main(String[] args) {
//		加密
        String a = "210045#2";
        String sec = encryption(15, a);
        System.out.println("加密后结果是: " +sec);
        String dc = decryption(15, sec);
        System.out.println("解密后结果是: " +dc);

    }

}
