package com.tm2ds.symbiotest.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.tm2ds.symbiotest.dao.ProductDAO;
import com.tm2ds.symbiotest.dao.SupplyDAO;
import com.tm2ds.symbiotest.dto.ProductSupply;
import com.tm2ds.symbiotest.model.Product;
import com.tm2ds.symbiotest.model.Supply;
import com.tm2ds.symbiotest.service.ProductSupplyService;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Set;

/**
 * Created by Working on 8/3/2015.
 */
public class ProductSupplyServiceImpl implements ProductSupplyService {

    private ProductDAO productDAO;
    private SupplyDAO supplyDAO;

    @Override
    public List<ProductSupply> calculateAllSupplies() {
        List<ProductSupply> result = Lists.newArrayList();

        List<Supply> allSupplies = getSupplyDAO().getAllSupplies();
        List<Product> allProducts = getProductDAO().getAllProducts();

        for (final Product product : allProducts) {
            ImmutableList<Supply> supplies = FluentIterable.from(allSupplies).filter(new Predicate<Supply>() {
                @Override
                public boolean apply(Supply input) {
                    return input.getName().equals(product.getName());
                }
            }).toList();

            List<Set<Supply>> calculateList = Lists.newArrayList();
            int size = product.getMaterials().size();
            for (int i = 0; i < size; i++) {
                calculateList.add(Sets.newHashSet(supplies));
            }
            Set<List<Supply>> cartesianProduct = Sets.cartesianProduct(calculateList);
            ImmutableSet<ProductSupply> productSupplies = FluentIterable.from(cartesianProduct).transform(new Function<List<Supply>, ProductSupply>() {
                @Override
                public ProductSupply apply(List<Supply> input) {
                    ProductSupply productSupply = new ProductSupply();
                    boolean first = true;
                    for (Supply supply : input) {
                        if (first) {
                            productSupply.setProductName(supply.getName());
                            productSupply.setStartDate(supply.getStartDate());
                            productSupply.setEndDate(supply.getEndDate());
                            productSupply.setAmount(supply.getAmount());
                            productSupply.addMaterial(supply.getMaterial());
                            first = false;
                        }
                        DateTime productSupplyStartDate = new DateTime(productSupply.getStartDate());
                        DateTime productSupplyEndDate = new DateTime(productSupply.getEndDate());

                        DateTime supplyStartDate = new DateTime(supply.getStartDate());
                        DateTime supplyEndDate = new DateTime(supply.getEndDate());

                        if (productSupplyStartDate.isAfter(supplyEndDate) ||
                                productSupplyStartDate.isEqual(supplyEndDate) ||
                                productSupplyEndDate.isBefore(supplyStartDate) ||
                                productSupplyEndDate.isEqual(supplyStartDate)) {
                            continue;
                        }
                        if (supplyStartDate.isAfter(productSupplyStartDate)) {
                            productSupply.setStartDate(supply.getStartDate());
                        }
                        if (supplyEndDate.isBefore(productSupplyEndDate)) {
                            productSupply.setEndDate(supply.getEndDate());
                        }
                        if (supply.getAmount() < productSupply.getAmount()) {
                            productSupply.setAmount(supply.getAmount());
                        }
                        productSupply.addMaterial(supply.getMaterial());
                    }
                    return productSupply;
                }
            }).filter(new Predicate<ProductSupply>() {
                @Override
                public boolean apply(ProductSupply input) {
                    Set<String> materials = product.getMaterials();
                    return input.getMaterials().containsAll(materials);
                }
            }).toSet();
            result.addAll(productSupplies);
        }

        return result;
    }

    public ProductDAO getProductDAO() {
        Preconditions.checkNotNull(productDAO);
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    public SupplyDAO getSupplyDAO() {
        Preconditions.checkNotNull(supplyDAO);
        return supplyDAO;
    }

    public void setSupplyDAO(SupplyDAO supplyDAO) {
        this.supplyDAO = supplyDAO;
    }
}
