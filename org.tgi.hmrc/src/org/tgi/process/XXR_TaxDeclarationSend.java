package org.tgi.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.tgi.model.MXXRTaxDeclaration;
import org.tgi.model.MXXRTaxDeclarationLine;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.tgi.util.HmrcUtil;

/**
 *	Create Tax Declaration Line
 * 	@author nmicoud
 */
public class XXR_TaxDeclarationSend extends SvrProcess
{
	MXXRTaxDeclaration mtd;

	protected void prepare() {}

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt()
	{
		mtd = new MXXRTaxDeclaration(getCtx(), getRecord_ID(), get_TrxName());
		if (mtd.isXXR_IsFinalised())
			return "@Error@ is finalised";

		for (MXXRTaxDeclarationLine mtdl : mtd.getLines())
			mtdl.deleteEx(false);

		int currencyID = 114;
		Timestamp from = mtd.getDateFrom();
		Timestamp to = mtd.getDateTo();

		BigDecimal box1 = HmrcUtil.getBox1(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());
		BigDecimal box2 = HmrcUtil.getBox2(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());
		BigDecimal box3 = HmrcUtil.getBox3(getCtx(), box1, box2, to, -1, currencyID, processUI, get_TrxName());
		BigDecimal box4 = HmrcUtil.getBox4(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());
		BigDecimal box5 = HmrcUtil.getBox5(getCtx(), box3, box4, to, -1, currencyID, processUI, get_TrxName());
		BigDecimal box6 = HmrcUtil.getBox6(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());
		BigDecimal box7 = HmrcUtil.getBox7(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());
		BigDecimal box8 = HmrcUtil.getBox8(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());
		BigDecimal box9 = HmrcUtil.getBox9(getCtx(), from, to, -1, false, currencyID, processUI, get_TrxName());

		MXXRTaxDeclarationLine line1 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_VATDueInThePeriodOnSalesAndOtherOutputs)
				.first();
		MXXRTaxDeclarationLine line2 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_VATDueInPeriodOnAcquisitionsFromOtherMemberOfEU)
				.first();
		MXXRTaxDeclarationLine line3 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_TotalVATDue)
				.first();
		MXXRTaxDeclarationLine line4 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_VATReclaimedInThePeriodOnPurchasesAndOtherInputs)
				.first();
		MXXRTaxDeclarationLine line5 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_NetVATToPayToHMRCOrReclaim)
				.first();
		MXXRTaxDeclarationLine line6 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_TotalValueOfSalesAndAllOtherOutputsExcludingAnyVAT)
				.first();
		MXXRTaxDeclarationLine line7 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_TotalValueOfPurchasesAndAllOtherInputsExcludingVAT)
				.first();
		MXXRTaxDeclarationLine line8 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_TotalValueSuppliesGoodsAndRelatedCosts_NoVAT_EU)
				.first();
		MXXRTaxDeclarationLine line9 = new Query (getCtx(), MXXRTaxDeclarationLine.Table_Name, "XXR_TaxDeclaration_ID = ? AND type=?", get_TrxName())
				.setParameters(mtd.getXXR_TaxDeclaration_ID(), 
						MXXRTaxDeclarationLine.TYPE_TotalValueAcquisitionsGoodsAndRelatedCosts_NoVAT_EU)
				.first();

		//sendData(code, mtd.getXXR_PeriodKey(), line1.getAmount())
		return "@ProcessOK@";
	}	//	doIt

	void createLine(String type, BigDecimal amt) {
		MXXRTaxDeclarationLine mtdl = new MXXRTaxDeclarationLine(getCtx(), 0, get_TrxName());
		mtdl.setXXR_TaxDeclaration_ID(mtd.getXXR_TaxDeclaration_ID());
		mtdl.setAD_Org_ID(mtd.getAD_Org_ID());
		mtdl.setType(type);
		mtdl.setAmount(amt);
		mtdl.setXXR_SentAmount(amt);
		mtdl.saveEx();
	}

}	//	XXR_TaxDeclarationCreateLine