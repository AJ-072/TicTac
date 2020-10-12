package com.aj.android.tictac;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements com.aj.android.tictac.adapter.onCLick {
    private int who = 1;
    private adapter adapter;
    private int winnerScore1 = 0, winnerScore2 = 0;
    private RecyclerView recyclerView;
    private List<List<Integer>> winner = new ArrayList<>();
    private int count = 0;
    private TextView won1, loss1, won2, loss2;
    private List<Integer> player1 = new ArrayList<>();
    private List<Integer> player2 = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tag", "onCreate: ");
        recyclerView = findViewById(R.id.recycler);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        won1 = findViewById(R.id.winnerScore1);
        won2 = findViewById(R.id.winnerScore2);
        loss1 = findViewById(R.id.lossCount1);
        loss2 = findViewById(R.id.lossCount2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int orientation = display.getRotation();
        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180)
            adapter = new adapter(9, displayMetrics.widthPixels, this);
        else
            adapter = new adapter(9, displayMetrics.heightPixels - 100, this);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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
                .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Restart();
                    }
                })
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onBackPressed();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    void Restart() {
        player1.clear();
        player2.clear();
        adapter.notifyDataSetChanged();
        who = 1;
        count = 0;
    }

    void setWinnerScore(int player) {
        if (player == 1) {
            winnerScore1++;
        } else {
            winnerScore2++;
        }
        won1.setText("won: " + winnerScore1);
        loss1.setText("loss: " + winnerScore2);
        won2.setText("won: " + winnerScore2);
        loss2.setText("loss: " + winnerScore1);
    }
}