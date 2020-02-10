package org.openapi.diff.ignore.models.ignore;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SecurityIgnore {
    private List<Map<String, Object>> securityOperationIgnoreList;

    public SecurityIgnore() {
    }

    public SecurityIgnore(List<Map<String, Object>> securities) {
        this.securityOperationIgnoreList = securities;
    }

    public List<Map<String, Object>> getSecurities() {
        return securityOperationIgnoreList;
    }

    public void setSecurities(List<Map<String, Object>> securities) {
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
