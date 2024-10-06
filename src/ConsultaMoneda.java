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
    Scanner teclado = new Scanner(System.in);
    private List<Conversion> historial = new ArrayList<>();


    public Moneda buscarMoneda(String codigoMoneda){

        if (this.moneda != null && this.moneda.base_code().equals(codigoMoneda)) {
            return this.moneda; // Si la moneda ya está cargada, no vuelve a hacer la solicitud
        }



        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/5d207f678235b4ebe271b56b/latest/"+codigoMoneda);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(direccion)
                    .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                this.moneda = new Gson().fromJson(response.body(), Moneda.class);
                return this.moneda;
            } else {
                throw new RuntimeException("Error en la solicitud, código de respuesta: " + response.statusCode());
            }
        } catch (java.net.ConnectException e) {
            throw new RuntimeException("Error de conexión: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }

        public void mostrarMensaje(){
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
            System.out.println(mensaje);
        }

    public void ingresarMonedasManual() {
        String monedaConvertir = "";
        String monedaConvertida = "";
        boolean datosValidos = false;

        while (!datosValidos) {
            try {
                System.out.println("Ingrese la moneda a convertir");
                monedaConvertir = teclado.nextLine().toUpperCase(); // Convierte a mayúsculas por consistencia

                // Buscar la moneda ingresada para validar si existe en la API
                Moneda monedaDatosConvertir = buscarMoneda(monedaConvertir); // Llama a buscarMoneda aquí

                System.out.println("Ingrese la moneda a recibir");
                monedaConvertida = teclado.nextLine().toUpperCase();

                if ((monedaConvertida.isEmpty() || monedaConvertida.length() != 3) || (monedaConvertir.isEmpty() || monedaConvertir.length() != 3)) {
                    throw new IllegalArgumentException("El código de la moneda debe ser de 3 caracteres.");
                }

                // Validar si la monedaConvertida también es válida
                if (!monedaDatosConvertir.conversion_rates().containsKey(monedaConvertir) || !monedaDatosConvertir.conversion_rates().containsKey(monedaConvertida)) {
                    throw new IllegalArgumentException("El codigo de moneda no ha sido encontrado.");
                }

                // Si ambos inputs son válidos, se procede con la conversión
                procesoConversion(monedaConvertir, monedaConvertida);
                datosValidos = true; // Sale del bucle ya que los datos son correctos

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Por favor, ingrese los datos correctamente.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                System.out.println("Intente de nuevo.");
            }
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

            mensajeFinal = this.conversion.cantidad() + " " + this.conversion.monedaConvertir() + " son " + this.conversion.resultado() + " " + this.conversion.monedaConvertida();

//            System.out.println("Ingrese una cantidad valida");


        System.out.println(mensajeFinal);

        historial.add(this.conversion);
    }

    public void verHistorial(){

        if(historial.isEmpty()){
            System.out.println("No hay carga de archivos todavia");
        }else{
            for(Conversion historia : historial){
                String mensajeHistorial = "Se convirtieron " + historia.cantidad() + " " + historia.monedaConvertir() + " a " + historia.resultado() + " " + historia.monedaConvertida() + " a las " + historia.fechaHora();
                System.out.println(mensajeHistorial);
            }
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
