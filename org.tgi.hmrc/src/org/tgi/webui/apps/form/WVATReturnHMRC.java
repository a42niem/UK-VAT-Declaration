package org.tgi.webui.apps.form;

import static org.tgi.util.HmrcUtil.username;
import static org.tgi.util.HmrcUtil.userpwd;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.util.ServerPushTemplate;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MConversionRate;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.tgi.model.MXXRTaxDeclaration;
import org.tgi.model.MXXRTaxDeclarationLine;
import org.tgi.util.HmrcUtil;
import org.tgi.webui.util.CommonServerPushCallbackOpenUrl;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.North;
import org.zkoss.zul.Vlayout;

/**
 * Panel used to send HMRC data
 * @author nmicoud, TGI
 */

public class WVATReturnHMRC extends ADForm implements EventListener<Event> {

	private static final long serialVersionUID = -8528918348159236707L;

	private final static String scopeWrite="write:vat";
	private final static String scopeRead="read:vat";
	
	// FIXME: change before next testing of same period, set back to "A" for production! 
	private static final String periodDelimiter = "D";
	
	private Button btnAuthorizeObligation, btnGetPeriodKey, btnAuthorizeReturn, btnSendReturn;
	private Label labelDateAcct = new Label();
	private Label labelDateAcct2 = new Label();
	private Label labelvatDueSales = new Label(); // : 105.5,
	private Label labelvatDueAcquisitions = new Label(); // : -100.45,
	private Label labeltotalVatDue = new Label(); // : 5.05,
	private Label labelvatReclaimedCurrPeriod = new Label(); // : 105.15,
	private Label labelnetVatDue = new Label(); // : 100.1,
	private Label labeltotalValueSalesExVAT = new Label(); // : 300.0,
	private Label labeltotalValuePurchasesExVAT = new Label(); // : 300.0,
	private Label labeltotalValueGoodsSuppliedExVAT = new Label(); // : 3000.0,
	private Label labeltotalAcquisitionsExVAT = new Label(); // : 3000.0,
	private WDateEditor  fieldDateAcct = new WDateEditor();
	private WDateEditor  fieldDateAcct2 = new WDateEditor();
	Textbox tbCredentials = new Textbox();
	Textbox tbRate = new Textbox();
	Textbox tbResult = new Textbox();
	Textbox tbPeriodKey = new Textbox();
	Textbox tbUrlWithCodeObligation = new Textbox();
	Textbox tbUrlWithCodeReturn = new Textbox();
	  //"periodKey" : "18Q1",
	Textbox tbvatDueSales = new Textbox(); // : 105.5,
	Textbox tbvatDueAcquisitions = new Textbox(); // : -100.45,
	Textbox tbtotalVatDue = new Textbox(); // : 5.05,
	Textbox tbvatReclaimedCurrPeriod = new Textbox(); // : 105.15,
	Textbox tbnetVatDue = new Textbox(); // : 100.1,
	Textbox tbtotalValueSalesExVAT = new Textbox(); // : 300.0,
	Textbox tbtotalValuePurchasesExVAT = new Textbox(); // : 300.0,
	Textbox tbtotalValueGoodsSuppliedExVAT = new Textbox(); // : 3000.0,
	Textbox tbtotalAcquisitionsExVAT = new Textbox(); // : 3000.0,
	  //"finalised" : true
	private boolean finalised = false;
	private Integer XXRTaxDeclarationID = 0;
	private MXXRTaxDeclaration mtd = null;
	private Properties ctx = Env.getCtx();
	private MXXRTaxDeclarationLine line1 = null;
	private MXXRTaxDeclarationLine line2 = null;
	private MXXRTaxDeclarationLine line3 = null;
	private MXXRTaxDeclarationLine line4 = null;
	private MXXRTaxDeclarationLine line5 = null;
	private MXXRTaxDeclarationLine line6 = null;
	private MXXRTaxDeclarationLine line7 = null;
	private MXXRTaxDeclarationLine line8 = null;
	private MXXRTaxDeclarationLine line9 = null;
	private String periodKey = null;
	
	protected void initForm() {
		try {
			XXRTaxDeclarationID = (Integer) this.getGridTab().getValue("XXR_TaxDeclaration_ID");
			mtd = new MXXRTaxDeclaration(ctx, XXRTaxDeclarationID, null);
			line1 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_VATDueInThePeriodOnSalesAndOtherOutputs)
					.first();
			line2 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_VATDueInPeriodOnAcquisitionsFromOtherMemberOfEU)
					.first();
			line3 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_TotalVATDue)
					.first();
			line4 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_VATReclaimedInThePeriodOnPurchasesAndOtherInputs)
					.first();
			line5 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_NetVATToPayToHMRCOrReclaim)
					.first();
			line6 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_TotalValueOfSalesAndAllOtherOutputsExcludingAnyVAT)
					.first();
			line7 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_TotalValueOfPurchasesAndAllOtherInputsExcludingVAT)
					.first();
			line8 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_TotalValueSuppliesGoodsAndRelatedCosts_NoVAT_EU)
					.first();
			line9 = new Query (ctx, MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", null)
					.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
							MXXRTaxDeclarationLine.TYPE_TotalValueAcquisitionsGoodsAndRelatedCosts_NoVAT_EU)
					.first();
			init();
		}
		catch (Exception e) {
			logger.severe("Error when opening Test HMRC form : " + e);
		}
	}

	private void init() throws Exception {
		this.setHeight("100%");

		Borderlayout mainLayout = new Borderlayout();
		mainLayout.setStyle("width: 100%; height: 100%; position: absolute;");
		appendChild(mainLayout);

		North north = new North();
		mainLayout.appendChild(north);
		ZKUpdateUtil.setVflex(north, "1");

		Center center = new Center();
		mainLayout.appendChild(center);
		center.setAutoscroll(true);

		labelDateAcct.setText(Msg.translate(Env.getCtx(), "DateAcct"));
		labelDateAcct2.setText("-");
//		labelRate.setText(Msg.translate(Env.getCtx(), "Rate"));
//		btnSelect = new Button("1. Select data ");
//		btnSelect.addEventListener(Events.ON_CLICK, this);

		Hlayout hl0 = new Hlayout();
		hl0.setValign("middle");
		hl0.appendChild(labelDateAcct);
		hl0.appendChild(fieldDateAcct.getComponent());
		fieldDateAcct.setValue(mtd.getDateFrom());
		fieldDateAcct.setReadWrite(false);
		hl0.appendChild(labelDateAcct2);
		hl0.appendChild(fieldDateAcct2.getComponent());
		fieldDateAcct2.setValue(mtd.getDateTo());
		fieldDateAcct2.setReadWrite(false);

		Vlayout vl = new Vlayout();
		vl.appendChild(hl0);
//		Hlayout hlper = new Hlayout();
//		labelPeriodKey.setText(Msg.translate(Env.getCtx(), "PeriodKey"));
//		hlper.appendChild(labelPeriodKey);
//		hlper.appendChild(tbPeriodKey);
//		vl.appendChild(hlper);
		// vatDueSales
		// VAT due on sales and other outputs.
		// This corresponds to box 1 on the VAT Return form. 
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hlvds = new Hlayout();
		labelvatDueSales.setText(Msg.translate(Env.getCtx(), "1 vatDueSales"));
		hlvds.appendChild(labelvatDueSales);
		hlvds.appendChild(tbvatDueSales);
		tbvatDueSales.setValue(line1.getXXR_SentAmount().toString());
		vl.appendChild(hlvds);
		// vatDueAcquisitions
		// VAT due on acquisitions from other EC Member States. 
		// This corresponds to box 2 on the VAT Return form. 
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hlvda = new Hlayout();
		labelvatDueAcquisitions.setText(Msg.translate(Env.getCtx(), "2 vatDueAcquisitions"));
		hlvda.appendChild(labelvatDueAcquisitions);
		hlvda.appendChild(tbvatDueAcquisitions);
		tbvatDueAcquisitions.setValue(line2.getXXR_SentAmount().toString());
		vl.appendChild(hlvda);
		// totalVatDue
		// Total VAT due (the sum of vatDueSales and vatDueAcquisitions).
		// This corresponds to box 3 on the VAT Return form.
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hltvd = new Hlayout();
		labeltotalVatDue.setText(Msg.translate(Env.getCtx(), "3 totalVatDue"));
		hltvd.appendChild(labeltotalVatDue);
		hltvd.appendChild(tbtotalVatDue);
		tbtotalVatDue.setValue(line3.getXXR_SentAmount().toString());
		vl.appendChild(hltvd);
		// vatReclaimedCurrPeriod
		// VAT reclaimed on purchases and other inputs (including acquisitions from the EC). 
		// This corresponds to box 4 on the VAT Return form. 
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hlvrcp = new Hlayout();
		labelvatReclaimedCurrPeriod.setText(Msg.translate(Env.getCtx(), "4 vatReclaimedCurrPeriod"));
		hlvrcp.appendChild(labelvatReclaimedCurrPeriod);
		hlvrcp.appendChild(tbvatReclaimedCurrPeriod);
		tbvatReclaimedCurrPeriod.setValue(line4.getXXR_SentAmount().toString());
		vl.appendChild(hlvrcp);
		// netVatDue
		// The difference between totalVatDue and vatReclaimedCurrPeriod. 
		// This corresponds to box 5 on the VAT Return form. 
		// The value must be between 0.00 and 99999999999.99
		Hlayout hlnvd = new Hlayout();
		labelnetVatDue.setText(Msg.translate(Env.getCtx(), "5 netVatDue"));
		hlnvd.appendChild(labelnetVatDue);
		hlnvd.appendChild(tbnetVatDue);
		tbnetVatDue.setValue(line5.getXXR_SentAmount().toString());
		vl.appendChild(hlnvd);
		// totalValueSalesExVAT
		// Total value of sales and all other outputs excluding any VAT. 
		// This corresponds to box 6 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltvsev = new Hlayout();
		labeltotalValueSalesExVAT.setText(Msg.translate(Env.getCtx(), "6 totalValueSalesExVAT"));
		hltvsev.appendChild(labeltotalValueSalesExVAT);
		hltvsev.appendChild(tbtotalValueSalesExVAT);
		tbtotalValueSalesExVAT.setValue(line6.getXXR_SentAmount().toString());
		vl.appendChild(hltvsev);
		// totalValuePurchasesExVAT
		// Total value of purchases and all other inputs excluding any VAT (including exempt purchases). 
		// This corresponds to box 7 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltvpev = new Hlayout();
		labeltotalValuePurchasesExVAT.setText(Msg.translate(Env.getCtx(), "7 totalValuePurchasesExVAT"));
		hltvpev.appendChild(labeltotalValuePurchasesExVAT);
		hltvpev.appendChild(tbtotalValuePurchasesExVAT);
		tbtotalValuePurchasesExVAT.setValue(line7.getXXR_SentAmount().toString());
		vl.appendChild(hltvpev);
		// totalValueGoodsSuppliedExVAT
		// Total value of all supplies of goods and related costs, excluding any VAT, to other EC member states. 
		// This corresponds to box 8 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltvgsev = new Hlayout();
		labeltotalValueGoodsSuppliedExVAT.setText(Msg.translate(Env.getCtx(), "8 totalValueGoodsSuppliedExVAT"));
		hltvgsev.appendChild(labeltotalValueGoodsSuppliedExVAT);
		hltvgsev.appendChild(tbtotalValueGoodsSuppliedExVAT);
		tbtotalValueGoodsSuppliedExVAT.setValue(line8.getXXR_SentAmount().toString());
		vl.appendChild(hltvgsev);
		// totalAcquisitionsExVAT
		// Total value of acquisitions of goods and related costs excluding any VAT, from other EC member states. 
		// This corresponds to box 9 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltaev = new Hlayout();
		labeltotalAcquisitionsExVAT.setText(Msg.translate(Env.getCtx(), "9 totalAcquisitionsExVAT"));
		hltaev.appendChild(labeltotalAcquisitionsExVAT);
		hltaev.appendChild(tbtotalAcquisitionsExVAT);
		tbtotalAcquisitionsExVAT.setValue(line9.getXXR_SentAmount().toString());
		vl.appendChild(hltaev);
		
		btnAuthorizeObligation = new Button("1. Request an OAuth 2.0 authorisation code with the required scope");
		btnAuthorizeObligation.addEventListener(Events.ON_CLICK, this);
		vl.appendChild(btnAuthorizeObligation);

		vl.appendChild(tbUrlWithCodeObligation);

		btnGetPeriodKey = new Button("2. Exchange the OAuth 2.0 authorisation code for an access token and send data");
		btnGetPeriodKey.addEventListener(Events.ON_CLICK, this);

		vl.appendChild(btnGetPeriodKey);

		btnAuthorizeReturn = new Button("3. Request an OAuth 2.0 authorisation code with the required scope");
		btnAuthorizeReturn.addEventListener(Events.ON_CLICK, this);

		vl.appendChild(btnAuthorizeReturn);
		vl.appendChild(tbUrlWithCodeReturn);

		btnSendReturn = new Button("4. Exchange the OAuth 2.0 authorisation code for an access token and send data");
		btnSendReturn.addEventListener(Events.ON_CLICK, this);

		vl.appendChild(btnSendReturn);


		vl.appendChild(tbResult);

		center.appendChild(vl);

		tbResult.setRows(10);
		tbResult.setHflex("true");
		tbUrlWithCodeObligation.setPlaceholder("Paste URL starting with idempiere.alfredkochen.de and containing ?code=");
		tbUrlWithCodeObligation.setHflex("true");
		tbUrlWithCodeReturn.setPlaceholder("Paste URL starting with idempiere.alfredkochen.de and containing ?code=");
		tbUrlWithCodeReturn.setHflex("true");

		tbUrlWithCodeObligation.setEnabled(false);
		tbRate.setEnabled(false);
		tbvatDueSales.setEnabled(false);
		tbvatDueAcquisitions.setEnabled(false);
		tbtotalVatDue.setEnabled(false);
		tbvatReclaimedCurrPeriod.setEnabled(false);
		tbnetVatDue.setEnabled(false);
		tbtotalValueSalesExVAT.setEnabled(false);
		tbtotalValuePurchasesExVAT.setEnabled(false);
		tbtotalValueGoodsSuppliedExVAT.setEnabled(false);
		tbtotalAcquisitionsExVAT.setEnabled(false);
		btnAuthorizeObligation.setEnabled(true);
		btnAuthorizeReturn.setEnabled(false);
		btnSendReturn.setEnabled(false);
	}

	public void onEvent(Event event) throws Exception {

		Properties ctx = Env.getCtx();		
		
		if (event.getTarget() == btnAuthorizeObligation) {
			String url = HmrcUtil.getAuthorizationRequestUrl(scopeRead);

			Desktop desktop = AEnv.getDesktop();
			ServerPushTemplate pushUpdateUi = new ServerPushTemplate (desktop);
			CommonServerPushCallbackOpenUrl callback = new CommonServerPushCallbackOpenUrl();
			callback.setUrl(url);
			callback.setOpenInNewTab(true);
			pushUpdateUi.executeAsync (callback);

			tbUrlWithCodeObligation.setEnabled(true);
			btnGetPeriodKey.setEnabled(true);

		}
		else if (event.getTarget() == btnGetPeriodKey) {

			String year = null;
			int month = 0;
			int day = 0;
			String fromDate = null;
			if (fieldDateAcct.getValue() != null) {
				Calendar fromCal = TimeUtil.getCalendar((Timestamp) fieldDateAcct.getValue());
				year = String.valueOf(fromCal.get(Calendar.YEAR));
				month = fromCal.get(Calendar.MONTH);
				day = fromCal.get(Calendar.DAY_OF_MONTH);
				fromDate = year + "-" 
				           + ((month < 10) ? "0" : "") + String.valueOf(month+1) + "-"
				           + ((day < 10) ? "0" : "") + String.valueOf(day);
			}
			
			String toDate = null;
			if (fieldDateAcct2.getValue() != null) {
				Calendar toCal = TimeUtil.getCalendar((Timestamp) fieldDateAcct2.getValue());
				year = String.valueOf(toCal.get(Calendar.YEAR));
				month = toCal.get(Calendar.MONTH);
				day = toCal.get(Calendar.DAY_OF_MONTH);
				toDate = year + "-" 
				           + ((month < 10) ? "0" : "") + String.valueOf(month+1) + "-"
				           + ((day < 10) ? "0" : "") + String.valueOf(day);
			}

			if (Util.isEmpty(tbUrlWithCodeObligation.getValue()))
				throw new WrongValueException(tbUrlWithCodeObligation, "You did not past the URL !");

			String url = tbUrlWithCodeObligation.getValue();

			int pos = url.indexOf("?code=");
			if (pos <= 0)
				throw new WrongValueException(tbUrlWithCodeObligation, "URL doesn't contain code !");

			String code = url.substring(pos + 6);
			System.out.println("url  = " + url);
			System.out.println("code = " + code);

			// PeriodKey
			// The ID code for the period that this obligation belongs to. 
			// The format is a string of four alphanumeric characters. 
			// Occasionally the format includes the # symbol
			periodKey = HmrcUtil.getPeriodKey(code,
					 fromDate, 
					 toDate
			   		);
			mtd.setXXR_PeriodKey(periodKey);
			mtd.save();
			
			btnAuthorizeReturn.setEnabled(true);
			
		}
		if (event.getTarget() == btnAuthorizeReturn) {
			String url = HmrcUtil.getAuthorizationRequestUrl(scopeWrite);

			Desktop desktop = AEnv.getDesktop();
			ServerPushTemplate pushUpdateUi = new ServerPushTemplate (desktop);
			CommonServerPushCallbackOpenUrl callback = new CommonServerPushCallbackOpenUrl();
			callback.setUrl(url);
			callback.setOpenInNewTab(true);
			pushUpdateUi.executeAsync (callback);

			tbUrlWithCodeReturn.setEnabled(true);
			btnSendReturn.setEnabled(true);

		}
		else if (event.getTarget() == btnSendReturn) {

			if (Util.isEmpty(tbUrlWithCodeReturn.getValue()))
				throw new WrongValueException(tbUrlWithCodeReturn, "You did not past the URL !");

			String url = tbUrlWithCodeReturn.getValue();

			int pos = url.indexOf("?code=");
			if (pos <= 0)
				throw new WrongValueException(tbUrlWithCodeReturn, "URL doesn't contain code !");

			String code = url.substring(pos + 6);
			System.out.println("url  = " + url);
			System.out.println("code = " + code);
			
			finalised = mtd.isXXR_IsFinalised();
			if (!finalised)
				throw new WrongValueException(tbUrlWithCodeObligation, "You did not finalise! Go back to declaration");
			
			String msg = HmrcUtil.sendReturnData(code, 
										   periodKey, 
										   Double.parseDouble(tbvatDueSales.getValue()),
										   Double.parseDouble(tbvatDueAcquisitions.getValue()),
										   Double.parseDouble(tbtotalVatDue.getValue()),
										   Double.parseDouble(tbvatReclaimedCurrPeriod.getValue()),
										   Double.parseDouble(tbnetVatDue.getValue()),
										   Double.parseDouble(tbtotalValueSalesExVAT.getValue()),
										   Double.parseDouble(tbtotalValuePurchasesExVAT.getValue()),
										   Double.parseDouble(tbtotalValueGoodsSuppliedExVAT.getValue()),
										   Double.parseDouble(tbtotalAcquisitionsExVAT.getValue()),
										   finalised
										   );
			tbResult.setValue(msg.toString());

			if (msg.startsWith("Error")) {
				FDialog.error(getWindowNo(), "Error", msg);
				btnAuthorizeReturn.setEnabled(true);
			}
			else
				FDialog.info(getWindowNo(), this, "", msg);
				mtd.setXXR_VatReturnResponse(msg);
				mtd.setXXR_DataSentOn(new Timestamp(System.currentTimeMillis()));
				mtd.setXXR_DataSentByUser_ID(Env.getAD_User_ID(ctx));
				mtd.setProcessed(true);
				mtd.saveEx();
		}

	}
}
