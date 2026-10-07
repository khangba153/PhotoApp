package thanhdnh.ueh.edu.article_app;

import android.app.Activity;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserData {
    private final Activity activity;
    private final GridView userGridView;
    private final TextView statusTextView;
    private final ExecutorService executorService;

    // Nhận màn hình và các thành phần cần cập nhật sau khi đọc dữ liệu.
    public UserData(Activity activity, GridView userGridView, TextView statusTextView) {
        this.activity = activity;
        this.userGridView = userGridView;
        this.statusTextView = statusTextView;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // dataSource là tên file trong assets hoặc URL HTTPS trả về JSON có khóa "users".
    public void loadData(final String dataSource) {
        executorService.execute(new Runnable() {
            @Override
            // Đọc dữ liệu trên luồng nền để không làm màn hình bị đứng.
            public void run() {
                try {
                    final UserList userList = readUserList(dataSource);
                    showUsers(userList);
                } catch (final Exception exception) {
                    android.util.Log.e("UserData", "Không thể đọc dữ liệu người dùng", exception);
                    showLoadError();
                }
            }
        });
    }

    // Đưa danh sách đã đọc vào Adapter trên luồng giao diện.
    private void showUsers(final UserList userList) {
        activity.runOnUiThread(new Runnable() {
            // Chỉ cập nhật giao diện nếu màn hình vẫn còn hoạt động.
            @Override
            public void run() {
                if (activity.isFinishing() || activity.isDestroyed()) {
                    return;
                }
                statusTextView.setText(R.string.no_users);
                UserAdapter userAdapter = new UserAdapter(userList.getUsers(), activity);
                userGridView.setAdapter(userAdapter);
            }
        });
    }

    // Thông báo lỗi tải mạng hoặc JSON không hợp lệ cho người dùng.
    private void showLoadError() {
        activity.runOnUiThread(new Runnable() {
            // Hiển thị thông báo lỗi trên luồng giao diện.
            @Override
            public void run() {
                if (activity.isFinishing() || activity.isDestroyed()) {
                    return;
                }
                statusTextView.setText(R.string.load_users_error);
                Toast.makeText(activity, R.string.load_users_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Mở file mẫu hoặc tải file HTTPS, rồi dùng Gson chuyển JSON thành UserList.
    private UserList readUserList(String dataSource) throws IOException {
        InputStream inputStream;
        File downloadedFile = null;
        if (dataSource.startsWith("https://")) {
            downloadedFile = Downloader.downloadFile(dataSource, activity.getCacheDir());
            inputStream = new FileInputStream(downloadedFile);
        } else {
            inputStream = activity.getAssets().open(dataSource);
        }

        // try-with-resources đóng reader và luồng đọc sau khi xử lý JSON.
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            UserList userList = gson.fromJson(reader, UserList.class);
            if (userList == null || userList.getUsers() == null) {
                throw new IOException("JSON phải chứa danh sách users.");
            }
            return userList;
        } finally {
            if (downloadedFile != null) {
                downloadedFile.delete();
            }
        }
    }

    // Ngừng nhận công việc tải mới khi Activity bị hủy.
    public void close() {
        executorService.shutdownNow();
    }
}
