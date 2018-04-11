@ECHO ON

call C:\APPS\IC\exec\init_Maven3.5.2_Jdk8x64_nodejs.bat

rem selected profile is dev to allow war usage.

cd server

mvn clean install -Pdev

cd ..

pause