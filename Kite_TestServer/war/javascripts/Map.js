<!-- HIDE JS FROM OLDER BROWSERS

// Add a property to Marker class called "eventId"
google.maps.Marker.prototype.eventId;

// Array of Marker objects
google.maps.Map.prototype.markers = [];

// The InfoBox used to display a marker's info
google.maps.Map.prototype.infoBox = new InfoBox();

google.maps.Map.prototype.addMarker = function(eventId, position, eventName, iconUrl) {

	// Create the event marker
	var marker = new google.maps.Marker({
		isOpened: false,
		position: position,
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
	        fontSize: "10pt",
	        width: "200px",
	        padding: "2px"
        },
		disableAutoPan: true,
        pixelOffset: new google.maps.Size(-100, 0),
        position: position,
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

	// Event handler "mouseover" for expanding the event list item
	google.maps.event.addListener(marker, "mouseover", function() {
		map.infoBox.setOptions(infoBoxOptions);
		map.infoBox.open(map, marker);
	});
	
	// Set the icon image using an image URL
	if (iconUrl != undefined) {
		marker.setIcon(iconUrl);
	}
	
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

// Close the map's main InfoBox
google.maps.Map.prototype.clearInfoBox = function() {
	
	this.infoBox.close();
};

// Get marker, given its ID
google.maps.Map.prototype.getMarker = function(eventId) {
	
	for (var i = 0; i < this.markers.length; ++i) {
		if (this.markers[i].eventId = eventId)
			return markers[i];
	}
	return null;
};

-->