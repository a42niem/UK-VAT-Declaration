package uk.gov.hmrc.service;

import com.google.common.base.Optional;

import uk.gov.hmrc.model.UnauthorizedException;

public class HelloWorldService extends HmrcService{

	private final ServiceConnector serviceConnector;

	public HelloWorldService(ServiceConnector serviceConnector) {
		super();
		this.serviceConnector = serviceConnector; 

	}
	
	public HelloWorldService(String urlHmrc,String clientId,String clientSecret,String callbackUrl,String serverToken,ServiceConnector serviceConnector) {
		super(urlHmrc,clientId,clientSecret,callbackUrl);
		this.serviceConnector = serviceConnector; 

	}


	public String helloUser(String accessToken) throws UnauthorizedException {
		return serviceConnector.get(
				urlHmrc+"/hello/user",
				"application/vnd.hmrc.1.0+json",
				Optional.of(accessToken));
	}

	@Override
	public String toString() {
		return "HelloWorldService {" + "urlHmrc = " + urlHmrc +
				", clientId = " + clientId  +
				", clientSecret = " + clientSecret +
				", callbackUrl = " + callbackUrl +
				'}';
	}
}
