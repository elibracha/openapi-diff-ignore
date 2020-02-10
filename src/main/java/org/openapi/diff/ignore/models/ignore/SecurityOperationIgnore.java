package org.openapi.diff.ignore.models.ignore;

import java.util.Map;
import java.util.Objects;

public class SecurityOperationIgnore {

    private Map<String, Object> securities;

    public SecurityOperationIgnore() {
    }

    public SecurityOperationIgnore(Map<String, Object> securities) {
        this.securities = securities;
    }

    public Map<String, Object> getSecurities() {
        return securities;
    }

    public void setSecurities(Map<String, Object> securities) {
        this.securities = securities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityOperationIgnore that = (SecurityOperationIgnore) o;
        return Objects.equals(securities, that.securities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securities);
    }

    @Override
    public String toString() {
        return "SecurityOperationIgnore{" +
                "securities=" + securities +
                '}';
    }
}
