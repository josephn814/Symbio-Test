package com.tm2ds.symbiotest.dao.impl;

import com.tm2ds.symbiotest.AbstractTest;
import com.tm2ds.symbiotest.dao.SupplyDAO;
import com.tm2ds.symbiotest.model.Supply;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Working on 8/3/2015.
 */
public class SupplyDAOImplTest extends AbstractTest {

    @Test
    public void testGetAllSupplies() throws Exception {
        SupplyDAO dao = new SupplyDAOImpl(getConnection());
        List<Supply> products = dao.getAllSupplies();
        Assert.assertEquals(8, products.size());
    }
}