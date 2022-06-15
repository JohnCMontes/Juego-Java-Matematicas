import java.util.Random;

public class QuestionGenerator {
    private Random random = new Random();
    private int firstNum;
    private int secondNum;

    //Genera signos, asignando este al campo de signos.
    //Devuelve el número de signo correspondiente
    //0: Suma, 1: Multiplicación, 2: resta, 3: División.
    private String generateSign(boolean hard) {
        String sign = "";
        int signGenerator;
        if(!hard) {
            signGenerator = random.nextInt(3);
            if(signGenerator == 0) sign = "+";
            else if (signGenerator == 1) sign = "*";
            else if (signGenerator == 2) sign = "-";
        }

        //La división es solo una opción para preguntas difíciles.
        else {
            signGenerator = random.nextInt(4);
            if (signGenerator == 0) sign = "+";
            else if (signGenerator == 1) sign = "*";
            else if (signGenerator == 2) sign = "-";
            else if (signGenerator == 3) sign = "/";
        }
        return sign;
    }

    //Al implementarse, se garantiza de que solo 1 valor booleano sea verdadero. Deje ambos falsos por difícil.
    public String generateQuestion(boolean easy, boolean medium) {

        //Generador de preguntas fácil, números menores a 10. Sin división.
        if (easy) {
            String sign = generateSign(false);
            if (sign.equals("-")) {
                firstNum = random.nextInt(10);
                secondNum = random.nextInt(10);
                while(secondNum > firstNum) {
                    secondNum = random.nextInt(10);
                }
            }
            else {
                firstNum = random.nextInt(10);
                secondNum = random.nextInt(10);
            }

            return firstNum + sign + secondNum;
        }

        //Generador de preguntas con dificultad media, un número menor a 13, el otro menor a 10. Sin división.
        else if (medium) {
            String sign = generateSign(false);
             if (sign.equals("-")) {
                firstNum = random.nextInt(10);
                secondNum = random.nextInt(10);
                while (secondNum > firstNum) {
                    secondNum = random.nextInt(10);
                }
            }
             else {
                firstNum = random.nextInt(13);
                secondNum = random.nextInt(10);
            }
            return firstNum + sign + secondNum;
        }

        //Generador de preguntas difíciles, ambos números menores a 13.
        else {
            String sign = generateSign(true);
            if(sign.equals("/")) {
                secondNum = random.nextInt(13) + 1;
                firstNum = secondNum * random.nextInt(7);
            }
            else if (sign.equals("-")) {
                firstNum = random.nextInt(10);
                secondNum = random.nextInt(10);
                while (secondNum > firstNum) {
                    secondNum = random.nextInt(10);
                }
            }
            else {
                firstNum = random.nextInt(13);
                secondNum = random.nextInt(13);
            }
            return firstNum + sign + secondNum;
        }
    }

    //Comprobar la respuesta correcta. La cadena debe presentarse en la misma forma que las salidas del generador. Sin espacios en la cadena.
    public int correctAnswer (String question) {
        char[] chars = question.toCharArray();

        //Comprueba si la pregunta tiene dos números de dos dígitos. En caso afirmativo, asigne estos números a firstNum y secondNum.
        if (chars.length == 5) {
            String char1 = String.valueOf(chars[0]);
            String char2 = String.valueOf(chars[1]);
            firstNum = Integer.parseInt(char1 + char2);
            String char3 = String.valueOf(chars[3]);
            String char4 = String.valueOf(chars[4]);
            secondNum = Integer.parseInt(char3 + char4);

            //Verifique el signo del operador y evalúa, devuelve verdadero si la respuesta anterior es correcta.
            if(chars[2] == '*') return firstNum * secondNum;
            else if(chars[2] == '+') return firstNum + secondNum;
            else if(chars[2] == '-') return firstNum - secondNum;
            else return firstNum / secondNum;
        }

        //Comprueba si la pregunta tiene dos números de un solo dígito. Asigne estos a firstNum y secondNum de ser así.
        else if (chars.length == 3) {
            firstNum = Integer.parseInt(String.valueOf(chars[0]));
            secondNum = Integer.parseInt(String.valueOf(chars[2]));

            //Comprueba el signo y evalúa, regresando verdadero o falso según la respuesta aprobada.
            if(chars[1] == '*') return firstNum * secondNum;
            else if(chars[1] == '+') return firstNum + secondNum;
            else if(chars[1] == '-') return firstNum - secondNum;
            else return firstNum / secondNum;
        }

        //La pregunta tiene un número de un solo dígito y otro de dos dígitos.
        else {

            //El primer número es de un solo dígito.
            if(chars[1] == '*' || chars[1] == '+' || chars[1] == '-' || chars[1] == '/') {
                    firstNum = Integer.parseInt(String.valueOf(chars[0]));
                    String char1 = String.valueOf(chars[2]);
                    String char2 = String.valueOf(chars[3]);
                    secondNum = Integer.parseInt(char1 + char2);
            }

            //El primer número es de dos dígitos.
            else {
                String char1 = String.valueOf(chars[0]);
                String char2 = String.valueOf(chars[1]);
                firstNum = Integer.parseInt(char1 + char2);
                secondNum = Integer.parseInt(String.valueOf(chars[3]));
            }

            //Comprobar signo y evaluar, devolver verdadero o falso basado en la respuesta aprobada.
            if(chars[1] == '*' || chars[2] == '*') return firstNum * secondNum;
            else if(chars[1] == '+' || chars[2] == '+') return firstNum + secondNum;
            else if(chars[1] == '-' || chars[2] == '-') return firstNum - secondNum;
            else return firstNum / secondNum;
        }
    }

    //Devuelve si la respuesta (tomada como entrada) es igual a la respuesta correcta, que se evalúa mediante el método correctAnswer.
    public boolean answerIsCorrect(String question, int answer) {
        int correctAnswer = correctAnswer(question);
        return correctAnswer == answer;
    }
}
