package org.tgi.webui.util;

import org.adempiere.webui.util.IServerPushCallback;
import org.zkoss.zk.ui.Executions;

/**
 * Permet d'ouvrir une url depuis un process
 */
public class CommonServerPushCallbackOpenUrl implements IServerPushCallback {

	private String m_url = "";
	private boolean m_openInNewTab = false;

	public void setUrl(String url) {
		m_url = url;
	}

	public void setOpenInNewTab(boolean openInNewTab) {
		m_openInNewTab = openInNewTab;
	}

	@Override
	public void updateUI() {
		Executions.getCurrent().sendRedirect(m_url, m_openInNewTab ? "_blank" : "_self");
	}

}
