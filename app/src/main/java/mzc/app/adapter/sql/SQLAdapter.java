package mzc.app.adapter.sql;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import mzc.app.bootstrap.AppManager;
import mzc.app.utils.FileManager;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class SQLAdapter implements IMainAdapter {
    @Getter
    private final @NotNull CustomerAdapter customer;

    @Getter
    private final @NotNull BillAdapter bill;
    @Getter
    private final @NotNull ProductAdapter product;
    @Getter
    private final @NotNull ProductHistoryAdapter productHistory;
    @Getter
    private final @NotNull FixedBillAdapter fixedBill;

    @Getter
    private final @NotNull ProductBillAdapter productBill;

    private final @NotNull HikariDataSource ds;

    public SQLAdapter() {
        ds = new HikariDataSource();
        ds.setJdbcUrl(AppManager.get().getAppSetting().getSqlRawDatabaseUrl());
        try (var con = ds.getConnection()) {
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader("schema.sql"));
            sr.runScript(reader);
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.product = new ProductAdapter(ds);
        this.productBill = new ProductBillAdapter(ds, this.product);
        this.bill = new BillAdapter(ds, this.productBill);
        this.customer = new CustomerAdapter(ds, bill);
        this.bill.setCustomerAdapter(this.customer);
        this.productHistory = new ProductHistoryAdapter(ds);
        this.fixedBill = new FixedBillAdapter(ds, productHistory);
    }

    @Override
    public void close() {

    }

    @Override
    public void clearData() {
        try {
            String[] tables = {"customer", "bill", "product", "producthistory", "fixedbill", "productbill"};
            for (String table : tables) {
                var statement = ds.getConnection().createStatement();
                statement.execute("DELETE FROM " + table);
                statement.execute("ALTER TABLE " + table + " AUTO_INCREMENT = 1");
                statement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
