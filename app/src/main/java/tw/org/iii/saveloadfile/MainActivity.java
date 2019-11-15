package tw.org.iii.saveloadfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        //存資料data/data/appname/shared_preds
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

    public void test3(View view) {
        try {
            //資料存在data/data/appname/files裡
            FileOutputStream fout = openFileOutput("DCH.txt", MODE_APPEND); //創檔名DCH.txt (不一定需要副檔名)
            fout.write("Hello World\n".getBytes()); //字串轉Byte
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.v("DCH", e.toString());
        }
    }

    public void test4(View view) {
    }
}
