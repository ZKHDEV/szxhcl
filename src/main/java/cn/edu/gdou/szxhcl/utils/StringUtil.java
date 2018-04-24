package cn.edu.gdou.szxhcl.utils;

public class StringUtil {
    public static String surround(String val, String tank){
        if(val == null){
            val = "";
        }
        return tank + val.trim() + tank;
    }
}
