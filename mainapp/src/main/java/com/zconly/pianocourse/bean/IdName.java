package com.zconly.pianocourse.bean;

/**
 * id/name
 * Created by dengbin
 */
public class IdName extends BaseBean {

    private static final long serialVersionUID = 2200L;

    private long id;
    private String name;

    public IdName(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IdName) {
            return ((IdName) o).getId() == this.getId();
        }
        return false;
    }
}
