package com.iktpreobuka.e_diary.services;

import java.util.List;


import com.iktpreobuka.e_diary.entities.ParentEntity;

public interface ParentService {

	public List<ParentEntity> getAllParents();
	
	public ParentEntity findParentById(Long id);
	
	public ParentEntity saveParent(ParentEntity parent);
	
	public ParentEntity updateParent(ParentEntity parent, Long id);
	
	public Boolean removeParent(Long id);
}
