package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.IProcessUI;
import org.compiere.util.Util;
import org.tgi.util.HmrcUtil;

public class MXXRTaxDeclaration extends X_XXR_TaxDeclaration {

	private static final long serialVersionUID = -2783293328699570287L;
	private final static int currencyID = 114;

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
	
	protected boolean afterSave (boolean newRecord, boolean success) {
		if (!success || newRecord)
			return success;

		if (is_ValueChanged(COLUMNNAME_XXR_PeriodKey) && !Util.isEmpty(getXXR_PeriodKey()))
			deleteOldAndInsertNewLines(getCtx(), this, null, get_TrxName());

		return true;
	}
	
	public static void deleteOldAndInsertNewLines(Properties ctx, MXXRTaxDeclaration mtd, IProcessUI processUI, String trxName) {
		for (MXXRTaxDeclarationLine mtdl : mtd.getLines())
			mtdl.deleteEx(false);

		Timestamp from = mtd.getDateFrom();
		Timestamp to = mtd.getDateTo();

		BigDecimal box1 = HmrcUtil.getBox1(ctx, from, to, -1, false, currencyID, processUI, trxName);
		BigDecimal box2 = HmrcUtil.getBox2(ctx, from, to, -1, false, currencyID, processUI, trxName);
		BigDecimal box3 = HmrcUtil.getBox3(ctx, box1, box2, to, -1, currencyID, processUI, trxName);
		BigDecimal box4 = HmrcUtil.getBox4(ctx, from, to, -1, false, currencyID, processUI, trxName);
		BigDecimal box5 = HmrcUtil.getBox5(ctx, box3, box4, to, -1, currencyID, processUI, trxName);
		BigDecimal box6 = HmrcUtil.getBox6(ctx, from, to, -1, false, currencyID, processUI, trxName);
		BigDecimal box7 = HmrcUtil.getBox7(ctx, from, to, -1, false, currencyID, processUI, trxName);
		BigDecimal box8 = HmrcUtil.getBox8(ctx, from, to, -1, false, currencyID, processUI, trxName);
		BigDecimal box9 = HmrcUtil.getBox9(ctx, from, to, -1, false, currencyID, processUI, trxName);

		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_VATDueInThePeriodOnSalesAndOtherOutputs, box1, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_VATDueInPeriodOnAcquisitionsFromOtherMemberOfEU, box2, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_TotalVATDue, box3, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_VATReclaimedInThePeriodOnPurchasesAndOtherInputs, box4, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_NetVATToPayToHMRCOrReclaim, box5, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_TotalValueOfSalesAndAllOtherOutputsExcludingAnyVAT, box6, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_TotalValueOfPurchasesAndAllOtherInputsExcludingVAT, box7, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_TotalValueSuppliesGoodsAndRelatedCosts_NoVAT_EU, box8, trxName);
		createLine(ctx, mtd, MXXRTaxDeclarationLine.TYPE_TotalValueAcquisitionsGoodsAndRelatedCosts_NoVAT_EU, box9, trxName);		
	}
	
	private static void createLine(Properties ctx, MXXRTaxDeclaration mtd, String type, BigDecimal amt, String trxName) {
		MXXRTaxDeclarationLine mtdl = new MXXRTaxDeclarationLine(ctx, 0, trxName);
		mtdl.setXXR_TaxDeclaration_ID(mtd.getXXR_TaxDeclaration_ID());
		mtdl.setAD_Org_ID(mtd.getAD_Org_ID());
		mtdl.setType(type);
		mtdl.setAmount(amt);
		mtdl.setXXR_SentAmount(amt);
		mtdl.saveEx();
	}

} // MXXRTaxDeclaration
