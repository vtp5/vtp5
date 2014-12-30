package vtp_launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Initial extends Launcher {

	static String fil = null;
	
	public static void start() {
		parse();
		File f = new File(System.getProperty("java.class.path"));
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();

		String p[] = path.split(";");

		//tp.setText(p[0]);

		String vtpdir = p[0]+"\\VTP5_files";
		String vtpdir2 = p[0]+"\\VTP5_files\\vtp5";
		//String vtpdir = "E:\\VTP5_files";
		//String vtpdir2 = "E:\\VTP5_files\\vtp5";
		File theDir = new File(vtpdir);
		File theDir2 = new File(vtpdir2);

		fil = vtpdir2 + "\\" + vtpversion + ".jar";
		
		if (!theDir.exists()) {
			System.out.println("creating directory: " + "VTP5");
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				System.out.println("Security");
			}
			if (result) {
				System.out.println(vtpdir);

			}
			if (!theDir2.exists()) {
				System.out.println("creating directory: " + "VTP5");
				boolean result2 = false;

				try {
					theDir2.mkdir();
					result2 = true;
				} catch (SecurityException se) {
					System.out.println("Security2");
				}
				if (result2) {
					System.out.println(vtpdir2);

				}

			}
		} else if (!theDir2.exists()) {
			System.out.println("creating directory: " + "VTP5");
			boolean result3 = false;

			try {
				theDir2.mkdir();
				result3 = true;
			} catch (SecurityException se) {
				System.out.println("Security3");
			}
			if (result3) {
				System.out.println(vtpdir2);
			}
		} else {
			System.out.println("Already there");

			File fi = new File(fil);
			if (fi.exists()) {
				tp.append("Latest build of VTP5 present\n");
			} else {
				tp.append("fetching newest version...\n");
				
				try {
					FileUtils.cleanDirectory(theDir2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
				File file = new File(fil);
				URL url = null;
				try {
					url = new URL(/*"http://192.168.1.141/vtp5/"+vtpversion+".jar"*/ "http://sabr.ddns.net/vtp5"+vtpversion+".jar");
					FileUtils.copyURLToFile(url,file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tp.append("Latest build of VTP5 present\n");
			}

		}

	}

	public static void parse() {

		try {
			Document test = Jsoup.connect("http://en.wikipedia.org/").get();

			Document docabdel = Jsoup.connect("http://192.168.1.141").get(); // this
																				// is
																				// needed
																				// for
																				// abdel
																				// to
																				// work
																				// on
																				// this
			Document doc = Jsoup.connect("http://sabr.ddns.net").get(); // other
																		// people

			vtpversion = doc.title();// change this too
			System.out.println(vtpversion);

		} catch (IOException e) {
			System.out.println("Can't connect to internet");
		}

	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to "
						+ dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}
}
