package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;

public interface GroupService {

	List<Group> getGroupsByDomain(Long domainId);

	Group save(Group group, Domain domain) throws NotUniquePropertyException;

	Group findById(Long groupId);

	Group joinGroup(Long groupId, String userName, String groupPassword) throws PasswordMatchException;

	Group findByIdFetched(Long groupId);

}