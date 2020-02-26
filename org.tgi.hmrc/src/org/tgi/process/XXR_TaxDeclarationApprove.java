package org.tgi.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.factory.ButtonFactory;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.util.ServerPushTemplate;
import org.adempiere.webui.window.FDialog;
import org.tgi.model.MXXRTaxDeclaration;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Msg;
import org.tgi.webui.util.CommonServerPushCallbackOpenPanel;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Vlayout;

/**
 *	Approve Vat Declaration
 * 	@author nmicoud
 */
public class XXR_TaxDeclarationApprove extends SvrProcess
{
	String p_type = "";

	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (name.equals("Type"))
				p_type = (String) para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	protected String doIt()
	{
		if (new MXXRTaxDeclaration(getCtx(), getRecord_ID(), get_TrxName()).getLines().length == 0)
			return "@Error@ @NoLines@";

		return "@ProcessOK@";
	}	//	doIt

	protected void postProcess(boolean success) {

		if (success) {
			WVatDeclarationApproval win = new WVatDeclarationApproval();
			Desktop desktop = AEnv.getDesktop();
			ServerPushTemplate pushUpdateUi = new ServerPushTemplate (desktop);
			CommonServerPushCallbackOpenPanel callback = new CommonServerPushCallbackOpenPanel();
			callback.setParam(win);
			pushUpdateUi.executeAsync (callback);
		}
	}

	public class WVatDeclarationApproval extends Window implements EventListener<Event>{

		private static final long serialVersionUID = -1830025652251375108L;
		private Button bOk = new Button();
		private Button bCancel = new Button();
		Html html;

		public WVatDeclarationApproval() {
			super();
			setTitle(Msg.getMsg(getCtx(), "XXR_VatDeclarationApprovalTitle"));
			html = new Html(Msg.getMsg(getCtx(), p_type.equals("1") ? "XXR_VatDeclarationApprovalMsgCustomer" : "XXR_VatDeclarationApprovalMsgAgent"));
			bOk = ButtonFactory.createButton(Msg.getMsg(getCtx(), "XXR_VatDeclarationApprovalOkLabel"), ThemeManager.getThemeResource("images/Ok16.png"), "");
			bOk.addEventListener(Events.ON_CLICK, this);
			bCancel = ButtonFactory.createButton(Msg.getMsg(getCtx(), "XXR_VatDeclarationApprovalCancelLabel"), ThemeManager.getThemeResource("images/Cancel16.png"), "");
			bCancel.addEventListener(Events.ON_CLICK, this);

			Borderlayout mainLayout = new Borderlayout();
			this.appendChild(mainLayout);
			Center center = new Center();
			mainLayout.appendChild(center);

			Vlayout vl = new Vlayout();
			vl.appendChild(html);
			Hbox hb = new Hbox();
			hb.appendChild(bOk);
			hb.appendChild(bCancel);
			vl.appendChild(hb);
			center.appendChild(vl);
			setSclass("popup-dialog");
			setClosable(false);
			setMaximizable(false);
			setSizable(true);
			setBorder(true);
			setAttribute(Window.MODE_KEY, Window.MODE_HIGHLIGHTED);
			setStyle("position: absolute; width: 600px; height: 150px;");
		}

		public void onEvent(Event event) throws Exception {
			if (event.getTarget() == bOk) {
				MXXRTaxDeclaration mtd = new MXXRTaxDeclaration(getCtx(), getRecord_ID(), null);
				mtd.setXXR_ApprovalByUser_ID(getAD_User_ID());
				mtd.setXXR_ApprovalOn(new Timestamp(System.currentTimeMillis()));
				mtd.setXXR_IsFinalised(true);
				mtd.saveEx();
				FDialog.info(0, this, "XXR_VatDeclarationApprovalProcessOk");
				onClose();
			}
			else if (event.getTarget() == bCancel) {
				onClose();
			}
		}
	}

}	//	XXR_TaxDeclarationApprove