package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AirportTable extends DataTable implements ResultFromTable {

    public ResultSet getResultFromTable(String table) {

        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getAirportWithID(String id) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM airport WHERE id_airport = " + Integer.parseInt(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }


    public String getAllAirport() {
        String ClientData = null;

        String id = null;
        String name = null;
        String country = null;
        String city = null;
        String place;
        String finalData = "";

        try {
            ResultSet rs2 = this.getResultFromTable(Const.AIRPORT_TABLE);

            ClientData = "";
            while (rs2.next()) {
                id = rs2.getString(Const.AIRPORT_ID);
                ClientData += id + ";";
            }
            rs2.close();

            rs2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String[] ClientIdArray = ClientData.split(";");

            for (int i = 0; i < ClientIdArray.length; ++i) {
                ResultSet rs1 = this.getAirportWithID(ClientIdArray[i]);
                rs1.next();

                name = rs1.getString(Const.AIRPORT_NAME);
                country = rs1.getString(Const.AIRPORT_COUNTRY);
                city = rs1.getString(Const.AIRPORT_CITY);
                place = rs1.getString(Const.AIRPORT_PLACE);

                rs1.close();

                finalData += ClientIdArray[i] + "," + name + "," + country + "," + city + "," + place + ";";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalData;
    }

    public void addAirport(String name, String country, String city, String place) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.AIRPORT_TABLE + "(" + Const.AIRPORT_NAME + "," + Const.AIRPORT_COUNTRY + "," + Const.AIRPORT_CITY + "," + Const.AIRPORT_PLACE + ")" + "VALUES (" + this.quotate(name) + "," + this.quotate(country) + "," + this.quotate(city) + "," + this.quotate(place) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String quotate(String content) {
        return "'" + content + "'";
    }

    public AirportTable(Statement stmt) {
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
