package uk.gov.hmrc.service;

import com.google.common.base.Optional;

import uk.gov.hmrc.model.UnauthorizedException;

public class VATService extends HmrcService{

	private final ServiceConnector serviceConnector;

	public VATService(ServiceConnector serviceConnector) {
		super();
		this.serviceConnector = serviceConnector; 

	}
	
	public VATService(String urlHmrc,String clientId,String clientSecret,String callbackUrl,ServiceConnector serviceConnector) {
		super(urlHmrc,clientId,clientSecret,callbackUrl);
		this.serviceConnector = serviceConnector; 

	}
	
	public String vatReturns(String accessToken,String vrn, String json) throws UnauthorizedException {
		return serviceConnector.post(
				urlHmrc+"/organisations/vat/"+vrn+"/returns",
				"application/vnd.hmrc.1.0+json",
				Optional.of(accessToken),
				Optional.of(json));
	}
	
	public String vatObligations(String accessToken, String vrn, String from, String to, String status) throws UnauthorizedException {
		String url = urlHmrc+"/organisations/vat/"+vrn+"/obligations";
		if ((status != null) | (from != null) | (to != null)) {
			url = url + "?";
			if (status != null && status.compareTo("") != 0)
				url = url + "status=" + status + "&";
			if (from != null)
				url = url + "from=" + from + "&";
			if (to != null)
				url = url + "to=" + to ;
		}
		return serviceConnector.get(
				url,
				"application/vnd.hmrc.1.0+json",
				Optional.of(accessToken));
	}
	
	public String vatLiabilities(String accessToken, String vrn, String from, String to) throws UnauthorizedException {
		String url = urlHmrc+"/organisations/vat/"+vrn+"/liabilities";
		url = url + "?";
		url = url + "from=" + from + "&";
		url = url + "to=" + to ;

		return serviceConnector.get(
				url,
				"application/vnd.hmrc.1.0+json",
				Optional.of(accessToken));
	}
	
	public String vatPayments(String accessToken, String vrn, String from, String to) throws UnauthorizedException {
		String url = urlHmrc+"/organisations/vat/"+vrn+"/payments";
		url = url + "?";
		url = url + "from=" + from + "&";
		url = url + "to=" + to ;

		return serviceConnector.get(
				url,
				"application/vnd.hmrc.1.0+json",
				Optional.of(accessToken));
	}
	
	@Override
	public String toString() {
		return "VATService {" + "urlHmrc = " + urlHmrc +
				", clientId = " + clientId  +
				", clientSecret = " + clientSecret +
				", callbackUrl = " + callbackUrl +
				'}';
	}
}
