# Monkey configurable ADB Input - Telnet

Monkey que permite la ejecución de procesos aleatorios a través de entradas a través de ADB y Telnet, como son:

1. TAP
2. Text
3. Swipe
4. Keyevent
5. Girar el dispositivo
6. Cambiar la velocidad del internet
7. Cambiar los valores de los sensores

El usuario podrá seleccionar aquellos comandos que desee.

## Modo de ejecución

El archivo ***[main.java]*** contiene el código necesario para la ejecución de las pruebas en este es necesario establecer algunos parámteros como son:

* [Configuración de rutas de ejecución de ADB y Telnet]
* [Configuración de Apks a ejecutar]: Como son Nombre de la APP, ubicación de la APK y nombre del paquete.

Una vez ejecutado el archivo main.java, por medio de la consola se solicitarán los datos de:

1. Número de eventos a realizar.
2. Aplicación a probar, depende de la configuración de las APKs
3. Comandos a ejecutar.

![commands](https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/img/comandos.gif?raw=true)

### Aplicaciones Probadas.

Se han utilizado las aplicaciones:

* [Loop Habit Tracker]
* [Antennapod]

Las Apks de las mismas se encuentran dentro del directorio **[apks]**

### Orden de ejecución de los comandos.

Dependiendo de la selección de comandos a probar, cada comando se ejecutará en un orden aleatorio, la ejecución se hace por medio de dos ciclos infinitos ```while(true)``` el ciclo interior generará un número aleatorio relacionado al número del evento a realizar, sí este ha sido seleccionado se ejecuta, una vez se ha completado la ejecución [de todos los comandos sin repetir] se realiza un nuevo ciclo, hasta completar la [totalidad de número de eventos establecidos].

### Ejecución Loop Habit Tracker

![Habit](https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/img/habitos_gif.gif?raw=true)

https://youtu.be/bAKvqKqY-mI


### Ejecución Antennapod

![Antennapod](https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/img/antennaPod_gif.gif?raw=true)

https://youtu.be/WpjgP7PTm_k


### Fuentes.

* [Android: Setting network latency]
* [ADB Shell Input Events]



### Autor
* Jorge Rubaino [@ostjh]
* Código: 201510711

License
----
MIT

[@ostjh]:https://twitter.com/ostjh
[main.java]:https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/main.java
[Configuración de rutas de ejecución de ADB y Telnet]:https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/main.java#L15
[Configuración de Apks a ejecutar]:https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/main.java#L67
[de todos los comandos sin repetir]:https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/main.java#L198
[Android: Setting network latency]:https://newfivefour.com/android-adb-network-latency.html
[ADB Shell Input Events]:https://stackoverflow.com/a/8483797
[Loop Habit Tracker]:https://github.com/iSoron/uhabits
[Antennapod]:https://f-droid.org/packages/de.danoeh.antennapod/
[apks]:https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/tree/master/ADB_Input_Telnet/apks
[totalidad de número de eventos establecidos]:https://github.com/jhrubiano10/Taller_07_MISO_4208_ADB_Input_Telnet_Espresso/blob/master/ADB_Input_Telnet/main.java#L271
