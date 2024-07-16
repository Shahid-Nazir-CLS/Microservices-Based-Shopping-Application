package com.shoppy.cart.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.modelmapper.config.Configuration.AccessLevel;

import com.shoppy.cart.dto.CartItemDTO;
import com.shoppy.cart.model.CartItem;
import com.shoppy.order.dto.ItemDTO;

@Configuration
public class CommonConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Ignore setId to prevent conflicts
		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE);

		modelMapper.addMappings(new PropertyMap<CartItemDTO, CartItem>() {
			@Override
			protected void configure() {
				map().setItemId(source.getItemId());
				// map().getCart().setCartId(source.getCartId());
				// Ignore the default setId method
				skip().setCartItemId(0);
			}
		});

		// Mapping configuration for CartItem to CartItemDTO
        modelMapper.addMappings(new PropertyMap<CartItem, CartItemDTO>() {
            @Override
            protected void configure() {
                map().setCartItemId(source.getCartItemId());
                // map().setCartId(source.getCart().getCartId()); // Explicitly map cartId from nested Cart object
            }
        });

		return modelMapper;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
