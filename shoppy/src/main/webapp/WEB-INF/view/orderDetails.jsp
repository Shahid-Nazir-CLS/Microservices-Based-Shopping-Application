<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


            <html>

            <head>
                <title>Order Details</title>
                <!-- Include Bootstrap CSS -->
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
                    integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
                    crossorigin="anonymous">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
            </head>

            <body class="d-flex flex-column min-vh-100 pt-5">

                <!-- Include Navigation Component -->
                <%@ include file="navbar.jsp" %>

                    <div class="container mt-5">
                        <h1 class="title">Order Details</h1>
                        <div class="card card-order mb-4">
                            <div class="card-body">
                                <h5 class="card-title">Order ID: ${order.orderId}</h5>
                                <p class="card-text"><span class="card-lhs">Status:</span>
                                    <c:choose>
                                        <c:when test="${order.status == 'PENDING'}">
                                            Payment confirmation is ${order.status}
                                        </c:when>
                                        <c:otherwise>
                                            ${order.status}
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p class="card-text"><span class="card-lhs">Total Price:</span> ${order.totalPrice}</p>
                                <p class="card-text"><span class="card-lhs">Address:</span> ${order.addressDTO}</p>
                            </div>
                            <div class="order-actions">
                                <c:if test="${order.status != 'CANCELLED'}">
                                    <button class="btn btn-danger ml-2" onclick="confirmCancel(${order.orderId})">Cancel
                                        Order</button>
                                </c:if>
                            </div>
                        </div>

                        <h2 class="mt-4 title">Order Items</h2>
                        <div class="row">
                            <c:forEach items="${orderItems}" var="orderItem">
                                <div class="col-md-4 mb-4">
                                    <div class="card order-item">
                                        <img src="${orderItem.itemDTO.imageURL}"
                                            style="width: 250px; height: 250px; object-fit: cover; margin-left: 50px"
                                            alt="${orderItem.itemDTO.name}">
                                        <div class="card-body">
                                            <h5 class="home-card-title">${fn:substring(orderItem.itemDTO.name, 0, 50)}
                                            </h5>
                                            <p class="card-text"><span class="card-lhs">Order Item ID:</span>
                                                ${orderItem.orderItemid}</p>
                                            <p class="card-text"><span class="card-lhs">Quantity:</span>
                                                ${orderItem.quantity}</p>
                                            <p class="card-text"><span class="card-lhs">Price:</span> ${orderItem.price}
                                            </p>
                                            <p class="card-text"><span class="card-lhs"></span>
                                                ${orderItem.itemDTO.description}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Include Footer Component -->
                    <%@ include file="footer.jsp" %>

                        <script>
                            function confirmCancel(orderId) {
                                if (confirm('Are you sure you want to cancel this order?')) {
                                    // Proceed with cancellation logic here, e.g., redirect to cancel endpoint
                                    window.location.href = '/orderCancel/' + orderId;
                                } else {
                                    // Cancel action
                                }
                            }
                        </script>

            </body>

            </html>