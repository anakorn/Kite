<!-- HIDE JS FROM OLDER BROWSERS

var geo;			// Google geocoder, used for translating ADDRESS <--> LAT/LNG

var map;			// Google map within this current HTML document
var mapOptions;		// Google map options for the current map
var mapStartPos;	// Starting LatLng location
var mapStartZoom;	// Starting zoom amount

function kiteInitializeMap() {

	// Geocoder Initialization
	geo = new google.maps.Geocoder();
	
	// Map Initialization
	mapStartPos = new google.maps.LatLng(33.6459, -117.8427);
	mapStartZoom = 16;
	mapOptions = {
		center: mapStartPos,
		zoom: mapStartZoom,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	alert(document.getElementById("map_canvas") == null);
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	
	// Event listener for clearing markers on:
	// - Mouse movement
	google.maps.event.addListener(map, "mousemove", map.clearInfoBox);
	
	// Initialization code
	map.addMarker(0, mapStartPos, "Game Developers Week @ UCI");
	map.addMarker(1, new google.maps.LatLng(33.6455, -117.8530), "[Workshop - Production] Legal Issues, Licenses, and Copyrights Event");
	// map.clearMarkers();
};

-->