# Proyecto Marketplace (nombre en proceso)
Aplicación de compraventa de productos.

Parte privada: gestión de tus productos, comunicarte con otros usuarios y comprar productos.Los usuarios administradores podrán gestionar todos los productos y usuarios.

Parte pública: darte de alta, login, ver productos.

# Vídeo DEMO
Se adjunta el link del vídeo explicativo del proyecto:
https://www.youtube.com/watch?v=6Kwo7EgnMgI&feature=youtu.be

# Despliegue
- (SE REALIZÓ PARA LA FASE 3 => EN LA VERSIÓN ACTUAL SE HA MIGRADO A DOCKER)

Se ha hecho el despliegue en Ubuntu Server 18.04, y para ello se han seguido los siguientes pasos:
### 1: Instalación de los paquetes necesarios
Se debe instalar tanto MySQL como java. Para ello se puede hacer todo con un solo comando:

`sudo apt install mysql-server openjdk-8-jre-headless openjdk-8-jdk git`

### 2: Inicialización y creación de la base de datos:
Se debe inicializar el servicio de `mysql` y crear la base de datos en si, además de crear un usuario y darle permisos sobre ella.
Iniciamos el servicio con: 

`sudo systemctl enable mysql`
`sudo systemctl start mysql`

Se crean la base de datos y el usuario siguiendo la documentación oficial:

https://spring.io/guides/gs/accessing-data-mysql/

Para que funcione correctamente el nombre de la base de datos debe ser `db_marketplace_sec`, el usuario debe ser `admin@localhost` y su contraseña `rooturjc`.

### 3: Ejecución
Para ello nos descargamos el proyecto con:

`git clone https://github.com/nipsn/ProyectoDAD`

En la carpeta `executables` hay 3 archivos `.jar` que se deberán ejecutar cada uno en una `tty`. En nuestro caso, la aplicación principal (`demo-0.0.1-SNAPSHOT.jar`) en la `tty0`, el servicio interno encargado de la creación de facturas (`pdfgen.jar`) en la `tty1` y el servicio interno que gestiona los correos electrónicos (`sender-emails-source.jar`) en la `tty2`.

Para ejecutar cada uno de ellos se usa el comando `java -jar ejecutable.jar`, donde `ejecutable.jar` corresponde a cada uno de los ejecutables.

# Estructura Dockerizada
La estructura se basa en la composición de varios contenedores Docker conectados entre sí. Se ha usado la herramienta de "docker-compose" para relacionar los contenedores. A continuación se especifican los contenedores:

### 1: Aplicación Web
Contenedor donde se ejecuta la aplicación web principal

### 2: Aplicación Web Duplicada
Contenedor donde se ejecuta la aplicación web principal duplicada para ser tolerante a fallos de caída de servidor.

### 3: Base de Datos - MySQL
Contenedor donde se ejecuta la base de datos.

### 4: Servicio Interno - Mailing
Contenedor donde se ejecuta la aplicación que se encarga de mandar un email cuando haces un pedido.

### 5: Servicio Interno - Generar Factura
Contenedor donde se ejecuta la aplicación que se encarga de generar una factura de un pedido concreto.

### 6: Balanceador de carga - HAProxy
Contenedor donde se ejecuta el balanceador de carga que se encarga de redirigir las peticiones a los contenedores de la Aplicación Web Principal.

![alt text](dockers.png)

# Modelo Entidad-Relación
![alt text](modeloER.jpeg)

# Diagrama UML
![alt text](UML.png)


# Servicios internos 
Los servicios internos se ejecutan en contenedores Docker independientes y se comunican a través de la exposición de los puertos correspondientes con la aplicación web principal.

**Generar una factura en PDF**

<kbd>![enter image description here](Interfaz/InvoiceGeneratorInterface.png)</kbd>

**Envío de correos como notificacion**

<kbd>![enter image description here](Interfaz/EmailSenderInterface.png)</kbd>

 ## Pantallas de la aplicación

**Diagrama de Flujo de Pantalla**

![enter image description here](Pantallas/FlujoPantallas.png)
**Diagrama de Flujo de Pantalla FINAL**

Diagrama de flujo resultante para la primera fase del desarrollo

![enter image description here](Pantallasfinales/flujofinal.png)

**Pantalla Inicial**

<kbd>![enter image description here](Pantallasfinales/vistalistadoproductos.png)</kbd>

**Pantalla de Producto**

<kbd>![enter image description here](Pantallasfinales/vistaproducto.png)</kbd>

**Pantalla de Perfil del Usuario**

<kbd>![enter image description here](Pantallasfinales/vistausuario.png)</kbd>

**Pantalla de Registrarse**

<kbd>![enter image description here](Pantallasfinales/vistaregistro.png)</kbd>

**Lista de usuarios**

<kbd>![enter image description here](Pantallasfinales/vistalistadousuarios.png)</kbd>

**Pantalla de Iniciar Sesión**

<kbd>![enter image description here](Pantallasfinales/vistalogin.png)</kbd>

**Pantalla de Chat sobre Producto**

<kbd>![enter image description here](Pantallasfinales/vistachat.png)</kbd>

**Pantalla de Lista de chats**

<kbd>![enter image description here](Pantallasfinales/vistalistadochats.png)</kbd>

**Pantalla de Gestión de Productos del Usuario**

<kbd>![enter image description here](Pantallasfinales/vistapedidosdeusuario.png)</kbd>

**Pantalla de Subir un Producto**

<kbd>![enter image description here](Pantallasfinales/vistasubirproducto.png)</kbd>


# Integrantes del grupo
Nombre | Apellidos | Correo | Github 
--- | --- | --- | --- 
Miguel | Santiago Herrero | m.santiagohe@alumnos.urjc.es | msantiagocsb
Diego | Díaz Pérez | d.diazp@alumnos.urjc.es | didushow
Oscar | Nydza Nicpoñ | o.nydza.2017@alumnos.urjc.es | nipsn
