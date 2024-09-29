import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el codigo de la moneda");
        String codigoMoneda = teclado.nextLine();

        System.out.println("Ingrese la cantidad");
        double cantidad = teclado.nextDouble();

        ConsultaMoneda consulta = new ConsultaMoneda();

        Moneda moneda = consulta.buscarMoneda(codigoMoneda);

        double conversion = consulta.convertirValor(cantidad, consulta.obtenerMoneda("ARS"));

        System.out.println(cantidad + " " + codigoMoneda + " son " +  conversion + " ARS" );

    }
}
