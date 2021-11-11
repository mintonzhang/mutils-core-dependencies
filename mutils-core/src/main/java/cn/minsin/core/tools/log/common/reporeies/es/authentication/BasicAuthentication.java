package cn.minsin.core.tools.log.common.reporeies.es.authentication;

import cn.minsin.core.tools.secure.Base64Util;

import java.net.HttpURLConnection;

public class BasicAuthentication implements Authentication {
    public void addAuth(HttpURLConnection urlConnection, String body) {
        String userInfo = urlConnection.getURL().getUserInfo();
        if (userInfo != null) {
            String basicAuth = "Basic " + Base64Util.encode(userInfo.getBytes());
            urlConnection.setRequestProperty("Authorization", basicAuth);
        }
    }
}
