package uk.gov.hmrc.model;

import java.util.List;

public class VATPaymentsResponse {

	private List<Payment> payments = null;

	public List<Payment> getPayments() {
	return payments;
	}

	public void setPayments(List<Payment> payments) {
	this.payments = payments;
	}

}
