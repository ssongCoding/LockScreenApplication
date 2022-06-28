package com.example.lockscreenapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//https://seongjaemoon.github.io/android/2017/12/04/androidActivity.html
//https://jhshjs.tistory.com/49
//https://androiddvlpr.com/android-calendar-library/
public class MainActivity extends AppCompatActivity{

    Button btn_lock;

    /*** 앱을 빠져나가면, 앱을 잠금하려고 쓰는 변수 ***/
    boolean shouldLock = false;

    /*** SharedPreferences에 접근할 때 쓸 Class ***/
    private AppLock appLock;

    // Activity 띄울 때
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_lock = findViewById(R.id.btn_lock);
        btn_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LockActivity.class);
                intent.putExtra(INTENT_TYPE.TYPE, INTENT_TYPE.SET_LOCK);
                startActivityForResult(intent, INTENT_TYPE.SET_LOCK);   // 잠금 설정 활성화
            }
        });

        appLock = new AppLock(this);
    }

    // Activity 뜨고, 가장 먼저 실행되는 메소드
    @Override
    protected void onStart() {
        super.onStart();

        if(shouldLock&&appLock.isLocked()){ // 앱이 밖에 나갔다가 들어올 때
                                            // 잠금설정이 되어있으면,
            Intent intent = new Intent(MainActivity.this, LockActivity.class);
            intent.putExtra(INTENT_TYPE.TYPE, INTENT_TYPE.UNLOCK);
            startActivityForResult(intent, INTENT_TYPE.UNLOCK);         // 잠금 해제
        }
    }

    // 사용자와 상호작용이 가능 할 때 호출 되는 메소드
    // 예를 들면, 버튼을 클릭하고, 값을 넣고 등등 할 수 있을 때 호출 된다는 것!
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        shouldLock = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // startActivityForResult 결과값을 받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {                   // 내가 정해서 보낸 requestCode (어떤 Activity에서 결과를 보내왔는가)
            case INTENT_TYPE.SET_LOCK:                       // 잠금 설정을 하고 온 거면,
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "잠금 설정을 하셨습니다.", Toast.LENGTH_SHORT).show();
                    shouldLock = false;         // 어플을 사용 중이기 때문에, 잠글 필요 X
                    break;
                }
            case INTENT_TYPE.SET_UNLOCK:
                break;
            case INTENT_TYPE.UNLOCK:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                    shouldLock = false;         // 어플을 사용 중이기 때문에, 잠글 필요 X
                    break;
                }
        }
    }
}


