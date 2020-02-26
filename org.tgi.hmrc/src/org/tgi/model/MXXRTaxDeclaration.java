package org.tgi.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MXXRTaxDeclaration extends X_XXR_TaxDeclaration {

	private static final long serialVersionUID = -2783293328699570287L;

	public MXXRTaxDeclaration(Properties ctx, int XXR_TaxDeclaration_ID, String trxName) {
		super(ctx, XXR_TaxDeclaration_ID, trxName);
	}	// MXXRTaxDeclaration

	public MXXRTaxDeclaration (Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}	//	MXXRTaxDeclaration

	public MXXRTaxDeclarationLine[] getLines () {
		List<MXXRTaxDeclarationLine> list = new Query(getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ?", get_TrxName())
				.setParameters(getXXR_TaxDeclaration_ID())
				.setOrderBy(MXXRTaxDeclarationLine.COLUMNNAME_XXR_TaxDeclarationLine_ID)
				.list();
		return list.toArray(new MXXRTaxDeclarationLine[list.size()]);
	}	//	getLines

	protected boolean beforeSave (boolean newRecord) {

		if (!newRecord && isXXR_IsFinalised() && (is_ValueChanged(COLUMNNAME_DateFrom) || is_ValueChanged(COLUMNNAME_DateTo))) {
			log.saveError("Error", "You cannot change dates as the declaration is finalised");
			return false;
		}

		return true;
	}

} // MXXRTaxDeclaration
