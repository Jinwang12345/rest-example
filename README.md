# Gestión Aeronáutica – Prueba Piloto
Este repositorio contiene un sistema informático para gestionar información aeronáutica, desarrollado como parte del examen práctico de DSA en la EETAC.

Objetivos del Proyecto
El sistema permite realizar las siguientes operaciones:

Añadir un avión:
Se registra un avión con identificador único, modelo y compañía aérea. Si el identificador ya existe, se actualizan los datos.

Añadir un vuelo:
Se registra un vuelo con identificador único, horas de salida y llegada, avión asignado, origen y destino. Si ya existe un vuelo con ese identificador, se actualizan los datos. Si el avión no está registrado, se muestra un error.

Facturar una maleta:
Se factura una maleta para un usuario en un vuelo. La maleta recibe un identificador automático y se carga en la bodega en orden de facturación (desde el fondo hacia el exterior). Si el vuelo no existe, se muestra un error.

Consultar maletas facturadas:
Devuelve la lista de maletas facturadas en un vuelo, en orden de descarga (última facturada, primera descargada). Esta operación solo se permite una vez que el avión haya llegado a destino.

Implementación
Parte I – Componente de Negocio
Entidades:

Avión: identificador, modelo, compañía aérea.

Vuelo: identificador, hora de salida, hora de llegada, avión asignado, origen, destino.

Maleta: identificador automático, usuario.

Fachada:
La interfaz GestorAeroespacial define las operaciones. Su implementación, GestorAeroespacialImpl, sigue el patrón Singleton y usa estructuras de datos en memoria (Map para aviones y vuelos, y un Map con listas para las maletas facturadas).
Se incluyen trazas con Log4J al inicio y final de cada método.

Pruebas:
Se han creado pruebas JUnit para validar las operaciones principales: añadir avión, añadir vuelo, facturar maleta y consultar maletas.

Parte II – Servicio REST
Endpoints disponibles:

POST /aeroespacial/avion: agregar o modificar un avión.

POST /aeroespacial/vuelo: agregar o modificar un vuelo.

POST /aeroespacial/maleta: facturar una maleta para un vuelo y usuario.

GET /aeroespacial/maleta/{idVuelo}: obtener lista de maletas facturadas en un vuelo (en orden de descarga).

DTO:
Se usa el objeto MaletaRequest para recibir los datos de facturación (id del vuelo y del usuario).

Configuración:
La API REST se ejecuta sobre Grizzly y Jersey. ResourceConfig escanea los servicios y se integran recursos Swagger para la documentación.

Fallo Detectado
La operación GET /aeroespacial/maleta/{idVuelo} no devuelve correctamente la lista de maletas y lanza un error en tiempo de ejecución.

Estado del Proyecto
Funcionalidades implementadas:

Alta y modificación de aviones y vuelos.

Facturación de maletas con identificador automático.

Consulta de maletas facturadas.

Pendiente:

Corregir la operación GET para que devuelva correctamente la lista de maletas.
