package com.tm2ds.symbiotest.model;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by Working on 8/3/2015.
 */
public class Product {

    private String name;
    private Set<String> materials = Sets.newHashSet();

    public Product(String name) {
        this.name = name;
    }

    public void addMaterial(String material) {
        this.materials.add(material);
    }

    public String getName() {
        return this.name;
    }

    public Set<String> getMaterials() {
        return materials;
    }
}
