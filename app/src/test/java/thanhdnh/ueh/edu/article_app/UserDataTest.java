package thanhdnh.ueh.edu.article_app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import org.junit.Test;

public class UserDataTest {
    @Test
    public void sampleJsonContainsCompleteUsersAndExistingAvatars() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/users.json");
        assertNotNull(inputStream);
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            UserList userList = new Gson().fromJson(reader, UserList.class);
            assertEquals(6, userList.getUsers().size());
            HashSet<Integer> userIds = new HashSet<Integer>();
            for (User user : userList.getUsers()) {
                assertTrue(userIds.add(user.getId()));
                assertNotNull(user.getUsername());
                assertNotNull(user.getPassword());
                assertNotNull(user.getProfileImageUrl());
                assertNotNull(user.getShortBiography());
                String imagePath = user.getProfileImageUrl().replace("file:///android_asset", "");
                try (InputStream imageStream = getClass().getResourceAsStream(imagePath)) {
                    assertNotNull(imageStream);
                }
            }
            User firstUser = userList.getUsers().get(0);
            assertEquals(1, firstUser.getId());
            assertEquals("An Nguyễn", firstUser.getUsername());
            assertEquals("demo1123", firstUser.getPassword());
            assertEquals("file:///android_asset/profiles/user_1.png", firstUser.getProfileImageUrl());
            assertEquals("Sinh viên yêu thích lập trình Android và thiết kế giao diện.",
                    firstUser.getShortBiography());
        }
    }

    @Test
    public void userCanBeSerializedForDetailActivity() throws Exception {
        User originalUser = new User(20, "Hà", "demo-password", "https://example.com/avatar.png", "Học Java");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(buffer)) {
            outputStream.writeObject(originalUser);
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            User restoredUser = (User) inputStream.readObject();
            assertEquals(originalUser.getId(), restoredUser.getId());
            assertEquals(originalUser.getUsername(), restoredUser.getUsername());
            assertEquals(originalUser.getPassword(), restoredUser.getPassword());
            assertEquals(originalUser.getProfileImageUrl(), restoredUser.getProfileImageUrl());
            assertEquals(originalUser.getShortBiography(), restoredUser.getShortBiography());
        }
    }
}
