package com.tm2ds.symbiotest;

import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Working on 8/3/2015.
 */
public abstract class AbstractTest {

    private Connection con;

    @Before
    public void setUp() throws Exception {
        DBHelper helper = DBHelper.getHelper();
        helper.validateData();
        this.con = helper.getConnection();
    }

    public Connection getConnection() {
        return con;
    }
}
