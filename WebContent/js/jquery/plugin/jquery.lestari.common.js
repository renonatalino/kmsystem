/*
 * jQuery Commonly Usage Plugins Collection
 *
 * Copyright (c) 2010 RENO NATALINO, anubis.its.reno@gmail.com
 *
 */
(function($){
	/* --------------------------------------------------------------------------- *
	 * ------------------------ STATIC METHOD PLUGINS ---------------------------- *
	 * --------------------------------------------------------------------------- */
    $.extend({
        // public interface: $.namespace *adopting from Ext Core :D thx jack!
        namespace: function() {
            var o, d;
            $.each(arguments, function() {
                d = this.split(".");
                o = window[d[0]] = window[d[0]] || {};
                $.each(d.slice(1), function(){
                    o = o[this] = o[this] || {};
                });
            });
            return o;
        },
        // public interface: $.delegate
        delegate: function(rules) {
            return function(e) {
                var target = $(e.target), parent = null;
                for (var selector in rules) {
                    if (target.is(selector) || ((parent = target.parents(selector)) && parent.length > 0)) {
                        return rules[selector].apply(this, [parent || target].concat($.makeArray(arguments)));
                    }
                    parent = null;
                }
            };
        },
    	// public interface: $.tmpl
    	tmpl : function(tmpl, vals, rgxp) {
			// default to doing no harm
			tmpl = tmpl   || '';
			vals = vals || {};
    		// regular expression for matching our placeholders; e.g., #{my-cLaSs_name77}
    		rgxp = rgxp || /#\{([^{}]*)}/g;
    		// function to making replacements
    		var repr = function (str, match) {
				return typeof vals[match] === 'string' || typeof vals[match] === 'number' ? vals[match] : str;
			};
			return tmpl.replace(rgxp, repr);
		}
	});
	/* --------------------------------------------------------------------------- *
	 * ----------------------- SELECTOR BASED PLUGINS ---------------------------- *
	 * --------------------------------------------------------------------------- */
	var plugins = {
		 // Clear a node contents
		clear: function() {
			return this.each(function() { $(this).html(''); });
		}
	};
	// initialize plugins
	$.each(plugins, function(i) {
    	$.fn[i] = this;
	});
})(jQuery);
