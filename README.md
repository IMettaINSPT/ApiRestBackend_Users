# Paso a paso completo (Windows) – Cloud SQL Auth Proxy + Spring Boot (MySQL)

Este documento explica **desde cero** cómo preparar **otra máquina** para ejecutar el backend y conectarse a **Google Cloud SQL (MySQL)** usando **Cloud SQL Auth Proxy**, sin depender de IP pública ni *Authorized Networks*.

---

## 0) Qué necesitas en esa máquina

- Windows 10/11
- Acceso a Internet
- Acceso a la cuenta Google que tiene permisos en el proyecto
- Permiso IAM mínimo en el proyecto: **Cloud SQL Client**

---

## 1) Instalar Google Cloud CLI (gcloud)

### 1.1 Descargar
Abrir el navegador y entrar a la guía oficial:

https://cloud.google.com/sdk/docs/install

### 1.2 Instalar
- Descargar el instalador de **Google Cloud CLI** para Windows
- Ejecutarlo con “Next / Next / Finish”

### 1.3 Verificar (en una terminal nueva)
Abrir **PowerShell** (Win → buscar “PowerShell”) y ejecutar:

```powershell
gcloud --version

2) Autenticarse con Google Cloud (ADC)

Esto es obligatorio porque no se usan claves JSON.

2.1 Login normal (abre navegador)

En PowerShell:

gcloud auth login


Elegir la cuenta Google correcta.

2.2 Application Default Credentials (ADC)

En PowerShell:

gcloud auth application-default login


Esto guarda credenciales locales y el proxy las usa.

2.3 Verificar cuenta activa
gcloud auth list


Debe aparecer tu cuenta.
Si la cuenta no tiene permisos del proyecto, el proxy fallará.

3) Descargar Cloud SQL Auth Proxy (cloud-sql-proxy.exe)
3.1 Descargar desde GitHub (Release oficial)

Abrir en el navegador la página oficial de releases:

https://github.com/GoogleCloudPlatform/cloud-sql-proxy/releases

3.2 Elegir el archivo correcto para Windows

Descargar el binario para Windows 64 bits:

cloud-sql-proxy.x.x.x.windows.amd64.exe

Renombrarlo a:

cloud-sql-proxy.exe (para simplificar)

3.3 Guardarlo en una ruta estándar

Crear carpeta:

C:\tools\cloud-sql-proxy\


Mover el archivo ahí con este nombre final:

C:\tools\cloud-sql-proxy\cloud-sql-proxy.exe

3.4 Verificar ejecución

En PowerShell:

C:\tools\cloud-sql-proxy\cloud-sql-proxy.exe --version


Si devuelve versión, está OK.

4) Preparar el repo (script incluido)

En el repo debe existir:

scripts/start-cloudsql-proxy.ps1


(este script es el que creamos para levantar el proxy con validaciones)

4.1 Ejecutar el script (desde la raíz del repo)

Abrir PowerShell en la carpeta del proyecto y ejecutar:

powershell -ExecutionPolicy Bypass -File .\scripts\start-cloudsql-proxy.ps1


Si PowerShell no te deja ejecutar scripts, este comando ya lo “saltea” por -ExecutionPolicy Bypass.

4.2 Resultado esperado

La consola debería mostrar algo como:

Listening on 127.0.0.1:3307
The proxy has started successfully and is ready for new connections!


⚠️ No cerrar esa consola. El proxy debe quedar corriendo mientras uses el backend.

5) Verificar que el puerto local esté abierto

Abrir otra consola PowerShell y ejecutar:

Test-NetConnection 127.0.0.1 -Port 3307


Debe devolver:

TcpTestSucceeded : True

6) Configurar el Backend (Spring Boot) para usar el proxy

El backend debe apuntar a localhost en el puerto del proxy.

6.1 Variables de entorno (IntelliJ)

En IntelliJ: Run/Debug Configurations → Environment variables:

DB_URL=jdbc:mysql://127.0.0.1:3307/DBPoliciaFederal?serverTimezone=UTC
DB_USER=<usuario_mysql>
DB_PASS=<password_mysql>


El DB_USER/DB_PASS son los del usuario MySQL creado en Cloud SQL.

6.2 Levantar la app

Correr Spring Boot normalmente.