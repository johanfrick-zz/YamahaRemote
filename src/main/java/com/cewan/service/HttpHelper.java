package com.cewan.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class HttpHelper {


    public String doPost(String url, String data) {
        try {
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(data);
            httpPost.setEntity(se);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpPost);

            if (response != null) {
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer);
                return writer.toString();
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
