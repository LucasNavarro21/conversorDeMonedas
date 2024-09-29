import java.util.Map;

//Se usa Map<String, Double> conversion_rates ya que la parte de conversion rates devuelta en el json es un objeto
//en donde la clave es un String(codigo de moneda) y el valor es el valor de la moneda.
public record Moneda(String base_code, Map<String, Double> conversion_rates) {
}
