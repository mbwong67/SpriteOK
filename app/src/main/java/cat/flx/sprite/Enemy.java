package cat.flx.sprite;

/**
 * Created by DAM on 16/3/17.
 */

public class Enemy extends Character{

    private static int[][] states = {
            { 20,16,17,18,19 }
    };
    int[][] getStates() { return states; }

    Enemy(Game game) {
        super(game);
        padLeft = padTop = 0;
        colWidth = colHeight = 12;
        frame = (int)(Math.random() * 5);
    }

    void physics() {
    }
}
