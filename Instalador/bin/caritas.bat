@echo off

set LOCALCLASSPATH=.

for %%i in ("..\lib\*.jar") do CALL .\lcp.bat %%i
for %%i in ("..\lib\*.zip") do CALL .\lcp.bat %%i
for %%i in ("..\dist\*.jar") do CALL .\lcp.bat %%i
for %%i in ("..\dist\*.zip") do CALL .\lcp.bat %%i
for %%i in ("..\bin\log4j.properties") do CALL .\lcp.bat %%i
rem Set LOCALCLASSPATH = C:\Archivos de programa\Java\jre6\bin;%PATH%

echo %LOCALCLASSPATH%

java -cp %LOCALCLASSPATH% com.reparadoras.caritas.CaritasApp