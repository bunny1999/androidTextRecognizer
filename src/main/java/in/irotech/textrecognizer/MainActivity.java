package in.irotech.textrecognizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {

    Button button;
    FirebaseVisionImage image;
    FirebaseVisionTextRecognizer recognizer;
    public static final int REQUEST_CODE=121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(cameraIntent,REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            Bundle imageData=data.getExtras();
            Bitmap bitmap=(Bitmap) imageData.get("data");

            recognizeText(bitmap);
        }
    }

    private void recognizeText(Bitmap bitmap) {

        try {
            image= FirebaseVisionImage.fromBitmap(bitmap);
            recognizer=FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        recognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String textRecognized=firebaseVisionText.getText();
                if (textRecognized.isEmpty()){
                    Toast.makeText(MainActivity.this,"NO TEXT DETECTED",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(MainActivity.this,TextIntent.class);
                    intent.putExtra(StartAndMantaineState.RESULT_TEXT,textRecognized);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
