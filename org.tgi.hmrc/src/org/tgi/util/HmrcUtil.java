package org.tgi.util;

import static org.tgi.model.SystemIDs_Tgi.XXR_VAT_RETURN_REFERENCE_ID;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_CALLBACK_URL;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_CLIENT_ID;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_CLIENT_SECRET;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_EC_COUNTRYGROUP_ID;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_RUBRIC_EUROPE_ORG_ID;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_URL;
import static org.tgi.model.SystemIDs_Tgi.XXR_HMRC_VRN;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.util.IProcessUI;
import org.compiere.model.MInvoice;
import org.compiere.model.MRefList;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.tgi.model.MTXXRReport;

import uk.gov.hmrc.model.Token;
import uk.gov.hmrc.model.UnauthorizedException;
import uk.gov.hmrc.model.VATReturn;
import uk.gov.hmrc.model.VATReturnResponse;
import uk.gov.hmrc.parser.VATParser;
import uk.gov.hmrc.service.OauthService;
import uk.gov.hmrc.service.ServiceConnector;
import uk.gov.hmrc.service.VATService;

public class HmrcUtil {

	public final static String username = "644864748384"; // "217057348397";
	public final static String userpwd = "b3ptzc2G4nit"; // "w8Ddfjomjxoq";
	private final static String vrn = MSysConfig.getValue(XXR_HMRC_VRN, "", Env.getAD_Client_ID(Env.getCtx())); //  "658278686";
	private final static String callbackUrl = MSysConfig.getValue(XXR_HMRC_CALLBACK_URL, "", Env.getAD_Client_ID(Env.getCtx())); //"https://rubric.com"; // must be set in Application > Manage redirect URIs
	private final static String clientId = MSysConfig.getValue(XXR_HMRC_CLIENT_ID, "", Env.getAD_Client_ID(Env.getCtx())); //"eUqzJONy8Y6HKmMPLKu6525pvj8a";
	private final static String scope="write:vat";
	private final static String urlHmrc=MSysConfig.getValue(XXR_HMRC_URL, "", Env.getAD_Client_ID(Env.getCtx())); //"https://test-api.service.hmrc.gov.uk";
	private final static String clientSecret = MSysConfig.getValue(XXR_HMRC_CLIENT_SECRET, "", Env.getAD_Client_ID(Env.getCtx())); //"1fc1524c-0023-455b-a80c-3b682d661e34";

	private final static int europeOrgID = MSysConfig.getIntValue(XXR_HMRC_RUBRIC_EUROPE_ORG_ID, -1, Env.getAD_Client_ID(Env.getCtx())); //1000001; // Europe
	private final static int ukCountryID = 333;
	private final static int ecCountryGroupID = MSysConfig.getIntValue(XXR_HMRC_EC_COUNTRYGROUP_ID, -1, Env.getAD_Client_ID(Env.getCtx())); //1000000; // EU
//	private final static int usdCurrencyID = MCurrency.get(Env.getCtx(), "USD").getC_Currency_ID();
//	private final static int gbpCurrencyID = MCurrency.get(Env.getCtx(), "GBP").getC_Currency_ID();

	// Create user: https://developer.service.hmrc.gov.uk/api-test-user
	// https://developer.service.hmrc.gov.uk/api-documentation/docs/tutorials

	// Accessing a user-restricted endpoint : 1. Request an OAuth 2.0 authorisation code with the required scope
	// https://www.ibm.com/developerworks/library/se-oauthjavapt2/index.html
	// jars found there : http://www.java2s.com/Code/Jar/o/Downloadorgapacheoltuoauth2common031jar.htm
	
	private static OauthService getOauthService() {
		return new OauthService(urlHmrc,clientId,clientSecret,callbackUrl);
	}

	public static String getAuthorizationRequestUrl() {
		OauthService oauthservice = HmrcUtil.getOauthService();
		return oauthservice.getAuthorizationRequestUrl(scope);
	}

	public static String sendData(String code, String periodKey, BigDecimal box1, BigDecimal box2, BigDecimal box3, BigDecimal box4, BigDecimal box5, BigDecimal box6, BigDecimal box7, BigDecimal box8, BigDecimal box9, boolean isFinalised) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,new  ServiceConnector());
		VATReturn vatReturn = new VATReturn(periodKey, box1.doubleValue(), box2.doubleValue(), box3.doubleValue(), box4.doubleValue(), box5.doubleValue(), box6.longValue(), box7.longValue(), box8.longValue(), box9.longValue(), isFinalised);
		
//		Token token = oauthservice.getToken(code);
//		System.out.println("token=" + token);
		String retValue = "";
		try {
			System.out.println(oauthservice.toString());
			retValue = doVATReturn(oauthservice, vatservice, code, vrn, vatReturn);
		} catch (IOException | UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retValue;
	}
	
	public static String doVATReturn(OauthService oauthservice, VATService vatservice, String code, String vrn, VATReturn vatReturn) throws IOException, UnauthorizedException {		

		String json =VATParser.toJson(vatReturn);
		System.out.println(json);

		String jsonResponse;
		Token token = oauthservice.getToken(code);
		try {
			
			jsonResponse =vatservice.vatReturns(token.getAccessToken(), vrn,json );
		} catch (UnauthorizedException ue) {
			Token refreshedToken = oauthservice.refreshToken(token.getRefreshToken());
			jsonResponse =vatservice.vatReturns(refreshedToken.getAccessToken(),vrn,json);
		}

//		VATReturnResponse vatReturnResponse=VATParser.fromJsonResponse(jsonResponse);		
//
//		System.out.println("PaymentIndicator - "+vatReturnResponse.getPaymentIndicator());
//		System.out.println("ProcessingDate - "+vatReturnResponse.getProcessingDate());
//		System.out.println("ChargeRefNumber - "+vatReturnResponse.getChargeRefNumber());
//		System.out.println("FormBundleNumber - "+vatReturnResponse.getFormBundleNumber());
		
		if (jsonResponse.startsWith("Error")) {
			return jsonResponse;
		}
		else { // 200
			System.out.println(jsonResponse.toString());

			VATReturnResponse vatReturnResponse=VATParser.fromJsonResponse(jsonResponse);

			System.out.println(vatReturnResponse.getPaymentIndicator());
			System.out.println(vatReturnResponse.getProcessingDate());
			System.out.println(vatReturnResponse.getChargeRefNumber());
			System.out.println(vatReturnResponse.getFormBundleNumber());

			StringBuilder msg = new StringBuilder("PaymentIndicator=").append(vatReturnResponse.getPaymentIndicator()).append(" - ")
					.append("ProcessingDate=").append(vatReturnResponse.getProcessingDate()).append(" - ")
					.append("ChargeRefNumber=").append(vatReturnResponse.getChargeRefNumber()).append(" - ")
					.append("FormBundleNumber=").append(vatReturnResponse.getFormBundleNumber());

			return msg.toString();//tbResult.setValue(msg.toString());
		}

	}

	/** TotalLines of purchase invoices from EC x 20% */
	private static BigDecimal addReverseCharge(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, String fromValue, IProcessUI iProcessUI, String trxName) {
		
		int taxID = 1000000; // hardcoded - faire sysconfig TODO
		BigDecimal taxRate = MTax.get(ctx, taxID).getRate();
		
		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" INNER JOIN C_BPartner_Location bpl ON (i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID)")
				.append(" INNER JOIN C_Location l ON (bpl.C_Location_ID = l.C_Location_ID)")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND l.C_Country_ID IN (SELECT C_Country_ID FROM C_CountryGroupCountry WHERE C_CountryGroup_ID = ").append(ecCountryGroupID).append(")")
				.append(" AND i.IsSOTrx = 'N' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.TotalLines = i.GrandTotal")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));
		
		String value = fromValue;
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " - Reverse charge : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getTotalLines(true).multiply(taxRate).divide(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP);
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));

			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.setComments("ReverseCharge");
				taf.saveEx();
			}
		}

		return totalAmt;
	}
	
	
	/** VAT due in the period on sales and other outputs : The total of VAT on sales invoices to UK */
	public static BigDecimal getBox1(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" INNER JOIN C_BPartner_Location bpl ON (i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID)")
				.append(" INNER JOIN C_Location l ON (bpl.C_Location_ID = l.C_Location_ID)")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND l.C_Country_ID = ").append(ukCountryID)
				.append(" AND i.IsSOTrx = 'Y' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.TotalLines != i.GrandTotal")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));

		String value = "1";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));

			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}

		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);

//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
////			taf.setAmount(amt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}

		totalAmt = totalAmt.add(addReverseCharge(ctx, from, to, instanceID, isDetail, totalAmtCurrencyID, value, iProcessUI, trxName));

		return totalAmt;
	}

	/** VAT due in the period on acquisitions from other member states of the EU : The total of VAT on purchase invoices from EC. This will always be 0 */
	public static BigDecimal getBox2(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" INNER JOIN C_BPartner_Location bpl ON (i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID)")
				.append(" INNER JOIN C_Location l ON (bpl.C_Location_ID = l.C_Location_ID)")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND l.C_Country_ID IN (SELECT C_Country_ID FROM C_CountryGroupCountry WHERE C_CountryGroup_ID = ").append(ecCountryGroupID).append(")")
				.append(" AND i.IsSOTrx = 'N' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.TotalLines != i.GrandTotal")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));

		String value = "2";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));

			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}

		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);
		
//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
////			taf.setAmount(totalAmt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}
		return totalAmt;
	}

	/** Total VAT due */
	public static BigDecimal getBox3(Properties ctx, BigDecimal amtBox1, BigDecimal amtBox2, Timestamp to, int instanceID, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {
		BigDecimal totalAmt = amtBox1.add(amtBox2);
		String value = "3";
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);
		statusUpdate(iProcessUI, getName(value, name));

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setDescription(getName(value, name));
			taf.setAmount(totalAmt);
			taf.setConvertedAmt(totalAmt);
			taf.setC_Currency_ID(totalAmtCurrencyID);
			taf.setDateAcct(to);
			taf.saveEx();
		}

		return totalAmt;
	}

	/** VAT reclaimed in the period on purchases and other inputs (including acquisitions from the EU) : The total of purchase invoices from UK & rest of EC */
	public static BigDecimal getBox4(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" INNER JOIN C_BPartner_Location bpl ON (i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID)")
				.append(" INNER JOIN C_Location l ON (bpl.C_Location_ID = l.C_Location_ID)")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND (l.C_Country_ID = ").append(ukCountryID)
				.append(" OR l.C_Country_ID IN (SELECT C_Country_ID FROM C_CountryGroupCountry WHERE C_CountryGroup_ID = ").append(ecCountryGroupID).append(")")
				.append(")")
				.append(" AND i.IsSOTrx = 'N' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.TotalLines != i.GrandTotal")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));

		String value = "4";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));

			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}
		
		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);

//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
//			taf.setAmount(totalAmt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}

		totalAmt = totalAmt.add(addReverseCharge(ctx, from, to, instanceID, isDetail, totalAmtCurrencyID, value, iProcessUI, trxName));

		return totalAmt;
	}

	/** net VAT to pay to HMRC or reclaim */
	public static BigDecimal getBox5(Properties ctx, BigDecimal amtBox3, BigDecimal amtBox4, Timestamp to, int instanceID, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {
		
		BigDecimal totalAmt = Env.ZERO;
		
		if (amtBox4.compareTo(amtBox3) > 0)
			totalAmt = amtBox4.subtract(amtBox3);
		else
			totalAmt = amtBox3.subtract(amtBox4);

		String value = "5";
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);
		statusUpdate(iProcessUI, getName(value, name));

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setDescription(getName(value, name));
//			taf.setAmount(totalAmt);
			taf.setConvertedAmt(totalAmt);
			taf.setC_Currency_ID(totalAmtCurrencyID);
			taf.setDateAcct(to);
			taf.saveEx();
		}

		return totalAmt;
	}

	/** total value of sales and all other outputs excluding any VAT : The total of sales invoices to UK, rest of EC & rest of world – excluding VAT */
	public static BigDecimal getBox6(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND i.IsSOTrx = 'Y' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));
		String value = "6";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getTotalLines(true);
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));

			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}
		
		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);

//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
////			taf.setAmount(totalAmt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}
		return totalAmt;
	}
	
	/** the total value of purchases and all other inputs excluding any VAT : The total of purchase invoices from UK, rest of EC & rest of world – excluding VAT */
	public static BigDecimal getBox7(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {
		
		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND i.IsSOTrx = 'N' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));
		String value = "7";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);
		
		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getTotalLines(true);
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
			
			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}
		
		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);
		
//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
////			taf.setAmount(totalAmt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}
		return totalAmt;
	}

	/** The total of sales invoices to rest of EC – excluding VAT */
	public static BigDecimal getBox8(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {

		boolean forceBoxes8And9ToZero = true; // hardcoded - faire sysconfig

		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" INNER JOIN C_BPartner_Location bpl ON (i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID)")
				.append(" INNER JOIN C_Location l ON (bpl.C_Location_ID = l.C_Location_ID)")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND l.C_Country_ID IN (SELECT C_Country_ID FROM C_CountryGroupCountry WHERE C_CountryGroup_ID = ").append(ecCountryGroupID).append(")")
				.append(" AND i.IsSOTrx = 'Y' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));

		if (forceBoxes8And9ToZero)
			sql.append(" AND 1=2");

		String value = "8";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);
		
		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getTotalLines(true);
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
			
			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}
		
		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);
		
//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
////			taf.setAmount(totalAmt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}
		return totalAmt;
	}
	
	
	/** The total of purchase invoices from rest of EC – excluding VAT */
	public static BigDecimal getBox9(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, int totalAmtCurrencyID, IProcessUI iProcessUI, String trxName) {
		
		boolean forceBoxes8And9ToZero = true; // hardcoded - faire sysconfig

		StringBuilder sql = new StringBuilder("SELECT i.C_Invoice_ID ")
				.append(" FROM C_Invoice i")
				.append(" INNER JOIN C_BPartner_Location bpl ON (i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID)")
				.append(" INNER JOIN C_Location l ON (bpl.C_Location_ID = l.C_Location_ID)")
				.append(" WHERE i.AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND i.AD_Org_ID = ").append(europeOrgID)
				.append(" AND l.C_Country_ID IN (SELECT C_Country_ID FROM C_CountryGroupCountry WHERE C_CountryGroup_ID = ").append(ecCountryGroupID).append(")")
				.append(" AND i.IsSOTrx = 'N' AND i.DocStatus IN ('CO', 'CL')")
				.append(" AND i.DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND i.DateAcct <= ").append(DB.TO_DATE(to));

		if (forceBoxes8And9ToZero)
			sql.append(" AND 1=2");

		String value = "9";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListDescription(ctx, DB.getSQLValueStringEx(null, "SELECT Name FROM AD_Reference WHERE AD_Reference_ID = ?", getReferenceID()), value);
		
		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, getName(value, name) + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getTotalLines(true);
			totalAmt = totalAmt.add(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
			
			if (instanceID > 0 && isDetail) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setDescription(getName(value, name));
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.setC_Invoice_C_Currency_ID(i.getC_Currency_ID());
				taf.setConvertedAmt(MTXXRReport.getConvertedAmt(ctx, i.getAD_Client_ID(), amt, i.getC_Currency_ID(), totalAmtCurrencyID, i.getDateInvoiced()));
				taf.setC_Currency_ID(totalAmtCurrencyID);
				taf.setDateAcct(i.getDateAcct());
				taf.saveEx();
			}
		}
		
		if (invoiceIDs.length == 0)
			insertEmptyLine(ctx, instanceID, value, name, totalAmtCurrencyID, to, trxName);
		
//		if (instanceID > 0 && !isDetail) { // line total
//			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
//			taf.setCode(value);
//			taf.setName(name);
////			taf.setAmount(totalAmt);
//			taf.setConvertedAmt(totalAmt);
//			taf.setC_Currency_ID(totalAmtCurrencyID);
//			taf.setDateAcct(to);
//			taf.saveEx();
//		}
		return totalAmt;
	}
	

	public static int getReferenceID() {
		return MSysConfig.getIntValue(XXR_VAT_RETURN_REFERENCE_ID, 0, Env.getAD_Client_ID(Env.getCtx()));
	}

	static void statusUpdate(IProcessUI iProcessUI, String text) {
		if (iProcessUI != null)
			iProcessUI.statusUpdate(text);
	}
	
	static String getName(String code, String name) {
		return code + " - " + name;
	}

	static void insertEmptyLine(Properties ctx, int instanceID, String code, String name, int currencyID, Timestamp dateAcct, String trxName) {
		MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
		taf.setCode(code);
		taf.setName(name);
		taf.setDescription(getName(code, name));
		taf.setAmount(Env.ZERO);
		taf.setConvertedAmt(Env.ZERO);
		taf.setC_Currency_ID(currencyID);
		taf.setDateAcct(dateAcct);
		taf.saveEx();
	}
}
