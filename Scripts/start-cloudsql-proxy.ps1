# scripts/start-cloudsql-proxy.ps1
# Requiere: Google Cloud CLI instalado + "gcloud auth application-default login" hecho

$INSTANCE="project-252263b1-986e-4221-9fd:us-central1:ivo-facu"
$PORT=3307

Write-Host "Iniciando Cloud SQL Auth Proxy..."
Write-Host "Instance: $INSTANCE"
Write-Host "Local:    127.0.0.1:$PORT"
Write-Host ""

cloud-sql-proxy.exe "$INSTANCE" --port $PORT
