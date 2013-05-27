package agh.bit.ideafactory.test.service;

import agh.bit.ideafactory.dao.AuthorityDao;
import agh.bit.ideafactory.service.AuthorityService;
import agh.bit.ideafactory.serviceimpl.AuthorityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 27.05.13
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorityServiceTest {

    @Mock
    private AuthorityDao authorityDao;

    @InjectMocks
    private AuthorityService authorityService = new AuthorityServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDelegateToDao() {
        String authorityName = "ROLE_USER";
        authorityService.findAuthority(authorityName);
        verify(authorityDao).findAuthority(authorityName);

    }




}
