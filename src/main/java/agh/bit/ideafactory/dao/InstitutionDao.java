package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.Institution;

/**
 * @author bgrabski
 */
public interface InstitutionDao extends BaseDao<Institution> {
    public Institution getByEmail(String email);
}
