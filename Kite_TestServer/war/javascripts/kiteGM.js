<!-- HIDE JS FROM OLDER BROWSERS

var map;			// Google map within this current HTML document
var mapOptions;		// Google map options for the current map
var mapStartPos;	// Starting LatLng location
var mapStartZoom;	// Starting zoom amount

function kiteInitializeMap() {
	
	// Map Initialization
	mapStartPos = new google.maps.LatLng(33.6459, -117.8427);
	mapStartZoom = 16;
	mapOptions = {
		center: mapStartPos,
		zoom: mapStartZoom,
		mapTypeId: google.maps.MapTypeId.ROADMAP,
		panControl: false,
		rotateControl: false,
		streetViewControl: false,
		zoomControlOptions: {
			position: google.maps.ControlPosition.RIGHT_CENTER
		}
	};

	map 			= new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	map.infoBox 	= new InfoBox();
	map.markers 	= [];
	map.geo 		= new google.maps.Geocoder();
	map.geoRequestQueue = new Queue();

};