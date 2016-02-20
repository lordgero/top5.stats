<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<html>
	<head>
		<link rel="stylesheet" href="//fonts.googleapis.com/icon?family=Material+Icons">
		<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,300italic,500,400italic,700,700italic' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="//storage.googleapis.com/code.getmdl.io/1.0.1/material.teal-red.min.css" />
		<script src="//storage.googleapis.com/code.getmdl.io/1.0.1/material.min.js"></script>
		<script src="//d3js.org/d3.v3.min.js" charset="utf-8"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js" charset="utf-8"></script>
		<script src="https://raw.githubusercontent.com/benkeen/d3pie/0.1.8/d3pie/d3pie.min.js"></script>
	</head>
	
	<body>
		<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-drawer">
			<header class="mdl-layout__header">
				<div class="mdl-layout__header-row">
					<i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">people</i>
					<span class="mdl-layout-title">
						Stats de ${joueur}
					</span>
					<div class="mdl-layout-spacer"></div>
					<nav class="mdl-navigation">
						<a class="mdl-navigation__link" href="#">Soldier</a>
						<a class="mdl-navigation__link" href="#">Scout</a>
						<a class="mdl-navigation__link" href="#">Demo</a>
						<a class="mdl-navigation__link" href="#">Medic</a>
					</nav>
				</div>
			</header>
			
			<div class="mdl-layout__drawer">
				<span class="mdl-layout-title">top5stats</span>
				<nav class="mdl-navigation">
					<a class="mdl-navigation__link" href="/stats/">mix</a>
					<a class="mdl-navigation__link" href="#">serv</a>
					<a class="mdl-navigation__link" href="#">cochon</a>
					<a class="mdl-navigation__link" href="#">about</a>
				</nav>
			</div>
			
			<main class="mdl-layout__content mdl-color--grey-100"">
				<div class="mdl-grid main-content">
					<!-- graphes pour le nombre de classes jouées -->
					<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
						 <canvas id="classes" height="60"></canvas>
					</div>
					
					<!-- stats générales : frags, matchs joués, airshoutes -->
					<div class="mdl-cell mdl-cell--12-col mdl-grid">
						<div class="mdl-cell--1-col"></div>
						
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--2-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreMatchs}
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Maps jouzinées</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--2-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreFrags}
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Frags</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--2-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreAirshoutes}
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Airshoutes</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--2-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreDommages} K
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Dommages</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
					</div>
					
					<div class="mdl-cell mdl-cell--12-col mdl-grid">
						<div class="mdl-cell--1-col"></div>
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--3-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreDommagesMoyen}
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Dommages moyen/map</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--3-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreFragsMoyen}
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Moyenne de frags/map</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
						
						<div class="mdl-card mdl-shadow--2dp mdl-cell--3-col">
  							<div class="mdl-card__title mdl-card--expand">
    							<h2>
      								  ${nombreAirshoutesMoyen}
								</h2>
							</div>
							<div class="mdl-card__actions mdl-card--border">
								<span class="demo-card-image__filename">Airshoutes moyen/map</span>
							</div>
						</div>
						
						<div class="mdl-cell--1-col"></div>
					</div>
					
					<!-- barres graphes pour faire joli -->
					<div class="mdl-cell mdl-cell--12-col mdl-grid">
						
					</div>
				</div>
			</main>
			
		</div>
		
	    <script type="application/javascript">
	    var barData = {
	    	    labels: ['Scout', 'Soldier', 'Pyro', 'Demo', 'h00vy', 'Ingé', 'Medic', 'Sniper', 'Spy'],
	    	    datasets: [
	    	        {
	    	            label: '% de classes jouées',
	    	            fillColor: '#2979ff',
	    	            data: [${scout}, ${soldier}, ${pyro}, ${demoman}, ${heavyweapons}, ${engineer}, ${medic}, ${sniper}, ${spy}]
	    	        //"demoman", "medic", "spy", "sniper", "pyro", 
					//   "heavyweapons", "engineer"}
	    	        }
	    	    ]
	    	};
	    Chart.defaults.global.responsive = true;
	    var context = document.getElementById('classes').getContext('2d');
	    var clientsChart = new Chart(context).Bar(barData);
	    </script>
	</body>
	
</html>

