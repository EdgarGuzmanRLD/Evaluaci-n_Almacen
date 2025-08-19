# Evaluaci-n_Almacen
Repositorio creado con el fin de compartir mi prueba para ser evaluada.

Hola que tal soy Edgar Emanuel Guzmán Zuñiga y para este proyecto utilice los siguientes recursos para elaborar el proyecto.
*************************************************************
*IDE y lenguaje:
-Utilice el IDE Netbeans 8.2.
-Java 8 con el JDK en su versión 8u202
*DBMS
Para la gestión de Bases de datos opte por:
-SQL SERVER 2022 y SQL Managment Sudio versión 19.3.4

*************************************************************
PASOS PARA PONER EN MARCHA EL PROYECTO

*Preparación: 
En caso de no contar con el IDE, JDK y DBMS instalados, guardare en una carpeta adjunta con el nombre *INSTALABLES*.
-Para el IDE se sugiere añadir el servidor *TOMCAT* en la instalación del IDE.

-En lo que respecta a la configuración de la base de datos.   
  1.-Tendremos que instalar una instancia de SQL SERVER primero de manera personalizada o basica.
  2.-Es de suma importancia que se agregue la autenticación mixta y coloquemos una contraseña para ingresar.

*NOTA:* En caso de ya contar con lo requerido anteriormente solo se necesitara el usuario y contraseña para acceder a la BD
Después de seguir estos pasos de preparación haremos lo siguiente.

/*
PASO 1. 
*/
Abriremos una query o script nuevo desde MASTER a lo que ejecutaremos el archivo para crear la BD.
**Esta sentencia esta en el archivo Almacen.txt en la carpeta de *INSTALABLES*

/*
PASO 2.
+/

Despues de configurar la BD lo siguiente es configurar la conexion. 

Abriremos el proyecto y en la carpeta de *Source Packages* nos dirigiremos a *Control* y por ultimo abriremos el archivo *Conexion*

/*
PASO 3
*/

Cambiaremos los valores de "url,usuario y password" a los valores que se encuentren en nuestro servidor.
Para la ruta unicamente es necesario cambiar el siguiente apartado: 
jdbc:sqlserver://localhost:1433;  //En caso de ser nuestra primera instancia SQL SERVER podemos dejarlo de esta manera y funcionara.
*En caso de que no funcione o no ser nuestra unica instancia cambiaremos al nombre de nuestra instancia de servidor de la siguiente manera:*
jdbc:sqlserver://localhost\\Nombre_Instancia; //EJ:jdbc:sqlserver://localhost\\SQLEXPRESS;

Al terminar estos pasos ya estamos listos para realizar pruebas.

