$(document).ready(function() {
	// VENTANA VER ESTADISTICAS	
	// Funcion para mostrar estadisticas
	$("body").on("click", "#estadisticas", function() {
		$.ajax({
			url: "/stats",
			method: "GET",
			success: function(data) {
				let tabla = "<table class='table table-striped table-dark text-center'><tr><th>Posición</th><th>Usuario</th><th>Puntuación</th></tr>";
				for (let indice in data) {
					let usuario = data[indice]
					let fila = "<tr>"
					let clasificacion = "<td>" + (parseInt(indice) + 1) + "</td>"
					let username = "<td>" + usuario.nombreUsuario + "</td>"
					let acumulado = "<td>" + usuario.acumulado + "</td>"
					fila += clasificacion + username + acumulado + "</tr>"
					tabla += fila
				}
				tabla += "</table>"

				$("#tablaEstadisticas").html(tabla)
			},
			error: function() {
				alert("Error Fatal")
			}
		})
	})
	// VENTANA VER PERFIL	
	// Funcion para modificar avatar
	$("body").on("click", "#guardarAvatar", function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		let split = $("#avatar").css("background-image").split("/")
		let avatar = parseInt(split[split.length - 1][0]);
		let idUser = parseInt($("#idUsuario").html());
		$.ajax({
			url: "/modAvatar",
			method: "POST",
			data: { avatar: avatar, idUsuario: idUser },
			beforeSend: request => request.setRequestHeader(header, token),
			success: function() {

			},
			error: function() {
				alert("Error Fatal")
			}
		})
	})
	$("body").on("click", ".botonAvatar", function(event) {
		let avatar = event.target.style.getPropertyValue("background-image")
		$("#avatar").css("background-image", avatar)
	})

	// VENTANA GESTIONAR PALABRAS	
	// Mostrar lista de palabras filtrada al introducir al menos 3 caracteres
	$('#palabra').on('input', function() {
		$('#mensajePalabras').html("");
		var filtro = $(this).val().trim().toUpperCase();
		if (filtro.length >= 3) {
			$.ajax({
				url: "/showWords",
				method: "GET",
				success: function(data) {
					$('#listaPalabras').empty();
					data.forEach(function(e) {
						if (e.palabra.substring(0, filtro.length) == filtro) {
							$('#listaPalabras').append($("<li>").html(e.palabra));
						}
					});
				},
				error: function() {
					alert("Error Fatal")
				}
			})
		} else {
			$('#listaPalabras').empty();
		}
	});
	// Añadir palabra	
	$("body").on("click", "#aniadirPalabra", function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		let palabra = $("#palabra").val().toUpperCase();

		if (!/^[A-Za-zÑñ]+$/.test(palabra)) {
			$('#mensajePalabras').html("Debe introducir sólo letras");
			$('#palabra').val("");
		} else {
			if (palabra.length >= 3) {
				$.ajax({
					url: "/addWord",
					method: "POST",
					data: { palabra: palabra },
					success: function(data) {
						$('#mensajePalabras').html(data);
						$("#palabra").val("");
					},
					beforeSend: request => request.setRequestHeader(header, token),
					error: function() {
						alert("Error Fatal")
					}
				})
			} else {
				$('#mensajePalabras').html("La palabra debe tener al menos 3 letras");
			}
		}

	})
	// Seleccionar palabras de la lista
	$(document).on('click', '#listaPalabras li', function() {
		$(this).toggleClass('seleccionada');
	});
	$("body").on("click", '#eliminarPalabra', function() {
		var seleccionadas = [];
		$(".seleccionada").each(function() {
			var palabra = $(this).html();
			seleccionadas.push(palabra);
		});
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		if (!seleccionadas.length > 0) {
			$('#mensajePalabras').html("No ha seleccionado ninguna palabra");
		}
		else {
			// Peticion asincrona para eliminar las palabras seleccionadas		
			$.ajax({
				url: '/deleteWord',
				method: 'POST',
				traditional: true,
				data: { palabras: seleccionadas },
				success: function(data) {
					$('#listaPalabras').empty();
					$('#mensajePalabras').html(data);
					$("#palabra").val("");
				},
				beforeSend: request => request.setRequestHeader(header, token),
				error: function() {
					alert("Error Fatal")
				}
			});
		}
	});

	// VENTANA GESTIONAR USUARIOS
	// Funcion para crear tabla de usuarios
	function crearTablaUsuarios(data) {
		let tabla = "<table class='table table-striped table-dark'><tr><th>Usuario</th><th>Nombre</th><th>Apellidos</th><th>E-Mail</th><th>Puntuación</th><th>Fecha de registro</th><th>Rol</th>"
		for (let indice in data) {
			let usuario = data[indice]
			let fila = "<tr>"
			let username = "<td>" + usuario.nombreUsuario + "</td>"
			let nombre = "<td>" + usuario.nombre + "</td>"
			let apellidos = "<td>" + usuario.apellidos + "</td>"
			let email = "<td>" + usuario.email + "</td>"
			let puntos = "<td>" + usuario.acumulado + "</td>"
			let fecha_registro = "<td>" + usuario.fecha_registro + "</td>"
			let rol = "<td>" + usuario.rol.nombre + "</td>"
			fila += username + nombre + apellidos + email + puntos + fecha_registro + rol + "</tr>"
			tabla += fila
		}
		tabla += "</table>"

		$("#tablaUsuarios").html(tabla)
	}
	// Peticion asincrona para mostrar la tabla de usuarios
	$("body").on("click", "#verUsuarios", function() {
		$.ajax({
			url: "/showUsers",
			method: "GET",
			success: function(data) {
				crearTablaUsuarios(data)
			},
			error: function() {
				alert("Error Fatal")
			}
		})
	})

	// Seleccionar las filas de la tabla
	$("body").on("click", "#tablaUsuarios > table td", function() {
		$(this).parent().toggleClass("seleccionada");
	});
	//Borrar las filas seleccionadas
	$("body").on("click", "#eliminarUsuarios", function() {
		// Obtener los nombres de usuario de las filas seleccionadas
		var seleccionados = [];
		$(".seleccionada").each(function() {
			var username = $(this).children().eq(0).html();
			seleccionados.push(username);
		});
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");

		if (!seleccionados.length > 0) {
			alert("No hay ningun usuario seleccionado")
		}
		else {
			// Enviar petición asincrona
			$.ajax({
				url: "/deleteUser",
				method: "POST",
				traditional: true,
				data: { usernames: seleccionados },
				beforeSend: request => request.setRequestHeader(header, token),
				success: function(data) {
					crearTablaUsuarios(data)
				},
				error: function() {
					alert("Error Fatal")
				}
			});
		}
	});
	// VENTANA PARTIDA PRIVADA
	// Crear partida
	let idUser = parseInt($("#idUsuario").html());
	$("body").on("click", "#crearPrivada", function() {
		if($('#crearPrivada').html() == "Obtener código"){
				$.ajax({
				url: "/createPrivate",
				method: "GET",
				data: { idUsuario: idUser },
				success: function(data) {
					$('#codigo').html(data.codPrivada);
					$('#crearPrivada').html("Iniciar partida");
					$('#crearPrivada').attr('href', '/game?code='+data.codPrivada+'&idUser='+idUser);										
				},
				error: function() {
					alert("Error Fatal")
				}
			})
		}
		
	});


	// Unirse a partida	
	$("body").on("click", "#unirsePrivada", function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url: "/joinPrivate",
			method: "POST",
			data: { idUsuario: idUser, codigo: codigo },
			beforeSend: request => request.setRequestHeader(header, token),
			success: function(data) {
			},
			error: function() {
				alert("Error Fatal")
			}
		})
	});
});
