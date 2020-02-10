package org.openapi.diff.ignore.models.ignore;

import java.util.List;
import java.util.Objects;

public class SecurityIgnore {
    private List<SecurityOperationIgnore> securityOperationIgnoreList;

    public SecurityIgnore() {
    }

    public SecurityIgnore(List<SecurityOperationIgnore> securities) {
        this.securityOperationIgnoreList = securities;
    }

    public List<SecurityOperationIgnore> getSecurities() {
        return securityOperationIgnoreList;
    }

    public void setSecurities(List<SecurityOperationIgnore> securities) {
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
