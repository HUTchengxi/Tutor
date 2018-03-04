$(window).load(function(){
     $('.preloader').fadeOut('slow');
});


/* =Main INIT Function
-------------------------------------------------------------- */
function initializeSite() {

	"use strict";

	//OUTLINE DIMENSION AND CENTER
	(function() {
	    function centerInit(){

			var sphereContent = $('.sphere'),
				sphereHeight = sphereContent.height(),
				parentHeight = $(window).height(),
				topMargin = (parentHeight - sphereHeight) / 2;

			sphereContent.css({
				"margin-top" : topMargin+"px"
			});

			var heroContent = $('.hero'),
				heroHeight = heroContent.height(),
				heroTopMargin = (parentHeight - heroHeight) / 2;

			heroContent.css({
				"margin-top" : heroTopMargin+"px"
			});

	    }

	    $(document).ready(centerInit);
		$(window).resize(centerInit);
	})();

	// Init effect 
	$('#scene').parallax();

};
/* END ------------------------------------------------------- */

/* =Document Ready Trigger
-------------------------------------------------------------- */
$(window).load(function(){

	initializeSite();
	(function() {
		setTimeout(function(){window.scrollTo(0,0);},0);
	})();

    $('#countdown').countdown({
        date: "December 13, 2017 18:03:26",
        render: function(data) {
            var el = $(this.el);
            var now = new Date();
            el.empty()
            //.append("<div>" + this.leadingZeros(data.years, 4) + "<span>years</span></div>")
                .append("<div><i class='hour'>" + this.leadingZeros(now.getHours(), 2) + " </i><span>小时</span></div>")
                .append("<div><i class='minute'>" + this.leadingZeros(now.getMinutes(), 2) + " </i><span>分钟</span></div>")
                .append("<div><i class='second'>" + this.leadingZeros(now.getSeconds(), 2) + " </i><span>秒</span></div>");
        }
    });

    /**
     * 自动获取并显示时间
     */
    var auto_gettime = function(){

		var now = new Date();
		$(".hour").text(now.getHours());
		$(".minute").text(now.getMinutes());
		$(".second").text(now.getSeconds());
	};
    window.setInterval(auto_gettime, 1000);

});
/* END ------------------------------------------------------- */

