import java.sql.SQLOutput;

public record Conversion(String monedaConvertir, String monedaConvertida, double cantidad, double resultado, String fechaHora) {
    public boolean isMayor0(){
        if(cantidad <= 0){
            System.out.println("Ingrese una cantidad valida");
            return false;
        }else{
            return true;
        }
    }
}
