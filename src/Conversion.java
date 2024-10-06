import java.sql.SQLOutput;

public record Conversion(String monedaConvertir, String monedaConvertida, double cantidad, double resultado, String fechaHora) {
    public Conversion {
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
        }
    }
}
