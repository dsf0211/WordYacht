$(document).ready(function() {
	// VENTANA VER ESTADISTICAS	
	$("body").on("click", "#estadisticas", function() {
		mostrarRanking();
		$("#verRanking").prop("class","btn btn-primary col-sm mb-3")
		$("#verHistorial").prop("class","btn btn-secondary col-sm mb-3")
	})	
	// Mostrar ranking
	$("body").on("click", "#verRanking", function() {
		mostrarRanking();
		$("#verRanking, #verHistorial").toggleClass("btn-secondary btn-primary");
	})
	function mostrarRanking() {
		$.ajax({
			url: "/ranking",
			method: "GET",
			success: function(data) {
				let tabla = "<table class='table table-striped table-dark text-center'><tr><th>Posición</th><th>Usuario</th><th>Puntuación media</th></tr>";
				for (let i = 0; i < data.length; i++) {
					let usuario = data[i]
					let fila = "<tr>"
					let clasificacion = "<td>" + (parseInt(i) + 1) + "</td>"
					let username = "<td>" + usuario.nombreUsuario + "</td>"
					let media = "<td>" + usuario.media + "</td>"
					fila += clasificacion + username + media + "</tr>"
					tabla += fila
				}
				tabla += "</table>"
				$("#tablaEstadisticas").html(tabla);

			},
			error: function() {
				alert("Error Fatal")
			}
		})
	}
	// Mostrar historial de partidas
	$("body").on("click", "#verHistorial", function() {
		$.ajax({
			url: "/history",
			method: "GET",
			data: { idUsuario: idUser },
			success: function(data) {
				let tabla = "<table class='table table-striped table-dark text-center'><tr><th>Fecha</th><th>Puntuación</th><th>Nº de jugadores</th><th>Posición</th></tr>";
				for (let i = 0; i < data.length; i++) {
					let fila = "<tr>"
					let fecha = "<td>" + data[i][0] + "</td>";
					let puntuacion = "<td>" + data[i][1] + "</td>";
					let numJugadores = "<td>" + data[i][2] + "</td>";
					let posicion = "<td>" + data[i][3] + "</td>";
					fila += fecha + puntuacion + numJugadores + posicion + "</tr>";
					tabla += fila;
				}
				tabla += "</table>";

				$("#verRanking, #verHistorial").toggleClass("btn-secondary btn-primary");
				$("#tablaEstadisticas").html(tabla);
			},
			error: function() {
				alert("Error Fatal");
			}
		});
	});
	// VENTANA VER PERFIL
	// Modificar avatar
	$("body").on("click", ".botonAvatar", function() {	
		// Cambiar la imagen del avatar del usuario segun la selección
		let avatar = this.style.getPropertyValue("background-image")
		$("#avatarUsuario").css("background-image", avatar)
		// Almacenar el valor id del elemento seleccionado
		let id = $(this).attr("id");
        $("#avatarUsuario").data("value", id);
	})
	// Guardar el valor del nuevo avatar en la base de datos
	$("body").on("click", "#guardarAvatar", function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");		
		let avatar = $("#avatarUsuario").data("value");
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
		// Comprobar que solo se ha introducido letras
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
	// Eliminar palabra/s
	$("body").on("click", '#eliminarPalabra', function() {
		var seleccionadas = [];
		$(".seleccionada").each(function() {
			var palabra = $(this).html();
			seleccionadas.push(palabra);
		});
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		// El usuario no ha seleccionado ninguna palabra a eliminar
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
	// Crear tabla de usuarios
	function crearTablaUsuarios(data) {
		let tabla = "<table class='table table-striped table-dark text-center'><tr><th>Usuario</th><th>Nombre</th><th>Apellidos</th><th>E-Mail</th><th>Puntuación</th><th>Fecha de registro</th><th>Rol</th></tr>";
		
		for (let indice in data) {
			let usuario = data[indice]
			let fila = "<tr>";
			let username = "<td>" + usuario.nombreUsuario + "</td>";
			let nombre = "<td>" + usuario.nombre + "</td>";
			let apellidos = "<td>" + usuario.apellidos + "</td>";
			let email = "<td>" + usuario.email + "</td>";
			let puntos = "<td>" + usuario.acumulado + "</td>";
			let fecha_registro = "<td>" + usuario.fecha_registro + "</td>";
			let rol = "<td><select class='select-rol custom-select bg-dark text-light'>" +
				"<option value='1'>Jugador</option>" +
				"<option value='2'>Administrador</option>" +
				"</select></td>";
			fila += username + nombre + apellidos + email + puntos + fecha_registro + rol + "</tr>";
			tabla += fila;
		}
		tabla += "</table>";
		$("#tablaUsuarios").html(tabla);
		// Establecer el valor de id rol como valor seleccionado por defecto en el select
		for (let usuario of data) {
        	let selectRol = $("#tablaUsuarios").find("tr:contains(" + usuario.nombreUsuario + ")").find(".select-rol");
        	selectRol.val(usuario.idRol);
    	}
	}	
	// Peticion asincrona para mostrar la tabla de usuarios
	$("body").on("click", "#verUsuarios", function() {
		$.ajax({
			url: "/showUsers",
			method: "GET",
			success: function(data) {
				crearTablaUsuarios(data);
			},
			error: function() {
				alert("Error Fatal")
			}
		})
	})
	// Modificar el rol de un usuario al cambiar el valor del select
	$("body").on("change", ".select-rol", function() {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
	    let username = $(this).closest("tr").find("td:first").text();  
	    let idRol = $(this).val();
	    // Realizar la solicitud AJAX para cambiar el rol del usuario
	    $.ajax({
	        url: "/modRolUser",
	        method: "POST",
	        data: { username: username, idRol: idRol },
			beforeSend: request => request.setRequestHeader(header, token),
	        error: function() {
	            alert("Error al modificar el rol del usuario.");
	        }
	    });
	});
	// Seleccionar usuarios de la tabla de usuarios
	$("body").on("click", "#tablaUsuarios > table td:not(:has(select))", function() {
		$(this).parent().toggleClass("seleccionada");
	});
	//Borrar usuarios de la tabla de usuarios
	$("body").on("click", "#eliminarUsuarios", function() {
		// Obtener los nombres de usuario de las filas seleccionadas
		var seleccionados = [];
		$(".seleccionada").each(function() {
			var username = $(this).children().eq(0).html();
			seleccionados.push(username);
		});
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		// El usuario no ha seleccionado ningún usuario a eliminar
		if (!seleccionados.length > 0) {
			alert("No hay ningun usuario seleccionado")
		}
		else {
			// Petición asincrona para eliminar usuarios
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
					alert("No se pudo eliminar el usuario")
				}
			});
		}
	});

	// VENTANA PARTIDA PRIVADA
	// Validar código introducido para unirse a partida privada
	$("body").on("click", "#unirsePrivada", function() {
		let codigo = parseInt($("#codigo").val())
		if (codigos.includes(codigo)) {
			$("#unirsePrivada").attr("href", "/joinPrivate?code=" + codigo + "&idUser=" + idUser)
			$("#errorCodigo").html("")
		} else {
			$("#errorCodigo").html("El código no existe")
			$("#unirsePrivada").attr("href", "#")
		}
	});
	// Limpiar input y mensaje al abrir la ventana modal
	$("body").on("click", "#privada", function() {		
		$("#codigo").val("");
		$("#errorCodigo").html("")
	});
});