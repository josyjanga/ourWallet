package wallet.apricot.at.ourwallet.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andy on 07.11.15.
 */
public class TextFormatUtil {
  private TextFormatUtil() {
  }

  public static String formatBirthDay(Date date) {
    if (date == null) {
      return "Unknown";
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    return sdf.format(date);
  }

  public static String formatDate(Date date) {
    if (date == null) {
      return "Unknown";
    }
    SimpleDateFormat sdf = new SimpleDateFormat("d. MMM");
    return sdf.format(date);
  }

  public static String formatAmount(double amount) {
    DecimalFormat df = new DecimalFormat("#.00");
    return df.format(amount) + " €";
  }

}
