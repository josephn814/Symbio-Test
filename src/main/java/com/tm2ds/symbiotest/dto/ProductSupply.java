package com.tm2ds.symbiotest.dto;

import com.google.common.collect.Sets;

import java.util.Date;
import java.util.Set;

/**
 * Created by Working on 8/3/2015.
 */
public class ProductSupply {

    private String productName;
    private Date startDate;
    private Date endDate;
    private Integer amount;
    private Set<String> materials = Sets.newHashSet();

    public String getProductName() {
        return productName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public void addMaterial(String material) {
        this.materials.add(material);
    }

    public Set<String> getMaterials() {
        return materials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSupply that = (ProductSupply) o;

        if (!getProductName().equals(that.getProductName())) return false;
        if (!getStartDate().equals(that.getStartDate())) return false;
        if (!getEndDate().equals(that.getEndDate())) return false;
        if (!getAmount().equals(that.getAmount())) return false;
        return getMaterials().equals(that.getMaterials());

    }

    @Override
    public int hashCode() {
        int result = getProductName().hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        result = 31 * result + getAmount().hashCode();
        result = 31 * result + getMaterials().hashCode();
        return result;
    }
}
