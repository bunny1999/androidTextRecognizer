package in.irotech.textrecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextIntent extends AppCompatActivity {

    Button button;
    TextView textView;
    String textRecognized="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_intent);

        button=findViewById(R.id.back_button);
        textView=findViewById(R.id.result_textview);
        textRecognized=getIntent().getStringExtra(StartAndMantaineState.RESULT_TEXT);
        textView.setText(textRecognized);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
