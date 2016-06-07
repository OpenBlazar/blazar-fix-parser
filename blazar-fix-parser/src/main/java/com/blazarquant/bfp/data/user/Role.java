package com.blazarquant.bfp.data.user;

import java.util.Objects;

/**
 * @author Wojciech Zankowski
 */
public class Role {

    public static final Role USER_ROLE = new Role("User");
    public static final Role ADMIN_ROLE = new Role("Admin");

    private final String name;

    public Role(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return getName() != null ? getName().equals(role.getName()) : role.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
