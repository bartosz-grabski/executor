package agh.bit.ideafactory.serviceimpl;

import agh.bit.ideafactory.dao.TokenDao;
import agh.bit.ideafactory.model.Token;
import agh.bit.ideafactory.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 18.05.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDao tokenDao;


    @Override
    @Transactional
    public void saveToken(Token t) {
        tokenDao.saveToken(t);
    }

    @Override
    @Transactional(readOnly = true)
    public Token findTokenById(int id) {
        return tokenDao.findTokenById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Token findTokenByUserId(int id) {
        return tokenDao.findTokenByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Token findToken(String token) {
        return tokenDao.findToken(token);
    }

    @Override
    @Transactional
    public void updateToken(Token t) {
        tokenDao.updateToken(t);
    }

    @Transactional
    @Override
    public void deleteToken(Token t) {
        tokenDao.deleteToken(t);
    }

}
