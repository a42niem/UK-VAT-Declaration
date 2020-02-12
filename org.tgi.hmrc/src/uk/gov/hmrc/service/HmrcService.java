package uk.gov.hmrc.service;

public class HmrcService {

	protected String urlHmrc;
	protected String clientId;
	protected String clientSecret;
	protected String callbackUrl;    
	protected String serverToken;

	private static final String DEFAULT_URL = "https://test-api.service.hmrc.gov.uk";
	private static final String DEFAULT_CALLBACK_URL = "http://localhost:8080";

	public HmrcService() {
		this.urlHmrc= DEFAULT_URL;
		this.callbackUrl=DEFAULT_CALLBACK_URL;
	}

	public HmrcService(String urlHmrc,String clientId,String clientSecret,String callbackUrl,String serverToken) {
		this.urlHmrc=urlHmrc;
		this.clientId=clientId;
		this.clientSecret=clientSecret;
		this.callbackUrl=callbackUrl;    
		this.serverToken=serverToken;
	}

	public String getUrlHmrc() {
		return urlHmrc;
	}

	public void setUrlHmrc(String urlHmrc) {
		this.urlHmrc = urlHmrc;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getServerToken() {
		return serverToken;
	}

	public void setServerToken(String serverToken) {
		this.serverToken = serverToken;
	}
	
	@Override
	public String toString() {
		return "HmrcService {" + "urlHmrc = " + urlHmrc +
				", clientId = " + clientId  +
				", clientSecret = " + clientSecret +
				", serverToken = " + serverToken +
				", callbackUrl = " + callbackUrl +
				'}';
	}


}
