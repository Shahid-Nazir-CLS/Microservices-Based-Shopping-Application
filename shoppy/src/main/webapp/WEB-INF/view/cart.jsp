<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping Cart</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
            integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
        <!-- Custom styles -->
        <style>
            .cart-img {
                height: 400px;
                width: 300px;
                margin-left: 50px;
            }
        </style>
    </head>

    <body class="d-flex flex-column min-vh-100 pt-5">

        <!-- Include Navigation Component -->
        <%@ include file="navbar.jsp" %>

            <div class="container mt-4">
                <h1 class="text-center title mb-4">Shopping Cart</h1>
                <div class="row justify-content-center">
                    <c:choose>
                        <c:when test="${empty cartItems}">
                            <div class="card"
                                style="margin: 100px auto; padding: 20px; border: 2px solid #e0e0e0; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 800px;">
                                <div class="card-body"
                                    style="background-color: #f8f9fa; padding: 20px; border-radius: 8px;">
                                    <h5 class="card-title" style="color: #dc3545; font-size: 48px; font-weight: bold;">
                                        Cart Empty</h5>
                                    <p class="card-text" style="color: #000000; font-size: 18px;">Shopping Cart is
                                        empty. Please add items to the cart to proceed.</p>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- Cart Items -->
                            <div class="col-md-7">
                                <!-- Cart Items -->
                                <c:forEach items="${cartItems}" var="item">
                                    <div class="card col-md-9" data-item-id="${item.itemDTO.id}"
                                        data-quantity="${item.quantity}"
                                        data-remaining="${item.itemDTO.remainingQuantity}">
                                        <img src="${item.itemDTO.imageURL}" class="cart-img" alt="${item.itemDTO.name}">
                                        <div class="card-body">
                                            <h5 class="card-title">${item.itemDTO.name}</h5>
                                            <h5 class="card-title">Rem Quantity : ${item.itemDTO.remainingQuantity}</h5>
                                            <p class="card-text">${item.itemDTO.description}</p>
                                            <p class="card-text"><strong>$ ${item.itemDTO.price}</strong></p>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div class="input-group mb-3 quantity-input">
                                                    <div class="input-group-append">
                                                        <button
                                                            class="btn btn-outline-primary btn-quantity btn-increase"
                                                            type="button"
                                                            onclick="updateQuantity(${item.cartItemId}, ${item.quantity + 1})">+</button>
                                                        <input type="text"
                                                            class="form-control text-center quantity-text"
                                                            value="${item.quantity}" readonly>
                                                        <button
                                                            class="btn btn-outline-primary btn-quantity btn-decrease"
                                                            type="button"
                                                            onclick="updateQuantity(${item.cartItemId}, ${item.quantity - 1})">-</button>
                                                    </div>
                                                </div>
                                                <button class="btn btn-delete btn-md" type="button"
                                                    onclick="removeItem(${item.cartItemId})">Delete</button>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                                <!-- Clear Cart Button -->
                                <div class="mt-3 btns-footer">
                                    <button class="btn btn-clear" type="button" onclick="clearCart()">Clear
                                        Cart</button>

                                    <c:if test="${not empty address}">
                                        <input type="hidden" id="addressExists" value="true">
                                    </c:if>
                                    <c:if test="${empty address}">
                                        <input type="hidden" id="addressExists" value="false">
                                    </c:if>
                                    <button class="btn btn-clear btn-buy" type="button"
                                        onclick="checkAddressAndBuy(${customerId})">Buy Items</button>
                                </div>
                            </div>
                            <!-- Addresses Panel -->
                            <c:if test="${address != null}">
                                <div class="col-md-5">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title">Deliver to Address</h5>
                                            <form>
                                                <div class="card card-address mb-3">
                                                    <div class="card-body">
                                                        <p>
                                                            <strong>Address:</strong> ${address.address}<br>
                                                            <strong>City:</strong> ${address.city}<br>
                                                            <strong>State:</strong> ${address.state}<br>
                                                            <strong>Pincode:</strong> ${address.pincode}<br>
                                                            <strong>Country:</strong> ${address.country}<br>
                                                            <strong>Phone Number:</strong> ${address.phoneNumber}
                                                        </p>
                                                        <div class="d-flex justify-content-end mt-3">
                                                            <button class="btn btn-sm btn-primary mr-2" type="button"
                                                                onclick="location.href='/editaddress/${address.addressId}'">Edit</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                            <button class="btn btn-primary mt-3" type="button"
                                                onclick="location.href='/newaddress/${customerId}'">Add New
                                                Address</button>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Include Footer Component -->
            <%@ include file="footer.jsp" %>
                <script>
                    // createOrder
                    function checkAddressAndBuy(customerId) {
                        var addressExists = document.getElementById('addressExists').value;
                        var itemsValid = true;

                        // Loop through each item card to validate quantities
                        document.querySelectorAll('.card').forEach(function (card) {
                            var quantity = parseInt(card.getAttribute('data-quantity'));
                            var remaining = parseInt(card.getAttribute('data-remaining'));

                            if (quantity > remaining) {
                                itemsValid = false;
                                alert('Quantity for item ' + card.querySelector('.card-title').innerText + ' exceeds remaining stock.');
                                return false; // Exit loop if any item is invalid
                            }
                        });

                        if (!itemsValid) {
                            return; // Exit function if validation fails
                        }

                        if (addressExists === 'true') {
                            location.href = '/orderCreate/' + customerId;
                        } else {
                            if (confirm('Default address not given, please add it. Do you want to add an address now?')) {
                                location.href = '/profile';
                            }
                        }
                    }

                    // function to remove item
                    function removeItem(cartItemId) {
                        window.location.href = '/removeItem/' + cartItemId;
                    }

                    // function to clear cart
                    function clearCart() {
                        window.location.href = '/clearCart';
                    }

                    // function to update quantity
                    function updateQuantity(cartItemId, newQuantity) {
                        window.location.href = '/updateQuantity/' + cartItemId + "?quantity=" + newQuantity;
                    }
                </script>
    </body>

    </html>