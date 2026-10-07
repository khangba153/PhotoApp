package thanhdnh.ueh.edu.article_app;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class UserList {
    @SerializedName("users")
    private ArrayList<User> users;

    // Tạo đối tượng chứa danh sách người dùng.
    public UserList(ArrayList<User> users) {
        this.users = users;
    }

    // Trả về danh sách đã đọc từ khóa users trong JSON.
    public ArrayList<User> getUsers() {
        return users;
    }

    // Thay đổi danh sách người dùng.
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
