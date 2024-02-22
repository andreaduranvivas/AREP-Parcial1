package edu.arep.servicios;

import edu.arep.backend.ChatAPI;

public class servicios {

    public static String subirResultado (String comando){
        String[] comandos = comando.split("%2");
        System.out.println("EL COMANDO ES "+ comando);

        String funcion = comandos[0];
        String respuesta = "";
        ChatAPI chat = new ChatAPI();

        if (funcion == "Class"){
            respuesta = String.valueOf(chat.Class(comandos[1]));
        }else if (funcion == "invoke"){
            respuesta = String.valueOf(chat.invoke(comandos[1], comandos[2]));
        } else if (funcion == "unaryInvoke") {
            respuesta = String.valueOf(chat.unaryInvoke(comandos[1], comandos[2], comandos[3], comandos[4]));
        } else if (funcion == "binaryInvoke") {
            respuesta = String.valueOf(chat.binaryInvoke(comandos[1], comandos[2], comandos[3], comandos[4], comandos[5], comandos[6]));
        }
        return respuesta;
    }
}
