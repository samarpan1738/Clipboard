package garg.sarthik.clipboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "Focus";
    Toolbar toolbar;
    TextView tvEditDate;
    EditText etClip;

    String orgTxt;
    String modTxt;
    int bookmark;

    Clip orgClip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);

        tvEditDate = findViewById(R.id.tvEditDate);
        etClip = findViewById(R.id.etClip);

        if (getIntent() != null) {

            orgClip = getIntent().getParcelableExtra("clip");
            bookmark = getIntent().getIntExtra("bookmark", 0);

            orgTxt = orgClip.getContent();
            etClip.setText(orgTxt);
            tvEditDate.setText(orgClip.getDate());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuMain; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miSave: {

                modTxt = etClip.getText().toString().trim();
                Clip clip = new Clip(modTxt, orgClip.getDate(), bookmark);
                Log.e(TAG, "onOptionsItemSelected: Bookmarked = " + bookmark);
                if (!orgTxt.equals(modTxt)) {

                    ClipApplication.getClipDb().getClipDao().deleteClip(orgClip);
                    ClipApplication.getClipDb().getClipDao().insertClip(clip);

                }
                finish();
                return true;
            }

            case R.id.miDiscard: {

                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
