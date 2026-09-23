package dao;

import database.DatabaseConnection;
import model.Crypto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CryptoDAO {

    public List<Crypto> getAllCryptocurrencies() {

        List<Crypto> cryptoList = new ArrayList<>();

        String sql = "SELECT * FROM cryptocurrencies";

        try {
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Crypto crypto = new Crypto(
                        resultSet.getInt("coin_id"),
                        resultSet.getString("name"),
                        resultSet.getString("symbol"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("market_cap"),
                        resultSet.getDouble("change_24h")
                );

                cryptoList.add(crypto);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            System.out.println("Unable to load cryptocurrency data!");
            e.printStackTrace();
        }

        return cryptoList;
    }
}