import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        String mensaje = """
                1) Dolar ==> Peso Argentino
                2) Peso Argentino ==> Dolar
                3) Dolar ==> Real Brasileño
                4) Real Brasileño ==> Dolar
                5) Dolar ==> Peso Colombiano
                6) Peso Colombiano ==> Dolar
                7) Salir
                Elija una opcion valida""";

        System.out.println(mensaje);
        int opcion = teclado.nextInt();

        ConsultaMoneda consulta = new ConsultaMoneda();
        Moneda moneda;

        if (opcion == 1){
            consulta.procesoConversion("USD", "ARS");
        } else if (opcion == 2) {
            moneda = consulta.buscarMoneda("ARS");
            System.out.println("Ingrese la cantidad");
            double cantidad = teclado.nextDouble();
            double conversion = consulta.convertirValor(cantidad, consulta.obtenerMoneda("USD"));
            System.out.println(cantidad + " ARS" + " son " +  conversion + " USD" );


        }else if (opcion == 3) {
            moneda = consulta.buscarMoneda("USD");

            System.out.println("Ingrese la cantidad en dólares:");
            double cantidad = teclado.nextDouble();

            // Conversión de USD a BRL (Real Brasileño)
            double conversion = consulta.convertirValor(cantidad, consulta.obtenerMoneda("BRL"));
            System.out.println(cantidad + " USD son " + conversion + " BRL");

        } else if (opcion == 4) {
            moneda = consulta.buscarMoneda("BRL");

            System.out.println("Ingrese la cantidad en reales brasileños:");
            double cantidad = teclado.nextDouble();

            // Conversión de BRL a USD
            double conversion = consulta.convertirValor(cantidad, consulta.obtenerMoneda("USD"));
            System.out.println(cantidad + " BRL son " + conversion + " USD");

        } else if (opcion == 5) {
            moneda = consulta.buscarMoneda("USD");

            System.out.println("Ingrese la cantidad en dólares:");
            double cantidad = teclado.nextDouble();

            // Conversión de USD a COP (Peso Colombiano)
            double conversion = consulta.convertirValor(cantidad, consulta.obtenerMoneda("COP"));
            System.out.println(cantidad + " USD son " + conversion + " COP");

        } else if (opcion == 6) {
            moneda = consulta.buscarMoneda("COP");

            System.out.println("Ingrese la cantidad en pesos colombianos:");
            double cantidad = teclado.nextDouble();

            // Conversión de COP a USD
            double conversion = consulta.convertirValor(cantidad, consulta.obtenerMoneda("USD"));
            System.out.println(cantidad + " COP son " + conversion + " USD");

        } else if (opcion == 7) {
            System.out.println("Chau");

        }else{
            System.out.println("Opcion invalida");
        }

    }
}
