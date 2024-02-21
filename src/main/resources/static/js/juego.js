$(document).ready(function () {
	//CONFIGURACIÓN DE WEBSOCKET
	
	let nJugadores = jugadores.length
	let stompClient;
	const socket = new SockJS("/websocket-example");
	stompClient = Stomp.over(socket);

	//Conectarse a la partida
	stompClient.connect({}, function () {
		//Los demás jugadores notan tu entrada en la partida y puedes ver cada jugador que se une y se va en tiempo real
		stompClient.send("/app/join", {}, idPartida + "," + jugador.avatar + "," + jugador.nombreUsuario);
		//Recibir datos del jugador
		stompClient.subscribe("/topic/player", function (respuesta) {
			let miNombre = jugador.nombreUsuario
			let datosJugadorConectado = respuesta.body.split(",")
			//Un jugador acaba de salir de la partida
			if (datosJugadorConectado.length == 2 && datosJugadorConectado[0] == idPartida) {
				nJugadores--
				//Si sólo queda un jugador en la partida y la partida ya ha comenzado, se termina
				if (nJugadores == 1 && iniciada) {
					stompClient.send("/app/end", {}, jugador.nombreUsuario + "," + puntosPartida + "," + idPartida)
				}
				$(".perfil").each(function () {
					let nombre = $(this).children().eq(1).html()
					if (nombre == datosJugadorConectado[1]) {
						$(this).remove()
					}
				})
				//Un jugador acaba de entrar en la partida
			} else {
				let idPartidaJugadorConectado = datosJugadorConectado[0]
				let avatarJugadorConectado = datosJugadorConectado[1]
				let nombreJugadorConectado = datosJugadorConectado[2]
				//Añadir el perfil del jugador que acaba de entrar en la partida
				if (idPartidaJugadorConectado == idPartida && nombreJugadorConectado != miNombre) {
					nJugadores++
					let fila1 = $("#fila1")
					let perfil = $("<div>").prop("class", "perfil")
					let avatar = $("<div>").prop("class", "avatar").css("background-image", "url(../img/" + avatarJugadorConectado + ".png)")
					let nombre = $("<div>").prop("class", "nombreJugador").html(nombreJugadorConectado)
					let puntuacion = $("<div>").html("0")
					fila1.append(perfil.append(avatar, nombre, puntuacion))
					if (tipoPartida == "publica") {
						//Comienza la partida (Partida pública)
						$.ajax({
							url: "/startGame", method: "POST", data: { idPartida: idPartida }, beforeSend: request => request.setRequestHeader(header, token),
							success: function () {
								stompClient.send("/app/start", {}, idPartida);
							},
							error: function () {
								alert("Error Fatal")
							}
						})
					}
				}
			}

		})
		//Comienza la partida
		stompClient.subscribe("/topic/start", function (respuesta) {
			let idPartidaUsuario = parseInt(respuesta.body)
			if (idPartidaUsuario == idPartida) {
				letrasRonda = listaLetras[posicionListaLetras]
				iniciada = true
				nuevaRonda()
			}
		})
		let resultados = []
		//Se toman los resultados de la ronda o los resultados finales de todos los jugadores
		stompClient.subscribe("/topic/results", function (respuesta) {
			let resultadosJugador = respuesta.body.split(",")
			if (resultadosJugador[resultadosJugador.length - 1] == idPartida) {
				resultados.push(resultadosJugador)
				if (resultados.length == nJugadores) {
					if (resultadosJugador.length == 5) {
						posicionListaLetras += 1
						letrasRonda = listaLetras[posicionListaLetras]
						mostrarResultados(resultados)
						resultados = []
						//Se da un tiempo a los jugadores para ver sus resultados, después se prepara la nueva ronda
						setTimeout(function () {
							rondas++
							$("#resultados").modal("hide")
							nuevaRonda()
						}, 10000)
					//Termina la partida
					} else {
						mostrarResultadoFinal(resultados)
						finalizada = true
					}
				}
			}
		})
	})
	//CONFIGURACIÓN DEL JUEGO
	
	//Se muestran los resultados finales de la partida
	function mostrarResultadoFinal(resultados) {
		resultados.sort(function (resultadosJugador1, resultadosJugador2) {
			return resultadosJugador2[1] - resultadosJugador1[1];
		});
		let tabla = "<table class='table table-striped table-dark text-center'><tr><th>Posición</th><th>Usuario</th><th>Puntuación</th></tr>";
		let posicion = 0
		for (let resultadosJugador of resultados) {
			posicion++
			let fila = "<tr><td>" + posicion + "</td><td>" + resultadosJugador[0] + "</td><td>" + resultadosJugador[1] + "</td></tr>"
			tabla += fila
		}
		tabla += "</table>"
		$("#resultados .modal-title").html("Resultados de la partida")
		$("#resultados .modal-body").html(tabla)
		$("#resultados").modal("show")
	}

	//Se muestran los resultados de la ronda
	function mostrarResultados(resultados) {
		let tabla = "<table class='table table-striped table-dark text-center'><tr><th>Usuario</th><th>Categoría</th><th>Puntuación</th><th>Palabras</th></tr>";
		for (let resultadosJugador of resultados) {
			let fila = "<tr><td>" + resultadosJugador[0] + "</td><td>" + resultadosJugador[1] + "</td><td>" + resultadosJugador[2] + "</td><td>" + resultadosJugador[3] + "</td></tr>"
			tabla += fila
		}
		tabla += "</table>"
		$("#resultados .modal-body").html(tabla)
		$("#resultados").modal("show")
		actualizarPerfiles(resultados)
	}

	//Se actualizan las puntuaciones de los jugadores al finalizar la ronda
	function actualizarPerfiles(resultados){
		for (let resultadosJugador of resultados) {
			let nombreJugador = resultadosJugador[0]
			let puntuacionJugador = resultadosJugador[2]
			$(".perfil").each(function () {
				let nombrePerfil = $(this).children().eq(1).html()
				let puntuacionPerfil = $(this).children().eq(2)
				if (nombrePerfil == nombreJugador){
					puntuacionPerfil.html(parseInt(puntuacionPerfil.html())+parseInt(puntuacionJugador))
				}
			})
		}
	}

	//Mostrar ventana de inicio de partida
	$("#iniciarPartida").modal("show")
	//Empezar el juego por el anfitrión
	$("body").on("click", "#empezar", function () {
		if ($(".perfil").toArray().length < 2) {
			$(".modal-footer > label").html("Tiene que haber un mínimo de 2 jugadores en la partida.")
		} else {
			$("#iniciarPartida").modal("hide")
			$.ajax({
				url: "/startGame", method: "POST", data: { idPartida: idPartida }, beforeSend: request => request.setRequestHeader(header, token),
				success: function () {
					stompClient.send("/app/start", {}, idPartida);
				},
				error: function () {
					alert("Error Fatal")
				}
			})
		}
	})

	//El jugador sale de la partida si hace clic en el botón de "Abandonar"
	$("body").on("click", "#btnAbandonar", function () {
		stompClient.send("/app/exit", {}, idPartida + "," + jugador.nombreUsuario);
		$.ajax({
			url: "/exitGame", method: "POST", data: { idPartida: idPartida, userID: jugador.idUsuario, finalizada: finalizada, puntosPartida: puntosPartida }, beforeSend: request => request.setRequestHeader(header, token)
		})
	})

	//Nueva ronda
	function nuevaRonda() {
		$("#botones").prop("hidden", "hidden");
		eventosElegirPuntuacion(false)
		$(".puntuacion").each(function () {
			if ($(this).css("color") == "rgb(255, 0, 0)") {
				$(this).css({ "color": "blue", "cursor": "auto" }).prop("class", "")
			}
		})
		limpiarLetras("deshacer")
		limpiarCategorias()
		limpiarPalabras()
		limpiarPostit()
		//Si se han acabado todas las rondas, se termina la partida
		if (rondas == 8) {
			stompClient.send("/app/end", {}, jugador.nombreUsuario + "," + puntosPartida + "," + idPartida)
		}
		//Si no, se continúa jugando
		else {
			generarLetras()
			$(".letra").prop("hidden", "");
			$("#letras2").prop("hidden", "");
			$("#mensaje > span").html("El turno empezará en ")
			$("#tiempo").html("5")
			$("#tiempo2").html("")
			$("#mensaje").prop("hidden", "")
			//Cuenta atrás para empezar la nueva ronda
			intervalo = setInterval(function () {
				$("#tiempo").html(parseInt($("#tiempo").html()) - 1)
				//Se acaba el tiempo
				if ($("#tiempo").html() == "0") {
					clearInterval(intervalo);
					$("#mensaje").prop("hidden", "hidden")
					empezarJuego()
				}
			}, 1000)
		}
	}

	//Cuenta atrás de la ronda actual
	function tiempoRestante() {
		$("#tiempo2").html("150")
		intervalo = setInterval(function () {
			$("#tiempo2").html(parseInt($("#tiempo2").html()) - 1)
			//Se acaba el tiempo
			if ($("#tiempo2").html() == "0") {
				$("#mensaje > span").html("Elige tu puntuación")
				$("#tiempo").html("")
				$("#mensaje").prop("hidden", "")
				clearInterval(intervalo);
				eventosElegirPuntuacion(true)
				eventoEscribirPalabras(false)
				eventoClickLetra(false)
				eventoEnviar(false)
				eventoLimpiar(false)
				$("#botones").prop("hidden", "hidden");
				//Se da un tiempo a los jugadores para elegir su puntuación
				setTimeout(function () {
					//Se calculan los resultados de la ronda
					let puntosTurno = parseInt($("#total").html())
					let categoria
					let palabras = ""
					$(".puntuacion").each(function () {
						if ($(this).css("color") == "rgb(255, 0, 0)") {
							categoria = $(this).parent().children().html()
						}
					})
					$("#tablaPalabras td").each(function () {
						if (!$(this).html() == "") {
							palabras += $(this).html() + " "
						}
					})
					if (categoria == null) {
						let puntuaciones = []
						$(".puntuacion").each(function () {
							if ($(this).css("color") == "rgb(255, 255, 255)"){
								puntuaciones.push(parseInt($(this).html()))
							}
						})
						puntosTurno = Math.max(...puntuaciones)
						$(".puntuacion").each(function () {
							if (parseInt($(this).html()) == puntosTurno) {
								$(this).css("color", "red")
								categoria = $(this).parent().children().html()
								return false
							}
						})
					}
					puntosPartida += puntosTurno
					//Se envían los resultados de la ronda
					stompClient.send("/app/results", {}, jugador.nombreUsuario + "," + categoria + "," + puntosTurno + "," + palabras + "," + idPartida)
				}, 10000)
			}
		}, 1000)
	}

	let palabrasDiccionario = []

	//Se almacenan todas las palabras del diccionario
	$.ajax({
		url: "/showWords",
		method: "GET",
		success: function (palabras) {
			for (palabra of palabras) {
				let textoPalabra = palabra.palabra
				palabrasDiccionario.push(textoPalabra)
			}
		},
		error: function () {
			alert("Error fatal")
		}
	})

	let letrasEscritas = 0

	//Deshacer todas las letras escritas
	function limpiarLetras(evento) {
		if (letrasEscritas > 0) {
			$("#letras2 > div").each(function () {
				limpiarLetra($(this), evento)
			})
		}
	}

	//Se limpia la libreta de las palabras escritas
	function limpiarPalabras() {
		$("#tablaPalabras td").each(function () {
			if ($(this).html() != "") {
				$(this).html("")
			}
		})
		idPalabra = 1
	}

	//Se pone en 0 todos los contadores del post-it
	function limpiarPostit() {
		$("#postit > table td").html("0")
		$("#postit #inicial").html("")
	}

	//Se limpia la tabla de categorías después de cada ronda
	function limpiarCategorias(){
		palabrasFormadas = []
		palabras3 = []
		palabras4 = []
		palabras5 = []
		palabras6 = []
		palabrasmas7 = []
		inicialesRepe = []
		mejorInicial = ""
		mejorContador = 0
		longitudes = []
		escalerasFormadas = []
		$(".npalabras").each(function(){
			$(this).css("color","white")
		})
		$(".puntuacion").each(function(){
			if ($(this).css("color") == "rgb(255, 255, 255)"){
				$(this).html("0")
			}
		})
		$("#numPalabras").children().eq(1).html("0")
		$("#yacht").children().eq(1).css("color","white")
		$("#total").css("color","white").html("0")
	}

	//Comienza la nueva ronda
	function empezarJuego() {
		$("#botones").prop("hidden", "");
		eventoClickLetra(true)
		eventoEscribirPalabras(true)
		eventoEnviar(true)
		eventoLimpiar(true)
		tiempoRestante()
	}

	//Se activan o desactivan los eventos de elegir la puntuación
	function eventosElegirPuntuacion(estado) {
		if (!estado) {
			$("body").off("click", ".puntuacion")
			$("body").off("mouseover", ".puntuacion")
			$("body").off("mouseout", ".puntuacion")
		}
		else {
			$("body").on("mouseover", ".puntuacion", function () {
				$(this).css({ "color": "red", "cursor": "pointer" })
			})
			$("body").on("mouseout", ".puntuacion", function () {
				$(this).css({ "color": "white", "cursor": "auto" })
			})
			$("body").on("click", ".puntuacion", function () {
				let puntuacionElegida = $(this).html()
				$("#total").html(puntuacionElegida).css("color", "red")
				$(this).css({ "cursor": "auto" })
				eventosElegirPuntuacion(false)
			})
		}
	}

	//Se activan o desactivan los eventos del botón "Limpiar"
	function eventoLimpiar(estado) {
		if (estado) {
			$("body").on("click", "#btnLimpiar", function () {
				limpiarLetras("deshacer")
			})
		}
		else {
			$("body").off("click", "#btnLimpiar")
		}
	}

	//Se activan o desactivan los eventos del botón "Enviar"
	function eventoEnviar(estado) {
		if (estado) {
			$("body").on("click", "#btnEnviar", function () {
				enviarPalabra()
			})
		} else {
			$("body").off("click", "#btnEnviar")
		}

	}

	//Inicialización de variables
	let token = $("meta[name='_csrf']").attr("content");
	let header = $("meta[name='_csrf_header']").attr("content");
	let rondas = 0
	let puntosPartida = 0
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
	let finalizada = false
	let letrasRonda = ""
	let posicionListaLetras = 0
	let listaLetras = letras.split(",")
	let iniciada = false

	//Se envía la palabra escrita para su posterior validación
	function enviarPalabra() {
		let palabra = ""
		$(".letra2 > div").each(function () {
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
		$("#postit #numeroPalabras > td").html(palabrasFormadas.length)
	}

	//Funcion que actualiza cada una de las categorias de la tabla de la libreta
	function actualizarCategoria(nOcurrencias, categoria, casos, puntuaciones) {
		let nLetrasUsadas = 0
		if (categoria == "#todas") {
			$(".letra > .textoLetra").each(function () {
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
						//Se actualizan los contadores del post-it
						actualizarPostit()
						//Se actualizan todas las diferentes categorías al mismo tiempo
						$(".puntuacion").each(function(){
							if ($(this).css("color") == "rgb(255, 255, 255)"){
								let categoria = $(this).parent().prop("id")
								switch (categoria) {
									case "tres":
										actualizarCategoria(palabras3.length, "#tres", [3, 5, 10], [15, 30, 45])
										break;
									case "cuatro":
										actualizarCategoria(palabras4.length, "#cuatro", [2, 4, 6], [15, 30, 45])
										break;
									case "mascinco":
										actualizarCategoria(palabras5.length + palabras6.length + palabrasmas7.length, "#mascinco", [1, 2, 3], [30, 50, 70])
									break;
									case "ini":
										actualizarCategoria(mejorContador, "#ini", [3, 5, 10], [15, 30, 45])
									break;
									case "numPalabras":
										actualizarCategoria(palabrasFormadas.length, "#numPalabras", null, null)
									break;
									case "yacht":
										actualizarCategoria(palabrasFormadas.length, "#yacht", null, null)
									break;
									case "escalera":
										actualizarCategoria(null, "#escalera", null, [25, 50, 75])
									break;
								}
							}
						})									
						idPalabra++
						return true
					} else {
						
					}
				}
				else {
					
				}
			}
			else {
				
			}
		} else {
			
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
			$(document).keydown(function (event) {
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
							escribirLetra(letra)
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

	function eventoClickLetra(estado){
		if (estado){
			$("body").on("click",".letra",function(){
				escribirLetra($(this))
			})
		} else {
			$("body").off("click",".letra")
		}
	}

	function escribirLetra(letra){
		let letraSeleccionada = letra
		//Añadir letra
		let letra2 = $("<div>").prop("class", "letra2").attr("id", letraSeleccionada.attr("id"))
		let textoLetra = letraSeleccionada.children()
		letra2.append(textoLetra)
		$("#letras2").append(letra2)
		//Borrar letra seleccionada
		letraSeleccionada.css("opacity", "0")
		letraSeleccionada.children().remove()
		letrasEscritas++
	}

	//Se muestran diez letras aleatorias con un mínimo de 3 vocales en cada ronda
	function generarLetras() {
		$(".letra").each(function (i) {
			$(this).children().html(letrasRonda[i])
			$(this).children().css("color", "red")
		})
	}
})