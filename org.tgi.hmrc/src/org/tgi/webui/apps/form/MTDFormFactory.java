/******************************************************************************
 * Copyright (C) 2013 Heng Sin Low                                            *
 * Copyright (C) 2013 Trek Global                 							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.tgi.webui.apps.form;

import java.util.logging.Level;

import org.adempiere.base.equinox.EquinoxExtensionLocator;
import org.adempiere.webui.factory.IFormFactory;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.util.ADClassNameMap;
import org.compiere.util.CLogger;
import org.zkoss.zk.ui.Component;

/**
 * @author hengsin
 *
 */
public class MTDFormFactory implements IFormFactory {

	private static final CLogger log = CLogger.getCLogger(MTDFormFactory.class); 
			
	/**
	 * default constructor
	 */
	public MTDFormFactory() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.webui.factory.IFormFactory#newFormInstance(java.lang.String)
	 */
	@Override
	public ADForm newFormInstance(String formName) {
		Object form = EquinoxExtensionLocator.instance().locate(Object.class, "org.tgi.webui.apps.form.WTestHMRC", formName, null).getExtension();
		if (form == null) {
			String webClassName = formName;
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> clazz = null; 
			if (loader != null) {
				try
				{
					clazz = loader.loadClass(webClassName);
				}
				catch (Exception e)
				{
					if (log.isLoggable(Level.INFO))
						log.log(Level.INFO, e.getLocalizedMessage(), e);
				}
			}
			if (clazz == null) {
				loader = this.getClass().getClassLoader();
				try
				{
					//	Create instance w/o parameters
					clazz = loader.loadClass(webClassName);
				}
				catch (Exception e)
				{
					if (log.isLoggable(Level.INFO))
						log.log(Level.INFO, e.getLocalizedMessage(), e);
				}
			}
			if (clazz != null) {
				try
				{
					form = clazz.getDeclaredConstructor().newInstance();
				}
				catch (Exception e)
				{
					if (log.isLoggable(Level.WARNING))
						log.log(Level.WARNING, e.getLocalizedMessage(), e);
				}
			}
		}

		if (form != null) {
			if (form instanceof ADForm) {
				return (ADForm)form;
			} else if (form instanceof IFormController) {
				IFormController controller = (IFormController) form;
				ADForm adForm = controller.getForm();
				adForm.setICustomForm(controller);
				return adForm;
			}
		}
		
		if (log.isLoggable(Level.INFO))
			log.info(formName + " not found at extension registry and classpath");
		return null;
	}

	/**
	 * Convert the rich client class name for a form to its web UI equivalent
	 *
	 * @param originalName	The full class path to convert
	 * @return the converted class name
	 */
	private static String translateFormClassName(String originalName)
	{
		String zkName = null;
		/*
		 * replacement string to translate class paths to the form
		 * "org.adempiere.webui.apps.form.<classname>"
		 */
		final String zkPackage = "org.adempiere.webui.";
		/*
		 * replacement string to translate custom form class name from
		 * "V<name>" to "W<name>"
		 */
		final String zkPrefix = "W";
		final String swingPrefix = "V";

        String tail = null;
        //first, try replace package
		if (originalName.startsWith("org.compiere."))
		{
			tail = originalName.substring("org.compiere.".length());
		}
		else if(originalName.startsWith("org.adempiere."))
		{
			tail = originalName.substring("org.adempiere.".length());
		}
		if (tail != null)
		{
			zkName = zkPackage + tail;

    		try {
				Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
				if (!isZkFormClass(clazz))
				{
					zkName = null;
				}
			} catch (ClassNotFoundException e) {
				zkName = null;
			}

			//try replace package and add W prefix to class name
			if (zkName == null)
			{
				String packageName = zkPackage;
				int lastdot = tail.lastIndexOf(".");
				String className = null;
				if (lastdot >= 0)
				{
					if (lastdot > 0)
						packageName = packageName + tail.substring(0, lastdot+1);
					className = tail.substring(lastdot+1);
				}
				else
				{
					className = tail;
				}

				//try convert V* to W*
				if (className.startsWith(swingPrefix))
				{
					zkName = packageName + zkPrefix + className.substring(1);
					try {
						Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
						if (!isZkFormClass(clazz))
						{
							zkName = null;
						}
					} catch (ClassNotFoundException e) {
						zkName = null;
					}
				}

				//try append W prefix to original class name
				if (zkName == null)
				{
					zkName = packageName + zkPrefix + className;
					try {
						Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
						if (!isZkFormClass(clazz))
						{
							zkName = null;
						}
					} catch (ClassNotFoundException e) {
						zkName = null;
					}
				}
			}
        }

		/*
		 *  not found, try changing only the class name
		 */
		if (zkName == null)
		{
			int lastdot = originalName.lastIndexOf(".");
			String packageName = originalName.substring(0, lastdot);
			String className = originalName.substring(lastdot+1);
			//try convert V* to W*
			if (className.startsWith(swingPrefix))
			{
				String zkClassName = zkPrefix + className.substring(1);
				zkName = packageName + "." + zkClassName;
				try {
					Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
					if (!isZkFormClass(clazz))
					{
						zkName = null;
					}
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}

			//try just append W to the original class name
			if (zkName == null)
			{
				String zkClassName = zkPrefix + className;
				zkName = packageName + "." + zkClassName;
				try {
					Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
					if (!isZkFormClass(clazz))
					{
						zkName = null;
					}
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}

			if (zkName == null)
			{
				//finally try whether same name is used for zk
				zkName = originalName;
				try {
					Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
					if (!isZkFormClass(clazz))
					{
						zkName = null;
					}
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}
		}

		return zkName;
	}

	private static boolean isZkFormClass(Class<?> clazz) {
		return IFormController.class.isAssignableFrom(clazz) || Component.class.isAssignableFrom(clazz);
	}


}
