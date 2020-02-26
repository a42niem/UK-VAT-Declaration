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

/** Generated Interface for XXR_TaxDeclarationLine
 *  @author iDempiere (generated) 
 *  @version Release 6.2
 */
@SuppressWarnings("all")
public interface I_XXR_TaxDeclarationLine 
{

    /** TableName=XXR_TaxDeclarationLine */
    public static final String Table_Name = "XXR_TaxDeclarationLine";

    /** AD_Table_ID=1000266 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

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

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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

    /** Column name XXR_AmountUpdatedByUser_ID */
    public static final String COLUMNNAME_XXR_AmountUpdatedByUser_ID = "XXR_AmountUpdatedByUser_ID";

	/** Set Amount updated by	  */
	public void setXXR_AmountUpdatedByUser_ID (int XXR_AmountUpdatedByUser_ID);

	/** Get Amount updated by	  */
	public int getXXR_AmountUpdatedByUser_ID();

	public org.compiere.model.I_AD_User getXXR_AmountUpdatedByUser() throws RuntimeException;

    /** Column name XXR_SentAmount */
    public static final String COLUMNNAME_XXR_SentAmount = "XXR_SentAmount";

	/** Set Sent Amount.
	  * Amount actually sent (can be updated by a user)
	  */
	public void setXXR_SentAmount (BigDecimal XXR_SentAmount);

	/** Get Sent Amount.
	  * Amount actually sent (can be updated by a user)
	  */
	public BigDecimal getXXR_SentAmount();

    /** Column name XXR_TaxDeclaration_ID */
    public static final String COLUMNNAME_XXR_TaxDeclaration_ID = "XXR_TaxDeclaration_ID";

	/** Set Tax Declaration	  */
	public void setXXR_TaxDeclaration_ID (int XXR_TaxDeclaration_ID);

	/** Get Tax Declaration	  */
	public int getXXR_TaxDeclaration_ID();

	public I_XXR_TaxDeclaration getXXR_TaxDeclaration() throws RuntimeException;

    /** Column name XXR_TaxDeclarationLine_ID */
    public static final String COLUMNNAME_XXR_TaxDeclarationLine_ID = "XXR_TaxDeclarationLine_ID";

	/** Set Tax Declaration Line	  */
	public void setXXR_TaxDeclarationLine_ID (int XXR_TaxDeclarationLine_ID);

	/** Get Tax Declaration Line	  */
	public int getXXR_TaxDeclarationLine_ID();

    /** Column name XXR_TaxDeclarationLine_UU */
    public static final String COLUMNNAME_XXR_TaxDeclarationLine_UU = "XXR_TaxDeclarationLine_UU";

	/** Set XXR_TaxDeclarationLine_UU	  */
	public void setXXR_TaxDeclarationLine_UU (String XXR_TaxDeclarationLine_UU);

	/** Get XXR_TaxDeclarationLine_UU	  */
	public String getXXR_TaxDeclarationLine_UU();
}
