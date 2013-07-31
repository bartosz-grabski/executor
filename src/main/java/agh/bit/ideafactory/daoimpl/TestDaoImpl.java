package agh.bit.ideafactory.daoimpl;

import agh.bit.ideafactory.dao.TestDao;
import agh.bit.ideafactory.model.Test;
import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDaoImpl extends BaseDaoImpl<Test> implements TestDao{

}
