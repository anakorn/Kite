(function($) {

	var app = $.sammy('#main', function() {
		this.use('Template');
		this.use('Session');

		this.helpers({
            loadJSON: function(location, options, callback) {
                options = $.extend(options, {json: true});
                return new Sammy.RenderContext(this).load(location, options, callback);
            }
        });

	    this.debug = false;
	    var query_url = 'testserver';
	    
	    // '#/' get route. The main page; loads the content template and the map.
	    this.get('#/', function(context) {
	    	google.maps.event.addDomListener(window, "load", kiteInitializeMap);
	    });
	    
	    // '#/filter' post route. Updates the page with event data requested from the java server (JSON).
	    this.post('#/filter', function(context) {
	    	var type = context.params['type'];
    		var query = "?type=" + type; 
	    	
    		// Send a request to the java server
    		context.loadJSON(query_url + query).then(function(events) {
    			// then refresh the events that are stored/displayed.
	    		$('.event-info').remove();
	    		context.events = events.data;
	    	}).then(function() {
	    		// then render and display the newly fetched events. 
	    		$.each(context.events, function(i, event) {
		    		context.render('templates/event-info.template', {id: i, event: event}).appendTo('#menu');
		    	});
	    	});	
	    });
	    	    
	    // Executes after '#/filter' is requested. Updates the map with new event data
	    this.after('#/filter', function() {
	    	var context = this;
	    	// Clear the map.
	    	map.clearMarkers();
	    	map.clearInfoBox();
	    	
	    	// Place the new markers.
	    	// for (var i = 0; i < eventInfos.length; ++i)
	    	//
	    });
	    
	    // '#/event' post route. Selects the event data associated a selected marker.
	    this.post('#/event', function(context) {
	    	context.log(context.params['id']);
	    	context.log(context.events);
			context.event = context.events[context.params['id']];
			context.log(context.event);
			if(!context.event) {return context.notFound(); }
			context.render('templates/event-detail.template', {name: "content-popup", event: context.event}).replace($('#content-popup'));
			$('#content-popup').bPopup();
	    });
	    

	});
	
	$(function() {
		app.run('#/');
	});
		
})(jQuery);