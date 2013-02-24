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
	map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	
	// Event listener for clearing markers on:
	// - Dragging map
	// - Clicking on map
	google.maps.event.addListener(map, "click", map.clearInfoBoxes);
	// google.maps.event.addListener(map, "click", clearInfoBoxes);
	
	// Initialization code
	map.addMarker(0, mapStartPos, "HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD HELLO WORLD ");
	map.addMarker(1, new google.maps.LatLng(33.6455, -117.8530), "WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT WHAT");
	// map.clearMarkers();
};

-->