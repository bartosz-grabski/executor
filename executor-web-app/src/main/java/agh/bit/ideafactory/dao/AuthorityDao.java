package agh.bit.ideafactory.dao;

import agh.bit.ideafactory.model.Authority;

/**
 * Created with IntelliJ IDEA. User: Bartek Date: 15.05.13 Time: 08:31 To change this template use File | Settings | File Templates.
 */
public interface AuthorityDao extends BaseDao<Authority> {
	public Authority findAuthority(String name);
}
