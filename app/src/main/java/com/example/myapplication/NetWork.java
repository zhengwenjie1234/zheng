package com.example.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 郑文杰 on 2017/9/26.
 */

public class NetWork {

    public static String getJson(String path) {

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = new byte[1024];
                int read = 0;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while ((read = inputStream.read(bytes)) != -1) {
                    byteArrayOutputStream.write(bytes, 0, read);
                }

                return byteArrayOutputStream.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
