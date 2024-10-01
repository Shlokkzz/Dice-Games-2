package androidsamples.java.dicegames;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A {@link Fragment} that implements the Game Play screen.
 */
public class GamesFragment extends Fragment {

    private static final String TAG = "GamesFragment";

    private GamesViewModel vm;

    private EditText addWager;
    private RadioGroup gameType;

    private TextView die1;
    private TextView die2;
    private TextView die3;
    private TextView die4;

    private TextView txt_coins;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);

        addWager = view.findViewById(R.id.addWager);
        gameType = view.findViewById(R.id.radioGroup);
        die1 = view.findViewById(R.id.die1);
        die2 = view.findViewById(R.id.die2);
        die3 = view.findViewById(R.id.die3);
        die4 = view.findViewById(R.id.die4);

        txt_coins = view.findViewById(R.id.coin_counter);

        view.findViewById(R.id.goButton).setOnClickListener(v->{
            String wagerString = addWager.getText().toString().trim();
            int wager ;
            try{
                wager = Integer.parseInt(wagerString);
                vm.setWager(wager);
            } catch (NumberFormatException e){
                Toast.makeText(getContext(), "Please choose correct inputs", Toast.LENGTH_SHORT).show();
                return ;
            }

            int radioId = gameType.getCheckedRadioButtonId();

            switch (radioId){
                case R.id.radio2Alike:
                    vm.setGameType(GameType.TWO_ALIKE);
                    break;
                case R.id.radio3Alike:
                    vm.setGameType(GameType.THREE_ALIKE);
                    break;
                case R.id.radio4Alike:
                    vm.setGameType(GameType.FOUR_ALIKE);
                    break;
                case -1:
                    break;
            }
            // Log.d(TAG,"radio id "+radioId);
            if(wager<=0 || radioId == -1 || !vm.isValidWager()){
                Toast.makeText(getContext(), "Please choose correct inputs", Toast.LENGTH_SHORT).show();
                return ;
            }

            Log.d(TAG,"Before :" + vm.balance);
            GameResult result = vm.play();
            if(result == GameResult.WIN){
                Toast.makeText(getContext(), "Congratulations! You Won", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), "You Lost! Try Again", Toast.LENGTH_SHORT).show();
            }
            // update UI
            updateUI();
        });

        view.findViewById(R.id.infoButton).setOnClickListener(v -> {
            NavDirections a = GamesFragmentDirections.actionGamesFragmentToInfoFragment();
            Navigation.findNavController(view).navigate(a);
            Log.d(TAG, "Going to InfoFragment");
        });
    }

    public void updateUI(){
        int[] dicevalues = vm.getDiceValues();
        die1.setText(Integer.toString(dicevalues[0]));
        die2.setText(Integer.toString(dicevalues[1]));
        die3.setText(Integer.toString(dicevalues[2]));
        die4.setText(Integer.toString(dicevalues[3]));

        txt_coins.setText(Integer.toString(vm.balance));
        addWager.setText("");
        gameType.clearCheck();
    }

    @Override
    public void onPause(){
        super.onPause();
        WalletPrefs.setBalance(requireActivity(),vm.balance);
    }

    @Override
    public void onResume(){
        super.onResume();
        vm.setBalance(WalletPrefs.balance(requireActivity()));
        updateUI();
    }
}