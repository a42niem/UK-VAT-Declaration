package org.tgi.process;

import java.util.logging.Level;

import org.adempiere.base.IProcessFactory;
import org.tgi.process.XXR_TaxDeclarationApprove;
import org.tgi.process.XXR_TaxDeclarationPrepare;
import org.compiere.process.ProcessCall;
import org.compiere.util.CLogger;

public class MTDProcessFactory implements IProcessFactory {

	private final static CLogger log = CLogger.getCLogger(MTDProcessFactory.class);

	public MTDProcessFactory() {
		log.log(Level.WARNING, "MTDProcessFactory ");
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessCall newProcessInstance(String className) {
		log.log(Level.WARNING, "MTDProcessFactory.newProcessInstance ");
		ProcessCall process = null;
		boolean here = false; 
		if (className.equals(XXR_ReportVAT.class.getName())) {
			className = "org.tgi.process.XXR_ReportVAT";
			here = true;
		}
		if (className.equals(XXR_TaxDeclarationPrepare.class.getName())) {
			className = "org.tgi.process.XXR_TaxDeclarationPrepare";
			here = true;
		}
		if (className.equals(XXR_TaxDeclarationApprove.class.getName())) {
			className = "org.tgi.process.XXR_TaxDeclarationApprove";
			here = true;
		}
		if (className.equals(XXR_TaxDeclarationSend.class.getName())) {
			className = "org.tgi.process.XXR_TaxDeclarationSend";
			here = true;
		}
		if (here) {
			//Get Class
			Class<?> processClass = null;
			//use context classloader if available
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader != null)
			{
				try
				{
					processClass = classLoader.loadClass(className);
				}
				catch (ClassNotFoundException ex)
				{
					if (log.isLoggable(Level.FINE))log.log(Level.FINE, className, ex);
				}
			}
			if (processClass == null)
			{
				classLoader = this.getClass().getClassLoader();
				try
				{
					processClass = classLoader.loadClass(className);
				}
				catch (ClassNotFoundException ex)
				{
					log.log(Level.WARNING, className, ex);
					return null;
				}
			}

			if (processClass == null) {
				return null;
			}

			//Get Process
			try
			{
				process = (ProcessCall)processClass.getConstructor().newInstance();
			}
			catch (Exception ex)
			{
				log.log(Level.WARNING, "Instance for " + className, ex);
				return null;
			}
		}
		return process;
	}

}
