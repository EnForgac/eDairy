package com.iktpreobuka.e_diary.services;

import com.iktpreobuka.e_diary.entities.EmailObject;

public interface EmailService {

	
	void sendMessage(EmailObject e) throws Exception;
}
