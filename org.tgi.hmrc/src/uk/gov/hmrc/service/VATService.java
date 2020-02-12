package uk.gov.hmrc.service;

import com.google.common.base.Optional;

import uk.gov.hmrc.model.UnauthorizedException;

public class VATService extends HmrcService{

	private final ServiceConnector serviceConnector;

	public VATService(ServiceConnector serviceConnector) {
		super();
		this.serviceConnector = serviceConnector; 

	}
	
	public VATService(String urlHmrc,String clientId,String clientSecret,String callbackUrl,String serverToken,ServiceConnector serviceConnector) {
		super(urlHmrc,clientId,clientSecret,callbackUrl,serverToken);
		this.serviceConnector = serviceConnector; 

	}
	
	public String vatReturns(String accessToken,String vrn, String json) throws UnauthorizedException {
		return serviceConnector.post(
				urlHmrc+"/organisations/vat/"+vrn+"/returns",
				"application/vnd.hmrc.1.0+json",
				Optional.of(accessToken),
				Optional.of(json));
	}
	
	@Override
	public String toString() {
		return "VATService {" + "urlHmrc = " + urlHmrc +
				", clientId = " + clientId  +
				", clientSecret = " + clientSecret +
				", serverToken = " + serverToken +
				", callbackUrl = " + callbackUrl +
				'}';
	}
}
