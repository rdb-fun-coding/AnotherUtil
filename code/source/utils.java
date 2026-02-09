

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.ns.NSName;
import com.wm.passman.PasswordManagerException;
import com.wm.util.security.WmSecureString;
import java.util.logging.Level;
import com.wm.app.b2b.server.OutboundPasswordManager;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void getSecrets (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSecrets)>> ---
		// @sigtype java 3.5
		// [i] field:0:required pkgName
		// [i] field:0:required alias
		// [i] field:0:required key
		// [o] field:0:required secret
		//get secret using password manager
		String pkgName = ValuesEmulator.getString(pipeline, "pkgName");
		String alias = ValuesEmulator.getString(pipeline, "alias");
		String key = ValuesEmulator.getString(pipeline, "key");
		String inHandle = "com.webmethods.cloudstream." + alias + "." + key;
		System.out.println("inHandle: " + inHandle);
		
		String serviceToLog = "wm.cloudstreams.service.util.runtime:log";
		NSName serviceNsName = NSName.create(serviceToLog);
		IData logPipeline = IDataFactory.create();
		ValuesEmulator.put(logPipeline, "connectorID", "DUMMY_CONN_ID");
		ValuesEmulator.put(logPipeline, "severityLevel", Level.INFO);
		try {
			WmSecureString retrievePassword = OutboundPasswordManager.getPasswordManager(pkgName).retrievePassword(inHandle);
			if(retrievePassword == null) {
				System.out.println("Got no secret with OutboundPasswordManager.getPasswordManager(pkgName).retrievePassword(inHandle) and pacakge as : " + pkgName);
				ValuesEmulator.put(logPipeline, "message", "Could not retrieve any password for handle : " + inHandle);
				ValuesEmulator.put(pipeline, "secret", "NO_SECRET_RETRIEVED");
			}
			else if(retrievePassword != null){
				String secret = retrievePassword.toString();
				System.out.println("Got this secret with OutboundPasswordManager.getPasswordManager(pkgName).retrievePassword(inHandle) : " + secret);
				ValuesEmulator.put(logPipeline, "message", "Got this secret: " + secret + " with handle: " + inHandle);
				ValuesEmulator.put(pipeline, "secret", secret);
			}
			Service.doInvoke(serviceNsName, logPipeline); 
		}
		catch (PasswordManagerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void getSecrets2 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSecrets2)>> ---
		// @sigtype java 3.5
		// [i] field:0:required pkgName
		// [i] field:0:required alias
		// [i] field:0:required key
		// [o] field:0:required secret
		String pkgName = ValuesEmulator.getString(pipeline, "pkgName");
		String alias = ValuesEmulator.getString(pipeline, "alias");
		String key = ValuesEmulator.getString(pipeline, "key");
		String inHandle = "com.webmethods.cloudstream." + alias + "." + key;
		System.out.println("inHandle: " + inHandle);
		String serviceToLog = "wm.cloudstreams.service.util.runtime:log";
		NSName serviceNsName = NSName.create(serviceToLog);
		IData logPipeline = IDataFactory.create();
		ValuesEmulator.put(logPipeline, "connectorID", "DUMMY_CONN_ID");
		ValuesEmulator.put(logPipeline, "severityLevel", Level.INFO);
		try {
			WmSecureString retrievePassword = OutboundPasswordManager.retrievePassword(pkgName, inHandle);
			if(retrievePassword == null) {
				System.out.println("Got no result with pacakge as : " + pkgName);
				ValuesEmulator.put(logPipeline, "message", "Could not retrieve any password for handle : " + inHandle);
				retrievePassword = OutboundPasswordManager.retrievePassword(null, inHandle);
			}
			else if(retrievePassword != null){
				String secret = retrievePassword.toString();
				System.out.println("Got this secret: " + secret);
				ValuesEmulator.put(logPipeline, "message", "Got this secret: " + secret + " with handle: " + inHandle);
				ValuesEmulator.put(pipeline, "secret", secret);
			}
			Service.doInvoke(serviceNsName, logPipeline); 
		}
		catch (PasswordManagerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// --- <<IS-END>> ---

                
	}
}

