<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>

<body class="d-flex flex-column min-vh-100 pt-5">

    <!-- Include Navigation Component -->
    <%@ include file="navbar.jsp" %>


        <!-- Main content -->
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">
                            Order Confirmed
                        </div>
                        <div class="card-body">
                            <div class="order-details">
                                <h4>Thank you for your order!</h4>
                                <h5>Your order has been successfully confirmed.</h5>
                            </div>
                            <div class="order-details">
                                <h4><strong>Order Details</strong></h4>
                                <h5><strong>Order ID:</strong> ${order.orderId}</h5>
                                <h5><strong>Date:</strong> ${order.orderDate}</h5>
                                <h5><strong>Total Amount:</strong> ${order.totalPrice}</h5>
                                <h5><strong>Status:</strong> Payment confirmation ${order.status}.</h5>
                            </div>
                            <div class="text-center">
                                <a href="/" class="btn btn-primary">Back to Shop</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Include Footer Component -->
        <%@ include file="footer.jsp" %>

</body>

</html>