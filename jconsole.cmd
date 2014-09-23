@REM launcher script
@REM Shi.Zhan @ 2013
@REM use 'sbt copyDep' to collect dependencies in "target\scala-2.11\lib"
@REM load packaged JAR from "target\scala-2.11\jenaconsole_2.11-1.0.jar"
@echo off
setlocal

set JAVA_OPTS=%JAVA_OPTS% -Xmx2g
set CP=
set JC_ROOT=%~dp0
@REM set JC_DATA=r:/data
set LIB=%JC_ROOT%target\scala-2.11\lib\
for /f %%i in ('dir /b %LIB%') do call :concat %%i
java -cp "%CP%;%JC_ROOT%target/scala-2.11/jenaconsole_2.11-1.0.jar" JenaConsole %1 %2 %3 %4 %5 %6 %7 %8 %9
endlocal
goto :eof

:concat
set CP=%CP%%LIB%%1;
goto :eof
