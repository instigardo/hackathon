<!DOCTYPE html>
    <html> 
    <head> 
       <meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
       <title>Google Maps API v3 Directions Example</title> 
       <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js"></script>
       <script type="text/javascript" 
               src="http://maps.google.com/maps/api/js?sensor=false"></script>
    </head> 
    <body style="font-family: Arial; font-size: 12px;"> 
       <div style="width: 600px;">
         <div id="map" style="width: 280px; height: 400px; float: left;"></div> 
         <div id="panel" style="width: 300px; float: right;"></div> 
       </div>
       
       <script type="text/javascript"> 
    
         var directionsService = new google.maps.DirectionsService();
         var directionsDisplay = new google.maps.DirectionsRenderer();
    
         var map = new google.maps.Map(document.getElementById('map'), {
           zoom:7,
           mapTypeId: google.maps.MapTypeId.ROADMAP
         });
        
         directionsDisplay.setMap(map);
         directionsDisplay.setPanel(document.getElementById('panel'));
         var query_string = {};
         var query = window.location.search.substring(1);
         var vars = query.split("&");
         //alert(vars);
    		var orign=vars[0];//jQuery.url.param("origin");//'Nandavanam Road,Palayam,Trivandrum,Kerala';
    		var dest=vars[1];//jQuery.url.param("destination");//'kolkata';
    		
         var request = {
           origin: orign, 
           destination: dest,
           travelMode: google.maps.DirectionsTravelMode.DRIVING
         };
    
         directionsService.route(request, function(response, status) {
           if (status == google.maps.DirectionsStatus.OK) {
             directionsDisplay.setDirections(response);
           }
         });
       </script> 
    </body> 
    </html>
