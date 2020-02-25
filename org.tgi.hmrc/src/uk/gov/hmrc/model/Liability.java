package uk.gov.hmrc.model;

public class Liability {

	private TaxPeriod taxPeriod;
	private String type;
	private Integer originalAmount;
	private Integer outstandingAmount;
	private String due;

	public TaxPeriod getTaxPeriod() {
		return taxPeriod;
	}

	public void setTaxPeriod(TaxPeriod taxPeriod) {
		this.taxPeriod = taxPeriod;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(Integer originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Integer getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Integer outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

}
