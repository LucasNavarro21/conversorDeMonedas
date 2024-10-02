import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        int opcion = 1;
        ConsultaMoneda consulta = new ConsultaMoneda();

        while (opcion != 8){
            String mensaje = """
                1) Dolar ==> Peso Argentino
                2) Peso Argentino ==> Dolar
                3) Dolar ==> Real Brasileño
                4) Real Brasileño ==> Dolar
                5) Dolar ==> Peso Colombiano
                6) Peso Colombiano ==> Dolar
                7) Ver historial de conversiones
                8) Salir
                Elija una opcion valida""";

            try {
                System.out.println(mensaje);
                opcion = teclado.nextInt();
                Moneda moneda;

                if (opcion == 1){
                    consulta.procesoConversion("USD", "ARS");
                } else if (opcion == 2) {
                    consulta.procesoConversion("ARS", "USD");
                }else if (opcion == 3) {
                    consulta.procesoConversion("USD", "BRL");
                } else if (opcion == 4) {
                    consulta.procesoConversion("BRL", "USD");
                } else if (opcion == 5) {
                    consulta.procesoConversion("USD", "COP");
                } else if (opcion == 6) {
                    consulta.procesoConversion("COP","USD");
                } else if (opcion == 7) {
                    consulta.verHistorial();
                } else if(opcion == 8){
                    System.out.println("Chau");
                }
                else{
                    System.out.println("Opcion invalida");
                }

            } catch (InputMismatchException e) {
                System.out.println("Ingrese un tipo de dato valido");
            }
        }


    }
}
