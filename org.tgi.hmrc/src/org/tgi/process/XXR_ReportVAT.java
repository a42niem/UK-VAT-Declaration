package org.tgi.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.tgi.util.HmrcUtil;

/**
 * VAT Report (summary and/or detail)
 * @author nmicoud
 */

public class XXR_ReportVAT extends SvrProcess {

	Timestamp p_dateAcctFrom = null;
	Timestamp p_dateAcctTo = null;
	boolean p_isDetail = false; // à supprimer
	int p_currencyID = 0; // GBP ou USD -> uniquement pour le Summary
	

	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();

			if (name.equals("DateAcct")) {
				p_dateAcctFrom = para[i].getParameterAsTimestamp();
				p_dateAcctTo = para[i].getParameter_ToAsTimestamp();
			}
			else if (name.equals("ListDetails"))
				p_isDetail = para[i].getParameterAsBoolean();
			else if (name.equals("C_Currency_ID"))
				p_currencyID = para[i].getParameterAsInt();
		}
	}

	protected String doIt() {

		BigDecimal box1 = HmrcUtil.getBox1(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());
		BigDecimal box2 = HmrcUtil.getBox2(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());
		BigDecimal box3 = HmrcUtil.getBox3(getCtx(), box1, box2, p_dateAcctTo, getAD_PInstance_ID(), p_currencyID, processUI, get_TrxName());
		BigDecimal box4 = HmrcUtil.getBox4(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());
		HmrcUtil.getBox5(getCtx(), box3, box4, p_dateAcctTo, getAD_PInstance_ID(), p_currencyID, processUI, get_TrxName());
		HmrcUtil.getBox6(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());
		HmrcUtil.getBox7(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());
		HmrcUtil.getBox8(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());
		HmrcUtil.getBox9(getCtx(), p_dateAcctFrom, p_dateAcctTo, getAD_PInstance_ID(), p_isDetail, p_currencyID, processUI, get_TrxName());

		return "@ProcessOK@";
	}
}
