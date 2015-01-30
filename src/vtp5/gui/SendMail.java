package vtp5.gui;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SendMail {
	
	static String u1;
	
	static String u2;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
	      JTextField xField = new JTextField(10);
	      JPasswordField yField = new JPasswordField(10);

	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("email:"));
	      myPanel.add(xField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("password:"));
	      myPanel.add(yField);

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Sign In", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  u1 = xField.getText();
	    	  u2 = yField.getText();
	         System.out.println("x value: " + xField.getText());
	         //System.out.println("y value: " + yField.getText());
	         if(u1.contains("@reading-school.co.uk")){
	         mail();
	         }else{
	        	 System.out.println("not reading school");
	         }
	         
	      }
	}
	
	public static void mail() {
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
					InternetAddress.parse("abdelabdalla@gmail.com"));
			message.setSubject("OMG It works");
			message.setText("Body of the email");
			Transport.send(message);
			System.out.println("Sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}