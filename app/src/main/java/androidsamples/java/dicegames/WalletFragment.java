package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A {@link Fragment} that implements the Wallet screen.
 */
public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";
    private GamesViewModel vm;

    private TextView txt_coins;
    private Button btn_die;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        Log.d(TAG, "VM: " + vm);

        txt_coins = view.findViewById(R.id.txt_balance);
        btn_die = view.findViewById(R.id.btn_die);

        view.findViewById(R.id.btn_die).setOnClickListener(v -> {
            vm.rollWalletDie();
            // update UI
            updateUI();
            Log.d(TAG, "Rolled");
        });

        view.findViewById(R.id.btn_games).setOnClickListener(v -> {
            NavDirections a = WalletFragmentDirections.actionWalletFragmentToGamesFragment();
            Navigation.findNavController(view).navigate(a);
            Log.d(TAG, "Going to GamesFragment");
        });
    }
    public void updateUI(){
        txt_coins.setText(Integer.toString(vm.balance));
        btn_die.setText(Integer.toString(vm.getWalletDieValue()));
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