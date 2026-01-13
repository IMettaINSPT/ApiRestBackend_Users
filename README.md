Cloud SQL Proxy – Guía Completa de Instalación (MySQL en Google Cloud)
====================================================================

Este proyecto utiliza MySQL alojado en Google Cloud mediante Cloud SQL Proxy.
El uso del proxy evita depender de IPs públicas, firewall o SSL manual.

Seguir TODOS los pasos en orden.

--------------------------------------------------------------------
REQUISITOS PREVIOS
--------------------------------------------------------------------

1) Java
- Java 21 (o la versión definida en el proyecto)

Verificar:
java -version

--------------------------------------------------------------------
GOOGLE CLOUD
--------------------------------------------------------------------

2) Instalar Google Cloud SDK

Descargar desde:
https://cloud.google.com/sdk/docs/install

Durante la instalación:
- Instalar gcloud CLI
- Agregar gcloud al PATH
- Usar PowerShell como shell por defecto

Verificar:
gcloud --version

--------------------------------------------------------------------

3) Autenticarse en Google Cloud (una sola vez)

Ejecutar:
gcloud auth login

Se abrirá el navegador.
Iniciar sesión con una cuenta que tenga acceso al proyecto.

Cerrar y volver a abrir PowerShell.

--------------------------------------------------------------------

4) Seleccionar el proyecto correcto

Ejecutar:
gcloud config set project project-252263b1-986e-4221-9fd

Verificar:
gcloud config list

--------------------------------------------------------------------
CLOUD SQL PROXY
--------------------------------------------------------------------

5) Descargar Cloud SQL Proxy

Descargar el binario para Windows desde:
https://cloud.google.com/sql/docs/mysql/sql-proxy#install

Archivo:
cloud-sql-proxy.exe

Ubicarlo, por ejemplo, en:
C:\tools\cloud-sql-proxy\

(La ubicación no es obligatoria, solo debe conocerse la ruta.)

--------------------------------------------------------------------
USO DEL SCRIPT DEL PROYECTO (FORMA RECOMENDADA)
--------------------------------------------------------------------

El proyecto incluye un script para levantar el proxy automáticamente.

Estructura requerida en el proyecto:

/scripts
  └── start-cloudsql-proxy.ps1

El script debe contener la ejecución del proxy apuntando a la instancia
y puerto correctos.

Ejemplo de contenido del script:

------------------------------------------------
start-cloudsql-proxy.ps1
------------------------------------------------
C:\tools\cloud-sql-proxy\cloud-sql-proxy.exe "project-252263b1-986e-4221-9fd:us-central1:ivo-facu" --port 3307
------------------------------------------------

--------------------------------------------------------------------

6) Levantar el Cloud SQL Proxy usando el script

IMPORTANTE:
Pararse en la RAÍZ del proyecto (donde está la carpeta "scripts").

Ejecutar en PowerShell:

powershell -ExecutionPolicy Bypass -File .\scripts\start-cloudsql-proxy.ps1

Salida esperada:
Listening on 127.0.0.1:3307
The proxy has started successfully

IMPORTANTE:
- NO cerrar esta consola
- El proxy debe quedar corriendo mientras se use el backend

--------------------------------------------------------------------
VARIABLES DE ENTORNO
--------------------------------------------------------------------

7) Configurar variables de entorno (una sola vez)

En Windows:
Configuración del sistema → Variables de entorno

Agregar:

DB_URL=jdbc:mysql://127.0.0.1:3307/NOMBRE_DB
DB_USER=usuario_mysql
DB_PASS=password_mysql

Luego cerrar y volver a abrir IntelliJ / PowerShell.

--------------------------------------------------------------------
LEVANTAR BACKEND
--------------------------------------------------------------------

8) Ejecutar el backend

Desde la carpeta del backend:

mvn spring-boot:run

Si todo está correcto:
- Hibernate conecta
- La aplicación inicia correctamente
- La API queda disponible

--------------------------------------------------------------------
REGLA IMPORTANTE
--------------------------------------------------------------------

PRIMERO el proxy, DESPUÉS el backend.

Si el proxy no está levantado:
- La base no conecta
- Hibernate falla
- Aparecen errores de Communications link failure

--------------------------------------------------------------------
ERRORES COMUNES
--------------------------------------------------------------------

El comando del script falla:
- Verificar que exista la carpeta "scripts"
- Verificar que el archivo se llame exactamente:
  start-cloudsql-proxy.ps1
- Verificar la ruta al cloud-sql-proxy.exe dentro del script

--------------------------------------------------------------------

Puerto ocupado:
Usar otro puerto (ej: 3308) en el script y actualizar DB_URL.

--------------------------------------------------------------------

El proxy levanta pero el backend no conecta:
- Verificar que el proxy siga corriendo
- Revisar DB_URL
- Verificar usuario y password de MySQL

--------------------------------------------------------------------
QUE NO HAY QUE HACER
--------------------------------------------------------------------

- No usar IP pública de la instancia
- No agregar IPs autorizadas
- No configurar SSL manual
- No tocar firewall

--------------------------------------------------------------------
RESUMEN RÁPIDO
--------------------------------------------------------------------

1) Instalar Google Cloud SDK
2) gcloud auth login
3) gcloud config set project project-252263b1-986e-4221-9fd
4) Descargar cloud-sql-proxy.exe
5) Ejecutar:
   powershell -ExecutionPolicy Bypass -File .\scripts\start-cloudsql-proxy.ps1
6) Configurar variables de entorno
7) Levantar el backend

Con estos pasos, cualquier integrante del proyecto puede trabajar
sin tocar infraestructura ni configuración de red.
