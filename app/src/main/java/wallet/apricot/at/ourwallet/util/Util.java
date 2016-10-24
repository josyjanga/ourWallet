package wallet.apricot.at.ourwallet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Christian on 07.11.2015.
 */
public class Util {

  public static void sendPushNotification(String walletId, double amount, String userName, String title) {
    ParsePush push = new ParsePush();
    push.setData(getPushData(userName, amount, title));
    push.setChannel(walletId);
    push.sendInBackground();
  }

  private static JSONObject getPushData(String userName, double amount, String title) {
    JSONObject data = new JSONObject();
    try {
      data.put("title", userName + " pays " + TextFormatUtil.formatAmount(amount));
      data.put("alert", title);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return data;
  }

  public static boolean isUserLoggedIn(Context context) {
    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return !mSharedPreferences.getString(AppConstants.USER_ID, "").isEmpty();
  }

  public static String getCurrentUserID(Context context) {
    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return mSharedPreferences.getString(AppConstants.USER_ID, "");
  }

  public static void deleteUser(Context context) {
    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    mSharedPreferences.edit().putString(AppConstants.USER_ID, "").commit();
  }

  public static void storeStringInPref(Context context, String key, String val) {
    SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    mSharedPreferences.edit().putString(key, val).commit();
  }

  public static boolean hasKitKat(){
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  // public static void storeInPreferences(String key, )
}
