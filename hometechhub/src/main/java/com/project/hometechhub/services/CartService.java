package com.project.hometechhub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hometechhub.Repository.CartItemRepo;
import com.project.hometechhub.Repository.CartRepo;
import com.project.hometechhub.Repository.ProductRepo;
import com.project.hometechhub.entities.Cart;
import com.project.hometechhub.entities.CartItem;
import com.project.hometechhub.entities.Product;
import com.project.hometechhub.exceptions.APIException;
import com.project.hometechhub.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CartService {
//	Cart addProductToCart(Long cartId, Long productId, Integer quantity);
//
//	List<Cart> getAllCarts();
//
//	Cart getCart(String emailId, Long cartId);
//
//	Cart updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);
//
//	void updateProductInCarts(Long cartId, Long productId);
//
//	String deleteProductFromCart(Long cartId, Long productId);

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	public Cart addProductToCart(Long cartId, Long productId, Integer quantity) {

		Cart cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem != null) {
			throw new APIException("Product " + product.getProductName() + " already exists in the cart");
		}

		if (product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available");
		}

		if (product.getQuantity() < quantity) {
			throw new APIException("Please, make an order of the " + product.getProductName()
					+ " less than or equal to the quantity " + product.getQuantity() + ".");
		}

		CartItem newCartItem = new CartItem();

		newCartItem.setProduct(product);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(quantity);
		newCartItem.setDiscount(product.getDiscount());
		newCartItem.setProductPrice(product.getSpecialPrice());

		cartItemRepo.save(newCartItem);

		product.setQuantity(product.getQuantity() - quantity);

		cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

		// CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

		// List<ProductDTO> productDTOs = cart.getCartItems().stream()
		// .map(p -> modelMapper.map(p.getProduct(),
		// ProductDTO.class)).collect(Collectors.toList());

		// cartDTO.setProducts(productDTOs);

		return cart;

	}

//	public List<CartDTO> getAllCarts() {
//		List<Cart> carts = cartRepo.findAll();
//
//		if (carts.size() == 0) {
//			throw new APIException("No cart exists");
//		}
//
//		List<CartDTO> cartDTOs = carts.stream().map(cart -> {
//			CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//			List<ProductDTO> products = cart.getCartItems().stream()
//					.map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());
//
//			cartDTO.setProducts(products);
//
//			return cartDTO;
//
//		}).collect(Collectors.toList());
//
//		return cartDTOs;
//	}

	public Cart getCart(String emailId, Long cartId) {
		Cart cart = cartRepo.findCartByEmailAndCartId(emailId, cartId);

		if (cart == null) {
			throw new ResourceNotFoundException("Cart", "cartId", cartId);
		}

		// Cart cartDTO = modelMapper.map(cart, CartDTO.class);

		// List<ProductDTO> products = cart.getCartItems().stream()
		// .map(p -> modelMapper.map(p.getProduct(),
		// ProductDTO.class)).collect(Collectors.toList());

		// cartDTO.setProducts(products);

		return cart;
	}

//	public void updateProductInCarts(Long cartId, Long productId) {
//		Cart cart = cartRepo.findById(cartId)
//				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
//
//		Product product = productRepo.findById(productId)
//				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
//
//		CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);
//
//		if (cartItem == null) {
//			throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
//		}
//
//		double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());
//
//		cartItem.setProductPrice(product.getSpecialPrice());
//
//		cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));
//
//		cartItem = cartItemRepo.save(cartItem);
//	}

	public Cart updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
		Cart cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

		if (product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available");
		}

		if (product.getQuantity() < quantity) {
			throw new APIException("Please, make an order of the " + product.getProductName()
					+ " less than or equal to the quantity " + product.getQuantity() + ".");
		}

		CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
		}

		double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

		product.setQuantity(product.getQuantity() + cartItem.getQuantity() - quantity);

		cartItem.setProductPrice(product.getSpecialPrice());
		cartItem.setQuantity(quantity);
		cartItem.setDiscount(product.getDiscount());

		cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

		cartItem = cartItemRepo.save(cartItem);

		// CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

		// List<ProductDTO> productDTOs = cart.getCartItems().stream()
		// .map(p -> modelMapper.map(p.getProduct(),
		// ProductDTO.class)).collect(Collectors.toList());

		// cartDTO.setProducts(productDTOs);

		return cart;

	}

	public String deleteProductFromCart(Long cartId, Long productId) {
		Cart cart = cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

		CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

		if (cartItem == null) {
			throw new ResourceNotFoundException("Product", "productId", productId);
		}

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

		Product product = cartItem.getProduct();
		product.setQuantity(product.getQuantity() + cartItem.getQuantity());

		cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

		return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
	}

}
