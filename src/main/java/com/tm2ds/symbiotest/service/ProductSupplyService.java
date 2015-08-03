package com.tm2ds.symbiotest.service;

import com.tm2ds.symbiotest.dao.ProductDAO;
import com.tm2ds.symbiotest.dao.SupplyDAO;
import com.tm2ds.symbiotest.dto.ProductSupply;

import java.util.List;

/**
 * Created by Working on 8/3/2015.
 */
public interface ProductSupplyService {

    List<ProductSupply> calculateAllSupplies();

    void setProductDAO(ProductDAO productDAO);

    void setSupplyDAO(SupplyDAO supplyDAO);
}
