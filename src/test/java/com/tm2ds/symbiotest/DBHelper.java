package com.tm2ds.symbiotest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Working on 8/3/2015.
 */
public class DBHelper {

    private static DBHelper INSTANCE = new DBHelper();

    private DBHelper() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:mem:test", "sa", "");
            initDatabase();
            initData();
            validateData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection con;

    private void initDatabase() throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(con, "CREATE TABLE product\n" +
                "(\n" +
                "  ID INT PRIMARY KEY NOT NULL IDENTITY,\n" +
                "  NAME VARCHAR(200) NOT NULL,\n" +
                "  MATERIAL VARCHAR(200) NOT NULL,\n" +
                "  AMOUNT INT NOT NULL\n" +
                ");");
        runner.update(con, "CREATE TABLE supply\n" +
                "(\n" +
                "  ID INT PRIMARY KEY NOT NULL IDENTITY,\n" +
                "  MATERIAL VARCHAR(200) NOT NULL,\n" +
                "  START_DATE DATE NOT NULL,\n" +
                "  END_DATE DATE NOT NULL,\n" +
                "  AMOUNT INT NOT NULL\n" +
                ");");
        con.commit();
    }

    private void initData() throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(con, "INSERT INTO PRODUCT(NAME,MATERIAL,AMOUNT) VALUES ('98100201','RAW_ROSE_005',14)");
        runner.update(con, "INSERT INTO PRODUCT(NAME,MATERIAL,AMOUNT) VALUES ('98100201','CAPACITY',1)");
        runner.update(con, "INSERT INTO PRODUCT(NAME,MATERIAL,AMOUNT) VALUES ('98102601','RAW_EUCALYPTUS_001',4)");
        runner.update(con, "INSERT INTO PRODUCT(NAME,MATERIAL,AMOUNT) VALUES ('98102601','RAW_ROSE_005',12)");
        runner.update(con, "INSERT INTO PRODUCT(NAME,MATERIAL,AMOUNT) VALUES ('98102601','CAPACITY',1)");
        runner.update(con, "INSERT INTO SUPPLY(MATERIAL, START_DATE, END_DATE, AMOUNT) VALUES ('RAW_EUCALYPTUS_001', '2014-02-04', '2014-11-30', 6000)");
        runner.update(con, "INSERT INTO SUPPLY(MATERIAL, START_DATE, END_DATE, AMOUNT) VALUES ('RAW_EUCALYPTUS_001', '2015-02-01', '2038-01-19', 6000)");
        runner.update(con, "INSERT INTO SUPPLY(MATERIAL, START_DATE, END_DATE, AMOUNT) VALUES ('RAW_ROSE_005', '2014-10-01', '2014-10-31', 18)");
        runner.update(con, "INSERT INTO SUPPLY(MATERIAL, START_DATE, END_DATE, AMOUNT) VALUES ('RAW_ROSE_005', '2015-01-01', '2015-01-31', 666)");
        runner.update(con, "INSERT INTO SUPPLY(MATERIAL, START_DATE, END_DATE, AMOUNT) VALUES ('CAPACITY', '2014-02-04', '2015-01-15', 999)");
        con.commit();
    }

    public void validateData() throws SQLException {
        QueryRunner runner = new QueryRunner();
        ResultSetHandler<Integer> convertor = new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet rs) throws SQLException {
                if (!rs.next()) {
                    return null;
                }
                return rs.getInt(1);
            }
        };
        Integer productCount = runner.query(con, "select count(*) from product", convertor);
        Assert.assertEquals(Integer.valueOf(5), productCount);
        Integer supplyCount = runner.query(con, "select count(*) from supply", convertor);
        Assert.assertEquals(Integer.valueOf(5), supplyCount);
    }

    public Connection getConnection(){
        return this.con;
    }

    public static DBHelper getHelper(){
        return INSTANCE;
    }


}
