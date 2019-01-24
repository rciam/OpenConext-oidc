Backbone.Model.prototype.fetchIfNeeded = function(options) {
	var _self = this;
	if (!options) {
		options = {};
	}
	var success = options.success;
	options.success = function(c, r) {
		_self.isFetched = true;
		if (success) {
			success(c, r);
		}
	};
	if (!this.isFetched) {
		return this.fetch(options);
	} else {
		return options.success(this, null);
	}
};

Backbone.Collection.prototype.fetchIfNeeded = function(options) {
	var _self = this;
	if (!options) {
		options = {};
	}
	var success = options.success;
	options.success = function(c, r) {
		_self.isFetched = true;
		if (success) {
			success(c, r);
		}
	};
	if (!this.isFetched) {
		return this.fetch(options);
	} else {
		return options.success(this, null);
	}
};

var URIModel = Backbone.Model.extend({

    validate: function(attrs){

        var expression = /^(?:([a-z0-9+.-]+:\/\/)((?:(?:[a-z0-9-._~!$&'()*+,;=:]|%[0-9A-F]{2})*)@)?((?:[a-z0-9-._~!$&'()*+,;=]|%[0-9A-F]{2})*)(:(?:\d*))?(\/(?:[a-z0-9-._~!$&'()*+,;=:@\/]|%[0-9A-F]{2})*)?|([a-z0-9+.-]+:)(\/?(?:[a-z0-9-._~!$&'()*+,;=:@]|%[0-9A-F]{2})+(?:[a-z0-9-._~!$&'()*+,;=:@\/]|%[0-9A-F]{2})*)?)(\?(?:[a-z0-9-._~!$&'()*+,;=:\/?@]|%[0-9A-F]{2})*)?(#(?:[a-z0-9-._~!$&'()*+,;=:\/?@]|%[0-9A-F]{2})*)?$/i;
        var regex = new RegExp(expression);

        if (attrs.item == null || !attrs.item.match(regex)) {
            return "Invalid URI";
        }
    }

});


/*
* Backbone JS Reusable ListWidget
*  Options
* {
*   collection: Backbone JS Collection
*   type: ('uri'|'default')
*   autocomplete: ['item1','item2'] List of auto complete items
* }
*
 */
var ListWidgetChildView = Backbone.View.extend({

    tagName: 'tr',

    events:{
        "click .btn-delete-list-item":'deleteItem',
        "change .checkbox-list-item":'toggleCheckbox'
    },
    
    deleteItem:function (e) {
    	e.preventDefault();
    	e.stopImmediatePropagation();
        //this.$el.tooltip('delete');
        
        this.model.destroy({
        	dataType: false, processData: false,
        	error:function (error, response) {
        		console.log("An error occurred when deleting from a list widget");

				//Pull out the response text.
				var responseJson = JSON.parse(response.responseText);
        		
        		//Display an alert with an error message
				$('#modalAlert div.modal-header').html(responseJson.error);
        		$('#modalAlert div.modal-body').html(responseJson.error_description);
        		
    			 $("#modalAlert").modal({ // wire up the actual modal functionality and show the dialog
    				 "backdrop" : "static",
    				 "keyboard" : true,
    				 "show" : true // ensure the modal is shown immediately
    			 });
        	}
        });
        
    },
    
    toggleCheckbox:function(e) {
    	e.preventDefault();
    	e.stopImmediatePropagation();
    	if ($(e.target).is(':checked')) {
    		this.options.collection.add(this.model);
    	} else {
    		this.options.collection.remove(this.model);
    	}
    	
    },

    initialize:function (options) {
    	this.options = {toggle: false, checked: false};
    	_.extend(this.options, options);
        if (!this.template) {
            this.template = _.template($('#tmpl-list-widget-child').html());
        }
    },

    render:function () {
    	
    	var data = {model: this.model.toJSON(), opt: this.options};
    	
        this.$el.html(this.template(data));

        $('.item-full', this.el).hide();
        
        if (this.model.get('item').length > 30) {
            this.$el.tooltip({title:$.t('admin.list-widget.tooltip')});
            
            var _self = this;
            
            $(this.el).click(function(event) {
            	event.preventDefault();
            	$('.item-short', _self.el).hide();
            	$('.item-full', _self.el).show();
            	_self.$el.tooltip('destroy');
            });
        }
        
        
        
        $(this.el).i18n();
        return this;
    }
});

var ListWidgetView = Backbone.View.extend({

    tagName: "div",

    events:{
    	"click .btn-add-list-item":"addItem",
        "keypress":function (e) {
        	// trap the enter key
            if (e.which == 13) {
            	e.preventDefault();
                this.addItem(e);
                $("input", this.$el).focus();
            }
        }
    },

    initialize:function (options) {
    	this.options = options;

        if (!this.template) {
            this.template = _.template($('#tmpl-list-widget').html());
        }

        this.collection.bind('add', this.render, this);
        this.collection.bind('remove', this.render, this);
    },

    addItem:function(e) {
    	e.preventDefault();

        var input_value = $("input", this.el).val().trim();

        if (input_value === ""){
           return;
        }

        var model;

        if (this.options.type == 'uri') {
            model = new URIModel({item:input_value});
        } else {
            model = new Backbone.Model({item:input_value});
            model.validate = function(attrs) { 
            	if(!attrs.item) {
            		return "value can't be null";
            	}
            };
        }

        // if it's valid and doesn't already exist
        if (model.get("item") != null && this.collection.where({item: input_value}).length < 1) {
            this.collection.add(model);
        } else {
            // else add a visual error indicator
            $(".control-group", this.el).addClass('error');
        }
    },

    render:function (eventName) {

        this.$el.html(this.template({placeholder:this.options.placeholder,
        							helpBlockText:this.options.helpBlockText}));

        var _self = this;

        if (_.size(this.collection.models) == 0 && _.size(this.options.autocomplete) == 0) {
    		$("tbody", _self.el).html($('#tmpl-list-widget-child-empty').html());
        } else {
        	
        	// make a copy of our collection to work from
        	var values = this.collection.clone();

        	// look through our autocomplete values (if we have them) and render them all as checkboxes
        	if (this.options.autocomplete) {
        		_.each(this.options.autocomplete, function(option) {
        			var found = _.find(values.models, function(element) {
        				return element.get('item') == option;
        			});
        			
        			var model = null;
        			var checked = false;
        			
        			if (found) {
        				// if we found the element, check the box
        				model = found;
        				checked = true;
        				// and remove it from the list of items to be rendered later
        				values.remove(found, {silent: true});
        			} else {
        				model = new Backbone.Model({item:option});
        				checked = false;
        			}
        			
        			var el = new ListWidgetChildView({model:model, toggle: true, checked: checked, collection: _self.collection}).render().el;
        			$("tbody", _self.el).append(el);
        			
        		}, this);
        	}
        	
        	
        	// now render everything not in the autocomplete list
        	_.each(values.models, function (model) {
        		var el = new ListWidgetChildView({model:model, collection: _self.collection}).render().el;
        		$("tbody", _self.el).append(el);
        	}, this);
        }

        $(this.el).i18n();
        return this;
    }
    
});

var BreadCrumbView = Backbone.View.extend({

    tagName: 'ul',

    initialize:function (options) {
    	this.options = options;

        if (!this.template) {
            this.template = _.template($('#tmpl-breadcrumbs').html());
        }

        this.$el.addClass('breadcrumb');

        this.collection.bind('add', this.render, this);
    },

    render:function () {

        this.$el.empty();
        var parent = this;

        // go through each of the breadcrumb models
        _.each(this.collection.models, function (crumb, index) {

            // if it's the last index in the crumbs then render the link inactive
            if (index == parent.collection.size() - 1) {
                crumb.set({active:true}, {silent:true});
            } else {
                crumb.set({active:false}, {silent:true});
            }

            this.$el.append(this.template(crumb.toJSON()));
        }, this);

        $('#breadcrumbs').html(this.el);
        $(this.el).i18n();
    }
});


// Stats table

var StatsModel = Backbone.Model.extend({
	url: "api/stats/byclientid"
});

// User Profile

var UserProfileView = Backbone.View.extend({
	tagName: 'span',
	
	initialize:function(options) {
    	this.options = options;
        if (!this.template) {
            this.template = _.template($('#tmpl-user-profile-element').html());
        }
	},
	
	render:function() {
		
        $(this.el).html($('#tmpl-user-profile').html());
        
        var t = this.template;

        _.each(this.model, function (value, key) {
        	if (key && value) {
        		
        		if (typeof(value) === 'object') {
        			
        			var el = this.el;
        			var k = key;
        			
        			_.each(value, function (value, key) {
        				$('dl', el).append(
       	            		t({key: key, value: value, category: k})
        				);
        			});
        		} else if (typeof(value) === 'array') {
        			// TODO: handle array types
        		} else {
    	            $('dl', this.el).append(
    	            		t({key: key, value: value})
    	            	);
        		}
        	}
        }, this);
		
        $(this.el).i18n();
		return this;
	}
});

// Router
var AppRouter = Backbone.Router.extend({

    routes:{
        "admin/clients":"listClients",
        "admin/client/new":"newClient",
        "admin/client/:id":"editClient",
        
        "admin/scope":"siteScope",
        "admin/scope/new":"newScope",
        "admin/scope/:id":"editScope",
        
		"user/services":"services",
        "user/profile":"profile",
        
        "": "root"
        	
    },
    
    root:function() {
    	if (isAdmin()) {
    		this.navigate('admin/clients', {trigger: true});
    	} else {
    		this.navigate('user/profile', {trigger: true});
    	}
    },
    
    initialize:function () {

        this.clientList = new ClientCollection();
        this.systemScopeList = new SystemScopeCollection();
        this.clientStats = new StatsModel(); 
		this.serviceList = new ServiceCollection();
		this.serviceRefreshTokensList = new ServiceRefreshTokenCollection();
		this.serviceAccessTokensList = new ServiceAccessTokenCollection();
                
        this.breadCrumbView = new BreadCrumbView({
            collection:new Backbone.Collection()
        });

        this.breadCrumbView.render();

        var base = $('base').attr('href');
        $.getJSON(base + '.well-known/openid-configuration', function(data) {
        	app.serverConfiguration = data;
        	var baseUrl = $.url(app.serverConfiguration.issuer);
			Backbone.history.start({pushState: true, root: baseUrl.attr('relative') + 'manage/'});
        });

    },

    listClients:function () {

    	if (!isAdmin()) {
    		this.root();
    		return;
    	}
    	
        this.breadCrumbView.collection.reset();
        this.breadCrumbView.collection.add([
            {text:$.t('admin.home'), href:""},
            {text:$.t('client.manage'), href:"manage/#admin/clients"}
        ]);
        
        this.updateSidebar('admin/clients');

        var view = new ClientListView({model:this.clientList, stats: this.clientStats, systemScopeList: this.systemScopeList, whiteListList: this.whiteListList});
        
        view.load(function() {
        	$('#content').html(view.render().el);
        	view.delegateEvents();
        	setPageTitle($.t('client.manage'));
        });

    },

    newClient:function() {

    	if (!isAdmin()) {
    		this.root();
    		return;
    	}

        this.breadCrumbView.collection.reset();
        this.breadCrumbView.collection.add([
            {text:$.t('admin.home'), href:""},
            {text:$.t('client.manage'), href:"manage/#admin/clients"},
            {text:$.t('client.client-form.new'), href:""}
        ]);

        this.updateSidebar('admin/clients');

        var client = new ClientModel();
    	
        var view = new ClientFormView({model:client, systemScopeList: this.systemScopeList});
        view.load(function() {
    		var userInfo = getUserInfo();
    		var contacts = [];
    		if (userInfo != null && userInfo.email != null) {
    			contacts.push(userInfo.email);
    		}
    		
			client.set({
				tokenEndpointAuthMethod: "SECRET_BASIC",
				generateClientSecret:true,
				displayClientSecret:false,
				requireAuthTime:true,
				defaultMaxAge:60000,
				scope: _.uniq(_.flatten(app.systemScopeList.defaultScopes().pluck("value"))),
				refreshTokenValiditySeconds:getDefaultRefreshTokenLifeTime(),
				accessTokenValiditySeconds:getDefaultAccessTokenLifeTime(),
				idTokenValiditySeconds:getDefaultIdTokenLifeTime(),
				grantTypes: ["authorization_code"],
				responseTypes: ["code"],
				subjectType: "PUBLIC",
				jwksType: "URI",
				contacts: contacts
			}, { silent: true });
        	
        	$('#content').html(view.render().el);
        	setPageTitle($.t('client.client-form.new'));
        });
    },

    editClient:function(id) {

    	if (!isAdmin()) {
    		this.root();
    		return;
    	}

        this.breadCrumbView.collection.reset();
        this.breadCrumbView.collection.add([
            {text:$.t('admin.home'), href:""},
            {text:$.t('client.manage'), href:"manage/#admin/clients"},
            {text:$.t('client.client-form.edit'), href:"manage/#admin/client/" + id}
        ]);

        this.updateSidebar('admin/clients');

        var client = this.clientList.get(id);
        if (!client) {
        	client = new ClientModel({id:id});
        }
        
        var view = new ClientFormView({model:client, systemScopeList: app.systemScopeList});
        view.load(function() {
	        if ($.inArray("refresh_token", client.get("grantTypes")) != -1) {
	        	client.set({
	        		allowRefresh: true
	        	}, { silent: true });
	        }
	        
	        if (client.get("jwks")) {
	        	client.set({
	        		jwksType: "VAL"
	        	}, { silent: true });
	        } else {
	        	client.set({
	        		jwksType: "URI"
	        	}, { silent: true });
	        }
	        
	    	client.set({
	    		generateClientSecret:false,
	    		displayClientSecret:false
	    	}, { silent: true });
	        
        	$('#content').html(view.render().el);
        	setPageTitle($.t('client.client-form.edit'));
        });

    },

	services: function () {
		this.breadCrumbView.collection.reset();
		this.breadCrumbView.collection.add([
			{ text: $.t('admin.home'), href: "" },
			{ text: $.t('service.manage'), href: "manage/#user/services" }
		])        

		this.updateSidebar('user/services');

		var view = new ServiceListView({
			model: {
				serviceClient: this.serviceList,
				serviceRefreshT: this.serviceRefreshTokensList,
				serviceAccessT: this.serviceAccessTokensList
			},
			systemScopeList: this.systemScopeList
		});
		view.load(
			function (collection, response, options) {
				$('#content').html(view.render().el);
				setPageTitle($.t('service.manage'));
			}
		);

	},
    
    notImplemented:function(){
        this.breadCrumbView.collection.reset();
        this.breadCrumbView.collection.add([
            {text:$.t('admin.home'), href:""}
        ]);
        
        this.updateSidebar('none');
        
   		$('#content').html("<h2>Not implemented yet.</h2>");
    },
    
    siteScope:function() {

    	if (!isAdmin()) {
    		this.root();
    		return;
    	}

    	this.breadCrumbView.collection.reset();
    	this.breadCrumbView.collection.add([
             {text:$.t('admin.home'), href:""},
             {text:$.t('scope.manage'), href:"manage/#admin/scope"}
        ]);
    	
        this.updateSidebar('admin/scope');
        
    	var view = new SystemScopeListView({model:this.systemScopeList});
    	
    	view.load(function() {
    		$('#content').html(view.render().el);
    		view.delegateEvents();
    		setPageTitle($.t('scope.manage'));    		
    	});

    },
    
    newScope:function() {

    	if (!isAdmin()) {
    		this.root();
    		return;
    	}

    	this.breadCrumbView.collection.reset();
    	this.breadCrumbView.collection.add([
             {text:$.t('admin.home'), href:""},
             {text:$.t('scope.manage'), href:"manage/#admin/scope"},
             {text:$.t('scope.system-scope-form.new'), href:"manage/#admin/scope/new"}
        ]);
    	
        this.updateSidebar('admin/scope');
        
    	var scope = new SystemScopeModel();
    	
    	var view = new SystemScopeFormView({model:scope});
    	view.load(function() {
    		$('#content').html(view.render().el);
    		setPageTitle($.t('scope.system-scope-form.new'));
    	});

    },
    
    editScope:function(sid) {

    	if (!isAdmin()) {
    		this.root();
    		return;
    	}

    	this.breadCrumbView.collection.reset();
    	this.breadCrumbView.collection.add([
             {text:$.t('admin.home'), href:""},
             {text:$.t('scope.manage'), href:"manage/#admin/scope"},
             {text:$.t('scope.system-scope-form.edit'), href:"manage/#admin/scope/" + sid}
        ]);

        this.updateSidebar('admin/scope');
        
    	var scope = this.systemScopeList.get(sid);
    	if (!scope) {
    		scope = new SystemScopeModel({id: sid});
    	}
    	
    	var view = new SystemScopeFormView({model:scope});
    	view.load(function() {
    		$('#content').html(view.render().el);
    		setPageTitle($.t('scope.system-scope-form.new'));
    	});
    	
    },
    
    profile:function() {
    	this.breadCrumbView.collection.reset();
    	this.breadCrumbView.collection.add([
             {text:$.t('admin.home'), href:""},
             {text:$.t('admin.user-profile.show'), href:"manage/#user/profile"}
        ]);
    
        this.updateSidebar('user/profile');
        
    	var view = new UserProfileView({model: getUserInfo()});
    	$('#content').html(view.render().el);
    	
    	setPageTitle($.t('admin.user-profile.show'));
    	
    },
    
    updateSidebar:function(item) {
    	$('.sidebar-nav li.active').removeClass('active');
    	
    	$('.sidebar-nav li a[href^="manage/#' + item + '"]').parent().addClass('active');
    }
});

// holds the global app.
// this gets init after the templates load
var app = null;

// main
$(function () {

    var _load = function (templates) {
        $('body').append(templates);
    };

    // load templates and append them to the body
    $.when(
    		$.get('resources/template/admin.html', _load),
    		$.get('resources/template/client.html', _load),
    		$.get('resources/template/scope.html', _load),
			$.get('resources/template/service.html', _load)
    		).done(function() {
    		    $.ajaxSetup({cache:false});
    		    app = new AppRouter();

    		    app.on('route', function(name, args) {
    		    	// scroll to top of page on new route selection
    		    	$("html, body").animate({ scrollTop: 0 }, "slow");
    		    });
    		    
    		    // grab all hashed URLs and send them through the app router instead
    		    $(document).on('click', 'a[href^="manage/#"]', function(event) {
    		    	event.preventDefault();
    		    	app.navigate(this.hash.slice(1), {trigger: true});
    		    });    		    
    		});
    
    window.onerror = function ( message, filename, lineno, colno, error ){
		//Display an alert with an error message
		$('#modalAlert div.modal-header').html($.t('error.title'));
		$('#modalAlert div.modal-body').html($.t('error.message') + ' <br /> ' [filename, lineno, colno, error]);
		
		 $("#modalAlert").modal({ // wire up the actual modal functionality and show the dialog
			 "backdrop" : "static",
			 "keyboard" : true,
			 "show" : true // ensure the modal is shown immediately
		 });

    }
}
);
