package wallet.apricot.at.ourwallet.entities;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Christian on 07.11.2015.
 */
@ParseClassName("Member")
public class Member extends ParseObject {

  //private String mName;
  //private String mAvatarURL;
  //List<String> mSharedAccountIDs;
  //List<PrivateAccount> mPrivateAccounts;

  public Member() {
  }

  public Date getBirthday() {
    String birthString = getString("birthdate");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return sdf.parse(birthString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new Date();
  }

  public String getMail() {
    return getString("email");
  }

  public String getAvatarURL() {
    ParseFile profileImage = null;
    try {
      profileImage = fetchIfNeeded().getParseFile("profileImage");
    } catch (com.parse.ParseException e) {
      e.printStackTrace();
    }
    return profileImage != null ? profileImage.getUrl() : "";
  }

  public String getUserName() {
    return getString("username");
  }

  public String getFirstName() {
    return getString("firstName");
  }

  public String getLastName() {
    return getString("lastName");
  }

  public List<PrivateAccount> getPrivateAccounts() {
    return getList("privateAccounts");
  }

  public void setPrivateAccounts(List<PrivateAccount> privateAccounts) {
    put("privateAccounts", privateAccounts);
  }


  public List<String> getSharedAccountIDs() {
    return getList("sharedAccountIDs");
  }

  public void setSharedAccountIDs(List<String> sharedAccountIDs) {
    put("sharedAccountIDs", sharedAccountIDs);
  }

  public static List<String> convertToIdList(List<Member> members) {
    List<String> idList = new ArrayList<>();
    for (Member curMember : members) {
      idList.add(curMember.getObjectId());
    }
    return idList;
  }
}


// Armor.java
/*@ParseClassName("Armor")
public class Armor extends ParseObject {
  public String getDisplayName() {
    return getString("displayName");
  }
  public void setDisplayName(String value) {
    put("displayName", value);
  }
}

// set up our query for a Member object
ParseQuery<ParseUser> userQuery = ParseUser.getQuery();

// configure any constraints on your query...
// for example, you may want users who are also playing with or against you
// tell the query to fetch all of the Weapon objects along with the user
// get the "many" at the same time that you're getting the "one"
userQuery.include("weaponsList");

// execute the query
userQuery.findInBackground(new FindCallback<ParseUser>() {
  public void done(List<ParseUser> userList, ParseException e) {
    // userList contains all of the Member objects, and their associated Weapon objects, too
  }
});



*/
