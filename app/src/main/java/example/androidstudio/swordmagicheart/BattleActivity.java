package example.androidstudio.swordmagicheart;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * Created by thomasmilgrew on 3/20/17.
 */

public class BattleActivity extends AppCompatActivity {

    ImageView playerChoice;
    ImageView computerChoice;
    Integer playerNumericScore = 0;
    Integer computerNumericScore  = 0;
    Boolean battleEnable = false; //we use this bool value to keep track of if user picked an option or not
    Integer secondsLeft = 30;
    TextView timer;

    public MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_screen);

        //----------------------------------------------------------
        //start the background song for the battle
        //----------------------------------------------------------
        mediaPlayer = MediaPlayer.create(this, R.raw.opening_theme);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        //----------------------------------------------------------
        //finds the button with the rock image and establishes an
        //on click listener
        //----------------------------------------------------------
        ImageButton rockButton = (ImageButton)findViewById(R.id.rock);
        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------
                //find's the player's selected choice and sets the rock
                //image in the main battle area
                //----------------------------------------------------------
                playerChoice = (ImageView)findViewById(R.id.player_battle_choice);
                playerChoice.setImageResource(R.drawable.rock);
                playerChoice.setTag(R.drawable.rock);
                playerChoice.setScaleX(-1);//to make the fist face the correct way
                battleEnable = true;  //allows us to battle
            }
        });

        //----------------------------------------------------------
        //finds the button with the paper image and establishes
        // an on click listener
        //----------------------------------------------------------
        ImageButton paperButton = (ImageButton)findViewById(R.id.paper);
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------
                //find's the player's selected choice and sets the paper
                //image in the main battle area
                //----------------------------------------------------------
                playerChoice = (ImageView)findViewById(R.id.player_battle_choice);
                playerChoice.setImageResource(R.drawable.paper);
                playerChoice.setTag(R.drawable.paper);
                playerChoice.setScaleX(1); //to fix the scale from rock image
                battleEnable = true; //allows us to battle
            }
        });

        //----------------------------------------------------------
        //finds the button with the scissor image and establishes an
        //on click listener
        //----------------------------------------------------------
        ImageButton scissorButton = (ImageButton)findViewById(R.id.scissor);
        scissorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------
                //find's the player's selected choice and sets the scissor
                //image in the main battle area
                //----------------------------------------------------------
                playerChoice = (ImageView)findViewById(R.id.player_battle_choice);
                playerChoice.setImageResource(R.drawable.scissor);
                playerChoice.setTag(R.drawable.scissor);
                playerChoice.setScaleX(1); //to fix the scale from rock image
                battleEnable = true; //allows us to battle
            }
        });

        //----------------------------------------------------------
        //finds the "battle" button and begins the battle
        //----------------------------------------------------------
        Button battleButton = (Button) findViewById(R.id.battle_button);
        battleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (battleEnable == false) { //if the user did not select a value, show message
                    Toast.makeText(BattleActivity.this, "Select an Option", Toast.LENGTH_SHORT).show();
                } else {
                    setComputerChoice();
                    battle();
                }
            }
        });

        //----------------------------------------------------------
        //starting the timer for the battle
        //----------------------------------------------------------
        timer = (TextView)findViewById(R.id.timer);
        new CountDownTimer(32000, 1000){
            @Override
            public void onTick(long l) {
                timer.setText(secondsLeft.toString());
                secondsLeft = secondsLeft - 1;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


    //----------------------------------------------------------
    //finds the computer's choice randomly
    //sets the random image and assigns a tag
    //----------------------------------------------------------
    public void setComputerChoice(){
        computerChoice = (ImageView)findViewById(R.id.computer_battle_choice);
        int [] imageOptions = new int[] {R.drawable.rock, R.drawable.paper, R.drawable.scissor};
        Random rand = new Random();
        int n = rand.nextInt(3);
        computerChoice.setImageResource(imageOptions[n]);
        computerChoice.setTag(imageOptions[n]);
    }

    public void battle(){

        TextView winnerText = (TextView)findViewById(R.id.winner_text);
        TextView playerScore = (TextView)findViewById(R.id.player_score);
        TextView computerScore = (TextView)findViewById(R.id.computer_score);

        //----------------------------------------------------------
        //if player and computer picked the same thing
        //----------------------------------------------------------
        if(playerChoice.getDrawable().getConstantState().equals(computerChoice.getDrawable().getConstantState())){
            winnerText.setText("It is a Tie!!!");
        }

        //----------------------------------------------------------
        //if player wins print "You win" otherwise the computer wins
        //----------------------------------------------------------
        else if((playerChoice.getTag().equals(R.drawable.paper) && computerChoice.getTag().equals(R.drawable.rock))
                || (playerChoice.getTag().equals(R.drawable.rock) && computerChoice.getTag().equals(R.drawable.scissor))
                || (playerChoice.getTag().equals(R.drawable.scissor) && computerChoice.getTag().equals(R.drawable.paper))){

            playerNumericScore= playerNumericScore + 1;
            playerScore.setText(playerNumericScore.toString());
            winnerText.setText("You win!!!");

        }else{
            computerNumericScore= computerNumericScore + 1;
            computerScore.setText(computerNumericScore.toString());
            winnerText.setText("Computer Wins!");
        }
    }

    //----------------------------------------------------------
    //when we stop the activity
    //----------------------------------------------------------
    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        mediaPlayer.release();
    }
}
