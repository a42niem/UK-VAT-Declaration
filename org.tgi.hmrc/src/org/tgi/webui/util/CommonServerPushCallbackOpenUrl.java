package org.tgi.webui.util;

import org.adempiere.webui.util.IServerPushCallback;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

/**
 * Permet d'ouvrir une url depuis un process
 */
public class CommonServerPushCallbackOpenUrl implements IServerPushCallback {

	private String m_url = "";
	private boolean m_openInNewTab = false;
	private Execution m_currEx = null;
	
	public void setUrl(String url) {
		m_url = url;
	}

	public void setOpenInNewTab(boolean openInNewTab) {
		m_openInNewTab = openInNewTab;
	}

	@Override
	public void updateUI() {
		m_currEx = Executions.getCurrent();
		m_currEx.sendRedirect(m_url, m_openInNewTab ? "hmrcpopup" : "_self");
	}
	
}
