function onDocumentLoad() {
	kiteInitializeMap();
//	window.location = '#/';
}

function getKeys(obj){
    var keys = [];
    for (key in obj) {
        if (obj.hasOwnProperty(key)) { keys[keys.length] = key; }
    } 
    return keys;
}

function toggleLoadpage(loading) {
	if(loading) {
		$('#loadpage').fadeIn();
	} else {
		$('#loadpage').fadeOut();
	}
}

function expandListItem(selector) {
	if($(selector).is('.event-info')) {
    	if($(selector).find('.event-detail').is(':hidden')) {
    		$('.event-info').css('background', '#FFF');
        	$('.event-detail').slideUp('fast');
    		$(selector).css('background', '#e7ddf2').find('.event-detail').slideDown('fast', function() {
    			scrollToListItem(selector, 200);
        		
        		// Close every marker's info first
        		for (var i = 0; i < map.markers.length; i++) {
        			map.markers[i].hideInfo();
        		}
        		
        		// Pan to marker on Google maps
        		var marker = map.getMarker($(selector).attr('id'));
        		if(marker) {
        			marker.showInfo();
            		map.panTo(marker.getPosition());
    			}
    		});
    	} else {
    		$(selector).css('background', '#FFFFFF').find('.event-detail').slideUp('fast');
    	}
	}
}

function scrollToListItem(selector, duration) {
	$('#event-list').scrollTo($(selector), duration, {axis: 'y'});
}

function resetForm() {
	$('input[type=text]').val('');
	$('input[type=radio]').attr('checked', false);
}