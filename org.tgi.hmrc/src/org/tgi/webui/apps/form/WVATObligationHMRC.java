package org.tgi.webui.apps.form;

import java.sql.Timestamp;
import java.util.Calendar;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.util.ServerPushTemplate;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.adempiere.webui.window.FDialog;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.tgi.util.HmrcUtil;
import org.tgi.webui.util.CommonServerPushCallbackOpenUrl;
import org.zkoss.zhtml.Object;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.North;
import org.zkoss.zul.Script;
import org.zkoss.zul.Vlayout;

/**
 * Panel used to query HMRC VAT obligations
 * @author a42niem, action 42 GmbH
 */

public class WVATObligationHMRC extends ADForm implements EventListener<Event> {

	private static final long serialVersionUID = -8528918348159236707L;

	private final static String scope="read:vat";
	
	private Button btnAuthorize, btnSend, btnSelect;
	private Label labelDateAcct = new Label();
	private Label labelDateAcct2 = new Label();
	private Label labelStatus = new Label();

	private WDateEditor  fieldDateAcct = new WDateEditor();
	private WDateEditor  fieldDateAcct2 = new WDateEditor();

	Textbox tbStatus = new Textbox();
	Textbox tbResult = new Textbox();
	Textbox tbUrlWithCode = new Textbox();
	
	Div authDiv = new Div();
	Iframe authFrame = new Iframe();
	Script authScript = new Script();
	
	private CommonServerPushCallbackOpenUrl callback = null;
	
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
		tbStatus.setPlaceholder(Msg.translate(Env.getCtx(), "(O)pen | (F)ulfilled | empty"));
		btnSelect = new Button("1. Check query data ");
		btnSelect.addEventListener(Events.ON_CLICK, this);

		Hlayout hl0 = new Hlayout();
		hl0.setValign("middle");
		hl0.appendChild(labelDateAcct);
		hl0.appendChild(fieldDateAcct.getComponent());
		hl0.appendChild(labelDateAcct2);
		hl0.appendChild(fieldDateAcct2.getComponent());
		hl0.appendChild(labelStatus);
		hl0.appendChild(tbStatus);
		hl0.appendChild(btnSelect);

		Vlayout vl = new Vlayout();
		vl.appendChild(hl0);
		
		btnAuthorize = new Button("2. Request an OAuth 2.0 authorisation code with the required scope");
		btnAuthorize.addEventListener(Events.ON_CLICK, this);

		Hlayout hl = new Hlayout();
		hl.setValign("middle");
		hl.appendChild(btnAuthorize);
		vl.appendChild(hl);


		btnSend = new Button("3. Exchange the OAuth 2.0 authorisation code for an access token and get data");
		btnSend.addEventListener(Events.ON_CLICK, this);

		vl.appendChild(tbUrlWithCode);
		
//		authObj.setType("text/html");
//		authObj.setWidth("800px");
//		authObj.setHeight("600px");
//		authObj.setStyle("overflow:auto;border:5px ridge blue");
//		authObj.setData(HmrcUtil.getAuthorizationRequestUrl(scope));
//		authDiv.appendChild(authObj);
//		String authObjID = authObj.getId()
		
//		StringBuilder authStr = new StringBuilder("<div>")
//				.append("<iframe src=\"")
//				.append(HmrcUtil.getAuthorizationRequestUrl(scope))
//                .append("\" width=\"800px\" height=\"600px\" style=\"overflow:auto;border:5px ridge blue\" />")
//			    .append("</div>");
//		vl.appendChild(Executions.createComponentsDirectly(authStr.toString().replaceAll( "&", "&amp;" ), "xul", this, null));

//		authFrame.setWidth("800px");
//		authFrame.setHeight("600px");
//		authFrame.setStyle("overflow:auto;border:5px ridge blue");
//		authFrame.setContent(null);
//		//Cell cell = new Cell();
//		authDiv.appendChild(authFrame);

		//authDiv.appendChild(authScript);
		vl.appendChild(authDiv);
				
		vl.appendChild(btnSend);
		vl.appendChild(tbResult);

		center.appendChild(vl);

		tbResult.setRows(10);
		tbResult.setHflex("true");
		tbUrlWithCode.setPlaceholder("Paste URL starting with idempiere.alfredkochen.de and containing ?code=");
		tbUrlWithCode.setHflex("true");

		tbUrlWithCode.setEnabled(false);
		btnAuthorize.setEnabled(false);
		btnSend.setEnabled(false);

	}

	public void onEvent(Event event) throws Exception {

		
		if (event.getTarget() == btnSelect) {

			if(tbStatus.getValue().compareTo("O") != 0 && fieldDateAcct.getValue()==null)
				throw new WrongValueException(fieldDateAcct.getComponent(), "StartDate must be set !");
			if(tbStatus.getValue().compareTo("O") != 0 && fieldDateAcct2.getValue()==null)
				throw new WrongValueException(fieldDateAcct2.getComponent(), "EndDate must be set !");

			btnAuthorize.setEnabled(true);
			tbUrlWithCode.setText("");
			btnSend.setEnabled(false);

		}		
		else if (event.getTarget() == btnAuthorize) {
			String url = HmrcUtil.getAuthorizationRequestUrl(scope);

//			authFrame.setContent(null);
//			// authFrame.getSrc();
//			authFrame.setSrc(url);
//			authFrame.invalidate();

//			authScript.setAttribute("width", "800px");
//			authScript.setAttribute("height", "600px");
//			authScript.setAttribute("target", "_hmrc");
//			authScript.setSrc(HmrcUtil.getAuthorizationRequestUrl(scope));
//			authScript.invalidate();

//			StringBuilder openStr = new StringBuilder("window.open(\"")
//			.append(HmrcUtil.getAuthorizationRequestUrl(scope))
//            .append("\" \"mywindow\" width=\"800px\" height=\"600px\")");
//			authDiv.appendChild(new Script(openStr.toString())); //.replaceAll( "&", "&amp;" )));
//			authDiv.invalidate();

//			Executions.sendRedirect(HmrcUtil.getAuthorizationRequestUrl(scope)); //, "_new");
			
			// authFrame.getSrc();
			Desktop desktop = AEnv.getDesktop();
			ServerPushTemplate pushUpdateUi = new ServerPushTemplate (desktop);
			callback = new CommonServerPushCallbackOpenUrl();
			callback.setUrl(url);
			callback.setOpenInNewTab(true);
			pushUpdateUi.executeAsync (callback);

			tbUrlWithCode.setEnabled(true);
			btnSend.setEnabled(true);

		}
		else if (event.getTarget() == btnSend) {

			if (Util.isEmpty(tbUrlWithCode.getValue()))
				throw new WrongValueException(tbUrlWithCode, "You did not past the URL !");

			String url = tbUrlWithCode.getValue();

			int pos = url.indexOf("?code=");
			if (pos <= 0)
				throw new WrongValueException(tbUrlWithCode, "URL doesn't contain code !");

			String code = url.substring(pos + 6);
			System.out.println("url  = " + url);
			System.out.println("code = " + code);

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
			
			String msg = HmrcUtil.getObligations(code,
												 fromDate, 
												 toDate, 
												 tbStatus.getValue()
										   		);
			tbResult.setValue(msg.toString());

			if (msg.startsWith("Error")) {
				FDialog.error(getWindowNo(), "Error", msg);
				btnAuthorize.setEnabled(true);
				tbUrlWithCode.setText("");
			}
			else {
				FDialog.info(getWindowNo(), this, "", msg);
				btnAuthorize.setEnabled(false);
				tbUrlWithCode.setText("");
				btnSend.setEnabled(false);
			}


		}

	}
}
