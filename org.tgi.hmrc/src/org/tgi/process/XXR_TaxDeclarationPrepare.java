package org.tgi.process;

import org.compiere.model.MXXRTaxDeclaration;
import org.compiere.process.SvrProcess;

/**
 *	Create Tax Declaration Line
 * 	@author nmicoud
 */
public class XXR_TaxDeclarationPrepare extends SvrProcess
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
		if (mtd.isProcessed())
			return "@Error@ has already been processed";
		if (mtd.isXXR_IsFinalised())
			return "@Error@ is finalised";

		MXXRTaxDeclaration.deleteOldAndInsertNewLines(getCtx(), mtd, processUI, get_TrxName());

		return "@ProcessOK@";
	}	//	doIt

}	//	XXR_TaxDeclarationCreateLine