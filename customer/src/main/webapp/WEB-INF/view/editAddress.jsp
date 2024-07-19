<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Address</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

</head>

<body class="d-flex flex-column min-vh-100 pt-5">


    <!-- Navbar (optional, can include from a separate file) -->
    <jsp:include page="navbar.jsp" />

    <div class="container mt-5 mb-5">
        <h1 class="text-center title">Edit Address</h1>
        <div class="d-flex justify-content-center mt-4">
            <div class="col-md-8">
                <div class="card profile-card">
                    <div class="card-body">
                        <form action="/saveAddress/${address.customerId}" method="post" modelAttribute="address">
                            <div class="form-group">
                                <label for="address">Address</label>
                                <input type="text" class="form-control" id="address" name="address"
                                    value="${address.address}" required>
                            </div>
                            <div class="form-group">
                                <label for="city">City</label>
                                <input type="text" class="form-control" id="city" name="city" value="${address.city}"
                                    required>
                            </div>
                            <div class="form-group">
                                <label for="state">State</label>
                                <input type="text" class="form-control" id="state" name="state" value="${address.state}"
                                    required>
                            </div>
                            <div class="form-group">
                                <label for="pincode">Pincode</label>
                                <input type="text" class="form-control" id="pincode" name="pincode"
                                    value="${address.pincode}" required>
                            </div>
                            <div class="form-group">
                                <label for="country">Country</label>
                                <input type="text" class="form-control" id="country" name="country"
                                    value="${address.country}" required>
                            </div>
                            <div class="form-group">
                                <label for="phoneNumber">Phone Number</label>
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                    value="${address.phoneNumber}" required>
                            </div>
                            <input type="hidden" name="addressId" value="${address.addressId}">
                            <input type="hidden" name="customerId" value="${address.customerId}">
                            <button type="submit" class="btn btn-primary">Update Address</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Include Footer Component -->
    <%@ include file="footer.jsp" %>
</body>

</html>