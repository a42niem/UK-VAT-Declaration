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
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for XXR_TaxDeclaration
 *  @author iDempiere (generated) 
 *  @version Release 6.2 - $Id$ */
public class X_XXR_TaxDeclaration extends PO implements I_XXR_TaxDeclaration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200310L;

    /** Standard Constructor */
    public X_XXR_TaxDeclaration (Properties ctx, int XXR_TaxDeclaration_ID, String trxName)
    {
      super (ctx, XXR_TaxDeclaration_ID, trxName);
      /** if (XXR_TaxDeclaration_ID == 0)
        {
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setProcessed (false);
			setXXR_IsFinalised (false);
// N
			setXXR_TaxDeclaration_ID (0);
        } */
    }

    /** Load Constructor */
    public X_XXR_TaxDeclaration (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_XXR_TaxDeclaration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_AD_User getXXR_ApprovalByUser() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getXXR_ApprovalByUser_ID(), get_TrxName());	}

	/** Set Approved by.
		@param XXR_ApprovalByUser_ID Approved by	  */
	public void setXXR_ApprovalByUser_ID (int XXR_ApprovalByUser_ID)
	{
		if (XXR_ApprovalByUser_ID < 1) 
			set_Value (COLUMNNAME_XXR_ApprovalByUser_ID, null);
		else 
			set_Value (COLUMNNAME_XXR_ApprovalByUser_ID, Integer.valueOf(XXR_ApprovalByUser_ID));
	}

	/** Get Approved by.
		@return Approved by	  */
	public int getXXR_ApprovalByUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XXR_ApprovalByUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Approved on.
		@param XXR_ApprovalOn Approved on	  */
	public void setXXR_ApprovalOn (Timestamp XXR_ApprovalOn)
	{
		set_Value (COLUMNNAME_XXR_ApprovalOn, XXR_ApprovalOn);
	}

	/** Get Approved on.
		@return Approved on	  */
	public Timestamp getXXR_ApprovalOn () 
	{
		return (Timestamp)get_Value(COLUMNNAME_XXR_ApprovalOn);
	}

	public org.compiere.model.I_AD_User getXXR_DataSentByUser() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getXXR_DataSentByUser_ID(), get_TrxName());	}

	/** Set Data send by.
		@param XXR_DataSentByUser_ID Data send by	  */
	public void setXXR_DataSentByUser_ID (int XXR_DataSentByUser_ID)
	{
		if (XXR_DataSentByUser_ID < 1) 
			set_Value (COLUMNNAME_XXR_DataSentByUser_ID, null);
		else 
			set_Value (COLUMNNAME_XXR_DataSentByUser_ID, Integer.valueOf(XXR_DataSentByUser_ID));
	}

	/** Get Data send by.
		@return Data send by	  */
	public int getXXR_DataSentByUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XXR_DataSentByUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Data sent on.
		@param XXR_DataSentOn Data sent on	  */
	public void setXXR_DataSentOn (Timestamp XXR_DataSentOn)
	{
		set_Value (COLUMNNAME_XXR_DataSentOn, XXR_DataSentOn);
	}

	/** Get Data sent on.
		@return Data sent on	  */
	public Timestamp getXXR_DataSentOn () 
	{
		return (Timestamp)get_Value(COLUMNNAME_XXR_DataSentOn);
	}

	/** Set Finalised.
		@param XXR_IsFinalised 
		Declaration that the user has finalised their VAT return
	  */
	public void setXXR_IsFinalised (boolean XXR_IsFinalised)
	{
		set_Value (COLUMNNAME_XXR_IsFinalised, Boolean.valueOf(XXR_IsFinalised));
	}

	/** Get Finalised.
		@return Declaration that the user has finalised their VAT return
	  */
	public boolean isXXR_IsFinalised () 
	{
		Object oo = get_Value(COLUMNNAME_XXR_IsFinalised);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Period Key.
		@param XXR_PeriodKey 
		The ID code for the period that this obligation belongs to
	  */
	public void setXXR_PeriodKey (String XXR_PeriodKey)
	{
		set_Value (COLUMNNAME_XXR_PeriodKey, XXR_PeriodKey);
	}

	/** Get Period Key.
		@return The ID code for the period that this obligation belongs to
	  */
	public String getXXR_PeriodKey () 
	{
		return (String)get_Value(COLUMNNAME_XXR_PeriodKey);
	}

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

	/** Set XXR_TaxDeclaration_UU.
		@param XXR_TaxDeclaration_UU XXR_TaxDeclaration_UU	  */
	public void setXXR_TaxDeclaration_UU (String XXR_TaxDeclaration_UU)
	{
		set_Value (COLUMNNAME_XXR_TaxDeclaration_UU, XXR_TaxDeclaration_UU);
	}

	/** Get XXR_TaxDeclaration_UU.
		@return XXR_TaxDeclaration_UU	  */
	public String getXXR_TaxDeclaration_UU () 
	{
		return (String)get_Value(COLUMNNAME_XXR_TaxDeclaration_UU);
	}

	/** Set VAT Return Response.
		@param XXR_VatReturnResponse VAT Return Response	  */
	public void setXXR_VatReturnResponse (String XXR_VatReturnResponse)
	{
		set_Value (COLUMNNAME_XXR_VatReturnResponse, XXR_VatReturnResponse);
	}

	/** Get VAT Return Response.
		@return VAT Return Response	  */
	public String getXXR_VatReturnResponse () 
	{
		return (String)get_Value(COLUMNNAME_XXR_VatReturnResponse);
	}
}