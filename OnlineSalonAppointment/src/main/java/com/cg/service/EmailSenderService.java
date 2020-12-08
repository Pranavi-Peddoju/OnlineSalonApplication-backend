package com.cg.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cg.controller.GenerateID;
import com.cg.model.Customer;
import com.cg.model.UserAccountConfirmation;
import com.cg.repository.AccountConfirmationRepository;
import com.cg.repository.CustomerRepo;

/**
 * The Email Sender Service responsible for sending mails
 *
 * @author :Peddoju Teja Pranavi
 * @version :1.0
 * @since :2020-12-01
 **/
@Service("emailSenderService")
public class EmailSenderService {

	private JavaMailSender javaMailSender;
	private GenerateID auto;
	private AccountConfirmationRepository confirmationRepository;
	private CustomerRepo customerRepo;

	@Autowired
	public EmailSenderService(JavaMailSender javaMailSender, AccountConfirmationRepository confirmationRepository,
			CustomerRepo customerRepo, GenerateID auto) {
		super();
		this.javaMailSender = javaMailSender;
		this.confirmationRepository = confirmationRepository;
		this.customerRepo = customerRepo;
		this.auto = auto;
	}

	@Async
	public void sendEmail(SimpleMailMessage helper) {
		javaMailSender.send(helper);
	}

	/**
	 * addCustomerSuccess
	 * <p>
	 * This method sends an otp to the customers mail Id
	 * </p>
	 * 
	 * @param : customer
	 * @return void
	 **/
	public void addCustomerSuccess(Customer customer) throws MessagingException {

		UserAccountConfirmation confirmationToken = new UserAccountConfirmation(customer);
		confirmationToken.setTokenid(auto.generateID(confirmationToken));
		confirmationRepository.save(confirmationToken);

		SimpleMailMessage helper = new SimpleMailMessage();
		helper.setTo(customer.getEmail());
		helper.setFrom("peddojupranavi@gmail.com");
		helper.setSubject("OTP for Registration!");
		helper.setText("Dear Customer, \n" + "You're almost there. Use this otp to confirm your account : "
				+ confirmationToken.getConfirmationToken());
		sendEmail(helper);
	}

	/**
	 * changePasswordSuccess
	 * <p>
	 * This method sends the confirmation status after changing password to
	 * customers mail Id
	 * </p>
	 * 
	 * @param : customer
	 * @return String : returns success status
	 **/
	public String changePasswordSuccess(Customer customer) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(customer.getEmail());
		mailMessage.setSubject("Your password has been changed.");
		mailMessage.setFrom("peddojupranavi@gmail.com");
		mailMessage.setText("Dear Customer, \n" + "Your password has been reset successfully." + "\n"
				+ "Try logging in with either of these details\nUser Name  : " + customer.getUserName() + "\n"
				+ "User Id : " + customer.getUserId());
		sendEmail(mailMessage);
		return "success";

	}

	/**
	 * activated
	 * <p>
	 * This method sends the confirmation status after changing password to
	 * customers mail Id
	 * </p>
	 * 
	 * @param : userId
	 * @return void
	 **/
	public void activated(long userId) {
		Customer customer = customerRepo.findById(userId).get();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(customer.getEmail());
		mailMessage.setSubject("Registration Success!");
		mailMessage.setFrom("peddojupranavi@gmail.com");
		mailMessage.setText("Dear Customer, \n" + "Your are successfully registered." + "\n"
				+ "Try logging in with either of these details\nUser Name  : " + customer.getUserName() + "\n"
				+ "User Id : " + customer.getUserId());
		sendEmail(mailMessage);
	}
}