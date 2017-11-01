/**
 * Created by jorgerubiano on 30/10/17.
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Math.round;


public class Main {
    public static final String ADB_ROOT = "/Users/jorgerubiano/Library/Android/sdk/platform-tools/";
    public static final String ADB_INPUT = ADB_ROOT + "adb shell input ";
    public static final String INSTALL_APK = ADB_ROOT + "adb install -r ";
    public static final String RUN_APK = ADB_ROOT + "adb shell am start -n ";
    public static final String TELNET_TOKEN = "onimYf2svassNqLG";
    public static final String EMULATOR_PORT = "5554";


    private static BufferedWriter connectToTelnet() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process telnet = rt.exec("telnet localhost "+EMULATOR_PORT);
        return new BufferedWriter(new OutputStreamWriter(telnet.getOutputStream()));
    }

    public static void rotate() throws IOException {
        BufferedWriter out = connectToTelnet();
        out.write("auth "+TELNET_TOKEN+"\n");
        out.write("rotate\n");
        out.write("quit\n");
        out.flush();
    }

    //Cambiar la velocidad del internet...
    public static void changeVelocity(String velocity) throws IOException {
        BufferedWriter out = connectToTelnet();
        out.write("auth "+TELNET_TOKEN+"\n");
        out.write("network delay "+(velocity)+"\n");
        out.write("network status\n");
        out.write("quit\n");
        out.flush();
    }

    //Cambiar los valores de los sensores...
    public static void changeSensors(String sensor, float min, float medium, float max) throws IOException {
        BufferedWriter out = connectToTelnet();
        out.write("auth "+TELNET_TOKEN+"\n");
        out.write("sensor set "+(sensor)+" "+(min)+":"+(medium)+":"+(max)+"\n");
        out.write("sensor get "+(sensor)+"\n");
        out.write("quit\n");
        out.flush();
    }

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        
        /*
            Para las propiedades de las Apps a probar...
                * Nombre de la Aplicación...
                * Ubicación del APK...
                * Nombre del paquete de la APK...
        */

        String[][] apksTest = new String [][] {
                {
                        "Habits",
                        "/Users/jorgerubiano/Documents/node/maestria/org.isoron.uhabits_35.apk",
                        "org.isoron.uhabits/.MainActivity"
                },
                {
                        "AntennaPod",
                        "/Users/jorgerubiano/Documents/node/maestria/de.danoeh.antennapod_1060203.apk",
                        "de.danoeh.antennapod/.activity.MainActivity"
                }
        };
        
        //Listado de comandos disponibles a ejecutar...
        String[][] commands = new String [][] {
                {
                        "TAP",
                        "",
                        "tap",
                        "1080;1920",
                        "0"

                },
                {
                        "Text",
                        "",
                        "text",
                        "Test!$%;prueba?¿)(;valor$%&/;nuevoª!$%&(=);$%·$%&",
                        "0"

                },
                {
                        "Swipe",
                        "",
                        "swipe",
                        "49 156 400 156;1000 156 700 156;523 404 523 583",
                        "0"
                },
                {
                        "Keyevent",
                        "",
                        "keyevent",
                        "1;85",
                        "0"
                },
                {
                        "Girar el dispositivo",
                        "",
                        "rotate",
                        "",
                        "0"
                },
                {
                        "Cambiar la velocidad del internet",
                        "",
                        "speed",
                        "gsm;hscsd;gprs;edge;umts;hsdpa;lte;evdo;full",
                        "0"
                },
                {
                        "Cambiar los valores de los sensores",
                        "",
                        "sensor",
                        "acceleration;gyroscope;magnetic-field;orientation;temperature;proximity;light;pressure;humidity;magnetic-field-uncalibrated",
                        "0"
                }
        };

        //Para leer valor por pantalla...
        BufferedReader br = null;

        try {

            Random random = new Random(12345);
            int numEvents = 0;
            br = new BufferedReader(new InputStreamReader(System.in));

            //Crear un menú para probar...
            System.out.print("Opciones de la prueba: \n\n");
            System.out.print("número de eventos a realizar: ");
            numEvents = Integer.parseInt(br.readLine());

            System.out.print("Probar Habits (1) o AntennaPod (2): ");
            int appTest = Integer.parseInt(br.readLine()) - 1;

            System.out.print("Se probará la aplicación: " + apksTest[appTest][0] + "\n");
            System.out.print("Se iniciará con la instalación de la aplicación" + "\n");

            //Para instalar/re-instalar la aplicación...
            Process install = rt.exec(INSTALL_APK + apksTest[appTest][1]);
            System.out.println("Ejecutando comando: " + INSTALL_APK + apksTest[appTest][1] + "\n");
            install.waitFor();
            System.out.print("Se ha instalado " + apksTest[appTest][0] + " en el dispositivo" + "\n\n");
            System.out.print("Ejecutando " + apksTest[appTest][0]);

            //Para abrir la aplicación...
            Process runapp = rt.exec(RUN_APK + apksTest[appTest][2]);
            System.out.println("Ejecutando comando: " + RUN_APK + apksTest[appTest][0] + "\n");
            runapp.waitFor();
            System.out.println("Aplicación en ejecución.\n");

            System.out.print("******* Selección de comandos a ejecutar*******\n\n");

            //Captura los comandos a ejecutar...
            for(int i = 0; i < commands.length; i++) {
                System.out.print("Realizar comando "+(commands[i][0])+" (y/n): ");
                commands[i][1] = br.readLine();
            }

            //Para saber la proporción en la que se ejecutarán los comandos...
            int contCommands = 0;
            for(int i = 0; i < commands.length; i++) {
                if(commands[i][1].equals("y")) {
                    contCommands = contCommands + 1;
                }
            }

            //El número de veces que se ejecutará cada comando...
            int numberOfTimes = round(numEvents / contCommands);
            System.out.print("Número de veces por comando: " + numberOfTimes + "\n");

            //Ejecutar los comandos...
            while(true){
                int numberOfExecutions = 0;
                int[] ordenExecute = new int[commands.length];
                int contExecuteCommand = 0;
                while (true){
                    int randomCommand = random.nextInt(commands.length - 0) + 1;
                    int numCommand = randomCommand - 1;
                    if(commands[numCommand][1].equals("y")) {
                        boolean exist = false;
                        for(int c = 0; c < ordenExecute.length; c++) {
                            if(ordenExecute[c] == randomCommand) {
                                exist = true;
                                break;
                            }
                        }
                        if(!exist) {
                            if(Integer.parseInt(commands[numCommand][4]) == numberOfTimes) {
                                numberOfExecutions = numberOfExecutions + 1;
                            } else {
                                //No ha terminado las ejecuciones...
                                if(commands[numCommand][2] != "rotate") {
                                    //Para las opciones de los comandos...
                                    String[] parts = commands[numCommand][3].split(";");
                                    switch (commands[numCommand][2]) {
                                        case "tap" : //Para las acciones de tap...
                                            int x = random.nextInt(1080);
                                            int y = random.nextInt(1920);
                                            rt.exec(ADB_INPUT + "tap " + x + " " + y);
                                            System.out.println(commands[numCommand][4] + " : " + ADB_INPUT + "tap " + x + " " + y);
                                            break;
                                        case "text" : //Para las acciones de texto...
                                            //./adb shell input text "Paris" Para el texto
                                            String textInput = parts[random.nextInt(parts.length - 0)];
                                            rt.exec(ADB_INPUT + "text \""+(textInput)+"\"");
                                            System.out.println(commands[numCommand][4] + " : " + ADB_INPUT + "text \""+(textInput)+"\"");
                                            break;
                                        case "swipe" : //Para la acción de swipe...
                                            //./adb shell input swipe 513 0 513 1200 Notificaciones
                                            String dataSwipe = parts[random.nextInt(parts.length - 0)];
                                            rt.exec(ADB_INPUT + "swipe " + dataSwipe);
                                            System.out.println(commands[numCommand][4] + " : " + ADB_INPUT + "swipe " + dataSwipe);
                                            break;
                                        case "Keyevent" : //Para las acciones de teclado...
                                            //./adb shell input keyevent 26
                                            int dataKey = random.nextInt(Integer.parseInt(parts[1]) - Integer.parseInt(parts[0]));
                                            rt.exec(ADB_INPUT + "keyevent " + dataKey);
                                            System.out.println(commands[numCommand][4] + " : " + ADB_INPUT + "keyevent " + dataKey);
                                            break;
                                        case "speed" : //Para el comando de cambiar la velocidad...
                                            String dataSpeed = parts[random.nextInt(parts.length - 0)];
                                            Main.changeVelocity(dataSpeed);
                                            System.out.println(commands[numCommand][4] + " : " + "network delay "+(dataSpeed));
                                            break;
                                        case "sensor" : //Para cambiar los valores de los sensores...
                                            String dataSensor = parts[random.nextInt(parts.length - 0)];
                                            float min = random.nextFloat() * (99 - 0) + 0;
                                            float medium = random.nextFloat() * (99 - 0) + 0;
                                            float max = random.nextFloat() * (99 - 0) + 0;
                                            Main.changeSensors(dataSensor, min, medium, max);
                                            System.out.println(commands[numCommand][4] + " : " + "sensor set " + (dataSensor) + " " + (min) + ":" + (medium) + ":" + (max));
                                            break;
                                    }
                                } else {
                                    int rotate = random.nextInt(2);
                                    if (rotate == 1) {
                                        Main.rotate();
                                    }
                                    System.out.println(commands[numCommand][4] + " : " + "rotate");
                                }
                                commands[numCommand][4] = Integer.toString(Integer.parseInt(commands[numCommand][4]) + 1);
                                Thread.sleep(200);
                            }
                            //Se guarda en el array la ejecución del comando...
                            ordenExecute[contExecuteCommand] = randomCommand;
                            contExecuteCommand = contExecuteCommand + 1;
                        } else {
                            if(contExecuteCommand == contCommands) {
                                break;
                            }
                        }
                    }
                }
                if(numberOfExecutions == contCommands) {
                    System.out.print("Termina la ejecución\n");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}