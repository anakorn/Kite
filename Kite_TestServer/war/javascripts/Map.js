<!-- HIDE JS FROM OLDER BROWSERS

google.maps.Map.prototype.INFO_WINDOW_MAX_WIDTH = 280;

// Array of Marker objects
google.maps.Map.prototype.markers = [];

// The InfoBox used to display a marker's info
google.maps.Map.prototype.infoBox = new InfoBox();

google.maps.Map.prototype.addMarker = function(eventId, position, innerHtml, iconUrl) {

	// Create the event marker
	var marker = new google.maps.Marker({
		isOpened: false,
		position: position,
		map: this
	});
	
	var boxText = document.createElement("div");
	boxText.style.cssText = "font-family: 'Verdana', Times, serif; border: 1px solid black; margin-top: 8px; background: #E0E4F0; padding: 5px;";
	boxText.innerHTML = innerHtml;

	// Create the associated InfoBox options
	var infoBoxOptions = {
		content: boxText,
        disableAutoPan: false,
		maxWidth: 0,
		pixelOffset: new google.maps.Size(-140, 0),
		zIndex: null,
		boxStyle: {
			background: "url('tipbox.gif') no-repeat",
			opacity: 0.85,
			width: "280px"
		},
		closeBoxMargin: "10px 2px 2px 2px",
		closeBoxURL: "http://www.google.com/intl/en_us/mapfiles/close.gif",
		infoBoxClearance: new google.maps.Size(1, 1),
		isHidden: false,
		pane: "floatPane",
		enableEventPropagation: false
	};
	
	// Hook up the marker with an event listener for opening info window
	google.maps.event.addListener(marker, "click", function() {
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
google.maps.Map.prototype.clearInfoBoxes = function() {
	
	this.infoBox.close();
};

-->