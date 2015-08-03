package com.tm2ds.symbiotest.dao.impl;

import com.google.common.collect.Sets;
import com.tm2ds.symbiotest.AbstractTest;
import com.tm2ds.symbiotest.dao.ProductDAO;
import com.tm2ds.symbiotest.model.Product;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Working on 8/3/2015.
 */
public class ProductDAOImplTest extends AbstractTest{

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDAO dao = new ProductDAOImpl(getConnection());
        List<Product> products = dao.getAllProducts();
        Assert.assertEquals(2, products.size());
        for (Product product : products) {
            if ("98100201".equals(product.getName())) {
                Assert.assertEquals(2, product.getMaterials().size());
            }
            if ("98102601".equals(product.getName())) {
                Assert.assertEquals(3, product.getMaterials().size());
            }
        }
    }

}