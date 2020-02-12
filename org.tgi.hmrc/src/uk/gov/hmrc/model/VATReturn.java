package uk.gov.hmrc.model;

public class VATReturn {

	/**
	 * Generated json field names
	 */
	static public final class FIELD_NAMES {
		public static final String PERIODKEY = "periodKey";
		public static final String VATDUESALES = "vatDueSales";
		public static final String VATDUEACQUISITIONS = "vatDueAcquisitions";
		public static final String TOTALVATDUE = "totalVatDue";
		public static final String VATRECLAIMEDCURRPERIOD = "vatReclaimedCurrPeriod";
		public static final String NETVATDUE = "netVatDue";
		public static final String TOTALVALUESALESEXVAT = "totalValueSalesExVAT";
		public static final String TOTALVALUEPURCHASESEXVAT = "totalValuePurchasesExVAT";
		public static final String TOTALVALUEGOODSSUPPLIEDEXVA = " totalValueGoodsSuppliedExVA";
		public static final String TOTALACQUISITIONSEXVAT = "totalAcquisitionsExVAT";
		public static final String FINALISED = "finalised";

	}

	/**
	 * String periodKey : The ID code for the period that this obligation belongs to. The format is a string of four alphanumeric characters. Occasionally the format includes the # symbol.
							For example: 18AD, 18A1, #001
	 */
	private String periodKey;	
	/**
	 * double vatDueSales : VAT due on sales and vatReturnResponse outputs. This corresponds to box 1 on the VAT Return form. The value must be between -9999999999999.99 and 9999999999999.99.
	 */
	private double vatDueSales;
	/**
	 * double vatDueAcquisitions : VAT due on acquisitions from vatReturnResponse EC Member States. This corresponds to box 2 on the VAT Return form. The value must be between -9999999999999.99 and 9999999999999.99.
	 */
	private double vatDueAcquisitions;
	/**
	 * double totalVatDue : Total VAT due (the sum of vatDueSales and vatDueAcquisitions). This corresponds to box 3 on the VAT Return form. The value must be between -9999999999999.99 and 9999999999999.99.
	 */
	private double totalVatDue;
	/**
	 * double vatReclaimedCurrPeriod : VAT reclaimed on purchases and vatReturnResponse inputs (including acquisitions from the EC). This corresponds to box 4 on the VAT Return form. The value must be between -9999999999999.99 and 9999999999999.99.
	 */	
	private double vatReclaimedCurrPeriod;
	/**
	 * double netVatDue : The difference between totalVatDue and vatReclaimedCurrPeriod. This corresponds to box 5 on the VAT Return form. The value must be between 0.00 and 99999999999.99
	 */
	private double netVatDue;
	/**
	 * double totalValueSalesExVAT : Total value of sales and all vatReturnResponse outputs excluding any VAT. This corresponds to box 6 on the VAT Return form. The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
	 */
	private double totalValueSalesExVAT;
	/**
	 * double totalValuePurchasesExVAT : Total value of purchases and all vatReturnResponse inputs excluding any VAT (including exempt purchases). This corresponds to box 7 on the VAT Return form. The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
	 */
	private double totalValuePurchasesExVAT;
	/**
	 * double totalValueGoodsSuppliedExVAT : Total value of all supplies of goods and related costs, excluding any VAT, to vatReturnResponse EC member states. This corresponds to box 8 on the VAT Return form. The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
	 */
	private double totalValueGoodsSuppliedExVAT;
	/**
	 * double totalAcquisitionsExVAT : Total value of acquisitions of goods and related costs excluding any VAT, from vatReturnResponse EC member states. This corresponds to box 9 on the VAT Return form. The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
	 */
	private double totalAcquisitionsExVAT;
	/**
	 * boolean finalised : Declaration that the user has finalised their VAT return.
	 */
	private boolean finalised;

	public VATReturn(String periodKey,double vatDueSales,double vatDueAcquisitions,double totalVatDue,	double vatReclaimedCurrPeriod,double netVatDue,
			double totalValueSalesExVAT,double totalValuePurchasesExVAT,double totalValueGoodsSuppliedExVAT,double totalAcquisitionsExVAT,boolean finalised) {

		this.periodKey=periodKey;
		this.vatDueSales=vatDueSales;
		this.vatDueAcquisitions=vatDueAcquisitions;
		this.totalVatDue=totalVatDue;
		this.vatReclaimedCurrPeriod=vatReclaimedCurrPeriod;
		this.netVatDue=netVatDue;
		this.totalValueSalesExVAT=totalValueSalesExVAT;
		this.totalValuePurchasesExVAT=totalValuePurchasesExVAT;
		this.totalValueGoodsSuppliedExVAT=totalValueGoodsSuppliedExVAT;
		this.totalAcquisitionsExVAT=totalAcquisitionsExVAT;
		this.finalised=finalised;

	}

	public String getPeriodKey() {
		return periodKey;
	}

	public void setPeriodKey(String periodKey) {
		this.periodKey = periodKey;
	}

	public double getVatDueSales() {
		return vatDueSales;
	}

	public void setVatDueSales(double vatDueSales) {
		this.vatDueSales = vatDueSales;
	}

	public double getVatDueAcquisitions() {
		return vatDueAcquisitions;
	}

	public void setVatDueAcquisitions(double vatDueAcquisitions) {
		this.vatDueAcquisitions = vatDueAcquisitions;
	}

	public double getTotalVatDue() {
		return totalVatDue;
	}

	public void setTotalVatDue(double totalVatDue) {
		this.totalVatDue = totalVatDue;
	}

	public double getVatReclaimedCurrPeriod() {
		return vatReclaimedCurrPeriod;
	}

	public void setVatReclaimedCurrPeriod(double vatReclaimedCurrPeriod) {
		this.vatReclaimedCurrPeriod = vatReclaimedCurrPeriod;
	}

	public double getNetVatDue() {
		return netVatDue;
	}

	public void setNetVatDue(double netVatDue) {
		this.netVatDue = netVatDue;
	}

	public double getTotalValueSalesExVAT() {
		return totalValueSalesExVAT;
	}

	public void setTotalValueSalesExVAT(double totalValueSalesExVAT) {
		this.totalValueSalesExVAT = totalValueSalesExVAT;
	}

	public double getTotalValuePurchasesExVAT() {
		return totalValuePurchasesExVAT;
	}

	public void setTotalValuePurchasesExVAT(double totalValuePurchasesExVAT) {
		this.totalValuePurchasesExVAT = totalValuePurchasesExVAT;
	}

	public double getTotalValueGoodsSuppliedExVAT() {
		return totalValueGoodsSuppliedExVAT;
	}

	public void setTotalValueGoodsSuppliedExVAT(double totalValueGoodsSuppliedExVAT) {
		this.totalValueGoodsSuppliedExVAT = totalValueGoodsSuppliedExVAT;
	}

	public double getTotalAcquisitionsExVAT() {
		return totalAcquisitionsExVAT;
	}

	public void setTotalAcquisitionsExVAT(double totalAcquisitionsExVAT) {
		this.totalAcquisitionsExVAT = totalAcquisitionsExVAT;
	}

	public boolean isFinalised() {
		return finalised;
	}

	public void setFinalised(boolean finalised) {
		this.finalised = finalised;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (finalised ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(netVatDue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((periodKey == null) ? 0 : periodKey.hashCode());
		temp = Double.doubleToLongBits(totalAcquisitionsExVAT);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalValueGoodsSuppliedExVAT);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalValuePurchasesExVAT);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalValueSalesExVAT);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalVatDue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vatDueAcquisitions);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vatDueSales);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vatReclaimedCurrPeriod);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VATReturn vatReturn = (VATReturn) o;
		if (finalised != vatReturn.finalised)
			return false;
		if (Double.doubleToLongBits(netVatDue) != Double.doubleToLongBits(vatReturn.netVatDue))
			return false;
		if (periodKey == null) {
			if (vatReturn.periodKey != null)
				return false;
		} else if (!periodKey.equals(vatReturn.periodKey))
			return false;
		if (Double.doubleToLongBits(totalAcquisitionsExVAT) != Double.doubleToLongBits(vatReturn.totalAcquisitionsExVAT))
			return false;
		if (Double.doubleToLongBits(totalValueGoodsSuppliedExVAT) != Double
				.doubleToLongBits(vatReturn.totalValueGoodsSuppliedExVAT))
			return false;
		if (Double.doubleToLongBits(totalValuePurchasesExVAT) != Double
				.doubleToLongBits(vatReturn.totalValuePurchasesExVAT))
			return false;
		if (Double.doubleToLongBits(totalValueSalesExVAT) != Double.doubleToLongBits(vatReturn.totalValueSalesExVAT))
			return false;
		if (Double.doubleToLongBits(totalVatDue) != Double.doubleToLongBits(vatReturn.totalVatDue))
			return false;
		if (Double.doubleToLongBits(vatDueAcquisitions) != Double.doubleToLongBits(vatReturn.vatDueAcquisitions))
			return false;
		if (Double.doubleToLongBits(vatDueSales) != Double.doubleToLongBits(vatReturn.vatDueSales))
			return false;
		if (Double.doubleToLongBits(vatReclaimedCurrPeriod) != Double.doubleToLongBits(vatReturn.vatReclaimedCurrPeriod))
			return false;
		return true;
	}
}
