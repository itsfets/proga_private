package core;

import modelworks.PasswordHasher;

public class Session {
    private String login;
    private String password;
    private boolean isAuthorized;

    public Session() {
        isAuthorized = false;
    }

    public void setCredentials(String login, String password) {
        this.login = login;
        this.password = PasswordHasher.hash(password);
        this.isAuthorized = true;
    }

    public void clear() {
        this.login = null;
        this.password = null;
        this.isAuthorized = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        if (login == null || password == null) {
            return;
        }
        this.isAuthorized = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordHasher.hash(password);
        if (login == null) {
        }
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }
}
