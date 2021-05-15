스마트 모바일 프로그래밍 약쏙 최종 보고서
===================================
목차   
-----


### 1.소개
>#### 1-1 개발배경
>#### 1-2 사용한 기능
>#### 1-3 기대효과

### 2. 기능 구현
>#### 2-1 사용자 생성
>>##### 2-1-1 Firebase와 Android Studio 연동
>>##### 2-1-2 회원가입
>>##### 2-1-3 로그인
>>##### 2-1-4 구글 로그인
>>##### 2-1-5 아이디 찾기 및 비밀번호 재설정
>>##### 2-1-6 회원정보 수정
>#### 2-2 약국 찾기
>>##### 2-2-1 위치 관련 퍼미션 허용과 GPS 활성화
>>##### 2-2-2 사용자의 현재 위치 확인 
>>##### 2-2-3 현재 위치를 기반으로 주변 약국 검색
>>##### 2-2-4 약국 파싱 
>>##### 2-2-5 ‘동이름’으로 약국 검색
>#### 2-3 사용자 후기 게시판
>>##### 2-3-1 firebase 연동
>>##### 2-3-2 게시물 등록  
>>##### 2-3-3 게시물 수정 및 삭제
>>##### 2-3-4 사용자 정보 연동
>#### 2-4 약검색   
>>##### 2-4-1 약 이름으로 검색   
>>##### 2-4-2 모양으로 약 검색   
>>##### 2-4-3 상세보기 페이지

<hr/>

## 1. 소개   

### 1-1 개발배경   
하루가 멀다 하고 새로운 의약품과 건강기능식품이 쏟아지고 있는 요즘, 의약품과 건강기능식품 홍수의 시대라고 해도 과언이 아니다. 수없이 출시되는 의약품과 건강기능식품을 의사처방 없이 쉽게 구입할 수 있어짐에 따라 소비자들의 딜레마 또한 피해갈 수 없다.    
약은 복용시간과 복용기간을 정확하게 지켰을 때 그 효과가 커진다. 그러나 현대인들은 바쁜 생활 속에서 복용중인 약의 복용시간 및 복용기간을 놓치기 쉽다. 때문에 사용자가 설정해놓은 약물 복용시간을 알려주고, 증상에 따른 약 추천 및 가지고 있는 약 사진과 일치하는 약의 정보를 알려줌으로 더욱 편리한 약 복용을 가능하게 하며, GPS를 통해 가까운 약국의 위치를 보여주어 약국 이용을 편리하게 한다.

### 1-2 사용한 기능   
의약품 정보 알림 서비스 어플리케이션은 약의 모양 또는 이름으로 간편하게 검색기능을 구현하여 의약품의 알맞은 복용방법과 편리한 약 검색을 위한 서비스를 제공하고, 약 복용시간 알림으로 정확한 시간에 약을 복용할 수 있도록한다. 또한 google맵과 연동을 통하여 내 주변에 약국의 위치와 지역(동)으로 검색하여 약국 위치를 알 수 있게 함으로서 모든 사람들에 의약품 정보를 언제 어디서나 편리하게 서비스를 제공 하는 것을 목표로 구성하였다. 약국과 의약품 정보를 파싱하기 위해 공공데이터를 이용하고, android studio에 데이터 저장 및 불러와 리뷰와 회원가입/로그인 알림 기능을 구현하고 또한 Google API연동을 통해 GPS를 받아와서 약국을 찾을 수 있도록 한다.

### 1-3 기대효과   
편의점에서도 약을 손쉽게 구매할 수 있는 요즘, 편의점 직원은 의약품에 대한 전공을 가지고 있는 약사가 아니어서 약에 대한 효능과 부작용을 잘 알고 있지 않다. 그렇기 때문에 약쏙 어플리케이션으로 합리적인 의약품을 선택할 수 있게 도와준다. 이와 더불어 집에 있는 의약품이 어떤 효능과 부작용을 가지고 있는지 알지 못할 때, 약의 모양과 이름을 통한 검색으로 자신에게 맞는 의약품을 선택할 수 있게 도와준다는 기대효과를 가져올 수 있다. 
<hr/>

## 2. 기능 구현

>### 2-1 사용자 생성
회원가입과 로그인 및 로그아웃을 위해 데이터를 저장해 놓을 Firebase가 필요하다.
>>#### 2-1-1 Firebase와 Android Studio 연동
1)Firebase에 개발을 진행할 프로젝트를 등록한다. Google 로그인을 사용할 예정이므로 SHA-1 정보도 저장해준다.   
2)Firebase Android 구성 파일(google-services.json)을 다운로드하여 등록한 프로젝트에 추가한다.   
3)어플에서 Firebase를 사용할 수 있도록 google-services 플러그인을 Gradle 파일에 추가힌다.
~~~java
dependencies {
    classpath 'com.google.gms:google-services:4.2.0'  // Google Services plugin
  }
}
~~~
4)모듈(앱 수준) Gradle 파일(일반적으로 app/build.gradle)에서 다음 줄을 파일 하단에 추가한다.
~~~java
apply plugin: 'com.android.application'

android {
  // ...
}
apply plugin: 'com.google.gms.google-services'  // Google Play services Gradle plugin
~~~
5)모듈(앱 수준) Gradle 파일(일반적으로 app/build.gradle)에서 핵심 Firebase SDK의 종속 항목을 추가한다.
~~~java
dependencies {
 implementation 'com.google.firebase:firebase-core:17.0.0'
 }
~~~
>>#### 2-1-2 회원가입
1)아이디로 사용할 이메일, 이름, 전화번호, 비밀번호를 입력한 후 각각의 항목에 대한 빈칸유무, 정규식 등의 유효성 검사를 진행한 후 입력한 값들이 모두 유효하면 Firebase에 저장해준다. 
~~~java
public class SignupActivity extends AppCompatActivity {
  // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    // 이메일 정규식
    public static final Pattern EMAIL_ADDRESS = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // 전화번호 정규식
    public static final Pattern PHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", Pattern.CASE_INSENSITIVE);
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // 파이어스토어 인증 객체 생성
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // 작성한 이메일 값과 비밀번호 값을 저장할 객체 생성
    private EditText editTextEmail;
    private EditText editTextPassword;

    // 비밀번호 확인에 작성한 값을 저장할 객체 생성
    private EditText editTextPasswordCheck;

    // 작성한 이름 값과 전화번호 값을 저장할 객체 생성
    private EditText editTextName;
    private EditText editTextPhone;

    // 회원정보에 저장할 값 객체 생성
    private String email = "";
    private String password = "";
    private String passwordCheck = "";
    private String name = "";
    private String phone = "";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_signup);
       
       // id가 write_email인 editText에 대한 메서드 저장
       editTextEmail = findViewById(R.id.write_email);
       // id가 signup_password인 editText에 대한 메서드 저장
       editTextPassword = findViewById(R.id.signup_password);
       // id가 check_password인 editText에 대한 메서드 저장
       editTextPasswordCheck = findViewById(R.id.check_password);
       // id가 write_name인 editText에 대한 메서드 저장
       editTextName = findViewById(R.id.write_name);
       // id가 write_phone인 editText에 대한 메서드 저장
       editTextPhone = findViewById(R.id.write_phone);
       }
~~~   
##### 회원가입 버튼 클릭시
~~~java
    // onClick signup
    public void singUp(View view) {
        // editText에 작성한 내용을 String으로 변환하여 객체에 저장
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        passwordCheck = editTextPasswordCheck.getText().toString();
        name = editTextName.getText().toString();
        phone = editTextPhone.getText().toString();

        // 유효성 검사 후 회원가입 실행
        if (isValidEmail() && isValidPasswd() && isValidPasswdcheck() && isValidName() && isValidPhone()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 입력한 email, password, name, phone 값을 파이어스토어에 저장
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", email);
                                user.put("password", password);
                                user.put("name", name);
                                user.put("phone", phone);
                                firebaseFirestore.collection("users").document(email)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // 정보 저장이 성공적으로 이루어지면 이메일 인증 메일 발송
                                                sendEmailVerification();
                                                //  이메일 인증 다이얼로그 보여줌
                                                authemailDialog.show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // 회원가입에 실패하면 "회원가입 실패" 토스트를 보여줌
                                                Toast.makeText(SignupActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {
                                Toast.makeText(SignupActivity.this, R.string.alreadyemail, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
~~~
##### 유효성 검사
~~~java
   // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 칸이 공백이면 false
            Toast.makeText(SignupActivity.this, R.string.writeemail, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(SignupActivity.this, R.string.notvaildemail, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 칸이 공백이면 false
            Toast.makeText(SignupActivity.this, R.string.writepassword, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식이 불일치하면 false
            Toast.makeText(SignupActivity.this, R.string.notvalidpassword, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 체크 유효성 검사
    private boolean isValidPasswdcheck() {
        if (passwordCheck.isEmpty()) {
            // 비밀번호 칸이 공백이면 false
            Toast.makeText(SignupActivity.this, R.string.writecheckpassword, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(passwordCheck)) {
            // 비밀번호와 비밀번호 확인에 입력한 값이 불일치하면 false
            Toast.makeText(SignupActivity.this, R.string.notmatchpass, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 이름 유효성 검사
    private boolean isValidName() {
        if (name.isEmpty()) {
            // 이름 칸이 공백이면 false
            Toast.makeText(SignupActivity.this, R.string.writename, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 전화번호 유효성 검사
    private boolean isValidPhone() {
        if (phone.isEmpty()) {
            // 전화번호 칸이 공백이면 false
            Toast.makeText(SignupActivity.this, R.string.writephone, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PHONE_PATTERN.matcher(phone).matches()) {
            Toast.makeText(SignupActivity.this, R.string.notvalidphone, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
~~~
<div>
<img src="https://user-images.githubusercontent.com/62936197/86549436-98e0b500-bf7a-11ea-8e1a-2906bb63d0d6.png" width="30%">   
<img src="https://user-images.githubusercontent.com/62936197/86549227-fe807180-bf79-11ea-9fbf-706f51c8ced9.png" width="60%">   
</div>

2)회원가입이 정상적으로 성공하면 입력한 이메일로 인증 메일이 전송되며, Dialog를 통해 이메일 인증이 필요함을 알려준다.   
전송된 메일을 통해 이메일 인증을 완료하지 않으면 이메일과 비밀번호 값이 일치해도 로그인에 성공할 수 없으며 이메일 인증이 완료되어야 로그인에 성공할 수 있다.   
##### 이메일 인증 다이얼로그를 작성한 java파일
~~~java
public class AuthemailDialog extends Dialog {

    // 확인 버튼 생성
    private Button positivebutton;
    // 확인 버튼의 onClickListner 생성
    private View.OnClickListener positiveListener;

    // 이메일 인증 다이얼로그
    public AuthemailDialog(@NonNull Context context, View.OnClickListener positiveListener) {
        super(context);
        this.positiveListener = positiveListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 밖의 화면은 흐리게 함
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.authemail_dialog);

        // 확인 버튼
        positivebutton = findViewById(R.id.positivebutton);
        // 클릭 리스너
        positivebutton.setOnClickListener(positiveListener);
    }
}
~~~
#####  SignUpActivity에 작성한 이메일 인증 다이얼로그
~~~java
 // 이메일 인증 다이얼로그 객체 생성
 private AuthemailDialog authemailDialog;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
          // 다이얼로그의 리스너 등록
        authemailDialog = new AuthemailDialog(this, positiveListener);
        }
~~~   
#####  이메일 인증 다이얼로그의 확인버튼 클릭시   
~~~java
    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            // 다이얼로그 종료
            authemailDialog.dismiss();
            // 로그인 화면으로 이동
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };
~~~   
<img src="https://user-images.githubusercontent.com/62936197/86549388-79e22300-bf7a-11ea-8504-d6576d6257b2.png" width="30%">   
                                                                                                                       
>>#### 2-1-3 로그인
1)회원가입 시에 입력한 이메일과 비밀번호를 입력하여 로그인을 진행한다.   
<img src="https://user-images.githubusercontent.com/62936197/86550056-5324ec00-bf7c-11ea-86d6-bfb4b3b7c1d1.png" width="30%">          
2)Firebase의 Auth에 저장된 값과 비교하여 입력한 값이 일치하면 로그인이 성공되고 메뉴 화면으로 전환된다.   

~~~java                                                                                                                      
public class MainActivity extends AppCompatActivity {
                                                                                                                       
    // 비밀번호 정규식
    Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
                                                                                
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    
    // 작성한 이메일 값과 비밀번호 값을 저장할 객체 생성
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    private String password = "";
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        // id가 write_email인 editText에 대한 메서드 저장
        editTextEmail = findViewById(R.id.et_eamil);
        // id가 signup_password인 editText에 대한 메서드 저장
        editTextPassword = findViewById(R.id.et_password);

        // 회원가입 버튼 객체 생성
        Button signup_btn = findViewById(R.id.btn_signUp);
        // 비밀번호 재설정 버튼 객체 생성
        Button findpw_btn = findViewById(R.id.btn_findpw);
        // 이메일 찾기 버튼 객체 생성
        Button findid_btn = findViewById(R.id.btn_findid);
        }
     
     // 이메일 로그인 메서드
     public void signInemail(View view) {
     // editText에 작성한 내용을 String으로 변환하여 객체에 저장
     email = editTextEmail.getText().toString();
     password = editTextPassword.getText().toString();
     // 유효성 검사 후 로그인 메서드 실행
     if(isValidEmail() && isValidPasswd()) {
     loginUser(email, password);
     }
     }
~~~   
##### 이메일 로그인 메서드   
~~~java
// 로그인 메서드
    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                // 로그인에 성공하면 "로그인 성공" 토스트를 보여줌
                                Toast.makeText(MainActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                // MenuActivity로 화면 전환
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"이메일 인증을 완료해주세요.",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // 로그인에 실패하면 "로그인 실패" 토스트를 보여줌
                            Toast.makeText(MainActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
~~~   
5)로그인을 진행하지 않아도 어플을 사용할 수 있으나 게시판 이용 및 회원정보 열람은 로그인에 성공하지 못하면 이용할 수 없다.  
>>#### 2-1-4 Google 로그인
계정 생성을 통한 로그인 외에 구글 아이디를 이용한 로그인 방법을 추가하였다.   
사용자는 자신의 Google 아이디를 이용하여 어플에 로그인을 진행할 수 있으며 Google 로그인을 성공적으로 진행한 사용자는 게시판 이용 또한 가능해진다.
~~~java
public class MainActivity extends AppCompatActivity {
    // 구글 로그인 객체 생성
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         // 구글 로그인
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // 구글 로그인 버튼  onClicklistener 생성
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.e("구글 로그인","버튼 클릭");
                // 클릭시 구글 로그인 메서드 실행
                signIn();
            }
        });
        }
~~~
#####  Google 로그인 메서드
~~~java
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.e("구글 로그인","메서드 실행");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("구글 로그인","result 메서드");
        // RC_SIGN_IN을 통해 로그인 확인여부 코드가 저상 전달되었다면
        if (requestCode == RC_SIGN_IN) {
            Log.e("구글 로그인","로그인 여부");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 구글 로그인이 성공하면, 파이어베이스에 로그인 인증 등록
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                // 구글 이용자가 확인된 사용자 정보를 파이어베이스로 넘기기
                firebaseAuthWithGoogle(account);
                Log.e("구글 로그인","구글 로그인 성공");
            } catch (ApiException ignored) {
                Log.e("구글 로그인","구글 로그인 실패");
            }
        }
    }
~~~
#####  Firebase와 Google 로그인 연결
~~~java
 // 파이어베이스와 구글 로그인 연결
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e("구글 로그인","파이어베이스랑 연결 중");
        // 파이어베이스로 받은 구글 사용자가 확인된 이용자의 값을 토큰으로 받음
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인에 성공하면 "로그인 성공" 토스트를 보여줌
                            Toast.makeText(MainActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // 로그인에 실패하면 "로그인 실패" 토스트를 보여줌
                            Toast.makeText(MainActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
~~~
>>#### 2-1-5 아이디 찾기 및 비밀번호 재설정
1)사용자가 자신의 아이디를 잊었다면 회원가입 시 사용한 이름과 전화번호를 통해 사용자의 아이디를 찾을 수 있게 한다.   
회원가입으로 인해 Firebase에 저장된 사용자의 이름, 전화번호 데이터와 아이디를 찾기 위해 Edittext에 입력한 이름, 전화번호 값이 모두 일치해야 사용자에게 이메일을 보여준다.   
~~~java
public class FindIdActivity extends AppCompatActivity {
    // 파이어베이스 인증 객체 생성
    private FirebaseFirestore firebaseFirestore;
    
    // 작성한 이름 값과 전화번호 값을 저장할 객체 생성
    private EditText editTextName;
    private EditText editTextPhone;
    private String sendname = "";
    private String sendphone = "";

    // 회원의 이메일을 보여줄 textview 객체 생성
    private TextView textshowid;
    private TextView textshowtext;

    // 로그인 화면으로 돌아가기 위한 버튼 객체 생성
    private Button btngotologin;

    // 사용자에게 실시간 진행상태를 알려주는 ProgressDialog 객체 생성
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);

        // id가 write_email인 editText에 대한 메서드 저장
        editTextName = findViewById(R.id.write_name);
        // id가 write_phone인 editText에 대한 메서드 저장
        editTextPhone = findViewById(R.id.write_phone);
        // id가 tv_showid인 textview에 대한 메서드 저장
        textshowid = findViewById(R.id.tv_showid);
        // id가 tv_showtext인 textview에 대한 메서드 저장
        textshowtext = findViewById(R.id.tv_showtext);

        // 로그인 화면으로 돌아가는 버튼에 대한 메서드 저장
        btngotologin = findViewById(R.id.btn_gotologin);

        // progressDialog 객체 선언
        progressDialog = new ProgressDialog(this);

        btngotologin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //로그인으로 돌아가기 버튼을 누르면 MainActivity로 이동
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
~~~
#####  아이디 찾기 버튼 클릭시
~~~java
    public void findEmail(View view)
    {
        // editText에 작성한 내용을 String으로 변환하여 객체에 저장
        sendname = editTextName.getText().toString();
        sendphone = editTextPhone.getText().toString();
        // findid 메서드 실행
        findid(sendname, sendphone);
    }
~~~
#####  findid 메서드
~~~java
    private void findid(final String sendname, final String sendphone) {
        // 프로그레스 디이얼로그 생성하여 보여줌
        progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
        progressDialog.show();
        // 파이어스토어 가져옴
        firebaseFirestore = FirebaseFirestore.getInstance();
        // 파이어스토어의 collection 경로를 "users"로 생성
        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 파이어스토어의 "users"에서 정보를 가져오는 것이 성공하면
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                // doument의 id와 입력한 이름이 같고 저장된 휴대폰번호와 입력한 휴대폰 번호가 같으면 입력한 이름의 저장된 이메일을 보여줌
                                if (sendname.equals(document.getData().get("name")) && sendphone.equals(document.getData().get("phone"))) {
                                    textshowtext.setText("회원님의 이메일은 다음과 같습니다.");
                                    textshowid.setText(document.getData().get("email").toString());
                                    // 로그인 화면으로 가는 버튼을 VISIBLE 처리하여 보여줌
                                    btngotologin.setVisibility(View.VISIBLE);
                                    break;
                                }
                                // 입력한 정보와 파이어베이스에 저장된 정보가 다르면 일치하는 회원정보가 없다는 텍스트를 보여줌
                                else {
                                    textshowtext.setText("일치하는 회원정보가 없습니다.");
                                    textshowid.setText("");
                                }
                            }
                        }
                        // 프로그래스 다이얼로그 사라짐
                        progressDialog.dismiss();
                    }
                });
    }
}
~~~
<img src="https://user-images.githubusercontent.com/62936197/86550165-a4cd7680-bf7c-11ea-9acf-818212ebd9d8.png" width="30%">   
2)비밀번호를 잊었을 경우 아이디로 사용하는 이메일을 입력하면 Firestore에 저장된 이메일 값과 비교 후, 일치하는 이메일 값이 있다면 해당하는 이메일로 비밀번호를 재설정할 수 있는 메일을 전송한다.   

~~~java
public class FindpwActivity extends AppCompatActivity {
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 작성한 이메일 값을 저장할 객체 생성
    private EditText editTextEmail;
    private String sendemail = "";

    // 사용자에게 실시간 진행상태를 알려주는 ProgressDialog 객체 생성
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        // id가 write_email인 editText에 대한 메서드 저장
        editTextEmail = findViewById(R.id.write_email);

        // progressDialog 객체 선언
        progressDialog = new ProgressDialog(this);
    }
~~~
#####  비밀번호 재전송 버튼 클릭시
~~~java
    public void findPw(View view)
    {
        // editText에 작성한 내용을 String으로 변환하여 객체에 저장
        sendemail = editTextEmail.getText().toString();

        // 이메일 유효성 검사 후 findpw 메서드 실행
        if(isValidEmail()){
            findpw(sendemail);
        }
    }
~~~
#####  findpw 메서드
~~~java
    private void findpw(String sendemail) {
        // 프로그래스 다이얼로그 생성하여 보여줌
        progressDialog.setMessage("처리중입니다. 잠시 기다려 주세요...");
        progressDialog.show();
        //비밀번호 재설정 이메일 보내기
        firebaseAuth.sendPasswordResetEmail(sendemail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // 이메일이 정상적으로 전송됐으면 "이메일을 발송하였습니다." 토스트를 보여줌
                            Toast.makeText(FindpwActivity.this, R.string.emailsending, Toast.LENGTH_LONG).show();
                            // 버튼을 누르면 메인화면으로 이동
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            // 이메일을 정상적으로 보낸 후 로그인 화면으로 이동
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // 이메일이 정상적으로 전송되지 않았다면 "이메일 발송에 실패하였습니다." 토스트를 보여줌
                            Toast.makeText(FindpwActivity.this, R.string.emailfail, Toast.LENGTH_LONG).show();
                        }
                        // 프로그래스 다이얼로그 사라짐
                        progressDialog.dismiss();
                    }
                });
    }
}
~~~  
<img src="https://user-images.githubusercontent.com/62936197/86553548-1100a800-bf86-11ea-9f8a-858e6a56c99e.png" width="50%">     
3)사용자는 해당 메일을 통해 비밀번호를 재설정할 수 있으며 이후 재설정한 비밀번호로 로그인을 진행한다.      
<img src="https://user-images.githubusercontent.com/62936197/86553483-ced76680-bf85-11ea-86a1-750e9f48279a.png" width="30%">   

>>#### 2-1-6 회원정보 수정   
계정을 생성하여 어플에 성공적으로 로그인을 한 사용자는 ‘마이페이지’탭에서 회원정보를 수정할 수 있다. 회원정보를 열람할 수 있는 탭은 Fragment로 구성하였다.     
1)탭에 방문할 때에는 이메일을 한 번 더 입력해야 하며 입력한 값이 현재 로그인 중인 사용자의 이메일 값과 일치할 경우 회원정보 및 정보 수정을 위한 버튼을 보여준다.     
이메일 값이 일치하지 않을 경우 회원정보가 보여 지지 않으며 로그인을 하지 않은 사용자가 이 탭에 방문할 때는 ‘로그인 후 이용해주세요.’라는 Text를 보여준다.   

~~~java
public class FragmentInfo extends Fragment {
    // 파이어베이스 사용자 객체 생성
    private FirebaseUser user;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    // 파이어스토어 인증 객체 생성
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // 작성한 이메일 값과 비밀번호 값을 저장할 객체 생성
    private TextView editTextEmail;
    private TextView editTextPassword;

    // 작성한 이름 값과 전화번호 값을 저장할 객체 생성
    private TextView editTextName;
    private TextView editTextPhone;

    // 로그인이 되어있지 않을 경우 보여주는 텍스트뷰 객체 생성
    private TextView tv_beforelogin;

    // 수정 버튼 객체 생성
    private ImageButton btn_modifypw;
    private ImageButton btn_modifypn;
    private ImageButton btn_modifyname;
    // 새로고침 버튼 객체 생성
    private ImageButton btn_refresh;

    // 새로고침 버튼 클릭시 이메일 입력 다이얼로그가 재생성되는 것을 방지하기 위하여 count라는 변수 생성하여 0을 기본값으로 설정함
    private int count=0;

    public FragmentInfo() {
    }

    // 로그인 소식을 듣고 다음단계로 넘겨주는 역할인 firebaseAuthListener을 선언을 onStart에 넣어줌
    @Override public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // firebaseAuthListener
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // 현재 로그인 중인 사용자를 가져옴
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // 로그인한 사용자가 있는 경우
                if (user != null) {
                    tv_beforelogin.setText("");
                    // 기본값인 count=0일 경우 이메일 입력 다이얼로그를 보여주고 count값을 1로 바꿔줌
                    if(count==0){
                        showDialog();
                        count =1;
                    }
                    else{
                        // 다이얼로그 인증을 끝낸 후 새로고침을 했을 경우 다이얼로그 없이 바로 수정된 회원정보를 보여줌
                        showInfo();
                    }
                }
                // 로그인한 사용자가 없는 경우
                else {
                    tv_beforelogin.setText("로그인 후 이용해주세요.");
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // id가 write_email인 editText에 대한 메서드 저장
        editTextEmail = view.findViewById(R.id.write_email);
        // id가 signup_password인 editText에 대한 메서드 저장
        editTextPassword = view.findViewById(R.id.signup_password);
        // id가 write_name인 editText에 대한 메서드 저장
        editTextName = view.findViewById(R.id.write_name);
        // id가 write_phone인 editText에 대한 메서드 저장
        editTextPhone = view.findViewById(R.id.write_phone);
        // id가 tv_beforeLogin인 textview에 대한 메서드 저장
        tv_beforelogin = view.findViewById(R.id.tv_beforelogin);

        // id가 modifybutton인 버튼에 대한 메서드 저장
        btn_modifypw = view.findViewById(R.id.modifybutton);
        btn_modifypn = view.findViewById(R.id.modifybutton2);
        btn_modifyname = view.findViewById(R.id.modifybutton3);

        // id가 refreshButton인 버튼에 대한 메서드 저장
        btn_refresh = view.findViewById(R.id.refreshButton);

        // 이메일 일치여부가 성공하기 전에 회원 정보를 보여줄 editText를 모두 빈칸 처리해줌
        editTextEmail.setText("");
        editTextName.setText("");
        editTextPhone.setText("");
        editTextPassword.setText("");
        tv_beforelogin.setText("로그인 후 이용해주세요.");

        // 수정 이미지 버튼과 새로고침 버튼을 GONE하여 버튼과 그 공간까지 보이지 않게 처리
        // 이메일 일치여부가 성공하여 수정할 수 있는 조건이 되면 VIDIBLE하여 보여줄 예정
        btn_modifypw.setVisibility(View.GONE);
        btn_modifypn.setVisibility(View.GONE);
        btn_modifyname.setVisibility(View.GONE);
        btn_refresh.setVisibility(View.GONE);

        // 비밀번호 수정 이미지 버튼 클릭 시
        btn_modifypw.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // modifyDialog 호출
                ModifyDialog modifyDialog = new ModifyDialog();
                modifyDialog.show(requireActivity().getSupportFragmentManager(), "tag");
            }
        });

        // 전화번호 수정 이미지 버튼 클릭 시
        btn_modifypn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // modifyPhoneDialog 호출
                ModifyPhoneDialog modifyPhoneDialog = new ModifyPhoneDialog();
                modifyPhoneDialog.show(requireActivity().getSupportFragmentManager(), "tag");
            }
        });

        // 이름 수정 이미지 버튼 클릭 시
        btn_modifyname.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // modifyNameDialog 호출
                ModifyNameDialog modifyNameDialog = new ModifyNameDialog();
                modifyNameDialog.show(requireActivity().getSupportFragmentManager(), "tag");
            }
        });

        // 새로고침 이미지 버튼 클릭 시
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // refresh 메서드 실행
                refresh();
            }
        });

        return view;
    }
~~~
##### 정보 수정 후 새로고침 버튼 클릭시 화면이 갱신되어 수정된 정보로 보여주는 refresh 메서드

~~~java
    // fragment 화면 갱신 메서드
    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }
~~~
##### 회원정보 열람 화면 호출할 이메일 입력 다이얼로그

~~~java
    private void showDialog(){
        // 입력하는 이메일 값을 저장할 변수
        final EditText edittext = new EditText(getActivity());
        // 다이얼로그 호출
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("회원정보 열람을 위해 이메일을 다시 한 번 입력해주세요.")
                .setMessage("(구글 로그인 회원은 회원정보를 제공하지 않습니다.)")
                .setView(edittext)  // 이메일을 입력하기 위한 edittext
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 로그인 중인 사용자를 불러옴
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        // 다이얼로그에 입력한 이메일 값
                        final String email = edittext.getText().toString();
                        // 로그인 중인 사용자의 이메일 값을 checkmail이라는 string 변수값에 넣어줌
                        assert user != null;
                        String checkmail = user.getEmail();
                        // 다이얼로그에 입력한 이메일 값과 로그인 중인 사용자의 이메일 값 비교
                        // 같을 때
                        if(email.equals(checkmail)){
                            Toast.makeText(getActivity(),"이메일 확인 성공", Toast.LENGTH_SHORT).show();
                            // "확인" 버튼 클릭시 info 메서드 호출
                            // 입력한 이메일 값을 파이어스토어의 이메일 값과 비교해야하기 때문에 info메서드에 email값을 넣어줌
                            info(email);
                        }
                        // 입력한 값이 없이 '확인'버튼을 누를 경우
                        else if(email.getBytes().length<1){
                            Toast.makeText(getActivity(),"이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        // 다이얼로그에 입력한 이메일 값과 로그인 중인 사용자의 이메일 값이 다를 때
                        else{
                            Toast.makeText(getActivity(),"이메일이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
~~~
<img src="https://user-images.githubusercontent.com/62936197/86555561-af433c80-bf8b-11ea-942f-00ecb48f5b0c.png" width="30%">      

##### 이메일 인증 다이얼로그로 인증이 완료된 후 새로고침을 했을 때 다이얼로그 없이 보여주기 위해 회원정보가 저장된 텍스트를 바로 띄워주기 위한 showInfo 메서드 

~~~java
    private void showInfo(){
        // 현재 로그인이 되어있는 사용자를 가져옴
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // firestore의 collection 경로를  "users"로 설정
        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 파이어스토어에서 데이터를 가져오는 것을 성공했을 때
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                assert user != null;
                                // 로그인 중인 사용자의 이메일과 파이어베이스의 이메일이 같을 때
                                if(user.getEmail().equals(document.getData().get("email"))) {
                                    // editText에 파이어스토에 저장된 값을 setText해줌
                                    editTextEmail.setText(document.getData().get("email").toString());
                                    editTextName.setText(document.getData().get("name").toString());
                                    editTextPhone.setText(document.getData().get("phone").toString());
                                    editTextPassword.setText(document.getData().get("password").toString());
                                    tv_beforelogin.setText("");
                                    // 수정버튼과 새로고침 버튼 VISIBLE 처리하여 보여줌
                                    btn_modifypw.setVisibility(View.VISIBLE);
                                    btn_modifypn.setVisibility(View.VISIBLE);
                                    btn_modifyname.setVisibility(View.VISIBLE);
                                    btn_refresh.setVisibility(View.VISIBLE);

                                }

                            }
                        }
                    }
                });
    }
~~~
##### 회원정보를 보여줄 info 메서드 
~~~java
    private void info(final String string){
        // firestore의 collection 경로를  "users"로 설정
        firebaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 파이어스토어에서 데이터를 가져오는 것을 성공했을 때
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                assert user != null;
                                // 입력한 string값과 파이어베이스의 이메일 비교하여 같을 때 처리
                                if(string.equals(document.getData().get("email"))) {
                                    // editText에 파이어스토에 저장된 값을 setText해줌
                                    editTextEmail.setText(document.getData().get("email").toString());
                                    editTextName.setText(document.getData().get("name").toString());
                                    editTextPhone.setText(document.getData().get("phone").toString());
                                    editTextPassword.setText(document.getData().get("password").toString());
                                    tv_beforelogin.setText("");
                                    // 수정버튼과 새로고침 버튼 VISIBLE 처리하여 보여줌
                                    btn_modifypw.setVisibility(View.VISIBLE);
                                    btn_modifypn.setVisibility(View.VISIBLE);
                                    btn_modifyname.setVisibility(View.VISIBLE);
                                    btn_refresh.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });
    }
}
~~~
<img src="https://user-images.githubusercontent.com/62936197/86555628-dc8fea80-bf8b-11ea-93d5-ce0479983e5b.png" width="30%">     
2)회원정보는 Firestore에 저장된 이메일, 이름, 전화번호, 비밀번호를 보여주되, 이메일은 수정이 불가하며 그 외의 항목 옆에 있는 수정버튼 클릭 시 Dialog로 수정할 값을 입력할 수 있다.   
수정할 값들 또한 회원가입 시와 동일하게 유효성 검사를 진행하고, 입력한 값들이 모두 유효하면 Firebase의 Auth와 Firestore에 변경한 값으로 데이터를 업데이트 해준다.   

##### 이름 수정 다이얼로그

~~~java
public class ModifyNameDialog extends DialogFragment {
    private Fragment fragment;

    // 파이어스토어에 저장된 "name"이라는 이름의 값을 pass_key라는 문자에 저장
    private static final String pass_key = "name";

    // 파이어스토어 인증 객체 생성
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // 다이얼로그의 확인 버튼과 취소 버튼 객체 생성
    private Button positivebutton;
    private Button negativebutton;

    // 다이얼로그에 입력하는 name 객체 생성
    private EditText name;

    // 새롭게 저장할 name 값 객체 생성
    String newName ="";

    public ModifyNameDialog(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modify3_dialog, container, false);

        // 레이아웃에 postivebutton이라는 버튼 값과 negativebutton이라는 버튼 값 저장
        positivebutton = view.findViewById(R.id.positivebutton);
        negativebutton = view.findViewById(R.id.negativebutton);

        // 다이얼로그에 작성하는 name값 저장
        name = view.findViewById(R.id.write_name);

        // 취소 버튼 클릭시
        negativebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 다이얼로그 사라짐
                getDialog().dismiss();
            }
        });

        // 확인 버튼 클릭시
        positivebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 로그인 중인 사용자 가져오기기
               final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // 로그인 상태일 경우
                if(user != null) {
                    // 다이얼로그에 작성한 name값을 string으로 처리하여 샤로운 name값인 newName에 저장
                    newName = name.getText().toString();
                    if (isValidName()) {
                        // 파이어스토어 collection 경로를 "uesrs"로 하고 로그인 중인 유저의 email을 documentID로 하는 정보를 가져옴
                        DocumentReference contact = firebaseFirestore.collection("users").document(user.getEmail());
                        // 새로운 name값으로 업데이트
                        contact.update(pass_key, newName)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // 업데이트 성공시 다이얼로그 사라짐
                                        getDialog().dismiss();
                                    }
                                });
                    }
                }
            }

        });
        return view;
    }
    // 이름 유효성 검사
    private boolean isValidName() {
        if (newName.isEmpty()) {
            // 이름 칸이 공백이면 false
            Toast.makeText(getActivity(), R.string.plzinputname, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
~~~
##### 비밀번호 수정 다이얼로그

~~~java
public class ModifyDialog extends DialogFragment {
    private Fragment fragment;

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어스토어에 저장된 "password"이라는 이름의 값을 pass_key라는 문자에 저장
    private final String pass_key = "password";

    // 파이어스토어 인증 객체 생성
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // 다이얼로그에 입력하는 password 객체 생성
    private EditText password;

    // 새롭게 저장할 password 값 객체 생성
    private String newPassword ="";

    public ModifyDialog(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modify_dialog, container, false);

        // 레이아웃에 postivebutton이라는 버튼 값과 negativebutton이라는 버튼 값 저장
        // 다이얼로그의 확인 버튼과 취소 버튼 객체 생성
        Button positivebutton = view.findViewById(R.id.positivebutton);
        Button negativebutton = view.findViewById(R.id.negativebutton);

        // 다이얼로그에 작성하는 password값 저장
        password = view.findViewById(R.id.signup_password);

        // 취소 버튼 클릭시
        negativebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 다이얼로그 사라짐
                getDialog().dismiss();
            }
        });

        //확인 버튼 클릭시
        positivebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 로그인 중인 사용자 가져오기
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // 로그인 상태일 경우
                if(user != null) {
                    // 다이얼로그에 작성한 password값을 string으로 처리하여 새로운 password값인 newPassword에 저장
                    newPassword = password.getText().toString();
                    // 입력한 비밀번호의 유효성 검사
                    if (isValidPasswd()) {
                        // 로그인 중인 사용자의 password를 newPassword로 업데이트
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // 업데이트에 성공하면 파이어스토어에 저장된 password도 업데이트
                                            // 파이어스토어 collection의 경로를 "users"로 하고 로그인 중인 유저의 email을 documentID로 하는 정보를 가져옴
                                            DocumentReference ref = firebaseFirestore.collection("users").document(user.getEmail());
                                            // 새로운 password값으로 업데이트
                                            ref.update(pass_key, newPassword)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // 업데이트 성공시 다이얼로그 사라짐
                                                            getDialog().dismiss();
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                }
            }
        });
        return view;
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (newPassword.isEmpty()) {
            // 비밀번호 칸이 공백이면 false
            Toast.makeText(getActivity(),R.string.plzinpytpassword, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            // 비밀번호 형식이 불일치하면 false
            Toast.makeText(getActivity(), R.string.notvalidpassword, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
~~~
##### 전화번호 수정 다이얼로그

~~~java
public class ModifyPhoneDialog extends DialogFragment {
    private Fragment fragment;

    // 파이어스토어에 저장된 "phone"이라는 이름의 값을 pass_key라는 문자에 저장
    private static final String pass_key = "phone";

    // 파이어스토어 인증 객체 생성
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // 전화번호 정규식
    public static final Pattern PHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", Pattern.CASE_INSENSITIVE);

    // 다이얼로그의 확인 버튼과 취소 버튼 객체 생성
    private Button positivebutton;
    private Button negativebutton;

    // 다이얼로그에 입력하는 phone 객체 생성
    private EditText phone;

    // 새롭게 저장할 phone 값 객체 생성
    String newPhone ="";

    public ModifyPhoneDialog(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modify2_dialog, container, false);

        // 레이아웃에 postivebutton이라는 버튼 값과 negativebutton이라는 버튼 값 저장
        positivebutton = view.findViewById(R.id.positivebutton);
        negativebutton = view.findViewById(R.id.negativebutton);

        // 다이얼로그에 작성하는 phone값 저장
        phone = view.findViewById(R.id.write_phone);

        // 취소 버튼 클릭시
        negativebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 다이얼로그 사라짐
                getDialog().dismiss();
            }
        });

        // 확인 버튼 클릭시
        positivebutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // 로그인 중인 사용자 가져오기
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // 로그인 상태일 경우
                if(user != null) {
                    // 다이얼로그에 작성한 phone값을 string으로 처리하여 새로운 phone값인 newPhone에 저장
                    newPhone = phone.getText().toString();
                    // 전화번호 유효성 검사
                    if (isValidPhone()) {
                        // 파이어스토어 collection 경로를 "uesrs"로 하고 로그인 중인 유저의 email을 documentID로 하는 정보를 가져옴
                        DocumentReference contact = firebaseFirestore.collection("users").document(user.getEmail());
                        // 새로운 phone값으로 업데이트
                        contact.update(pass_key, newPhone)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // 업데이트 성공시 다이얼로그 사라짐
                                        getDialog().dismiss();
                                    }
                                });
                    }
                }
            }
        });
        return view;
    }
    // 전화번호 유효성 검사
    private boolean isValidPhone() {
        if (newPhone.isEmpty()) {
            // 전화번호 칸이 공백이면 false
            Toast.makeText(getActivity(), "변경할 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PHONE_PATTERN.matcher(newPhone).matches()) {
            // 전화번호 형식이 불일치하면 false
            Toast.makeText(getActivity(), R.string.notvalidphone, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    }
~~~
<img src="https://user-images.githubusercontent.com/62936197/86555739-2f69a200-bf8c-11ea-8c39-da34b681ac41.png" width="30%">   
*****
>### 2-2 약국 찾기
주변 약국을 찾기 위해 사용하는 지도와 주변 약국의 위치를 사용하기 위해 Google Map과 Google Place를 사용한다.   

##### Google map과 Google Place를 사용하기 위한 초기작업   
1)Google Developers Console사이트(https://console.developers.google.com/apis/dashboard)에 접속하여 새 프로젝트를 생성한다.   
<img src="https://user-images.githubusercontent.com/57400913/86548778-967d5b80-bf78-11ea-874e-6af154fa64f7.png" width="70%">

2)사용자 인증 정보 만들기 - API 키 - 생성 완료
<img src="https://user-images.githubusercontent.com/57400913/86549992-21ac2080-bf7c-11ea-8031-e9bbc339137e.png" width="70%">   


3)API 라이브러리에서 Maps SDK for Android와 Place API을 사용설정 한다.
<img src="https://user-images.githubusercontent.com/57400913/86551257-887f0900-bf7f-11ea-8d94-6ea94830123a.png" width="70%">   
          
4)Google Service를 사용하기 위해서 Google Play services 라이브러리 패키지를 설치해줘야 한다.   
안드로이드 스튜디오 - Tools - SDK Manager - SDK Tools탭 클릭 - Google Play services체크 - Apply   
<img src="https://user-images.githubusercontent.com/57400913/86551723-e06a3f80-bf80-11ea-98ca-94354ef16e20.png" width="70%"> 

5)AndroidManifest.xml파일의 <application>태그의 하위 요소로 추가한다.   
  2)에서 발급받은 API키를 추가한다.
~~~jave
<meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="발급받은 API키"/>
~~~   

6)Gradle Scripts - Module app의 build.gradle에 라이브러리를 사용하기 위해 추가한다.   
추가 후 - Sync Now 클릭
~~~java
dependencies {
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'noman.placesapi:placesAPI:1.1.3'
    }
   ~~~   
   
7)MapMainActivity.java를 만들고 지도를 생성해준다.
~~~java
package com.example.androidlogin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MapMainActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, PlacesListener {
    PharmParser parser = new PharmParser();
    String data;
    private GoogleMap mMap;
    private Marker currentMarker = null;
    Button handle_btn;
    EditText edit;
    String getedit; //약국 동이름으로 검색하는 edittext

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 1000; //권한 설정을 한 activity에 request값으로 받아올 변수 설정
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;


    // 앱을 실행하기 위해 필요한 퍼미션 정의
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소

    Location mCurrentLocatiion;
    LatLng currentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    private View mLayout;  // Snackbar 사용하기 위해서 View가 필요
    List<Marker> previous_marker = null; //google place에서 얻어온 약국 마커 표시

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.map_activity_main);

        previous_marker = new ArrayList<Marker>();

        handle_btn = (Button) findViewById(R.id.handle);
        edit = (EditText) findViewById(R.id.edit);

        //약국 찾기 버튼 눌렀을때(마커 생성)
        Button button = (Button)findViewById(R.id.pharm_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlaceInformaiton(currentPosition);
            }
        });

        mLayout = findViewById(R.id.layout_main);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        parser.edit = (EditText)findViewById(R.id.edit);
        parser.text = (TextView)findViewById(R.id.result);

        // 홈으로 이동하는 버튼 객체 생성
        ImageButton btn_home = findViewById(R.id.gohome);

        // 홈 버튼 onclicklistener 생성
        btn_home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 버튼을 누르면 메인화면으로 이동
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

        });

    }
~~~   

>>#### 2-2-1 위치 관련 퍼미션 허용과 GPS 활성화   
##### 위치 관련 퍼미션 허용 여부 검사   
~~~java
@Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 퍼미션 허용됐는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                // 퍼미션을 허용했다면 위치 업데이트를 시작
                startLocationUpdates();
            }

            else {

                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료함
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    //사용자가 퍼미션을 거부했을때 뜨는 메시지
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();

                }else {

                    //사용자가 "다시 묻지 않음"을 누르고 퍼미션을 거부하면 설정-앱 정보에서 퍼미션을 허용해야 사용 가능함을 알림
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 활성화하려면 설정-앱 정보 에서 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }
 ~~~   
 
##### GPS활성화 여부 검사 
~~~java
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MapMainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
 ~~~   

 1)처음 실행하면 현재 위치를 찾는데 시간이 걸리기 때문에 초기 위치를 서울역으로 지정해준다.   
 ##### 지도 실행시 초기 위치로 
 ~~~java
 public void setDefaultLocation() {

        //디폴트 위치, 부산역으로 지정
        LatLng DEFAULT_LOCATION = new LatLng(37.553321, 126.972627); //부산역의 위도, 경도
        String markerTitle = "부산역";

        if (currentMarker != null) currentMarker.remove();


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15); //zoom 설정 1~21까지
        mMap.moveCamera(cameraUpdate);

    }
~~~

 2)위치 퍼미션과 GPS 활성화 여부를 검사한 후에 모두 활성화 되어있다면 위치 업데이트를 시작한다.    
 ##### 위치 업데이트    
 ~~~java
  @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        //런타임 퍼미션 처리
        // 위치 퍼미션을 가지고 있는지 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            startLocationUpdates(); // 이미 퍼미션 가지고 있다면 위치 업데이트 시작
        }
        else {  //퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요.

            // 사용자가 퍼미션 거부를 한 적이 있는 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                //요청을 진행하기 전에 사용자에게 접근 권한이 필요함을 알림
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        //사용자게에 퍼미션 요청을 함 요청 결과는 onRequestPermissionResult에서 수신
                        ActivityCompat.requestPermissions(MapMainActivity.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();

            } else {
                //사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 함
                //요청 결과는 onRequestPermissionResult에서 수신
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
        //결과 맵에 띄우기
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                Log.d( TAG, "onMapClick :");
            }
        });
    }
 ~~~ 
 
<div>
<img src="https://user-images.githubusercontent.com/57400913/86553887-f5e26800-bf86-11ea-9c12-fc412c5b63fc.png" width="30%">       
<img src="https://user-images.githubusercontent.com/57400913/86553418-a8193000-bf85-11ea-8c6d-da8c8a4f3d24.png" width="30%">
<img src="https://user-images.githubusercontent.com/57400913/86553428-b1a29800-bf85-11ea-9750-3571cd1ebdb2.png" width="30%">
</div>   
   
   
>>#### 2-2-2 사용자의 현재 위치 확인
##### GPS서비스 상태 파악 후 현재 위치 업데이트 
~~~java
 private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {
            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();//gps비활성화 상태일때 서비스 세팅 메서드 호출
        }
        else {
            //gps활성화 되어있으면 퍼미션 확인
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {
                return;
            }

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);

        }
    }
~~~   
##### Geocoder로 GPS를 주소로 변환   
~~~java
 public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }
    }
 ~~~   
    
##### 현재 위치에 마커 생성하기   
~~~java
public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) currentMarker.remove();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentMarker = mMap.addMarker(markerOptions);
    }
~~~

<div>
<img src="https://user-images.githubusercontent.com/57400913/86555113-79ea1f00-bf8a-11ea-8ae5-dce9c8927e97.png" width="30%">       
<img src="https://user-images.githubusercontent.com/57400913/86555039-40b1af00-bf8a-11ea-9188-8bb074153239.png" width="30%">
</div>   
   
   
>>#### 2-2-3 현재 위치를 기반으로 주변 약국 검색   
##### PlacesListener 인터페이스 구현   
MainActivity에서 PlacesListener를 구현해주고 인터페이스가 요구하는 메서드 4개를 추가한다.    
1)PlacesListener 인터페이스 구현
~~~java
public class MapMainActivity extends AppCompatActivity implements OnMapReadyCallback,   
ActivityCompat.OnRequestPermissionsResultCallback, PlacesListener {
~~~
     
2)PlacesListener 인터페이스가 요구하는 메서드 4개 Override로 추가
~~~java
    @Override
    public void onPlacesFailure(PlacesException e) { }

    @Override
    public void onPlacesStart() { }

    @Override //구글 플레이스에서 가져온 정보 지도에 표시하기
    public void onPlacesSuccess(final List<Place> places) { }

    @Override
    public void onPlacesFinished() { }
~~~

##### 주변 약국 마커 생성하기   
~~~java
public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (noman.googleplaces.Place place : places) {

                    LatLng latLng
                            = new LatLng(place.getLatitude()
                            , place.getLongitude());

                    String markerSnippet = getCurrentAddress(latLng);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(place.getName());
                    markerOptions.snippet(markerSnippet);

                    //약국 마커 아이콘 바꾸기
                    BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.marker6);
                    Bitmap b=bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 150, false);
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    Marker item = mMap.addMarker(markerOptions);

                    previous_marker.add(item);
                }

                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);

            }
        });

    }
~~~   
      
##### 현재 위치를 기반으로 주변 약국 찾기   
radius의 값으로 반경 2500m로 설정해주었기 때문에 현재 위치를 기반으로 2500m 근처에 있는 약국을 찾는다.   
내 주변 약국 찾기 버튼을 누를때마다 마커가 클리어되고 새롭게 찾아진 주변 약국 마커가 생성된다.   
~~~java
    public void showPlaceInformaiton(LatLng location)
    {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MapMainActivity.this)
                .key("발급받은 자신의 API 키") // Google API
                .latlng(location.latitude, location.longitude)//현재 위치
                .radius(2500)// 반경
                .type(PlaceType.PHARMACY) //약국
                .build()
                .execute();
    }
~~~
<div>
<img src="https://user-images.githubusercontent.com/57400913/86556093-40ff7980-bf8d-11ea-96c3-e2160409c832.png" width="30%">       
<img src="https://user-images.githubusercontent.com/57400913/86556107-4fe62c00-bf8d-11ea-8e5f-bf161c52c0d4.png" width="30%">
</div>   
   

>>#### 2-2-4 약국 파싱
1)공공데이터로 XML형태로 제공하는 전국 약국 정보를 파싱하기 위한 PharmParser.java 파일 만들기

##### 공공데이터 키 값 받기
<img src="https://user-images.githubusercontent.com/62935657/86558576-bcb0f480-bf94-11ea-88e1-6cdb04e78f6a.png" width="70%"></img>

활용신청을 눌러서 키값을 받는다.

<img src="https://user-images.githubusercontent.com/62935657/86558760-3812a600-bf95-11ea-8bb7-430bcc89fa83.png" width="70%"></img>


##### 공공데이터 파싱

2)인증키값을 queryURL를 만들어서 약국을 파싱해 올 수 있도록 한다. 
~~~java
XmlPullParser xpp;
    String key = "공공데이터 약국 키값 받기"; //약국 공공데이터 서비스키

    public String getXmlData() {
        StringBuffer buffer = new StringBuffer();

        String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
        String location = null;
        try {
            location = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String queryUrl = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyLcinfoInqire?serviceKey="//요청 URL
                + key +"&numOfRows=100" + "&emdongNm=" + location; //동 이름으로 검색
~~~
str변수에 edittext에 입력된 text를 얻어와서 encode한 후 location에 저장해준다.
요청변수+키값+페이지+동 태그+location한 Url을 만들어서, 동이름으로 약국을 파싱해올 수 있게한다. 

3)파싱해온 약국의 정보를 태그값에 따라서 주소, 약국, 전화번호를 가져오고 stringbuffer를 이용해서 저장해준다. 
~~~java
XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 태그값이랑 비교

                        else if (tag.equals("addr")) {
                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어오기
                            buffer.append("\n"); //줄바꿈
                        } else if (tag.equals("yadmNm")) {
                            buffer.append("약국명 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("telno")) {
                            buffer.append("전화번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("item"))
                            buffer.append("\n");// 첫번째 검색결과종료 후 줄바꿈
                        break;
                }
                break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("item"))
                            buffer.append("\n");// 첫번째 검색결과종료 후 줄바꿈
                        break;
                }

                eventType = xpp.next();
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
~~~

4)stringbuffer를 이용해서 문자열 객체를 반환한다.
~~~java
return buffer.toString();//StringBuffer 문자열 객체 반환
~~~




>>#### 2-2-5 ‘동이름’으로 약국 검색   

##### 검색 버튼 눌렀을 때
~~~java
public void mOnClick(View v){
        //edit 검색 후 키보드 숨기기
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(parser.edit.getWindowToken(),0);
        getedit = edit.getText().toString();
        if(getedit.getBytes().length <= 0)
        {
            Toast.makeText(getApplicationContext(),"검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } // edittext에 아무것도 입력하지 않고 검색 버튼을 눌렀을 경우
        
        else{
            handle_btn.setText("검색 결과를 보려면 위로 슬라이딩 해주세요.");
            switch (v.getId()){
                case R.id.serach_btn :
                    new Thread(new Runnable(){

                        @Override
                        public void run() {
                            data = parser.getXmlData();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    parser.text.setText(data);
                                }
                            });
                        }
                    }).start();
                    break;

            }

        }

    }
 ~~~
<div>
<img src="https://user-images.githubusercontent.com/57400913/86556685-fe3ea100-bf8e-11ea-97dc-850dda76d829.png" width="30%">
<img src="https://user-images.githubusercontent.com/57400913/86557019-eae00580-bf8f-11ea-8d11-b519cdf41e36.png" width="30%">       
</div>   


*****    
>### 2-3 사용자 후기 게시판
>>#### 2-3-1 firebase 연동   
firebase와 연동하는 방법은 회원가입 부분에서 설명한 방법과 동일하다.      
>>#### 2-3-2 게시물 등록     
recyclerView와 cardView를 이용하여 목록에서 firebase에 저장된 데이터들을 보여줄 것이다.    
    
1)getter와 setter를 정의해주는 ReviewPostInfo.java 파일 생성하기    
    
외부에서 접근할 때 객체의 무결성을 보장하기 위해 getter와 setter를 정의하였다  
게시글을 입력했을 때, 게시글의 제목과 내용, 등록한 날짜를 정의해준다.   

##### getter,setter 정의   
~~~java
import java.io.Serializable;
import java.util.Date;
//getter와 setter를 정의해주는 코드
public class ReviewPostInfo implements Serializable {
    //intent에서 putExtra로 보내주기 위해 implements Serializable가 사용됨.
    private String title;
    private String contents;
    private Date createdAt;
    private String id;
    
   public ReviewPostInfo(String title, String Contents, Date createdAt) {
        this.title = title;
        this.contents = Contents;
        this.createdAt = createdAt;
    }
    //id값을 불러올 때 사용되는 생성자함수
    public ReviewPostInfo(String title, String Contents, Date createdAt, String id) {
        this.title = title;
        this.contents = Contents;
        this.createdAt = createdAt;
        this.id = id;
    }
    //get set 메서드 이용
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContents() { 
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Map<String,Object> getPostInfo(){
        Map<String,Object> docData = new HashMap<>();
        docData.put("title",title);
        docData.put("contents",contents);
        docData.put("createdAt",createdAt);
        return docData;
    }
}
~~~
2)게시물을 입력할 ReviewWriteActivity.java 파일 생성    
위에서 정의해준 getter와 setter를 이용하여 사용자가 입력한 값을 ReviewPostInfo.java의 setter에 저장해준다.   
##### 게시물 입력시 setter에 저장     
~~~java
//text 업데이트를 위한 코드
    private void contentsUpdate() {
        //titleEditText과 contentEditText의 값을 받아서 string값으로 받아옴
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        final String contents = ((EditText) findViewById(R.id.contentEditText)).getText().toString();
        final Date date = reviewPostInfo2 ==null? new Date() : reviewPostInfo2.getCreatedAt();//날짜가 존재하지 않으면 현재 날짜를 불러오고, 수정 시 날짜가 존재하니까 그때는 그 날짜 그대로 유지시켜줌
        //제목과 글이 둘 다 입력 되었을 때 실행됨
        if(title.length() > 0 && contents.length()>0){
            loadrLayout.setVisibility(View.VISIBLE);
            ReviewPostInfo reviewPostInfo = new ReviewPostInfo(title, contents, date);
            uploader(reviewPostInfo);//값들이 postinfo로 들어와 uploader 메서드로 들어감
        }else {//그렇지 않으면 게시글을 입력해달라는 toast가 띄워짐
            startToast("게시글을 입력해주세요");
        }
    }
~~~
입력한 게시물의 내용을 setter를 이용하여 firebase에 저장해준다 
##### firebase에 저장해주는 코드   

~~~java
 private void uploader(ReviewPostInfo reviewPostInfo){
        firebaseFirestore = FirebaseFirestore.getInstance();

        //값이 null이면 앞에것을 반환.->게시물 등록 시 사용됨. null이 아니면 뒤에것을 반환
        final DocumentReference documentReference = reviewPostInfo2 ==null? firebaseFirestore.collection("posts").document()
                :firebaseFirestore.collection("posts").document(reviewPostInfo2.getId());
        //게시물에서 입력한 텍스트들을 받아와서 database의 document부분에 넣어줌.
        documentReference.set(reviewPostInfo.getPostInfo())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {//성공시
                        Log.d(TAG,"id");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override// 실패 시
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error",e);
                    }
                });
    }
~~~

3)firebase에 저장된 게시물을 목록으로 보여주기 위해 ReviewMainAdapter.java에 adapter를 정의해준다   
이 adpater는 recyclerView와 cardView를 이용한다.    
   
##### RecyclerView와 cardView를 이용하여 등록된 리뷰를 전체 리스트로 출력할것임. 그것을 위한 정의
~~~java

    static class MainViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        MainViewHolder(Activity activity, CardView v, ReviewPostInfo reviewPostInfo) {
            super(v);
            cardView = v;
        }
    }
~~~

##### 배열로 들어온 데이터들을 불러오는 작업.   
~~~java
ReviewMainAdapter(Activity activity, ArrayList<ReviewPostInfo> mDataset) {//생성자. 초기화해줌
    this.mDataset = mDataset;
    this.activity = activity;
}
~~~

##### RecyclerView와 cardView를 만들어주는 작업.    
보이는 부분만 load함.   
~~~java
    @NonNull
    @Override//
    public ReviewMainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout을 view객체로 만들기 위해 layoutInflater를 이용한다.
        final CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_post, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(activity, cardView, mDataset.get(viewType));//cardview가 하나하나 돌때, position값을 알기위해 viewType을 넣어 만듬.
        cardView.setOnClickListener(new View.OnClickListener() {//하나의 카드뷰를 클릭 시 intent로 해당하는 값을 ReviewActivityPost로넘겨줌.
            @Override
            public void onClick(View view) {
                //postInfo 데이터를 보내줘야 데이터를 가지고 레이아웃에 그려줌.
                Intent intent = new Intent(activity, ReviewActivityPost.class);
                intent.putExtra("postInfo", mDataset.get(mainViewHolder.getAdapterPosition()));//앞에는 key값, 뒤에는 실제 값
                //postInfo의 이름으로 intent를 보내 PostActivity에서 받아서 쓸수있게함
                activity.startActivity(intent);
            }
        });
        return mainViewHolder;
    }
~~~

##### 실제 db들의 값들을 넣어주는 작업.   
~~~java
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        

        //CardView에 firebase에 저장된 title값 넣어주기
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataset.get(position).getTitle());

        //게시물을 추가한 날짜 넣어주기
        TextView createTextView = cardView.findViewById(R.id.createdTextView);
        createTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));

        //contents값 넣어주기
        final TextView contentsTextView = cardView.findViewById(R.id.contentsTextView);
        contentsTextView.setText(mDataset.get(position).getContents());

        textEmail = cardView.findViewById(R.id.textView2);
        textEmail.setText(mDataset.get(position).getEmail());
    }

    @Override //자동 override됨. 데이터들의 수를 세줌.
    public int getItemCount() {
        return (mDataset != null ? mDataset.size() : 0);
    }
}
~~~

4)ReviewMainActivity.java에서 firebase에 저장된 데이터들을 위에서 정의된 adapter를 이용하여 넣어준다.    
Review의 목록을 보여줄 java 파일인 ReviewMainActivity.java 파일에 recyclerView를 정의해준다     
##### adapter에 데이터들을 넣어주는 코드    
~~~java
public class ReviewMainActivity extends AppCompatActivity {
    private  ArrayList<ReviewPostInfo> postList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ReviewMainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //이 파일에서는 review_activity_main.xml창을 보여줄것임.
        setContentView(R.layout.review_activity_main);
        fragmentMainMenu = new FragmentMainMenu();
        
        //recyclerView 초기화
        recyclerView = findViewById(R.id.recyclerView);//recyclerViewid연결
        recyclerView.setHasFixedSize(true);//recylerView 기존 성능 강화
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReviewMainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        postUpdate();
        findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);//게시글 추가 버튼을 클릭 시
    }
    //게시글 추가 버튼을 클릭할 때 처리하는 기능
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {//게시글 추가 버튼을 눌렀을 때 ReviewWriteActivity.java로 넘겨줌
            if (view.getId() == R.id.floatingActionButton) {
                myStartActivity(ReviewWriteActivity.class);
            }
         }
    };

 //실제 게시물을 보여주고 업데이트 해주는 코드
    public void postUpdate(){
        firebaseFirestore.collection("posts").orderBy("createdAt", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            postList = new ArrayList<>();//arraylist에 받아온값들을 다 넣어주어 보여줌
                            postList.clear();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId()+" => "+document.getData());
                                postList.add(new ReviewPostInfo(//데이터를 다 가져와 postList배열에 넣어줌.
                                        document.getData().get("title").toString(),
                                        document.getData().get("contents").toString(),
                                        new Date(document.getDate("createdAt").getTime()),
                                        document.getId()
                                ));//각 post들을 구분할 수 있게 하기 위해 post의id값을 얻어옴
                            }
                            //MainAdaper에서 넘겨줌.
                            mainAdapter = new ReviewMainAdapter(ReviewMainActivity.this, postList);
                            mainAdapter.setOnPostListener(onPostListener);//onPostListener를 넘겨주면 MainAdapter에서도 쓸수있음.
                            recyclerView.setAdapter(mainAdapter);
                            mainAdapter.notifyDataSetChanged();
                        }else {
                            Log.d(TAG, "Error : ",task.getException());
                        }

                    }
                });
}
~~~
 
5)게시글 자세히보기 기능    
##### 등록된 게시물은 다음 코드를 이용하여 내용의 일부만 보여주도록 구현함   

~~~java
<TextView
    android:id="@+id/titleTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:textColor="#000000"
    android:textSize="15sp"
    android:textStyle="bold"
    tools:text="@string/itemPostTitle"
    android:maxLines="1"
    android:ellipsize="end"/>
~~~
##### 이에 따라 게시글의 자세히보기 기능을 구현함.   
이는 해당 게시물을 클릭하면 게시글을 전체볼 수 있는 창으로 넘어가도록 구현한 것이다.   
먼저 adapter에서 해당하는 게시물을 클릭했을 때, intent를 이용하여 값들을 넘겨주었다.   
~~~java
@NonNull
    @Override//RecyclerView와 cardView를 만들어주는 작업. 보이는 부분만 load함.
    public ReviewMainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout을 view객체로 만들기 위해 layoutInflater를 이용한다.
        final CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_post, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(activity, cardView, mDataset.get(viewType));//cardview가 하나하나 돌때, position값을 알기위해 viewType을 넣어 만듬.
        //Log.e("로그: ","로그: "+viewType);

        cardView.setOnClickListener(new View.OnClickListener() {//하나의 카드뷰를 클릭 시 intent로 해당하는 값을 ReviewActivityPost로넘겨줌.
            @Override
            public void onClick(View view) {
                //postInfo 데이터를 보내줘야 데이터를 가지고 레이아웃에 그려줌.
                Intent intent = new Intent(activity, ReviewActivityPost.class);
                intent.putExtra("postInfo", mDataset.get(mainViewHolder.getAdapterPosition()));//앞에는 key값, 뒤에는 실제 값
                //postInfo의 이름으로 intent를 보내 PostActivity에서 받아서 쓸수있게함
                activity.startActivity(intent);
            }
        });
~~~
##### intent를 이용하여 넘겨준 값들을 ReviewActivityPost.java 파일에서 받아서 띄워줌     

~~~java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //이 java파일에서는 activity_write_post창을 보여줄것임.
        setContentView(R.layout.review_activity_post);

        reviewPostInfo = (ReviewPostInfo) getIntent().getSerializableExtra("postInfo");
        //title 넣어주기
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(reviewPostInfo.getTitle());

        //게시물을 추가한 날짜 넣어주기
        TextView createTextView = findViewById(R.id.createdTextView);
        createTextView.setText(new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(reviewPostInfo.getCreatedAt()));//local시간을 생성하여 넣어줄것임.
        //contents 넣어주기
        final TextView contentsTextView = findViewById(R.id.contentsTextView);
        contentsTextView.setText(reviewPostInfo.getContents());
        
        reviewPostAdapter = new ReviewPostAdapter(this);
        reviewPostAdapter.setOnPostListener(onPostListener);//ReviewPostAdapter에 연결해줌.
    }
~~~
<div>
<img src="https://user-images.githubusercontent.com/57400849/86554916-e1539f00-bf89-11ea-965e-1b2bd067ae3a.png" width="70%"> 
<img src="https://user-images.githubusercontent.com/57400849/86554958-01835e00-bf8a-11ea-8329-65fb59a8c7db.png">  
</div>

 




>>#### 2-3-3 게시물 수정 및 삭제   
게시글의 수정 및 삭제 기능은 popup메뉴를 이용한 방법과 버튼을 이용한 방법. 이 두가지를 이용하여 기능 이용이 가능하도록 구현하였다.
게시물의 업데이트를 위해 interface를 이용하는 것이다.   


1.수정 및 삭제 방법 설명   
먼저 기능 구현을 설명하기 전, 공통적인 수정 및 삭제 방법을 설명한다.   
1)수정기능   
우선 수정버튼을 클릭하면, ReviewWriteActivity.java파일로 넘어가게 된다. 이때, 이 java파일에서는 해당하는 게시물의 저장되어있는 값들을 가져와
보여주며 원래의 게시글을 수정할 수 있게 도와준다.   
##### 수정버튼을 눌렀을 때 그 전의 값들을 넣어줌   
~~~java
 private void postInit (){
        if(reviewPostInfo2 !=null){
            titleEditText.setText(reviewPostInfo2.getTitle());
            contentEditText.setText(reviewPostInfo2.getContents());
        }
    }
~~~
##### 이 메서드를 이용하여 uploader메서드의 아래의 코드에서 firebase에서의 수정도 가능하게 한다.   
~~~java
 //값이 null이면 앞에것을 반환.->게시물 등록 시 사용됨. null이 아니면 뒤에것을 반환->수정버튼 이용 시 사용됨
        final DocumentReference documentReference = reviewPostInfo2 ==null? firebaseFirestore.collection("posts").document()
                :firebaseFirestore.collection("posts").document(reviewPostInfo2.getId());
~~~
<div>
<img src="https://user-images.githubusercontent.com/57400849/86555488-786d2680-bf8b-11ea-865c-e833ca339498.png" width="70%"> 
<img src="https://user-images.githubusercontent.com/57400849/86555300-f5e46700-bf8a-11ea-92ee-52cdbd07c024.png" width="20%">
</div>   


2)삭제기능   
삭제하고싶은 게시물에서 popup메뉴의 삭제버튼이나 게시물의 삭제버튼을 누르게 되면 해당하는 게시물의 position값을 얻어와 해당 게시물을
firebase와 리뷰 목록에서 삭제 가능하도록 구현하였다.    
이를 구현하기 위해 OnPostListener.java 파일에 수정 및 삭제 기능의 listener를 이용하였다.   
수정 및 삭제시, 게시물 업데이트를 위해 interface를 이용하는 것이다. 이 메서드 하나로 수정 및 삭제 기능을 구현가능하도록 한다.    
##### listener 메서드 구현     
~~~java
public interface OnPostListener {
    void onDelete(int position);
    void onModify(int position);
}
~~~
##### ReviewMainActivity.java파일에서 정의함     
~~~java
 OnPostListener onPostListener = new OnPostListener() {//인터페이스인 OnPostListener를 가져와서 구현해줌
        @Override
        public void onDelete(int position) {//MainAdapter에 넘겨주기 위한 메서드 작성

            String id = postList.get(position).getId();//document의 id에 맞게 지워주기 위해 id값을 얻어옴
            firebaseFirestore.collection("posts").document(id).delete()//그 id에 맞는 값들을 지워줌
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {//성공시
                            startToast("게시글을 삭제하였습니다.");
                            postUpdate();//새로고침을 위해 이 이벤트를 mainActivity에서 알아야함.->listener를 만들어줘야함
                        }
                    }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {//실패시
                    startToast("게시글 삭제에 실패하였습니다.");
                }
            });
        }

        @Override
        public void onModify(int position) {//여기서 수정하면 writepostActivity를 켜서 수정해주는코드
            myStartActivity(ReviewWriteActivity.class,postList.get(position));
        }
    };
~~~
<img src="https://user-images.githubusercontent.com/57400849/86555531-99ce1280-bf8b-11ea-93a3-742ee9cd725a.png" width="70%">   

2. popup메뉴를 이용하여 수정 및 삭제 기능을 구현하였다.   
1)각각의 adapter에서 구현이 가능하도록 해야하기 때문에 ReviewMainAdapter.java에서 추가적으로 popup메서드를 구현하였다.   

##### popup메서드 구현     
~~~java
 //popup메뉴를 만들기 위한 메서드. view로 받아오기 위해 activity사용함. 여기서 popup메뉴는 수정 삭제가 내려오는 메뉴임.
    public void showPopup(View v, final int position) {//android studio에서 제공하는 팝업 메뉴 표시 기능
        //db값을 갖고오고, 선택된 post값을 알아오기 위해 사용함. view와 위치값(position)을 갖고와서 사용하기. 하나의 postID를 알아야함.
        //postID를 알아야 그 post를 삭제할수있음.->postInfo.java수정

        //수정,삭제의 popup메뉴를 보여주는 버튼을 cardview로 정의함.
        // 버튼을 클릭시 popup메뉴를 보여주는 코드임.

        PopupMenu popup = new PopupMenu(activity,v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override//popup메뉴 내의 삭제버튼, 수정버튼을 눌렀을 때 삭제,수정기능 구현
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.modify ://modify버튼을 눌렀을 때
                        onPostListener.onModify(position);//인터페이스의 onModify를 이용
                        return true;
                    case R.id.delete://delete버튼을 눌렀을 때
                        // 게시글 삭제를 위한 메서드. db에서도 삭제함.
                        onPostListener.onDelete(position);//인터페이스의 onDelete를 이용
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();//inflater를 이용하여 view화 시킴
        inflater.inflate(R.menu.post, popup.getMenu());//popup메뉴를 보여줌.
        popup.show();
    }
~~~
2)이 showPopup() 메서드를 onCreateViewHolder 메서드에 추가적으로 정의해준다.    
~~~java
@NonNull
    @Override//RecyclerView와 cardView를 만들어주는 작업. 보이는 부분만 load함.
    public ReviewMainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout을 view객체로 만들기 위해 layoutInflater를 이용한다.
        final CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_post, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(activity, cardView, mDataset.get(viewType));//cardview가 하나하나 돌때, position값을 알기위해 viewType을 넣어 만듬.
        //Log.e("로그: ","로그: "+viewType);
        cardView.setOnClickListener(new View.OnClickListener() {//하나의 카드뷰를 클릭 시 intent로 해당하는 값을 ReviewActivityPost로넘겨줌.
            @Override
            public void onClick(View view) {
                //postInfo 데이터를 보내줘야 데이터를 가지고 레이아웃에 그려줌.
                Intent intent = new Intent(activity, ReviewActivityPost.class);
                intent.putExtra("postInfo", mDataset.get(mainViewHolder.getAdapterPosition()));//앞에는 key값, 뒤에는 실제 값
                //postInfo의 이름으로 intent를 보내 PostActivity에서 받아서 쓸수있게함
                activity.startActivity(intent);
            }
        });
        //수정,삭제의 popup메뉴를 보여주는 버튼을 cardview로 정의함.
        // 버튼을 클릭시 popup메뉴를 보여주는 코드임.
        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, mainViewHolder.getAdapterPosition());
            }
        });
        return mainViewHolder;
    }
~~~
따라서 각각의 adapter에서 수정 및 삭제 popup 메뉴가 생성되도록 구현하도록 한다.    

<img src="https://user-images.githubusercontent.com/57400849/86555610-ce41ce80-bf8b-11ea-8e6d-b97148d80912.png" width="20%">   


3. 게시물을 자세히 보기 클릭시, 그 안에서도 수정 및 삭제 기능이 가능하도록 구현하였다.    
1)먼저 게시글 삭제 기능을 ReviewPostAdapter.java파일에 따로 구현해주었다. 이는 메서드의 유연한 사용을 위함이다.   

~~~java
public void postDelete(ReviewPostInfo reviewPostInfo){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();//FirebaseFirestore 초기화해주는 코드
        String id = reviewPostInfo.getId();//document의 id에 맞게 지워주기 위해 id값을 얻어옴
        firebaseFirestore.collection("posts").document(id).delete()//그 id에 맞는 값들을 지워줌
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {//성공시
                        startToast("게시글을 삭제하였습니다.");//삭제시 삭제 토스트를 띄워줌
                        //끝났는줄 알고 post 업데이트를 해줘야 하니까 리스너 필요
                        onPostListener.onDelete(1);
                    }
                }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {//실패시 실패 토스트를 띄워줌
                startToast("게시글 삭제에 실패하였습니다."); }
        });
    }

    public void startToast(String msg){//toast를 띄워주는 메서드를 함수로 정의함
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
    }
};
~~~
2)ReviewActivityPost,java파일에 추가적으로 정의해준다.   
이때도 onPostListener의 메서드를 불러온다.
~~~java
OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
            Log.e("로그", "삭제 성공");
        }

        @Override
        public void onModify(int position) {
            Log.e("로그", "수정 성공");
        }
    };
    //수정버튼, 삭제버튼 클릭 시 실행해주는 메서드를 구현함
    private void buttonClick(){
        //dialog를 이용해서 삭제를 재 확인 해주는 메서드를 구현함.
        final AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);
        //삭제버튼 클릭 시
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oDialog.setMessage("삭제하시겠습니까?")//Dialog로 듸워줌
                        .setTitle("알림")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener()//아니오 클릭 시
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Log.i("Dialog", "취소");
                                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("예", new DialogInterface.OnClickListener()//예 버튼 클릭 시
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //실제 삭제 기능을 수행하는 코드.
                                reviewPostAdapter.postDelete(reviewPostInfo); //reviewPostInfo의 postDelete메서드를 이용해 삭제시킴
                                myStartActivity(ReviewMainActivity.class, reviewPostInfo);//intent로 ReviewMainActivity로 넘겨줌
                                finish();
                            }
                        })
                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                        .show();
            }
        });
        //수정버튼 클릭 시
        modify2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(ReviewWriteActivity.class, reviewPostInfo);//바로 ReviewWriteActivity창으로 넘겨줌. 다시 게시물을 작성할 수 있는 창으로 넘겨주는것임.
            }
        });
    }
~~~
<img src="https://user-images.githubusercontent.com/57400849/86555662-f3364180-bf8b-11ea-938d-246628adb58e.png" width="20%">    


>>#### 2-3-4 사용자 정보 연동   
1)로그인을 하지 않은 사용자는 사용자 후기 게시판에 접근할 수 없도록함. 로그인이 됐다면, 사용자 후기 게시판에 접근할 수 있음.  
ReviewMainActivity.java에 메서드를 추가한다.
##### 파이어베이스에 로그인 중인지 판단   
~~~java
 @Override public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }
~~~

##### 로그인 중인 사용자가 있는 지 판단   
~~~java
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity_main);
        fragmentMainMenu = new FragmentMainMenu();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                // 로그인한 사용자가 있는 경우
                if (user != null) {
                    Log.e("로그","리뷰게시판입니다.");
                }
                // 로그인한 사용자가 없는 경우
                else {
                    login();
                }
            }
        };
~~~   
##### 로그인을 확인하는 메서드   
~~~java   
private void login() {
AlertDialog.Builder dialog = new AlertDialog.Builder(this);
if((!this.isFinishing())){
    dialog.setMessage("로그인 후 이용해주세요.")
            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            })
            .setCancelable(false)
            .show();
}
~~~   
<img src="https://user-images.githubusercontent.com/57400849/86573689-b4b27e00-bfaf-11ea-80d3-eb20b85fca59.png" width="70%">

2)게시물을 등록할 때, 회원가입 시 등록한 이메일 정보를 통해 게시글 작성자를 구분하도록 함.   
##### ReviewPostInfo.java 파일에 email에 대한 getter와 setter를 정의함. 이에 따라 생성자 함수에도 추가해줌   
~~~java
 public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ReviewPostInfo(String title, String Contents, Date createdAt, String id,String email) {
        this.title = title;
        this.contents = Contents;
        this.createdAt = createdAt;
        this.id = id;
        this.email = email;
    }
   public ReviewPostInfo(String title, String Contents, Date createdAt, String email) {
        this.title = title;
        this.contents = Contents;
        this.createdAt = createdAt;
        this.email = email;
    }
    public Map<String,Object> getPostInfo(){
        Map<String,Object> docData = new HashMap<>();
        docData.put("title",title);
        docData.put("contents",contents);
        docData.put("createdAt",createdAt);
        docData.put("user",email);
        return docData;
    }
~~~

##### 이에 따라 ReviewWriteActivity.java 파일의 contentsUpdate()메서드도 아래와 같이 수정해준다.   
~~~java
 //text 업데이트를 위한 코드
    private void contentsUpdate() {
        //titleEditText과 contentEditText의 값을 받아서 string값으로 받아옴
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        final String contents = ((EditText) findViewById(R.id.contentEditText)).getText().toString();
        final Date date = reviewPostInfo2 ==null? new Date() : reviewPostInfo2.getCreatedAt();//날짜가 존재하지 않으면 현재 날짜를 불러오고, 수정 시 날짜가 존재하니까 그때는 그 날짜 그대로 유지시켜줌
        //제목과 글이 둘 다 입력 되었을 때 실행됨
        if(title.length() > 0 && contents.length()>0){
            loadrLayout.setVisibility(View.VISIBLE);
            user = firebaseAuth.getCurrentUser();
            assert user != null;
            ReviewPostInfo reviewPostInfo = new ReviewPostInfo(title, contents, date, user.getEmail());
            uploader(reviewPostInfo);//값들이 postinfo로 들어와 uploader 메서드로 들어감
        }else {//그렇지 않으면 게시글을 입력해달라는 toast가 띄워짐
            startToast("게시글을 입력해주세요");
        }
    }
~~~   

##### ReviewMainActivity.java파일에서 adapter로 넘기는 부분도 아래와 같이 수정해준다.   
~~~java
 //실제 게시물을 보여주고 업데이트 해주는 코드
public void postUpdate(){
    firebaseFirestore.collection("posts").orderBy("createdAt", Query.Direction.DESCENDING).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        postList = new ArrayList<>();//arraylist에 받아온값들을 다 넣어주어 보여줌
                        postList.clear();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Log.d(TAG, document.getId()+" => "+document.getData());
                            postList.add(new ReviewPostInfo(//데이터를 다 가져와 postList배열에 넣어줌.
                                    document.getData().get("title").toString(),
                                    document.getData().get("contents").toString(),
                                    new Date(document.getDate("createdAt").getTime()),
                                    document.getId(),
                                    document.getData().get("user").toString()
                            ));
                        }
                        mainAdapter = new ReviewMainAdapter(ReviewMainActivity.this, postList);
                        mainAdapter.setOnPostListener(onPostListener);//onPostListener를 넘겨주면 MainAdapter에서도 쓸수있음.
                        recyclerView.setAdapter(mainAdapter);
                        mainAdapter.notifyDataSetChanged();
                    }else {
                        Log.d(TAG, "Error : ",task.getException());
                    }

                }
            });
~~~

3)사용자 본인이 작성한 게시물만 삭제 및 수정이 가능하며, 자신의 게시물에만 popup메뉴 및 수정 삭제 버튼이 보이게 함.   
   
popup메뉴에서의 수정 삭제를 위한 사용자 확인 메서드를 ReviewMainAdapter.java파일에서 수정.   
##### 게시글을 등록한 사용자와 현재 로그인한 사용자가 일치하면 해당 사용자가 등록한 게시글의 수정 삭제 popup메뉴를 보여줌.   
~~~java
 final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //현재 로그인중인 유저
        assert user != null;
        if(user != null)  {
            email = user.getEmail();
            cardView1= cardView.findViewById(R.id.menu);
            if (email.equals(mDataset.get(viewType).getEmail())) {
                cardView1.setVisibility(View.VISIBLE);
            } else {
                cardView1.setVisibility(View.GONE);
            }
        }
        else {
            Log.e("error : ", "error");
        }
~~~


게시글 자세히 보기에서의 수정 삭제 버튼에 대한 사용자 확인 메서드를 ReviewActivityPost.java파일에서 수정.
##### 로그인중인 사용자와 게시글 등록 시 저장된 사용자가 동일하면 수정 및 삭제 버튼을 보여줌.   
~~~java   
private void userCheck(){
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //현재 로그인중인 유저
    assert user != null;
    email = user.getEmail();
    firebaseFirestore =  FirebaseFirestore.getInstance();
    if (email.equals(reviewPostInfo.getEmail())) {
        //Log.e("log : ",document.getData().get("user").toString());
        Log.e("log : ",reviewPostInfo.getEmail());
        modify2.setVisibility(View.VISIBLE);
        delete2.setVisibility(View.VISIBLE);
        buttonClick();
    }
    // 입력한 정보와 파이어베이스에 저장된 정보가 다르면 일치하는 회원정보가 없다는 텍스트를 보여줌
    else {
        //Log.e("log : ",document.getData().get("user").toString());
        Log.e("log : ",reviewPostInfo.getEmail());
        modify2.setVisibility(View.GONE);
        delete2.setVisibility(View.GONE);
    }
}
~~~   

<img src="https://user-images.githubusercontent.com/57400849/86564192-bde81e80-bfa0-11ea-92b1-44a9210ed929.png" width="50%">



*****    
>### 2-4 약검색
>>#### 2-4-1 약 이름으로 검색   
검색창에 알고싶은 약의 이름을 검색하면 해당 약에 대한 검색 결과를 보여주게 한다.   
   
1)공공데이터 허가받기   

공공데이터 포털에서 XML형식으로 제공하는 '식품 의약품 안전처' 에서 제공하는 '의약품 낱알식별정보(DB) 서비스'와 '식품 의약품 안전처, 식품의약품안전평가원'에서 제공하는 '의약품 제품 허가정보 서비스' 공공데이터를 이용하여 의약품의 정보를 얻어왔다.   

##### 의약품 낱알식별정보(DB) 서비스   
<div>
<img src="https://user-images.githubusercontent.com/57400849/86556026-12819e80-bf8d-11ea-82d0-5abb55d3f596.png" width="50%" >
<img src="https://user-images.githubusercontent.com/57400849/86556401-31ccfb80-bf8e-11ea-9bd8-0415836e0e39.png" width="40%" hight="50%">
</div>   
    
##### 의약품 제품 허가정보 서비스   
<div>
<img src="https://user-images.githubusercontent.com/57400849/86556081-393fd500-bf8d-11ea-828c-40812dc3f1f1.png" width="50%">
<img src="https://user-images.githubusercontent.com/57400849/86556542-9a1bdd00-bf8e-11ea-802d-2e8251cd2502.png" width="40%" hight="50%">
</div>    
    

    
2)먼저 의약품의 기본 정보를 불러오기 위해 getter와 setter를 NameDrug.java 파일에 정의한다.   
이는 검색한 의약품에 해당하는 품목명, 업소명, 이미지, 분류명, 전문/일반 구분을 사용자에게 제공하기 위해서 getter, setter로 받아오고 불러오기 위함이다.  

##### getter, setter 정의   
~~~java
public class NameDrug {
    private Bitmap image;//image는 Bitmap값을 이용해야한다.
    private String drugName;
    private String company;
    private String className;
    private String etcOtcName;

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getEtcOtcName() {
        return etcOtcName;
    }
    public void setEtcOtcName(String etcOtcName) {
        this.etcOtcName = etcOtcName;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getDrugName() {
        return drugName;
    }
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
}
~~~   
   
3)사용자가 검색버튼을 누를 때 Thread 실행    
  
우선, 의약품에 대한 간단한 정보를 불러오기 위하여 '의약품 낱알식별정보(DB) 서비스'를 이용한다.   
데이터가 많을 때 별도로 스레드를 만들어 사용하면 빠르게 실행된다.   
안드로이드는 싱글 쓰레드 체제이며, 오직 메인 쓰레드(UI 쓰레드)만이 뷰의 값을 바꿀 수 있는 권한을 갖고 있다.   
그래서 뷰의 값에 간섭하는 작업을 하는 쓰레드만을 만들고 뷰에 접근하려 시도하면, 안드로이드 자체적으로 앱을 죽여버린다.   
이 경우를 막기 위해 안드로이드 개발자들은 핸들러라는 것을 만들어서 쓰는 것이다.   

##### 검색버튼 클릭   
~~~java
public void mOnClick(final View view) { //검색 버튼을 클릭 시
        
        getedit = edit.getText().toString();
        if(getedit.getBytes().length <= 0)
        {
            Toast.makeText(getApplicationContext(),"검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            //검색 결과가 다 뜰때까지 로딩중을 띄워줌 -> 공공데이터 파싱을 이용하기 때문에 오래 걸리기 때문이다.
            progressDialog.setMessage("로딩중입니다.");
            progressDialog.show();
            if (view.getId() == R.id.buttonNameSearch) {//버튼을 클릭 시 Thread 발생, 공공데이터를 search하여 불러오는 메서드 실행
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                        runOnUiThread(new Runnable() { //스레드 사용 시 Ui를 이용하기 때문에 runOnUiThread가 필요하다.
                            //지금 작업을 수행하는 쓰레드가 메인 쓰레드라면 즉시 작업을 시작하고
                            //메인 쓰레드가 아니라면 쓰레드 이벤트 큐에 쌓아두는 기능을 하는 게 runOnUiThread다.
                            //Runnable : 특정 동작을 UI 스레드에서 동작하도록 합니다. 만약 현재 스레드가 UI 스레드이면 그 동작은 즉시 수행됨
                            //Thread에서 UI에 접근하여 변경할 때 필요한것이다.
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                MyAsyncTask myAsyncTask = new MyAsyncTask();
                                myAsyncTask.execute();
                            }
                        });
                    }
                }).start();
            }
        }
    }
~~~
   
4)MyAsyncTask메서드 생성   
   
검색버튼을 누를 시 실행되는 MyAsyncTask 메서드는 사용자가 EditText창에 입력한 검색어가 포함된 의약품들의 정보를 파싱하는 메서드이다.   
검색을 실행할 때는 스마트폰이 인터넷과 연결되어 있어야 한다는 주의사항이 존재한다.   
파싱을 위해서는 우선 사용자가 검색한 내용을 UTF-8로 인코딩 하는 과정이 필요하다.

##### 인코딩   
~~~java
String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
   String drugSearch = null;//약 이름으로 검색하기 위해 null로 초기화해줌
   try {//인코딩을 위한 try catch문
       drugSearch = URLEncoder.encode(str, "UTF-8");//Edit창에 적은 String값을 인코딩 해줌
   } catch (UnsupportedEncodingException e) {
       e.printStackTrace();
   }
~~~
   
인코딩해서 얻어온 값을 파싱할 주소에 넣은 후, 파싱을 시작하여 파싱한 결과를 저장한다.

##### 공공데이터 파싱   
~~~java
 //공공데이터 파싱을 위한 주소
requestDrugUrl = "http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?ServiceKey="//요청 URL
      + key  + "&numOfRows=100&item_name=" + drugSearch; //약 이름으로 검색 하기.

//실질적으로 파싱해서 inputstream해주는 코드
URL url = new URL(requestDrugUrl); //공공데이터 파싱 주소를 url에 넣음음
InputStream is = url.openStream(); //Stream파일로 읽어들이기 위해 가져온 url을 연결함.
XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//Tag 및 데이터를 가지고 올 때 필요함.
parser = factory.newPullParser();//string을 xml로 바꾸어 넣을 곳
parser.setInput(new InputStreamReader(is, "UTF-8"));//string을 xml로.
eventType = parser.getEventType();//파싱해온 주소의 eventType을 가져옴. 이것을 이용하여 파싱의 시작과 끝을 구분해줌

~~~

##### 파싱한 내용 중 원하는 내용만 TAG 값으로 구분하여 가져옴   
TAG값을 구분하여 원하는 데이터들만 가져온다. 가져온 데이터들을 setter를 이용하여 저장한다. 이렇게 저장한 데이터들을 베열을 선언하여 배열에 저장해준다.
~~~java
 //eventType이 END_DOCUMENT이 아닐때까지 while문이 돌아감
       while (eventType != XmlPullParser.END_DOCUMENT) {
           switch (eventType) {
               case XmlPullParser.START_DOCUMENT://eventType이 START_DOCUMENT일 경우
                   list = new ArrayList<>();//배열을 선언해줌
                   break;
               case XmlPullParser.END_TAG://eventType이 END_TAG일 경우, 태그가 끝나는 부분
                   if (parser.getName().equals("item") && nameDrug != null) {//Tag 이름이 item일경우
                       list.add(nameDrug);//배열에 Drug.java에 들어간 인자들을 넣어주고 끝냄
                   }
                   break;
               case XmlPullParser.START_TAG://eventType이 START_TAG일 경우, 태그가 시작되는 부분. parser가 시작 태그를 만나면 실행
                   if (parser.getName().equals("item")) {//TAG명이 item일 때 Drug를 초기화 해줌
                       nameDrug = new NameDrug();
                   }
                   //Tag가 시작될 때 다 true로 변경함

                   if (parser.getName().equals("ITEM_NAME")) drugName = true;
                   if (parser.getName().equals("ENTP_NAME")) company = true;
                   if (parser.getName().equals("ITEM_IMAGE")) image = true;
                   if (parser.getName().equals("CLASS_NAME")) class_name = true;
                   if (parser.getName().equals("ETC_OTC_NAME")) etc_otc_name = true;

                   break;
               case XmlPullParser.TEXT://eventType이 TEXT일 경우. parser가 내용에 접근했을때

                   if (drugName) {//drugName이 true일때 태그의 내용을 저장함.
                       nameDrug.setDrugName(parser.getText());//drug에 약 이름을 set해줌
                       drugName = false;//마지막에 false로 돌려 초기화해줌
                   } else if (company) {//company이 true일때 태그의 내용을 저장함.
                       nameDrug.setCompany(parser.getText());//drug에
                       company = false;
                   } else if (class_name) {//class_name이 true일때 태그의 내용을 저장함.
                       nameDrug.setClassName(parser.getText());//drug에 약 이름을 set해줌
                       class_name = false;//마지막에 false로 돌려 초기화해줌
                   } else if (etc_otc_name) {//etc_otc_name이 true일때 태그의 내용을 저장함.
                       nameDrug.setEtcOtcName(parser.getText());//drug에
                       etc_otc_name = false;
                   } else if (image) {//이미지 parser하는 방법은 조금 다름
                       imag = parser.getText();//가져온 결과는 URL링크형식임
                       try {
                           //img는 따로 buffer를 이용하여 가져온 후 뿌려줘야함.
                           URL url1 = new URL(imag);//URL링크 형식으로 받아온 결과를 집어넣음
                           URLConnection conn = url1.openConnection();// URL을 연결한 객체 생성.
                           conn.connect();
                           BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                           Bitmap bm = BitmapFactory.decodeStream(bis);
                           bis.close();
                           nameDrug.setImage(bm);//Bitmap형식으로된 이미지를 저장해줌
                           image = false;
                       } catch (Exception ignored) {
                       }
                   }
                   break;
           }

           eventType = parser.next();//다음 parser를 찾아옴
           //detailEventType = detailParser.next();
       }

   } catch(Exception e){
       e.printStackTrace();
   }

   return null;
}
~~~

이미지 파일인 경우 Bitmap을 이용하여 이와 같이 따로 얻어온다.

##### url형태의 이미지 공공데이터 파싱    
~~~java
else if (image) {//이미지 parser하는 방법은 조금 다름
  imag = parser.getText();//가져온 결과는 URL링크형식임
  try {
      //img는 따로 buffer를 이용하여 가져온 후 뿌려줘야함.
      URL url1 = new URL(imag);//URL링크 형식으로 받아온 결과를 집어넣음
      URLConnection conn = url1.openConnection();// URL을 연결한 객체 생성.
      conn.connect();
      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
      Bitmap bm = BitmapFactory.decodeStream(bis);
      bis.close();
      nameDrug.setImage(bm);//Bitmap형식으로된 이미지를 저장해줌
      image = false;
  } catch (Exception ignored) {
  }
}
~~~

5)이렇게 파싱해온 데이터들을 adapter로 넘겨준다. 이는 검색 결과를 목록으로 보여주기 위해서이다.   

##### adapter 연결   
데이터들이 저장된 배열을 adapter로 넘겨준다.
~~~java
@Override
  protected void onPostExecute (String s){//adapter를 연결해주는 부분. 이 코드를 이용해 AsyncTask를 실행한다.
      //결과 파라미터를 리턴하면서 그 리턴값을 통해 스레드 작업이 끝났을 때의 동작을 구현함.
      super.onPostExecute(s);
      //검색 결과가 다 뜨면 progressDialog를 없앰
      progressDialog.dismiss();
      //어답터 연결.
      adapter = new NameMyAdapter(getApplicationContext(), list);//앞의 인자는 application context를 제공하며, 뒤에 인자는 위에서 값을 넣어준 list.
      recyclerView.setAdapter(adapter);
      adapter.notifyDataSetChanged();//어댑터에 연결된 항목들을 갱신함.
  }
~~~
   
검색 결과들을 목록으로 띄워주기 위해 recyclerView를 이용한 adapter를 NameMyAdapter.java 파일에 정의   

##### 생성자를 context와 배열로 초기화   
~~~java
NameMyAdapter(Context context, ArrayList<NameDrug> mList) {
     this.mList = mList;
     this.mInflate = LayoutInflater.from(context);
     this.mContext = context;
 }
~~~
##### view holder를 생성하고 View를 붙여준다.   
~~~java
 @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //최초 view에 대한 list item에 대한 view를 생성함.
        //onBindViewHolder한테 실질적으로 매칭해주는 역할을 함.
        View view = mInflate.inflate(R.layout.list_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }
~~~   
   
##### view에 내용을 넣어준다.   
~~~java
 @Override//재활용 되는 뷰가 호출하여 실행되는 메서드. 뷰 홀더를 전달하고 어댑터는 postion의 데이터를 결합시킴
 public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
     //각 item에 대한 매칭을함.
     //arrayList가 NameDrug에 연결해놓았음. NameMainactivity에서 파싱한 데이터를 받아옴.NameMainactivity에서 NameDrug객체가 있는 arrayList에 담아서 adapter쪽으로 쏨
     //그러면 onBindViewHolder여기서 그것을 받아 glide로 load하게됨


     //Glide를 이용해서 이미지 view 안에 서버로부터 이미지를 받아와 BindViewHolder될 때 넣어줄것임.삽입될것임
     Glide.with(holder.itemView).load(mList.get(position).getImage()).into(holder.list_image);

     //position : 현재 position에 있는것을 가져와서 그대로 입력해줌.
     //NameMainactivity에서 파싱한 데이터들을 실질적으로 넣어줌.
     holder.tv_name.setText(mList.get(position).getDrugName());
     holder.tv_company.setText(mList.get(position).getCompany());
     holder.tv_className.setText(mList.get(position).getClassName());
     holder.tv_etcOtcName.setText(mList.get(position).getEtcOtcName());
 }
~~~   
   
<img src="https://user-images.githubusercontent.com/57400849/86573235-0c041e80-bfaf-11ea-9875-7fab1dabe5e3.png" width="30%">

6)출력된 리스트 중에 상세보기를 원하는 의약품을 클릭했을 시 보여지는 페이지는 2-4-3 약 상세보기 기능에서 설명한다.   

   
>>#### 2-4-2 모양으로 약 검색   
1)'의약품안전나라'사이트에서 csv형식으로 제공하는 '의약품 낱알식별' 파일을 다운로드 받는다.
<img src="https://user-images.githubusercontent.com/57400913/86558535-9c813580-bf94-11ea-8dac-a6032270ccf8.png" width="70%">   

2)csv형식을 안드로이드 스튜디오에서 사용하기 좋게 json형식으로 변환한다.     
<div>
<img src="https://user-images.githubusercontent.com/57400913/86558548-a2771680-bf94-11ea-9fb8-a8ce03f54e16.png" width="40%">
<img src="https://user-images.githubusercontent.com/57400913/86558552-a4d97080-bf94-11ea-89b7-8f1752c71524.png" width="40%">
</div>
   
3)app폴더 아래에 assets폴더를 생성한 후에 json으로 변환한 파일을 넣어준다.
<img src="https://user-images.githubusercontent.com/57400913/86558778-4234a480-bf95-11ea-82fb-facc8f9ec789.png" width="70%">

4)사용자가 검색한 의약품의 정보들을 저장하기 위한 FormDrug.java 폴더를 생성한다.   
검색한 의약품에 해당하는 품목명, 업소명, 이미지, 분류명, 전문/일반 구분을 사용자에게 제공하기 위해서 getter, setter로 받아오고 불러오기 위함이다.  
~~~java
public class FormDrug {
    //리스트에 띄울 목록
    private String drugName; //품목명
    private String company; // 업소명
    private String image;//이미지 주소
    private String className; //분류명
    private String etcOtcName; // 전문일반구분

    //검색할때 사용, 리스트에 띄우지 않음
    private String shape; //모양
    private String color; //색상
    private String type; //제형
    private String markfront; // 식별 표시 앞
    private String markback; // 식별 표시 뒤

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEtcOtcName() {
        return etcOtcName;
    }

    public void setEtcOtcName(String etcOtcName) {
        this.etcOtcName = etcOtcName;
    }



    //////////////모양 검색할때 사용//////////////

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarkfront() {
        return markfront;
    }

    public void setMarkfront(String markfront) {
        this.markfront = markfront;
    }

    public String getMarkback() {
        return markback;
    }

    public void setMarkback(String markback) {
        this.markback = markback;
    }

}
~~~    

5)FormMainActivity.java 폴더를 생성한다.   
색상, 모양, 제형 카테고리에서 사용자가 선택한 것을 처리한다.     
식별자로 검색할 때, 사용자가 검색하기 위해 Edittext에 입력한 값을 처리한다.      
##### 색상, 모양, 제형 버튼을 클릭하기 위한 버튼들 배열로 생성, 초기화
~~~java
public class FormMainActivity extends AppCompatActivity {
    private static final String TAG = "Ma";

    // 각각의 카테고리에서 최종적으로 선택한 것 저장
    private String choosecolor = null; // 선택한 색상 저장
    private String chooseshape = null; // 선택한 모양 저장
    private String choosetype = null; // 선택한 제형 저장
    private String searchmarkfront = null; // 식별자 검색 저장(앞)
    private String searchmarkback = null; // 식별자 검색 저장(뒤)

    //색상 버튼과 관련
    Button[] colorBtn = new Button[16]; //색상 버튼 배열
    Button result_colorbtn; //버튼의 id값 저장
    private String colorbtn_id; //버튼의 id값
    private String thiscolor; // 비교할 색상 값

    //모양 버튼과 관련
    Button[] shapeBtn = new Button[11]; //모양 버튼 배열
    Button result_shapebtn; //버튼의 id값 저장
    private String shapebtn_id; //버튼의 id값
    private String thisshape; // 비교할 색상 값

    //제형 버튼과 관련
    Button[] typeBtn = new Button[4]; //모양 버튼 배열
    Button result_typebtn; //버튼의 id값 저장
    private String typebtn_id; //버튼의 id값
    private String thistype; // 비교할 색상 값
~~~
    
##### 색상 버튼 이벤트
choosecolor는 사용자가 선택한 버튼에 맞는 검색 결과를 보여주기 위한 값을 저장하는 변수이며, thiscolor는 사용자가 누른 버튼의 배경색만 변경해주기 위한 값을 저장하는 변수이다.   

1)사용자가 누른 버튼의 배경색을 하양색으로 변경하고 choosecolor와 thiscolor에 버튼의 text값을 저장한다.   
2)사용자가 버튼을 누를때마다 choosecolor와 thiscolor의 값이 바뀐다.   
3)사용자가 누른 버튼의 색만 변경해주기 위해서 버튼을 누를 때마다 반복문을 이용해서 버튼의 수만큼 각 버튼의 text값과 현재 선택한 값이    choosecolor를 비교해서 값이 다르다면 원래의 색으로 변경해준다.    
4)또, 바로 이 전에 누른 버튼의 text값과 thiscolr의 값을 비교해주어 같다면 배경색을 원래의 색으로 변경해준다.   
~~~java
public void settingColorbtn(){
        for(int i=0; i <colorBtn.length; i++){
            colorbtn_id = "color_btn" + (i+1); //버튼 아이디값 저장
            colorBtn[i] = findViewById(getResources().getIdentifier(colorbtn_id, "id",getPackageName())); //버튼 초기화

        }

        for(Button buttonId : colorBtn){
            buttonId.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    result_colorbtn = findViewById(v.getId());
                    result_colorbtn.setBackgroundResource(R.drawable.choose_btton); //해당아이디 버튼의 배경색을 바꿈
                    result_colorbtn.setTextColor(Color.WHITE);
                    choosecolor = result_colorbtn.getText().toString(); //선택 색상을 저장

                    //////여기서 for문으로 thiscolor랑 result.getText.toString()비교해서 배경색 다시 바꿔주기
                    Log.e("다음 클릭 후 : ", thiscolor);

                    for(int j=0; j<colorBtn.length; j++){
                        if(!colorBtn[j].getText().toString().equals(choosecolor)) {
                            colorBtn[j].setBackgroundResource(R.drawable.basic_button);
                            colorBtn[j].setTextColor(Color.BLACK);
                        }if(colorBtn[j].getText().toString().equals(thiscolor)){
                            colorBtn[j].setBackgroundResource(R.drawable.basic_button);
                            colorBtn[j].setTextColor(Color.BLACK);
                        }
                    }

                    thiscolor = textcolor.getText().toString();

                }
            });
        }

    }
~~~    
##### 모양 버튼 이벤트   
색상 버튼 이벤트와 동일한 방식으로 버튼의 배경색 처리를 한다.   
~~~java
public void settingShapebtn(){
        for(int i=0; i <shapeBtn.length; i++){
            shapebtn_id = "shape_btn" + (i+1); //버튼 아이디값 저장
            shapeBtn[i] = findViewById(getResources().getIdentifier(shapebtn_id, "id",getPackageName()));
        }

        for(Button buttonId : shapeBtn){
            buttonId.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    result_shapebtn = findViewById(v.getId());
                    result_shapebtn.setBackgroundResource(R.drawable.choose_btton); //해당아이디 버튼의 배경색을 하양으로 바꿈
                    result_shapebtn.setTextColor(Color.WHITE);
                    chooseshape = result_shapebtn.getText().toString();


                    Log.e("다음 클릭 후 : ", thisshape);

                    for(int j=0; j<shapeBtn.length; j++){
                        if(!shapeBtn[j].getText().toString().equals(chooseshape)) {
                            shapeBtn[j].setBackgroundResource(R.drawable.basic_button);
                            shapeBtn[j].setTextColor(Color.BLACK);
                        }if(shapeBtn[j].getText().toString().equals(thisshape)){
                            shapeBtn[j].setBackgroundResource(R.drawable.basic_button);
                            shapeBtn[j].setTextColor(Color.BLACK);
                        }
                    }

                    //  textcolor.setText(result.getText()); // 선택 색상을 보여줄 textview

                    thisshape = textshape.getText().toString();
                }
            });
        }
    }
~~~   
##### 제형 버튼 이벤트
색상과 모양 버튼의 버튼의 text값과 동일하기 때문에 클릭한 버튼의 text값을 바로 변수에 저장해주었지만   
제형 버튼은 공공데이터에서 제공하는 파일의 형식이 맞추려면 과정이 복잡해진다.    
(ex.정제류 - 나정, 필름코팅정, 서방정, 저작정, 추어블정(저작정), 구강붕해정, 서방성필름코팅정, 장용성필름코팅정, 다층정, 분산정(현탁정))     
1)제형 버튼 중 클릭한 버튼의 text값을 choosetype에 저장한다.   
2)버튼의 text값과 json파일에 저장되어있는 제형의 종류를 공통으로 포함된 문자열을 비교한 후에 다시 choosetype에 모든 종류를 저장한다.   
3)이후에 사용자가 선택한 버튼의 배경색만 변경하는 부분은 위의 색상 이벤트에서 설명한것과 동일하다.   
~~~java
public void settingTypebtn(){
        for(int i=0; i <typeBtn.length; i++){
            typebtn_id = "type_btn" + (i+1); //버튼 아이디값 저장
            typeBtn[i] = findViewById(getResources().getIdentifier(typebtn_id, "id",getPackageName())); //초기화
        }

        for(Button buttonId : typeBtn){
            buttonId.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    result_typebtn = findViewById(v.getId());
                    result_typebtn.setBackgroundResource(R.drawable.choose_btton); //해당아이디 버튼의 배경색을 하양으로 바꿈
                    result_typebtn.setTextColor(Color.WHITE);
                    choosetype = result_typebtn.getText().toString();

                    if(choosetype.contains("정")){
                        choosetype = "나정, 필름코팅정, 서방정, 저작정, 추어블정(저작정), 구강붕해정, 서방성필름코팅정, 장용성필름코팅정, 다층정, 분산정(현탁정), 정제";
                    }else if(choosetype.contains("경질")){
                        choosetype = "경질캡슐제|산제, 경질캡슐제|과립제, 경질캡슐제|장용성과립제, 스팬슐, 서방성캡슐제|펠렛";
                    }else if(choosetype.contains("연질")){
                        choosetype ="연질캡슐제|현탁상, 연질캡슐제|액상";
                    } else if(choosetype.contains("기타")){
                        choosetype = "껌제, 트로키제";
                    }

                    //texttype.setText(choosetype);

                    Log.e("choosetype ?????", choosetype);
                    Log.e("다음 클릭 후 : ", thistype);

                    for(int j=0; j<typeBtn.length; j++){

                        if(typeBtn[j].getText().toString().contains("정")){
                            if(!choosetype.contains("정")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                            if(thisshape.contains("정")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                        }else if(typeBtn[j].getText().toString().contains("경질")){
                            if(!choosetype.contains("경질")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                            if(thisshape.contains("경질")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                        }else if(typeBtn[j].getText().toString().contains("연질")){
                            if(!choosetype.contains("연질")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                            if(thisshape.contains("연질")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                        }else {
                            if(!choosetype.contains("껌제")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                            if(thisshape.contains("제")) {
                                typeBtn[j].setBackgroundResource(R.drawable.basic_button);
                                typeBtn[j].setTextColor(Color.BLACK);
                            }
                        }

                    }

                    //  textcolor.setText(result.getText()); // 선택 색상을 보여줄 textview

                    thistype = texttype.getText().toString();
                }
            });
        }
    }
~~~      

##### 색상, 모양, 제형 버튼 선택 초기화
1)사용자가 선택한 버튼을 초기화하기 위해서 클릭하면 choosecolor, chooseshape, choosetype에 모두 null값이 저장된다.   
2)사용자가 선택해서 하양색으로 변한 배경색 또한 원래의 배경색으로 돌아온다.   
3)초기화 되었다는 Toast가 뜬다.   
~~~java
//초기화 버튼
    public void click_research(View view) {
        choosecolor = null;
        chooseshape = null;
        choosetype = null;

        Toast myToast = Toast.makeText(this.getApplicationContext(),"선택이 초기화 되었습니다.", Toast.LENGTH_SHORT);
        myToast.show();

        for(int i=0; i <colorBtn.length; i++){
            colorBtn[i].setBackgroundColor(Color.WHITE);
            colorBtn[i].setBackgroundResource(R.drawable.basic_button);
            colorBtn[i].setTextColor(Color.BLACK);
        }
        for(int i=0; i <shapeBtn.length; i++){
            shapeBtn[i].setBackgroundColor(Color.WHITE);
            shapeBtn[i].setBackgroundResource(R.drawable.basic_button);
            shapeBtn[i].setTextColor(Color.BLACK);
        }
        for(int i=0; i <typeBtn.length; i++){
            typeBtn[i].setBackgroundColor(Color.WHITE);
            typeBtn[i].setBackgroundResource(R.drawable.basic_button);
            typeBtn[i].setTextColor(Color.BLACK);
        }

    }
}
~~~     
<img src="https://user-images.githubusercontent.com/57400913/86567020-a3647400-bfa5-11ea-8dee-0fdac8278d3d.png" width="40%">   

##### 의약품의 앞, 뒤에 쓰여있는 식별 표시로 검색하기    
1)공공데이터로 제공한 파일에서 식별 표시에 없는 의약품의 경우에는 '-'로 저장되어있다.   
2)사용자가 앞이나 뒤 한 곳만 입력했을때도 올바른 결과를 나오게 하기 위해서 입력된 값의 길이를 체크한 후에 공백이면 searchamarkfront와 serachmarkback에 '-'를 저장해준다.   

~~~java
//식별자 앞 edittext값 초기화, 저장
    public void takeMarkfront(){
        EditText markfront = (EditText) findViewById(R.id.mark_front);
        searchmarkfront = markfront.getText().toString();
        if(searchmarkfront.length() == 0){
            searchmarkfront = null; // 입력된 값이 없을때 '-'로 저장
        }else {
            searchmarkfront=this.searchmarkfront;
        }
    }

    //식별자 뒤 edittext값 초기화, 저장
    public void takeMarkBack(){
        EditText markback = (EditText) findViewById(R.id.mark_Back);
        searchmarkback = markback.getText().toString();
        if(searchmarkback.length() == 0){
            searchmarkback = null;
        }else{
            searchmarkback = this.searchmarkback;
        }
    }
~~~

##### 사용자가 선택 또는 입력한 값을 Intent로 넘겨주기
1)최종적으로 choosecolor, chooseshape, choosetype과 searchmarkfront, searchmarkback에 저장된 값을 FormSearchActivity.java폴더에 Intent로 넘겨준다.    
~~~java
//검색 결과 버튼
    public void click_result(View view) {

        Intent intent = new Intent(getApplicationContext(), FormSearchActivity.class);

        intent.putExtra("choosecolor",choosecolor);
        intent.putExtra("chooseshape",chooseshape);
        intent.putExtra("choosetype",choosetype);


        startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
    }

    //식별자 검색 결과 버튼
    public void click_markresult(View view) {

        takeMarkfront(); // 식별자 앞 edit에 입력한 텍스트값 가져오기
        takeMarkBack();

        Intent intent = new Intent(getApplicationContext(), FormSearchActivity.class);
        intent.putExtra("searchmarkfront",searchmarkfront);
        intent.putExtra("searchmarkback", searchmarkback);


        startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
    }
~~~
2)FormMainActivity.java 폴더에서 Intent로 넘어온 값과 일치하는 조건들을 Json파일에서 찾아 배열로 저장한 후에 어댑터로 결과를 넘겨주는 과정을 처리할 FormSearchActivity.java 폴더를 생성한다.   
##### 색상, 모양, 제형 버튼으로 검색한 것인지, 식별 표시로 검색한 것인지 구분
구분하여 서로 다른 메서드를 실행해준다.   
~~~java
        if (choosecolor == null && chooseshape == null && choosetype ==null) {
            marksearchJson();
            Log.e("dg","식별자");
        }
        else {
            searchJson();
            Log.e("dg","컬러");
        }
        recyclerView = (RecyclerView)findViewById(R.id.rv_recyclerview);//리사이클러뷰 초기화
        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존 성능 강화

        //리니어레이아웃을 사용하여 리사이클러뷰에 넣어줄것임
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new FormMyAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
~~~
##### 색상, 모양, 제형 버튼으로 검색한 경우
1)세 개의 카테고리 중 한 카테고리에서만 선택해도 올바른 검색 결과를 나오게 하기 위해서 총 7가지 경우로 나누었다.   
2)json파일은 key와 value로 구성되어있는데 사용자가 선택한 값과 일치하는 value값을 찾아 품목명, 제품 이미지, 업소명, 분류명, 전문일반구문 key에 해당하는 value값을 사용자에게 보여주기 위해서 setter에 저장한다.   
 ~~~java
 //json에서 조건에 맞는 것 검색(색상, 모양, 제형) 7가지.
    public void searchJson(){
        try{
            InputStream is = getAssets().open("druglist.json"); //assests파일에 저장된 druglist_final.json 파일 열기
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("druglist"); //json파일에서 의약품리스트의 배열명, jsonArray로 저장

            list = new ArrayList<>();

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);

                //'색상, 모양, 제형' 선택하고 검색하기(3개의 카테고리 중 하나만 선택 하고 검색 가능)
                //1. 색상만 선택된 경우
                if(choosecolor != null && chooseshape == null && choosetype == null){
                    if ((jsonObject.getString("색상앞").contains(choosecolor))) {
                        FormDrug formDrug = new FormDrug();
                        Log.e("1번 : ", jsonObject.getString("품목명") + jsonObject.getString("색상앞") + jsonObject.getString("의약품제형"));

                        formDrug.setImage(jsonObject.getString("큰제품이미지"));
                        formDrug.setDrugName(jsonObject.getString("품목명"));
                        formDrug.setCompany(jsonObject.getString("업소명"));
                        formDrug.setClassName(jsonObject.getString("분류명"));
                        formDrug.setEtcOtcName(jsonObject.getString("전문일반구분"));

                        list.add(formDrug);
                    }
                }
                //2. 색상 & 모양
                else if(choosecolor != null && chooseshape != null && choosetype == null){
                    if ((jsonObject.getString("색상앞").contains(choosecolor)) && (jsonObject.getString("의약품제형").equals(chooseshape))) {
                        FormDrug formDrug = new FormDrug();
                        Log.e("2번 : ", jsonObject.getString("품목명") + jsonObject.getString("색상앞") + jsonObject.getString("의약품제형") + jsonObject.getString("제형코드명") + jsonObject.getString("표시앞") + jsonObject.getString("표시뒤"));

                        formDrug.setImage(jsonObject.getString("큰제품이미지"));
                        formDrug.setDrugName(jsonObject.getString("품목명"));
                        formDrug.setCompany(jsonObject.getString("업소명"));
                        formDrug.setClassName(jsonObject.getString("분류명"));
                        formDrug.setEtcOtcName(jsonObject.getString("전문일반구분"));
                        list.add(formDrug);
                    }
                } ...
 ~~~   
 <div>
<img src="https://user-images.githubusercontent.com/57400913/86566889-64362300-bfa5-11ea-887c-20be6eb94caf.png" width="40%">
<img src="https://user-images.githubusercontent.com/57400913/86567239-fb02df80-bfa5-11ea-8ea5-f20b52c8ff2b.png" width="40%">
</div>    

 ##### 식별 표시로 검색한 경우
 1)식별 표시 앞, 뒤 중 하나만 입력해도 올바른 검색 결과를 나오게 하기 위해서 3가지 경우로 나누었다.   
 2)해당하는 의약품의 정보를 보여주기 위한 json파싱 방법은 위와 동일하다.   
 ~~~java
 //json에서 조건에 맞는 것 검색(식별자) 3가지.
    public void marksearchJson(){
        try{
            InputStream is = getAssets().open("druglist.json"); //assests파일에 저장된 druglist_final.json 파일 열기
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("druglist"); //json파일에서 의약품리스트의 배열명, jsonArray로 저장

            list = new ArrayList<>();

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);

                //8. 표시앞만
                if(searchmarkfront != null && searchmarkback == null) { //식별자 앞이 입력됐을 경우
                    if (searchmarkfront.equals(jsonObject.getString("표시앞")))
                    {
                        FormDrug formDrug = new FormDrug();
                        Log.e("8번째 : ", jsonObject.getString("품목명") + jsonObject.getString("색상앞") + jsonObject.getString("의약품제형") + jsonObject.getString("제형코드명") + jsonObject.getString("표시앞") + jsonObject.getString("표시뒤"));
                        formDrug.setImage(jsonObject.getString("큰제품이미지"));
                        formDrug.setDrugName(jsonObject.getString("품목명"));
                        formDrug.setCompany(jsonObject.getString("업소명"));
                        formDrug.setClassName(jsonObject.getString("분류명"));
                        formDrug.setEtcOtcName(jsonObject.getString("전문일반구분"));
                        list.add(formDrug);
                    }

                } //9. 표시 앞 뒤 둘 다 입력
                else if(searchmarkfront != null){ //두개 다 입력
                    if (searchmarkfront.equals(jsonObject.getString("표시앞")) && searchmarkback.equals(jsonObject.getString("표시뒤")))
                    {
                        FormDrug formDrug = new FormDrug();
                        Log.e("9번째 : ", jsonObject.getString("품목명") + jsonObject.getString("색상앞") + jsonObject.getString("의약품제형") + jsonObject.getString("제형코드명") + jsonObject.getString("표시앞") + jsonObject.getString("표시뒤"));
                        formDrug.setImage(jsonObject.getString("큰제품이미지"));
                        formDrug.setDrugName(jsonObject.getString("품목명"));
                        formDrug.setCompany(jsonObject.getString("업소명"));
                        formDrug.setClassName(jsonObject.getString("분류명"));
                        formDrug.setEtcOtcName(jsonObject.getString("전문일반구분"));
                        list.add(formDrug);
                    }
                }//10. 표시뒤만
~~~
 3)list에 배열로 결과를 저장하고 FormMyAdapter.java 폴더를 생성한 후에 넘겨준다.   
 <div>
<img src="https://user-images.githubusercontent.com/57400913/86567351-33a2b900-bfa6-11ea-83b4-d91234e0b060.png" width="40%">
<img src="https://user-images.githubusercontent.com/57400913/86567379-3e5d4e00-bfa6-11ea-95b0-eda7e9152b70.png" width="40%">  
 </div>   
 <div>
<img src="https://user-images.githubusercontent.com/57400913/86567395-43220200-bfa6-11ea-8364-33dfff8e0fdf.png" width="40%">
<img src="https://user-images.githubusercontent.com/57400913/86567408-46b58900-bfa6-11ea-8548-b4da6b0c02c3.png" width="40%">  
</div>   

 ##### 검색 결과 Recyclerview로 띄어주기
 1)비트맵 방식으로 이미지를 띄워주었던 2-4-1 약 이름으로 검색 기능의 4)와 다르게 Glide로 이미지를 변환한다. 다른 부분만 다르고 동일하다.     
 2)출력된 리스트 중에 상세보기를 원하는 의약품을 클릭했을 시 보여지는 페이지는 2-4-3 약 상세보기 기능에서 설명한다.   
~~~java
public class FormMyAdapter extends RecyclerView.Adapter<FormMyAdapter.MyViewHolder>{
    private static final String sort = "form";

    private String drugString;
    private ArrayList<FormDrug> mList;
    private LayoutInflater mInflate;
    private Context mContext;
    private String data = null;
    private Intent intent;
    private String searchString;

    FormMyAdapter(Context context, ArrayList<FormDrug> mList) {//생성자를 context와 배열로 초기화해줌
        this.mList = mList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.list_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        //최초 view에 대한 list item에 대한 view를 생성함.
        //이 onBindViewHolder친구한테 실질적으로 매칭해주는 역할을 함.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(mList.get(position).getImage())
                .into(holder.list_image);

        holder.tv_name.setText(mList.get(position).getDrugName());
        holder.tv_company.setText(mList.get(position).getCompany());
        holder.tv_className.setText(mList.get(position).getClassName());
        holder.tv_etcOtcName.setText(mList.get(position).getEtcOtcName());
    }

    @Override
    public int getItemCount() {
        return (mList != null ? mList.size() : 0);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView list_image;
        public TextView tv_name;
        public TextView tv_company;
        public TextView tv_etcOtcName;
        public TextView tv_className;
        public View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            list_image = itemView.findViewById(R.id.list_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_company = itemView.findViewById(R.id.tv_company);
            tv_etcOtcName = itemView.findViewById(R.id.tv_etcOtcName);
            tv_className = itemView.findViewById(R.id.tv_className);
        }
    }
~~~
  
    
>>#### 2-4-3 상세보기 페이지   

1)검색한 의약품에 대한 목록 중 원하는 의약품을 클릭했을 시, adapter에서 공공데이터를 파싱한다.      

해당 약에 대한 상세 정보를 얻기 위하여 공공데이터의 '의약품 제품 허가정보 서비스'를 이용한다.   
사용자가 상세 정보를 얻고 싶어하는 의약품을 선택하면, 해당 이름을 파싱 주소로 넘겨주어 새롭게 파싱한다.   
2-4-1의 4)와 동일하게 TAG값을 얻어 사용자에게 보여주고 싶은 데이터들만 뽑아냈다.   
이렇게 파싱한 데이터들을 buffer에 저장한다.

~~~java
String getXmlData(String string){

        StringBuffer buffer = new StringBuffer();

        try {//인코딩을 위한 try catch문
            searchString = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //공공데이터 파싱을 위한 주소
        //약국 공공데이터 서비스키
        String key = "gyhnkvw8BuHNtPGQzXT5Nluh3Ri3hGlcpEnheMdjI1gjDbZhPSEpy05ofIMaFu2a96c%2FUX%2FzOVblYrTa%2B%2Fu%2Bjg%3D%3D";
        String requestUrl = "http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?ServiceKey="//요청 URL
                + key + "&item_name=" + searchString; //약 이름으로 검색
        Log.e("drugSearch : ", requestUrl);

        try {
            //일단 false로 선언해준 후 파싱해온 tag이름과 같으면 true로 바꾸어 배열에 넣어줄것임
            boolean Nb_doc_data = false;
            boolean doc = false;
            boolean ee_doc_data = false;
            boolean paragraph = false;
            boolean ud_doc_data = false;
            boolean article = false;
            boolean articleEnd = false;
            String tagName = null;

            //실질적으로 파싱해서 inputstream해주는 코드
            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));

            //파싱해온 주소의 eventType을 가져옴. 이것을 이용하여 파싱의 시작과 끝을 구분해좀
            int eventType = parser.getEventType();

            parser.next();
            //eventType이 END_DOCUMENT이 아닐때까지 while문이 돌아감
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://eventType이 START_DOCUMENT일 경우
                        break;
                    case XmlPullParser.END_TAG://eventType이 END_TAG일 경우, 태그가 끝나는 부분
                        if (parser.getName().equals("item")) {//Tag 이름이 item일경우
                            Log.e("END_TAG : ", "END");
                        }
                        if (parser.getName().equals("DOC")) {
                            articleEnd = true;
                        }
                        if(parser.getName().equals("body")){
                            buffer.append("\n");
                            buffer.append("※ 허가 취소된 의약품이거나 상세정보를 제공하지 않는 의약품입니다. ※");
                        }
                        break;
                    case XmlPullParser.START_TAG://eventType이 START_TAG일 경우, 태그가 시작되는 부분
                        if (parser.getName().equals("item")) {
                            buffer.append("\n");
                        }
                        //Tag가 시작될 때 다 true로 변경함

                        //xml파일은 Doc안에 Article안에 paragraph내에 text가 있는 구조임. 그래서 그 안의 구조를 가져오기 위해 이렇게 선언함.
                        if (parser.getName().equals("DOC")) {

                            //xml파일에서 Tag의 title에 적힌 값을 읽어오기 위한 코드.
                            String arti = parser.getAttributeValue(null, "title");
                            buffer.append("\n\n");
                            buffer.append("< ").append(arti).append(" >");
                            articleEnd = false;//article의 End부분은 false로 선언해줌. 이것을 이용하여 문서의 끝을 알림.
                            doc = true;
                        }
                        if (parser.getName().equals("ARTICLE")) {
                            //xml파일에서 Tag의 title에 적힌 값을 읽어오기 위한 코드.
                            String arti = parser.getAttributeValue(null, "title");
                            buffer.append(arti);
                            article = true;
                        }
                        if (parser.getName().equals("PARAGRAPH")) {
                            paragraph = true;
                        }
                        if (parser.getName().equals("EE_DOC_DATA")) ee_doc_data = true;//효능효과
                        if (parser.getName().equals("UD_DOC_DATA")) {//용법용량
                            ud_doc_data = true;
                        }
                        if (parser.getName().equals("NB_DOC_DATA")) {//사용상의주의사항
                            Nb_doc_data = true;
                        }
                        break;

                    case XmlPullParser.TEXT://eventType이 TEXT일 경우
                        if (ee_doc_data) {//효능효과부분을 가져오는 코드
                            if (doc) {//doc 데이터 안에
                                if (!articleEnd) {//article부분이 끝날때까지 돌리기 위해 사용됨
                                    if (article) {//article 부분에
                                        if (paragraph) {//paragraph부분. 이곳에 text가 있음.
                                            //parsing부분
                                            String ee_text = parser.getText();//text를 가져옴
                                            //Log.e("GBN_NAME : ", ee_text);
                                            buffer.append(ee_text);//요소의 TEXT 읽어와서 문자열버퍼에 추가
                                        }
                                    }
                                    buffer.append("\n"); //꼭필요
                                    break;
                                }
                            }
                            ee_doc_data = false;
                        } else if (ud_doc_data) {//용법용량부분을 가져오는 코드
                            if (doc) {
                                if (!articleEnd) {
                                    if (article) {
                                        if (paragraph) {
                                            String ud_text = parser.getText();
                                            if (ud_text.contains("<") || ud_text.contains("&")) {//table형태 등 html문서로된 부분이 있으면 변환하여 buffer에 추가해줌
                                                buffer.append(Html.fromHtml(ud_text));

                                            } else {//html요소가 포함되어있지 않으면 그냥 buffer에 추가해줌
                                                buffer.append(ud_text);
                                            }
                                        }
                                    }
                                    buffer.append("\n");
                                    break;
                                }
                            }
                            ud_doc_data = false;
                        } else if (Nb_doc_data) {//사용상의주의사항부분
                            if (doc) {

                                if (!articleEnd) {
                                    if (article) {
                                        if (paragraph) {
                                            String nb_doc_data = parser.getText();
                                            //Log.e("GBN_NAME : ", nb_doc_data);
                                            if (nb_doc_data.contains("<") || nb_doc_data.contains("&")) {//table형태 등 html문서로된 부분이 있으면 변환하여 buffer에 추가해줌
                                                buffer.append(Html.fromHtml(nb_doc_data));
                                            } else {//html요소가 포함되어있지 않으면 그냥 buffer에 추가해줌
                                                buffer.append(nb_doc_data);
                                            }
                                        }
                                        buffer.append("\n");
                                    }
                                    break;
                                }
                            }
                            ud_doc_data = false;//다시 false로 돌리는 초기화함
                        }
                }
                eventType = parser.next();//다음 parser를 찾아옴
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();//buffer를 String형식으로 return해줌
    }

~~~

2)파싱한 결과를 상세정보 페이지인 LookupActivity.java파일로 intent를 통해 넘긴다.   

상세정보 페이지인 LookupActivity.java파일로 이동할 때, 파싱한 결과를 저장한 buffer를 intent를 이용하여 전달한다.   
이 때, adapter에서 이미지를 변환 후 넘기는 방식에서 이름으로 검색할 때와 약 모양으로 검색할 때의 차이가 존재한다.   
따라서 intent할 때 동일한 key에 대한 value값에 구분을 두어 상세정보 페이지에서 구분하여 받을 수 있게 한다.   
   
##### 이름으로 검색    
이미지를 Bitmap으로 변환 후 넘겨준다. 이는 이미지 데이터를 파싱을 통해 가져오기 때문이다.   
~~~java
private static final String sort = "name";

//해당하는 holder를 눌렀을 때 intent를 이용해서 상세정보 페이지로 넘겨줌
holder.itemView.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
       new Thread(new Runnable() { //파싱을 이용했기 때문에 스레드가 필요하다. 오래 걸리기 때문에 background에서 처리해줘야함
           @Override
           public void run() {
               // TODO Auto-generated method stub
               //알고싶은 약의 상세정보를 누르면 그 약의 이름을 받아와 다시 파싱을 시작함
               //그렇기 때문에 약의 이름을 drugString에 저장해준 후 그 이름을 getXmlData()의 메서드로 넘겨줌
               drugString = mList.get(position).getDrugName();
               data = getXmlData(drugString);//drugString에 해당하는 데이터를 string형식으로 가져와 data변수에 저장해줌

               intent = new Intent(mContext, LookupActivity.class);//intent를 초기화해주는 코드
               //앞에는 key값, 뒤에는 실제 값
               intent.putExtra("Drug", mList.get(position).getDrugName());//drug의 이름을 넘겨줌
               intent.putExtra("data", data);//파싱한 데이터들을 "data"의 키로 넘겨줌

               //이미지의 용량을 작게 해주는 코드
               //-> intent로 이미지를 넘길 떼 이미지의 용량이  100kb로 제한되어있기 때문에 그 사이즈에 맞춰서 넘겨줘야함
               //이미지의 용량을 임의로 지정하여 intent로 넘겨주는 코드
               Bitmap bitmap = mList.get(position).getImage();
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
               byte[] b = stream.toByteArray();
               intent.putExtra("image", b); //image의 크기를 낮춰준 후 intent로 넘겨줌
               intent.putExtra("sort",sort);

               //전체의 intent를 실제로 넘겨주는 코드.
               mContext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
           }
       }).start();
   }

});
~~~

##### 모양으로 검색    
이미지를 그대로 넘겨준다. 이는 이미지 data를 json에서 가져오기 때문이다.   
~~~java

private static final String sort = "form";

holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
    new Thread(new Runnable() { //파싱을 이용했기 때문에 스레드가 필요하다. 오래 걸리기 때문에 background에서 처리해줘야함
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //알고싶은 약의 상세정보를 누르면 그 약의 이름을 받아와 다시 파싱을 시작함
            //그렇기 때문에 약의 이름을 drugString에 저장해준 후 그 이름을 getXmlData()의 메서드로 넘겨줌
            drugString = mList.get(position).getDrugName();
            data = getXmlData(drugString);//drugString에 해당하는 데이터를 string형식으로 가져와 data변수에 저장해줌
            intent = new Intent(mContext, LookupActivity.class);//intent를 초기화해주는 코드
            //앞에는 key값, 뒤에는 실제 값
            intent.putExtra("Drug", drugString);//drug의 이름을 넘겨줌
            intent.putExtra("data", data);//파싱한 데이터들을 "data"의 키로 넘겨줌
            intent.putExtra("image", mList.get(position).getImage());
            intent.putExtra("sort", sort);
            //전체의 intent를 실제로 넘겨주는 코드.
            mContext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
        }
    }).start();
}

});

~~~

3)LookupActivity.java 파일에서 파싱된 정보를 key값을 통해서 받는다.   
 
adapter에서 넘겨준 image의 형식이 다르기 때문에 adpater에서 구분하여 넘겨준 key에 대한 value값을 구분하여 이미지를 각각의 방식에 맞게 변환한다.   

##### 이름으로 검색   

~~~java
if(sort.equals("name")){
   byte[] b = getIntent().getByteArrayExtra("image");
   Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);

   //textView와 imageView에 받아온 값들을각각 저장해줌.
   textView.setText(drugString);
   detailStr.setText(str_detailStr);
   imageView.setImageBitmap(bitmap);

}
~~~


##### 모양으로 검색   

~~~java
else if(sort.equals("form")){
   image = getIntent().getStringExtra("image");
   //Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);

   Glide.with(this)
           .load(image)
           .into(imageView);

   //textView와 imageView에 받아온 값들을각각 저장해줌.
   textView.setText(drugString);
   detailStr.setText(str_detailStr);
}
~~~   
##### 약 상세정보 페이지   
이미지를 제외한 나머지 부분은 동일하다. 파싱해서 넘겨받은 데이터들을 의약품의 이미지와 함께 보여준다.   
~~~java
public class LookupActivity extends NameMainActivity {
    TextView textView;
    TextView detailStr;
    ImageView imageView;
    String drugString;
    String str_detailStr;
    String image; //form 에서 넘어온 어댑터에서 이미지 넣어줄때 사용
    String sort = null; // form, name 중 어느 어댑터에서 넘어온 건지 구분하기 위함
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);
        textView = findViewById(R.id.textView);
        detailStr = findViewById(R.id.detailStr);
        imageView = findViewById(R.id.image);
        //Drug라는 key값으로 NameMyAdapter에서 intent해줄때 넘겨준 값을 가져옴.
        drugString = getIntent().getStringExtra("Drug");//String값으로 받아옴. 이것은 약의 이름을 받아오는것.
        str_detailStr = getIntent().getStringExtra("data");
        //NameMyAdapter.java파일에서 intent로 넘겨준 image를 받아와 byte배열에 저장 후 decode하여 imageview에 보여줌.
        sort = getIntent().getStringExtra("sort");
        Log.e("form/sort??",sort);
        // 이미지 넘겨주는 형식이 다르게 때문에 bitmap, string 구분하기 위해 if문 사용
        if(sort.equals("name")){
            byte[] b = getIntent().getByteArrayExtra("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);

            //textView와 imageView에 받아온 값들을각각 저장해줌.
            textView.setText(drugString);
            detailStr.setText(str_detailStr);
            imageView.setImageBitmap(bitmap);
        }
        else if(sort.equals("form")){
            image = getIntent().getStringExtra("image");
            //Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);

            Glide.with(this)
                    .load(image)
                    .into(imageView);

            //textView와 imageView에 받아온 값들을각각 저장해줌.
            textView.setText(drugString);
            detailStr.setText(str_detailStr);
        }
    }
}





