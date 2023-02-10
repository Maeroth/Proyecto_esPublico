//Carga inicial de componentes
$(document).ready( function () {
	
	/******************************************************
		DIALOGS JQUERY: Cuadros de diálogo de la aplicación
	*******************************************************/

	//Diálogo de error
	$("#dialog_error").dialog({
		autoOpen: false,
		width: "500px"
	});

	//Diálogo de OK. Tiene botón.
	$("#dialog_ok").dialog({
		autoOpen: false,
		width: "500px",
		buttons: {
			Ok: function(){
				$( this ).dialog( "close" );
			}
		}
	});
	
	//Diálogo que lanza el script de CSV. Tiene botón de lanzamiento de proceso.
	$("#dialog_cargaDatosEjericio1").dialog({
		autoOpen: false,
		width: "500px",
		modal: true,
		buttons: {
			Ok: function(){
				$( this ).dialog( "close" );
				sendAjaxRequestEjercicio1(); //AJAX - Petición de proceso CSV
			}
		}
	});
	
	//Diálogo que lanza el script de API Star Wars. Tiene botón de lanzamiento de proceso
	$("#dialog_cargaDatosEjericio2").dialog({
		autoOpen: false,
		width: "500px",
		modal: true,
		buttons: {
			Ok: function(){
				$( this ).dialog( "close" );
				sendAjaxRequestEjercicio2_Actores(); //AJAX - Petición de proceso API STAR WARS
			}
		}
	});
	
	/******************************************************
		DATATABLES JQUERY: Tablas de la aplicación con Jquery
	*******************************************************/
    $("#tablaActores").DataTable({
    	 bPaginate: true, //Queremos que la tabla haga páginas de los datos
    	 bLengthChange: false, //No queremos mostrar por número de registros (por defecto 10) 
    	 bFilter: true, //Queremos un cuadro de filtro para filtrar por texto
    	 bInfo: true, //Queremos información sobre la tabla (abajo izquierda)
    	 bAutoWidth: false, //No queremos que se autoajuste
    	 language: {
    	      emptyTable: "Busca las películas de los actores de Star Wars y aparecerán aquí",
    	      info: "Mostrando página _PAGE_ de _PAGES_. Total: _MAX_ registros",
    	      infoEmpty: "",
    	      search: "Búsqueda",
    	      paginate:{
    	    	  previous: "Anterior",
    	    	  next: "Siguiente"
    	      }
    	    }
    }); 
    
	/******************************************************
		ACORDION JQUERY: Permite dar efecto a los bloques de código señalados
	*******************************************************/
    $("#acordion").accordion({
    	collapsible: true, //Que tenga el efecto de abrirse/cerrarse al clicar sobre el título
    	active: 'none' //Que no venga abierto ninguno de sus apartados
    });
  } );
	
/******************************************************
	PETICIONES AJAX
*******************************************************/

//Llamada al proceso de lectura CSV, escritura en BD y escritura CSV ordenado
function sendAjaxRequestEjercicio1() {
	$("#loading1").show(); //mostramos el logo de la nave "loading"
	$("#boton1").hide(); //ocultamos el botón que lanza el proceso
    $.ajax({
        url: '/ejercicio1',
        type: 'POST',
        contentType: 'application/json',
        success: function(response) {
            $("#dialog_ok").text("El proceso del ejercicio 1 se ha ejecutado correctamente").dialog("open"); //mostramos dialog de éxito
            $("#loading1").hide(); //Ocultamos la imagen de loading
            $("#boton1").show(); //Volvemos a mostrar el botón que lanza el proceso
        },
        error: function(jqXHR, textStatus, errorThrown) { //Caso de error...
    		$("#dialog_error").text("Algo ha ido mal: "+jqXHR.responseText).dialog('open'); //Mostramos el diálogo del error
            $("#loading1").hide(); //Ocultamos la imagen de loading
            $("#boton1").show(); //Volvemos a mostrar el botón que lanza el proceso
         }
      });
}

//Llamada al proceso de Lectura de API STAR WARS, guardado de datos en BD y lectura de los personajes con sus películas
function sendAjaxRequestEjercicio2_Actores() {
	$("#loading2").show(); //mostramos el logo de la nave "loading"
	$("#boton2").hide(); //ocultamos el botón que lanza el proceso
    $.ajax({
    	url: '/ejercicio2_devolverActores',
        type: 'POST',
        contentType: 'application/json',
        success: function(response) {
        	var table = $("#tablaActores").DataTable(); //Tabla del ejercicio 2
        	table.clear(); //limpiamos la tabla de los actores para volver a llenarla con la nueva petición
        	  
        	$.each(response, function(index, item) { //iteramos con los resultados devueltos
        		table.row.add([item.name,item.numTitles, item.titles]).draw(); //Añadimos datos a la tabla
            })
            
			sendAjaxRequestEjercicio2_Peliculas(); //Si todo ha ido bien, llamamos al proceso de obtención de películas (AJAX)
        	 
        },
        error: function(jqXHR, textStatus, errorThrown) { //Caso de error...
        	$("#dialog_error").text("Algo ha ido mal: "+jqXHR.responseText).dialog('open'); //Mostramos el diálogo del error
            $("#loading2").hide(); //Ocultamos la imagen de loading
            $("#boton2").show(); //Volvemos a mostrar el botón que inició el proceso
        }
	});
}

//Llamada al proceso de lectura de películas de STAR WARS en BD      
function sendAjaxRequestEjercicio2_Peliculas() {
	$.ajax({
    	url: '/ejercicio2_devolverPeliculas',
        type: 'POST',
        contentType: 'application/json',
        success: function(response) {
        	$("#bloqueTablaPeliculas").show(); //Mostramos el selector de películas, que inicialmente aparece escondido
        	$.each(response, function(index, item) { //Iteramos los datos recibidos 
        		$("#film_list").append($('<option>',{ //Añadimos las opciones al multiselect
        		value: item.url,	//Url de la película (PK)
        		text: item.title	//Título de la película
            }));
        })
        		
        $("#film_list") //Componente Jquery.multiSelect del multiselect seleccionado
        	.multiSelect({
				addSearchBox:false, //no queremos buscador
				addActionBox:false, //no queremos cuadro de acción 
				animateSearch:false, //No queremos búsqueda animada
				afterSelect: function(){ //Añadimos función de búsqueda AJAX al seleccionar una opción
					sendAjaxRequestEjercicio2_PilotoConduceNaveMasVistaEnPeliculas(); //Busca el actor que ha pilotado la nave que más aparece en las películas actualmente seleccionadas en el multiselect
				},
				afterDeselect: function(){ //Añadimos función de búsqueda AJAX al deseleccionar una opción
					sendAjaxRequestEjercicio2_PilotoConduceNaveMasVistaEnPeliculas(); //Busca el actor que ha pilotado la nave que más aparece en las películas actualmente seleccionadas en el multiselect
				},
				selectableHeader: "<div class='custom-header'>Películas</div>", //Añadimos cabecera a las opciones 
				selectionHeader: "<div class='custom-header'>Películas seleccionadas</div>" //Añadimos cabecera a las opciones seleccionadas
           	}).width(1000);
                
            $("#dialog_ok").text("El proceso del ejercicio 2 se ha ejecutado correctamente").dialog("open");
            $("#loading2").hide(); //Ocultamos la imagen de loading
            $("#boton2").show(); //Mostramos el botón que inició el proceso
          },
          error: function(jqXHR, textStatus, errorThrown) { //errores...
	        	$("#dialog_error").text("Algo ha ido mal: "+jqXHR.responseText).dialog('open'); //diálogo de error
	            $("#loading2").hide(); //escondemos la imagen de loading
	            $("#boton2").show(); //mostramos el botón que inició el proceso
    	  }
	});
}

//Llamada al proceso de lectura del actor que ha pilotado la nave que más aparece en las películas selccionadas en el multiselect
function sendAjaxRequestEjercicio2_PilotoConduceNaveMasVistaEnPeliculas() {
	$.ajax({
		url: '/ejercicio2_devolverPilotoNavesGrupoPeliculas',
	    type: 'POST',
	    contentType: 'application/json',
	    data: JSON.stringify($('#film_list').val()), //opciones seleccionadas en el multiselect
	    success: function(response) {
	    	$("#personaje_nave").text(""); //Limpiamos el span de información
	        if(response !== ""){ //Si response no viene vacío...
	        	//Actualizamos el span de información con los datos que trae la petición
	        	$("#personaje_nave").append("El pesonaje <b>"+response.personName+"</b> pilota la nave <b>"+response.starshipName+"</b> que es la que más aparece en las películas seleccionadas (un total de <b>"+response.totalApariciones+"</b> veces)");
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) { ///errores...
	    	$("#dialog_error").text("Algo ha ido mal: "+jqXHR.responseText).dialog('open'); //diálogo de error

	    }
	});
}