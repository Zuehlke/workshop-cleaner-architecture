package com.zuehlke.colossus.cart;

import com.zuehlke.colossus.cart.model.Cart;

public interface CartServiceInterface {

    Cart findOrCreate(String userId);

    Cart addProduct(String userId, Long productId);

}
