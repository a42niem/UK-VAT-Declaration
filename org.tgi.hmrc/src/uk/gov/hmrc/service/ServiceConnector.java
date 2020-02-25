package uk.gov.hmrc.service;

import com.google.common.base.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.tgi.util.HmrcUtil;

import uk.gov.hmrc.model.UnauthorizedException;

import java.io.IOException;


public class ServiceConnector {

    private final HttpClient client;

    public ServiceConnector() {
        client = HttpClientBuilder.create().build();
    }

    public String get(String url, String acceptHeader, Optional<String> bearerToken) throws UnauthorizedException {
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", acceptHeader);
        if (bearerToken.isPresent()) {
            request.addHeader("Authorization", "Bearer " + bearerToken.get());
        }

        // Fraud-Prevention-Headers for application connection method "OTHER_DIRECT"
//        request.addHeader("Gov-Client-Connection-Method", "OTHER_DIRECT");
//        request.addHeader("Gov-Client-Public-IP", HmrcUtil.publicIP);
        // Sandbox Test data
//        request.addHeader("Gov-Test-Scenario", "MULTIPLE_PAYMENTS");

        try {
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 401) {
                throw new UnauthorizedException();
            }
            return EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public String post(String url, String acceptHeader, Optional<String> bearerToken,Optional<String> json) throws UnauthorizedException {
        HttpPost request = new HttpPost(url);
        request.addHeader("Accept", acceptHeader);
        if (bearerToken.isPresent()) {
            request.addHeader("Authorization", "Bearer " + bearerToken.get());
        }
        if (json.isPresent()) {
        	StringEntity requestEntity = new StringEntity(
        		    json.get(),
        		    ContentType.APPLICATION_JSON);
        	request.setEntity(requestEntity);
        }

        try {
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 201) { // Created
           	 return EntityUtils.toString(response.getEntity());	
           }
           else { // TODO get the explicite error message (in response)
           	return "Error " + response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase() + response.getStatusLine().toString();
           }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
