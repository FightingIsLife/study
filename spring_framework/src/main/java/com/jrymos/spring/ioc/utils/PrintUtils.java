package com.jrymos.spring.ioc.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintUtils {
    public static void errorLog(String...ss) {
        StackTraceElement st = new RuntimeException().getStackTrace()[1];
        System.err.println("%s\t%s\t%s\t%d\t".formatted(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()),
                st.getClassName(), st.getMethodName(), st.getLineNumber()) + String.join("\t", ss));
    }
}
