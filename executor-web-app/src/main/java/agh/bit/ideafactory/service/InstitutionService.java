package agh.bit.ideafactory.service;

import agh.bit.ideafactory.model.Institution;

/**
 * @author bgrabski
 */
public interface InstitutionService {

	void addInstitution(Institution a);

	Institution getInstitutionByEmail(String email);
}
