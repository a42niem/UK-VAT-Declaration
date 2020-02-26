package org.tgi.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

public class MXXRTaxDeclarationLine extends X_XXR_TaxDeclarationLine {

	private static final long serialVersionUID = -6910985200776442932L;

	public MXXRTaxDeclarationLine(Properties ctx, int XXR_TaxDeclarationLine_ID, String trxName) {
		super(ctx, XXR_TaxDeclarationLine_ID, trxName);
	}	// MXXRTaxDeclarationLine

	public MXXRTaxDeclarationLine (Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}	//	MXXRTaxDeclarationLine

	protected boolean beforeSave (boolean newRecord) {

		if (is_ValueChanged(COLUMNNAME_IsManual)) {
			if (isManual())
				setXXR_AmountUpdatedByUser_ID(Env.getAD_User_ID(getCtx()));
			else {
				setXXR_AmountUpdatedByUser_ID(-1);
				setXXR_SentAmount(getAmount());
			}
		}

		return true;
	}

} // MXXRTaxDeclarationLine
