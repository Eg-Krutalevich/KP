package tables;


import java.sql.Statement;

public abstract class DataTable {
    protected Statement stmt;
    protected DatabaseHandler mdbc;

    public abstract String quotate(String content);

    public void init(Statement stmt) {
        initStmt(stmt);
        initDatabaseHandler();
    }

    abstract public void initStmt(Statement stmt);

    abstract public void initDatabaseHandler();


}
