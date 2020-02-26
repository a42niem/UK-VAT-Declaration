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

/** Generated Interface for XXR_TaxDeclaration
 *  @author iDempiere (generated) 
 *  @version Release 6.2
 */
@SuppressWarnings("all")
public interface I_XXR_TaxDeclaration 
{

    /** TableName=XXR_TaxDeclaration */
    public static final String Table_Name = "XXR_TaxDeclaration";

    /** AD_Table_ID=1000267 */
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

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Timestamp getDateTo();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

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

    /** Column name XXR_ApprovalByUser_ID */
    public static final String COLUMNNAME_XXR_ApprovalByUser_ID = "XXR_ApprovalByUser_ID";

	/** Set Approved by	  */
	public void setXXR_ApprovalByUser_ID (int XXR_ApprovalByUser_ID);

	/** Get Approved by	  */
	public int getXXR_ApprovalByUser_ID();

	public org.compiere.model.I_AD_User getXXR_ApprovalByUser() throws RuntimeException;

    /** Column name XXR_ApprovalOn */
    public static final String COLUMNNAME_XXR_ApprovalOn = "XXR_ApprovalOn";

	/** Set Approved on	  */
	public void setXXR_ApprovalOn (Timestamp XXR_ApprovalOn);

	/** Get Approved on	  */
	public Timestamp getXXR_ApprovalOn();

    /** Column name XXR_DataSentByUser_ID */
    public static final String COLUMNNAME_XXR_DataSentByUser_ID = "XXR_DataSentByUser_ID";

	/** Set Data send by	  */
	public void setXXR_DataSentByUser_ID (int XXR_DataSentByUser_ID);

	/** Get Data send by	  */
	public int getXXR_DataSentByUser_ID();

	public org.compiere.model.I_AD_User getXXR_DataSentByUser() throws RuntimeException;

    /** Column name XXR_DataSentOn */
    public static final String COLUMNNAME_XXR_DataSentOn = "XXR_DataSentOn";

	/** Set Data sent on	  */
	public void setXXR_DataSentOn (Timestamp XXR_DataSentOn);

	/** Get Data sent on	  */
	public Timestamp getXXR_DataSentOn();

    /** Column name XXR_IsFinalised */
    public static final String COLUMNNAME_XXR_IsFinalised = "XXR_IsFinalised";

	/** Set Finalised.
	  * Declaration that the user has finalised their VAT return
	  */
	public void setXXR_IsFinalised (boolean XXR_IsFinalised);

	/** Get Finalised.
	  * Declaration that the user has finalised their VAT return
	  */
	public boolean isXXR_IsFinalised();

    /** Column name XXR_PeriodKey */
    public static final String COLUMNNAME_XXR_PeriodKey = "XXR_PeriodKey";

	/** Set Period Key.
	  * The ID code for the period that this obligation belongs to
	  */
	public void setXXR_PeriodKey (String XXR_PeriodKey);

	/** Get Period Key.
	  * The ID code for the period that this obligation belongs to
	  */
	public String getXXR_PeriodKey();

    /** Column name XXR_TaxDeclaration_ID */
    public static final String COLUMNNAME_XXR_TaxDeclaration_ID = "XXR_TaxDeclaration_ID";

	/** Set Tax Declaration	  */
	public void setXXR_TaxDeclaration_ID (int XXR_TaxDeclaration_ID);

	/** Get Tax Declaration	  */
	public int getXXR_TaxDeclaration_ID();

    /** Column name XXR_TaxDeclaration_UU */
    public static final String COLUMNNAME_XXR_TaxDeclaration_UU = "XXR_TaxDeclaration_UU";

	/** Set XXR_TaxDeclaration_UU	  */
	public void setXXR_TaxDeclaration_UU (String XXR_TaxDeclaration_UU);

	/** Get XXR_TaxDeclaration_UU	  */
	public String getXXR_TaxDeclaration_UU();

    /** Column name XXR_VatReturnResponse */
    public static final String COLUMNNAME_XXR_VatReturnResponse = "XXR_VatReturnResponse";

	/** Set VAT Return Response	  */
	public void setXXR_VatReturnResponse (String XXR_VatReturnResponse);

	/** Get VAT Return Response	  */
	public String getXXR_VatReturnResponse();
}
