package com.cg.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.model.Address;
import com.cg.model.Admin;
import com.cg.model.Appointment;
import com.cg.model.Card;
import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.Payment;
import com.cg.model.SalonService;
import com.cg.model.UserAccountConfirmation;


/**
* The GenerateID class responsible for generating ID's
*
* @author   :Peddoju Teja Pranavi
* @version  :1.0
* @since    :2020-12-01 
**/
@Component
public class GenerateID {

	@Autowired
	Random random;

	public int generateID(Object obj) {
		if (obj instanceof Admin) {
			return random.nextInt(1000) + 1000;
		} else if (obj instanceof Customer) {
			return (random.nextInt(100000) + 100000);
		} else if (obj instanceof Address) {
			return random.nextInt(1000) + 2000;
		} else if (obj instanceof SalonService) {
			return random.nextInt(1000) + 4000;
		} else if (obj instanceof Appointment) {
			return random.nextInt(10000) + 50000;
		} else if (obj instanceof Order) {
			return random.nextInt(1000) + 3000;
		} else if (obj instanceof Payment) {
			return random.nextInt(100000) + 600000;
		} else if (obj instanceof Card) {
			return random.nextInt(10000) + 70000;
		} else if (obj instanceof UserAccountConfirmation) {
			return random.nextInt(100) + 900;
		}
		return 0;
	}

}
