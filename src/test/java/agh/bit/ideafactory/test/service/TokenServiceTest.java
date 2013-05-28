package agh.bit.ideafactory.test.service;

import agh.bit.ideafactory.dao.TokenDao;
import agh.bit.ideafactory.model.Token;
import agh.bit.ideafactory.service.TokenService;
import agh.bit.ideafactory.serviceimpl.TokenServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: bgrabski
 * Date: 26.05.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

    @Mock
    TokenDao tokenDao;

    @InjectMocks
    private TokenService tokenService = new TokenServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDelegateToTokenDao() {
        Token token = mock(Token.class);
        String tokenStr = "token";
        tokenService.saveToken(token);
        verify(tokenDao).saveToken(token);
        tokenService.findToken(tokenStr);
        verify(tokenDao).findToken(tokenStr);
        tokenService.findTokenById(1);
        verify(tokenDao).findTokenById(1);
        tokenService.findTokenByUserId(2);
        verify(tokenDao).findTokenByUserId(2);
        tokenService.updateToken(token);
        verify(tokenDao).updateToken(token);
        tokenService.deleteToken(token);
        verify(tokenDao).deleteToken(token);
    }


}
