/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.conversordemonedas_svb;

/**
 *
 * @author balas
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorDeMonedas_SVB {
    private static final String API_KEY = "TU_API_KEY";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("*****************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
            System.out.println("1) Dalar => Peso argentino");
            System.out.println("2) Peso argentino => Dalar");
            System.out.println("3) Dalar => Real brasile�o");
            System.out.println("4) Real brasile�o => Dalar");
            System.out.println("5) Dalar => Peso colombiano");
            System.out.println("6) Peso colombiano => Dalar");
            System.out.println("7) Salir");
            System.out.println("*****************************************");
            System.out.print("Elija una opcion valida: ");
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    convertirMoneda("USD", "ARS");
                    break;
                case 2:
                    convertirMoneda("ARS", "USD");
                    break;
                case 3:
                    convertirMoneda("USD", "BRL");
                    break;
                case 4:
                    convertirMoneda("BRL", "USD");
                    break;
                case 5:
                    convertirMoneda("USD", "COP");
                    break;
                case 6:
                    convertirMoneda("COP", "USD");
                    break;
                case 7:
                    System.out.println("Gracias por usar el conversor de monedas. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        } while (opcion != 7);
        
        scanner.close();
    }
    
    private static void convertirMoneda(String from, String to) {
        try {
            URL url = new URL(API_URL + from);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject json = new JSONObject(content.toString());
            double tasaDeCambio = json.getJSONObject("conversion_rates").getDouble(to);
            System.out.println("Tasa de cambio de " + from + " a " + to + ": " + tasaDeCambio);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la cantidad en " + from + ": ");
            double cantidad = scanner.nextDouble();
            double resultado = cantidad * tasaDeCambio;
            System.out.println(cantidad + " " + from + " son " + resultado + " " + to);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener la tasa de cambio. Por favor, intente de nuevo.");
        }
    }
    
}
