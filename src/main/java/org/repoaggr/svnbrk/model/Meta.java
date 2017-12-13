package org.repoaggr.svnbrk.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Clock;

public class Meta implements Serializable {
    private String url = "";
    private String login = "";
    private String password = "";
    private BigDecimal last_sync_date =
            new BigDecimal(Clock.systemDefaultZone().millis());

    public Meta(String url, String login, String password) {
        this.url = url;
        this.login = login;

        // Хорошо бы добавить хэширование для паролей
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getLast_sync_date() {
        return last_sync_date;
    }

    public void setLast_sync_date(BigDecimal last_sync_date) {
        this.last_sync_date = last_sync_date;
    }

    public boolean authNeeds() {
        return login.isEmpty() || password.isEmpty();
    }
}
