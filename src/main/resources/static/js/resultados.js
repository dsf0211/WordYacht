$(document).ready(function() {
	let segundos = 0
	intervalo = setInterval(function() {
		segundos++
		if (segundos == 3) {
			clearInterval(intervalo)
			$.ajax({
				url: "/game",
				method: "GET",
				success: function(vista) {
					$('body').html(vista);
				},
				error: function() {
					alert("Error fatal")
				}
			})
		}
	}, 1000)
})
