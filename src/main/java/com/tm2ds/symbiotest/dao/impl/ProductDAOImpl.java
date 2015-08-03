package com.tm2ds.symbiotest.dao.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tm2ds.symbiotest.dao.ProductDAO;
import com.tm2ds.symbiotest.model.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Working on 8/3/2015.
 */
public class ProductDAOImpl implements ProductDAO {

    private Connection con;

    public ProductDAOImpl(Connection con) {
        Preconditions.checkNotNull(con, "Connection is null.");
        this.con = con;
    }

    @Override
    public List<Product> getAllProducts() {
        QueryRunner runner = new QueryRunner();
        List<Product> result;
        try {
            result = runner.query(con, "select NAME, MATERIAL from product", new ResultSetHandler<List<Product>>() {
                @Override
                public List<Product> handle(ResultSet rs) throws SQLException {
                    if (!rs.next()) {
                        return Collections.emptyList();
                    }
                    Map<String, Product> cache = Maps.newHashMap();

                    do {
                        String name = rs.getString("name");
                        String material = rs.getString("material");

                        Product product = cache.get(name);
                        if (product == null) {
                            product = new Product(name);
                            cache.put(name, product);
                        }

                        product.addMaterial(material);


                    } while (rs.next());

                    return Lists.newArrayList(cache.values());
                }
            });
        } catch (SQLException e) {
            //ignore
            result = Collections.emptyList();
        }
        return result;
    }
}
