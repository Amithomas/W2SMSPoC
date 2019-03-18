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
		else if(w2Customer!=null && message.equals("W2")) {
			
	    return "Hi " + w2Customer.getCustomerName() + "\n" + "W2 Report sent to Registered Email ID : " +  w2Customer.getRegEmail() + " .";
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
    	
		customerDetailsRepository.save(w2Customer);
		
		CustomerDetails w2Customer1= new CustomerDetails();
    	w2Customer1.setCustomerId(2);
    	w2Customer1.setCustomerName("Amith Thomas");
    	w2Customer1.setRegEmail("Amith.Thomas@equifax.com");
    	w2Customer1.setRegMob("+12057750342");
    	customerDetailsRepository.save(w2Customer1);
    	return customerDetailsRepository.findAll();
	}
    
    
	

}
