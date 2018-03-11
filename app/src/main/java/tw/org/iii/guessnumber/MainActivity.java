package tw.org.iii.guessnumber;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView log, mesg;
    private EditText input;
    private Button guess;
    private String answer;
    private int count;
    private int x = 3;
    private int checkedItem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = findViewById(R.id.log);
        input = findViewById(R.id.input);
        mesg = findViewById(R.id.mesg);
        guess = findViewById(R.id.guess);
        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGuess();
            }
        });
        mesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesg.setVisibility(View.GONE);
            }
        });

        initGame();


    }

    private void initGame(){

        answer = createAnswer(x);
        input.setText("");
        log.setText("");
        count = 0;
        Log.v("brad", answer);
    }

    private void doGuess(){
        String strInput = input.getText().toString();
        Log.v("brad",""+strInput.length());
        input.setText("");

        if(strInput.length() != x){
            AlertDialog alert = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("請輸入"+ x + "碼數字");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert = builder.create();
            alert.show();
        }else {
            String result = checkAB(answer, strInput);
            log.append(count+1 + ") " + strInput + ":" + result + "\n");
            if (result.equals(x + "A0B")) {
                count = 0;
                showDialog();
            } else if (count >= 9) {
                showLoser();
            } else {
                count++;
            }
        }

    }

    private void showDialog(){
        //mesg.setVisibility(View.VISIBLE);

        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You Win");
        builder.setMessage("恭喜老爺,賀喜夫人");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initGame();
            }
        });
        alert = builder.create();
        alert.show();





    }


    static String checkAB(String a, String g) {
        int A, B; A = B = 0;

        for (int i=0 ;i<a.length(); i++) {
            if (g.charAt(i) == a.charAt(i)) {
                A++;
            }else if (a.indexOf(g.charAt(i)) != -1) {
                B++;
            }
        }

        return A + "A" + B +"B";
    }

    static String createAnswer(int d) {
        int[] poker = new int[10];
        for (int i=0; i<poker.length; i++) poker[i] = i;

        for (int i=poker.length; i>0; i--) {
            int rand = (int)(Math.random()*i);
            int temp = poker[rand];
            poker[rand] = poker[i-1];
            poker[i-1] = temp;
        }

        String ret = "";
        for (int i=0; i<d; i++) {
            ret += poker[i];
        }

        return ret;
    }

    public void end(View view) {
        finish();
    }

    public void reset(View view) {
        initGame();
    }

    public void showLoser(){
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You Lose");
        builder.setMessage("答案是： " + answer);
        builder.setCancelable(false);
        builder.setPositiveButton("重玩", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initGame();
                count = 0;
            }
        });
        builder.setNegativeButton("結束遊戲", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void setting(View view) {
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("設定數字位數");
        CharSequence[] array = {"3", "4", "5"};
        builder.setSingleChoiceItems(array, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        answer = createAnswer(3);
                        x = 3;
                        checkedItem = 0;
                        Log.v("brad","3");
                        break;
                    case 1:
                        answer = createAnswer(4);
                        x = 4;
                        checkedItem = 1;
                        Log.v("brad","4");
                        break;
                    case 2:
                        answer = createAnswer(5);
                        x = 5;
                        checkedItem = 2;
                        Log.v("brad","5");
                        break;
                    default :
                        break;
                }
            }
        });
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v("brad", answer);
                initGame();
            }
        });

        alert = builder.create();
        alert.show();

    }
}