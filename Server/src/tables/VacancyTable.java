package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VacancyTable extends DataTable implements ResultFromTable {

    public ResultSet getResultFromTable(String table) {

        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getVacancyWithID(String id) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM vacancy WHERE id_vacancy = " + Integer.parseInt(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }


    public String getAllVacancy() {
        String AdminData = null;

        String id = null;
        String surname = null;
        String name = null;
        String post = null;
        String salary = null;
        String finalData = "";

        try {
            ResultSet rs2 = this.getResultFromTable(Const.VACANCY_TABLE);

            AdminData = "";
            while (rs2.next()) {
                id = rs2.getString(Const.VACANCY_ID);
                AdminData += id + ";";
            }
            rs2.close();

            rs2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String[] AdminIdArray = AdminData.split(";");

            for (int i = 0; i < AdminIdArray.length; ++i) {
                ResultSet rs1 = this.getVacancyWithID(AdminIdArray[i]);
                rs1.next();

                surname = rs1.getString(Const.VACANCY_SURNAME);
                name = rs1.getString(Const.VACANCY_NAME);
                post = rs1.getString(Const.VACANCY_POST);
                salary = rs1.getString(Const.VACANCY_SALARY);

                rs1.close();

                finalData += AdminIdArray[i] + "," + surname + "," + name + "," + post + "," + salary + ";";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalData;
    }

    public void addVacancy(String surname, String name, String post, String salary) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.VACANCY_TABLE + "(" + Const.VACANCY_SURNAME + "," + Const.VACANCY_NAME + "," + Const.VACANCY_POST + "," + Const.VACANCY_SALARY + ")" + "VALUES (" + this.quotate(surname) + "," + this.quotate(name) + "," + this.quotate(post) + "," + this.quotate(salary) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String quotate(String content) {
        return "'" + content + "'";
    }

    public VacancyTable(Statement stmt) {
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
