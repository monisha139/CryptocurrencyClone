package ui;

import dao.CryptoDAO;
import dao.PortfolioDAO;
import dao.WatchlistDAO;
import model.Crypto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardFrame extends JFrame {

    private JTable cryptoTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    private List<Crypto> cryptoList;

    private int loggedInUserId = 1;

    public DashboardFrame() {

        setTitle("Cryptocurrency Clone - Market Dashboard");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel =
                new JLabel("Cryptocurrency Market Dashboard");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // SEARCH PANEL
        JPanel searchPanel =
                new JPanel();

        JLabel searchLabel =
                new JLabel("Search Coin:");

        searchField =
                new JTextField(20);

        JButton searchButton =
                new JButton("Search");

        JButton showAllButton =
                new JButton("Show All");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        mainPanel.add(
                searchPanel,
                BorderLayout.BEFORE_FIRST_LINE
        );

        // TABLE
        String[] columns = {
                "ID",
                "Coin Name",
                "Symbol",
                "Price",
                "Market Cap",
                "24h Change"
        };

        tableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        cryptoTable =
                new JTable(tableModel);

        cryptoTable.setRowHeight(30);

        cryptoTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(cryptoTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // BUTTONS
        JButton refreshButton =
                new JButton("Refresh");

        JButton watchlistButton =
                new JButton("⭐ Add to Watchlist");

        JButton viewWatchlistButton =
                new JButton("📋 My Watchlist");

        JButton portfolioButton =
                new JButton("💰 Add to Portfolio");

        JButton viewPortfolioButton =
                new JButton("📊 My Portfolio");

        JButton marketOverviewButton =
                new JButton("📈 Market Overview");

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(refreshButton);
        bottomPanel.add(watchlistButton);
        bottomPanel.add(viewWatchlistButton);
        bottomPanel.add(portfolioButton);
        bottomPanel.add(viewPortfolioButton);
        bottomPanel.add(marketOverviewButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // SEARCH
        searchButton.addActionListener(
                e -> searchCryptocurrency()
        );

        // SHOW ALL
        showAllButton.addActionListener(e -> {

            searchField.setText("");

            displayCryptocurrencies(cryptoList);
        });

        // REFRESH
        refreshButton.addActionListener(
                e -> loadCryptocurrencies()
        );

        // ADD WATCHLIST
        watchlistButton.addActionListener(
                e -> addSelectedCoinToWatchlist()
        );

        // VIEW WATCHLIST
        viewWatchlistButton.addActionListener(e -> {

            WatchlistFrame frame =
                    new WatchlistFrame();

            frame.setVisible(true);
        });

        // ADD PORTFOLIO
        portfolioButton.addActionListener(
                e -> addSelectedCoinToPortfolio()
        );

        // VIEW PORTFOLIO
        viewPortfolioButton.addActionListener(e -> {

            PortfolioFrame frame =
                    new PortfolioFrame();

            frame.setVisible(true);
        });

        // MARKET OVERVIEW
        marketOverviewButton.addActionListener(e -> {

            MarketOverviewFrame frame =
                    new MarketOverviewFrame();

            frame.setVisible(true);
        });

        // DOUBLE CLICK COIN DETAILS
        cryptoTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && SwingUtilities
                                .isLeftMouseButton(e)) {

                            showCoinDetails();
                        }
                    }
                }
        );

        add(mainPanel);

        loadCryptocurrencies();
    }

    // LOAD CRYPTOCURRENCIES
    private void loadCryptocurrencies() {

        CryptoDAO cryptoDAO =
                new CryptoDAO();

        cryptoList =
                cryptoDAO.getAllCryptocurrencies();

        displayCryptocurrencies(cryptoList);
    }

    // DISPLAY CRYPTOCURRENCIES
    private void displayCryptocurrencies(
            List<Crypto> list) {

        tableModel.setRowCount(0);

        for (Crypto crypto : list) {

            Object[] row = {
                    crypto.getCoinId(),
                    crypto.getName(),
                    crypto.getSymbol(),
                    "$" + crypto.getPrice(),
                    "$" + crypto.getMarketCap(),
                    crypto.getChange24h() + "%"
            };

            tableModel.addRow(row);
        }
    }

    // SEARCH
    private void searchCryptocurrency() {

        String searchText =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        if (searchText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a coin name or symbol!"
            );

            return;
        }

        ArrayList<Crypto> filteredList =
                new ArrayList<>();

        for (Crypto crypto : cryptoList) {

            if (crypto.getName()
                    .toLowerCase()
                    .contains(searchText)
                    || crypto.getSymbol()
                    .toLowerCase()
                    .contains(searchText)) {

                filteredList.add(crypto);
            }
        }

        if (filteredList.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cryptocurrency not found!"
            );

        } else {

            displayCryptocurrencies(
                    filteredList
            );
        }
    }

    // COIN DETAILS
    private void showCoinDetails() {

        int selectedRow =
                cryptoTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        String coinName =
                tableModel
                        .getValueAt(selectedRow, 1)
                        .toString();

        String symbol =
                tableModel
                        .getValueAt(selectedRow, 2)
                        .toString();

        String price =
                tableModel
                        .getValueAt(selectedRow, 3)
                        .toString();

        String marketCap =
                tableModel
                        .getValueAt(selectedRow, 4)
                        .toString();

        String change =
                tableModel
                        .getValueAt(selectedRow, 5)
                        .toString();

        String details =
                "Cryptocurrency Details\n\n"
                + "Coin Name: " + coinName + "\n"
                + "Symbol: " + symbol + "\n"
                + "Price: " + price + "\n"
                + "Market Cap: " + marketCap + "\n"
                + "24h Change: " + change;

        JOptionPane.showMessageDialog(
                this,
                details,
                coinName + " Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ADD TO WATCHLIST
    private void addSelectedCoinToWatchlist() {

        int selectedRow =
                cryptoTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a cryptocurrency first!"
            );

            return;
        }

        int coinId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(selectedRow, 0)
                                .toString()
                );

        String coinName =
                tableModel
                        .getValueAt(selectedRow, 1)
                        .toString();

        WatchlistDAO watchlistDAO =
                new WatchlistDAO();

        boolean success =
                watchlistDAO.addToWatchlist(
                        loggedInUserId,
                        coinId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    coinName
                    + " added to your Watchlist! ⭐"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    coinName
                    + " is already in your Watchlist!"
            );
        }
    }

    // ADD TO PORTFOLIO
    private void addSelectedCoinToPortfolio() {

        int selectedRow =
                cryptoTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a cryptocurrency first!"
            );

            return;
        }

        int coinId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(selectedRow, 0)
                                .toString()
                );

        String coinName =
                tableModel
                        .getValueAt(selectedRow, 1)
                        .toString();

        String quantityText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter quantity for "
                        + coinName + ":"
                );

        if (quantityText == null) {
            return;
        }

        try {

            double quantity =
                    Double.parseDouble(
                            quantityText.trim()
                    );

            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than 0!"
                );

                return;
            }

            PortfolioDAO portfolioDAO =
                    new PortfolioDAO();

            boolean success =
                    portfolioDAO.addToPortfolio(
                            loggedInUserId,
                            coinId,
                            quantity
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        coinName
                        + " added to your Portfolio! 💰"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        coinName
                        + " is already in your Portfolio!"
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity!"
            );
        }
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DashboardFrame dashboard =
                    new DashboardFrame();

            dashboard.setVisible(true);
        });
    }
}