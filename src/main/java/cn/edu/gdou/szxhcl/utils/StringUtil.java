package cn.edu.gdou.szxhcl.utils;

public class StringUtil {
    public static String surround(String val, String tank){
        if(val == null){
            val = "";
        }
        return tank + val.trim() + tank;
    }

    public static Boolean isEmpty(String val){
        Boolean isEmpty = true;
        if(val != null && !val.trim().equals("")) {
            isEmpty = false;
        }
        return isEmpty;
    }

    public static String prettyDataSize(Long size) {
        String sizeStr = null;
        Long preSize = size;
        size /= 1024;
        if(size > 0) {
            preSize = size;
            size /= 1024;
            if(size > 0){
                preSize = size;
                size /= 1024;
                if(size > 0){
                    preSize = size;
                    size /= 1024;
                    if(size > 0){
                        sizeStr = preSize + " GB";
                    } else {
                        sizeStr = preSize + " GB";
                    }
                } else {
                    sizeStr = preSize + " MB";
                }
            } else {
                sizeStr = preSize + " KB";
            }
        } else {
            sizeStr = preSize + " B";
        }

        return sizeStr;
    }
}
