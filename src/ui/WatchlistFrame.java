package ui;

import dao.WatchlistDAO;
import model.Crypto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WatchlistFrame extends JFrame {

    private JTable watchlistTable;
    private DefaultTableModel tableModel;

    // Temporary logged-in user
    private int loggedInUserId = 1;

    public WatchlistFrame() {

        setTitle("Cryptocurrency Clone - My Watchlist");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel =
                new JLabel("⭐ My Cryptocurrency Watchlist");

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

        // Table
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

        watchlistTable =
                new JTable(tableModel);

        watchlistTable.setRowHeight(30);

        watchlistTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(watchlistTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Bottom Panel
        JButton refreshButton =
                new JButton("Refresh");

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(refreshButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        refreshButton.addActionListener(
                e -> loadWatchlist()
        );

        add(mainPanel);

        loadWatchlist();
    }

    // Load watchlist
    private void loadWatchlist() {

        tableModel.setRowCount(0);

        WatchlistDAO watchlistDAO =
                new WatchlistDAO();

        List<Crypto> watchlist =
                watchlistDAO.getWatchlist(
                        loggedInUserId
                );

        for (Crypto crypto : watchlist) {

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

        if (watchlist.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your Watchlist is empty!"
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            WatchlistFrame frame =
                    new WatchlistFrame();

            frame.setVisible(true);
        });
    }
}