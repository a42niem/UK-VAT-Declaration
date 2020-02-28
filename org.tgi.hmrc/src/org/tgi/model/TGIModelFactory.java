package org.tgi.model;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.IModelFactory;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public class TGIModelFactory implements IModelFactory {

	private final static CLogger s_log = CLogger.getCLogger(TGIModelFactory.class);

	public TGIModelFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equals(I_T_XXR_Report.Table_Name))
			return MTXXRReport.class;		
		if (tableName.equals(I_XXR_TaxDeclaration.Table_Name))
			return MXXRTaxDeclaration.class;		
		if (tableName.equals(I_XXR_TaxDeclarationLine.Table_Name))
			return MXXRTaxDeclarationLine.class;		
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		boolean errorLogged = false;
		Class<?> clazz = null;
		try {
			Constructor<?> constructor = null;
			if (tableName.equals(I_T_XXR_Report.Table_Name))
				clazz =  MTXXRReport.class;		
			if (tableName.equals(I_XXR_TaxDeclaration.Table_Name))
				clazz =  MXXRTaxDeclaration.class;		
			if (tableName.equals(I_XXR_TaxDeclarationLine.Table_Name))
				clazz =  MXXRTaxDeclarationLine.class;		
			if (clazz == null)
			{
				return null;
			}
			constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
			PO po = constructor!=null ? (PO)constructor.newInstance(new Object[] {Env.getCtx(), Integer.valueOf(Record_ID), trxName}) : null;
			return po;
		}
		catch (Exception e)
		{
			if (e.getCause() != null)
			{
				Throwable t = e.getCause();
				s_log.log(Level.SEVERE, "(id) - Table=" + tableName + ",Class=" + clazz, t);
				errorLogged = true;
				if (t instanceof Exception)
					s_log.saveError("Error", (Exception)e.getCause());
				else
					s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
			}
			else
			{
				s_log.log(Level.SEVERE, "(id) - Table=" + tableName + ",Class=" + clazz, e);
				errorLogged = true;
				s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
			}
		}
		if (!errorLogged)
			s_log.log(Level.SEVERE, "(id) - Not found - Table=" + tableName
				+ ", Record_ID=" + Record_ID);

		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		Class<?> clazz = getClass(tableName);
		boolean errorLogged = false;
		try
		{
			Constructor<?> constructor = null;
			if (tableName.equals(I_T_XXR_Report.Table_Name))
				clazz =  MTXXRReport.class;		
			if (tableName.equals(I_XXR_TaxDeclaration.Table_Name))
				clazz =  MXXRTaxDeclaration.class;		
			if (tableName.equals(I_XXR_TaxDeclarationLine.Table_Name))
				clazz =  MXXRTaxDeclarationLine.class;		
			if (clazz == null)
			{
				return null;
			}
			constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, ResultSet.class, String.class});
			PO po = (PO)constructor.newInstance(new Object[] {Env.getCtx(), rs, trxName});
			return po;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "(rs) - Table=" + tableName + ",Class=" + clazz, e);
			errorLogged = true;
			s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
		}
		if (!errorLogged)
			s_log.log(Level.SEVERE, "(rs) - Not found - Table=" + tableName);
		return null;
	}

}
