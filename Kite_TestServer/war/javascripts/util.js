function onDocumentLoad() {
	kiteInitializeMap();
	window.location = "#/";
	toggleLoadpage(false);
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
		$('#loadpage').fadeIn(100);
	} else {
		$('#loadpage').fadeOut(100);
	}
}

function expandListItem(selector) {
	if($(selector).is('.event-info')) {
    	if($(selector).find('.event-detail').is(':hidden')) {
    		$('.event-info').css('background', '#FFF');
        	$('.event-detail').slideUp('fast');
    		$(selector).css('background', '#e7ddf2').find('.event-detail').slideDown('fast', function() {
    			scrollToListItem(selector, 200);
    		});
    	}
	}
}

function scrollToListItem(selector, duration) {
	$('#event-list').scrollTo($(selector), duration, {axis: 'y'});
}