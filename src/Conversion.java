import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.GsonBuilder;

public class Conversion {
    public Modelo convetirMoneda(String MonedaLocal, String MonedaFinal, double Monto) {
        URI enlace = URI.create("https://v6.exchangerate-api.com/v6/13dbbf5aa2761de19439dd08/pair/" +
                MonedaLocal +
                "/" + MonedaFinal +
                "/" + Monto);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder().uri(enlace).build();

        try {
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            return new GsonBuilder().setPrettyPrinting().create().fromJson(response.body(), Modelo.class);
        } catch (Exception e) {
            throw new RuntimeException("No se encontró la moneda");
        }
    }
}