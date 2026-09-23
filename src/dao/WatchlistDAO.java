package dao;

import database.DatabaseConnection;
import model.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WatchlistDAO {

    // Add coin to watchlist
    public boolean addToWatchlist(int userId, int coinId) {

        String checkSql =
                "SELECT * FROM watchlist WHERE user_id = ? AND coin_id = ?";

        String insertSql =
                "INSERT INTO watchlist (user_id, coin_id) VALUES (?, ?)";

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

            int rows =
                    insertStatement.executeUpdate();

            insertStatement.close();
            connection.close();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Unable to add coin to watchlist!"
            );

            e.printStackTrace();

            return false;
        }
    }

    // Get user's watchlist
    public List<Crypto> getWatchlist(int userId) {

        List<Crypto> watchlist =
                new ArrayList<>();

        String sql =
                "SELECT c.* " +
                "FROM cryptocurrencies c " +
                "JOIN watchlist w " +
                "ON c.coin_id = w.coin_id " +
                "WHERE w.user_id = ?";

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

                watchlist.add(crypto);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Unable to load watchlist!"
            );

            e.printStackTrace();
        }

        return watchlist;
    }
}