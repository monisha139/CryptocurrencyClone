package dao;

import database.DatabaseConnection;
import model.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDAO {

    // Add crypto to portfolio
    public boolean addToPortfolio(
            int userId,
            int coinId,
            double quantity) {

        String checkSql =
                "SELECT * FROM portfolio " +
                "WHERE user_id = ? AND coin_id = ?";

        String insertSql =
                "INSERT INTO portfolio " +
                "(user_id, coin_id, quantity) " +
                "VALUES (?, ?, ?)";

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement checkStatement =
                    connection.prepareStatement(checkSql);

            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, coinId);

            ResultSet resultSet =
                    checkStatement.executeQuery();

            if (resultSet.next()) {

                resultSet.close();
                checkStatement.close();
                connection.close();

                return false;
            }

            resultSet.close();
            checkStatement.close();

            PreparedStatement insertStatement =
                    connection.prepareStatement(insertSql);

            insertStatement.setInt(1, userId);
            insertStatement.setInt(2, coinId);
            insertStatement.setDouble(3, quantity);

            int rows =
                    insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Unable to add crypto to portfolio!"
            );

            e.printStackTrace();

            return false;
        }
    }

    // Get user's portfolio
    public List<Crypto> getPortfolio(int userId) {

        List<Crypto> portfolio =
                new ArrayList<>();

        String sql =
                "SELECT c.* " +
                "FROM cryptocurrencies c " +
                "JOIN portfolio p " +
                "ON c.coin_id = p.coin_id " +
                "WHERE p.user_id = ?";

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Crypto crypto =
                        new Crypto(
                                resultSet.getInt("coin_id"),
                                resultSet.getString("name"),
                                resultSet.getString("symbol"),
                                resultSet.getDouble("price"),
                                resultSet.getDouble("market_cap"),
                                resultSet.getDouble("change_24h")
                        );

                portfolio.add(crypto);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Unable to load portfolio!"
            );

            e.printStackTrace();
        }

        return portfolio;
    }

    // Get quantity of a crypto
    public double getQuantity(
            int userId,
            int coinId) {

        String sql =
                "SELECT quantity FROM portfolio " +
                "WHERE user_id = ? AND coin_id = ?";

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);
            statement.setInt(2, coinId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                double quantity =
                        resultSet.getDouble("quantity");

                resultSet.close();
                statement.close();
                connection.close();

                return quantity;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Unable to get portfolio quantity!"
            );

            e.printStackTrace();
        }

        return 0;
    }
}