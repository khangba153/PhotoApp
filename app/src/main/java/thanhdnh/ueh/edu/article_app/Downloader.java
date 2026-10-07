package thanhdnh.ueh.edu.article_app;

import java.io.File;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

// Chỉ gọi hàm tải đồng bộ này từ luồng nền của UserData.
public class Downloader {
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    // Tải JSON vào file cache; kiểm tra HTTP và báo lỗi nếu tải không thành công.
    public static File downloadFile(String url, File cacheDirectory) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Tải dữ liệu thất bại. HTTP " + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IOException("Phản hồi không có dữ liệu.");
            }
            File downloadedFile = File.createTempFile("users_", ".json", cacheDirectory);
            try (BufferedSink outputSink = Okio.buffer(Okio.sink(downloadedFile))) {
                outputSink.writeAll(responseBody.source());
            } catch (IOException exception) {
                downloadedFile.delete();
                throw exception;
            }
            return downloadedFile;
        }
    }
}
