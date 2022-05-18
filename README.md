# cast-Test-Orange
Test, który ma na celu znalezienie listy aktorów w danym filmie za pomocą __Accessibility ID__ kategorii i __nazwy__ filmu.

## Wymagania
Do działania test wymaga:
- Appium server,
- Java Development Kit (JDK) 17,
- ADB,
- emulator Androida, bądź fizyczne urządzenie.

## Jak zbudować test
Należy wejść do folderu testu za pomocą wiersza poleceń i wpisać poniższą komendę:
```
gradlew build
```
W folderze build/libs pojawi się plik __cast_test-1.0-all.jar__ testu i plik konfiguracyjny __config.properties__

## Jak uruchomić test
W wierszu poleceń
```
java -jar cast_test-1.0-all.jar
```

##Konfiguracja testu
