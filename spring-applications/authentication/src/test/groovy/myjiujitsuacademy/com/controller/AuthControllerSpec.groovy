package myjiujitsuacademy.com.controller


import com.fasterxml.jackson.databind.ObjectMapper
import myjiujitsuacademy.com.AuthenticationApplication
import myjiujitsuacademy.com.utils.TestWebUtils
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.message.BasicNameValuePair
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AuthenticationApplication.class)
class AuthControllerSpec extends Specification {

    @LocalServerPort
    int port

    ObjectMapper objectMapper = new ObjectMapper()

    def "When API receives HTTP POST /auth then status is 200 and header with setCookie is present"() {
        given: "A user"
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", "fran@myjiujitsuacademy.com"));
        params.add(new BasicNameValuePair("password", "1234"));
        when: "When API receives a HTTP POST at /auth with valid username and password"

        CloseableHttpResponse response = TestWebUtils.executeHttpPost("/auth", port, new UrlEncodedFormEntity(params))
        then: "status code returned is 200"
        response.getStatusLine().getStatusCode() == 200

    }

    def "When API receives HTTP POST /auth with invalid password then error"() {
        given: "A user"
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", "fran@myjiujitsuacademy.com"));
        params.add(new BasicNameValuePair("password", password));
        when: "When API receives a HTTP POST at /auth with valid username and password"

        CloseableHttpResponse response = TestWebUtils.executeHttpPost("/auth", port, new UrlEncodedFormEntity(params))
        then: "status code returned is 302"
        response.getStatusLine().getStatusCode() == 302

        where:
        password << ["123444", "123456", "123"]

    }


}
