package org.openapi.diff.ignore.models.ignore;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SecurityIgnore {
    private Map<String, List<String>> securityOperationIgnoreList;

    public SecurityIgnore() {
    }

    public SecurityIgnore(Map<String, List<String>> securities) {
        this.securityOperationIgnoreList = securities;
    }

    public Map<String, List<String>> getSecurities() {
        return securityOperationIgnoreList;
    }

    public void setSecurities(Map<String, List<String>> securities) {
        this.securityOperationIgnoreList = securities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityIgnore that = (SecurityIgnore) o;
        return Objects.equals(securityOperationIgnoreList, that.securityOperationIgnoreList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityOperationIgnoreList);
    }

    @Override
    public String toString() {
        return "SecurityOperationIgnore{" +
                "securities=" + securityOperationIgnoreList +
                '}';
    }
}
