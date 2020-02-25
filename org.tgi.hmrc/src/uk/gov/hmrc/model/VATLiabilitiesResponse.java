package uk.gov.hmrc.model;

import java.util.List;

public class VATLiabilitiesResponse {

	private List<Liability> liabilities = null;

	public List<Liability> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<Liability> liabilities) {
		this.liabilities = liabilities;
	}

}
