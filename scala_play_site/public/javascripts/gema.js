function cleanUpSome(sstring){
	return sstring.substr(5).slice(0, - 1);
}

function validateEmail(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function initDocument() {
	var currentLocation = window.location + "";
	
	/*
	if ($.browser.msie && (currentLocation.indexOf("browser") == -1)){
		window.location = "/browser";
	}
	*/

	if(typeof String.prototype.trim !== 'function') {
	  String.prototype.trim = function() {
	    return this.replace(/^\s+|\s+$/g, ''); 
	  }
	}
		
	// calculate the aspect ratio and setup the resize function/event...
	var theWindow 			= $(window),
		theUniversalNav = $("#nav"),
		maximumWidth = parseInt(theUniversalNav.css("max-width").split("px")[0]);
		
	function resizeBg() {
		// check whether we need to adjust the background image and centralize the view pane
		var currentWidth = theWindow.width();
		// workaround small differences between browsers...
		if(jQuery.browser.msie) currentWidth = document.documentElement.offsetWidth;
		else currentWidth = window.innerWidth;
		var elementsOffset = -250; // <-- how much we want original elements to offset by
		var newWidth = (currentWidth > maximumWidth)?maximumWidth:currentWidth;
		var difference = (newWidth > 1200)?currentWidth-1200:currentWidth-newWidth;
		theUniversalNav.css('width', newWidth - elementsOffset); // <-- compensate for the offset...
		if (currentWidth < 1150) {
			theUniversalNav.css('width', newWidth - elementsOffset - difference/2 - elementsOffset); // <-- compensate for the offset...
			theUniversalNav.css('left', -310);
		}
		else theUniversalNav.css('left', Math.min(difference/2, parseInt(difference/2 + elementsOffset)));
		
		// size adjustments (shouldn't need this, as it is 100% already, but whatever...
		$("#foot").css('width', currentWidth);
		$("#clb").css('width', currentWidth);
	}
	
	theWindow.resize(function() {
		resizeBg();
	}).trigger("resize");
}

// bind documentReady to the ready() event in jQuery
$(document).ready(initDocument);
