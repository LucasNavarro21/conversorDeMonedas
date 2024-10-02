import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsultaMoneda {

    private Moneda moneda;
    private ConsultaMoneda consulta;
    Scanner teclado = new Scanner(System.in);
    private List<String> historial = new ArrayList<>();


    public Moneda buscarMoneda(String codigoMoneda){

            URI direccion = URI.create("https://v6.exchangerate-api.com/v6/5d207f678235b4ebe271b56b/latest/"+codigoMoneda);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(direccion)
                    .build();

            try {
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                this.moneda = new Gson().fromJson(response.body(), Moneda.class);

                //Almaceno el json convertido en objeto a this.moneda por lo que ya lo puedo usarla
                //en los otros metodos con sus datos

                return this.moneda;

            } catch (Exception e) {
                throw new RuntimeException("No se encontro el codigo de moneda");
            }
        }

        public Double convertirValor(double valor1, double valor2){

            double resultado = valor1 * valor2;
            return resultado;
        }

        public double obtenerMoneda(String codigoMoneda){

            if (this.moneda != null) {
                return this.moneda.conversion_rates().get(codigoMoneda);
            } else {
                throw new RuntimeException("No se ha inicializado la moneda");
            }

        }

    public void procesoConversion(String monedaConvertir, String monedaConvertida){
        //No hace falta aclarar poner la clase ya que estamos en esta misma, en caso de que fuese otra clase la
        // que se llama se declara como atributo el objeto y se lo instancia
        buscarMoneda(monedaConvertir);
        System.out.println("Ingrese la cantidad");
        double cantidad = teclado.nextDouble();
        double conversion = convertirValor(cantidad, obtenerMoneda(monedaConvertida));
        String mensajeFinal = cantidad + " USD" + " son " +  conversion + " ARS";
        System.out.println(mensajeFinal);
        historial.add(mensajeFinal);
    }

    public void verHistorial(){
        for(String historia : historial){
            System.out.println(historia);
        }
//        System.out.println(historial);
    }

}
