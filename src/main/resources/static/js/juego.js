$(document).ready(function() {
	tiempoRestante("#tiempo")

	function tiempoRestante(id) {
		if (id == "#tiempo") {
			intervalo = setInterval(function() {
				$("#tiempo").html(parseInt($("#tiempo").html()) - 1)
				if ($("#tiempo").html() == "0") {
					clearInterval(intervalo);
					$("#mensaje").remove()
					empezarJuego()
				}
			}, 1000)
		} else if (id == "#tiempo2") {
			intervalo = setInterval(function() {
				$("#tiempo2").html(parseInt($("#tiempo2").html()) - 1)
				if ($("#tiempo2").html() == "0") {
					clearInterval(intervalo);
					eventosElegirPuntuacion(true)
					eventoEscribirPalabras(false)
					eventoEnviar(false)
					eventoLimpiar(false)
					tiempoRestante(null)
				}
			}, 1000)
		} else {
			intervalo = setInterval(function() {
				$("#tiempo2").html(parseInt($("#tiempo2").html()) + 1)
				if ($("#tiempo2").html() == "3") {
					clearInterval(intervalo)
					$("#resultados").modal("show")
					setTimeout(function(){
						$("#resultados").modal("hide")
					},5000)
				}
			}, 1000)
		}
	}

	let palabrasDiccionario = []

	$.ajax({
		url: "/showWords",
		method: "GET",
		success: function(palabras) {
			for (palabra of palabras) {
				let textoPalabra = palabra.palabra
				palabrasDiccionario.push(textoPalabra)
			}
		},
		error: function() {
			alert("Error fatal")
		}
	})

	let letrasEscritas = 0

	//Empieza el turno
	function empezarJuego() {
		generarLetras()
		$(".letra").prop("hidden", "");
		$("#letras2").prop("hidden", "");
		$("#botones").prop("hidden", "");
		eventoEscribirPalabras(true)
		eventoEnviar(true)
		eventoLimpiar(true)
		tiempoRestante("#tiempo2")
	}

	function eventosElegirPuntuacion(estado) {
		if (!estado) {
			$("body").off("click", ".puntuacion")
			$("body").off("mouseover", ".puntuacion")
			$("body").off("mouseout", ".puntuacion")
		}
		else {
			$("body").on("mouseover", ".puntuacion", function() {
				$(this).css({ "font-size": "1.8vh", "cursor": "pointer" })
			})
			$("body").on("mouseout", ".puntuacion", function() {
				$(this).css({ "font-size": "1.7vh", "cursor": "auto" })
			})
			$("body").on("click", ".puntuacion", function() {
				let puntuacionElegida = $(this).html()
				$("#total").html(puntuacionElegida).css("color", "red")
				$(this).css({ "font-weight": "bold", "color": "red", "cursor": "auto" })
				eventosElegirPuntuacion(false)
			})
		}
	}

	//Deshacer todas las letras escritas
	function limpiarLetras(evento) {
		if (letrasEscritas > 0) {
			$("#letras2 > div").each(function() {
				limpiarLetra($(this), evento)
			})
		}
	}

	//Evento de clic del botón Limpiar
	function eventoLimpiar(estado) {
		if (estado) {
			$("body").on("click", "#btnLimpiar", function() {
				limpiarLetras("deshacer")
			})
		}
		else {
			$("body").off("click", "#btnLimpiar")
		}
	}

	//Evento de clic del botón Enviar
	function eventoEnviar(estado) {
		if (estado) {
			$("body").on("click", "#btnEnviar", function() {
				enviarPalabra()
			})
		} else {
			$("body").off("click", "#btnEnviar")
		}

	}

	//Declaracion de variables
	let idPalabra = 1
	let palabrasFormadas = []
	let palabras3 = []
	let palabras4 = []
	let palabras5 = []
	let palabras6 = []
	let palabrasmas7 = []
	let inicialesRepe = []
	let mejorInicial = ""
	let mejorContador = 0
	let longitudes = []
	let escaleras = ["345", "456", "567", "678", "789", "3456", "4567", "5678", "6789", "34567", "45678", "56789"]
	let escalerasFormadas = []

	//Se envía la palabra escrita para su posterior validación
	function enviarPalabra() {
		let palabra = ""
		$(".letra2 > div").each(function() {
			palabra += $(this).html()
		})
		if (validarPalabra(palabra)) {
			limpiarLetras("confirmar")
			actualizarCategoria(null, "#todas", null)
		}
	}

	//Se actualiza la tabla del post-it
	function actualizarPostit() {
		$("#postit #tresletras > td").html(palabras3.length)
		$("#postit #cuatroletras > td").html(palabras4.length)
		$("#postit #cincoletras > td").html(palabras5.length)
		$("#postit #seisletras > td").html(palabras6.length)
		$("#postit #massieteletras > td").html(palabrasmas7.length)
		$("#postit #inicial").html(mejorInicial)
		$("#postit #ocurrenciasInicial").html(mejorContador)
	}

	//Funcion que actualiza cada una de las categorias de la tabla de la libreta
	function actualizarCategoria(nOcurrencias, categoria, casos, puntuaciones) {
		let nLetrasUsadas = 0
		if (categoria == "#todas") {
			$(".letra > .textoLetra").each(function() {
				if ($(this).css("color") == "rgb(0, 0, 0)") {
					nLetrasUsadas++
				}
			})
			if (nLetrasUsadas == 10) {
				$(categoria).children().eq(2).html("45")
			}
		} else if (categoria == "#escalera") {
			for (let escalera of escalerasFormadas) {
				switch (escalera.length) {
					case 3:
						$(categoria).children().eq(1).css("color", "red")
						$(categoria).children().eq(4).html(puntuaciones[0])
						break;
					case 4:
						$(categoria).children().eq(2).css("color", "red")
						$(categoria).children().eq(4).html(puntuaciones[1])
						break;
					case 5:
						$(categoria).children().eq(3).css("color", "red")
						$(categoria).children().eq(4).html(puntuaciones[2])
						break;
				}
			}
		} else if (categoria == "#numPalabras") {
			$(categoria).children().eq(1).html(nOcurrencias)
			$(categoria).children().eq(2).html(nOcurrencias)
		}
		else if (categoria == "#yacht") {
			if (nOcurrencias == 15) {
				$(categoria).children().eq(1).css("color", "red")
				$(categoria).children().eq(2).html("75")
			}
		} else {
			switch (nOcurrencias) {
				case casos[0]:
					$(categoria).children().eq(1).css("color", "red")
					$(categoria).children().eq(4).html(puntuaciones[0])
					break;
				case casos[1]:
					$(categoria).children().eq(2).css("color", "red")
					$(categoria).children().eq(4).html(puntuaciones[1])
					break;
				case casos[2]:
					$(categoria).children().eq(3).css("color", "red")
					$(categoria).children().eq(4).html(puntuaciones[2])
					break;
			}
		}
	}
	//Se valida la palabra enviada
	function validarPalabra(palabra) {
		if (palabrasDiccionario.includes(palabra)) {
			if (idPalabra < 25) {
				if (!palabrasFormadas.includes(palabra)) {
					if (palabra.length >= 3) {
						//Se añade la palabra escrita a la pizarra
						$("#tablaPalabras #" + idPalabra).html(palabra)

						palabrasFormadas.push(palabra)
						//Se almacenan todas las escaleras diferentes formadas hasta el momento
						longitudes.push(palabra.length)
						longitudes.sort()
						longitudes = Array.from(new Set(longitudes))
						let longitudes2 = longitudes.join("")
						for (let escalera of escaleras) {
							if (longitudes2.includes(escalera)) {
								escalerasFormadas.push(escalera)
							}
						}
						escalerasFormadas = Array.from(new Set(escalerasFormadas))
						//Se clasifican las palabras según el número de letra
						switch (palabra.length) {
							case 3:
								palabras3.push(palabra)
								break;
							case 4:
								palabras4.push(palabra)
								break;
							case 5:
								palabras5.push(palabra)
								break;
							case 6:
								palabras6.push(palabra)
								break;
							case 7:
								palabrasmas7.push(palabra)
								break;
						}
						if (palabra.length > 7) {
							palabrasmas7.push(palabra)
						}
						//Se almacena la inicial de la palabra al array de las iniciales
						inicialesRepe.push(palabra[0])
						let inicialesSinRepetir = new Set(inicialesRepe)
						//Se calcula el nº de ocurrencias de cada inicial, y se obtiene el mayor y su correspondiente letra 
						for (let inicialSinRepe of inicialesSinRepetir) {
							let ocurrencias = 0
							for (let inicialRepe of inicialesRepe) {
								if (inicialSinRepe == inicialRepe) {
									ocurrencias++;
								}
							}
							if (ocurrencias > mejorContador) {
								mejorContador = ocurrencias
								mejorInicial = inicialSinRepe
							}
						}
						actualizarPostit()
						//Se actualizan todas las categorías al mismo tiempo
						actualizarCategoria(palabras3.length, "#tres", [3, 5, 10], [15, 30, 45])
						actualizarCategoria(palabras4.length, "#cuatro", [2, 4, 6], [15, 30, 45])
						actualizarCategoria(palabras5.length + palabras6.length + palabrasmas7.length, "#mascinco", [1, 2, 3], [30, 50, 70])
						actualizarCategoria(mejorContador, "#ini", [3, 5, 10], [15, 30, 45])
						actualizarCategoria(palabrasFormadas.length, "#numPalabras", null, null)
						actualizarCategoria(palabrasFormadas.length, "#yacht", null, null)
						actualizarCategoria(null, "#escalera", null, [25, 50, 75])
						idPalabra++
						return true
					} else {
						alert("La palabra debe tener más de 2 letras.")
					}
				}
				else {
					alert("Esa palabra ya está añadida.")
				}
			}
			else {
				alert("No caben más palabras.")
			}
		} else {
			alert("Esa palabra no existe.")
		}

	}

	//Deshacer una letra escrita
	function limpiarLetra(letra, evento) {
		let textoLetraBorrada
		if (evento == "deshacer") {
			textoLetraBorrada = letra.children()
		}
		else if (evento == "confirmar") {
			textoLetraBorrada = letra.children().css("color", "black")
		}
		if (letrasEscritas > 0) {
			let letraBorrada = $("#letras > #" + letra.attr("id"))
			//Recuperar la letra
			letraBorrada.append(textoLetraBorrada)
			letraBorrada.css("opacity", "1")
			//Deshacer la letra escrita
			letra.remove()
		}
	}
	//Evento de tecla que espera recibir una letra de las mostradas, la tecla Retroceso o la tecla Entrar
	function eventoEscribirPalabras(estado) {
		if (estado) {
			$(document).keydown(function(event) {
				let teclaIntroducida = event.key
				//Si se pulsa la tecla Retroceso se deshace la última letra escrita, volviendo a su posición original
				if (teclaIntroducida == "Backspace") {
					if (letrasEscritas > 0) {
						let ultimaLetra = $("#letras2 :last").parent()
						limpiarLetra(ultimaLetra, "deshacer")
					}
					//Si se pulsa la tecla Entrar se limpian todas las letras introducidas y se envía la palabra
				} else if (teclaIntroducida == "Enter") {
					enviarPalabra()
				}
				//Si no, se busca una letra que coincida con la introducida para escribirla
				else {
					//Obtener el array de las letras aleatorias
					let letrasArray = $("#letras > div")
					for (let i in letrasArray) {
						let letra = letrasArray.eq(i)
						let encontrada = false
						//Coincidencia de letra
						if (letra.children().html() == teclaIntroducida.toUpperCase()) {
							//Añadir letra
							let letra2 = $("<div>").prop("class", "letra2").attr("id", letra.attr("id"))
							let textoLetra = letra.children()
							letra2.append(textoLetra)
							$("#letras2").append(letra2)
							//Borrar letra seleccionada
							letra.css("opacity", "0")
							letra.children().remove()
							letrasEscritas++
							encontrada = true
						}
						if (encontrada) {
							break
						}
					}
				}
			});
		} else {
			$(document).off("keydown")
		}
	}

	//Se generan diez letras aleatorias con un mínimo de 3 vocales
	function generarLetras() {
		let vocales = "AEIOU"
		let alfabeto = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"
		let listaLetras = []
		for (let i = 0; i < 3; i++) {
			let vocal = vocales[Math.floor(Math.random() * 5)]
			listaLetras.push(vocal)
		}
		for (let i = 0; i < 7; i++) {
			let letra = alfabeto[Math.floor(Math.random() * 22)]
			listaLetras.push(letra)
		}
		$(".letra").each(function(i) {
			$(this).children().html(listaLetras[i])
		})
	}
})