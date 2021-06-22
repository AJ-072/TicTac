package com.aj.android.tictac.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aj.android.tictac.R;
import com.aj.android.tictac.adapter.GameAdapter;
import com.aj.android.tictac.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.onCLick {
    private int who = 1;
    private GameAdapter GameAdapter;
    private int winnerScore1 = 0, winnerScore2 = 0;
    private RecyclerView recyclerView;
    private List<List<Integer>> winner = new ArrayList<>();
    private int count = 0;
    private List<Integer> player1 = new ArrayList<>();
    private List<Integer> player2 = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int orientation = display.getRotation();
        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180)
            GameAdapter = new GameAdapter(9, displayMetrics.widthPixels, this);
        else
            GameAdapter = new GameAdapter(9, displayMetrics.heightPixels - 100, this);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(GameAdapter);
        setWinner();
    }

    void setWinner() {
        winner.add(Arrays.asList(0, 1, 2));
        winner.add(Arrays.asList(3, 4, 5));
        winner.add(Arrays.asList(6, 7, 8));
        winner.add(Arrays.asList(0, 3, 6));
        winner.add(Arrays.asList(1, 4, 7));
        winner.add(Arrays.asList(2, 5, 8));
        winner.add(Arrays.asList(0, 4, 8));
        winner.add(Arrays.asList(2, 4, 6));
    }

    @Override
    public void OnClickListener(int position, View view) {
        ++count;
        if (!player1.contains(position) && !player2.contains(position)) {
            view.setBackground(ContextCompat.getDrawable(this, who == 1 ? R.drawable.ic_circle : R.drawable.ic_cross));
            if (who == 1) {
                ++who;
                player1.add(position);
                if (winnerDeclare(player1)) {
                    setWinnerScore(1);
                    gameOver("Player 1 Won!");
                }
            } else {
                --who;
                player2.add(position);
                if (winnerDeclare(player2)) {
                    setWinnerScore(2);
                    gameOver("Player 2 Won!");
                }
            }
        } else
            Toast.makeText(this, "Already Taken By Player " + (player1.contains(position) ? 1 : 2), Toast.LENGTH_SHORT).show();

    }

    boolean winnerDeclare(List<Integer> player) {
        for (List<Integer> win : winner) {
            if (player.containsAll(win))
                return true;
        }
        if (count == 9)
            gameOver("Game Over");
        return false;
    }

    void gameOver(String Reason) {
        new AlertDialog.Builder(this)
                .setTitle(Reason)
                .setNegativeButton("Restart", (dialog, which) -> Restart())
                .setPositiveButton("Exit", (dialog, which) -> {
                    dialog.dismiss();
                    onBackPressed();
                })
                .setCancelable(false)
                .create()
                .show();
    }

    void Restart() {
        player1.clear();
        player2.clear();
        GameAdapter.notifyDataSetChanged();
        who = 1;
        count = 0;
    }

    void setWinnerScore(int player) {
        if (player == 1) {
            winnerScore1++;
        } else {
            winnerScore2++;
        }
    }
}