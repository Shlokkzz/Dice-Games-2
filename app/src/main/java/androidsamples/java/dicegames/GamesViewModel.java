package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} shared between {@link androidx.fragment.app.Fragment}s.
 */
public class GamesViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static final String TAG = "GamesViewModel";

    private final int WIN_VALUE = 6;
    private final int INCR_VALUE = 5;

    int balance;

    private int wager;

    GameType gameType;

    private Die mDie;

    public GamesViewModel(){
        balance = 0;
        wager = 0;

        gameType = GameType.NONE; // default
        mDie = new Die6();
    }

    void rollWalletDie(){
        mDie.roll();
        if(mDie.value() == WIN_VALUE){
            balance += INCR_VALUE;
        }
    }

    boolean isValidWager(){
        if(wager == 0) return false;
        switch (gameType){
            case TWO_ALIKE:
                return 2 * wager <= balance;
            case THREE_ALIKE:
                return 3 * wager <= balance;
            case FOUR_ALIKE:
                return 4 * wager <= balance;
            case NONE:
                return true;
        }
        return false;
    }

    public GameResult play() throws IllegalStateException {
        if(wager == 0){
            throw new IllegalStateException("Wager not set, can't play!");
        }
        if(gameType == GameType.NONE){
            throw new IllegalStateException("Game Type not set, can't play!");
        }

        int winCount=0;
        switch (gameType){
            case TWO_ALIKE:
                 winCount = 2;
                 break;
            case THREE_ALIKE:
                 winCount = 3;
                 break;
            case FOUR_ALIKE:
                 winCount = 4;
                 break;
        }
        int [] diceValues = diceValues();
        for(int i=1;i<=6;i++){
            int cnt=0;
            for(int j=0;j<4;j++){
                if(diceValues[j] == i)cnt++;
            }
            if(cnt == winCount){
                return GameResult.WIN;
            }
        }
        return GameResult.LOSS;
    }

    public int[] diceValues(){
        int [] diceValues = new int[4];
        Die tempDie = new Die6();
        for(int i=0;i<4;i++){
            tempDie.roll();
            diceValues[i] = tempDie.value();
        }
        return diceValues;
    }
    // setters
    public void setWager(int wager){
        this.wager = wager;
    }
    public void setBalance(int balance){
        this.balance = balance;
    }
    public void setGameType(GameType gameType){
        this.gameType = gameType;
    }

}