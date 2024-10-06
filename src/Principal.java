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
//        String monedaConvertir = "";
//        String monedaConvertida = "";
        ConsultaMoneda consulta = new ConsultaMoneda();

        while (opcion != 9){
            try {
                consulta.mostrarMensaje();
                opcion = teclado.nextInt();
                teclado.nextLine();

                Moneda moneda;

                switch (opcion) {
                    case 1 -> consulta.procesoConversion("USD", "ARS");
                    case 2 -> consulta.procesoConversion("ARS", "USD");
                    case 3 -> consulta.procesoConversion("USD", "BRL");
                    case 4 -> consulta.procesoConversion("BRL", "USD");
                    case 5 -> consulta.procesoConversion("USD", "COP");
                    case 6 -> consulta.procesoConversion("COP", "USD");
                    case 7 -> {
                        consulta.ingresarMonedasManual();
                    }
                    case 8 -> consulta.verHistorial();
                    case 9 -> System.out.println("Chau");
                    default -> System.out.println("Opción inválida");
                }
            } catch (InputMismatchException | NullPointerException e) {
                System.out.println("Ingrese un tipo de dato valido");
                teclado.nextLine();
//                teclado.close();

            }
        }


    }
}
