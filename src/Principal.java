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
        String monedaConvertir = "";
        String monedaConvertida = "";
        ConsultaMoneda consulta = new ConsultaMoneda();

        while (opcion != 9){
            String mensaje = """
                1) Dolar ==> Peso Argentino
                2) Peso Argentino ==> Dolar
                3) Dolar ==> Real Brasileño
                4) Real Brasileño ==> Dolar
                5) Dolar ==> Peso Colombiano
                6) Peso Colombiano ==> Dolar
                7) Ingreso a eleccion
                8) Ver historial de conversiones
                9) Salir
                Elija una opcion valida""";

            try {
                System.out.println(mensaje);
                opcion = teclado.nextInt();
                teclado.nextLine();

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
                    System.out.println("Ingrese la moneda a convertir");
                    monedaConvertir = teclado.nextLine();
                    System.out.println("Ingrese la moneda a recibir");
                    monedaConvertida = teclado.nextLine();
                    consulta.procesoConversion(monedaConvertir, monedaConvertida);
                }else if(opcion == 8){
                    consulta.verHistorial();
                }else if(opcion == 9){
                    System.out.println("Chau");
                }
                else{
                    System.out.println("Opcion invalida");
                }

            } catch (InputMismatchException | NullPointerException e) {
                System.out.println("Ingrese un tipo de dato valido");
                teclado.nextLine();

            }
        }


    }
}
