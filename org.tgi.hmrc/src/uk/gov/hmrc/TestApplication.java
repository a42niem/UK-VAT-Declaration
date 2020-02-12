package uk.gov.hmrc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.gov.hmrc.model.Token;
import uk.gov.hmrc.model.UnauthorizedException;
import uk.gov.hmrc.model.VATReturn;
import uk.gov.hmrc.model.VATReturnResponse;
import uk.gov.hmrc.parser.VATParser;
import uk.gov.hmrc.service.HelloWorldService;
import uk.gov.hmrc.service.OauthService;
import uk.gov.hmrc.service.ServiceConnector;
import uk.gov.hmrc.service.VATService;


public class TestApplication {


	public static void main(String[] args) {

		String callbackUrl = "https://www.action42.de";
		//String 	clientId = "eUqzJONy8Y6HKmMPLKu6525pvj8a";
		String 	clientId = "L_fjrRZZMhWMS1miZv4AarJXHxMa";
		//String 	scope="hello";
		String 	scope="write:vat";
		String urlHmrc="https://test-api.service.hmrc.gov.uk";

		//String 	clientSecret = "1fc1524c-0023-455b-a80c-3b682d661e34";
		String 	clientSecret = "a0677ce4-c729-4eb0-99c3-6a28743f5371";
		//String 	serverToken = "c043966ad6a834492ef2c5af4efd822";
		String 	serverToken = "a15618d72d3aad6eda1822e2fbb8144";

		OauthService oauthservice = new OauthService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken);
		/*1ere etape*/		

		System.out.println(oauthservice.getAuthorizationRequestUrl(scope));

		/*2eme etape*/
		String code ="c44f17045c12487eab218acfbab53c82";
		Token token = oauthservice.getToken(code);

		/*HelloWorldService helloservice = new HelloWorldService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,new  ServiceConnector());
		try {
			System.out.println(helloservice.helloUser(token.getAccessToken()));
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		/*3eme etape*/
		VATReturn vatReturn = new VATReturn("A001",105.50,-100.45, 5.05,105.15,100.10,300,300,3000,3000,true);
		try {
			String json =VATParser.toJson(vatReturn);
			System.out.println(json);
			String vrn="658278686";
			VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,new  ServiceConnector());


			String jsonResponse =vatservice.vatReturns(token.getAccessToken(), vrn,json );
			VATReturnResponse vatReturnResponse=VATParser.fromJsonResponse(jsonResponse);
			System.out.println(vatReturnResponse.getPaymentIndicator());
			System.out.println(vatReturnResponse.getProcessingDate());
			System.out.println(vatReturnResponse.getChargeRefNumber());
			System.out.println(vatReturnResponse.getFormBundleNumber());
		} catch (IOException | UnauthorizedException e) {
			e.printStackTrace();
		}
	}
}
