package edu.arep.backend;

import edu.arep.servicios.servicios;

import java.net.*;
import java.io.*;

public class HttpServer {

    private static boolean running = false;
    private static servicios service = new servicios();

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        running = true;
        while (running) {

            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            String outputLine = null;

            boolean firstLine = true;
            String uriStr = "";
            String method = "";

            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    String[] parts = inputLine.split(" ");
                    method = parts[0];
                    uriStr = parts[1];
                    firstLine = false;
                }

                System.out.println("Recibí: " + inputLine);

                URI uri= new URI(uriStr);

                URI requestUri = new URI(uriStr);

                try {
                    if (requestUri.getPath().startsWith("/cliente")){
                        outputLine = cliente();
                    }else if(requestUri.getPath().startsWith("/consulta")){
                        outputLine = comando(uriStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!in.ready()) {
                    break;
                }
            }


            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
            serverSocket.close();

    }

    public static String cliente(){
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Reflective</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Reflective ChatGPT</h1>\n" +
                "        <form action=\"/consulta\">\n" +
                "            <label for=\"name\">Comando:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n"+
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/consulta?comando=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "    </body>\n" +
                "</html>";

        return outputLine;
    }

    public static String comando(String requestUri){
        String resultado = service.subirResultado(requestUri.substring(18));
        //resultado = "aja";

        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Resultado</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Resultado</h1>\n" + resultado
                + "</body>\n"
                + "</html>\n";

        return outputLine;


    }
}
