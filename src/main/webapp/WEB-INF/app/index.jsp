<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="#">

	<title>${title}</title>

	<!-- Multi-Level Push Menu	-->
	<link rel="stylesheet" type="text/css" href="css/jquery.navgoco.css" media="screen" />

	<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.css" rel="stylesheet">

	<link href="css/font-awesome.css" rel="stylesheet">

    <!-- Add IntroJs styles -->
    <link href="ui/intro.js/introjs.css" rel="stylesheet">

	<link rel="stylesheet" type="text/css" href="css/grid.src.css">
</head>

<body>

	<!--Static navbar-->
	<div class="navbar navbar-default navbar-static-fixed">
		<div class="nav-header">
			<div class="navbar-header">
				<div class="navbar-toggle collapsed navbar-toggle-plus" data-toggle="collapse" data-target=".navbar-collapse-plus">
					<div>
						<span class="icon-bar pull-left block-solid"></span>
						<span class="icon-bar pull-right"></span>
					</div>
					<div>
						<span class="icon-bar pull-left"></span>
						<span class="icon-bar pull-right"></span>
					</div>
				</div>
				<div class="navbar-toggle collapsed navbar-toggle-menu" data-toggle="offcanvas" data-target=".row-offcanvas">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</div>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div style="height: 1px;" class="navbar-collapse collapse navbar-collapse-plus">
				<button id="add">Add new Tab</button>
					<!-- <ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
						<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li class="dropdown-header">Nav header</li>
						<li><a href="#">Separated link</a></li>
						<li><a href="#">One more separated link</a></li>
						</ul>
					</li>
				</ul> -->
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">username</a></li>
					<li>
						<a id="Notifications" href="#"  title="Notifications" id="nav-plus-notice">
							<span class="fa fa-bell hidden-xs"><span class="badge">42</span></span>
							<div class="collapsed visible-xs">
								<span class="fa fa-bell"></span>
								<span>Notifications<span class="badge pull-right">42</span></span>
							</div>
						</a>
					</li>
					<li>
						<a href="#" title="Send massage" id="nav-plus-massage">
							<span class="fa fa-envelope hidden-xs"><span class="badge">1</span></span>
							<div class="collapsed visible-xs">
								<span class="fa fa-envelope"></span>
								<span>Send massage<span class="badge pull-right">1</span></span>
							</div>
						</a>
					</li>
					<li>
						<a href="#" title="Apps" rel="custom-popover" data-target="apps">
							<span class="fa fa-th hidden-xs"></span>
							<div class="collapsed visible-xs">
								<span class="fa fa-th"></span>
								<span>Apps</span>
							</div>
						</a>
						<div id="apps-popover" class="popover bottom hidden-xs hidden">
							<div class="arrow"></div>
							<h3 class="popover-title">Popover bottom</h3>

							<div class="popover-content">
								<p>Sed posuere consectetur est at lobortis. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.</p>
							</div>
						</div>
					</li>
					<li>
						<a href="#" title="Guide" id="nav-plus-guide">
							<span class="fa fa-question-circle hidden-xs"></span>
							<div class="collapsed visible-xs">
								<span class="fa fa-question-circle"></span>
								<span>Guide</span>
							</div>
						</a>
					</li>
					<li>
						<a href="#" title="Profile" rel="custom-popover" data-target="profile">
							<span class="fa fa-user hidden-xs"></span>
							<div class="collapsed visible-xs">
								<span class="fa fa-sign-out"></span>
								<span>Sign out</span>
							</div>
						</a>
						<div id="profile-popover" class="popover bottom">
							<div class="arrow"></div>
							<h3 class="popover-title">Popover bottom</h3>

							<div class="popover-content">
								<p>Sed posuere consectetur est at lobortis. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.</p>
								<button type="button" class="btn btn-default">Sign out</button>
							</div>
						</div>
					</li>
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</div>
	<!--/Static navbar-->

	<!--Fixed navbar-->
	<div class="navbar navbar-default nav-fixedbar navbar-collapse collapse" data-toggle="tooltip" data-placement="buttom" title="Double click me can scroll to top">
		<div class="">
			<div class="nav pull-right hidden nav-plus">
				<div class="nav-fixedbar-plus">
					<div class="nav-fixedbar-plus-item-container">
						<div class="nav-fixedbar-plus-item">
							<a href="#"><span class="fa fa-bell"></span></a>
						</div>
					</div>
					<div class="nav-fixedbar-plus-item-container">
						<div class="nav-fixedbar-plus-item">
							<a href="#"><span class="fa fa-envelope"></span></a>
						</div>
					</div>
				</div>
			</div>

			<div class="navbar-header">
				<div id="sidebar-collapse">
					<a href="#" class="btn btn-xs" data-toggle="offcanvas" data-target=".row-offcanvas">
						<span class="fa fa-th-list"></span>
					</a>
				</div>
			</div>

			<div class="navbar-tabs sliding-active nav-plus-invisible">
				<a href="#" class="tabs-prev hidden"><span class="fa fa-chevron-left"></span></a>
				<a href="#" class="tabs-next hidden"><span class="fa fa-chevron-right"></span></a>
				<div class="nav-tabs-wrap">
					<ul id="nav-tabs" class="nav-tabs">
						<li class="active"><a data-toggle="tab" href="#home"><button class="close closeTab" type="button" >×</button>Home</a></li>
						<li><a data-toggle="tab" href="#myprofile"><button class="close closeTab" type="button" >×</button>Profile</a></li>
					</ul>
				</div>
			</div>

		</div>
	</div>
	<!--/ixed navbar-->

	<div class="row row-offcanvas row-offcanvas-left">
		<!---sidebar-->
		<div class="col-xs-11 col-sm-4 col-md-3 col-lg-2 sidebar-offcanvas" id="sidebar" role="navigation">
			<div class="well sidebar-nav">

				<ul id="menu" class="navgoco">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">News and Events</a>
						<ul>
							<li><a href="#" data-link='signin.html' data-index='321'>Hot News</a></li>
							<li><a href="#" data-link=' http://www.google.com/ncr'  data-outlink='true'>RSS Feeds</a></li>
							<li><a href="#" data-link='http://www.wikipedia.org'  data-outlink='true'>Peach Events</a></li>
							<li><a href="#">User Groups</a></li>
						</ul>
					</li>
					<li><a href="#">About</a>
						<ul>
							<li><a href="#">Public Relations</a></li>
							<li><a href="#">Investors</a></li>
							<li><a href="#">Working at Peach</a></li>
							<li><a href="#">Environment</a></li>
							<li><a href="#">Contact Us</a>
								<ul>
									<li><a href="#">Support and Service</a></li>
									<li><a href="#">Product Feedback</a></li>
									<li><a href="#">Website Feedback</a></li>
								</ul>
							</li>
							<li><a href="#">Working with Peach</a>
								<ul>
									<li><a href="#">Procurement</a></li>
									<li><a href="#">Supplier Responsibility</a></li>
								</ul>
							</li>
						</ul>
					</li>
					<li><a href="#">Where to Buy</a>
						<ul>
							<li><a href="#">Where can I buy Peach products?</a></li>
							<li><a href="#">Peach Online Store</a></li>
							<li><a href="#">Peach Store for Business</a></li>
							<li><a href="#">Peach Store for Education</a></li>
							<li><a href="#">Peach Online Store Country Selector</a></li>
							<li><a href="#">Peach Retail</a></li>
							<li><a href="#">Find a Reseller</a></li>
							<li><a href="#">Peach Financial Services</a></li>
							<li><a href="#">Peach Rebates</a></li>
						</ul>
					</li>
				</ul>
			</div><!--/.well -->
		</div><!--/span-->
		<!---/sidebar-->

		<!--content-->
		<div class="col-xs-12 col-sm-8 col-md-9 col-lg-10 pull-right content">
			<div id="tab-content" class="tab-content">
				<div class="tab-pane active" id="home">
					<div class="jumbotron">
						<h1>Hello, world!</h1>
						<p>This is an example to show the potential of an offcanvas layout pattern in Bootstrap. Try some responsive-range viewport sizes to see it in action.</p>
					</div>
					<div class="row">
						<div class="col-6 col-sm-6 col-lg-4">
							<h2>Heading</h2>
							<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
							<p><a class="btn btn-default" href="#">View details &raquo;</a></p>
						</div><!--/span-->
						<div class="col-6 col-sm-6 col-lg-4">
							<h2>Heading</h2>
							<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
							<p><a class="btn btn-default" href="#">View details &raquo;</a></p>
						</div><!--/span-->
						<div class="col-6 col-sm-6 col-lg-4">
							<h2>Heading</h2>
							<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
							<p><a class="btn btn-default" href="#">View details &raquo;</a></p>
						</div><!--/span-->
						<div class="col-6 col-sm-6 col-lg-4">
							<h2>Heading</h2>
							<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
							<p><a class="btn btn-default" href="#">View details &raquo;</a></p>
						</div><!--/span-->
						<div class="col-6 col-sm-6 col-lg-4">
							<h2>Heading</h2>
							<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
							<p><a class="btn btn-default" href="#">View details &raquo;</a></p>
						</div><!--/span-->
						<div class="col-6 col-sm-6 col-lg-4">
							<h2>Heading</h2>
							<p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
							<p><a class="btn btn-default" href="#">View details &raquo;</a></p>
						</div><!--/span-->
					</div><!--/row-->

					<hr>
					<footer>
						<p>&copy; Company 2013</p>
					</footer>

				</div>

				<div class="tab-pane" id="myprofile">
					<h1>Orange</h1>
				</div>
			</div><!--/span-->
		</div><!--/row-->
		<!--/content-->

	</div><!--/.container--> 

		<!--[if gte IE 9]>
			<script src='js/jquery.js'></script>
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src='js/jquery-1.10.2.js'></script>
			<script src="js/html5shiv.js"></script>
			<script src="js/respond.min.js"></script>
			<script src="js/es5-shim.js"></script>
		<![endif]-->

		<!-- Bootstrap core JavaScript
		================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src='js/jquery.js'></script>
		<script src="js/bootstrap.js"></script>
		<script src="js/less.js"></script>


		<script type="text/javascript" src="ui/intro.js/intro.js"></script>
		<!-- Multi-Level Menu  -->
		<script type="text/javascript" src="js/jquery.navgoco.js"></script>

		<script type="text/javascript" src="js/grid.src.js"></script>
		<script type="text/javascript">
			var mobile = false, guided = false;
		</script>
	</body>
</html>