import static java.lang.System.currentTimeMillis; //El método devuelve la hora actual en milisegundos. 
                                                  //La unidad de tiempo del valor devuelto es un milisegundo.

public class Clock {    //Clase pública reloj
    private long secondsPassed;   //Declaración de variables
    private long realTime = 0;
    private int startTimeSec;

    //Reloj en cuenta regresiva, tiempo de inicioe inicie la cuenta regresiva.
    //Después que se haya tomado el tiempo, se estará reduciendo continuamente
    //y se cuenta el número de segundos transcurridos
    public void init(int startTimeSec) {
        this.startTimeSec = startTimeSec;
        realTime = System.currentTimeMillis();
    }

    //Retorna el tiempo restante
    public long timeRemaining() {
        if(realTime != 0) {
            secondsPassed = (System.currentTimeMillis() - realTime) / 1000;
        }
        return startTimeSec - secondsPassed;
    }
}
