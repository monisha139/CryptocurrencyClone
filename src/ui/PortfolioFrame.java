package ui;

import dao.PortfolioDAO;
import model.Crypto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PortfolioFrame extends JFrame {

    private JTable portfolioTable;
    private DefaultTableModel tableModel;

    // Temporary logged-in user
    private int loggedInUserId = 1;

    public PortfolioFrame() {

        setTitle("Cryptocurrency Clone - My Portfolio");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel =
                new JLabel("💰 My Cryptocurrency Portfolio");

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
                "Quantity",
                "Total Value"
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

        portfolioTable =
                new JTable(tableModel);

        portfolioTable.setRowHeight(30);

        portfolioTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(portfolioTable);

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
                e -> loadPortfolio()
        );

        add(mainPanel);

        loadPortfolio();
    }

    // Load portfolio
    private void loadPortfolio() {

        tableModel.setRowCount(0);

        PortfolioDAO portfolioDAO =
                new PortfolioDAO();

        List<Crypto> portfolio =
                portfolioDAO.getPortfolio(
                        loggedInUserId
                );

        double totalPortfolioValue = 0;

        for (Crypto crypto : portfolio) {

            double quantity =
                    portfolioDAO.getQuantity(
                            loggedInUserId,
                            crypto.getCoinId()
                    );

            double totalValue =
                    crypto.getPrice() * quantity;

            totalPortfolioValue += totalValue;

            Object[] row = {
                    crypto.getCoinId(),
                    crypto.getName(),
                    crypto.getSymbol(),
                    "$" + crypto.getPrice(),
                    quantity,
                    "$" + totalValue
            };

            tableModel.addRow(row);
        }

        if (portfolio.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your Portfolio is empty!"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Total Portfolio Value: $"
                    + totalPortfolioValue,
                    "Portfolio Summary",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PortfolioFrame frame =
                    new PortfolioFrame();

            frame.setVisible(true);
        });
    }
}