package org.tgi.webui.util;

import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.IServerPushCallback;

public class CommonServerPushCallbackOpenPanel implements IServerPushCallback {

	Window m_window;

	public void setParam(Window window) {
		m_window = window;
	}

	public void updateUI() {
		SessionManager.getAppDesktop().showWindow(m_window, "center");
	}
}
