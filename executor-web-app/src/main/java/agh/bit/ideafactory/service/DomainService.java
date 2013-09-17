package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Institution;

public interface DomainService {

	List<Domain> getDomainsByAdminName(String username);

	Domain create(Domain domain, Institution institution) throws NotUniquePropertyException;

	Domain findById(Long domainId);

	Domain findByIdFetched(Long domainId);

}
