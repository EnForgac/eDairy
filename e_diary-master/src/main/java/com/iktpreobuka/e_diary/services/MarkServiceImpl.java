package com.iktpreobuka.e_diary.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.ClassEntity;
import com.iktpreobuka.e_diary.entities.EmailObject;
import com.iktpreobuka.e_diary.entities.MarkEntity;
import com.iktpreobuka.e_diary.entities.StudentEntity;
import com.iktpreobuka.e_diary.entities.SubjectEntity;
import com.iktpreobuka.e_diary.entities.TeacherEntity;
import com.iktpreobuka.e_diary.repositories.MarkRepository;

@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	private MarkRepository markRepo;

	@Autowired
	private EmailService emailService;

	// GET ALL
	@Override
	public List<MarkEntity> getAllMarks() {
		List<MarkEntity> marks = (List<MarkEntity>) markRepo.findAll();
		return marks;
	}

	// GET BY ID
	@Override
	public MarkEntity findMarkById(Long id) {
		Optional<MarkEntity> m = markRepo.findById(id);
		if (m.isPresent()) {
			return m.get();
		} else {
			return null;
		}
	}

	// POST
	@Override
	public MarkEntity saveMark(MarkEntity mark) {

		StudentEntity stud = mark.getStudent();
		ClassEntity clas = stud.getClas();
		List<SubjectEntity> subjs = clas.getSubject();

		for (SubjectEntity s : subjs) {
			if (s.getId() == mark.getSubject().getId()) {

				List<TeacherEntity> tchrs = s.getTeachers();
				for (TeacherEntity t : tchrs) {
					if (t.getId() == mark.getTeacher().getId()) {
						MarkEntity m = markRepo.save(mark);
						try {
							emailService.sendMessage(new EmailObject(stud.getParent().getEmail(), stud.getName(),
									stud.getLastName(), m.getMark(), m.getMarkType(), m.getSubject().getName(),
									m.getTeacher().getName(), m.getTeacher().getLastName()));
						} catch (Exception e) {
							// do nothing
						}
						return m;
					}
				}
			}
		}
		return null;
	}

	// PUT
	@Override
	public MarkEntity editMark(MarkEntity mark, Long id) {
		try {
			Optional<MarkEntity> op = markRepo.findById(id);
			MarkEntity m = op.get();
			
			StudentEntity stud = mark.getStudent();
			ClassEntity clas = stud.getClas();
			List<SubjectEntity> subjs = clas.getSubject();

			for (SubjectEntity s : subjs) {
				if (s.getId() == mark.getSubject().getId()) {

					List<TeacherEntity> tchrs = s.getTeachers();
					for (TeacherEntity t : tchrs) {
						if (t.getId() == mark.getTeacher().getId()) {
							m.updateMark(mark);			
						}
					}
				}
			}
			return markRepo.save(m);
		} catch (Exception e) {
			return null;
		}

	}

	// DELETE
	@Override
	public boolean removeMark(Long id) {
		Optional<MarkEntity> m = markRepo.findById(id);
		if (m.isPresent()) {
			markRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
