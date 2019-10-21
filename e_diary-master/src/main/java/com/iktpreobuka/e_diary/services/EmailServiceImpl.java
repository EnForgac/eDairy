package com.iktpreobuka.e_diary.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.EmailObject;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void sendMessage(EmailObject object) throws Exception {
		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(object.getEmailParent());
		helper.setSubject("ocenjivanje");
		String text = "<html><body>Poštovani, <br> Vaše dete, " + object.getStudentName() + " "
				+ object.getStudentLastName() + ", dobio/la je ocenu " + object.getMark() + " iz predmeta "
				+ object.getSubjectName() +" (" + object.getMarkType()
				+ ") , koji predaje " + object.getTeacherName() + " "
				+ object.getTeacherLastName() + "! <br><br> S' poštovanjem <br> Školska uprava</body></html>";

		helper.setText(text, true);
		emailSender.send(mail);

	}

}
