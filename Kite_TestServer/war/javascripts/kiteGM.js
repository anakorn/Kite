<!-- HIDE JS FROM OLDER BROWSERS

var geo;			// Google geocoder, used for translating ADDRESS <--> LAT/LNG

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
		streetViewControl: false,
		zoomControlOptions: {
			position: google.maps.ControlPosition.RIGHT_CENTER
		}
	};

	map 			= new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	map.geo 		= new google.maps.Geocoder();
	map.infoBox 	= new InfoBox();
	map.markers 	= [];
	
	// Initialization code
//	map.addMarkerByAddress(
//			0, 								// Event ID (probably going to be removed)
//			"Game Developers Week @ UCI",	// Event name
//			"Donald Bren Hall"				// Event address
//	);
//	map.addMarker(
//			1, 
//			"[Workshop - Production] Legal Issues, Licenses, and Copyrights Event", 
//			mapStartPos
//	);
};

-->