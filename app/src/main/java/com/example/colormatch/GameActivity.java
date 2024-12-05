package com.example.colormatch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Random;


public class GameActivity extends AppCompatActivity {


   // red #ff0101
   // green #50ca17
   // blue #0036ff
   // yellow #ffd100

    private static final String RED = "red";
    private static final String RED_VALUE = "#ff0101";

    private static final String GREEN = "green";
    private static final String GREEN_VALUE = "#50ca17";

    private static final String BLUE = "blue";
    private static final String BLUE_VALUE = "#0036ff";

    private static final String YELLOW = "yellow";
    private static final String YELLOW_VALUE = "#ffd100";



    private TextView txtQuestion = null;
    private Button btnRed, btnBlue, btnGreen, btnYellow = null;

    private ProgressBar progressBar = null;

    private Integer score = 0;

    private final Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        txtQuestion = findViewById(R.id.txt_question);

        btnRed = findViewById(R.id.btn_red);
        btnBlue = findViewById(R.id.btn_blue);
        btnGreen = findViewById(R.id.btn_green);
        btnYellow = findViewById(R.id.btn_yellow);


        progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(0);

        setQuestion();





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setQuestion() {

        setTimer();

        Map<String, String> colorMap = new HashMap<>();
        colorMap.put(RED, RED_VALUE);
        colorMap.put(BLUE, BLUE_VALUE);
        colorMap.put(GREEN, GREEN_VALUE);
        colorMap.put(YELLOW, YELLOW_VALUE);

        String question = getRandomColor();
        String questionColor = getRandomColor();

        if(colorMap.containsKey(question) && colorMap.containsKey(questionColor)) {
            txtQuestion.setText(question);
            String questionTextColor = colorMap.get(questionColor);
            txtQuestion.setTextColor(Color.parseColor(questionTextColor));

        }

    }

    private String getRandomColor() {
        List<String> colors = Arrays.asList(RED, BLUE, GREEN, YELLOW);


        Random random = new Random();
        int randomIndex = random.nextInt(4);
        return colors.get(randomIndex);
    }

    public void submitAnswer(View view) {
        Button btn = (Button) view;
        boolean result = txtQuestion.getText().equals(btn.getText());

        if(result) {
            //answer correct
            score += 1;
            setQuestion();
        } else {
            //wrong answer. exit the game
            exit();
        }

    }

    private void exit() {
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("score", String.valueOf(score));
        this.startActivity(intent);

    }

    private void setTimer() {
        progressBar.setProgress(0);
        handler.removeCallbacksAndMessages(null);
        final int delay = 1000;

        final int[] counter = {1};

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int progress = counter[0] * 20;
                progressBar.setProgress(progress);

                if (progress == 100) {
                    exit();
                }

                counter[0]++;
                handler.postDelayed(this, delay);
            }
        }, delay
        );
    }
}