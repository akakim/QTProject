<script type="text/javascript">
		$(window).ready(function() {
			var swiper = new Swiper('.floor-container', {
				scrollbar: '.floor-scrollbar',
				slideClass: 'floor-slide',
				wrapperClass: 'floor-wrapper',
				scrollbarHide: false,
				slidesPerView: 'auto',
				grabCursor: true,
				onTouchStart: function (swiper) {
					swiper.scrollbar.drag.addClass('scrolling');
				},
				onTouchEnd: function (swiper) {
					swiper.scrollbar.drag.removeClass('scrolling');
				}
			});
		});

        function showAlert(){
           alert("showAlert");
        }


	</script>