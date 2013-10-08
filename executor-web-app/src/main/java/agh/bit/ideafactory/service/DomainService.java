package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.exception.NoObjectFoundException;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;
import agh.bit.ideafactory.model.User;

public interface DomainService {

	List<Domain> getDomainsByAdminName(String username);

	Domain create(Domain domain, Institution institution) throws NotUniquePropertyException;

	Domain findById(Long domainId);

	Domain findByIdFetched(Long domainId);

	List<Domain> findAll();

	boolean joinDomain(Long domainId, String password, String userName) throws PasswordMatchException;

	List<Domain> findAllNotJoinedYet(String userName);

	List<User> getUsersWhoCanBecomeAdmins(Long id);

	Domain addAdminToDomain(Long domainId, Long userId) throws NoObjectFoundException;

	Domain deleteAdminFromDomain(Long domainId, Long userId) throws NoObjectFoundException;

}
