<p align="center">
<h1 align="center">Universidad Tecnologica Nacional FRRo</h1>
  <img src="https://lh7-rt.googleusercontent.com/docsz/AD_4nXc7wa-j6kELJWYJ7OUuoBc5xsp69Gi6ev4MG6OBC-hqzZ9Dts1sxN3nIl407JV26-pGRpj6yMIPmFuBmigTE96SGfaG53PGU9hbtm9lLyLusGeFYrn5Lob48nk7PINleJAjSgPbrD3qsKl11cJM0ZjRViQZ?key=99aoIXU-yXzWQdSlUCgKxA">

**Java**

Trabajo Practico

AÑO:2024

**_Alumno_**: Armas Luciano - 47181 - lucianoarmas11@gmail.com
</p>

#


# Enunciado
## Negocio de pedidos de comida y bebida
#### Descripción General:
La página web del negocio permite ver un listado de las comidas y bebidas a la venta, realizar pedidos (agregando comidas y bebidas al mismo), ver sus pedidos activos, cancelar un pedido, loguearse como cliente (ingresando los datos), y crearse una cuenta en caso de no tenerla.

#### Proceso de negocio:

Un cliente ingresa a la página web, previamente logueandose (si no tiene una cuenta se la crea), para visualizar los productos disponibles.

El sistema valida que productos tienen stock y los muestra en el listado de productos, el cliente selecciona los productos que va a pedir y el sistema registra el pedido como pendiente.
 
El cliente confirma el pedido, y el sistema registra el pedido como aceptado. De momento el modo de entrega del pedido es take-away, en un futuro se va a implementar el delivery.

Una vez que el cliente retira el pedido, este mismo es registrado como finalizado en el sistema.

El método de pago actual es en efectivo, más adelante se implementará pago por transferencia.

El cliente puede cancelar un pedido con estado pendiente o aceptado.

#### Reglas de negocio:
-   Un cliente tiene dni, nombre, apellido, email, usuario, contraseña, nivelAcceso.    
-   Un pedido tiene código, precio final, estado. Puede tener una o muchas líneas de pedido.
-   Una lineaPedido pertenece a un solo pedido, y tiene numero, producto, cantidad de dicho producto, precio subtotal (precio del producto * cantidad)
-   Un producto tiene id, stock, precio unitario, descripción y nombre. A su vez puede estar dividido en:
	* Bebidas, tiene cantidad de mililitros.
	* Comidas, tiene cantidad de personas que pueden comer.
-   Un cliente para poder agregar productos al carrito (pedido) tiene que estar logueado.
-   Un cliente puede tener muchos pedidos, pero un pedido es de un solo cliente.

# ABMC
-   ABMC Simple:
	- Cliente
	- Producto
    
-   ABMC Complejo:
	- Línea de Pedido
	- Pedido

# CU
[CURS01_RelizarPedido](https://docs.google.com/document/d/1SD_SEWDuVOsbSuWap0mE0G3X5UiHLoV792xvu6ppA3U/edit?usp=sharing)

# MD
**![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXdC7fll5hTMc55SBYbFk3mgdcFMQfeq1H9mDXSqd997Jc4pHVGtI_M-bwhc5sREx-E6taOMyqBq0gIpHMByAli_FUvlt2JzeIIQuvld0KxkvlK0ZC49-G_CccdxeMpqrsLjbjXDav3BiseC1aSBl0g37eMI?key=99aoIXU-yXzWQdSlUCgKxA)**

[Link del Modelo de Dominio](https://drive.google.com/file/d/1_Y_Fuq-RM5cVEc-OZwAT0QW-C9OtiZMc/view?usp=sharing)

# DER
**![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXc8_J9UtQtF39KtPjqqnAOu_92bzOg2f5E8YW8tK_NTVGc3WhRgivfh78fNwoAUtz0_2cIgYSO3x2vFmiR4uW81VOkXNIwuALrjicrkqpUb8Y96EfjswMS5a8ROoOjoWn__T22-iFcoc9TkVRk1fHnO_e8Z?key=99aoIXU-yXzWQdSlUCgKxA)**
[Link del Diagrama de Entidades](https://drive.google.com/file/d/1K64Y-SSynIrH05rpqr0MtqlEMQiC9KJD/view?usp=sharing)


# LISTADOS
-   Listados Simple:
	- Productos (vista admin)
	- Líneas del Pedido
	- Pedidos (vista cliente)
    
-   Listado Complejo:
	- Clientes
	- Productos (vista cliente)
	- Pedidos (vista admin)

# NIVELES DE ACCESO
- Cliente
- Administrador


# REQ EXTRAS
- El sistema permite subir una imagen del producto que se desea crear (en vista administrador), la cual se almacena localmente en los archivos de la web.
- El sistema permite eliminar una imagen del producto que se desea eliminar (en vista administrador).


# CAPTURAS DE PANTALLA
[Fotos_TP_Java_ArmasLuciano](https://docs.google.com/document/d/1ZWzVoty5b8J1rRUjyBN8sLK9mFP_U6WsIPRjYMoLOGo/edit?usp=sharing)

# CODIGO
[Codigo_TP_Java_ArmasLuciano_47181](https://docs.google.com/document/d/1wvv5DWgfUoCJeeRl0rlhSr8pDDdyirchznMQ9n0Mq1M/edit?usp=sharing)

