import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class IntChecker {
    public boolean isInt(String answer) {

        //Toma la respuesta como una cadera y la convierte en una matriz de caracteres.
        char[] chars = answer.toCharArray();
        List<Boolean> charIsInt = new ArrayList<>();
        for(char c : chars) {

            //Si cada número es un int, agregue verdadero a una matriz booleana.
            if(c == '1' || c == '2' || c == '3' || c == '4'|| c == '5' ||
            c == '6' || c == '7' || c == '8' || c == '9' || c == '0') {
                charIsInt.add(true);
            }

            //Si la respuesta está vacía (o no se responde), agregue falso.
            else if(answer.isBlank() || answer.isEmpty()) {
                charIsInt.add(false);
            }
            else {
                charIsInt.add(false);
            }
        }
        //Si contiene falso (cualquier cosa menos int) devuelve falso.
       return(!charIsInt.contains(false));
    }
}
