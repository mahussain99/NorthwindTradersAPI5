package com.pluralsight.NorthwindTradersAPI5.dao.interfaces;

import com.pluralsight.NorthwindTradersAPI5.models.Product;

import java.util.List;

public interface IProductDAO {

    List<Product> getAllProduct();

    Product getById(int productId);

    Product add(Product product);
    void update(int productId, Product product);

    /*Product insert(Product product);

    void update(int productId, Product product);

    void delete(int productId);
}
*/
}