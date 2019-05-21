# W2SMSPoC
Twilio and GCP App engine
Note: The App uses H2 in-memory database for storage of details, which may not be ideal for cloud environment.
You can deploy the app from the cloud console or via GCP plug-in for eclipse to the cloud.
The exposed API will be "http://$yourgcpprojectname$.appspot.com/getW2Report"
Now change the HTTP GET end point to the above url in the messaging option, so that Twilio will call the API with all the necessary parameters when an SMS is sent to the twilio number.
The appropriate response from the API will be sent as sms.
