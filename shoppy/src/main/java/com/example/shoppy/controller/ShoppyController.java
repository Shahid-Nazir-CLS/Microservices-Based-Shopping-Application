// package com.example.shoppy.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.client.RestTemplate;

// import com.example.shoppy.dto.CartDTO;
// import com.example.shoppy.dto.CartItemDTO;
// import com.example.shoppy.dto.CustomerDTO;
// import com.example.shoppy.dto.ItemsResponseDTO;
// import com.example.shoppy.model.AddressDTO;
// import com.example.shoppy.model.ItemDTO;
// import com.example.shoppy.model.OrderDTO;

// @Controller
// public class ShoppyController {
//         @Autowired
//         private RestTemplate restTemplate;
//         int userId = 1;

//         public ShoppyController() {
//         }

//         @GetMapping("/")
//         public String getHome(@RequestParam(name = "page", defaultValue = "0") String pageNumber, Model model) {

//                 ItemsResponseDTO response = restTemplate.getForObject("http://localhost:8081/api/v1/items?page=" + pageNumber, ItemsResponseDTO.class);
//                 List<ItemDTO> itemDTOs = response.getEmbeddedItems().getItems();
//                 // Add the list to the model
//                 model.addAttribute("items", itemDTOs);
//                 model.addAttribute("totalPages", response.getPage().getTotalPages());
//                 model.addAttribute("currPageNumber", response.getPage().getNumber());
//                 model.addAttribute("linksProvided", true);
//                 return "home";
//         }

//         @GetMapping("/search")
//         public String getSearch(@RequestParam("keyword") String keyword, Model model){

//                 System.out.println("Search keyword: " + keyword);

//                 ResponseEntity<List<ItemDTO>> response = restTemplate.exchange(
//                             "http://localhost:8081/api/v1/items/search?keyword=" + keyword,
//                             HttpMethod.GET,
//                             null,
//                             new ParameterizedTypeReference<List<ItemDTO>>() {}
//                 );
//                 List<ItemDTO> orderDTOs = response.getBody();
//                 System.out.println(orderDTOs);
//                 // ItemsResponseDTO response = restTemplate.getForObject("http://localhost:8081/api/v1/items/search?keyword=" + keyword, ItemsResponseDTO.class);
//                 model.addAttribute("items", orderDTOs);
//                 model.addAttribute("keyword", keyword);
//                 model.addAttribute("linksProvided", false);
//                 return "home";
//         }

//         // view item details
//         @GetMapping("/itemDetails/{itemId}")
//         public String getItemDetails(@PathVariable("itemId") String itemId, Model model) {
//                 ItemDTO itemDto = restTemplate.getForObject("http://localhost:8081/api/v1/items/" + itemId,ItemDTO.class);
//                 model.addAttribute("item", itemDto);
//                 return "itemDetails";
//         }

//         // get user cart
//         @GetMapping("/cart")
//         public String getCart(Model model) {

//                 CartDTO cartDTO = restTemplate.getForObject("http://localhost:8083/api/v1/carts/" + userId,CartDTO.class);
//                 if(cartDTO != null){
//                         CustomerDTO customerDTO =  restTemplate.getForObject("http://localhost:8082/api/v1/customers/"+userId, CustomerDTO.class);

//                         AddressDTO defaultAddressDTO = customerDTO.getAddresses().stream()
//                                                             .filter(address -> address.getAddressId() == customerDTO.getDefaultAddress())
//                                                             .findFirst()
//                                                             .orElse(null);

//                         model.addAttribute("cartItems", cartDTO.getCartDtoItems());
//                         model.addAttribute("address", defaultAddressDTO);
//                         model.addAttribute("customerId", userId);
//                 }
//                 return "cart";
//         }

//         // add item to cart
//         @GetMapping("/addItem/{itemId}/{returnPage}")
//         public String addItemToCart(@PathVariable("itemId") int itemId, @PathVariable("returnPage") String returnPage) {
//                 CartItemDTO cartItemDTO = restTemplate.postForObject("http://localhost:8083/api/v1/carts/" + userId + "/items/" + itemId, null, CartItemDTO.class);
//                 // System.out.println(cartItemDTO);
//                 System.out.println("item added " + itemId);
//                 if(returnPage.equals("home")){
//                         return "redirect:/";
//                 }else{
//                         return "redirect:/itemDetails/" + itemId;
//                 }
//         }

//         // remove item from cart
//         @GetMapping("/removeItem/{cartItemId}")
//         public String removeItemfromCart(@PathVariable("cartItemId") int cartItemId) {
//                 ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8083/api/v1/carts/" + userId + "/items/" + cartItemId, HttpMethod.DELETE, null, Void.class);
//                 System.out.println("item removed " + cartItemId);
//                 return "redirect:/cart";
//         }

//         // clear user cart
//         @GetMapping("/clearCart")
//         public String clearCart() {
//                 System.out.println("clearing cart");
//                 ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8083/api/v1/carts/" + userId, HttpMethod.DELETE, null, Void.class);
//                 System.out.println("cart cleared ");
//                 return "redirect:/cart";
//         }

//         // increase quantity
//         @GetMapping("/updateQuantity/{cartItemId}")
//         public String updateQuantity(@PathVariable("cartItemId") int cartItemId, @RequestParam("quantity") int newQuantity) {
//                 System.out.println("clearing cart");
//                 ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8083/api/v1/carts/" + userId + "/items/" + cartItemId + "?newQuantity=" +newQuantity, HttpMethod.PUT, null, Void.class);

//                 System.out.println("quantity Updated " + newQuantity);
//                 return "redirect:/cart";
//         }


//         @GetMapping("/orderCancel/{orderId}")
//         public String getOrderCancelled(@PathVariable("orderId") int orderId, Model model) {
//                 ResponseEntity<OrderDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/orders/cancel/" + orderId, HttpMethod.PUT, null, OrderDTO.class);
//                 OrderDTO orderDTO = response.getBody();
//                 model.addAttribute("order", orderDTO);
//                 System.out.println(orderDTO);
//                 // System.out.println(orderId);
//                 return "orderCancel";
//         }

//         @GetMapping("/orderCreate/{customerId}")
//         public String createOrder(@PathVariable("customerId") int customerId, Model model) {
//                 OrderDTO orderDTO = restTemplate.postForObject("http://localhost:8083/api/v1/orders/" + customerId, null, OrderDTO.class);
//                 // OrderDTO orderDTO = response.getBody();
//                 model.addAttribute("order", orderDTO);
//                 System.out.println(orderDTO);
//                 return "orderSuccess";
//         }

//         @GetMapping("/login")
//         public String login() {
//                 return "redirect:http://localhost:8082/login";
//         }

//         @GetMapping("/profile")
//         public String getProfile(Model model) {

//                 CustomerDTO customerDTO =  restTemplate.getForObject("http://localhost:8082/api/v1/customers/"+userId, CustomerDTO.class);

//                 // Add user information and addresses to the model
//                 model.addAttribute("customer", customerDTO);
//                 model.addAttribute("addresses", customerDTO.getAddresses());
//                 model.addAttribute("defaultAddress", customerDTO.getDefaultAddress());
//                 return "profile";
//         }

//         @GetMapping("/editProfile/{customerId}")
//         public String getEditProfile(Model model, @PathVariable("customerId") int customerId) {
//                 CustomerDTO customerDTO =  restTemplate.getForObject("http://localhost:8082/api/v1/customers/"+customerId, CustomerDTO.class);
//                 // Add user information and addresses to the model
//                 model.addAttribute("customer", customerDTO);
//                 return "editProfile";
//         }

//         @PostMapping("/updateProfile/{customerId}")
//         public String updateProfile(@ModelAttribute("customer") CustomerDTO customerDTO) {
//                 customerDTO =  restTemplate.postForObject("http://localhost:8082/api/v1/customers/", customerDTO, CustomerDTO.class);
//                 // Add user information and addresses to the model
//                 return "redirect:/profile";
//         }

//         @GetMapping("/orders")
//         public String getOrders(Model model) {
//                 ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
//                             "http://localhost:8083/api/v1/orders/customer/" + userId,
//                             HttpMethod.GET,
//                             null,
//                             new ParameterizedTypeReference<List<OrderDTO>>() {}
//                 );
//                 List<OrderDTO> orderDTOs = response.getBody();
//                 model.addAttribute("orders", orderDTOs);
//                 return "orders";
//         }

//         @GetMapping("/order/details/{orderId}")
//         public String getOrderDetails(Model model, @PathVariable("orderId") int orderId) {
//                 OrderDTO orderDTO = restTemplate.getForObject("http://localhost:8083/api/v1/orders/" + orderId, OrderDTO.class);
//                 System.out.println(orderDTO);
//                 System.out.println(orderDTO.getOrderItems());
//                 model.addAttribute("order", orderDTO);
//                 model.addAttribute("orderItems", orderDTO.getOrderItems());
//                 return "orderDetails";
//         }

//         @GetMapping("/logout")
//         public String logout() {
//                 return "testcollapse";
//         }

//         @GetMapping("/newaddress/{customerId}")
//         public String newAddress(Model model, @PathVariable("customerId") int customerId) {
//                 System.out.println(customerId);
//                 model.addAttribute("customerId", customerId);
//                 return "newAddress";
//         }

//         @GetMapping("/editaddress/{addressId}")
//         public String editAddress(@PathVariable("addressId") int addressId, Model model) {

//                 AddressDTO addressDTO = restTemplate.getForObject("http://localhost:8082/api/v1/addresses/" + addressId, AddressDTO.class);
//                 System.out.println(addressDTO);
//                 if (addressDTO == null) {
//                         // Handle address not found scenario
//                         return "redirect:/profile";
//                 }

//                 model.addAttribute("address", addressDTO);
//                 return "editAddress";
//         }

//         @PostMapping("/saveAddress/{customerId}")
//         public String saveAddress(@ModelAttribute("address") AddressDTO addressDTO, @PathVariable("customerId") int customerId){
//                 System.out.println(addressDTO);
//                 System.out.println(customerId);
//                 AddressDTO updateAddressDTO = restTemplate.postForObject("http://localhost:8082/api/v1/addresses/", addressDTO, AddressDTO.class);
//                 return "redirect:/profile";
//         }

//         @GetMapping("/address/delete/{addressId}")
//         public String deleteAddress(@PathVariable("addressId") int addressId){
//                 ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8082/api/v1/addresses/" + addressId, HttpMethod.DELETE, null, Void.class);
//                 System.out.println("address deleted");
//                 return "redirect:/profile";
//         }

//         @GetMapping("/address/setDefault/{addressId}")
//         public String setDefaultAddress(@PathVariable("addressId") int addressId){
//                 ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8082/api/v1/customers/" + userId + "/address/" + addressId, HttpMethod.PUT, null, Void.class);
//                 System.out.println("default address updated");
//                 return "redirect:/profile";
//         }

// }
