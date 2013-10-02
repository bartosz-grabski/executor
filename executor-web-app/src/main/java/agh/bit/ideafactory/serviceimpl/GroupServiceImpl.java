package agh.bit.ideafactory.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agh.bit.ideafactory.dao.DomainDao;
import agh.bit.ideafactory.dao.GroupDao;
import agh.bit.ideafactory.dao.UserDao;
import agh.bit.ideafactory.exception.NotUniquePropertyException;
import agh.bit.ideafactory.exception.PasswordMatchException;
import agh.bit.ideafactory.helpers.ExecutorSaltSource;
import agh.bit.ideafactory.model.Domain;
import agh.bit.ideafactory.model.Group;
import agh.bit.ideafactory.model.User;
import agh.bit.ideafactory.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private DomainDao domainDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	public UserDao userDao;

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
			if (group.getTitle().equalsIgnoreCase(existingGroup.getTitle())) {
				throw new NotUniquePropertyException("Group title must be unique among domain groups!", Group.class, "title");
			}
		}

		group.setPassword(passwordEncoder.encodePassword(group.getPassword(), ExecutorSaltSource.getSalt()));

		group.setDomain(domain);
		domain.getGroups().add(group);

		groupDao.save(group);

		return group;
	}

	@Override
	public Group findById(Long groupId) {
		return groupDao.findById(groupId);
	}

	@Override
	public Group findByIdFetched(Long groupId) {
		Group group = groupDao.findById(groupId);
		if (group != null) {
			group.getUsers().size();
		}
		return group;
	}

	@Override
	public Group joinGroup(Long groupId, String userName, String groupPassword) throws PasswordMatchException {

		Group group = groupDao.findById(groupId);

		if (group != null) {
			User user = userDao.getUserByUserName(userName);

			String passwordEncoded = passwordEncoder.encodePassword(groupPassword, ExecutorSaltSource.getSalt());

			if (!group.getPassword().equals(passwordEncoded)) {
				throw new PasswordMatchException("Group password doesn't match!");
			}

			user.getGroups().add(group);
			group.getUsers().add(user);

			groupDao.save(group);
			userDao.save(user);

		}

		return group;
	}

}
