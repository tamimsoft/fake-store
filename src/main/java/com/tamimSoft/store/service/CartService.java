package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.AddToCartDTO;
import com.tamimSoft.store.dto.CartDTO;
import com.tamimSoft.store.entity.Cart;
import com.tamimSoft.store.entity.Product;
import com.tamimSoft.store.entity.User;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    final ProductService productService;
    private final CartRepository cartRepository;
    private final UserService userService;

    @Transactional
    public void addToCart(AddToCartDTO addToCartDTO, String userName) {

        Product product = productService.getProductById(addToCartDTO.getProductId());
        Cart cart = new Cart();
        cart.setSize(addToCartDTO.getSize());
        cart.setColor(addToCartDTO.getColor());
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setProduct(product);
        cartRepository.save(cart);
        User user = userService.getUserByUserName(userName);
        user.getCarts().add(cart);
        userService.updateUser(user);
    }

    public List<CartDTO> getAllCartDTOsByUserName(String userName) {
        User user = userService.getUserByUserName(userName);
        return user.getCarts().stream().map(cart -> new CartDTO(cart.getId(), cart.getQuantity(), cart.getColor(), cart.getSize(), cart.getProduct())).toList();
    }


    protected Cart getCartById(String cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
    }

    public void updateCart(String cartId, AddToCartDTO addToCartDTO) {
        Cart cartToUpdate = getCartById(cartId);
        // Update only non-null fields
        cartToUpdate.setQuantity(addToCartDTO.getQuantity());
        cartToUpdate.setSize(addToCartDTO.getSize() != null ? addToCartDTO.getSize() : cartToUpdate.getSize());
        cartToUpdate.setColor(addToCartDTO.getColor() != null ? addToCartDTO.getColor() : cartToUpdate.getColor());
        cartRepository.save(cartToUpdate);
    }

    @Transactional
    public void deleteCartById(String cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart not found with id: " + cartId);
        }
        cartRepository.deleteById(cartId);
        User user = userService.getUserByUserName(userService.getUserDTO(cartId).getUserName());
        user.getCarts().removeIf(cart -> cart.getId().equals(cartId));
        userService.updateUser(user);
    }
}
