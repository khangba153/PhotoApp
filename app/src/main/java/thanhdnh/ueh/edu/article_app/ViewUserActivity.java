package thanhdnh.ueh.edu.article_app;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.io.Serializable;

public class ViewUserActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "selected_user";
    private ImageView profileImageView;
    private TextView usernameTextView;
    private TextView userIdTextView;
    private TextView biographyTextView;

    // Tạo giao diện, chuẩn bị thanh tiêu đề và hiển thị người dùng đã nhận.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        setupTitleBar();
        findViews();
        showSelectedUser();
    }

    // Hiển thị tiêu đề hồ sơ và mũi tên quay lại.
    private void setupTitleBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.user_detail_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // Tìm các thành phần trong XML; thực hiện sau setContentView.
    private void findViews() {
        profileImageView = findViewById(R.id.detailProfileImageView);
        usernameTextView = findViewById(R.id.detailUsernameTextView);
        userIdTextView = findViewById(R.id.detailUserIdTextView);
        biographyTextView = findViewById(R.id.detailBiographyTextView);
    }

    // Đọc User từ Intent, kiểm tra dữ liệu rồi đưa thông tin lên giao diện.
    private void showSelectedUser() {
        // Truyền cả User qua Intent để màn hình chi tiết không phụ thuộc biến static.
        Serializable selectedValue = getIntent().getSerializableExtra(EXTRA_USER);
        if (!(selectedValue instanceof User)) {
            Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        User selectedUser = (User) selectedValue;

        showProfileImage(selectedUser.getProfileImageUrl());
        usernameTextView.setText(selectedUser.getUsername());
        userIdTextView.setText(getString(R.string.user_id, selectedUser.getId()));
        biographyTextView.setText(selectedUser.getShortBiography());
    }

    // Picasso đọc ảnh theo URI hoặc URL; dùng ảnh mặc định trong lúc chờ hoặc khi lỗi.
    private void showProfileImage(String imageUrl) {
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.user_avatar)
                .error(R.drawable.user_avatar)
                .resize(600, 600)
                .centerCrop()
                .into(profileImageView);
    }
    // Đóng màn hình chi tiết khi bấm mũi tên quay lại trên thanh tiêu đề.
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
