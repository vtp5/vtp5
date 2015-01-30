package vtp5.gui;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import vtp5.logic.TestFile;

/*VTP5 Copyright (C) 2015  Abdel-Rahim Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

public class SendMail {
	
	static String u1,u2,u3;

	@SuppressWarnings("deprecation")
	public static void m() throws IOException {
	      JTextField uField = new JTextField(10);
	      JPasswordField pField = new JPasswordField(10);
	      JTextField tField = new JTextField(10);

	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("Email:"));
	      myPanel.add(uField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("Password:"));
	      myPanel.add(pField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("Send To:"));
	      myPanel.add(tField);

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Sign In", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  u1 = uField.getText();
	    	  u2 = pField.getText();
	    	  u3 = tField.getText();
	         System.out.println("u value: " + uField.getText());
	         //System.out.println("y value: " + yField.getText());
	         if(u1.contains("@reading-school.co.uk")){
	         mail();
	         }else{
	        	 System.out.println("not reading school");
	         }
	         
	      }
	}
	
	public static void mail() throws IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.office365.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(u1, u2);
					}
				});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(u1));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(u3));
			message.setSubject("VTP5 Screenshot");
			//message.setText("Body of the email");
			 // Create the message part 
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Fill the message
	         
	         messageBodyPart.setText("Score: "+TestFile.score);
	         
	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = FinishPanel.screenloc;
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);
			Transport.send(message);
			System.out.println("Sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}