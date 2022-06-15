public class HighScore  {

    private int highScore = 0;

    //A ser llamado si la puntuación cambia por una nueva puntuación (más alta)
    public void updateHighScore(int score) {
        if (score > highScore) highScore = score;
    }
    public int getHighScore() {
        return highScore;
    }
}
