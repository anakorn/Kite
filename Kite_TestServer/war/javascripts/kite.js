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
	    var events = '';
	    
	    // The base template must always be rendered first. 
	    this.around(function(callback) {
	    	var context = this;
	    	context.app.swap('');	
	    	context.render('templates/content.template')
		    	.appendTo(context.$element())
		    	.then(callback);
	    });
	    
	    // 
	    this.get('#/', function(context) {
	    	
	    });
	    
	    this.post('#/filter', function(context) {
	    	var type = this.params['type'];
    		var query = "?type=" + type; 
	    	
    		context.loadJSON(query_url + query).then(function(events) {
	    		context.events = events.data;
	    	}).then(function() {
	    		$.each(context.events, function(i, event) {
		    		context.render('templates/event-info.template', {id: i, event: event}).appendTo('#menu');
		    	});
	    	});
    		
	    });

	});
	
	$(function() {
		app.run('#/');
	});
		
})(jQuery);