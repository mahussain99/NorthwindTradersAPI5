package com.pluralsight.NorthwindTradersAPI5.dao.interfaces;

import com.pluralsight.NorthwindTradersAPI5.models.Category;

import java.util.List;

public interface ICategoryDAO {

    List<Category> getAllCatergory();

    Category getById(int categoryId);

    Category add(Category category);
    void update(int id, Category category);


}
