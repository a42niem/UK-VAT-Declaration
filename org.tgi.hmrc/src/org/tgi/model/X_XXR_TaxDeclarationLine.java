/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.tgi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for XXR_TaxDeclarationLine
 *  @author iDempiere (generated) 
 *  @version Release 6.2 - $Id$ */
public class X_XXR_TaxDeclarationLine extends PO implements I_XXR_TaxDeclarationLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200226L;

    /** Standard Constructor */
    public X_XXR_TaxDeclarationLine (Properties ctx, int XXR_TaxDeclarationLine_ID, String trxName)
    {
      super (ctx, XXR_TaxDeclarationLine_ID, trxName);
      /** if (XXR_TaxDeclarationLine_ID == 0)
        {
			setAmount (Env.ZERO);
// 0
			setIsManual (false);
// N
			setType (null);
			setXXR_TaxDeclaration_ID (0);
			setXXR_TaxDeclarationLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_XXR_TaxDeclarationLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_XXR_TaxDeclarationLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_ValueNoCheck (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** VAT due in the period on sales and other outputs = 1 */
	public static final String TYPE_VATDueInThePeriodOnSalesAndOtherOutputs = "1";
	/** VAT reclaimed in the period on purchases and other inputs = 4 */
	public static final String TYPE_VATReclaimedInThePeriodOnPurchasesAndOtherInputs = "4";
	/** VAT due in period on acquisitions from other member of EU = 2 */
	public static final String TYPE_VATDueInPeriodOnAcquisitionsFromOtherMemberOfEU = "2";
	/** Total VAT due = 3 */
	public static final String TYPE_TotalVATDue = "3";
	/** Net VAT to pay to HMRC or reclaim = 5 */
	public static final String TYPE_NetVATToPayToHMRCOrReclaim = "5";
	/** Total value of sales and all other outputs excluding any VAT = 6 */
	public static final String TYPE_TotalValueOfSalesAndAllOtherOutputsExcludingAnyVAT = "6";
	/** Total value of purchases and all other inputs excluding VAT = 7 */
	public static final String TYPE_TotalValueOfPurchasesAndAllOtherInputsExcludingVAT = "7";
	/** Total value supplies goods and related costs - No VAT - EU = 8 */
	public static final String TYPE_TotalValueSuppliesGoodsAndRelatedCosts_NoVAT_EU = "8";
	/** Total value acquisitions goods and related costs -No VAT- EU = 9 */
	public static final String TYPE_TotalValueAcquisitionsGoodsAndRelatedCosts_NoVAT_EU = "9";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	public org.compiere.model.I_AD_User getXXR_AmountUpdatedByUser() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getXXR_AmountUpdatedByUser_ID(), get_TrxName());	}

	/** Set Amount updated by.
		@param XXR_AmountUpdatedByUser_ID Amount updated by	  */
	public void setXXR_AmountUpdatedByUser_ID (int XXR_AmountUpdatedByUser_ID)
	{
		if (XXR_AmountUpdatedByUser_ID < 1) 
			set_Value (COLUMNNAME_XXR_AmountUpdatedByUser_ID, null);
		else 
			set_Value (COLUMNNAME_XXR_AmountUpdatedByUser_ID, Integer.valueOf(XXR_AmountUpdatedByUser_ID));
	}

	/** Get Amount updated by.
		@return Amount updated by	  */
	public int getXXR_AmountUpdatedByUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XXR_AmountUpdatedByUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sent Amount.
		@param XXR_SentAmount 
		Amount actually sent (can be updated by a user)
	  */
	public void setXXR_SentAmount (BigDecimal XXR_SentAmount)
	{
		set_Value (COLUMNNAME_XXR_SentAmount, XXR_SentAmount);
	}

	/** Get Sent Amount.
		@return Amount actually sent (can be updated by a user)
	  */
	public BigDecimal getXXR_SentAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_XXR_SentAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_XXR_TaxDeclaration getXXR_TaxDeclaration() throws RuntimeException
    {
		return (I_XXR_TaxDeclaration)MTable.get(getCtx(), I_XXR_TaxDeclaration.Table_Name)
			.getPO(getXXR_TaxDeclaration_ID(), get_TrxName());	}

	/** Set Tax Declaration.
		@param XXR_TaxDeclaration_ID Tax Declaration	  */
	public void setXXR_TaxDeclaration_ID (int XXR_TaxDeclaration_ID)
	{
		if (XXR_TaxDeclaration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_XXR_TaxDeclaration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_XXR_TaxDeclaration_ID, Integer.valueOf(XXR_TaxDeclaration_ID));
	}

	/** Get Tax Declaration.
		@return Tax Declaration	  */
	public int getXXR_TaxDeclaration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XXR_TaxDeclaration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax Declaration Line.
		@param XXR_TaxDeclarationLine_ID Tax Declaration Line	  */
	public void setXXR_TaxDeclarationLine_ID (int XXR_TaxDeclarationLine_ID)
	{
		if (XXR_TaxDeclarationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_XXR_TaxDeclarationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_XXR_TaxDeclarationLine_ID, Integer.valueOf(XXR_TaxDeclarationLine_ID));
	}

	/** Get Tax Declaration Line.
		@return Tax Declaration Line	  */
	public int getXXR_TaxDeclarationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XXR_TaxDeclarationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set XXR_TaxDeclarationLine_UU.
		@param XXR_TaxDeclarationLine_UU XXR_TaxDeclarationLine_UU	  */
	public void setXXR_TaxDeclarationLine_UU (String XXR_TaxDeclarationLine_UU)
	{
		set_Value (COLUMNNAME_XXR_TaxDeclarationLine_UU, XXR_TaxDeclarationLine_UU);
	}

	/** Get XXR_TaxDeclarationLine_UU.
		@return XXR_TaxDeclarationLine_UU	  */
	public String getXXR_TaxDeclarationLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_XXR_TaxDeclarationLine_UU);
	}
}