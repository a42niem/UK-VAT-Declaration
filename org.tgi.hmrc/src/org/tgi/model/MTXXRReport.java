package org.tgi.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.tgi.model.X_T_XXR_Report;

public class MTXXRReport extends X_T_XXR_Report {

	private static final long serialVersionUID = 524710324947300840L;

	public MTXXRReport(Properties ctx, int T_XXR_Report_ID, String trxName) {
		super(ctx, T_XXR_Report_ID, trxName);
	}

	public MTXXRReport (Properties ctx, int T_XXR_Report_ID, int instanceID, String trxName) {
		super (ctx, T_XXR_Report_ID, trxName);
		setAD_PInstance_ID(instanceID);
	}	//	MTXXRReport

	/** Convert the amount in project currency at sysdate */
	public static BigDecimal getConvertedAmt(Properties ctx, int clientID, BigDecimal amt, int currencyFromID, int currencyToID, Timestamp convDate) {
		// FIXME: org is hardcoded, type is hardcoded
		return MConversionRate.convert (ctx, amt, currencyFromID, currencyToID, convDate, 114, clientID, 1000000);
	}

	/** Get the rate for currencies at given date */
	public static BigDecimal getRate(Properties ctx, int clientID, int currencyFromID, int currencyToID, Timestamp convDate) {
		BigDecimal rate = MConversionRate.getRate(currencyFromID, currencyToID, convDate, 0, clientID, 0);
		if (rate == null) {	
			// FIXME: org is hardcoded, type is hardcoded
			rate = MConversionRate.getRate(currencyFromID, currencyToID, convDate, 114, clientID, 1000000);
		}
		return rate;
	}

	/** Convert the amount in the desired currency using the rate (see MConversionRate.convert) */
	public static BigDecimal convert(Properties ctx, BigDecimal amt, BigDecimal rate, int currencyToID) {

		BigDecimal retValue = rate.multiply(amt);
		int stdPrecision = MCurrency.getStdPrecision(ctx, currencyToID);		

		if (retValue.scale() > stdPrecision)
			retValue = retValue.setScale(stdPrecision, RoundingMode.HALF_UP);

		return retValue;
	}

}
