@echo off
title Cloud SQL Auth Proxy

set INSTANCE=project-8526e033-6e34-451a-8cd:us-central1:tp-facu
set PORT=3307

echo Iniciando Cloud SQL Auth Proxy...
echo Instance: %INSTANCE%
echo Local:    127.0.0.1:%PORT%
echo.

"C:\tools\cloud-sql-proxy\cloud-sql-proxy.exe" %INSTANCE% --address 127.0.0.1 --port %PORT%

pause
