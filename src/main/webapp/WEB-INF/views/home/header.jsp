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