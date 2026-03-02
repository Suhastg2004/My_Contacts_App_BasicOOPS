package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.Objects;

public class Tag {

    private String label;

    public Tag(String label) {
        this.label = label.trim().toLowerCase();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag t = (Tag) o;
        return label.equals(t.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return label;
    }
}