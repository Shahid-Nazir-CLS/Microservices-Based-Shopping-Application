<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile Information</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
            integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

    </head>

    <body class="d-flex flex-column min-vh-100 pt-5">


        <!-- Include Navigation Component -->
        <%@ include file="navbar.jsp" %>

            <div class="container mt-5">
                <h1 class="text-center title">Profile Information</h1>

                <div class="d-flex justify-content-center">
                    <div class="card-profile col-md-6 ">
                        <img src="${customer.profilePic}" class="card-img-top" alt="User Photo" width="150px">

                        <div class="card-body">
                            <h5 class="card-title">${customer.username}</h5>
                            <p class="card-text">${customer.email}</p>
                            <button class="btn btn-primary"
                                onclick="location.href='/editProfile/${customer.customerId}'">Edit Profile</button>
                        </div>
                    </div>
                </div>
                <hr>
                <h2 class="title">Addresses</h2>
                <div>
                    <button class="btn btn-primary new-address"
                        onclick="location.href='/newaddress/${customer.customerId}'">Add New Address</button>
                </div>
                <div class="row">
                    <c:forEach items="${addresses}" var="address">
                        <div class="col-md-6 mb-4">
                            <div class="card">
                                <div class="card-body">
                                    <p>
                                        <strong>Address:</strong> ${address.address}<br>
                                        <strong>City:</strong> ${address.city}<br>
                                        <strong>State:</strong> ${address.state}<br>
                                        <strong>Pincode:</strong> ${address.pincode}<br>
                                        <strong>Country:</strong> ${address.country}<br>
                                        <strong>Phone Number:</strong> ${address.phoneNumber}
                                    </p>
                                    <c:choose>
                                        <c:when test="${defaultAddress == address.addressId}">
                                            <p><em><strong> Default Address</strong></em></p>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-primary"
                                                onclick="location.href='/address/setDefault/${address.addressId}'">Set
                                                as
                                                default</button>
                                        </c:otherwise>
                                    </c:choose>
                                    <button class="btn btn-primary"
                                        onclick="location.href='/editaddress/${address.addressId}'">Edit</button>
                                    <button class="btn btn-danger"
                                        onclick="location.href='/address/delete/${address.addressId}'">Remove</button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Include Footer Component -->
            <%@ include file="footer.jsp" %>

    </body>

    </html>