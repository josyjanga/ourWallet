package wallet.apricot.at.ourwallet.entities;

import android.graphics.Color;

import com.amulyakhare.textdrawable.TextDrawable;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import wallet.apricot.at.ourwallet.R;

/**
 * Created by Christian on 07.11.2015.
 */
@ParseClassName("SharedAccount")
public class SharedAccount extends ParseObject {
  //private List<Member> mGroupMembers;
  //private double mPeriodicDepit;
  //private String mTitle;
  //private String mDescription;
  // access token
  // ownerID

  public SharedAccount() {
  }

  public SharedAccount(String title) {
    setTitle(title);
  }

  public SharedAccount(List<Member> groupMembers, double periodicDepit, String title, String description, String AccountOwnerID) {
    setGroupMembers(Member.convertToIdList(groupMembers));
    setPeriodicDepit(periodicDepit);
    setTitle(title);
    setDescription(description);
  }

  public void setAccountOwnerID(String id) {
    put("accountOwnerID", id);
  }

  public String getAccountOwnerID() {
    return getString("accountOwnerID");
  }

  public List<String> getGroupMembers() {
    return getList("groupMembers");
  }

  private void setGroupMembers(List<String> groupMembers) {
    put("groupMembers", groupMembers);
  }

  public String getDescription() {
    return getString("description");
  }

  public void setDescription(String description) {

    put("description", description);
  }

  public double getPeriodicDepit() {

    return getDouble("periodicDepit");
  }

  public void setPeriodicDepit(double periodicDepit) {
    put("periodicDepit", periodicDepit);
  }

  public String getTitle() {
    return getString("title");
  }

  public void setTitle(String title) {
    put("title", title);
  }

  public int getMemberCount() {
    if (getGroupMembers() == null) return 0;
    return getGroupMembers().size();
  }

  public ProfileDrawerItem asDrawerItem(int walletColor) {
    String memberDesc = getMemberCount() + " Members";
    //TODO use parse object as identifier
    TextDrawable textDrawable = TextDrawable.builder()
        .buildRound(getTitle().substring(0, 1), walletColor);
    return new ProfileDrawerItem().withName(getTitle()).withTag(getObjectId()).
        withNameShown(true).withEmail(memberDesc).withIcon(textDrawable);
  }

}
