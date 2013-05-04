    <head>
        <meta charset="utf-8">
        <title>Schweizer SGS 1-29</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
		<script src="/resources/js/bootstrap.js"></script>
		<script src="/resources/js/bootstrap.min.js"></script>
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
        <!-- Le styles -->
		<link href="/resources/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
        <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
              }
              .sidebar-nav {
                padding: 9px 0;
              }
            table {
			  width: 100%;
			}
			table,th, td
				{
				border: 1px solid black;
				}
			table a {
				display:block;
   				 text-decoration:none;
			}
        </style>
</head>
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
                            <li>
                                <a href="/home">Home</a>
                            </li>
                            <li>
                                <a href="/login">Log in</a>
                            </li>
                            <li class="active">
                                <a href="/register">Register</a>
                            </li>
                            <li>
                                <a href="/problem">Problem?</a>
                            </li>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>