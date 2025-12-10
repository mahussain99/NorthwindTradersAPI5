package com.pluralsight.NorthwindTradersAPI5.controllers;

import com.pluralsight.NorthwindTradersAPI5.dao.interfaces.IProductDAO;
import com.pluralsight.NorthwindTradersAPI5.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {
    private final IProductDAO productDAO;

    @Autowired
    public ProductsController(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getALlProduct() {
        return productDAO.getAllProduct();
    }

    @RequestMapping(path = "/products/{productId}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int productId) {
        return productDAO.getById(productId);

    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productDAO.add(product);

    }

    @RequestMapping(path = "/categories/{categoriesId}", method = RequestMethod.PUT)
    public void updateCategories(@PathVariable int productId, @RequestBody Product product) {
        productDAO.update(productId, product);

    }
}


