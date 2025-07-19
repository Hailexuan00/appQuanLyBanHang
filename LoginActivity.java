package fpoly.hailxph49396.quanlybanhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    Button btn_dangnhap, btn_dangKy;
    TextInputLayout txt_pass, txt_user;
    TextInputEditText ed_user, ed_pass;
    CheckBox cb_ghinho;

    String UserDangKy = " ", PassDangky  = " ", UserDangNhap, PassDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txt_user = findViewById(R.id.txt_user);
        ed_user = findViewById(R.id.ed_user);
        txt_pass = findViewById(R.id.txt_pass);
        ed_pass =findViewById(R.id.ed_pass);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        btn_dangKy = findViewById(R.id.btn_danhky);
        cb_ghinho = findViewById(R.id.cb_ghinho);

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                launcher.launch(i);

            }
        });

        btn_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, Register_Activity.class);
                launcher.launch(i);
            }
        });
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    // nơi xữ lí dũ liệu
                    if (o.getResultCode() == 1){
                        Intent i = o.getData();
                        UserDangKy = i.getStringExtra("User");
                        PassDangky = i.getStringExtra("Pass");
                    }

                }
            }
    );
}