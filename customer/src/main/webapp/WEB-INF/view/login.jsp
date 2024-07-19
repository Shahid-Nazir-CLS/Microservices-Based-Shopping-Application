<!DOCTYPE html>
<html>

<head>
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

</head>

<body class="d-flex flex-column min-vh-100 pt-5">
    <!-- Include Navigation Component -->
    <%@ include file="navbar.jsp" %>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card mt-5">
                        <div class="card-body">
                            <h3 class="card-title text-center">Login</h3>
                            <form action="${pageContext.request.contextPath}/login" method="post">
                                <div class="form-group">
                                    <label for="username">Username</label>
                                    <input type="text" id="username" name="username" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" id="password" name="password" class="form-control" required>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block">Login</button>
                            </form>
                            <hr>
                            <h5 class="text-center">Or</h5>
                            <a href="${pageContext.request.contextPath}/oauth2/authorization/google"
                                class="btn btn-primary btn-block">
                                <img src="https://steelbluemedia.com/wp-content/uploads/2019/06/new-google-favicon-512.png"
                                    alt="Google Logo" style="width:20px; height:20px; margin-right:10px;">
                                Login with Google
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Include Footer Component -->
        <%@ include file="footer.jsp" %>
            <!-- Bootstrap JS and dependencies -->
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>

</html>