import javax.swing.*; //Proporciona clases para la API swing de Java, como JButton, JTextField, JTextArea, JRadioButton, JCheckbox, JMenu, JColorChooser, etc.
import java.awt.event.*; //Proporciona interfaces y clases para tratar con diferentes tipos de eventos disparados por componentes AWT.
import java.beans.PropertyChangeEvent; //Se envía como argumento a los métodos PropertyChangeListener y VetoableChangeListener.
import java.beans.PropertyChangeListener;//La interfaz se implementa mediante objetos que se registran a sí mismos para recibir notificaciones de estos cambios de valor de propiedad.
import java.io.File;//Es una representación abstracta de archivos y nombres de rutas de directorios.
import java.io.FileNotFoundException;//Señala que ha fallado un intento de abrir el archivo indicado por un nombre de ruta especificado.
import java.util.Scanner;//Paquete utilizado para obtener la entrada de los tipos de datos como int, double, etc. y cadenas.

public class Gameplay {
    private JLabel title; //Definición de etiquetas, campos y botones
    private JLabel timeClock;
    private JTextField answerField;
    private JLabel questionField;
    private JLabel highScore;
    private JLabel questionsCorrect;
    private JButton startButton;
    private JLabel actionMessage;
    private JButton answerButton;
    private JPanel background;
    private JLabel actualAnswer;

    private QuestionGenerator generator = new QuestionGenerator();
    private String currentQ;
    private int score = 0;
    private Clock clock = new Clock();
    private IntChecker intChecker = new IntChecker();
    private int answer;
    private HighScore highScoreChecker = new HighScore();
    private int timesRestart = 0;


    public Gameplay() {

        //Cuanto el botónstart/restart es presionado.
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread clockThread = new Thread() {
                    public void run() {
                        clock.init(60);
                        while (clock.timeRemaining() >= 0) {
                            timeClock.setText("Time Remaining: " + clock.timeRemaining());
                        }
                        if (clock.timeRemaining() < 1) {
                            //Juego reseteado.
                            timeClock.setText("Time Remaining: 0");
                            answerField.setText("Answer");
                            questionField.setText("Question");
                            actualAnswer.setText("");

                            //Actualiza y muestra el puntaje máximo.
                            highScoreChecker.updateHighScore(score);
                            highScore.setText("High Score: " + highScoreChecker.getHighScore());
                        }
                    }
                };

                clockThread.start();

                //Restablecer o resetear el juego.
                answerField.setText("");
                score = 0;
                questionsCorrect.setText("Score " + score);

                //Generar conjunto de preguntas.
                currentQ = generator.generateQuestion(true, false);
                questionField.setText(currentQ);

                startButton.setText("Restart");
            }
        });

        //Cuando enviar es presionado
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Se garantiza que el juego sea inicializado.
                if (startButton.getText().equals("Start")) answerField.setText("Press 'start' to begin!");

                //Mientras se desarrolla el juego, primeros 15 segundos.
                else if (clock.timeRemaining() >= 45){

                    //Se comprueba que la respuesta es un número entero.
                    String answerText = answerField.getText();
                    if (!intChecker.isInt(answerText)) answer = -1000;

                    else if (answerText.isEmpty()) answer = -1000;

                    else answer = Integer.parseInt(answerText);

                    //Se verifica si la respuesta es correcta y actualiza la puntuación según el resultado.
                    if (generator.answerIsCorrect(currentQ, answer)) {
                        score += 1;
                        actualAnswer.setText("Actual Answer:");
                    }

                    //Si se responde mal, se muestra la respuesta correcta.
                    else actualAnswer.setText("Actual Answer: " + generator.correctAnswer(currentQ));

                    //Preguntas en modo fácil
                    questionsCorrect.setText("Score: " + score);
                    currentQ = generator.generateQuestion(true, false);
                    questionField.setText(currentQ);
                    answerField.setText("");
                }

                //Mientras se desarrolla el juego, 30 segundos.
                else if (clock.timeRemaining() >= 15 && clock.timeRemaining() < 45){

                    //Se comprueba que la respuesta es un número entero.
                    String answerText = answerField.getText();
                    if (!intChecker.isInt(answerText)) answer = -1000;

                    else if (answerText.isEmpty()) answer = -1000;

                    else answer = Integer.parseInt(answerText);

                    //Se verifica si la respuesta es correcta y actualiza la puntuación según el resultado.
                    if (generator.answerIsCorrect(currentQ, answer)) {
                        score += 1;
                        actualAnswer.setText("Actual Answer:");
                    }

                    //Si se responde mal, se muestra la respuesta correcta.
                    else actualAnswer.setText("Actual Answer: " + generator.correctAnswer(currentQ));

                    //Preguntas en dificultad media.
                    questionsCorrect.setText("Score: " + score);
                    currentQ = generator.generateQuestion(false, true);
                    questionField.setText(currentQ);
                    answerField.setText("");
                }
                //Mientras se desarrolla el juego, últimos 15 segundos.
                else if (clock.timeRemaining() < 15 && clock.timeRemaining() > 0) {

                    //Se comprueba que la respuesta es un número entero.
                    String answerText = answerField.getText();
                    if (!intChecker.isInt(answerText)) answer = -1000;

                    else if (answerText.isEmpty()) answer = -1000;

                    else answer = Integer.parseInt(answerText);

                    //Se verifica si la respuesta es correcta y actualiza la puntuación según el resultado.
                    if (generator.answerIsCorrect(currentQ, answer)) {
                        score += 1;
                        actualAnswer.setText("Actual Answer:");
                    }

                    //Si se responde mal, se muestra la respuesta correcta.
                    else actualAnswer.setText("Actual Answer: " + generator.correctAnswer(currentQ));

                    //Preguntas en modo difícil.
                    questionsCorrect.setText("Score: " + score);
                    currentQ = generator.generateQuestion(false, false);
                    questionField.setText(currentQ);
                    answerField.setText("");
                }
                else answerField.setText("Press 'start' to begin!");
            }
        });

        //Agregando la lógica del botón de envío para ingresar la clave.
        answerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {

                    //Garantiza que el juego sea inicializado
                    if (startButton.getText().equals("Start")) answerField.setText("Press 'start' to begin!");

                        //Mientras se desarrolla el juego, primeros 15 segundos.
                    else if (clock.timeRemaining() >= 45){

                        //Se comprueba que la respuesta es un número entero.
                        String answerText = answerField.getText();
                        if (!intChecker.isInt(answerText)) answer = -1000;

                        else if (answerText.isEmpty()) answer = -1000;

                        else answer = Integer.parseInt(answerText);

                        //Se verifica si la respuesta es correcta y actualiza la puntuación según el resultado.
                        if (generator.answerIsCorrect(currentQ, answer)) {
                            score += 1;
                            actualAnswer.setText("Actual Answer:");
                        }

                        //Si se responde mal, se muestra la respuesta correcta.
                        else actualAnswer.setText("Actual Answer: " + generator.correctAnswer(currentQ));

                        //Preguntas en modo fácil
                        questionsCorrect.setText("Score: " + score);
                        currentQ = generator.generateQuestion(true, false);
                        questionField.setText(currentQ);
                        answerField.setText("");
                    }

                    //Mientras se desarrolla el juego, 30 segundos.
                    else if (clock.timeRemaining() >= 15 && clock.timeRemaining() < 45){

                        //Se comprueba que la respuesta es un número entero.
                        String answerText = answerField.getText();
                        if (!intChecker.isInt(answerText)) answer = -1000;

                        else if (answerText.isEmpty()) answer = -1000;

                        else answer = Integer.parseInt(answerText);

                        //Se verifica si la respuesta es correcta y actualiza la puntuación según el resultado.
                        if (generator.answerIsCorrect(currentQ, answer)) {
                            score += 1;
                            actualAnswer.setText("Actual Answer:");
                        }

                        //Si se responde mal, se muestra la respuesta correcta.
                        else actualAnswer.setText("Actual Answer: " + generator.correctAnswer(currentQ));

                        //Preguntas en dificultad media.
                        questionsCorrect.setText("Score: " + score);
                        currentQ = generator.generateQuestion(false, true);
                        questionField.setText(currentQ);
                        answerField.setText("");
                    }
                    //Mientras se desarrolla el juego, últimos 15 segundos.
                    else if (clock.timeRemaining() < 15 && clock.timeRemaining() > 0) {

                        //Se comprueba que la respuesta es un número entero.
                        String answerText = answerField.getText();
                        if (!intChecker.isInt(answerText)) answer = -1000;

                        else if (answerText.isEmpty()) answer = -1000;

                        else answer = Integer.parseInt(answerText);

                        //Se verifica si la respuesta es correcta y actualiza la puntuación según el resultado.
                        if (generator.answerIsCorrect(currentQ, answer)) {
                            score += 1;
                            actualAnswer.setText("Actual Answer:");
                        }

                        //Si se responde mal, se muestra la respuesta correcta.
                        else actualAnswer.setText("Actual Answer: " + generator.correctAnswer(currentQ));

                        //Preguntas en modo difícil.
                        questionsCorrect.setText("Score: " + score);
                        currentQ = generator.generateQuestion(false, false);
                        questionField.setText(currentQ);
                        answerField.setText("");
                    }
                    else answerField.setText("Press 'start' to begin!");
                }
            }
        });
    }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Math Solver Game");
            Gameplay gameplay = new Gameplay();
            frame.setContentPane(gameplay.background);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 400);
            frame.setVisible(true);
        }
    }
