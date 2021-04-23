package com.example.xando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final Button[][] btn = new Button[3][3];
    private boolean player_ONE_Turn = true;
    private int round_count;
    private int player_one_points;
    private int player_two_Points;
    private TextView txt_view_player_one;
    private TextView txt_view_player_two;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_view_player_one = findViewById(R.id.text_view_p1);
        txt_view_player_two = findViewById(R.id.text_view_p2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                btn[i][j] = findViewById(resID);
                btn[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player_ONE_Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        round_count++;
        if (whoWin()) {
            if (player_ONE_Turn) {
                player_one_win();
            } else {
                player_two_wins();
            }
        } else if (round_count == 9) {
            tie();
        } else {
            player_ONE_Turn = !player_ONE_Turn;
        }
    }
    private boolean whoWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = btn[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void player_one_win() {
        player_one_points++;
        Toast.makeText(this, "Player 1 wins!:)", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player_two_wins() {
        player_two_Points++;
        Toast.makeText(this, "Player 2 wins!:)", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void tie() {
        Toast.makeText(this, "Draw!:-p", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText() {
        txt_view_player_one.setText("Player 1: " + player_one_points);
        txt_view_player_two.setText("Player 2: " + player_two_Points);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j].setText("");
            }
        }
        round_count = 0;
        player_ONE_Turn = true;
    }
    private void resetGame() {
        player_one_points = 0;
        player_two_Points = 0;
        updatePointsText();
        resetBoard();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Round Count", round_count);
        outState.putInt("Player1 points", player_one_points);
        outState.putInt("Player2 points", player_two_Points);
        outState.putBoolean("Player1 turn", player_ONE_Turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        round_count = savedInstanceState.getInt("Round count");
        player_one_points = savedInstanceState.getInt("player1Points");
        player_two_Points = savedInstanceState.getInt("player2Points");
        player_ONE_Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
