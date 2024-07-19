<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Simple Shopping App</title>
            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
                integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
                crossorigin="anonymous">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

        </head>

        <body class="d-flex flex-column min-vh-100 pt-5">

            <!-- Include Navigation Component -->
            <%@ include file="navbar.jsp" %>

                <!-- Main Content -->
                <div class="container mt-5">
                    <div class="row">

                        <!-- if items empty then show no items presen -->
                        <c:choose>
                            <c:when test="${empty items}">
                                <div class="card"
                                    style="margin:  100px auto; padding: 20px; border: 2px solid #e0e0e0; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 800px;">
                                    <div class="card-body"
                                        style="background-color: #f8f9fa; padding: 20px; border-radius: 8px;">
                                        <h5 class="card-title"
                                            style="color: #dc3545; font-size: 48px; font-weight: bold;">No items present
                                        </h5>
                                        <p class="card-text" style="color: #000000; font-size: 18px;">There are no items
                                            to display for keyword "${keyword}". Please check back
                                            later.</p>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>

                                <!-- Iterate over products list -->
                                <c:forEach items="${items}" var="item">
                                    <div class="col-md-4 mb-4">
                                        <div class="card">
                                            <img src="${item.imageURL}"
                                                style="width: 200px; height: 200px; object-fit: cover; margin-left: 50px"
                                                alt="${item.name}">
                                            <div class="card-body">
                                                <h5 class="home-card-title">${fn:substring(item.name, 0, 50)}</h5>
                                                <h5 class="card-title">$ ${item.price}</h5>
                                                <p class="card-text">${fn:substring(item.description, 0, 150)}...</p>
                                                <a href="/itemDetails/${item.id}" class="btn btn-primary">View
                                                    Details</a>
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <c:choose>
                                                    <c:when test="${item.quantity == 0}">
                                                        <button href="#" class="btn btn-primary disabled">Add to
                                                            Cart</button>
                                                        <p class="out-stock">Out of stock</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${isLoggedIn and not empty username}">
                                                                <button onclick="addItem(${item.id})"
                                                                    class="btn btn-primary">Add to Cart</button>
                                                                <p>In stock</p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button onclick="showLoginMessage()"
                                                                    class="btn btn-primary">Add to Cart</button>
                                                                <p>In stock</p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <c:choose>
                        <c:when test="${empty items or not linksProvided}">

                        </c:when>
                        <c:otherwise>
                            <!-- Pagination -->
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <c:choose>
                                        <c:when test="${currPageNumber > 0}">
                                            <li class="page-item">
                                                <a class="page-link font-weight-bold" href="/?page=0">First</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link font-weight-bold"
                                                    href="/?page=${currPageNumber - 1}">Previous</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item disabled">
                                                <a class="page-link font-weight-bold" href="#" tabindex="-1">First</a>
                                            </li>
                                            <li class="page-item disabled">
                                                <a class="page-link font-weight-bold" href="#"
                                                    tabindex="-1">Previous</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${currPageNumber < totalPages - 1}">
                                            <li class="page-item">
                                                <a class="page-link font-weight-bold"
                                                    href="/?page=${currPageNumber + 1}">Next</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link font-weight-bold"
                                                    href="/?page=${totalPages - 1}">Last</a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item disabled">
                                                <a class="page-link font-weight-bold" href="#" tabindex="-1">Next</a>
                                            </li>
                                            <li class="page-item disabled">
                                                <a class="page-link font-weight-bold" href="#" tabindex="-1">Last</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </nav>


                        </c:otherwise>
                    </c:choose>

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