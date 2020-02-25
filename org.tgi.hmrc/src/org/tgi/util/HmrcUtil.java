package org.tgi.util;

import static org.tgi.model.SystemIDs_Tgi.XXR_VAT_RETURN_REFERENCE_ID;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.IProcessUI;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MInvoice;
import org.compiere.model.MRefList;
import org.compiere.model.MSysConfig;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.tgi.model.MTXXRReport;

import uk.gov.hmrc.model.Liability;
import uk.gov.hmrc.model.Payment;
import uk.gov.hmrc.model.TaxPeriod;
import uk.gov.hmrc.model.Token;
import uk.gov.hmrc.model.UnauthorizedException;
import uk.gov.hmrc.model.VATLiabilitiesResponse;
import uk.gov.hmrc.model.VATObligation;
import uk.gov.hmrc.model.VATObligationResponse;
import uk.gov.hmrc.model.VATPaymentsResponse;
import uk.gov.hmrc.model.VATReturn;
import uk.gov.hmrc.model.VATReturnResponse;
import uk.gov.hmrc.parser.VATParser;
import uk.gov.hmrc.service.OauthService;
import uk.gov.hmrc.service.ServiceConnector;
import uk.gov.hmrc.service.VATService;

public class HmrcUtil {

	public final static String username = "597396986018";
	public final static String userpwd = "kzim9cdlebl1";
	private final static String vrn = "736547705";
	private final static String callbackUrl = "https://nostromo:8443"; // must be set in Application > Manage redirect URIs
	//private final static String callbackUrl = "https://devuan.alfredkochen.de:8443"; // must be set in Application > Manage redirect URIs
	public final static String publicIP = "87.139.125.175"; // 
	private final static String clientId = "L_fjrRZZMhWMS1miZv4AarJXHxMa";
	private final static String scope="write:vat";
	private final static String urlHmrc="https://test-api.service.hmrc.gov.uk";
	//private final static String clientSecret = "a0677ce4-c729-4eb0-99c3-6a28743f5371";
	private final static String clientSecret = "f81c2994-3ffc-4ba2-bd43-c6782e832317";
	private final static String serverToken = "a15618d72d3aad6eda1822e2fbb8144";

	// Create user: https://developer.service.hmrc.gov.uk/api-test-user
	// https://developer.service.hmrc.gov.uk/api-documentation/docs/tutorials

	// Accessing a user-restricted endpoint : 1. Request an OAuth 2.0 authorisation code with the required scope
	// https://www.ibm.com/developerworks/library/se-oauthjavapt2/index.html
	// jars found there : http://www.java2s.com/Code/Jar/o/Downloadorgapacheoltuoauth2common031jar.htm
	
	private static OauthService getOauthService() {
		return new OauthService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken);
	}

	public static String getAuthorizationRequestUrl() {
		OauthService oauthservice = HmrcUtil.getOauthService();
		return oauthservice.getAuthorizationRequestUrl(scope);
	}

	public static String getAuthorizationRequestUrl(String scope) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		return oauthservice.getAuthorizationRequestUrl(scope);
	}

	public static String sendData(String code, String periodKey) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,new  ServiceConnector());
		VATReturn vatReturn = new VATReturn(periodKey,105.50,-100.45, 5.05,105.15,100.10,300,300,3000,3000,true);
		
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
		/*

		Token token = oauthservice.getToken(code);

		VATReturn vatReturn = new VATReturn(periodKey,105.50,-100.45, 5.05,105.15,100.10,300,300,3000,3000,true);
		try {
			String json = VATParser.toJson(vatReturn);
			System.out.println(json);

			VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,new  ServiceConnector());
			String jsonResponse;

			try {
				jsonResponse =vatservice.vatReturns(token.getAccessToken(), vrn,json );
			} catch (UnauthorizedException ue) {
				Token refreshedToken = oauthservice.refreshToken(token.getRefreshToken());
				jsonResponse =vatservice.vatReturns(refreshedToken.getAccessToken(),vrn,json);
			}

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

		} catch (IOException | UnauthorizedException e) {
			e.printStackTrace();
		}
		return "Error while sending data";
		*/
	}
	
	public static String doVATReturn(OauthService oauthservice, VATService vatservice, String code, String vrn, VATReturn vatReturn) throws IOException, UnauthorizedException {		

		String json = VATParser.toJson(vatReturn);
		System.out.println(json);

		String jsonResponse;
		Token token = oauthservice.getToken(code);
		try {
			
			jsonResponse = vatservice.vatReturns(token.getAccessToken(), vrn, json );
		} catch (UnauthorizedException ue) {
			Token refreshedToken = oauthservice.refreshToken(token.getRefreshToken());
			jsonResponse = vatservice.vatReturns(refreshedToken.getAccessToken(), vrn, json);
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

	/** VAT due in the period on sales and other outputs */
	public static BigDecimal getBox1(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("SELECT C_Invoice_ID FROM C_Invoice WHERE AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND IsSOTrx = 'Y' AND DocStatus IN ('CO', 'CL')")
				.append(" AND TotalLines != GrandTotal")
				.append(" AND DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND DateAcct <= ").append(DB.TO_DATE(to));
		String value = "1";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);

			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}

	/** VAT due in the period on acquisitions from other member states of the EU */
	public static BigDecimal getBox2(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("");
		String value = "2";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);

			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}

	/** Total VAT due */
	public static BigDecimal getBox3(Properties ctx, BigDecimal amtBox1, BigDecimal amtBox2, int instanceID, IProcessUI iProcessUI, String trxName) {
		BigDecimal totalAmt = amtBox1.add(amtBox2);
		String value = "3";
		String name = MRefList.getListName(ctx, getReferenceID(), value);
		statusUpdate(iProcessUI, name);

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}

		return totalAmt;
	}

	/** VAT reclaimed in the period on purchases and other inputs (including acquisitions from the EU) */
	public static BigDecimal getBox4(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("SELECT C_Invoice_ID FROM C_Invoice WHERE AD_Client_ID = ").append(Env.getAD_Client_ID(ctx))
				.append(" AND IsSOTrx = 'N' AND DocStatus IN ('CO', 'CL')")
				.append(" AND DateAcct >= ").append(DB.TO_DATE(from))
				.append(" AND DateAcct <= ").append(DB.TO_DATE(to));
		String value = "4";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);

			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}

	/** net VAT to pay to HMRC or reclaim */
	public static BigDecimal getBox5(Properties ctx, BigDecimal amtBox3, BigDecimal amtBox4, int instanceID, IProcessUI iProcessUI, String trxName) {
		BigDecimal totalAmt = amtBox3.add(amtBox4).abs(); // Deduct the smaller from the larger and enter the difference
		String value = "5";
		String name = MRefList.getListName(ctx, getReferenceID(), value);
		statusUpdate(iProcessUI, name);

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}

		return totalAmt;
	}

	/** total value of sales and all other outputs excluding any VAT */
	public static BigDecimal getBox6(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {

		StringBuilder sql = new StringBuilder("");
		String value = "6";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);

		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);

			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}

		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}
	
	/** the total value of purchases and all other inputs excluding any VAT */
	public static BigDecimal getBox7(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {
		
		StringBuilder sql = new StringBuilder("");
		String value = "7";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);
		
		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);
			
			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}
		
		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}

	/** */
	public static BigDecimal getBox8(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {
		
		StringBuilder sql = new StringBuilder("");
		String value = "8";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);
		
		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);
			
			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}
		
		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}
	
	
	/** */
	public static BigDecimal getBox9(Properties ctx, Timestamp from, Timestamp to, int instanceID, boolean isDetail, IProcessUI iProcessUI, String trxName) {
		
		StringBuilder sql = new StringBuilder("");
		String value = "9";
		int[] invoiceIDs = DB.getIDsEx(null, sql.toString());
		int idx = 0;
		BigDecimal totalAmt = Env.ZERO;
		String name = MRefList.getListName(ctx, getReferenceID(), value);
		
		for (int invoiceID : invoiceIDs) {
			statusUpdate(iProcessUI, name + " : " + idx++ + "/" + invoiceIDs.length);
			MInvoice i = new MInvoice(ctx, invoiceID, trxName);
			BigDecimal amt = i.getGrandTotal(true).subtract(i.getTotalLines(true));
			totalAmt = totalAmt.add(amt);
			
			if (instanceID > 0) {
				MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
				taf.setCode(value);
				taf.setName(name);
				taf.setAD_Org_ID(i.getAD_Org_ID());
				taf.setC_Invoice_ID(invoiceID);
				taf.setAmount(amt);
				taf.saveEx();
			}
		}
		
		if (instanceID > 0) { // line total
			MTXXRReport taf = new MTXXRReport(ctx, 0, instanceID, trxName);
			taf.setCode(value);
			taf.setName(name);
			taf.setAmount(totalAmt);
			taf.saveEx();
		}
		return totalAmt;
	}
	

	static int getReferenceID() {
		return MSysConfig.getIntValue(XXR_VAT_RETURN_REFERENCE_ID, 0, Env.getAD_Client_ID(Env.getCtx()));
	}

	static void statusUpdate(IProcessUI iProcessUI, String text) {
		if (iProcessUI != null)
			iProcessUI.statusUpdate(text);
	}

	public static String sendReturnData(String code, String periodKey, double vatDueSales, double vatDueAcquisitions,
								  double totalVatDue, double vatReclaimedCurrPeriod, double netVatDue, 
			                      double totalValueSalesExVAT, double totalValuePurchasesExVAT, 
			                      double totalValueGoodsSuppliedExVAT, double totalAcquisitionsExVAT,
			                      boolean finalised) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,
				                               new  ServiceConnector());
		VATReturn vatReturn = new VATReturn(periodKey, vatDueSales, vatDueAcquisitions, totalVatDue, 
				                            vatReclaimedCurrPeriod, netVatDue, totalValueSalesExVAT,
				                            totalValuePurchasesExVAT, totalValueGoodsSuppliedExVAT,
				                            totalAcquisitionsExVAT, finalised);
		
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

	public static String getObligations(String code, String dateFrom, String dateTo, String status) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,
				new  ServiceConnector());

		String retValue = "";
		try {
			System.out.println(oauthservice.toString());
			retValue = getVATObligations(oauthservice, vatservice, code, vrn, dateFrom, dateTo, status);
		} catch (IOException | UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retValue;
	}
	
	public static String getVATObligations(OauthService oauthservice, VATService vatservice, String code, 
			                               String vrn, String dateFrom, String dateTo, String status) 
			                               throws IOException, UnauthorizedException {		

		String jsonResponse;
		Token token = oauthservice.getToken(code);
		try {
			
			jsonResponse = vatservice.vatObligations(token.getAccessToken(), vrn, dateFrom, dateTo, status );
		} catch (UnauthorizedException ue) {
			Token refreshedToken = oauthservice.refreshToken(token.getRefreshToken());
			jsonResponse = vatservice.vatObligations(refreshedToken.getAccessToken(), vrn, dateFrom, dateTo, status);
		}
		
		if (jsonResponse.contains("statusCode")) {
			return jsonResponse;
		}
		else { // 200
			System.out.println(jsonResponse.toString());

			VATObligationResponse vatObligationResponse=VATParser.fromJsonObligations(jsonResponse);

			VATObligation[] obligations = vatObligationResponse.getObligations();
			StringBuilder msg = new StringBuilder("Obligations: \n");
			for (VATObligation obligation : obligations) {
				System.out.println(obligation.getStart());
				System.out.println(obligation.getEnd());
				System.out.println(obligation.getStatus());
				msg.append("StartDate=").append(obligation.getStart()).append(" - ")
				.append("EndDate=").append(obligation.getEnd()).append(" - ")
				.append("Status=").append(obligation.getStatus()).append(" - ");
				if (obligation.getStatus().compareTo("F") == 0) {
					System.out.println(obligation.getReceived());
					msg.append("Received=").append(obligation.getReceived()).append("\n");
				}
				if (obligation.getStatus().compareTo("O") == 0) {
					System.out.println(obligation.getDue());
					msg.append("Due=").append(obligation.getDue()).append("\n");
				}
				System.out.println(obligation.getPeriodKey());
			}
			

			return msg.toString();
		}
	}
	

	public static String getLiabilities(String code, String dateFrom, String dateTo) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,
				new  ServiceConnector());

		String retValue = "";
		try {
			System.out.println(oauthservice.toString());
			retValue = getVATLiabilities(oauthservice, vatservice, code, vrn, dateFrom, dateTo);
		} catch (IOException | UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retValue;
	}

	public static String getVATLiabilities(OauthService oauthservice, VATService vatservice, String code, 
			String vrn, String dateFrom, String dateTo) 
					throws IOException, UnauthorizedException {		

		String jsonResponse;
		Token token = oauthservice.getToken(code);
		try {

			jsonResponse = vatservice.vatLiabilities(token.getAccessToken(), vrn, dateFrom, dateTo);
		} catch (UnauthorizedException ue) {
			Token refreshedToken = oauthservice.refreshToken(token.getRefreshToken());
			jsonResponse = vatservice.vatLiabilities(refreshedToken.getAccessToken(), vrn, dateFrom, dateTo);
		}

		if (jsonResponse.contains("code") || jsonResponse.contains("statusCode")) {
			return jsonResponse;
		}
		else { // 200
			System.out.println(jsonResponse.toString());

			VATLiabilitiesResponse vatLiabilitiesResponse=VATParser.fromJsonLiabilities(jsonResponse);

			List<Liability> liabilities = vatLiabilitiesResponse.getLiabilities();
			StringBuilder msg = new StringBuilder("Liabilities: \n");
			for (Liability liability : liabilities) {
				msg.append("- TaxPeriod from ").append(liability.getTaxPeriod().getFrom())
				.append(" to ").append(liability.getTaxPeriod().getTo()).append(" - ")
				.append("Type=").append(liability.getType()).append(" \n ")
				.append("-- Original Amount=").append(liability.getOriginalAmount()).append(" - ")
				.append("Outstanding Amount=").append(liability.getOutstandingAmount()).append(" - ")
				.append("Due Date=").append(liability.getDue()).append(" \n ");
			}

			return msg.toString();
		}
	}

	public static String getPayments(String code, String dateFrom, String dateTo) {
		OauthService oauthservice = HmrcUtil.getOauthService();
		VATService vatservice = new VATService(urlHmrc,clientId,clientSecret,callbackUrl,serverToken,
				new  ServiceConnector());

		String retValue = "";
		try {
			System.out.println(oauthservice.toString());
			retValue = getVATPayments(oauthservice, vatservice, code, vrn, dateFrom, dateTo);
		} catch (IOException | UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retValue;
	}

	public static String getVATPayments(OauthService oauthservice, VATService vatservice, String code, 
			String vrn, String dateFrom, String dateTo) 
					throws IOException, UnauthorizedException {		

		String jsonResponse;
		Token token = oauthservice.getToken(code);
		try {

			jsonResponse = vatservice.vatPayments(token.getAccessToken(), vrn, dateFrom, dateTo);
		} catch (UnauthorizedException ue) {
			Token refreshedToken = oauthservice.refreshToken(token.getRefreshToken());
			jsonResponse = vatservice.vatPayments(refreshedToken.getAccessToken(), vrn, dateFrom, dateTo);
		}

		if (jsonResponse.contains("code") || jsonResponse.contains("statusCode")) {
			return jsonResponse;
		}
		else { // 200
			System.out.println(jsonResponse.toString());

			VATPaymentsResponse vatPaymentsResponse=VATParser.fromJsonPayments(jsonResponse);

			List<Payment> payments = vatPaymentsResponse.getPayments();
			StringBuilder msg = new StringBuilder("Payments: \n");
			for (Payment payment : payments) {
				msg.append("- Amount ").append(payment.getAmount());
				if (payment.getReceived() != null) {
					msg.append(" received ").append(payment.getReceived());
				}
				else {
					msg.append(" without date ");
				}
				
				msg.append(" \n ");
			}

			return msg.toString();
		}
	}

}
