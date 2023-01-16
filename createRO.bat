set batdir=%~dp0
@echo %batdir%
cd %batdir%
mvn clean test -Dtest="ROCreationUtilTestCase#createRO*"
Pause