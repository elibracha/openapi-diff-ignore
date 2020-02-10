package org.openapi.diff.ignore.models.ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecurityOperationIgnore {

    private List<String> securities;

    public SecurityOperationIgnore() {
        this.securities = new ArrayList<>();
    }

    public List<String> getSecurities() {
        return securities;
    }

    public void setSecurities(List<String> securities) {
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
