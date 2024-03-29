<!-- HIDE JS FROM OLDER BROWSERS

// Marker.eventId for tracking event info
google.maps.Marker.prototype.eventId;

// Map.geo for geocoding addresses
google.maps.Map.prototype.geo;

// Map.geoRequestQueue for processing geocoding requests
google.maps.Map.prototype.geoRequestQueue;

// Map.markers tracks all markers on map
google.maps.Map.prototype.markers;

// Map.oms used to expand overlapping markers into multiple markers
google.maps.Map.prototype.oms;

// Map.infoBox displays a marker's info
google.maps.Map.prototype.infoBox;

// Marker.showInfo will show a marker's info
google.maps.Marker.prototype.showInfo;

// Marker.hideInfo will hide a marker's info
google.maps.Marker.prototype.hideInfo;

google.maps.Map.prototype.addGeocodeRequest = function(eventId, eventName, address) {
	
	// Enqueue the geocode function as a request
	this.geoRequestQueue.enqueue(function() {
		map.geo.geocode({ "address": address }, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				map.addMarker(eventId, eventName, results[0].geometry.location);
			}
			else if (status == google.maps.GeocoderStatus.ZERO_RESULTS){
				console.log("Geocode was not successful for the following reason: " + status);
			}
			else {
				alert("Geocode was not successful for the following reason: " + status);
			}
			
			// Process remaining requests after this geocoded address is found
			map.processGeocodeRequests();
		});
	})
};

google.maps.Map.prototype.processGeocodeRequests = function() {
	
	if (!this.geoRequestQueue.isEmpty()) {
		this.geoRequestQueue.dequeue()();
	}
	else {
		toggleLoadpage(false);
	}
};

google.maps.Map.prototype.addMarker = function(eventId, eventName, latLng) {

	// Create the event marker
	var marker = new google.maps.Marker({
		icon: "red-dot.png",
		position: latLng,
		map: this,
		eventId: eventId
	});
	
	var infoBoxOptions = {
		content: eventName,
	    boxStyle: {
	    	background: "#E0E4F0",
	        border: "1px solid grey",
	        textAlign: "center",
	        font: "10pt",
	        width: "200px",
	        padding: "2px"
	    },
		disableAutoPan: true,
	    pixelOffset: new google.maps.Size(-100, 0),
	    position: latLng,
	    closeBoxURL: "",
	    isHidden: false,
	    pane: "mapPane",
	    enableEventPropagation: true
	};

	// Marker.showInfo should highlight, display text, and center to marker
	marker.showInfo = function() {
		map.infoBox.setOptions(infoBoxOptions);
		map.infoBox.open(map, this);
		this.setIcon("yellow-dot.png");
	};
	
	marker.hideInfo = function() {
		map.infoBox.close();
		this.setIcon("red-dot.png");
	};
	
	// OMS-handled: Event handler "click" for expanding the event list item
	this.oms.addListener(marker, "click", function() {
		
	});
	
	// Event handler "click" for expanding the event list item
	google.maps.event.addListener(marker, "click", function() {
		expandListItem("#" + marker.eventId);
	});

	// Event handler "mouseover" for label text and marker highlight
	google.maps.event.addListener(marker, "mouseover", marker.showInfo);

	// Event handler "mouseout" for label text and marker highlight
	google.maps.event.addListener(marker, "mouseout", marker.hideInfo);

	// Store in Marker array and track with oms
	this.markers.push(marker);
	this.oms.addMarker(marker);
};

// Removes all markers from the map
google.maps.Map.prototype.clearMarkers = function() {
	
	// This function should only be called by kite.js during loading JSON events.
	// Page needs to be loaded right after this function call.
	toggleLoadpage(true);
	
	for (var i = 0; i < this.markers.length; ++i) {
		this.markers[i].setMap(null);
		this.markers[i] = null;
	}
	this.markers = [];
	this.oms.clearMarkers();
};

// Get marker, given its ID
google.maps.Map.prototype.getMarker = function(eventId) {
	
	for (var i = 0; i < this.markers.length; i++) {
		if (this.markers[i].eventId == eventId)
			return this.markers[i];
	}
	return null;
};

-->