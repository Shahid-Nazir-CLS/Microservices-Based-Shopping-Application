// package com.example.customer.handler;

// import java.io.IOException;
// import java.util.List;
// import java.util.Map;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import
// org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;

// import com.example.customer.dao.CustomerDAO;
// import com.example.customer.dto.AddressDTO;
// import com.example.customer.dto.CustomerDTO;
// import com.example.customer.entity.Address;
// import com.example.customer.entity.Customer;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

// @Autowired
// private CustomerDAO customerDAO;

// @Autowired
// private ModelMapper modelMapper;

// @Override
// public void onAuthenticationSuccess(HttpServletRequest request,
// HttpServletResponse response,
// Authentication authentication) throws IOException, ServletException {

// OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
// Map<String, Object> attributes = oauth2User.getAttributes();

// // Log the attributes
// attributes.forEach((key, value) -> System.out.println(key + ": " + value));

// // check if customer exists in db
// // if not save in db
// Customer customer =
// customerDAO.findByEmail(String.valueOf(attributes.get("email")));
// if (customer == null) {
// CustomerDTO customerDTO = new CustomerDTO();
// customerDTO.setEmail(String.valueOf(attributes.get("email")));
// Long googleId =
// Long.valueOf((String.valueOf(attributes.get("sub")).substring(0, 18)));
// customerDTO.setGoogleId(googleId);
// customerDTO.setUsername(String.valueOf(attributes.get("name")));
// customerDTO.setProfilePic(String.valueOf(attributes.get("picture")));
// customer = customerDAO.save(modelMapper.map(customerDTO, Customer.class));
// }

// // Redirect to home page or any other URL
// response.sendRedirect("/home");
// }
// }
