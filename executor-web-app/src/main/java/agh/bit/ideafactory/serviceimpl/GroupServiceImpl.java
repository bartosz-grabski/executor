package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private DomainDao domainDao;

	@Override
	public List<Group> getGroupsByDomain(Long domainId) {

		Domain domain = domainDao.findById(domainId);
		if (domain == null) {
			return null;
		}
		return domain.getGroups();

	}

}
