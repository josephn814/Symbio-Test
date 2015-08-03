package com.tm2ds.symbiotest.dao.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tm2ds.symbiotest.dao.ProductDAO;
import com.tm2ds.symbiotest.dao.SupplyDAO;
import com.tm2ds.symbiotest.model.Product;
import com.tm2ds.symbiotest.model.Supply;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Working on 8/3/2015.
 */
public class SupplyDAOImpl implements SupplyDAO {

    private Connection con;

    public SupplyDAOImpl(Connection con) {
        Preconditions.checkNotNull(con, "Connection is null.");
        this.con = con;
    }

    @Override
    public List<Supply> getAllSupplies() {
        QueryRunner runner = new QueryRunner();
        List<Supply> result;
        try {
            result = runner.query(con, "SELECT p.NAME, p.MATERIAL, s.START_DATE, s.END_DATE, (s.AMOUNT / p.AMOUNT) AS amount FROM PRODUCT p JOIN SUPPLY s ON p.MATERIAL = s.MATERIAL;", new ResultSetHandler<List<Supply>>() {
                @Override
                public List<Supply> handle(ResultSet rs) throws SQLException {
                    if (!rs.next()) {
                        return Collections.emptyList();
                    }

                    List<Supply> result = Lists.newArrayList();

                    do {
                        String name = rs.getString("NAME");
                        String material = rs.getString("material");
                        Date startDate = rs.getDate("START_DATE");
                        Date endDate = rs.getDate("END_DATE");
                        Integer amount = rs.getInt("amount");

                        Supply supply = new Supply();
                        result.add(supply);

                        supply.setName(name);
                        supply.setMaterial(material);
                        supply.setStartDate(startDate);
                        supply.setEndDate(endDate);
                        supply.setAmount(amount);

                    } while (rs.next());

                    return result;
                }
            });
        } catch (SQLException e) {
            //ignore
            result = Collections.emptyList();
        }
        return result;
    }

}
