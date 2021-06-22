package com.aj.android.tictac.model;

import com.aj.android.tictac.databinding.PlayerDataBinding;

public class Player {
    private final PlayerDataBinding dataBinding;
    private final String playerName;
    private final int playerColor;

    public Player(PlayerDataBinding dataBinding, String playerName, int playerColor) {
        this.dataBinding = dataBinding;
        this.playerName = playerName;
        this.playerColor = playerColor;

        dataBinding.playerName.setText(playerName);
    }
}
