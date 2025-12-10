package com.pluralsight.NorthwindTradersAPI5.dao.impl;

import com.pluralsight.NorthwindTradersAPI5.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTradersAPI5.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDAO implements IProductDAO {

    private DataSource dataSource;

    @Autowired
    public JdbcProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                       SELECT ProductID, ProductName, CategoryID, UnitPrice
                       FROM Products
                     """);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                int categoryID = resultSet.getInt("CategoryID");
                double unitPrice = resultSet.getDouble("UnitPrice");
                Product product = new Product(productID, productName, categoryID, unitPrice);
                productList.add(product);
            }
        } catch (Exception e) {
            System.out.println("Show me run time error");
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getById(int productId) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     SELECT ProductID, ProductName, CategoryID, UnitPrice
                     FROM Products
                     WHERE ProductID = ?
                     """)) {

            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int productID = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    int categoryID = resultSet.getInt("CategoryID");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    Product product = new Product(productID, productName, categoryID, unitPrice);
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Product add(Product product) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statementOfProduct = connection.prepareStatement("""
                             INSERT INTO Products (ProductName, CategoryID, UnitPrice) VALUES (?, ?, ?)""",
                     Statement.RETURN_GENERATED_KEYS)) {

            statementOfProduct.setString(1, product.getProductName());
            statementOfProduct.setInt(2, product.getCategoryId());
            statementOfProduct.setDouble(3, product.getUnitPrice());

            int affectedRows = statementOfProduct.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("If Creating failed anything, no rows affected.");
            }

            try (ResultSet generatedKeys = statementOfProduct.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setProductId(generatedId);
                } else {
                    throw new SQLException("show us creating product failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }


    @Override
    public void update(int productId, Product product) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement("""
                     UPDATE Products SET ProductName = ?, CategoryID = ?, UnitPrice = ? WHERE ProductID = ?
                     """)) {

            updateStatement.setString(1, product.getProductName());
            updateStatement.setInt(2, product.getCategoryId());
            updateStatement.setDouble(3, product.getUnitPrice());
            updateStatement.setInt(4, productId);

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

  /*  @Override
    public void delete(int productId) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement("""
                     DELETE FROM Products WHERE ProductID = ?
                     """)) {

            statement.setInt(1, productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
*/
}