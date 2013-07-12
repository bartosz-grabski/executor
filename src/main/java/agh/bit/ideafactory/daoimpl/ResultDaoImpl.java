package agh.bit.ideafactory.daoimpl;

import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.ResultDao;
import agh.bit.ideafactory.model.Result;

@Repository("resultDao")
public class ResultDaoImpl extends BaseDaoImpl<Result> implements ResultDao {

}
