package myjiujitsuacademy.com.utils

import org.apache.http.HttpEntity
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils


class TestWebUtils {
    static CloseableHttpResponse executeHttpGet(String path, int port) {
        HttpGet httpGet = new HttpGet("http://localhost:" + port + path)
        return HttpClientBuilder.create().build().execute(httpGet)
    }

    static CloseableHttpResponse executeHttpPost(String path, int port) {
        HttpPost httpPost = new HttpPost("http://localhost:" + port + path)
        return HttpClientBuilder.create().build().execute(httpPost)
    }

    static CloseableHttpResponse executeHttpPost(String path, int port, UrlEncodedFormEntity body) {
        HttpPost httpPost = new HttpPost("http://localhost:" + port + path)
        httpPost.setEntity(body)
        return HttpClientBuilder.create().build().execute(httpPost)
    }

    static CloseableHttpResponse executeHttpPut(String path, int port) {
        HttpPut httpPut = new HttpPut("http://localhost:" + port + path)
        return HttpClientBuilder.create().build().execute(httpPut)
    }

    static String extractBody(CloseableHttpResponse response) {
        HttpEntity entity = response.getEntity()
        return EntityUtils.toString(entity)
    }
}
