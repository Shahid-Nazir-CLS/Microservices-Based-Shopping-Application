<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <title>Edit Profile</title>
                <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

            </head>

            <body class="d-flex flex-column min-vh-100 pt-5">
                <%@ include file="navbar.jsp" %>

                    <div class="container mt-5 mb-5">
                        <h1 class="text-center title">Edit Profile</h1>
                        <div class="d-flex justify-content-center mt-4">
                            <div class="col-md-8">
                                <div class="card profile-card">
                                    <div class="card-body">
                                        <form action="/updateProfile/${customer.customerId}" method="post"
                                            modelAttribute="customer">
                                            <div class="form-group">
                                                <label for="username">Username</label>
                                                <input type="text" class="form-control" id="username" name="username"
                                                    value="${customer.username}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="email">Email</label>
                                                <input type="text" class="form-control" id="email" name="email"
                                                    value="${customer.email}" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="profilePic">Profile Pic</label>
                                                <input type="text" class="form-control" id="profilePic"
                                                    name="profilePic" value="${customer.profilePic}" required>
                                            </div>
                                            <input type="hidden" name="customerId" value="${customer.customerId}">
                                            <button type="submit" class="btn btn-primary">Update Profile</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%@ include file="footer.jsp" %>

                        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
                        <script
                            src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
                        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
            </body>

            </html>