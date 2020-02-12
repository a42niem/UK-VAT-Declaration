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
package org.tgi.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for XX_HMRCVAT
 *  @author iDempiere (generated) 
 *  @version Release 6.2
 */
@SuppressWarnings("all")
public interface I_XX_HMRCVAT 
{

    /** TableName=XX_HMRCVAT */
    public static final String Table_Name = "XX_HMRCVAT";

    /** AD_Table_ID=1000264 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name HMRCchargeRefNumber */
    public static final String COLUMNNAME_HMRCchargeRefNumber = "HMRCchargeRefNumber";

	/** Set HMRCchargeRefNumber.
	  * HMRCchargeRefNumber
	  */
	public void setHMRCchargeRefNumber (String HMRCchargeRefNumber);

	/** Get HMRCchargeRefNumber.
	  * HMRCchargeRefNumber
	  */
	public String getHMRCchargeRefNumber();

    /** Column name HMRCformBundleNumber */
    public static final String COLUMNNAME_HMRCformBundleNumber = "HMRCformBundleNumber";

	/** Set HMRCformBundleNumber.
	  * HMRCformBundleNumber
	  */
	public void setHMRCformBundleNumber (String HMRCformBundleNumber);

	/** Get HMRCformBundleNumber.
	  * HMRCformBundleNumber
	  */
	public String getHMRCformBundleNumber();

    /** Column name HMRCpaymentIndicator */
    public static final String COLUMNNAME_HMRCpaymentIndicator = "HMRCpaymentIndicator";

	/** Set HMRCpaymentIndicator.
	  * HMRCpaymentIndicator
	  */
	public void setHMRCpaymentIndicator (String HMRCpaymentIndicator);

	/** Get HMRCpaymentIndicator.
	  * HMRCpaymentIndicator
	  */
	public String getHMRCpaymentIndicator();

    /** Column name HMRCprocessingDate */
    public static final String COLUMNNAME_HMRCprocessingDate = "HMRCprocessingDate";

	/** Set HMRCprocessingDate.
	  * HMRCprocessingDate
	  */
	public void setHMRCprocessingDate (String HMRCprocessingDate);

	/** Get HMRCprocessingDate.
	  * HMRCprocessingDate
	  */
	public String getHMRCprocessingDate();

    /** Column name MultiplyRate */
    public static final String COLUMNNAME_MultiplyRate = "MultiplyRate";

	/** Set Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate);

	/** Get Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate();

    /** Column name netVatDue */
    public static final String COLUMNNAME_netVatDue = "netVatDue";

	/** Set netVatDue.
	  * netVatDue
	  */
	public void setnetVatDue (BigDecimal netVatDue);

	/** Get netVatDue.
	  * netVatDue
	  */
	public BigDecimal getnetVatDue();

    /** Column name periodKey */
    public static final String COLUMNNAME_periodKey = "periodKey";

	/** Set periodKey.
	  * periodKey
	  */
	public void setperiodKey (String periodKey);

	/** Get periodKey.
	  * periodKey
	  */
	public String getperiodKey();

    /** Column name totalAcquisitionsExVAT */
    public static final String COLUMNNAME_totalAcquisitionsExVAT = "totalAcquisitionsExVAT";

	/** Set totalAcquisitionsExVAT.
	  * totalAcquisitionsExVAT
	  */
	public void settotalAcquisitionsExVAT (BigDecimal totalAcquisitionsExVAT);

	/** Get totalAcquisitionsExVAT.
	  * totalAcquisitionsExVAT
	  */
	public BigDecimal gettotalAcquisitionsExVAT();

    /** Column name totalValueGoodsSuppliedExVAT */
    public static final String COLUMNNAME_totalValueGoodsSuppliedExVAT = "totalValueGoodsSuppliedExVAT";

	/** Set totalValueGoodsSuppliedExVAT.
	  * totalValueGoodsSuppliedExVAT
	  */
	public void settotalValueGoodsSuppliedExVAT (BigDecimal totalValueGoodsSuppliedExVAT);

	/** Get totalValueGoodsSuppliedExVAT.
	  * totalValueGoodsSuppliedExVAT
	  */
	public BigDecimal gettotalValueGoodsSuppliedExVAT();

    /** Column name totalValuePurchasesExVAT */
    public static final String COLUMNNAME_totalValuePurchasesExVAT = "totalValuePurchasesExVAT";

	/** Set totalValuePurchasesExVAT.
	  * totalValuePurchasesExVAT
	  */
	public void settotalValuePurchasesExVAT (BigDecimal totalValuePurchasesExVAT);

	/** Get totalValuePurchasesExVAT.
	  * totalValuePurchasesExVAT
	  */
	public BigDecimal gettotalValuePurchasesExVAT();

    /** Column name totalValueSalesExVAT */
    public static final String COLUMNNAME_totalValueSalesExVAT = "totalValueSalesExVAT";

	/** Set totalValueSalesExVAT.
	  * totalValueSalesExVAT
	  */
	public void settotalValueSalesExVAT (BigDecimal totalValueSalesExVAT);

	/** Get totalValueSalesExVAT.
	  * totalValueSalesExVAT
	  */
	public BigDecimal gettotalValueSalesExVAT();

    /** Column name totalVatDue */
    public static final String COLUMNNAME_totalVatDue = "totalVatDue";

	/** Set totalVatDue.
	  * totalVatDue
	  */
	public void settotalVatDue (BigDecimal totalVatDue);

	/** Get totalVatDue.
	  * totalVatDue
	  */
	public BigDecimal gettotalVatDue();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/** Set Valid to.
	  * Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo);

	/** Get Valid to.
	  * Valid to including this date (last day)
	  */
	public Timestamp getValidTo();

    /** Column name vatDueAcquisitions */
    public static final String COLUMNNAME_vatDueAcquisitions = "vatDueAcquisitions";

	/** Set vatDueAcquisitions.
	  * vatDueAcquisitions
	  */
	public void setvatDueAcquisitions (BigDecimal vatDueAcquisitions);

	/** Get vatDueAcquisitions.
	  * vatDueAcquisitions
	  */
	public BigDecimal getvatDueAcquisitions();

    /** Column name vatDueSales */
    public static final String COLUMNNAME_vatDueSales = "vatDueSales";

	/** Set vatDueSales.
	  * vatDueSales
	  */
	public void setvatDueSales (BigDecimal vatDueSales);

	/** Get vatDueSales.
	  * vatDueSales
	  */
	public BigDecimal getvatDueSales();

    /** Column name vatReclaimedCurrPeriod */
    public static final String COLUMNNAME_vatReclaimedCurrPeriod = "vatReclaimedCurrPeriod";

	/** Set vatReclaimedCurrPeriod.
	  * vatReclaimedCurrPeriod
	  */
	public void setvatReclaimedCurrPeriod (BigDecimal vatReclaimedCurrPeriod);

	/** Get vatReclaimedCurrPeriod.
	  * vatReclaimedCurrPeriod
	  */
	public BigDecimal getvatReclaimedCurrPeriod();

    /** Column name XX_HMRCVAT_ID */
    public static final String COLUMNNAME_XX_HMRCVAT_ID = "XX_HMRCVAT_ID";

	/** Set HMRC MTD VAT	  */
	public void setXX_HMRCVAT_ID (int XX_HMRCVAT_ID);

	/** Get HMRC MTD VAT	  */
	public int getXX_HMRCVAT_ID();

    /** Column name XX_HMRCVAT_UU */
    public static final String COLUMNNAME_XX_HMRCVAT_UU = "XX_HMRCVAT_UU";

	/** Set XX_HMRCVAT_UU	  */
	public void setXX_HMRCVAT_UU (String XX_HMRCVAT_UU);

	/** Get XX_HMRCVAT_UU	  */
	public String getXX_HMRCVAT_UU();
}
