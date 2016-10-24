package wallet.apricot.at.ourwallet.gui;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.util.CircleTransform;

/**
 * Created by andy on 07.11.15.
 */
public class UserListAdapter extends BaseAdapter {
  private final List<Member> mMembers;
  private final Context mContext;

  public UserListAdapter(Context context, List<Member> members) {
    mContext = context;
    mMembers = members;
  }

  public boolean contains(String objectID) {
    for (Member m : mMembers) {
      if (m.getObjectId().equals(objectID)) {
        return true;
      }
    }
    return false;
  }

  public void addMember(Member member) {
    if (member != null) {
      mMembers.add(member);
      notifyDataSetChanged();
    }
  }

  public List<Member> getMembers() {
    return mMembers;
  }

  @Override
  public int getCount() {
    return mMembers.size();
  }

  @Override
  public Object getItem(int i) {
    return mMembers.get(i);
  }

  @Override
  public long getItemId(int i) {
    return mMembers.get(i).hashCode();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) mContext
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    convertView = inflater.inflate(R.layout.list_item_user, parent, false);

    if (convertView != null) {
      Member curMember = mMembers.get(position);

      ImageView imgView = (ImageView) convertView.findViewById(R.id.image_user_avatar);
      try {
        Picasso.with(mContext).load(curMember.getAvatarURL())
            .resize(100, 100).centerInside().transform(new CircleTransform())
            .placeholder(R.drawable.account_placeholder).into(imgView);
      }catch(Exception e){
        e.printStackTrace();
      }

      TextView textName = (TextView) convertView.findViewById(R.id.text_user_name);
      StringBuilder str = new StringBuilder();
      str.append(curMember.getFirstName());
      str.append(" ");
      str.append(curMember.getLastName());
      textName.setText(str.toString());
    }

    return convertView;
  }
}
