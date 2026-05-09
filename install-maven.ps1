# Maven Installation Script
Write-Host "Installing Apache Maven..." -ForegroundColor Green

$mavenVersion = "3.9.6"
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
$installPath = "C:\Program Files\Apache\Maven"
$zipPath = "$env:TEMP\maven.zip"

# Create directory
Write-Host "Creating installation directory..."
New-Item -ItemType Directory -Force -Path $installPath | Out-Null

# Download Maven
Write-Host "Downloading Maven $mavenVersion..."
Invoke-WebRequest -Uri $mavenUrl -OutFile $zipPath

# Extract
Write-Host "Extracting Maven..."
Expand-Archive -Path $zipPath -DestinationPath $installPath -Force

# Set environment variable
$mavenHome = "$installPath\apache-maven-$mavenVersion"
[Environment]::SetEnvironmentVariable("MAVEN_HOME", $mavenHome, "Machine")

# Add to PATH
$currentPath = [Environment]::GetEnvironmentVariable("Path", "Machine")
if ($currentPath -notlike "*$mavenHome\bin*") {
    [Environment]::SetEnvironmentVariable("Path", "$currentPath;$mavenHome\bin", "Machine")
}

# Update current session PATH
$env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine")

# Cleanup
Remove-Item $zipPath -Force

Write-Host "`nMaven installed successfully!" -ForegroundColor Green
Write-Host "Location: $mavenHome" -ForegroundColor Yellow
Write-Host "`nPlease restart your terminal and run:" -ForegroundColor Cyan
Write-Host "  cd backend" -ForegroundColor White
Write-Host "  mvn spring-boot:run" -ForegroundColor White
Write-Host "`nPress any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
