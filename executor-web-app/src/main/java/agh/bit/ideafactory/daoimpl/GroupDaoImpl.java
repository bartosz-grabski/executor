package agh.bit.ideafactory.daoimpl;

import org.springframework.stereotype.Repository;

import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.model.Group;

@Repository("groupDao")
public class GroupDaoImpl extends BaseDaoImpl<Group> implements GroupDao {

}
