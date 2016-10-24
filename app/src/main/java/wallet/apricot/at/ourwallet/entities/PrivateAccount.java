package wallet.apricot.at.ourwallet.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Christian on 07.11.2015.
 */
@ParseClassName("PrivateAccount")
public class PrivateAccount extends ParseObject {

    //private String mOwnerID;

    public PrivateAccount() {
    }


    public String getOwnerID() {
        return getString("ownerID");
    }

    public void setOwnerID(String ownerID) {
        put("ownerID",ownerID);
    }
}
