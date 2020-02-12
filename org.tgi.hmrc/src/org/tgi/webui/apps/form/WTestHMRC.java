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
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.compiere.util.Util;
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

public class WTestHMRC extends ADForm implements EventListener<Event> {

	private static final long serialVersionUID = -8528918348159236707L;

	// FIXME: change before next testing of same period, set back to "A" for production! 
	private static final String periodDelimiter = "D";
	
	private Button btn1, btn2, btn0, btn3;
	private Label labelLegal = new Label();
	private Label labelDateAcct = new Label();
	private Label labelDateAcct2 = new Label();
	private Label labelPeriodKey = new Label();
	private Label labelRate = new Label();
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
	Textbox tbUrlWithCode = new Textbox();
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
	
	protected void initForm() {
		try {
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
		labelRate.setText(Msg.translate(Env.getCtx(), "Rate"));
		btn0 = new Button("1. Select data ");
		btn0.addEventListener(Events.ON_CLICK, this);

		Hlayout hl0 = new Hlayout();
		hl0.setValign("middle");
		hl0.appendChild(labelDateAcct);
		hl0.appendChild(fieldDateAcct.getComponent());
		hl0.appendChild(labelDateAcct2);
		hl0.appendChild(fieldDateAcct2.getComponent());
		hl0.appendChild(btn0);

		Vlayout vl = new Vlayout();
		vl.appendChild(hl0);
		Hlayout hlr = new Hlayout();
		hlr.appendChild(labelRate);
		hlr.appendChild(tbRate);
		vl.appendChild(hlr);
		// PeriodKey
		// The ID code for the period that this obligation belongs to. 
		// The format is a string of four alphanumeric characters. 
		// Occasionally the format includes the # symbol
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
		vl.appendChild(hlvds);
		// vatDueAcquisitions
		// VAT due on acquisitions from other EC Member States. 
		// This corresponds to box 2 on the VAT Return form. 
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hlvda = new Hlayout();
		labelvatDueAcquisitions.setText(Msg.translate(Env.getCtx(), "2 vatDueAcquisitions"));
		hlvda.appendChild(labelvatDueAcquisitions);
		hlvda.appendChild(tbvatDueAcquisitions);
		vl.appendChild(hlvda);
		// totalVatDue
		// Total VAT due (the sum of vatDueSales and vatDueAcquisitions).
		// This corresponds to box 3 on the VAT Return form.
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hltvd = new Hlayout();
		labeltotalVatDue.setText(Msg.translate(Env.getCtx(), "3 totalVatDue"));
		hltvd.appendChild(labeltotalVatDue);
		hltvd.appendChild(tbtotalVatDue);
		vl.appendChild(hltvd);
		// vatReclaimedCurrPeriod
		// VAT reclaimed on purchases and other inputs (including acquisitions from the EC). 
		// This corresponds to box 4 on the VAT Return form. 
		// The value must be between -9999999999999.99 and 9999999999999.99.
		Hlayout hlvrcp = new Hlayout();
		labelvatReclaimedCurrPeriod.setText(Msg.translate(Env.getCtx(), "4 vatReclaimedCurrPeriod"));
		hlvrcp.appendChild(labelvatReclaimedCurrPeriod);
		hlvrcp.appendChild(tbvatReclaimedCurrPeriod);
		vl.appendChild(hlvrcp);
		// netVatDue
		// The difference between totalVatDue and vatReclaimedCurrPeriod. 
		// This corresponds to box 5 on the VAT Return form. 
		// The value must be between 0.00 and 99999999999.99
		Hlayout hlnvd = new Hlayout();
		labelnetVatDue.setText(Msg.translate(Env.getCtx(), "5 netVatDue"));
		hlnvd.appendChild(labelnetVatDue);
		hlnvd.appendChild(tbnetVatDue);
		vl.appendChild(hlnvd);
		// totalValueSalesExVAT
		// Total value of sales and all other outputs excluding any VAT. 
		// This corresponds to box 6 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltvsev = new Hlayout();
		labeltotalValueSalesExVAT.setText(Msg.translate(Env.getCtx(), "6 totalValueSalesExVAT"));
		hltvsev.appendChild(labeltotalValueSalesExVAT);
		hltvsev.appendChild(tbtotalValueSalesExVAT);
		vl.appendChild(hltvsev);
		// totalValuePurchasesExVAT
		// Total value of purchases and all other inputs excluding any VAT (including exempt purchases). 
		// This corresponds to box 7 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltvpev = new Hlayout();
		labeltotalValuePurchasesExVAT.setText(Msg.translate(Env.getCtx(), "7 totalValuePurchasesExVAT"));
		hltvpev.appendChild(labeltotalValuePurchasesExVAT);
		hltvpev.appendChild(tbtotalValuePurchasesExVAT);
		vl.appendChild(hltvpev);
		// totalValueGoodsSuppliedExVAT
		// Total value of all supplies of goods and related costs, excluding any VAT, to other EC member states. 
		// This corresponds to box 8 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltvgsev = new Hlayout();
		labeltotalValueGoodsSuppliedExVAT.setText(Msg.translate(Env.getCtx(), "8 totalValueGoodsSuppliedExVAT"));
		hltvgsev.appendChild(labeltotalValueGoodsSuppliedExVAT);
		hltvgsev.appendChild(tbtotalValueGoodsSuppliedExVAT);
		vl.appendChild(hltvgsev);
		// totalAcquisitionsExVAT
		// Total value of acquisitions of goods and related costs excluding any VAT, from other EC member states. 
		// This corresponds to box 9 on the VAT Return form. 
		// The value must be in pounds (no pence) between -9999999999999 and 9999999999999.
		Hlayout hltaev = new Hlayout();
		labeltotalAcquisitionsExVAT.setText(Msg.translate(Env.getCtx(), "9 totalAcquisitionsExVAT"));
		hltaev.appendChild(labeltotalAcquisitionsExVAT);
		hltaev.appendChild(tbtotalAcquisitionsExVAT);
		vl.appendChild(hltaev);
		
		btn1 = new Button("2. Request an OAuth 2.0 authorisation code with the required scope");
		btn1.addEventListener(Events.ON_CLICK, this);
		tbCredentials.setValue(username + " / " + userpwd);
		tbCredentials.setReadonly(true);

		Hlayout hl = new Hlayout();
		hl.setValign("middle");
		hl.appendChild(btn1);
		hl.appendChild(tbCredentials);
		vl.appendChild(hl);

		labelLegal.setText(Msg.translate(Env.getCtx(), "When you submit this VAT information you are making a legal\r\n" + 
				"declaration that the information is true and complete. A false declaration\r\n" + 
				"can result in prosecution."));
		btn3 = new Button("3. Accept legal ");
		btn3.addEventListener(Events.ON_CLICK, this);

		btn2 = new Button("4. Exchange the OAuth 2.0 authorisation code for an access token and send data");
		btn2.addEventListener(Events.ON_CLICK, this);

		vl.appendChild(tbUrlWithCode);
		vl.appendChild(labelLegal);
		vl.appendChild(btn3);
		vl.appendChild(btn2);
		vl.appendChild(tbResult);

		center.appendChild(vl);

		tbResult.setRows(10);
		tbResult.setHflex("true");
		tbUrlWithCode.setPlaceholder("Paste URL starting with idempiere.alfredkochen.de and containing ?code=");
		tbUrlWithCode.setHflex("true");
		tbPeriodKey.setPlaceholder("Period Key (eg: 'A001'");

		tbUrlWithCode.setEnabled(false);
		//tbPeriodKey.setEnabled(false);
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
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
	}

	public void onEvent(Event event) throws Exception {

		Properties ctx = Env.getCtx();		
		
		if (event.getTarget() == btn1) {
			String url = HmrcUtil.getAuthorizationRequestUrl();

			Desktop desktop = AEnv.getDesktop();
			ServerPushTemplate pushUpdateUi = new ServerPushTemplate (desktop);
			CommonServerPushCallbackOpenUrl callback = new CommonServerPushCallbackOpenUrl();
			callback.setUrl(url);
			callback.setOpenInNewTab(true);
			pushUpdateUi.executeAsync (callback);

			tbUrlWithCode.setEnabled(true);
			//tbPeriodKey.setEnabled(true);
			btn3.setEnabled(true);
			btn2.setEnabled(false);

		}
		else if (event.getTarget() == btn2) {

			//String periodKey = tbPeriodKey.getValue();
			Calendar periodCal = TimeUtil.getCalendar((Timestamp) fieldDateAcct.getValue());
			LocalDate dateAcctLocal = ((Timestamp) fieldDateAcct.getValue()).toLocalDateTime().toLocalDate();
			//String periodKey = String.valueOf(periodCal.get(Calendar.YEAR)) + "B" + String.valueOf((periodCal.get(Calendar.MONTH) / 3) + 1);
			String periodKey = String.valueOf(periodCal.get(Calendar.YEAR) - 2000) + periodDelimiter + String.valueOf(dateAcctLocal.get(IsoFields.QUARTER_OF_YEAR));
			if (Util.isEmpty(periodKey))
				throw new WrongValueException(tbPeriodKey, "Period key must be set !");

			if (Util.isEmpty(tbUrlWithCode.getValue()))
				throw new WrongValueException(tbUrlWithCode, "You did not past the URL !");

			String url = tbUrlWithCode.getValue();

			int pos = url.indexOf("?code=");
			if (pos <= 0)
				throw new WrongValueException(tbUrlWithCode, "URL doesn't contain code !");

			String code = url.substring(pos + 6);
			System.out.println("url  = " + url);
			System.out.println("code = " + code);

			String msg = HmrcUtil.sendData(code, 
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
				btn1.setEnabled(true);
			}
			else
				FDialog.info(getWindowNo(), this, "", msg);

		}
		else if (event.getTarget() == btn0) {

			if(fieldDateAcct.getValue()==null)
				throw new WrongValueException(fieldDateAcct.getComponent(), "StartDate must be set !");
			if(fieldDateAcct2.getValue()==null)
				throw new WrongValueException(fieldDateAcct2.getComponent(), "EndDate must be set !");
			// FIXME: avoid fix EUR to GBP rate, enable others 
			BigDecimal rate = MConversionRate.getRate (102, 114, (Timestamp) fieldDateAcct2.getValue(), 
					 								   114, Env.getAD_Client_ID(ctx), 
					 								   Env.getAD_Org_ID(ctx));
			rate = rate.setScale(4, RoundingMode.HALF_UP);
			tbRate.setValue(rate.toString());
            String trxname = Trx.createTrxName();
			Query query = new Query(ctx, "RV_C_InvoiceTax", 
					"RV_C_InvoiceTax.AD_Org_ID=? AND TRUNC(RV_C_InvoiceTax.DateInvoiced) BETWEEN trunc(cast(? as date)) AND trunc(cast(? as date)) AND RV_C_InvoiceTax.C_Tax_ID=?", trxname);
			//  UK Sales Tax 1000022
			query.setParameters(Env.getAD_Org_ID(ctx),fieldDateAcct.getValue(),fieldDateAcct2.getValue(),1000022);
			BigDecimal uksalesbase = query.aggregate("TaxBaseAmt", Query.AGGREGATE_SUM);
			BigDecimal uksalestax = query.aggregate("TaxAmt", Query.AGGREGATE_SUM);
			BigDecimal uksalesbasegbp = uksalesbase.multiply(rate);
			BigDecimal uksalestaxgbp = uksalestax.multiply(rate);
			tbvatDueSales.setValue(uksalestaxgbp.setScale(2, RoundingMode.HALF_UP).toString()); // 1
			tbvatDueAcquisitions.setValue("0"); // 2
			tbtotalVatDue.setValue(uksalestaxgbp.setScale(2, RoundingMode.HALF_UP).toString()); // 3
			//  UK Tax 1000021
			query.setParameters(Env.getAD_Org_ID(ctx),fieldDateAcct.getValue(),fieldDateAcct2.getValue(),1000021);
			BigDecimal ukbase = query.aggregate("TaxBaseAmt", Query.AGGREGATE_SUM);
			BigDecimal uktax = query.aggregate("TaxAmt", Query.AGGREGATE_SUM);
			BigDecimal ukbasegbp = ukbase.multiply(rate);
			BigDecimal uktaxgbp = uktax.multiply(rate);
			tbvatReclaimedCurrPeriod.setValue(uktaxgbp.setScale(2, RoundingMode.HALF_UP).toString()); // 4
			tbnetVatDue.setValue(uksalestaxgbp.subtract(uktaxgbp).setScale(2, RoundingMode.HALF_UP).toString()); // 5
			tbtotalValueSalesExVAT.setValue(uksalesbasegbp.setScale(0, RoundingMode.DOWN).toString()); // 6
			tbtotalValuePurchasesExVAT.setValue(ukbasegbp.setScale(0, RoundingMode.DOWN).toString()); // 7
			tbtotalValueGoodsSuppliedExVAT.setValue("0"); // 8
			tbtotalAcquisitionsExVAT.setValue("0"); // 9
			btn1.setEnabled(true);
			btn2.setEnabled(false);
			btn3.setEnabled(false);
						

//			StringBuilder sql = new StringBuilder("SELECT abs(fa.amtacctdr-fa.amtacctcr), (fa.amtacctdr-fa.amtacctcr),") // 1-2
//					PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			try
//			{
//				pstmt = DB.prepareStatement(sql.toString(), null);
//				int i = 1;
//				pstmt.setInt(i++, m_AD_Client_ID);
//				

		}		
		else if (event.getTarget() == btn3) {
			String msg = "Thank you for accepting the legal declaration";
			FDialog.info(getWindowNo(), this, "", msg);
			btn3.setEnabled(false);
			btn0.setEnabled(false);
			btn1.setEnabled(false);
			btn2.setEnabled(true);
			finalised = true;

		}

	}
}
