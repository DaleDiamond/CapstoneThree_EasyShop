package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;


@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RequestMapping("/cart")

public class ShoppingCartController {
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart getCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
            }

            int userId = user.getId();
            ShoppingCart cart = shoppingCartDao.getByUserId(userId);
            if (cart == null) {
                cart = new ShoppingCart();
            }
            return cart;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "OH NO... please try again.");
        }
    }

    @PostMapping("/products/{productID}")
    public void addProductToCart(@PathVariable int productId, Authentication authentication) {
        try {
            String userName = authentication.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();
            shoppingCartDao.addProductToCart(userId, productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops.. our bad", e);
        }
    }


    @PutMapping("products/{productID}")
    public void updateCart (@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.updateCart(userId, productId, item.getQuantity());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oh NO... please try again", e);
        }
    }

    @DeleteMapping
    public void clearCart(Principal principal)
    {
        try
        {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            shoppingCartDao.clearCart(userId);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OH NO.. please try again", e);
        }
    }
}

