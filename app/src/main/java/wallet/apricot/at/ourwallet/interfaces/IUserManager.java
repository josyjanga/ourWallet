package wallet.apricot.at.ourwallet.interfaces;

import java.util.List;

import wallet.apricot.at.ourwallet.entities.PrivateAccount;
import wallet.apricot.at.ourwallet.entities.Member;

/**
 * Created by Christian on 07.11.2015.
 */
public interface IUserManager {

  void saveMyUser(Member user);

  Member getMyUser();



}
