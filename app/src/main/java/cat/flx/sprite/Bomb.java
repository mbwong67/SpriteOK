package cat.flx.sprite;

/**
 * Created by sergiodiaz on 16/5/17.
 */

class Bomb extends Character{
    private Game game;
    private int move;
    private static int[][] states = {
            {41}
    };
    int[][] getStates() { return states; }

    Bomb(Game game2) {
        super(game2);
        game = game2;
        padLeft = padTop = 0;
        colWidth = colHeight = 12;
        frame = (int)(Math.random() * 5);
        move= 2;
    }
    void stop(){
        move=0;
    }

    void physics() {

        this.y+=move;
        if(y>250){
            int random = (int)(Math.random() * 500);
            x=random;
            y=-20;
            //game.setPuntuacion(1);
        }
    }
}
