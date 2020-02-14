package uk.gov.hmrc.service;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.tgi.util.HmrcUtil;

import uk.gov.hmrc.model.Token;

public class OauthService extends HmrcService {

    private final OAuthClient oAuthClient;

    public final String authorizeUrl;
    public final String tokenUrl;


    public OauthService() {
    	super();
        this.oAuthClient = new OAuthClient(new URLConnectionClient());
        this.authorizeUrl = urlHmrc+"/oauth/authorize";
        this.tokenUrl = urlHmrc+"/oauth/token";
    }
    
    public OauthService(String urlHmrc,String clientId,String clientSecret,String callbackUrl,String serverToken) {
    	super(urlHmrc,clientId,clientSecret,callbackUrl,serverToken);
        this.oAuthClient = new OAuthClient(new URLConnectionClient());
        this.authorizeUrl = urlHmrc+"/oauth/authorize";
        this.tokenUrl = urlHmrc+"/oauth/token";
    }
    
    public Token getToken(String code) {
        try {
        	System.out.println("Token_getToken_code = " + code);
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(tokenUrl)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRedirectURI(callbackUrl)
                    .setCode(code)
                    .buildBodyMessage();
            
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");

            return fetchToken(request);

        } catch (Exception e) {
        	System.out.println("Token_getToken_code ERROR");
            throw new RuntimeException(e);
        }
    }

    public Token refreshToken(String refreshToken) {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(tokenUrl)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRefreshToken(refreshToken)
                    .buildBodyMessage();

            return fetchToken(request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
	public String getAuthorizationRequestUrl(String scope) {
		try {
			OAuthClientRequest request = OAuthClientRequest
					.authorizationLocation(authorizeUrl)
					.setResponseType("code")
					.setClientId(clientId)
					.setScope(scope)
					.setRedirectURI(callbackUrl)
					.buildQueryMessage();
			return request.getLocationUri();

		} catch (OAuthSystemException e) {
			throw new RuntimeException(e);
		}
	}

    private Token fetchToken(OAuthClientRequest tokenRequest) throws OAuthProblemException, OAuthSystemException {
    	System.out.println("Token_fetchToken_1");
        OAuthJSONAccessTokenResponse tokenResponse = oAuthClient.accessToken(tokenRequest);
        System.out.println("Token_fetchToken_2");
        return new Token(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
    }
    
    @Override
	public String toString() {
		return "OauthService {" + "urlHmrc = " + urlHmrc +
				", clientId = " + clientId  +
				", clientSecret = " + clientSecret +
				", serverToken = " + serverToken +
				", callbackUrl = " + callbackUrl +
				'}';
	}
}
