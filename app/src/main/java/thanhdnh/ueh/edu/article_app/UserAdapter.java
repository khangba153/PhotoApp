package thanhdnh.ueh.edu.article_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private final ArrayList<User> users;
    private final LayoutInflater layoutInflater;

    // Nhận danh sách User và chuẩn bị công cụ tạo giao diện từng ô.
    public UserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.layoutInflater = LayoutInflater.from(context);
    }

    // Trả về số ô người dùng cần hiển thị.
    @Override
    public int getCount() {
        return users.size();
    }

    // Lấy người dùng tại vị trí được chọn.
    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    // Trả về mã người dùng của ô tại vị trí này.
    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    // Dữ liệu mẫu sử dụng mã người dùng duy nhất và không đổi.
    @Override
    public boolean hasStableIds() {
        return true;
    }

    // Tạo hoặc dùng lại một ô, rồi gán tên, tiểu sử và ảnh của User vào ô đó.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.user_list_item, parent, false);
            viewHolder = new UserViewHolder();
            viewHolder.profileImageView = convertView.findViewById(R.id.userProfileImageView);
            viewHolder.usernameTextView = convertView.findViewById(R.id.usernameTextView);
            viewHolder.biographyTextView = convertView.findViewById(R.id.biographyTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UserViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        viewHolder.usernameTextView.setText(user.getUsername());
        viewHolder.biographyTextView.setText(user.getShortBiography());
        // Picasso đọc được cả URL HTTPS và URI ảnh trong assets.
        Picasso.get().load(user.getProfileImageUrl())
                .placeholder(R.drawable.user_avatar)
                .error(R.drawable.user_avatar)
                .resize(300, 300)
                .centerCrop()
                .into(viewHolder.profileImageView);
        return convertView;
    }

    private static class UserViewHolder {
        ImageView profileImageView;
        TextView usernameTextView;
        TextView biographyTextView;
    }
}
