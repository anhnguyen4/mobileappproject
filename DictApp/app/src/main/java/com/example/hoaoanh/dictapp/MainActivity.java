package com.example.hoaoanh.dictapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editInput;
    EditText editOutput;
    Button btnTranslate;

    public static final char SPACE = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editInput = (EditText)findViewById(R.id.editInput);
        editOutput = (EditText)findViewById(R.id.editOutput);

        btnTranslate = (Button)findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	int count = 0;
            	boolean notCounted = true;
                String input = editInput.getText().toString();
                for (int i = 0;i < input.length(); i++) {
                	if(input.charAt(i) != SPACE) {
                		if (notCounted) {
                			count++;
                			notCounted = false;
                		}
                	}
                	else
                		notCounted = true;
                }
                if (count <= 2){
                    editOutput.setText("Nghĩa của từ là:");
                }
                else
                	editOutput.setText("Không có kết quả.");
            }
        });

    }
}
