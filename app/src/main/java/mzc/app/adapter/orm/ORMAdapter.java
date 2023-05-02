package mzc.app.adapter.orm;

import lombok.Getter;
import mzc.app.adapter.base.IMainAdapter;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

public class ORMAdapter implements IMainAdapter {

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
    private final @NotNull Session session;

    public ORMAdapter() {
        session = SessionManager.getSession();
        customer = new CustomerAdapter(session);
        bill = new BillAdapter(session);
        product = new ProductAdapter(session);
        productHistory = new ProductHistoryAdapter(session);
        fixedBill = new FixedBillAdapter(session);
    }

    @Override
    public void close() {
        SessionManager.closeSession();
    }

    @Override
    public void clearData() {
    }
}
