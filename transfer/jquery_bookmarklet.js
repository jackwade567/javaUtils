function loadScript(url, callback) {
  var head = document.getElementsByTagName("head")[0];
  var script = document.createElement("script");
  script.src = url;

  // Attach handlers for all browsers
  var done = false;
  script.onload = script.onreadystatechange = function() {
    if (!done && (!this.readyState || this.readyState == "loaded" || this.readyState == "complete")) {
      done = true;
      callback();
      script.onload = script.onreadystatechange = null;
      head.removeChild(script);
    }
  };

  head.appendChild(script);
}
loadScript('https://code.jquery.com/jquery-3.6.0.min.js', function() {  
  loadScript('https://code.jquery.com/ui/1.12.1/jquery-ui.min.js', function() {
    var $fb_jq = jQuery.noConflict();
	var customDialogStyles = {
    container: {
        'background-color': '#fff',
        'border': '1px solid #ddd',
        'border-radius': '10px',
        'box-shadow': '0 4px 8px rgba(0, 0, 0, 0.1)'
    },
    titlebar: {
        'background-color': '#4CAF50',
        'color': 'white',
        'padding': '10px',
        'border-top-left-radius': '10px',
        'border-top-right-radius': '10px'
    },
    titlebarClose: {
        'background-color': '#ff1744',
        'color': 'white',
        'border-radius': '50%'
    },
    content: {
        'padding': '15px',
        'color': '#333',
        'font-family': 'Arial, sans-serif'
    },
    buttonpane: {
        'background-color': '#f5f5f5',
        'padding': '10px',
        'text-align': 'right'
    }
};
    (function($fb_jq) {
    // Ensure $ is an alias to jQuery in this scope
    
    // Footer class
    class Footer {
      constructor() {
        this.$modal = null;
        this.draggable = true;
        this.resizable = false;
        this.collapsible = false;

        this.$footer = $fb_jq('<footer></footer>').css({
          'display': 'none',
          'position': 'fixed',
          'left': '0',
          'bottom': '0',
          'width': '100%',
          'background-color': 'rgba(245, 245, 245, 0.7)',
          'text-align': 'center',
          'padding': '5px'
        });
        $fb_jq('body').append(this.$footer);
        this.$footer.show(); // Removed animation

		  var $settingsIcon = $fb_jq('<i>settings</i>').addClass('fa fa-cog icon-settings');
        var $poweredByIcon = $fb_jq('<i>Powered by Care Connect</i>').addClass('fa fa-plug icon-powered-by');
        this.$footer.append($settingsIcon);
        this.$footer.append($poweredByIcon);
      }

      addButton(label, url) {
        console.log('Adding button:', label);
        var $button = $fb_jq('<button></button>').css({
          'background-color': '#4CAF50',
          'border': 'none',
          'color': 'white',
          'padding': '5px 5px',
          'text-align': 'center',
          'text-decoration': 'none',
          'display': 'inline-block',
          'font-size': '16px',
          'margin': '4px 2px',
          'cursor': 'pointer'
        });
        $button.html('<i class="icon"></i> ' + label);
        $button.data('url', url);
        this.$footer.append($button);
      
      }
    }

    // Modal class
    class Modal {
  static instance;
  static getInstance() {
    if (!Modal.instance) {
      Modal.instance = new Modal();
    }
    return Modal.instance;
  }
  static instance;
      constructor() {
        this.$modal = null;
        this.draggable = true;
        this.resizable = true;
        this.collapsible = true;
      }

      setDraggable(draggable) {
        this.draggable = draggable;
      }

      setResizable(resizable) {
        this.resizable = resizable;
      }

      setCollapsible(collapsible) {
        this.collapsible = collapsible;
      }

      openModal(url) {
  //if (this.$modal) { this.$modal.remove(); }
  //setTimeout(() => {
	  this.$modal = $fb_jq('<div></div>').dialog({
    closeText: 'X',
    draggable: this.draggable,
    resizable: true,
    autoOpen: true,
    height: 400,
    width: 350,
    modal: false,
    collapsible: this.collapsible,
 
});
this.$modal.css(customDialogStyles.content);
this.$modal.dialog('widget').css(customDialogStyles.container);
this.$modal.prev('.ui-dialog-titlebar').css(customDialogStyles.titlebar);
this.$modal.parent().find('.ui-dialog-titlebar-close').css(customDialogStyles.titlebarClose);
this.$modal.next('.ui-dialog-buttonpane').css(customDialogStyles.buttonpane);
	//}, 1000);

  console.log('Opening modal window with URL:', url);
  var $iframe = $fb_jq('<iframe></iframe>').css({
    'width': '100%',
    'height': '100%',
    'border': 'none'
  }).attr('src', url);
  this.$modal.append($iframe);
  this.$modal.css({'border': '1px solid #ccc'}).fadeIn();
}
    }

    // Add footer to the page
    var footer = new Footer();
    footer.addButton('Care Gaps', 'https://benhoyt.com/writings/the-small-web-is-beautiful');
    footer.addButton('Member Indicator', 'https://www.tinywebgallery.com/blog/advanced-iframe/free-iframe-checker');
    footer.addButton('Member REL', 'https://www.tinywebgallery.com/blog/advanced-iframe/free-iframe-checker');
    footer.addButton('Claims', 'https://www.tinywebgallery.com/blog/advanced-iframe/free-iframe-checker');

    // Handle button click events
    $fb_jq(document).on('click', 'footer button', function() {
      var url = $fb_jq(this).data('url');
      var modal = Modal.getInstance();
  if (modal.$modal) {
    modal.$modal.remove();
  }
      modal.openModal(url);
    console.log('Number of visible iframes:', $fb_jq('iframe:visible').length);
    });
  })(jQuery);
  });
});
