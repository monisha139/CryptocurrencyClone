package ui;

import dao.CryptoDAO;
import model.Crypto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MarketOverviewFrame extends JFrame {

    private JLabel totalCoinsLabel;
    private JLabel highestPriceLabel;
    private JLabel lowestPriceLabel;
    private JLabel totalMarketCapLabel;

    public MarketOverviewFrame() {

        setTitle("Cryptocurrency Clone - Market Overview");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(null);

        // Title
        JLabel titleLabel =
                new JLabel("📈 Market Overview");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        titleLabel.setBounds(
                150, 30, 350, 40
        );

        mainPanel.add(titleLabel);

        // Total Coins
        JLabel totalCoinsTitle =
                new JLabel("Total Cryptocurrencies:");

        totalCoinsTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        totalCoinsTitle.setBounds(
                80, 110, 220, 30
        );

        mainPanel.add(totalCoinsTitle);

        totalCoinsLabel =
                new JLabel("0");

        totalCoinsLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        totalCoinsLabel.setBounds(
                350, 110, 180, 30
        );

        mainPanel.add(totalCoinsLabel);

        // Highest Price
        JLabel highestTitle =
                new JLabel("Highest Price Coin:");

        highestTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        highestTitle.setBounds(
                80, 160, 220, 30
        );

        mainPanel.add(highestTitle);

        highestPriceLabel =
                new JLabel("-");

        highestPriceLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        highestPriceLabel.setBounds(
                350, 160, 220, 30
        );

        mainPanel.add(highestPriceLabel);

        // Lowest Price
        JLabel lowestTitle =
                new JLabel("Lowest Price Coin:");

        lowestTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        lowestTitle.setBounds(
                80, 210, 220, 30
        );

        mainPanel.add(lowestTitle);

        lowestPriceLabel =
                new JLabel("-");

        lowestPriceLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        lowestPriceLabel.setBounds(
                350, 210, 220, 30
        );

        mainPanel.add(lowestPriceLabel);

        // Total Market Cap
        JLabel marketCapTitle =
                new JLabel("Total Market Cap:");

        marketCapTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        marketCapTitle.setBounds(
                80, 260, 220, 30
        );

        mainPanel.add(marketCapTitle);

        totalMarketCapLabel =
                new JLabel("$0");

        totalMarketCapLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        totalMarketCapLabel.setBounds(
                350, 260, 220, 30
        );

        mainPanel.add(totalMarketCapLabel);

        // Refresh Button
        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setBounds(
                250, 330, 130, 40
        );

        mainPanel.add(refreshButton);

        refreshButton.addActionListener(
                e -> loadMarketOverview()
        );

        add(mainPanel);

        loadMarketOverview();
    }

    // Load market overview
    private void loadMarketOverview() {

        CryptoDAO cryptoDAO =
                new CryptoDAO();

        List<Crypto> cryptoList =
                cryptoDAO.getAllCryptocurrencies();

        if (cryptoList.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No cryptocurrency data available!"
            );

            return;
        }

        totalCoinsLabel.setText(
                String.valueOf(cryptoList.size())
        );

        Crypto highestPriceCoin =
                cryptoList.get(0);

        Crypto lowestPriceCoin =
                cryptoList.get(0);

        double totalMarketCap = 0;

        for (Crypto crypto : cryptoList) {

            if (crypto.getPrice()
                    > highestPriceCoin.getPrice()) {

                highestPriceCoin = crypto;
            }

            if (crypto.getPrice()
                    < lowestPriceCoin.getPrice()) {

                lowestPriceCoin = crypto;
            }

            totalMarketCap +=
                    crypto.getMarketCap();
        }

        highestPriceLabel.setText(
                highestPriceCoin.getName()
                + " ($"
                + highestPriceCoin.getPrice()
                + ")"
        );

        lowestPriceLabel.setText(
                lowestPriceCoin.getName()
                + " ($"
                + lowestPriceCoin.getPrice()
                + ")"
        );

        totalMarketCapLabel.setText(
                "$"
                + totalMarketCap
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MarketOverviewFrame frame =
                    new MarketOverviewFrame();

            frame.setVisible(true);
        });
    }
}