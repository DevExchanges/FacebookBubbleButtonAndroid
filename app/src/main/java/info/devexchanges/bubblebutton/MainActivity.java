package info.devexchanges.bubblebutton;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.webianks.library.PopupBubble;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<DummyData> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private PopupBubble popupBubble;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        setDummyData();

        popupBubble = (PopupBubble) findViewById(R.id.popup_bubble);

        //handling onclick bubble button
        popupBubble.setPopupBubbleListener(new PopupBubble.PopupBubbleClickListener() {
            @Override
            public void bubbleClicked(Context context) {
                Toast.makeText(MainActivity.this, "ListView updated!", Toast.LENGTH_SHORT).show();
            }
        });

        //attaching bubble button with recyclerview
        popupBubble.setRecyclerView(recyclerView);

        //add new items from a background thread after 8 seconds
        addNewContent();
    }

    private void setDummyData() {
        DummyData dummyData;
        for (int i = 0; i < 20; i++) {
            dummyData = new DummyData();
            dummyData.setName("RecyclerView item " + (i + 1));
            list.add(dummyData);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void addNewContent() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DummyData data;
                for (int i = 10; i > 0; i--) {
                    data = new DummyData();
                    data.setName("New RecyclerView item " + i);
                    list.add(0, data);
                }
                recyclerViewAdapter.notifyItemRangeInserted(0, 10);
                popupBubble.updateText("10 new items");
                popupBubble.activate();
            }
        }, 10000);

    }
}
