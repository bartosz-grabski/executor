package agh.bit.ideafactory.service;

import java.util.List;

import agh.bit.ideafactory.model.Domain;

public interface DomainService {

	List<Domain> getDomainsByAdminName(String username);

}
