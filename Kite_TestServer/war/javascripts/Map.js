<!-- HIDE JS FROM OLDER BROWSERS

// Marker.eventId for tracking event info
google.maps.Marker.prototype.eventId;

// Map.geo for geocoding addresses
google.maps.Map.prototype.geo;

// Map.geoRequestQueue for processing geocoding requests
google.maps.Map.prototype.geoRequestQueue;

// Map.markers tracks all markers on map
google.maps.Map.prototype.markers;

// Map.infoBox displays a marker's info
google.maps.Map.prototype.infoBox;

google.maps.Map.prototype.addGeocodeRequest = function(eventId, eventName, address) {
	
	// Enqueue the geocode function as a request
	this.geoRequestQueue.enqueue(function() {
		map.geo.geocode({ "address": address }, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				map.addMarker(eventId, eventName, results[0].geometry.location);
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
};

google.maps.Map.prototype.addMarker = function(eventId, eventName, latLng) {

	// Create the event marker
	var marker = new google.maps.Marker({
		icon: "red-dot.png",
		position: latLng,
		map: this,
		eventId: eventId
	});

	// Create the associated InfoBox options
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
	
	// Event handler "click" for expanding the event list item
	google.maps.event.addListener(marker, "click", function() {
		//map.infoBox.setOptions(infoBoxOptions);
		//map.infoBox.open(map, marker);
	});

	// Event handler "mouseover" for label text and marker highlight
	google.maps.event.addListener(marker, "mouseover", function() {
		map.infoBox.setOptions(infoBoxOptions);
		map.infoBox.open(map, marker);
		marker.setIcon("yellow-dot.png");
	});

	// Event handler "mouseout" for label text and marker highlight
	google.maps.event.addListener(marker, "mouseout", function() {
		map.infoBox.close();
		marker.setIcon("red-dot.png");
	});
	
	// Store in Marker array
	this.markers.push(marker);
};

// Removes all markers from the map
google.maps.Map.prototype.clearMarkers = function() {
	
	for (var i = 0; i < this.markers.length; ++i) {
		this.markers[i].setMap(null);
		this.markers[i] = null;
	}
	this.markers = [];
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