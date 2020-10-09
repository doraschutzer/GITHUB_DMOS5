package br.edu.dmos5.github_dmos5.model;

import java.io.Serializable;

public class Repository implements Serializable {
    private String name;

    public Repository(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
