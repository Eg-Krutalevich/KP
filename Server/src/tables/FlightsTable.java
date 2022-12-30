package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class FlightsTable extends DataTable implements ResultFromTable {

    public ResultSet getResultFromTable(String table) {

        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void saveTicketToFile(String airport, String date, String outtime, String intime, String fullprice) {
        String text = "Аэропорт прибытия: " + airport + " \r\n";
        saveTicketData(false, text);
        String text1 = "Дата вылета: " + date + " \r\n";
        saveTicketData(true, text1);
        String text2 = "Время вылета: " + outtime + " \r\n";
        saveTicketData(true, text2);
        String text3 = "Время прибытия: " + intime + " \r\n";
        saveTicketData(true, text3);
        String text4 = "Цена: " + fullprice;
        saveTicketData(true, text4);
    }

    public void saveTicketData(Boolean bool, String text) {
        try (FileOutputStream fos = new FileOutputStream("D://upload//KP//ticketAirport.txt", bool)) {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveServerLog(Boolean bool, String text) {
        try (FileOutputStream fos = new FileOutputStream("D://upload//KP//serverLog.txt", bool)) {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUserData(String idud) {
        int IDUD = Integer.parseInt(idud);
        try {
            stmt.executeUpdate("DELETE FROM " + Const.USERDATA_TABLE + " WHERE (" + Const.USERDATA_IDUD + " = '" + IDUD + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFl(String airport, String date, String outtime) {
        String idmd = getidmd(airport, date, outtime);
        try {
            stmt.executeUpdate("DELETE FROM " + Const.FLIGHT_TABLE + " WHERE (" + Const.FL_IDMD + " LIKE '" + idmd + "');");
            stmt.executeUpdate("DELETE FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_IDMD + " = '" + idmd + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addInfoToUserData(String age, String laggage, String userName) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.USERDATA_TABLE + "(" + Const.USERDATA_IDU + "," + Const.USERDATA_A + "," + Const.USERDATA_l + ")" + "VALUES (" + getIdUser(userName) + "," + this.quotate(age) + "," + this.quotate(laggage) + ");");
            String lprice = addLaPrice(laggage);
            stmt.executeUpdate("INSERT INTO " + Const.LA_TABLE + "(" + Const.LA_IDUD + "," + Const.LA_PRICE + ")" + "VALUES (" + getIDUD(getIdUser(userName)) + "," + this.quotate(lprice) + ");");
            String asale = addSale(age);
            stmt.executeUpdate("INSERT INTO " + Const.A_TABLE + "(" + Const.A_IDUD + "," + Const.A_SALE + ")" + "VALUES (" + getIDUD(getIdUser(userName)) + "," + this.quotate(asale) + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFl(String airport, String date, String outtime, String intime, String price, String seatsamount) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.MAINDATA_TABLE + "(" + Const.MAINDATA_AIRPORT + "," + Const.MAINDATA_DATE + "," + Const.MAINDATA_OUTTIME + "," + Const.MAINDATA_INTIME + ")" + "VALUES (" + this.quotate(airport) + "," + this.quotate(date) + "," + this.quotate(outtime) + "," + this.quotate(intime) + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String idmd = getidmd(airport, date, outtime);

        try {
            stmt.executeUpdate("INSERT INTO " + Const.FLIGHT_TABLE + "(" + Const.FL_PRICE + "," + Const.FL_IDMD + "," + Const.FL_SEATS + ")" + "VALUES (" + this.quotate(price) + "," + this.quotate(idmd) + "," + this.quotate(seatsamount) + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getidmd(String airport, String date, String outtime) {
        ResultSet resultSet;
        String idmd = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.MAINDATA_IDMD + " FROM " + Const.MAINDATA_TABLE + " WHERE (" + Const.MAINDATA_AIRPORT + " LIKE '" + airport + "' AND " + Const.MAINDATA_DATE + " LIKE '" + date + "' AND " + Const.MAINDATA_OUTTIME + " LIKE '" + outtime + "');");
            while (resultSet.next()) idmd = resultSet.getString(Const.MAINDATA_IDMD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idmd;
    }

    public void addInfoToTicket(String idud, String fullPrice) {
        String idu = getIdU(idud);
        try {
            stmt.executeUpdate("INSERT INTO " + Const.TICKET_TABLE + "(" + Const.TICKET_IDUS + "," + Const.TICKET_FULLPRICE + ")" + "VALUES (" + this.quotate(idu) + "," + this.quotate(fullPrice) + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getClientName(String idud) {
        String idu = getIdU(idud);
        String name = getClName(idu);
        String surname = getClSurname(idu);
        String ns = name + "," + surname;
        return ns;
    }

    public String getClName(String idu) {
        ResultSet resultSet;
        String name = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.CLIENT_NAME + " FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENT_ID + " LIKE '" + idu + "';");
            while (resultSet.next()) name = resultSet.getString(Const.CLIENT_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getClSurname(String idu) {
        ResultSet resultSet;
        String surname = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.CLIENT_SURNAME + " FROM " + Const.CLIENT_TABLE + " WHERE " + Const.CLIENT_ID + " LIKE '" + idu + "';");
            while (resultSet.next()) surname = resultSet.getString(Const.CLIENT_SURNAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surname;
    }

    public String getIdU(String idud) {
        ResultSet resultSet;
        String id = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDU + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next()) id = resultSet.getString(Const.USERDATA_IDU);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void addIDFtoUD(String id, String username) {
        int idu = getIdUser(username);
        try {
            stmt.executeUpdate("UPDATE " + Const.USERDATA_TABLE + " SET " + Const.USERDATA_IDF + " = '" + id + "' WHERE (" + Const.USERDATA_IDU + " = '" + idu + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addSale(String age) {
        String sale = "";
        switch (age) {
            case "Взpослые(16+ лет)":
                sale = "0";
                break;
            case "Подpостки(12-15 лет)":
                sale = "20";
                break;
            case "Дети(2-14 лет)":
                sale = "50";
                break;
            case "Младенцы(0-2 года)":
                sale = "100";
                break;
        }
        return sale;
    }

    public String addLaPrice(String laggage) {
        String price = "";
        switch (laggage) {
            case "Pучная кладь":
                price = "0";
                break;
            case "Дополнительная pучная кладь":
                price = "10";
                break;
            case "Основной багаж":
                price = "20";
                break;
            case "Дополнительный основной багаж":
                price = "30";
                break;
            case "Кpупногабаритный багаж":
                price = "40";
                break;
        }
        return price;
    }

    public int getIdUser(String username) {
        ResultSet resultSet;
        int id = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERS_ID + " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + " LIKE '" + username + "';");
            while (resultSet.next()) id = resultSet.getInt(Const.USERS_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getIDUserD(String idf) {
        ResultSet resultSet;
        String idU = "";

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDUD + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDF + " = '" + idf + "';");
            while (resultSet.next())
                idU = resultSet.getString(Const.USERDATA_IDUD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idU;
    }

    public int getIDUD(int idu) {
        ResultSet resultSet;
        int idU = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.USERDATA_IDUD + " FROM " + Const.USERDATA_TABLE + " WHERE " + Const.USERDATA_IDU + " = '" + idu + "';");
            while (resultSet.next()) idU = resultSet.getInt(Const.USERDATA_IDUD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idU;
    }

    public String sendData(String data) {
        return data;
    }

    public String senddata(String data, String data1, String data2) {
        return data + "," + data1 + "," + data2;
    }

    public int getIDmd(String idf) {
        ResultSet resultSet;
        int idmd = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_IDMD + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " LIKE '" + idf + "';");
            while (resultSet.next()) idmd = resultSet.getInt(Const.FL_IDMD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveServerLog(true, "idf = " + idf + " \r\n");
        saveServerLog(true, "idmd = " + idmd + " \r\n");
        return idmd;
    }

    public int getLaggagePrice(String idud) {
        ResultSet resultSet;
        int lprice = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.LA_PRICE + " FROM " + Const.LA_TABLE + " WHERE " + Const.LA_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next()) lprice = resultSet.getInt(Const.LA_PRICE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveServerLog(true, "lprice = " + lprice + " \r\n");
        return lprice;
    }

    public int getSale(String idud) {
        ResultSet resultSet;
        int sale = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.A_SALE + " FROM " + Const.A_TABLE + " WHERE " + Const.A_IDUD + " LIKE '" + idud + "';");
            while (resultSet.next()) sale = resultSet.getInt(Const.A_SALE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveServerLog(true, "sale = " + sale + " \r\n");
        return sale;
    }

    public int getFlPrice(String idf) {
        ResultSet resultSet;
        int price = 0;

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_PRICE + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " LIKE '" + idf + "';");
            while (resultSet.next()) price = resultSet.getInt(Const.FL_PRICE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveServerLog(true, "flprice = " + price + " \r\n");
        return price;
    }

    public String CalculateFullPrice(String idud, String idf) {
        String fullprice = "";
        int laPrice = getLaggagePrice(idud);
        int flPrice = getFlPrice(idf);
        int sale = getSale(idud);
        int full = flPrice - flPrice * sale / 100 + laPrice;

        saveServerLog(true, "full price = " + full + " \r\n");
        fullprice = String.valueOf(full);
        return fullprice;
    }

    public String getTicketInfo(String idf) {
        String idmd = String.valueOf(getIDmd(idf));
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        try {
            while (rs.next()) {
                String idMD = rs.getString(Const.MAINDATA_IDMD);
                if (idmd.equals(idMD)) {

                    airport = rs.getString(Const.MAINDATA_AIRPORT);
                    date = rs.getString(Const.MAINDATA_DATE);
                    outtime = rs.getString(Const.MAINDATA_OUTTIME);
                    intime = rs.getString(Const.MAINDATA_INTIME);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String tickInfo = airport + "," + date + "," + outtime + "," + intime;

        saveServerLog(true, tickInfo + " \r\n");
        return tickInfo;
    }

    public String getAvFl(String id, String count) {
        String[] messParts = id.split(";");
        for (int j = 0; j < Integer.parseInt(count); j++) saveServerLog(true, "id-flight " + messParts[j] + " \r\n");

        String AvFlData = "";
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        String price = null;
        String seats = null;
        String idfl = null;

        for (int i = 0; i < Integer.parseInt(count); i++) {
            ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
            try {
                while (rs.next()) {
                    String idmd = rs.getString(Const.MAINDATA_IDMD);

                    if (idmd.equals(messParts[i])) {
                        airport = rs.getString(Const.MAINDATA_AIRPORT);
                        date = rs.getString(Const.MAINDATA_DATE);
                        outtime = rs.getString(Const.MAINDATA_OUTTIME);
                        intime = rs.getString(Const.MAINDATA_INTIME);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet rs1 = this.getResultFromTable(Const.FLIGHT_TABLE);
            try {
                while (rs1.next()) {
                    String idMD = rs1.getString(Const.FL_IDMD);

                    if (idMD.equals(messParts[i])) {
                        price = rs1.getString(Const.FL_PRICE);
                        seats = rs1.getString(Const.FL_SEATS);
                        idfl = rs1.getString(Const.FL_ID);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            AvFlData += idfl + "," + airport + "," + date + "," + outtime + "," + intime + "," + seats + "," + price + ";";
            saveServerLog(true, "Getting information about Flight: " + AvFlData + " \r\n");
        }

        return AvFlData;
    }

    public int getSeatsAmount(String id) {
        ResultSet resultSet;
        int seats = 0;
        int ID = Integer.parseInt(id);

        try {
            resultSet = stmt.executeQuery("SELECT " + Const.FL_SEATS + " FROM " + Const.FLIGHT_TABLE + " WHERE " + Const.FL_ID + " = '" + ID + "';");
            while (resultSet.next()) seats = resultSet.getInt(Const.FL_SEATS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void CountFreeSeatsAmount(String id) {
        int seats = getSeatsAmount(id) - 1;
        try {
            stmt.executeUpdate("UPDATE " + Const.FLIGHT_TABLE + " SET " + Const.FL_SEATS + " = '" + seats + "' WHERE (" + Const.FL_ID + " LIKE '" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String сheckAvFl(String airport, String date) {
        String hasAvFl = "fail";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int i = 0;
        try {
            while (rs.next()) {
                String Airport = rs.getString(Const.MAINDATA_AIRPORT);
                String Date = rs.getString(Const.MAINDATA_DATE);

                if (Airport.equals(airport) && Date.equals(date)) i++;
            }
            if (i > 0) hasAvFl = "success";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveServerLog(true, "Checking flight availability: " + hasAvFl + " \r\n");
        return hasAvFl;
    }

    public String getAvFlID(String airport, String date) {
        String mesToCl = "";
        String mesToCl1 = "";
        ResultSet rs = this.getResultFromTable(Const.MAINDATA_TABLE);
        int count = 0;
        try {
            while (rs.next()) {
                String Airport = rs.getString(Const.MAINDATA_AIRPORT);
                String Date = rs.getString(Const.MAINDATA_DATE);

                if (Airport.equals(airport) && Date.equals(date)) {
                    String ID = rs.getString(Const.MAINDATA_IDMD);

                    mesToCl += ID + ";";
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mesToCl1 = String.valueOf(count) + "," + mesToCl;

        saveServerLog(true, "id-flights " + mesToCl1 + " \r\n");
        return mesToCl1;
    }

    public String getAllFlights() throws SQLException {
        String FlID = null;
        String FlData = null;
        String FlDataInfo = null;
        String airport = null;
        String date = null;
        String outtime = null;
        String intime = null;
        String price = null;
        String seats = null;
        String idfl = null;

        try {
            ResultSet rs2 = this.getResultFromTable(Const.FLIGHT_TABLE);

            FlID = "";
            while (rs2.next()) {
                idfl = rs2.getString(Const.FL_ID);
                FlID += idfl + ",;";
            }
            rs2.close();

            FlData = "";
            ResultSet resultSet = this.getResultFromTable(Const.MAINDATA_TABLE);
            while (resultSet.next()) {
                airport = resultSet.getString(Const.MAINDATA_AIRPORT);
                date = resultSet.getString(Const.MAINDATA_DATE);
                outtime = resultSet.getString(Const.MAINDATA_OUTTIME);
                intime = resultSet.getString(Const.MAINDATA_INTIME);
                //FlData += idfl + "," + airport + "," + date + "," + outtime + "," + intime + "," + seats + "," + price + ";";

                FlData += airport + "," + date + "," + outtime + "," + intime + ",;";
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs1 = this.getResultFromTable(Const.FLIGHT_TABLE);

            FlDataInfo = "";
            while (rs1.next()) {
                seats = rs1.getString(Const.FL_SEATS);
                price = rs1.getString(Const.FL_PRICE);

                FlDataInfo += seats + "," + price + ";";
            }
            rs1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String FlDataFinal = "";
        int count = FlID.split(";").length;

        String[] tmpID = FlID.split(";");
        String[] tmpData = FlData.split(";");
        String[] tmpInfo = FlDataInfo.split(";");

        for (int i = 0; i < count; ++i) {
            FlDataFinal += tmpID[i] + tmpData[i] + tmpInfo[i] + ";";
        }

        return FlDataFinal;
    }

    public String getDataForPie() {
        String data = "";
        ResultSet resultSet = this.getResultFromTable(Const.MAINDATA_TABLE);
        int countmorn = 0;
        int countday = 0;
        int counteve = 0;
        int countni = 0;
        try {
            while (resultSet.next()) {
                String outtime = resultSet.getString(Const.MAINDATA_OUTTIME);

                String[] time = outtime.split(":");

                int outTime = Integer.parseInt(time[0]);
                if (outTime >= 0 && outTime < 6) countni++;
                else if (outTime >= 6 && outTime < 12) countmorn++;
                else if (outTime >= 12 && outTime < 18) countday++;
                else if (outTime >= 18 && outTime < 24) counteve++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String CM = String.valueOf(countmorn);
        saveServerLog(true, "Count Morning flights: " + CM + " \r\n");
        String CD = String.valueOf(countday);
        saveServerLog(true, "Count Day flights: " + CD + " \r\n");
        String CE = String.valueOf(counteve);
        saveServerLog(true, "Count Evening flights: " + CE + " \r\n");
        String CN = String.valueOf(countni);
        saveServerLog(true, "Count Night flights: " + CN + " \r\n");
        data = CM + "," + CD + "," + CE + "," + CN;

        return data;
    }

    @Override
    public String quotate(String content) {
        return "'" + content + "'";
    }

    public FlightsTable(Statement stmt) {
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
