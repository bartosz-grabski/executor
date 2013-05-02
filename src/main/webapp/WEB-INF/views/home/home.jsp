<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Schweizer SGS 1-29</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
		<script src="resources/js/bootstrap.js"></script>
		<script src="resources/js/bootstrap.min.js"></script>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
        <!-- Le styles -->
		<link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
        <link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
              }
              .sidebar-nav {
                padding: 9px 0;
              }
        </style>
</head>

    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
 <span class="icon-bar"></span>
 <span class="icon-bar"></span>

                    </a>
                    <a class="brand" href="#">Schweizer SGS 1-29</a>
                    <div class="nav-collapse collapse">
                        <c:if test="${not empty username}">
                        		<p class="navbar-text pull-right">Logged in as
                                    <a href="#" class="navbar-link">${username}</a>
                                </p>
                        </c:if>

                        <ul class="nav">
                            <li class="active">
                                <a href="/home">Home</a>
                            </li>
                            <li>
                                <a href="/login">Log in</a>
                            </li>
                            <li>
                                <a href="/register">Register</a>
                            </li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row-fluid">

                <!--/span-->
                <div class="span12 pagination-centered">
                    <div class="hero-unit">
                         <h1 style="margin-bottom:20px">Log in or register!</h1>
                        <p>
                            <a href="/register" class="btn btn-primary btn-large">Register</a>
							<a href="/login" class="btn btn-primary btn-large">Log in!</a>
                        </p>
                    </div>
                </div>
                <!--/span-->
            </div>
            <!--/row-->
            <hr>
            <footer>
                <p>Copyright : BIT Idea Factory 2013</p>
            </footer>
        </div>
        <!--/.fluid-container-->
        <!-- Le javascript==================================================-
        ->
    <!-- Placed at the end of the document so the pages load faster -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js">
</script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script src="/resources/js/main.js"></script>
</body>

</html>