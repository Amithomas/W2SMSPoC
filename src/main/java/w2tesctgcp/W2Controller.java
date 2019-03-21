package w2tesctgcp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getW2Report(@RequestParam("From") String from, @RequestParam("Body") String message)  
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
    	w2Customer1.setRegEmail("Amith.Thomas@equifax.com");
    	w2Customer1.setRegMob("+12057750342");
    	w2Customer1.setRegStatus(1);
    	customerDetailsRepository.save(w2Customer1);
    	
    	CustomerDetails w2Customer2= new CustomerDetails();
    	w2Customer2.setCustomerId(3);
    	w2Customer2.setCustomerName("Mathews");
    	w2Customer2.setRegEmail("mathews.ebhram@equifax.com");
    	w2Customer2.setRegMob("+919048620256");
    	w2Customer2.setRegStatus(1);
    	customerDetailsRepository.save(w2Customer2);
    	
    	CustomerDetails w2Customer3= new CustomerDetails();
    	w2Customer3.setCustomerId(4);
    	w2Customer3.setCustomerName("Rajan");
    	w2Customer3.setRegEmail("Rajan@equifax.com");
    	w2Customer3.setRegMob("+919048611468");
    	w2Customer3.setRegStatus(0);
    	customerDetailsRepository.save(w2Customer3);
    	
    	
    	return customerDetailsRepository.findAll();
	}
    
    @CrossOrigin
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public CustomerDetails insertDB (@RequestParam CustomerDetails cd){
    	return customerDetailsRepository.save(cd);
    }
	

}
