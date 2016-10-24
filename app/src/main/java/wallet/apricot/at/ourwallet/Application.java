package wallet.apricot.at.ourwallet;

import android.app.ProgressDialog;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.entities.PrivateAccount;
import wallet.apricot.at.ourwallet.entities.PrivateTransaction;
import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;
import wallet.apricot.at.ourwallet.entities.PaymentTag;
import wallet.apricot.at.ourwallet.modules.EntityManager;
import wallet.apricot.at.ourwallet.util.Util;

/**
 * Created by Christian on 07.11.2015.
 */
public class Application extends android.app.Application{

    public static final boolean DEBUG = false;
    /**
     * Checks code on backend
     */
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        ParseObject.registerSubclass(Member.class);
        ParseObject.registerSubclass(SharedAccount.class);
        ParseObject.registerSubclass(PrivateAccount.class);
        ParseObject.registerSubclass(SharedTransaction.class);
        ParseObject.registerSubclass(PrivateTransaction.class);
        ParseObject.registerSubclass(PaymentTag.class);

        Parse.enableLocalDatastore(this);

        // create new instances with CTO
        // reference to an existing object --> Armor armorReference = ParseObject.createWithoutData(Armor.class, armor.getObjectId());

        // Add a relation between the Post with objectId "1zEcyElZ80" and the comment
        //myComment.put("parent", ParseObject.createWithoutData("Post", "1zEcyElZ80"));

        Parse.initialize(this, "KxprPOVXO4zrQVDSk1T6p1XEBYpdXF2HoqrS5oX8", "geSVazRqruP6u9IyId1UU7JIbTYYWyYACSFptJev");
        ParseInstallation.getCurrentInstallation().saveInBackground();

      // delete for new user login test
      //Util.deleteUser(getApplicationContext());
      EntityManager.INSTANCE.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

}
