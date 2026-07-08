package in.ssf.notification.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import in.ssf.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    
    @Override
    public void sendEmail(String to,
                          String subject,
                          String body) 
    {
    		log.info("Sending email to {}",to);
    		
    		try 
    		{
	        SimpleMailMessage message = new SimpleMailMessage();
	
	        message.setFrom("rajdeepsinghc185@gmail.com");
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	
	        mailSender.send(message);
	        log.info("Mail Sent Successfully");
    		}
    		catch (Exception e) {
    			log.error("Failed to process the Email Sending Request", e);
    	    }
    }
}
