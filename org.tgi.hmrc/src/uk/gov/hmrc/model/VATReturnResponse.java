package uk.gov.hmrc.model;

public class VATReturnResponse {
	

	/**
	 * Generated json field names
	 */
	static public final class FIELD_NAMES {
		public static final String PROCESSINGDATE = "processingDate";
		public static final String PAYMENTINDICATOR = "paymentIndicator";
		public static final String FORMBUNDLENUMBER = "formBundleNumber";
		public static final String CHARGEREFNUMBER = "chargeRefNumber";
	}

	/**
	 * String processingDate : The time that the message was processed in the system.
	 */
	private String processingDate;
	/**
	 *  String paymentIndicator(optional) : Is DD if the netVatDue value is a debit and HMRC holds a Direct Debit Instruction for the client. Is BANK if the netVatDue value is a credit and HMRC holds the client’s bank data. Otherwise not present.
							Limited to the following possible values: DD and BANK
	 */
	private String paymentIndicator;
	/**
	 * String formBundleNumber : Unique number that represents the form bundle. The system stores VAT Return data in forms, which are held in a unique form bundle.
							Must conform to the regular expression ^[0-9]{12}$
	 */
	private String formBundleNumber;
	/**
	 * String chargeRefNumber(optional) : The charge reference number is returned, only if the netVatDue value is a debit. Between 1 and 16 characters.
	 */
	private String chargeRefNumber;

	public String getProcessingDate() {
		return processingDate;
	}
	public void setProcessingDate(String processingDate) {
		this.processingDate = processingDate;
	}
	public String getPaymentIndicator() {
		return paymentIndicator;
	}
	public void setPaymentIndicator(String paymentIndicator) {
		this.paymentIndicator = paymentIndicator;
	}
	public String getFormBundleNumber() {
		return formBundleNumber;
	}
	public void setFormBundleNumber(String formBundleNumber) {
		this.formBundleNumber = formBundleNumber;
	}
	public String getChargeRefNumber() {
		return chargeRefNumber;
	}
	public void setChargeRefNumber(String chargeRefNumber) {
		this.chargeRefNumber = chargeRefNumber;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VATReturnResponse vatReturnResponse = (VATReturnResponse) o;

        if (processingDate != null ? !processingDate.equals(vatReturnResponse.processingDate) : vatReturnResponse.processingDate != null) return false;
        if (paymentIndicator != null ? !paymentIndicator.equals(vatReturnResponse.paymentIndicator) : vatReturnResponse.paymentIndicator != null) return false;
        if (formBundleNumber != null ? !formBundleNumber.equals(vatReturnResponse.formBundleNumber) : vatReturnResponse.formBundleNumber != null) return false;
        return !(chargeRefNumber != null ? !chargeRefNumber.equals(vatReturnResponse.chargeRefNumber) : vatReturnResponse.chargeRefNumber != null);
    }

    @Override
    public int hashCode() {
        int result = processingDate != null ? processingDate.hashCode() : 0;
        result = 31 * result + (paymentIndicator != null ? paymentIndicator.hashCode() : 0);
        result = 31 * result + (formBundleNumber != null ? formBundleNumber.hashCode() : 0);
        result = 31 * result + (chargeRefNumber != null ? chargeRefNumber.hashCode() : 0);
        return result;
    }

}
