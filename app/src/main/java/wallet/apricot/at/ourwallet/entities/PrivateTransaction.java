package wallet.apricot.at.ourwallet.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Christian on 07.11.2015.
 */
@ParseClassName("PrivateTransaction")
public class PrivateTransaction extends ParseObject {
    //private String mSharedTransactionID;
    /**
     * The members private transaction
     */
    //private String mUserID;

    //private double mSplittedTransactionAmount;

    public PrivateTransaction(){}
    public PrivateTransaction(String sharedTransactionID, double splittedAmount, String userID){
        setSharedTransactionID(sharedTransactionID);
        setSplittedTransactionAmount(splittedAmount);
        setUserID(userID);
    }

    public String getSharedTransactionID() {
        return getString("sharedTransactionID");
    }

    public void setSharedTransactionID(String sharedTransactionID) {
        put("sharedTransactionID",sharedTransactionID);
    }

    public double getSplittedTransactionAmount() {
        return getDouble("splittedTransactionAmount");
    }

    public void setSplittedTransactionAmount(double splittedTransactionAmount) {
       put("splittedTransactionAmount",splittedTransactionAmount);
    }
    public void setUserID(String userID){
        put("userID",userID);
    }
    public String getUserID(){
        return getString("userID");
    }

}
