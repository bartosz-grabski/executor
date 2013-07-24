package agh.bit.ideafactory.daoimpl;

import agh.bit.ideafactory.dao.InstitutionDao;
import agh.bit.ideafactory.model.Institution;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bgrabski
 */
@Repository("institutionDao")
public class InstitutionDaoImpl implements InstitutionDao {
    @Override
    public Institution getByEmail(String email) {
        return null;
    }

    @Override
    public Institution findById(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Institution> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteById(long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Institution object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(Institution object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveOrUpdate(Institution object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Institution object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
