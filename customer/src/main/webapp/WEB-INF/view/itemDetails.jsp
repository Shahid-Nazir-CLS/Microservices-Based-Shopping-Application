<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Item Details</title>
            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
                integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
                crossorigin="anonymous">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
        </head>

        <body>

            <!-- Include Navigation Component -->
            <%@ include file="navbar.jsp" %>

                <!-- Main content -->
                <div class="container mt-5">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card">
                                <img src="${item.imageURL}" class="card-img-top" alt="${item.name}">
                                <div class="card-body">
                                    <h5 class="card-title">${item.name}</h5>
                                    <p class="card-text">${item.description}</p>
                                    <p class="card-text"><strong>Price: $${item.price}</strong></p>
                                    <c:choose>
                                        <c:when test="${item.quantity == 0}">
                                            <button href="#" class="btn btn-primary disabled">Add to Cart</button>
                                            <p class="out-stock">Out of stock</p>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${isLoggedIn and not empty username}">
                                                    <button onclick="addItem(${item.id})" class="btn btn-primary">Add to
                                                        Cart</button>
                                                    <p>In stock</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <button onclick="showLoginMessage()" class="btn btn-primary">Add to
                                                        Cart</button>
                                                    <p>In stock</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Include Footer Component -->
                <%@ include file="footer.jsp" %>

                    <script>
                        function addItem(itemId) {
                            window.location.href = '/addItem/' + itemId + "/home";
                        }

                        function showLoginMessage() {
                            alert('Please login first to add items to your cart.');
                        }
                    </script>
        </body>

        </html>