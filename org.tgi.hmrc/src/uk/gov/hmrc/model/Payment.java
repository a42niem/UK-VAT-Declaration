package uk.gov.hmrc.model;

public class Payment {

	private Integer amount;
	private String received;

	public Integer getAmount() {
	return amount;
	}

	public void setAmount(Integer amount) {
	this.amount = amount;
	}

	public String getReceived() {
	return received;
	}

	public void setReceived(String received) {
	this.received = received;
	}

}
