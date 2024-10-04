import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsultaMoneda {

    private Moneda moneda;
    private Conversion conversion;
    private ConsultaMoneda consulta;
    Scanner teclado = new Scanner(System.in);
    private List<Conversion> historial = new ArrayList<>();


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
        double conversionResultado = convertirValor(cantidad, obtenerMoneda(monedaConvertida));

        String fechaHora = obtenerHoraDia();
        this.conversion = new Conversion(monedaConvertir, monedaConvertida, cantidad, conversionResultado, fechaHora);
        String mensajeFinal = "";
        if(cantidad > 0){
            mensajeFinal = this.conversion.cantidad() + " " + this.conversion.monedaConvertir() + " son " + this.conversion.resultado() + " " + this.conversion.monedaConvertida();
        }else{
            System.out.println("Ingrese una cantidad valida");
        }

        System.out.println(mensajeFinal);

        historial.add(this.conversion);
    }

    public void verHistorial(){


        for(Conversion historia : historial){
            String mensajeHistorial = "Se convirtieron " + historia.cantidad() + " " + historia.monedaConvertir() + " a " + historia.resultado() + " " + historia.monedaConvertida() + " a las " + historia.fechaHora();
            System.out.println(mensajeHistorial);
        }

    }

    public String obtenerHoraDia(){
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Formatear la fecha y hora (opcional)
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formato);
        return fechaHoraFormateada;
    }

}
