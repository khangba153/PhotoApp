package thanhdnh.ueh.edu.article_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GridView userGridView;
    private UserData userData;
    private TextView statusTextView;

    // Tải dữ liệu User từ file JSON trên nhánh bài lab của repository GitHub.
    private static final String USER_DATA_SOURCE =
            "https://raw.githubusercontent.com/khangba153/PhotoApp/codex/lab1-user/data/users.json";

    // Tạo màn hình rồi lần lượt tìm giao diện, đăng ký sự kiện và đọc dữ liệu.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        findViews();
        setupUserClick();
        loadUsers();
    }

    // Tìm danh sách và dòng thông báo trong activity_main.xml.
    private void findViews() {
        userGridView = findViewById(R.id.userGridView);
        statusTextView = findViewById(R.id.statusTextView);
        userGridView.setEmptyView(statusTextView);
    }

    // Giao việc đọc JSON cho UserData; không đọc mạng trực tiếp trong Activity.
    private void loadUsers() {
        userData = new UserData(this, userGridView, statusTextView);
        userData.loadData(USER_DATA_SOURCE);
    }

    // Đăng ký thao tác bấm vào một người dùng trong GridView.
    private void setupUserClick() {
        userGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Lấy đối tượng ở vị trí được bấm rồi mở hồ sơ của người đó.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) parent.getItemAtPosition(position);
                openUserDetail(selectedUser);
            }
        });
    }

    // Gửi người dùng được chọn sang màn hình chi tiết qua Intent.
    private void openUserDetail(User selectedUser) {
        Intent detailIntent = new Intent(this, ViewUserActivity.class);
        detailIntent.putExtra(ViewUserActivity.EXTRA_USER, selectedUser);
        startActivity(detailIntent);
    }

    // Dừng tác vụ đọc dữ liệu khi Activity bị hủy.
    @Override
    protected void onDestroy() {
        if (userData != null) {
            userData.close();
        }
        super.onDestroy();
    }
}
