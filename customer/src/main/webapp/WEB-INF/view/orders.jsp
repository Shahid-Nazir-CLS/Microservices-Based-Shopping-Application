<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <html>

        <head>
            <title>My Orders</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
                integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
                crossorigin="anonymous">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
        </head>

        <body class="d-flex flex-column min-vh-100 pt-5">

            <!-- Include Navigation Component -->
            <%@ include file="navbar.jsp" %>

                <div class="container mt-5">
                    <h2 class="mb-4 title">My Orders</h2>
                    <c:forEach items="${orders}" var="order">
                        <div class="card order-card">
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
                                <div class="order-actions">
                                    <a href="/order/details/${order.orderId}" class="btn btn-primary">View Details</a>
                                    <c:if test="${order.status != 'CANCELLED'}">
                                        <button class="btn btn-danger ml-2"
                                            onclick="confirmCancel(${order.orderId})">Cancel Order</button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
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
                    </div>
        </body>

        </html>