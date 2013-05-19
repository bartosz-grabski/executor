package agh.bit.ideafactory.service;

/**
 * Created with IntelliJ IDEA.
 * User: Bartek
 * Date: 17.05.13
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
public interface MailService {

    public void sendMail(String from, String to, String subject, String message);

}
