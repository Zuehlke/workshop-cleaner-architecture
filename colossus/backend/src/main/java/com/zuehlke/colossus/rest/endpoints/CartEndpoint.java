package com.zuehlke.colossus.rest.endpoints;

import com.zuehlke.colossus.cart.CartService;
import com.zuehlke.colossus.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartEndpoint {

    @Autowired
    private CartService cartService;

    @GetMapping(value = "/cart", produces = "application/json")
    public ResponseEntity<Cart> viewCart(@RequestHeader("COLOSSUS_SESSION") String colossusSession) {
        Cart cart = cartService.findOrCreate(colossusSession);
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping(value = "/cart/products", produces = "application/json")
    public ResponseEntity<Cart> addProductToCart(@RequestHeader("COLOSSUS_SESSION") String colossusSession,
                                                 @RequestParam Long productId) {
        return ResponseEntity.ok().body(cartService.addProduct(colossusSession, productId));
    }

}
