package org.openapi.diff.ignore.models.ignore;

import java.util.Map;
import java.util.Objects;

public class SecurityIgnore {
    private Map<String, SecurityOperationIgnore> securityOperationIgnoreList;

    public SecurityIgnore() {
    }

    public Map<String, SecurityOperationIgnore> getSecurities() {
        return securityOperationIgnoreList;
    }

    public void setSecurities(Map<String, SecurityOperationIgnore> securities) {
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
