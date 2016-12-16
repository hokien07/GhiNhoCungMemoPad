package com.app.vpgroup.ghinhocungmemopad;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.app.vpgroup.ghinhocungmemopad.Adapter.MemoPadAdapter;
import com.app.vpgroup.ghinhocungmemopad.model.MemoPad;

import java.util.List;

public class MainActivity extends AbsActivity {
    private GridView gridView;

    @Override
    protected String getActionBarTitle() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_main_activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(10);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity.onCreate(...).new OnItemClickListener() {...}",
                        "onItemClick: -----> dsds");
                MemoPad memoPad = (MemoPad) parent.getAdapter().getItem(position);
                onActionAddOrViewMemoPad(memoPad);
            }
        });
    }

    @Override
    protected void onResume() {
        List<MemoPad> memoPads = MemoPad.find(MemoPad.class, "deleted = 0");
        if(memoPads == null){
            return;
        }
        MemoPadAdapter adapter = new MemoPadAdapter(this, memoPads);
        gridView.setAdapter(adapter);
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            onActionAddOrViewMemoPad(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActionAddOrViewMemoPad(MemoPad memoPad) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("isViewMode", memoPad != null);
        Bundle bundle = new Bundle();
        if (memoPad != null) {
            intent.putExtra("id", memoPad.getId());
            bundle.putSerializable(MemoPad.class.getName(), memoPad);
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
