package tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class FAQTable extends DataTable implements ResultFromTable {
    public ResultSet getResultFromTable(String table) {

        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFAQWithID(String id) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM faq WHERE id_faq = " + Integer.parseInt(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public String getAllFAQ() throws SQLException {
        String ClientData = null;
        String finalData = "";

        String id = null;
        String question = null;
        String answer = null;

        try {
            ResultSet rs2 = this.getResultFromTable(Const.FAQ_TABLE);

            ClientData = "";
            while (rs2.next()) {
                id = rs2.getString(Const.FAQ_ID);
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
                ResultSet rs1 = this.getFAQWithID(AdminIdArray[i]);
                rs1.next();

                question = rs1.getString(Const.FAQ_QUESTION);
                answer = rs1.getString(Const.FAQ_ANSWER);

                rs1.close();

                finalData += AdminIdArray[i] + "," + question + "," + answer + ";";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalData;
    }

    public void addFAQ(String question, String answer) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.FAQ_TABLE + "(" + Const.FAQ_QUESTION + "," + Const.FAQ_ANSWER + ")" + "VALUES (" + this.quotate(question) + "," + this.quotate(answer) + ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String quotate(String content) {
        return "'" + content + "'";
    }

    public FAQTable(Statement stmt) {
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
