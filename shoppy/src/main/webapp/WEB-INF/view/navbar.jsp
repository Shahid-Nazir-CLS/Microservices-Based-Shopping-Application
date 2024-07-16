<!-- Navbar with Search Bar -->
<nav class="navbar navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="/">My Shopping App</a>

    <div class="d-flex ml-auto">
        <form class="form-inline" method="GET" action="/search">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" name="keyword">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
        <ul class="navbar-nav ml-3">
            <li class="nav-item mr-3 mt-1">
                <a class="btn btn-outline-success cart" href="/cart">&#128722; Cart</a>
                <a href="/profile"><span class="user-name">Shahid Nazir</span></a>
                <a href="/login"><span class="user-name">Login</span></a>
            </li>
        </ul>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>

    <div class="collapse navbar-collapse bg-body-tertiary" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link nav-items" href="/profile">Profile Information</a>
            </li>
            <li class="nav-item">
                <a class="nav-link nav-items" href="/orders">View Orders</a>
            </li>
            <li class="nav-item">
                <a class="nav-link nav-items" href="/">Logout</a>
            </li>
        </ul>
    </div>
</nav>

<!-- JavaScript to close navbar collapse on clicking outside and hover out -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        var myNavbar = document.querySelector('.navbar');

        // Function to close navbar collapse when clicking outside
        function closeNavbarCollapse(e) {
            if (!myNavbar.contains(e.target)) {
                var navbarCollapse = myNavbar.querySelector('.navbar-collapse');
                if (navbarCollapse.classList.contains('show')) {
                    navbarCollapse.classList.remove('show');
                }
            }
        }

        // Event listener for clicking outside the navbar
        document.addEventListener('click', closeNavbarCollapse);

        // Event listener for hovering out of the navbar
        myNavbar.addEventListener('mouseleave', function () {
            var navbarCollapse = myNavbar.querySelector('.navbar-collapse');
            if (navbarCollapse.classList.contains('show')) {
                navbarCollapse.classList.remove('show');
            }
        });
    });
</script>