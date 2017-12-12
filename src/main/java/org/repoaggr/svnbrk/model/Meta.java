package org.repoaggr.svnbrk.model;

import java.io.Serializable;

public class Meta implements Serializable {
    private String login = "";
    private String password = "";

    public Meta(String login, String password) {
        this.login = login;

        // Хорошо бы добавить хэширование для паролей
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean authNeeds() {
        return login.isEmpty() || password.isEmpty();
    }
}
