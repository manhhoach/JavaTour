package com.manhhoach.JavaTour.helper;

import jakarta.servlet.http.HttpServletRequest;

public class UrlHelper {
    public static boolean isValidUrl(String url) {
        try {
            new java.net.URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();             // http, https
        String serverName = request.getServerName();     // domain.com
        int serverPort = request.getServerPort();        // 80, 443, etc.

        // Chuẩn bị port phần chỉ thêm nếu không phải port mặc định
        String portPart = "";
        if ((scheme.equals("http") && serverPort != 80) ||
                (scheme.equals("https") && serverPort != 443)) {
            portPart = ":" + serverPort;
        }

        return scheme + "://" + serverName + portPart;
    }
}
