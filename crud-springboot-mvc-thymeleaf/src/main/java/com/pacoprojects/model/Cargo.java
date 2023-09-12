package com.pacoprojects.model;

public enum Cargo {

    JUNIOR("Junior"),
    PLENO("Pleno"),
    SENIOR("Senior");

   private String key;
   private String value;

    Cargo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return this.name();
    }
}
