$(function() {
	$(window).scroll(function(event) {
		var top = $(document).scrollTop();
		if(top <= 50)
		{
			$('.nav-fixedbar')
			.removeClass('navbar-fixed-top nav-fixedbar-shadow');

			$('.nav-fixedbar .nav-plus')
			.addClass('invisible');

			$('.nav-fixedbar .navbar-tabs')
			.addClass('nav-plus-invisible');

			$('.sidebar-offcanvas')
			.removeClass('affix');
		}
		else if(top > 50)
		{
			$('.nav-fixedbar')
			.addClass('navbar-fixed-top nav-fixedbar-shadow');

			$('.nav-fixedbar .nav-plus')
			.removeClass('invisible');

			$('.nav-fixedbar .navbar-tabs')
			.removeClass('nav-plus-invisible');

			$('.sidebar-offcanvas')
			.addClass('affix');
		}

		clearTimeout($.data(this, 'scrollTimer'));
		$.data(this, 'scrollTimer', setTimeout(function() {
					// scroll tab
					tabScroll();
				}, 50));

	});

	$('.nav-fixedbar').dblclick(function(event) {
		scrollToElement('html');
		return false;
	});

// popover
	$('[rel=custom-popover]').click(function(event) {
		var modal = $(this).attr('data-target');
		$('.popover:not(#'+modal+'-popover)').hide();
		$('#'+modal+'-popover').fadeIn(300, function(){
			$('body').one('click', function(event) {
				$('#'+modal+'-popover').fadeOut(300);
			});
		})
		.click(function(event) {
			return false;
		});
		return false;
	});

	$('#nav-tabs').tab();

	$('#add').click(function(event) {
		var tab = + new Date();
		addTab(tab, tab, false, tab);
	});

	// Initialize navgoco
	$("#menu").navgoco({
		caret: '<span class="caret"></span>',
		accordion: false,
		openClass: 'open',
		save: true,
		cookie: {
			name: 'navgoco',
			expires: false,
			path: '/'
		},
		slide: {
			duration: 400,
			easing: 'swing'
		},
		onClickAfter: function(e, submenu) {
			e.preventDefault();
			if($(this).attr('data-link')) {
				var link = $(this).attr('data-link')
				outlink = $(this).attr('data-outlink') == undefined ? false : $(this).attr('data-outlink'),
				id = $(this).attr('data-index'),
				title = $(this).text();
				if (mobile) {
					loadContent(outlink, link);
				} else {
					addTab(id, title, true, link);
				}
				if(mobile) {
					$('.row-offcanvas').toggleClass('active');

				} else {
					if($('.nav-fixedbar').position().top == 0) {
						scrollToElement('#tab-content');
					}
				}
			}
		}
	});

	/**
	 * toggle menu display
	 */
	 $('[data-toggle=offcanvas]').click(function() {
	 	$('.row-offcanvas').toggleClass('active');
	 	return false;
	 });

	/**
	 * scroll right
	 */
	 $('.navbar-tabs .tabs-prev').click(function(event) {
	 	tabScroll('right');
	 });

	/**
	 * scroll left
	 */
	 $('.navbar-tabs .tabs-next').click(function(event) {
	 	tabScroll('left');
	 });

	/**
	 * Remove a Tab
	 */
	 $('.nav-fixedbar .nav-tabs > li > a > button.close').click(function(event) {
	 	var tabId = $(this).parent('a').attr('href');

	 	$(this).parents('.nav-tabs > li').remove();

	 	$(tabId).remove();
	 	$('#nav-tabs a:last').tab('show');
		// scroll tab
		tabScroll();
	});

	/**
	 * Double click remove a Tab
	 */
	 $('.nav-fixedbar .nav-tabs > li > a').dblclick(function(event) {
	 	var tabId = $(this).attr('href');

	 	$(this).parents('li').remove();

	 	$(tabId).remove();
	 	$('#nav-tabs a:last').tab('show');
		// scroll tab
		tabScroll();
	});

	 onwindowresize();

	/**
	 * 窗口尺寸
	 */
	 function onwindowresize() {
	 	var bH = $(window).height(), bW = $(window).width();
	 	var rT = $(".row-offcanvas").offset().top;

	 	$(".well.sidebar-nav").css({'height': bH-rT-10});
	 }

	/*
	 * 浏览器窗口大小变化
	 */
	 $(window).resize(function() {
	 	onwindowresize();
	 });

	 /**
	 * load content for mobile device
	 */
	function loadContent(outlink, url) {
		if(outlink) {
			window.open(url);
		} else {
			$.ajax({
				dataType: "html",
				type: "Get",
				url: url,
				data: {timestamp: +new Date()},
				success: function (html, textStatus) {
					// replace the `HTML` tags with `NOTHTML` tags
					// and the `BODY` tags with `NOTBODY` tags
					html = html.replace(/(<\/?)html( .+?)?>/gi,'$1NOTHTML$2>', html)
					html = html.replace(/(<\/?)body( .+?)?>/gi,'$1NOTBODY$2>', html)
					// select the `notbody` tag and log for testing
					//console.log($(html).find('notbody').html());

					var content_wrap = $('<div>').append($(html).find('notbody').html());
					$('#tab-content').empty().append(content_wrap);
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) {
					$('#tab-content').append(textStatus);
				}
			});
		}
	}

	/**
	 * 新建选项卡
	 * @param id 栏目id
	 * @param title 栏目标题
	 * @param isFrame 是否内嵌页面
	 * @param src 栏目地址
	 */
	function addTab(id, title, isFrame, urlOrContent) {
		// duplicate only one tab
		if($('#nav-tabs a[href=#'+id+']').length != 0) {
			$('#nav-tabs a[href=#'+id+']').tab('show');
			return false;
		}

		// create the tab
		var closeBtn = $('<button>')
		.text('×')
		.addClass('close closeTab')
		.click(function(event) {
			var tabId = $(this).parent('a').attr('href');

			$(this).parents('.nav-tabs > li').remove();

			$(tabId).remove();
			// make the last tab active
			$('#nav-tabs a:last').tab('show');
			// scroll tab
			tabScroll();
		});

		var a = $('<a>')
		.text(title)
		.attr({
			'data-toggle': 'tab',
			'href': '#'+id
		})
		.prepend(closeBtn)
		.dblclick(function(event) {
			var tabId = $(this).attr('href');

			$(this).parents('li').remove();

			$(tabId).remove();
			// make the last tab active
			$('#nav-tabs a:last').tab('show');
			// scroll tab
			tabScroll();
		});

		$('<li>')
		.append(a)
		.appendTo($('#nav-tabs'));

		// create the tab content
		var elm = "";
		if (isFrame) {
			elm = $('<iframe>')
			.attr({
				src: urlOrContent,
				frameborder: 0
			})
			.css({
				width: '100%'
			})
			.load(function() {
				//console.log(this.contentWindow.document.body.offsetHeight);
				this.style.height =
				this.contentWindow.document.body.offsetHeight + 'px';
			});
		} else {
			elm = urlOrContent;
		}

		$('<div>')
		.attr({
			id: id
		})
		.addClass('tab-pane')
		.append(elm)
		.appendTo($('#tab-content'));

		// make the new tab active
		$('#nav-tabs a:last').tab('show');
		// scroll tab
		tabScroll();
	};

	/**
	 * scroll tab
	 */
	function tabScroll() {
		tabScroll(null);
	};

	/**
	 * scroll tab
	 * @param direction 方向
	 */
	function tabScroll(direction) {
		var navTabsWrapWidth = $('.nav-tabs-wrap').width();
		var navTabsWidth = 0, offset = 150, speed = 200;

		$.each($('#nav-tabs > li'), function(i, n) {
			/* iterate through object */
			navTabsWidth += $(n).width();
		});

		//console.log(navTabsWrapWidth+ ' - '+navTabsWidth+' = '+ (navTabsWrapWidth - navTabsWidth));

		// tabs overflow
		if(navTabsWidth > navTabsWrapWidth) {
			// auto scroll
			if (direction == null) {

				$('.nav-tabs')
				.animate({'margin-left': navTabsWrapWidth - navTabsWidth}, speed);

				$('.navbar-tabs .tabs-prev')
				.addClass('active')
				.removeClass('invisible');

				$('.navbar-tabs .tabs-next')
				.removeClass('invisible');

			} else { // click scrollbar
				// width difference between navTabsWidth and navTabsWrapWidth
				var widthDef = navTabsWidth - navTabsWrapWidth,
				marginLeft = $('.nav-tabs').css('margin-left').replace('px', '').replace('-', '');

				// scroll to right
				if(direction == 'right') {
					if(marginLeft - offset < 0) {
						offset = 0;
						$('.navbar-tabs .tabs-prev')
						.removeClass('active');
					} else {
						offset = offset - marginLeft;
						$('.navbar-tabs .tabs-prev, .navbar-tabs .tabs-next')
						.addClass('active');
					}
					$('.nav-tabs')
					.animate({'margin-left':offset}, speed);

				} else { // scroll to left

					if(widthDef - offset - marginLeft <= 0) {
						offset = 0 - widthDef;
						$('.navbar-tabs .tabs-next')
						.removeClass('active');
					} else {
						offset = 0 - offset - marginLeft;
						$('.navbar-tabs .tabs-prev, .navbar-tabs .tabs-next')
						.addClass('active');
					}
					$('.nav-tabs')
					.animate({'margin-left': offset}, speed);

				}

			}
		} else if ($('.nav-tabs').css('margin-left')<'0px') { // reset

			$('.nav-tabs')
			.animate({'margin-left': 0}, speed)
			.removeAttr('margin-left');

			$('.navbar-tabs .tabs-prev, .navbar-tabs .tabs-next')
			.addClass('invisible')
			.removeClass('active');
		}
	};

	function scrollToElement( target ) {
		var speed = 800;
		var destination = jQuery( target ).offset().top;
		jQuery( 'html:not(:animated),body:not(:animated)' ).animate( { scrollTop: destination}, speed, function() {
			window.location.hash = target;
		});
		return false;
	}
});