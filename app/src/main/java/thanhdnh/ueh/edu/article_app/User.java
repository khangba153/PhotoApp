package thanhdnh.ueh.edu.article_app;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

// Tên thuộc tính Java rõ nghĩa; SerializedName ánh xạ đúng khóa JSON của bài lab.
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private int id;

    @SerializedName("uname")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("url_profile")
    private String profileImageUrl;

    @SerializedName("short_bio")
    private String shortBiography;

    // Tạo một người dùng có đủ năm thông tin của đề.
    public User(int id, String username, String password,
                String profileImageUrl, String shortBiography) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.shortBiography = shortBiography;
    }

    // Đọc mã người dùng.
    public int getId() {
        return id;
    }

    // Cập nhật mã người dùng.
    public void setId(int id) {
        this.id = id;
    }

    // Đọc tên người dùng.
    public String getUsername() {
        return username;
    }

    // Cập nhật tên người dùng.
    public void setUsername(String username) {
        this.username = username;
    }

    // Đọc mật khẩu mẫu; giao diện hồ sơ không hiển thị mật khẩu.
    public String getPassword() {
        return password;
    }

    // Cập nhật mật khẩu.
    public void setPassword(String password) {
        this.password = password;
    }

    // Đọc địa chỉ ảnh đại diện.
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    // Cập nhật địa chỉ ảnh đại diện.
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // Đọc phần giới thiệu ngắn.
    public String getShortBiography() {
        return shortBiography;
    }

    // Cập nhật phần giới thiệu ngắn.
    public void setShortBiography(String shortBiography) {
        this.shortBiography = shortBiography;
    }
}
