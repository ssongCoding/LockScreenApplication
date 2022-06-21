package com.example.lockscreenapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//https://seongjaemoon.github.io/android/2017/12/04/androidActivity.html
//https://jhshjs.tistory.com/49
//https://androiddvlpr.com/android-calendar-library/
public class MainActivity extends AppCompatActivity{

    Button btn_lock;

    boolean isLocked = false;

    /**
     * 생명주기
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_lock = findViewById(R.id.btn_lock);
        btn_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LockActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    // Activity 뜨고, 가장 먼저 실행되는 메소드
    @Override
    protected void onStart() {
        super.onStart();

        if(isLocked){ // 잠금 설정이 되어 있으면,
            Intent intent = new Intent(MainActivity.this, LockActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    // 사용자와 상호작용이 가능 할 때 호출 되는 메소드
    // 예를 들면, 버튼을 클릭하고, 값을 넣고 등등 할 수 있을 때 호출 된다는 것!
    @Override
    protected void onResume() {
        super.onResume();

        if(isLocked){ // 잠금 설정이 되어 있으면,
            Intent intent = new Intent(MainActivity.this, LockActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // startActivityForResult 결과값을 받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getApplicationContext(), "WHHHHHHY", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), requestCode,
//                Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(getApplicationContext(), resultCode,
//                Toast.LENGTH_SHORT).show();
//
        switch (requestCode) { // 내가 정해서 보낸 requestCode
            case 1:
                Toast.makeText(getApplicationContext(), "Hi",
                        Toast.LENGTH_SHORT).show();
                if (resultCode == Activity.RESULT_OK) {
                    isLocked = true;
                    break;
                }
        }
    }
}


