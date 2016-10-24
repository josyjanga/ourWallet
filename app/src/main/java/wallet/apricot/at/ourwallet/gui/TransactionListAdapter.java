package wallet.apricot.at.ourwallet.gui;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;
import wallet.apricot.at.ourwallet.util.TextFormatUtil;

/**
 * Created by andy on 07.11.15.
 */
public class TransactionListAdapter extends BaseAdapter {
  private final List<SharedTransaction> mTransactions;
  private final Context mContext;

  public TransactionListAdapter(Context context, List<SharedTransaction> sharedTransactions) {
    this.mTransactions = sharedTransactions;
    this.mContext = context;
  }

  @Override
  public int getCount() {
    return mTransactions.size();
  }

  @Override
  public Object getItem(int i) {
    return mTransactions.get(i);
  }

  @Override
  public long getItemId(int i) {
    return mTransactions.get(i).hashCode();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) mContext
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.list_item_transaction, parent, false);
      SharedTransaction curTransaction = mTransactions.get(position);

      ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.image_avatar);
      Member payingMember = curTransaction.getPayingMember();
      if (payingMember != null) {
        String avatarUrl = payingMember.getAvatarURL();
        Log.d("uuu", "hu: " + avatarUrl);
        if (!avatarUrl.isEmpty()) {
          Picasso.with(mContext).load(avatarUrl).
              placeholder(R.drawable.account_placeholder).into(imgAvatar);
        } else {
          String userName = payingMember.getUserName();
          String firstLetter = userName != null ? userName.substring(0, 1) : " ";
          TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter,
              ColorGenerator.MATERIAL.getRandomColor());
          imgAvatar.setImageDrawable(textDrawable);
        }
      }
      TextView textAmount = (TextView) convertView.findViewById(R.id.text_amount);
      textAmount.setText(TextFormatUtil.formatAmount(curTransaction.getTransactionAmount()));

      TextView textDesc = (TextView) convertView.findViewById(R.id.text_description);
      textDesc.setText(curTransaction.getTitle());

      TextView textDate = (TextView) convertView.findViewById(R.id.text_date);
      textDate.setText(TextFormatUtil.formatDate(curTransaction.getTransactionDate()));
    }

    return convertView;
  }
}
