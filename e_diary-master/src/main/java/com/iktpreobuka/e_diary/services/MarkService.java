package com.iktpreobuka.e_diary.services;

import java.util.List;

import com.iktpreobuka.e_diary.entities.MarkEntity;

public interface MarkService {
	
	public List<MarkEntity> getAllMarks();
	
	public MarkEntity findMarkById(Long id);

	public MarkEntity saveMark(MarkEntity mark);
	
	public MarkEntity editMark(MarkEntity mark, Long id);
	
	public boolean removeMark(Long id);

}
