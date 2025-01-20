package com.zuehlke.colossus.cart;

import com.zuehlke.colossus.catalog.model.Product;
import com.zuehlke.colossus.catalog.repositories.ProductRepository;
import com.zuehlke.colossus.cart.model.Cart;
import com.zuehlke.colossus.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartService() {
    }

    public Cart findOrCreate(String sessionId) {
        return cartRepository
                .findBySession(sessionId).orElseGet(createShoppingCart(sessionId));
    }

    public Cart addProduct(String sessionId, Long productId) {
        Cart cart = findOrCreate(sessionId);
        Product product = productRepository.findById(productId).orElseThrow();
        cart.addProduct(product.id());
        cartRepository.save(cart);
        return cart;
    }

    private Supplier<? extends Cart> createShoppingCart(String sessionId) {
        Cart cart = new Cart(sessionId);
        cartRepository.save(cart);
        return () -> cart;
    }

}
