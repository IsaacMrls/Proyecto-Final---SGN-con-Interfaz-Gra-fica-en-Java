package auth;

import utils.FileUtils;
import java.util.HashMap;

public class AdminAuth {
    private HashMap<String, String> credentials;

    public AdminAuth() {
        credentials = FileUtils.loadCredentials();
    }

    public boolean login(String email, String password) {
        return credentials.containsKey(email) && credentials.get(email).equals(password);
    }

    public boolean register(String email, String password) {
        if (credentials.containsKey(email)) return false;
        credentials.put(email, password);
        FileUtils.saveCredentials(credentials);
        return true;
    }
}