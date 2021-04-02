package uk.gov.hmrc.service;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import uk.gov.hmrc.model.Token;

public class OauthService extends HmrcService {

    public final String authorizeUrl;
    public final String tokenUrl;


    public OauthService() {
    	super();
        this.authorizeUrl = urlHmrc+"/oauth/authorize";
        this.tokenUrl = urlHmrc+"/oauth/token";
    }
    
    public OauthService(String urlHmrc,String clientId,String clientSecret,String callbackUrl) {
    	super(urlHmrc,clientId,clientSecret,callbackUrl);
        this.authorizeUrl = urlHmrc+"/oauth/authorize";
        this.tokenUrl = urlHmrc+"/oauth/token";
    }
    
    public Token getToken(String code) {
        try {
        	System.out.println("Token_getToken_code = " + code);

			AuthorizationCodeTokenRequest request = new AuthorizationCodeTokenRequest(new NetHttpTransport(),
					GsonFactory.getDefaultInstance(),
					new GenericUrl(this.tokenUrl), code);
			request.setRedirectUri(callbackUrl);
			request.setGrantType("authorization_code");
			request.setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
			TokenResponse tokenResponse = request.execute();

			return fetchToken(tokenResponse);

        } catch (Exception e) {
        	System.out.println("Token_getToken_code CATCH");
            throw new RuntimeException(e);
        }
    }

    public Token refreshToken(String refreshToken) {
        try {
        	System.out.println("refreshToken : " + refreshToken);
        	RefreshTokenRequest request = new RefreshTokenRequest(new NetHttpTransport(),
					GsonFactory.getDefaultInstance(),
					new GenericUrl(this.tokenUrl),refreshToken);
			request.setGrantType("refresh_token");
			request.setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
			TokenResponse tokenResponse = request.execute();
			return fetchToken(tokenResponse);

        } catch (Exception e) {
        	System.out.println("refreshToken_catch");
            throw new RuntimeException(e);
        }
    }
    
	public String getAuthorizationRequestUrl(String scope) {
		try {
			List<String> scopesList = Arrays.asList(scope);
			List<String> ResponseTypeList = Arrays.asList("code");
		    //noinspection UnnecessaryLocalVariable
		    AuthorizationCodeRequestUrl request = new AuthorizationCodeRequestUrl(authorizeUrl, clientId)
		            .setRedirectUri(callbackUrl)
		            .setScopes(scopesList)
		            .setResponseTypes(ResponseTypeList);

			return request.build();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    
    private Token fetchToken(TokenResponse tokenRequest)  {
    	System.out.println("Token_fetchToken_1");
		return new Token(tokenRequest.getAccessToken(), tokenRequest.getRefreshToken());
	}
    
    @Override
	public String toString() {
		return "OauthService {" + "urlHmrc = " + urlHmrc +
				", clientId = " + clientId  +
				", clientSecret = " + clientSecret +
				", callbackUrl = " + callbackUrl +
				'}';
	}
}
