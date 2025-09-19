package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable {    // ← add this
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() { return name; }
    public String getProvince() { return province; }

    // (optional) private static final long serialVersionUID = 1L;
}
