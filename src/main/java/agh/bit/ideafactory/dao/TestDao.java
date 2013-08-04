package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.Test;

public interface TestDao extends BaseDao<Test> {

	public Long getHighestTestID();
}
