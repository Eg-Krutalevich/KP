package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForbiddenStuffTable extends DataTable implements ResultFromTable {
    public ResultSet getResultFromTable(String table) {

        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getStuffWithID(String id) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM stuff WHERE id_stuff = " + Integer.parseInt(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }


    public String getAllStuff() {
        String ClientData = null;

        String id = null;
        String stuff_object = null;
        String punishment = null;
        String finalData = "";

        try {
            ResultSet rs2 = this.getResultFromTable(Const.STUFF_TABLE);

            ClientData = "";
            while (rs2.next()) {
                id = rs2.getString(Const.STUFF_ID);
                ClientData += id + ";";
            }
            rs2.close();

            rs2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String[] AdminIdArray = ClientData.split(";");

            for (int i = 0; i < AdminIdArray.length; ++i) {
                ResultSet rs1 = this.getStuffWithID(AdminIdArray[i]);
                rs1.next();

                stuff_object = rs1.getString(Const.STUFF_STUFF_OBJECT);
                punishment = rs1.getString(Const.STUFF_PUNISHMENT);

                rs1.close();

                finalData += AdminIdArray[i] + "," + stuff_object + "," + punishment + ";";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalData;
    }

    public void addStuff(String stuff, String punishment) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.STUFF_TABLE + "(" + Const.STUFF_STUFF_OBJECT + "," + Const.STUFF_PUNISHMENT + ")" + "VALUES (" + this.quotate(stuff) + "," + this.quotate(punishment) + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String quotate(String content) {
        return "'" + content + "'";
    }

    public ForbiddenStuffTable(Statement stmt) {
        init(stmt);
    }

    @Override
    public void initStmt(Statement stmt) {
        this.stmt = stmt;
    }

    @Override
    public void initDatabaseHandler() {
        this.mdbc = DatabaseHandler.getInstance();
    }
}
