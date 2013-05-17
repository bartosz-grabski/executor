package agh.bit.ideafactory.serviceimpl;

import agh.bit.ideafactory.dao.AuthorityDao;
import agh.bit.ideafactory.model.Authority;
import agh.bit.ideafactory.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 15.05.13
 * Time: 08:57
 * To change this template use File | Settings | File Templates.
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public Authority findAuthority(String name) {
        return authorityDao.findAuthority(name);
    }
}
