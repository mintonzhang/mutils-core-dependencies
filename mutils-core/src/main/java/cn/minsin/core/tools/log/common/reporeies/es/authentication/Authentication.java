package cn.minsin.core.tools.log.common.reporeies.es.authentication;

import cn.minsin.core.tools.secure.Base64Util;

import java.net.HttpURLConnection;

public interface Authentication {
    static Authentication basic2Authentication() {
        return (urlConnection, body) -> {
            String userInfo = urlConnection.getURL().getUserInfo();
            if (userInfo != null) {
                String basicAuth = "Basic " + Base64Util.encode(userInfo.getBytes());
                urlConnection.setRequestProperty("Authorization", basicAuth);
            }
        };
    }

    /**
     * Modify the given urlConnection for whatever authentication scheme is used.
     *
     * @param urlConnection the connection to the server
     * @param body          the message being sent
     */
    void authentication(HttpURLConnection urlConnection, String body);
}
