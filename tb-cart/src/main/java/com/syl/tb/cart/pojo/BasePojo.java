package com.syl.tb.cart.pojo;

import java.util.Date;

public abstract class BasePojo {
    protected Date created;

    protected Date updated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
