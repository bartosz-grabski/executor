package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.model.Group;

public interface GroupService {

	List<Group> getGroupsByDomain(Long domainId);

}
