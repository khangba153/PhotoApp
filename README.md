# Lab 1: User View

Dự án được fork từ PhotoApp của thầy và chuyển sang hiển thị người dùng.

## Các thay đổi theo yêu cầu

| Mã nguồn ban đầu | Mã nguồn bài lab |
| --- | --- |
| Article | User |
| ArticleList | UserList |
| ArticleData | UserData |
| ArticleAdapter | UserAdapter |
| ViewArticleActivity | ViewUserActivity |
| article_disp_tpl.xml | user_list_item.xml |
| activity_view_article.xml | activity_view_user.xml |

User chứa đủ 5 thuộc tính của đề bài. Tên biến Java rõ nghĩa được Gson ánh xạ sang khóa JSON:

| Khóa JSON | Thuộc tính Java |
| --- | --- |
| id | id |
| uname | username |
| password | password |
| url_profile | profileImageUrl |
| short_bio | shortBiography |

## Luồng hoạt động

1. MainActivity tạo UserData và gọi loadData(USER_DATA_SOURCE) với URL Raw GitHub.
2. UserData đọc JSON trên luồng nền bằng Runnable tường minh, chuyển JSON thành UserList bằng Gson.
3. UserAdapter hiển thị ảnh, tên và giới thiệu ngắn trong GridView.
4. Khi chọn người dùng, MainActivity truyền User qua Intent sang ViewUserActivity.
5. ViewUserActivity hiển thị ảnh, tên, mã người dùng và giới thiệu. Nút quay lại trên thanh tiêu đề trở về danh sách.

User implements Serializable để truyền qua Intent. Màn hình chi tiết không dựa vào dữ liệu static của màn hình danh sách.
Mật khẩu là dữ liệu giả cho bài lab, có getter/setter trong User và không hiển thị trên màn hình hồ sơ.
Code Java không dùng lambda; callback được viết bằng lớp anonymous có phương thức override đầy đủ.

## Dữ liệu mẫu

`app/src/main/assets/users.json` chứa 6 người dùng và đủ các khóa của đề bài.
Ảnh trong `app/src/main/assets/profiles/` được đóng gói cùng app. Danh sách tải từ GitHub cần có mạng.
Không dùng products.json vì file đó chứa articles với cấu trúc khác User.

File `data/users.json` là bản chuẩn bị để đưa lên GitHub. Chỉ có dữ liệu giả cho bài tập;
không đưa mật khẩu thật hoặc thông tin cá nhân thật vào repository công khai.

### Chuyển nguồn User sang mạng

1. Mở repository GitHub của bạn trên trình duyệt.
2. Chọn **Add file > Upload files**, kéo file `D:\P\data\users.json` vào rồi commit.
3. Mở file vừa tải lên, chọn **Raw** và sao chép URL trên thanh địa chỉ.
4. Mở `MainActivity.java`, thay giá trị `USER_DATA_SOURCE` từ `"users.json"` thành URL Raw HTTPS đó.
5. Run app với thiết bị có mạng. App sẽ tải JSON rồi hiển thị User bằng GridView.

App được cấu hình đọc `https://raw.githubusercontent.com/khangba153/PhotoApp/codex/lab1-user/data/users.json`.
File assets vẫn giữ lại để học và kiểm thử; app không tự chuyển sang assets khi tải mạng lỗi.
Ví dụ dạng URL: `https://raw.githubusercontent.com/TEN_TAI_KHOAN/TEN_REPO/TEN_NHANH/users.json`.
Đây chỉ là mẫu cấu trúc; phải dùng URL thật lấy từ nút Raw, không dán nguyên ví dụ.

Endpoint cần trả về một object có khóa `users` là một mảng chứa các trường ở bảng trên.
`url_profile` có thể là URL HTTPS hoặc URI ảnh assets như dữ liệu mẫu.
Downloader tải JSON trên luồng nền; UserData báo lỗi nếu tải thất bại hoặc JSON thiếu danh sách users.
Trong file chuẩn bị, ảnh vẫn dùng assets đã có trong app. Tải JSON qua mạng không bắt buộc
ảnh cũng phải qua mạng. Nếu muốn ảnh mạng, thay từng `url_profile` bằng link HTTPS ảnh trực tiếp.

## Chạy bài

Mở thư mục D:\P bằng Android Studio, Sync Gradle, chọn thiết bị Android 10 (API 29) trở lên và bấm Run.
AGP 9.3.0 đã được chọn để phù hợp Android Studio trên máy; Gradle wrapper dùng 9.7.1, compile SDK 34.
`local.properties` chứa đường dẫn Android SDK của máy và đã được gitignore.

Kiểm tra từ PowerShell:

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest
```

Kiểm thử đọc trực tiếp users.json, kiểm tra đủ 5 trường, ID không trùng, ảnh mẫu tồn tại,
và User được serialize/deserialize đầy đủ khi truyền sang màn hình chi tiết.

Nhánh bài lab: `codex/lab1-user`.
