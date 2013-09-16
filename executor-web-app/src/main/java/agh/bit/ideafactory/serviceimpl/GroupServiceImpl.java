package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private DomainDao domainDao;

	@Autowired
	private GroupDao groupDao;

	@Override
	public List<Group> getGroupsByDomain(Long domainId) {

		Domain domain = domainDao.findById(domainId);
		if (domain == null) {
			return null;
		}
		return domain.getGroups();

	}

	@Override
	public Group save(Group group, Domain domain) throws NotUniquePropertyException {

		for (Group existingGroup : domain.getGroups()) {
			if (group.getTitle().equals(existingGroup.getTitle())) {
				throw new NotUniquePropertyException("Group title must be unique among domain groups!", Group.class, "title");
			}
		}

		group.setDomain(domain);
		domain.getGroups().add(group);
		groupDao.save(group);

		return group;
	}

}
