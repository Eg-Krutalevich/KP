package tables;

import java.sql.ResultSet;

public interface ResultFromTable {
    ResultSet getResultFromTable(String table);
}
