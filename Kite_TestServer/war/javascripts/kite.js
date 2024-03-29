(function($) {

	var app = $.sammy('#content', function() {
		this.use('Template');
		this.helpers({
            loadJSON: function(location, options, callback) {
                options = $.extend(options, {json: true});
                return new Sammy.RenderContext(this).load(location, options, callback);
            }
        });
	    this.debug = false;
	    
	    // Instance vars
	    var query_url =  'DefaultFbEventServlet';
	    var events = [];
	    var DEFAULT_QUERY = {'name': 'UCI'};
	    
	    // '#/' get route.	    
	    this.get('#/', function(context) { 
	    	context.params = new Sammy.Object(DEFAULT_QUERY);
	    	loadEventList(query_url, context);
        });
	    
	    // '#/filter' post route. Updates the page with event data requested from the java server (JSON).
	    this.post('#/filter', function(context) {
	    	loadEventList(query_url, context);	 
	    	
	    	resetForm();
	    });
	    
	    this.get('#/test/:selector', function(context) {
			expandListItem("#"+context.params['selector']);
	    });
	    
		// function loadEventList(url, params): 
	    // Send a request to the java server; refresh the events that are stored/displayed; 
    	// render and display the newly fetched events; create a marker; 
    	// add event data and marker to events; render the event list item 
    	// var marker = createMarker();
        function loadEventList(url, context) {
	    	var query = "?";
	    	$.each(getKeys(context.params), function(i, key) {
	    		query += key + "=" + context.params[key];
	    		if(i < getKeys(context.params).length - 1) {
	    			query += "&";
	    		}
	    	});
	    	
	    	console.log("Sending a request to: " + url + query);
    		context.loadJSON(url + query).then(function(events) {
	    		$('.event-info').remove(); // animate?
	    		context.events = events.data;
	    		map.clearMarkers();
	    		$('#nef').remove();
	    	}).then(function() {
	    		if(context.events.length > 0) {
		    		$.each(context.events, function(i, event) {
		    			map.addGeocodeRequest(i, event.name, event.location);
		    			events[i] = {'event': event}//, marker: marker};
		    			if(i < context.events.length - 1) {
		    				context.render('templates/event-info.template', {id: i, event: event})
		    					.appendTo('#event-list');
		    			} else {
		    				context.render('templates/event-info.template', {id: i, event: event})
		    					.appendTo('#event-list')
		    					.then(function() {
		    		        		$('.event-info').click(function(e) {
		    		        			expandListItem($(e.target).closest('.event-info'));
		    		        		}).hover(
		    		        			function() {
		    		        				$(this).css('background', '#ebf4fb');
		    		        			}, function() {
		    		        				$(this).css('background', '#FFFFFF');
		    		        			}
			        				);	
		    			    	});
		    			}
			    	});
	    		} else {
	    			$('#event-list').html("<h1 id='nef'>No events could be found.</h1>");
	    		}
	    		
	    		// Process geocode requests after adding them
	    		map.processGeocodeRequests();
	    	});
        }
	    
	});
	
	$(document).ready(function() {
		app.run('#/');
	});
		
})(jQuery);