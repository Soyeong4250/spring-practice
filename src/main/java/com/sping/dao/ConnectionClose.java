package com.sping.dao;

public class ConnectionClose {
    public static void close(AutoCloseable...autoCloseables) {
        for(AutoCloseable ac : autoCloseables) {
            try {
                if (ac != null) {
                    ac.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
