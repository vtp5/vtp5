package vtp5.gui;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

import org.korecky.sharepoint.SPList;
import org.korecky.sharepoint.SPListCollection;
import org.korecky.sharepoint.SPSite;
import org.korecky.sharepoint.SPVersion;
import org.korecky.sharepoint.SPWeb;
import org.korecky.sharepoint.authentication.NtlmAuthenticator;
import org.korecky.sharepoint.net.HttpProxy;

public class Authenticator {


	public static void main(String[] args){
		String u = "";
		String p = "";
		
		// Create NTLM v2 credentials (authenticator)
		NtlmAuthenticator credentials = new NtlmAuthenticator( u, p);
		// Initialize proxy settings
		//HttpProxy httpProxy = new HttpProxy("myProxyServer.com", 80);
		
	// Connect to Sharepoint
	try {
		SPSite instance = new SPSite(new URL("http://rgmedia.reading-school.co.uk"), credentials, true, SPVersion.SP2010);
		System.out.println("pt1");
		SPWeb rootWeb = instance.getRootWeb();
		System.out.println("pt2");
		try {
			SPListCollection lists = rootWeb.getLists();
			System.out.println("pt3");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (KeyManagementException | NoSuchAlgorithmException
			| MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
