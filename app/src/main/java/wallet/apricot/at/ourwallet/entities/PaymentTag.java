package wallet.apricot.at.ourwallet.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Christian on 07.11.2015.
 */
@ParseClassName("PaymentTag")
public class PaymentTag extends ParseObject {
    public String getTag() {
        return getString("tag");
    }

    public void setTag(String tag) {
        put("tag",tag);
    }

    //private String mTag;

    public PaymentTag(){}
}
