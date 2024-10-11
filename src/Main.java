import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Conversion consultaHttp = new Conversion();
        boolean salida = false;
        while (!salida) {
            int eleccion = 0;
            String MonedaBase = "";
            String MonedaFinal = "";
            double Monto = 0;

            System.out.println("*".repeat(59));
            System.out.println("*".repeat(59));
            System.out.println("""
                    -----Seleccione el formato de conversión de la moneda:-----
                    
                    1: Dólar [USD] >>> Peso Argentino  [ARS].
                    2: Peso Argentino  [ARS] >>> Dólar [USD].
                    3: Dólar [USD] >>> Real Brasileño  [BRL].
                    4: Real Brasileño  [BRL] >>> Dólar [USD].
                    5: Dólar [USD] >>> Soles [PEN].
                    6: Soles [PEN] >>> Dólar [USD].
                    7: Peso Colombiano [COP] >>> Dólar [USD].
                    8: Dólar [USD] >>> Peso Colombiano [COP].
                    9: Euro  [EUR] >>> Dólar [USD].
                    10:Dólar [USD] >>> Euro  [EUR].
                    0: Salir.
                    """);
            System.out.println("*".repeat(59));
            System.out.println("*".repeat(59));
            System.out.println();
            System.out.println("Elige una opción:");
            System.out.print("> ");
            eleccion = teclado.nextInt();
            teclado.nextLine();
            switch (eleccion) {
                case 1:
                    MonedaBase = "USD";
                    MonedaFinal = "ARS";
                    break;
                case 2:
                    MonedaBase = "ARS";
                    MonedaFinal = "USD";
                    break;
                case 3:
                    MonedaBase = "USD";
                    MonedaFinal = "BRL";
                    break;
                case 4:
                    MonedaBase = "BRL";
                    MonedaFinal = "USD";
                    break;
                case 5:
                    MonedaBase = "USD";
                    MonedaFinal = "PEN";
                    break;
                case 6:
                    MonedaBase = "PEN";
                    MonedaFinal = "USD";
                    break;
                case 7:
                    MonedaBase = "COP";
                    MonedaFinal = "USD";
                    break;
                case 8:
                    MonedaBase = "USD";
                    MonedaFinal = "COP";
                    break;
                case 9:
                    MonedaBase = "EUR";
                    MonedaFinal = "USD";
                    break;
                case 10:
                    MonedaBase = "USD";
                    MonedaFinal = "EUR";
                    break;
                case 0:
                    salida = true;
                    break;
                default:
                    System.out.println("Elección no válida...");
                    teclado.nextLine();
                    System.out.println("\f");
                    break;
            }
            while (Monto == 0) {
                System.out.println("*".repeat(10));
                System.out.println("Provea una cantidad a convertir de " + MonedaBase + " a " + MonedaFinal + ":");
                System.out.print("> ");
                Monto = teclado.nextDouble();
                teclado.nextLine();
                if (Monto == 0) {
                    System.out.println("el monto debe ser al menos mayor a 0...");
                    teclado.nextLine();
                    System.out.println("\f");
                } else {
                    try {
                        Modelo modeloMoneda = consultaHttp.convetirMoneda(MonedaBase, MonedaFinal, Monto);
                        System.out.println("Valor en el mercado: " + modeloMoneda.conversion_rate());
                        System.out.println("El valor de " + Monto + "en " + MonedaBase + "es de: " + modeloMoneda.conversion_result() + " " + MonedaFinal);
                        teclado.nextLine();
                        generador paraJSON = new generador();
                        paraJSON.guardarJson(modeloMoneda, Monto);
                        String ele = "";
                        System.out.println("¿Desea continuar convirtiendo? Y/N");
                        System.out.print("> ");
                        while (ele.isEmpty()) {
                            ele = teclado.nextLine();
                            if (ele.contains("Y")) {
                                salida = false;
                            } else if (ele.contains("N")) {
                                salida = true;
                            } else {
                                ele = "";
                                System.out.println("Elección no valida...");
                                teclado.nextLine();
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Número no encontrado: " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}