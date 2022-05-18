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
1. Uruchomić serwer Appium
2. Uruchomić emulator, bądź podłączyć telefon z Androidem ( z włączonym debugowaniem USB)
3. Wpisać odpowiednią konfigurację w pliku __config.properties__
4. W wierszu poleceń wpisać:
```
java -jar cast_test-1.0-all.jar
```

##Konfiguracja testu
W pliku __config.properties__ można wykonać następujące zmiany:
- CATEGORY
Należy wpisać Accessibility ID kategorii w której znajduje się szukany przez nas film
- MOVIE
Należy wpisać nazwę filmu, którego szukamy
- URL
Należy wpisać adres URL na których serwer Appium nasłuchuje requestów
- PLATFORM_VERSION
Należy wpisać wersję Anroida (napisaną liczbowo)
- DEVICE_NAME
Nazwa urządzenia, wymagane, ale jeżeli podane jest UDID to Appium nazwa jest pomijana.
- UDID
ID urządzenia z komendy adb devices.
- APPPACKAGE
Nazwa pakietu aplikacji.
- APPACTIVITY
Nazwa aktywności, która jest pierwsza po uruchomieniu aplikacji
- APPWAITACTIVITY
Nazwa aktywności na którą czeka skrypt by zacząć testowanie. Można zostawić puste
- APPFILEPATH
Ścieżka absolutna do pliku APK. Gdy podane i zmienna __FULLRESET__ jest ustawiona na _true_ przy każdym teście, aplikacja jest odinstalowana, kasowane są dane aplikacji i instalowana na nowo
- FULLRESET
Jeżeli ustawiona jest na _true_ skrypt odinstalowuje, usuwa dane aplikacji i instaluje na nowo przy każdym uruchomieniu skryptu.

