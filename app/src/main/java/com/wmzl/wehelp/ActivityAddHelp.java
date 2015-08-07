package com.wmzl.wehelp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityAddHelp extends Activity {

    Button btnPublishHelp;
    EditText editTextHelpTitle;

    //Data
    String strHelpTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help);

        btnPublishHelp=(Button)findViewById(R.id.btnPublishHelp);
        editTextHelpTitle=(EditText)findViewById(R.id.editTextHelpTitle);

        btnPublishHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strHelpTitle = editTextHelpTitle.getText().toString();
                HelpModel model = new HelpModel();
                model.setName(DataManager.getInstance().GetCurrentUserName());
                model.setHelpTitle(strHelpTitle);
                model.setDate(new SimpleDateFormat().format(new Date()).toString());
                DataManager.getInstance().SaveHelpInBackground(model);
                Toast.makeText(getApplicationContext(), "Publish Accomplished.", Toast.LENGTH_SHORT).show();

                FinishActivity();

            }
        });

    }

    private void FinishActivity() {
        setResult(RESULT_OK);
        this.finish();
    }

}
