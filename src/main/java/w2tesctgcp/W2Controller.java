package w2tesctgcp;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class W2Controller {
	@Autowired
	private CustomerDetailsRepository customerDetailsRepository;
	

	
	@CrossOrigin
	@RequestMapping(value="/getW2Report", method = RequestMethod.GET)
    public String getW2Report(@RequestParam("From") String from, @RequestParam("Body") String message) throws MessagingException, IOException  
    {	
		List<CustomerDetails> cd =PostCD();
		System.out.println(message);
		System.out.println(from);
		Map<String,String> Status = new HashMap<String,String>();
		CustomerDetails w2Customer = customerDetailsRepository.findByregMob(from);
		if( message.equals("W2")==false){
			
	   return "Invalid Request";
			
		}
		
		if(w2Customer==null && message.equals("W2") ) {
			
	   return "Mobile Number is not Registered";
		}
		if(w2Customer.getRegStatus()==0)
		{
			return "Hi " + w2Customer.getCustomerName() + ", You are not registered for this sevice.";
		}
		else if(w2Customer!=null && message.equals("W2")) {
			sendMail(w2Customer);
			
	    return "Hi " + w2Customer.getCustomerName() + "," + "\n" + "Your W2 Report will be sent to your Registered Email ID : " +  w2Customer.getRegEmail() + " .";
		}
		else {
			
	    return "Inavlid Request!! ";
		}
		
    }
    
    @CrossOrigin
	@RequestMapping(value="/PostCD", method = RequestMethod.GET)
	public List<CustomerDetails> PostCD (){
    	CustomerDetails w2Customer= new CustomerDetails();
    	w2Customer.setCustomerId(1);
    	w2Customer.setCustomerName("Satheesh Unnikrishnan");
    	w2Customer.setRegEmail("Satheesh.Unnikrishnan@equifax.com");
    	w2Customer.setRegMob("+17329939672");
    	w2Customer.setRegStatus(1);
		customerDetailsRepository.save(w2Customer);
		
		CustomerDetails w2Customer1= new CustomerDetails();
    	w2Customer1.setCustomerId(2);
    	w2Customer1.setCustomerName("Amith Thomas");
    	w2Customer1.setRegEmail("amithomas95@gmail.com");
    	w2Customer1.setRegMob("+12057750342");
    	w2Customer1.setRegStatus(1);
    	customerDetailsRepository.save(w2Customer1);
    	
    	CustomerDetails w2Customer2= new CustomerDetails();
    	w2Customer2.setCustomerId(3);
    	w2Customer2.setCustomerName("Mathews");
    	w2Customer2.setRegEmail("Mathews.Abraham@equifax.com");
    	w2Customer2.setRegMob("+919048620256");
    	w2Customer2.setRegStatus(1);
    	customerDetailsRepository.save(w2Customer2);
    	
    	CustomerDetails w2Customer3= new CustomerDetails();
    	w2Customer3.setCustomerId(4);
    	w2Customer3.setCustomerName("Rajan");
    	w2Customer3.setRegEmail("Rajan.Kundoor@equifax.com");
    	w2Customer3.setRegMob("+919048611468");
    	w2Customer3.setRegStatus(0);
    	customerDetailsRepository.save(w2Customer3);
    	
    	
    	return customerDetailsRepository.findAll();
	}
    
    @CrossOrigin
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public CustomerDetails insertDB (@RequestBody CustomerDetails cd){
    	return customerDetailsRepository.save(cd);
    }
    
    @CrossOrigin
	@RequestMapping(value="/sendMail", method = RequestMethod.GET)
    public void sendMail(CustomerDetails w2Customer) throws MessagingException, IOException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("w2@sbclouddeploy.appspotmail.com", "Important:W2 Service Mail"));
      msg.addRecipient(Message.RecipientType.TO,
                       new InternetAddress(w2Customer.getRegEmail(), w2Customer.getCustomerName()));
      msg.setSubject("W2 Tax Form 2019");
      msg.setText("Hi " + w2Customer.getCustomerName() + "," + "\n" + "PFA your W2 Tax Form for the year 2019" + "\n" + "Thanks" + "\n" + "W2 Admins");
      String htmlBody = "<html>\r\n" + 
      		"  <head>\r\n" + 
      		"    <meta name=\"viewport\" content=\"width=device-width\" />\r\n" + 
      		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n" + 
      		"    <title>Simple Transactional Email</title>\r\n" + 
      		"    <style>\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          GLOBAL RESETS\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      \r\n" + 
      		"      /*All the styling goes here*/\r\n" + 
      		"      \r\n" + 
      		"      img {\r\n" + 
      		"        border: none;\r\n" + 
      		"        -ms-interpolation-mode: bicubic;\r\n" + 
      		"        max-width: 100%; \r\n" + 
      		"      }\r\n" + 
      		"      body {\r\n" + 
      		"        background-color: #f6f6f6;\r\n" + 
      		"        font-family: sans-serif;\r\n" + 
      		"        -webkit-font-smoothing: antialiased;\r\n" + 
      		"        font-size: 14px;\r\n" + 
      		"        line-height: 1.4;\r\n" + 
      		"        margin: 0;\r\n" + 
      		"        padding: 0;\r\n" + 
      		"        -ms-text-size-adjust: 100%;\r\n" + 
      		"        -webkit-text-size-adjust: 100%; \r\n" + 
      		"      }\r\n" + 
      		"      table {\r\n" + 
      		"        border-collapse: separate;\r\n" + 
      		"        mso-table-lspace: 0pt;\r\n" + 
      		"        mso-table-rspace: 0pt;\r\n" + 
      		"        width: 100%; }\r\n" + 
      		"        table td {\r\n" + 
      		"          font-family: sans-serif;\r\n" + 
      		"          font-size: 14px;\r\n" + 
      		"          vertical-align: top; \r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          BODY & CONTAINER\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      .body {\r\n" + 
      		"        background-color: #f6f6f6;\r\n" + 
      		"        width: 100%; \r\n" + 
      		"      }\r\n" + 
      		"      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\r\n" + 
      		"      .container {\r\n" + 
      		"        display: block;\r\n" + 
      		"        margin: 0 auto !important;\r\n" + 
      		"        /* makes it centered */\r\n" + 
      		"        max-width: 580px;\r\n" + 
      		"        padding: 10px;\r\n" + 
      		"        width: 580px; \r\n" + 
      		"      }\r\n" + 
      		"      /* This should also be a block element, so that it will fill 100% of the .container */\r\n" + 
      		"      .content {\r\n" + 
      		"        box-sizing: border-box;\r\n" + 
      		"        display: block;\r\n" + 
      		"        margin: 0 auto;\r\n" + 
      		"        max-width: 580px;\r\n" + 
      		"        padding: 10px; \r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          HEADER, FOOTER, MAIN\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      .main {\r\n" + 
      		"        background: #ffffff;\r\n" + 
      		"        border-radius: 3px;\r\n" + 
      		"        width: 100%; \r\n" + 
      		"      }\r\n" + 
      		"      .wrapper {\r\n" + 
      		"        box-sizing: border-box;\r\n" + 
      		"        padding: 20px; \r\n" + 
      		"      }\r\n" + 
      		"      .content-block {\r\n" + 
      		"        padding-bottom: 10px;\r\n" + 
      		"        padding-top: 10px;\r\n" + 
      		"      }\r\n" + 
      		"      .footer {\r\n" + 
      		"        clear: both;\r\n" + 
      		"        margin-top: 10px;\r\n" + 
      		"        text-align: center;\r\n" + 
      		"        width: 100%; \r\n" + 
      		"      }\r\n" + 
      		"        .footer td,\r\n" + 
      		"        .footer p,\r\n" + 
      		"        .footer span,\r\n" + 
      		"        .footer a {\r\n" + 
      		"          color: #999999;\r\n" + 
      		"          font-size: 12px;\r\n" + 
      		"          text-align: center; \r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          TYPOGRAPHY\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      h1,\r\n" + 
      		"      h2,\r\n" + 
      		"      h3,\r\n" + 
      		"      h4 {\r\n" + 
      		"        color: #000000;\r\n" + 
      		"        font-family: sans-serif;\r\n" + 
      		"        font-weight: 400;\r\n" + 
      		"        line-height: 1.4;\r\n" + 
      		"        margin: 0;\r\n" + 
      		"        margin-bottom: 30px; \r\n" + 
      		"      }\r\n" + 
      		"      h1 {\r\n" + 
      		"        font-size: 35px;\r\n" + 
      		"        font-weight: 300;\r\n" + 
      		"        text-align: center;\r\n" + 
      		"        text-transform: capitalize; \r\n" + 
      		"      }\r\n" + 
      		"      p,\r\n" + 
      		"      ul,\r\n" + 
      		"      ol {\r\n" + 
      		"        font-family: sans-serif;\r\n" + 
      		"        font-size: 14px;\r\n" + 
      		"        font-weight: normal;\r\n" + 
      		"        margin: 0;\r\n" + 
      		"        margin-bottom: 15px; \r\n" + 
      		"      }\r\n" + 
      		"        p li,\r\n" + 
      		"        ul li,\r\n" + 
      		"        ol li {\r\n" + 
      		"          list-style-position: inside;\r\n" + 
      		"          margin-left: 5px; \r\n" + 
      		"      }\r\n" + 
      		"      a {\r\n" + 
      		"        color: #3498db;\r\n" + 
      		"        text-decoration: underline; \r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          BUTTONS\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      .btn {\r\n" + 
      		"        box-sizing: border-box;\r\n" + 
      		"        width: 100%; }\r\n" + 
      		"        .btn > tbody > tr > td {\r\n" + 
      		"          padding-bottom: 15px; }\r\n" + 
      		"        .btn table {\r\n" + 
      		"          width: auto; \r\n" + 
      		"      }\r\n" + 
      		"        .btn table td {\r\n" + 
      		"          background-color: #ffffff;\r\n" + 
      		"          border-radius: 5px;\r\n" + 
      		"          text-align: center; \r\n" + 
      		"      }\r\n" + 
      		"        .btn a {\r\n" + 
      		"          background-color: #ffffff;\r\n" + 
      		"          border: solid 1px #3498db;\r\n" + 
      		"          border-radius: 5px;\r\n" + 
      		"          box-sizing: border-box;\r\n" + 
      		"          color: #3498db;\r\n" + 
      		"          cursor: pointer;\r\n" + 
      		"          display: inline-block;\r\n" + 
      		"          font-size: 14px;\r\n" + 
      		"          font-weight: bold;\r\n" + 
      		"          margin: 0;\r\n" + 
      		"          padding: 12px 25px;\r\n" + 
      		"          text-decoration: none;\r\n" + 
      		"          text-transform: capitalize; \r\n" + 
      		"      }\r\n" + 
      		"      .btn-primary table td {\r\n" + 
      		"        background-color: #3498db; \r\n" + 
      		"      }\r\n" + 
      		"      .btn-primary a {\r\n" + 
      		"        background-color: #3498db;\r\n" + 
      		"        border-color: #3498db;\r\n" + 
      		"        color: #ffffff; \r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          OTHER STYLES THAT MIGHT BE USEFUL\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      .last {\r\n" + 
      		"        margin-bottom: 0; \r\n" + 
      		"      }\r\n" + 
      		"      .first {\r\n" + 
      		"        margin-top: 0; \r\n" + 
      		"      }\r\n" + 
      		"      .align-center {\r\n" + 
      		"        text-align: center; \r\n" + 
      		"      }\r\n" + 
      		"      .align-right {\r\n" + 
      		"        text-align: right; \r\n" + 
      		"      }\r\n" + 
      		"      .align-left {\r\n" + 
      		"        text-align: left; \r\n" + 
      		"      }\r\n" + 
      		"      .clear {\r\n" + 
      		"        clear: both; \r\n" + 
      		"      }\r\n" + 
      		"      .mt0 {\r\n" + 
      		"        margin-top: 0; \r\n" + 
      		"      }\r\n" + 
      		"      .mb0 {\r\n" + 
      		"        margin-bottom: 0; \r\n" + 
      		"      }\r\n" + 
      		"      .preheader {\r\n" + 
      		"        color: transparent;\r\n" + 
      		"        display: none;\r\n" + 
      		"        height: 0;\r\n" + 
      		"        max-height: 0;\r\n" + 
      		"        max-width: 0;\r\n" + 
      		"        opacity: 0;\r\n" + 
      		"        overflow: hidden;\r\n" + 
      		"        mso-hide: all;\r\n" + 
      		"        visibility: hidden;\r\n" + 
      		"        width: 0; \r\n" + 
      		"      }\r\n" + 
      		"      .powered-by a {\r\n" + 
      		"        text-decoration: none; \r\n" + 
      		"      }\r\n" + 
      		"      hr {\r\n" + 
      		"        border: 0;\r\n" + 
      		"        border-bottom: 1px solid #f6f6f6;\r\n" + 
      		"        margin: 20px 0; \r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          RESPONSIVE AND MOBILE FRIENDLY STYLES\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      @media only screen and (max-width: 620px) {\r\n" + 
      		"        table[class=body] h1 {\r\n" + 
      		"          font-size: 28px !important;\r\n" + 
      		"          margin-bottom: 10px !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] p,\r\n" + 
      		"        table[class=body] ul,\r\n" + 
      		"        table[class=body] ol,\r\n" + 
      		"        table[class=body] td,\r\n" + 
      		"        table[class=body] span,\r\n" + 
      		"        table[class=body] a {\r\n" + 
      		"          font-size: 16px !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .wrapper,\r\n" + 
      		"        table[class=body] .article {\r\n" + 
      		"          padding: 10px !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .content {\r\n" + 
      		"          padding: 0 !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .container {\r\n" + 
      		"          padding: 0 !important;\r\n" + 
      		"          width: 100% !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .main {\r\n" + 
      		"          border-left-width: 0 !important;\r\n" + 
      		"          border-radius: 0 !important;\r\n" + 
      		"          border-right-width: 0 !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .btn table {\r\n" + 
      		"          width: 100% !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .btn a {\r\n" + 
      		"          width: 100% !important; \r\n" + 
      		"        }\r\n" + 
      		"        table[class=body] .img-responsive {\r\n" + 
      		"          height: auto !important;\r\n" + 
      		"          max-width: 100% !important;\r\n" + 
      		"          width: auto !important; \r\n" + 
      		"        }\r\n" + 
      		"      }\r\n" + 
      		"      /* -------------------------------------\r\n" + 
      		"          PRESERVE THESE STYLES IN THE HEAD\r\n" + 
      		"      ------------------------------------- */\r\n" + 
      		"      @media all {\r\n" + 
      		"        .ExternalClass {\r\n" + 
      		"          width: 100%; \r\n" + 
      		"        }\r\n" + 
      		"        .ExternalClass,\r\n" + 
      		"        .ExternalClass p,\r\n" + 
      		"        .ExternalClass span,\r\n" + 
      		"        .ExternalClass font,\r\n" + 
      		"        .ExternalClass td,\r\n" + 
      		"        .ExternalClass div {\r\n" + 
      		"          line-height: 100%; \r\n" + 
      		"        }\r\n" + 
      		"        .apple-link a {\r\n" + 
      		"          color: inherit !important;\r\n" + 
      		"          font-family: inherit !important;\r\n" + 
      		"          font-size: inherit !important;\r\n" + 
      		"          font-weight: inherit !important;\r\n" + 
      		"          line-height: inherit !important;\r\n" + 
      		"          text-decoration: none !important; \r\n" + 
      		"        }\r\n" + 
      		"        .btn-primary table td:hover {\r\n" + 
      		"          background-color: #34495e !important; \r\n" + 
      		"        }\r\n" + 
      		"        .btn-primary a:hover {\r\n" + 
      		"          background-color: #34495e !important;\r\n" + 
      		"          border-color: #34495e !important; \r\n" + 
      		"        } \r\n" + 
      		"      }\r\n" + 
      		"    </style>\r\n" + 
      		"  </head>\r\n" + 
      		"  <body class=\"\">\r\n" + 
      		
      		"    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\r\n" + 
      		"      <tr>\r\n" + 
      		"        <td>&nbsp;</td>\r\n" + 
      		"        <td class=\"container\">\r\n" + 
      		"          <div class=\"content\">\r\n" + 
      		"\r\n" + 
      		"            <!-- START CENTERED WHITE CONTAINER -->\r\n" + 
      		"            <table role=\"presentation\" class=\"main\">\r\n" + 
      		"\r\n" + 
      		"              <!-- START MAIN CONTENT AREA -->\r\n" + 
      		"              <tr>\r\n" + 
      		"                <td class=\"wrapper\">\r\n" + 
      		"                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
      		"                    <tr>\r\n" + 
      		"                      <td>\r\n" + 
      		"                        <p>Hi " +  w2Customer.getCustomerName()  + ",</p>\r\n" + 
      		"                        <p>Please Find Attached your W2 Tax Form for the year 2019.</p>\r\n" + 
      		 
      		"                        <p>Thanks</p>\r\n" + 
      		"                        <p>W2 Service Admin</p>\r\n" + 
      		"                      </td>\r\n" + 
      		"                    </tr>\r\n" + 
      		"                  </table>\r\n" + 
      		"                </td>\r\n" + 
      		"              </tr>\r\n" + 
      		"\r\n" + 
      		"            <!-- END MAIN CONTENT AREA -->\r\n" + 
      		"            </table>\r\n" + 
      		"            <!-- END CENTERED WHITE CONTAINER -->\r\n" + 
      		"\r\n" + 
      		"            <!-- START FOOTER -->\r\n" + 
      		"            <div class=\"footer\">\r\n" + 
      		"              <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n" + 
      		"                <tr>\r\n" + 
      		"                  <td class=\"content-block\">\r\n" + 
      		"                    <span class=\"apple-link\">Equifax, Inc., Atlanta, Georgia, United States</span>\r\n" + 
      		"                    <br> If you are not an intended recipient, please refrain from any disclosure, copying, distribution or use of this information.\r\n" + 
      		"                  </td>\r\n" + 
      		"                </tr>\r\n" + 
      		"                <tr>\r\n" + 
      		"                  <td class=\"content-block powered-by\">\r\n" + 
      		"                    Powered by Equifax Innovation Team.\r\n" + 
      		"                  </td>\r\n" + 
      		"                </tr>\r\n" + 
      		"              </table>\r\n" + 
      		"            </div>\r\n" + 
      		"            <!-- END FOOTER -->\r\n" + 
      		"\r\n" + 
      		"          </div>\r\n" + 
      		"        </td>\r\n" + 
      		"        <td>&nbsp;</td>\r\n" + 
      		"      </tr>\r\n" + 
      		"    </table>\r\n" + 
      		"  </body>\r\n" + 
      		"</html>"; 
    
      Multipart mp = new MimeMultipart();

      MimeBodyPart htmlPart = new MimeBodyPart();
      htmlPart.setContent(htmlBody, "text/html");
      mp.addBodyPart(htmlPart);
      ClassLoader classLoader = getClass().getClassLoader();
      File file = new File(classLoader.getResource("2019FormW-2.pdf").getFile());
      byte[] bytesArray = FileUtils.readFileToByteArray(file);
      MimeBodyPart attachment = new MimeBodyPart();
      InputStream attachmentDataStream = new ByteArrayInputStream(bytesArray);
      attachment.setFileName("2019FormW-2.pdf");
      attachment.setContent(attachmentDataStream, "application/pdf");
      mp.addBodyPart(attachment);

      msg.setContent(mp);
      System.out.print("Sending");
      Transport.send(msg);
      System.out.print("Sent");
    }
    
	

}
