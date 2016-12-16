package com.app.vpgroup.ghinhocungmemopad;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.app.vpgroup.ghinhocungmemopad.customView.SecondLineEditTex;
import com.app.vpgroup.ghinhocungmemopad.model.MemoPad;
import com.app.vpgroup.ghinhocungmemopad.util.FontServices;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AbsActivity {
    private boolean isModeView = true;
    private MemoPad memoPad;
    private SecondLineEditTex content, createdDate;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        isModeView = getIntent().getBooleanExtra("isViewMode", false);
        long id = getIntent().getLongExtra("id", 0L);
        if (id > 0) {
            memoPad = MemoPad.findById(MemoPad.class, id);
        }
        content = (SecondLineEditTex) findViewById(R.id.content);
        createdDate = (SecondLineEditTex) findViewById(R.id.createdDate);
        FontServices.getInstance(this).setTypeface(content, createdDate);
        updateContent();
        invalidateOptionsMenu();
    }
    private void updateContent() {
        String date =
                new SimpleDateFormat("dd-MM/yyyy HH:mm").format(new Date(System.currentTimeMillis()));
        if (memoPad != null && !TextUtils.isEmpty(memoPad.content)) {
            content.setText(memoPad.content);
            if (memoPad.created_date > 0)
                date = new SimpleDateFormat("dd-MM/yyyy HH:mm").format(new Date(memoPad.created_date));
        }
        createdDate.setText(date);
    }

    private void updateMenu(Menu menu) {
        menu.findItem(R.id.action_save).setVisible(!isModeView);
        menu.findItem(R.id.action_edit).setVisible(isModeView);
        content.setEditable(!isModeView);
        content.setCursorVisible(!isModeView);
        content.setSelection(content.length());
        onHideKeyboard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        updateMenu(menu);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            onActionSave();
        } else if (id == R.id.action_edit) {
            onActionEdit();
        } else if (id == R.id.action_delete) {
            onActionDelete();
        }
        return true;
    }

    private void onActionDelete() {
        if (memoPad == null)
            return;
        memoPad.deleted = true;
        long deletedId = memoPad.save();
        if (deletedId > 0)
            finish();
        else
            showError();
    }

    private void showError() {
        final AlertDialog dialog =
                new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                        .setMessage("Error! Please contact developer to get support.\n\n")
                        .setCancelable(true).setPositiveButton("OK", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void onActionEdit() {
        isModeView = false;
        invalidateOptionsMenu();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void onActionSave() {
        if (TextUtils.isEmpty(content.getText().toString()))
            return;
        if (memoPad == null) {
            memoPad = new MemoPad();
            memoPad.created_date = System.currentTimeMillis();
        }
        memoPad.update_date = System.currentTimeMillis();
        memoPad.content = content.getText().toString();
        long savedId = memoPad.save();
        if (savedId > 0) {
            isModeView = true;
            invalidateOptionsMenu();
        } else {
            showError();
        }
    }

    public void onHideKeyboard() {
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() == null)
            return;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return 0;
    }
}
