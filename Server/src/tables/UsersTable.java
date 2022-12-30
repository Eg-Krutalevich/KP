package tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersTable extends DataTable implements ResultFromTable {

    public ResultSet getUserWithID(String id) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM users WHERE idUsers = " + Integer.parseInt(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public ResultSet getResultFromTable(String table) {
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery("SELECT * FROM " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public String checkUserInDB(String username) {
        String state = "success";
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        try {
            while (rs.next()) {
                String Username = rs.getString(Const.USERS_USERNAME);
                if (Username.equals(username)) state = "fail";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }

    public String isBlocked(String username) {
        String isBlocked = "no";
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        try {
            while (rs.next()) {
                String usn = rs.getString(Const.USERS_USERNAME);
                if (usn.equals(username)) {
                    String ib = rs.getString(Const.USERS_IB);
                    if (ib.equals("yes")) isBlocked = "yes";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isBlocked;
    }

    public String Blocked(String username) {
        String Blocked = "yes";
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);
        try {
            while (rs.next()) {
                String usn = rs.getString(Const.USERS_USERNAME);
                if (usn.equals(username)) {
                    String ib = rs.getString(Const.USERS_IB);
                    if (ib.equals("no")) Blocked = "no";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Blocked;
    }

    public String CheckLoginClient(String username, String password) {
        String state = "";
        int i = 0;
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);

        try {
            while (rs.next()) {
                String tableLogin = rs.getString(Const.USERS_USERNAME);
                String tablePassword = rs.getString(Const.USERS_PASSWORD);

                if (tableLogin.equals(username) && tablePassword.equals(password)) {
                    int id = getIdUser(username);
                    ResultSet resultSet = this.getResultFromTable(Const.CLIENT_TABLE);
                    while (resultSet.next()) {
                        int Cid = resultSet.getInt(Const.CLIENT_ID);
                        if (Cid == id) {
                            state = "successClient";
                            i++;
                        }
                    }
                }
            }
            if (i == 0) state = "fail";
        } catch (Exception e) {
            System.out.println("Exception in Table of users");
        } finally {
            this.mdbc.close(rs);
        }
        return state;
    }

    public String CheckLoginAdmin(String username, String password) {
        String state = "";
        int i = 0;
        ResultSet rs = this.getResultFromTable(Const.USER_TABLE);

        try {
            while (rs.next()) {
                String tableLogin = rs.getString(Const.USERS_USERNAME);
                String tablePassword = rs.getString(Const.USERS_PASSWORD);

                if (tableLogin.equals(username) && tablePassword.equals(password)) {
                    int id = getIdUser(username);
                    System.out.println(id);
                    ResultSet resultSet = this.getResultFromTable(Const.ADMIN_TABLE);
                    while (resultSet.next()) {
                        int Cid = resultSet.getInt(Const.ADMIN_ID);
                        System.out.println(Cid);
                        if (Cid == id) {
                            state = "successAdmin";
                            i++;
                        }
                    }
                }
            }
            if (i == 0) state = "fail";
        } catch (Exception e) {
            System.out.println("Exception in Table of users!");
        } finally {
            this.mdbc.close(rs);
        }
        return state;
    }

    public void AddClient(String name, String surname, String sex, String country, String username, String password) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" +
                "VALUES (" + this.quotate(username) + "," + this.quotate(password) + ")";

        try {
            this.stmt.executeUpdate(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int id = getIdUser(username);

        try {
            stmt.executeUpdate("INSERT INTO " + Const.CLIENT_TABLE + "(" + Const.CLIENT_ID + "," +
                    Const.CLIENT_NAME + "," + Const.CLIENT_SURNAME + "," +
                    Const.CLIENT_COUNTRY + "," + Const.CLIENT_SEX + ")" +
                    "VALUES (" + id + "," + this.quotate(name) + "," + this.quotate(surname) + "," + this.quotate(country) + "," + this.quotate(sex) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void addAdmin(String username, String password) {
        try {
            stmt.executeUpdate("INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES (" + this.quotate(username) + "," + this.quotate(password) + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String idu = String.valueOf(getIdUser(username));

        try {
            stmt.executeUpdate("INSERT INTO " + Const.ADMIN_TABLE + "(" + Const.ADMIN_ID + "," + Const.ADMIN_STATUS + ")" + "VALUES (" + this.quotate(idu) + "," + this.quotate("ordinary") + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAdmin(String username) {
        String id = String.valueOf(getIdUser(username));
        try {
            stmt.executeUpdate("DELETE FROM " + Const.ADMIN_TABLE + " WHERE (" + Const.ADMIN_ID + " LIKE '" + id + "');");
            stmt.executeUpdate("DELETE FROM " + Const.USER_TABLE + " WHERE (" + Const.USERS_ID + " LIKE '" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void blockUser(String username) {
        String bl = "yes";
        try {
            stmt.executeUpdate("UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_IB + " = '" + bl + "' WHERE (" + Const.USERS_USERNAME + " = '" + username + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unblockUser(String username) {
        String bl = "no";
        try {
            stmt.executeUpdate("UPDATE " + Const.USER_TABLE + " SET " + Const.USERS_IB + " = '" + bl + "' WHERE (" + Const.USERS_USERNAME + " = '" + username + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAllUsers() {
        String AdminData = null;
        String ClientData = null;
        String UserData = null;

        String id = null;
        String login = null;
        String password = null;
        String status = null;
        String finalData = "";

        try {
            ResultSet rs2 = this.getResultFromTable(Const.ADMIN_TABLE);

            AdminData = "";
            while (rs2.next()) {
                id = rs2.getString(Const.ADMIN_ID);
                AdminData += id + ";";
            }
            rs2.close();

            ClientData = "";
            ResultSet resultSet = this.getResultFromTable(Const.CLIENT_TABLE);
            while (resultSet.next()) {
                id = resultSet.getString(Const.CLIENT_ID);
                ClientData += id + ";";
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String[] AdminIdArray = AdminData.split(";");
            String[] ClientIdArray = ClientData.split(";");

            for (int i = 0; i < AdminIdArray.length; ++i) {
                ResultSet rs1 = this.getUserWithID(AdminIdArray[i]);
                rs1.next();

                login = rs1.getString(Const.USERS_USERNAME);
                password = rs1.getString(Const.USERS_PASSWORD);
                status = rs1.getString(Const.USERS_IB);

                rs1.close();

                finalData += AdminIdArray[i] + "," + login + "," + password + "," + "Администратор" + "," + status + ";";
            }

           for (int i = 0; i < ClientIdArray.length; ++i) {
                ResultSet rs1 = this.getUserWithID(ClientIdArray[i]);
                rs1.next();

                login = rs1.getString(Const.USERS_USERNAME);
                password = rs1.getString(Const.USERS_PASSWORD);
                status = rs1.getString(Const.USERS_IB);

                rs1.close();

                finalData += ClientIdArray[i] + "," + login + "," + password + "," + "Клиент" + "," + status + ";";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finalData;
    }

    @Override
    public String quotate(String content) {
        return "'" + content + "'";
    }

    public UsersTable(Statement stmt) {
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
