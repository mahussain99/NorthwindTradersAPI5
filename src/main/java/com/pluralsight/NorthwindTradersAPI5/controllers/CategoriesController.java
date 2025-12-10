package com.pluralsight.NorthwindTradersAPI5.controllers;

import com.pluralsight.NorthwindTradersAPI5.dao.interfaces.ICategoryDAO;
import com.pluralsight.NorthwindTradersAPI5.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    public class CategoriesController {

    private final ICategoryDAO categoryDAO;

    @Autowired
    public CategoriesController(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public List<Category> getAllCatergories() {
        return categoryDAO.getAllCatergory();
    }

    @RequestMapping(path = "/categories/{categoryId}", method = RequestMethod.GET)
    public Category getCategoriesById(@PathVariable int categoryId) {
        return categoryDAO.getById(categoryId);
    }

    @RequestMapping(path = "/categories", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return categoryDAO.add(category);
    }
        @RequestMapping(path = "/categories/{categoriesId}", method = RequestMethod.PUT)
        public void updateCategories(@PathVariable int categoriesId, @RequestBody Category category) {
            categoryDAO.update(categoriesId, category);

    }
}
