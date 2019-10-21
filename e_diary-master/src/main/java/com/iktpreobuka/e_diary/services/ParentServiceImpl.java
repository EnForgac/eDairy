package com.iktpreobuka.e_diary.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_diary.entities.ParentEntity;
import com.iktpreobuka.e_diary.repositories.ParentRepository;


@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	private ParentRepository parentRepo;

	// GET ALL
	@Override
	public List<ParentEntity> getAllParents() {
		List<ParentEntity> parents = (List<ParentEntity>) parentRepo.findAll();
		return parents;
	}

	// GET BY ID
	@Override
	public ParentEntity findParentById(Long id) {
		Optional<ParentEntity> p = parentRepo.findById(id);
			if (p.isPresent()) {
				return p.get();
			}else
			{
			return null;
		}
	}

	// POST
	@Override
	public ParentEntity saveParent(ParentEntity parent) {
		try {
			return parentRepo.save(parent);
		} catch (Exception e) {
			return null;
		}
	}

	// PUT
	@Override
	public ParentEntity updateParent(ParentEntity parent, Long id) {

		try {
			Optional<ParentEntity> op = parentRepo.findById(id);
			ParentEntity p = op.get();
			p.updateParent(parent);
			return parentRepo.save(p);
		} catch (Exception e) {
			return null;
		}

	}

	// DELETE
	@Override
	public Boolean removeParent(Long id) {
		Optional<ParentEntity> p = parentRepo.findById(id);
		if (p.isPresent()) {
			parentRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
