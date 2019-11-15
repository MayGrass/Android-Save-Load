package tw.org.iii.saveloadfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("DCH", MODE_PRIVATE);
        editor = sp.edit();
    }

    public void test1(View view) {
        editor.putString("username", "DCH");
        editor.putBoolean("sound", false);
        editor.putInt("stage", 4);
        editor.commit();
        Toast.makeText(this,"save ok", Toast.LENGTH_SHORT).show();
    }

    public void test2(View view) {
        boolean isSound = sp.getBoolean("sound", true);
        String username = sp.getString("username", "nobody");
        int stage = sp.getInt("stage", 1);
        Log.v("DCH", username + ":" + stage + ":" + isSound);
    }
}
