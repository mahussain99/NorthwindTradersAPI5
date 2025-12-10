package com.pluralsight.NorthwindTradersAPI5.dao.impl;

import com.pluralsight.NorthwindTradersAPI5.dao.interfaces.ICategoryDAO;
import com.pluralsight.NorthwindTradersAPI5.models.Category;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCategoryDAO implements ICategoryDAO {
    private DataSource dataSource;

    @Autowired
    public JdbcCategoryDAO(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public List<Category> getAllCatergory() {
        List<Category> categoryList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                       SELECT  CategoryID, CategoryName
                       FROM Categories
                     """);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int categoryID = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");
                String Description = resultSet.getString("Description");
                Category category = new Category(categoryID, categoryName, Description);
                categoryList.add(category);
            }
        } catch (Exception e) {
            System.out.println("Show me run time error");
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public Category getById(int categoryId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement categoriesStatement = connection.prepareStatement("""
                     "SELECT * FROM Categories WHERE CategoryID = ?"
                     """)) {

            categoriesStatement.setInt(1, categoryId);
            try (ResultSet resultSet = categoriesStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryID = resultSet.getInt("CategoryID");
                    String categoryName = resultSet.getString("CategoryName");
                    String description = resultSet.getString("Description");

                    Category category = new Category(categoryID, categoryName, description);
                    return category;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Category add(Category category) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statementOfCategory = connection.prepareStatement("""
                             INSERT INTO Category (CategoryID, CategoryName, Description) VALUES (?, ?, ?)""",
                     Statement.RETURN_GENERATED_KEYS)) {

            statementOfCategory.setInt(1, category.getCategoryId());
            statementOfCategory.setString(2, category.getCategoryName());
            statementOfCategory.setString(3, category.getDescription());

            int affectedRows = statementOfCategory.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("If Creating failed anything, no rows affected.");
            }

            try (ResultSet generatedKeys = statementOfCategory.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    category.setCategoryId(generatedId);
                } else {
                    throw new SQLException("show us creating category failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    public void update(int id, Category category) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement("""
                     UPDATE Categories SET CategoryID = ?, CategoryName = ?, Description = ?
                     """)) {

            updateStatement.setInt(1, category.getCategoryId());
            updateStatement.setString(2, category.getCategoryName());
            updateStatement.setString(3, category.getDescription());

            updateStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}