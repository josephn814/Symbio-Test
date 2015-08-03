package com.tm2ds.symbiotest;

import com.tm2ds.symbiotest.dao.ProductDAO;
import com.tm2ds.symbiotest.dao.SupplyDAO;
import com.tm2ds.symbiotest.dao.impl.ProductDAOImpl;
import com.tm2ds.symbiotest.dao.impl.SupplyDAOImpl;
import com.tm2ds.symbiotest.dto.ProductSupply;
import com.tm2ds.symbiotest.service.ProductSupplyService;
import com.tm2ds.symbiotest.service.impl.ProductSupplyServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * Created by Working on 8/3/2015.
 */
public class MainTest extends AbstractTest {

    @Test
    public void testMain() throws Exception {
        ProductDAO productDAO = new ProductDAOImpl(getConnection());
        SupplyDAO supplyDAO = new SupplyDAOImpl(getConnection());
        ProductSupplyService productSupplyService = new ProductSupplyServiceImpl();
        productSupplyService.setProductDAO(productDAO);
        productSupplyService.setSupplyDAO(supplyDAO);
        List<ProductSupply> supplies = productSupplyService.calculateAllSupplies();
        for (ProductSupply supply : supplies) {
            System.out.println(supply.getProductName() + " " + supply.getStartDate() + " " + supply.getEndDate() + " " + supply.getAmount());
        }
    }


}
