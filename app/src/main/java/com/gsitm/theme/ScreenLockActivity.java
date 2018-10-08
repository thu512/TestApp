package com.gsitm.theme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gsitm.theme.bioauth.FingerprintAuthenticationDialogFragment;
import com.gsitm.theme.bioauth.MyAuthticationCallback;

public class ScreenLockActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView pwd1;
    private ImageView pwd2;
    private ImageView pwd3;
    private ImageView pwd4;

    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    private Button num5;
    private Button num6;
    private Button num7;
    private Button num8;
    private Button num9;
    private Button num0;

    private ImageView delete;

    private TextView pwd;


    //지문인증
    private BiometricPrompt mBiometricPrompt;
    private FingerprintAuthenticationDialogFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock);
        pwd1 = findViewById(R.id.pwd1);
        pwd2 = findViewById(R.id.pwd2);
        pwd3 = findViewById(R.id.pwd3);
        pwd4 = findViewById(R.id.pwd4);

        num0 = findViewById(R.id.num0);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        delete = findViewById(R.id.delete);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        delete.setOnClickListener(this);


        pwd = findViewById(R.id.pwd);
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() == 1 ){
                    pwd1.setImageResource(R.drawable.circle_fill);
                    pwd2.setImageResource(R.drawable.circle_blank);
                    pwd3.setImageResource(R.drawable.circle_blank);
                    pwd4.setImageResource(R.drawable.circle_blank);
                }else if(editable.toString().length() == 2 ){
                    pwd1.setImageResource(R.drawable.circle_fill);
                    pwd2.setImageResource(R.drawable.circle_fill);
                    pwd3.setImageResource(R.drawable.circle_blank);
                    pwd4.setImageResource(R.drawable.circle_blank);
                }else if(editable.toString().length() == 3 ){
                    pwd1.setImageResource(R.drawable.circle_fill);
                    pwd2.setImageResource(R.drawable.circle_fill);
                    pwd3.setImageResource(R.drawable.circle_fill);
                    pwd4.setImageResource(R.drawable.circle_blank);
                }else if(editable.toString().length() == 4 ){
                    pwd1.setImageResource(R.drawable.circle_fill);
                    pwd2.setImageResource(R.drawable.circle_fill);
                    pwd3.setImageResource(R.drawable.circle_fill);
                    pwd4.setImageResource(R.drawable.circle_fill);
                    // 인증 처리
                    if(getIntent().getIntExtra("state",0) == 1){
                        //비밀번호 저장
                        Util.getInstance().setScreenPwd(getApplicationContext(),editable.toString());
                        Util.getInstance().setScreenSavePwd(getApplicationContext(), true);
                        Util.getInstance().setScreenState(getApplicationContext(), true);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //비밀번호 입력
                        if(Util.getInstance().getScreenPwd(getApplicationContext()).equals(editable.toString())){
                            Util.getInstance().setScreenState(getApplicationContext(), true);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"암호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                            pwd1.setImageResource(R.drawable.circle_blank);
                            pwd2.setImageResource(R.drawable.circle_blank);
                            pwd3.setImageResource(R.drawable.circle_blank);
                            pwd4.setImageResource(R.drawable.circle_blank);
                            editable.clear();
                        }
                    }

                }else if(editable.toString().length() == 0){
                    pwd1.setImageResource(R.drawable.circle_blank);
                    pwd2.setImageResource(R.drawable.circle_blank);
                    pwd3.setImageResource(R.drawable.circle_blank);
                    pwd4.setImageResource(R.drawable.circle_blank);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.num0:
                pwd.setText(pwd.getText()+"0");
                break;
            case R.id.num1:
                pwd.setText(pwd.getText()+"1");
                break;
            case R.id.num2:
                pwd.setText(pwd.getText()+"2");
                break;
            case R.id.num3:
                pwd.setText(pwd.getText()+"3");
                break;
            case R.id.num4:
                pwd.setText(pwd.getText()+"4");
                break;
            case R.id.num5:
                pwd.setText(pwd.getText()+"5");
                break;
            case R.id.num6:
                pwd.setText(pwd.getText()+"6");
                break;
            case R.id.num7:
                pwd.setText(pwd.getText()+"7");
                break;
            case R.id.num8:
                pwd.setText(pwd.getText()+"8");
                break;
            case R.id.num9:
                pwd.setText(pwd.getText()+"9");
                break;
            case R.id.delete:
                if(pwd.getText().toString().length() > 0){
                    pwd.setText(pwd.getText().subSequence(0,pwd.getText().length()-1));
                }
                break;
        }

    }

    @Override
    public void onBackPressed() {
        //아래코드를 막으면 현재 화면의 종료처리가 중단됨
        //super.onBackPressed();
        if(!isFirstEnd){
            //최초한번 백키를 눌렀다.
            isFirstEnd=true;
            //3초후에 초기화된다.(최초로 한번 백키를 눌렀던 상황이)
            handler.sendEmptyMessageDelayed(1,3000);
            Toast.makeText(this,"뒤로가기를 한번 더 누르시면 종료됩니다.",Toast.LENGTH_LONG).show();
        }else{
            //앱종료
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }

    boolean isFirstEnd; //백키를 한번 눌렀나?

    //핸들러, 메세지를 던져서 큐에 담고 하나씩 꺼내서 처리하는 메시징 시스템
    Handler handler = new Handler(){
        //이 메소드는 큐에 메세지가 존재하면 뽑아서 호출된다.
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){ //최초로 백키를 한번 눌렀다.

            }else if(msg.what == 1){ //3초가 지났다. 다시 초기화.
                isFirstEnd=false;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //여기서 지문인증 가능한 기기면 지문인증 팝업 띄움

        if(getIntent().getIntExtra("state",0) == 1){
            //비밀번호 저장이 안된 초기사용 유저는 지문인증 패스 -> 무조건 비밀번호입력해서 설정
        }else{
            //이미 비밀번호 설정한 사용자 -> 지문 사용
            //28이상 버전
            if (Build.VERSION.SDK_INT >= 28) {

                //지문을 사용할수 있는경우
                if (Util.getInstance().isSupportBiometricPrompt(this)) {

                    // Create biometricPrompt
                    mBiometricPrompt = new BiometricPrompt.Builder(this)
                            .setDescription("Description")
                            .setTitle("Title")
                            .setSubtitle("Subtitle")
                            .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("LCJ", "Cancel button clicked");
                                 }
                            })
                            .build();
                    CancellationSignal cancellationSignal = getCancellationSignal();
                    BiometricPrompt.AuthenticationCallback authenticationCallback = new MyAuthticationCallback(this){
                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            Log.d("LCJ", "Message: 인증 되었습니다.");

                            Toast.makeText(getApplicationContext(), "지문인증 완료", Toast.LENGTH_SHORT).show();

                            Util.getInstance().setScreenState(getApplicationContext(), true);
                            finish();
                        }
                    };


                    Log.d("LCJ", "Show biometric prompt");
                    mBiometricPrompt.authenticate( cancellationSignal, getMainExecutor(), authenticationCallback);

                }

            } else { //FingerPrint사용
                Log.i("지문", "API 27");
                if(Util.isFingerprintAuthAvailable(getApplicationContext())){
                    mFragment = new FingerprintAuthenticationDialogFragment();
                    mFragment.setCallback(new FingerprintAuthenticationDialogFragment.SecretAuthorize(){
                        @Override
                        public void success() {
                            Toast.makeText(getApplicationContext(), "인증 성공", Toast.LENGTH_SHORT).show();
                            Log.d("LCJ", "Message: 인증 되었습니다.");
                            Util.getInstance().setScreenState(getApplicationContext(), true);
                            finish();
                        }

                        @Override
                        public void fail() {
                            Log.d("LCJ", "Message: 인증 실패.");
                            Toast.makeText(getApplicationContext(), "인증 실패", Toast.LENGTH_SHORT).show();
                            mFragment.dismiss();
                        }

                    });
                    mFragment.show(this.getFragmentManager(), "my_fragment");
                }

            }
        }

    }




    //지문 인증 취소 이벤트
    private CancellationSignal getCancellationSignal() {
        // With this cancel signal, we can cancel biometric prompt operation
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                //handle cancel result
                Log.d("LCJ", "Canceled");
            }
        });
        return cancellationSignal;
    }
}
