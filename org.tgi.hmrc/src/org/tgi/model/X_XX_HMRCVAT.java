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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for XX_HMRCVAT
 *  @author iDempiere (generated) 
 *  @version Release 6.2 - $Id$ */
public class X_XX_HMRCVAT extends PO implements I_XX_HMRCVAT, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200106L;

    /** Standard Constructor */
    public X_XX_HMRCVAT (Properties ctx, int XX_HMRCVAT_ID, String trxName)
    {
      super (ctx, XX_HMRCVAT_ID, trxName);
      /** if (XX_HMRCVAT_ID == 0)
        {
			setHMRCchargeRefNumber (null);
			setHMRCformBundleNumber (null);
			setHMRCpaymentIndicator (null);
			setHMRCprocessingDate (null);
			setMultiplyRate (Env.ZERO);
			setnetVatDue (Env.ZERO);
			setperiodKey (null);
			settotalAcquisitionsExVAT (Env.ZERO);
			settotalValueGoodsSuppliedExVAT (Env.ZERO);
			settotalValuePurchasesExVAT (Env.ZERO);
			settotalValueSalesExVAT (Env.ZERO);
			settotalVatDue (Env.ZERO);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setvatDueAcquisitions (Env.ZERO);
			setvatDueSales (Env.ZERO);
			setvatReclaimedCurrPeriod (Env.ZERO);
			setXX_HMRCVAT_ID (0);
        } */
    }

    /** Load Constructor */
    public X_XX_HMRCVAT (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_XX_HMRCVAT[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set HMRCchargeRefNumber.
		@param HMRCchargeRefNumber 
		HMRCchargeRefNumber
	  */
	public void setHMRCchargeRefNumber (String HMRCchargeRefNumber)
	{
		set_Value (COLUMNNAME_HMRCchargeRefNumber, HMRCchargeRefNumber);
	}

	/** Get HMRCchargeRefNumber.
		@return HMRCchargeRefNumber
	  */
	public String getHMRCchargeRefNumber () 
	{
		return (String)get_Value(COLUMNNAME_HMRCchargeRefNumber);
	}

	/** Set HMRCformBundleNumber.
		@param HMRCformBundleNumber 
		HMRCformBundleNumber
	  */
	public void setHMRCformBundleNumber (String HMRCformBundleNumber)
	{
		set_Value (COLUMNNAME_HMRCformBundleNumber, HMRCformBundleNumber);
	}

	/** Get HMRCformBundleNumber.
		@return HMRCformBundleNumber
	  */
	public String getHMRCformBundleNumber () 
	{
		return (String)get_Value(COLUMNNAME_HMRCformBundleNumber);
	}

	/** Set HMRCpaymentIndicator.
		@param HMRCpaymentIndicator 
		HMRCpaymentIndicator
	  */
	public void setHMRCpaymentIndicator (String HMRCpaymentIndicator)
	{
		set_Value (COLUMNNAME_HMRCpaymentIndicator, HMRCpaymentIndicator);
	}

	/** Get HMRCpaymentIndicator.
		@return HMRCpaymentIndicator
	  */
	public String getHMRCpaymentIndicator () 
	{
		return (String)get_Value(COLUMNNAME_HMRCpaymentIndicator);
	}

	/** Set HMRCprocessingDate.
		@param HMRCprocessingDate 
		HMRCprocessingDate
	  */
	public void setHMRCprocessingDate (String HMRCprocessingDate)
	{
		set_Value (COLUMNNAME_HMRCprocessingDate, HMRCprocessingDate);
	}

	/** Get HMRCprocessingDate.
		@return HMRCprocessingDate
	  */
	public String getHMRCprocessingDate () 
	{
		return (String)get_Value(COLUMNNAME_HMRCprocessingDate);
	}

	/** Set Multiply Rate.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Multiply Rate.
		@return Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set netVatDue.
		@param netVatDue 
		netVatDue
	  */
	public void setnetVatDue (BigDecimal netVatDue)
	{
		set_Value (COLUMNNAME_netVatDue, netVatDue);
	}

	/** Get netVatDue.
		@return netVatDue
	  */
	public BigDecimal getnetVatDue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_netVatDue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set periodKey.
		@param periodKey 
		periodKey
	  */
	public void setperiodKey (String periodKey)
	{
		set_Value (COLUMNNAME_periodKey, periodKey);
	}

	/** Get periodKey.
		@return periodKey
	  */
	public String getperiodKey () 
	{
		return (String)get_Value(COLUMNNAME_periodKey);
	}

	/** Set totalAcquisitionsExVAT.
		@param totalAcquisitionsExVAT 
		totalAcquisitionsExVAT
	  */
	public void settotalAcquisitionsExVAT (BigDecimal totalAcquisitionsExVAT)
	{
		set_Value (COLUMNNAME_totalAcquisitionsExVAT, totalAcquisitionsExVAT);
	}

	/** Get totalAcquisitionsExVAT.
		@return totalAcquisitionsExVAT
	  */
	public BigDecimal gettotalAcquisitionsExVAT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalAcquisitionsExVAT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalValueGoodsSuppliedExVAT.
		@param totalValueGoodsSuppliedExVAT 
		totalValueGoodsSuppliedExVAT
	  */
	public void settotalValueGoodsSuppliedExVAT (BigDecimal totalValueGoodsSuppliedExVAT)
	{
		set_Value (COLUMNNAME_totalValueGoodsSuppliedExVAT, totalValueGoodsSuppliedExVAT);
	}

	/** Get totalValueGoodsSuppliedExVAT.
		@return totalValueGoodsSuppliedExVAT
	  */
	public BigDecimal gettotalValueGoodsSuppliedExVAT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalValueGoodsSuppliedExVAT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalValuePurchasesExVAT.
		@param totalValuePurchasesExVAT 
		totalValuePurchasesExVAT
	  */
	public void settotalValuePurchasesExVAT (BigDecimal totalValuePurchasesExVAT)
	{
		set_Value (COLUMNNAME_totalValuePurchasesExVAT, totalValuePurchasesExVAT);
	}

	/** Get totalValuePurchasesExVAT.
		@return totalValuePurchasesExVAT
	  */
	public BigDecimal gettotalValuePurchasesExVAT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalValuePurchasesExVAT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalValueSalesExVAT.
		@param totalValueSalesExVAT 
		totalValueSalesExVAT
	  */
	public void settotalValueSalesExVAT (BigDecimal totalValueSalesExVAT)
	{
		set_Value (COLUMNNAME_totalValueSalesExVAT, totalValueSalesExVAT);
	}

	/** Get totalValueSalesExVAT.
		@return totalValueSalesExVAT
	  */
	public BigDecimal gettotalValueSalesExVAT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalValueSalesExVAT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalVatDue.
		@param totalVatDue 
		totalVatDue
	  */
	public void settotalVatDue (BigDecimal totalVatDue)
	{
		set_Value (COLUMNNAME_totalVatDue, totalVatDue);
	}

	/** Get totalVatDue.
		@return totalVatDue
	  */
	public BigDecimal gettotalVatDue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalVatDue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_ValueNoCheck (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_ValueNoCheck (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set vatDueAcquisitions.
		@param vatDueAcquisitions 
		vatDueAcquisitions
	  */
	public void setvatDueAcquisitions (BigDecimal vatDueAcquisitions)
	{
		set_Value (COLUMNNAME_vatDueAcquisitions, vatDueAcquisitions);
	}

	/** Get vatDueAcquisitions.
		@return vatDueAcquisitions
	  */
	public BigDecimal getvatDueAcquisitions () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_vatDueAcquisitions);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set vatDueSales.
		@param vatDueSales 
		vatDueSales
	  */
	public void setvatDueSales (BigDecimal vatDueSales)
	{
		set_Value (COLUMNNAME_vatDueSales, vatDueSales);
	}

	/** Get vatDueSales.
		@return vatDueSales
	  */
	public BigDecimal getvatDueSales () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_vatDueSales);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set vatReclaimedCurrPeriod.
		@param vatReclaimedCurrPeriod 
		vatReclaimedCurrPeriod
	  */
	public void setvatReclaimedCurrPeriod (BigDecimal vatReclaimedCurrPeriod)
	{
		set_Value (COLUMNNAME_vatReclaimedCurrPeriod, vatReclaimedCurrPeriod);
	}

	/** Get vatReclaimedCurrPeriod.
		@return vatReclaimedCurrPeriod
	  */
	public BigDecimal getvatReclaimedCurrPeriod () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_vatReclaimedCurrPeriod);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HMRC MTD VAT.
		@param XX_HMRCVAT_ID HMRC MTD VAT	  */
	public void setXX_HMRCVAT_ID (int XX_HMRCVAT_ID)
	{
		if (XX_HMRCVAT_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_XX_HMRCVAT_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_XX_HMRCVAT_ID, Integer.valueOf(XX_HMRCVAT_ID));
	}

	/** Get HMRC MTD VAT.
		@return HMRC MTD VAT	  */
	public int getXX_HMRCVAT_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XX_HMRCVAT_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getXX_HMRCVAT_ID()));
    }

	/** Set XX_HMRCVAT_UU.
		@param XX_HMRCVAT_UU XX_HMRCVAT_UU	  */
	public void setXX_HMRCVAT_UU (String XX_HMRCVAT_UU)
	{
		set_Value (COLUMNNAME_XX_HMRCVAT_UU, XX_HMRCVAT_UU);
	}

	/** Get XX_HMRCVAT_UU.
		@return XX_HMRCVAT_UU	  */
	public String getXX_HMRCVAT_UU () 
	{
		return (String)get_Value(COLUMNNAME_XX_HMRCVAT_UU);
	}
}