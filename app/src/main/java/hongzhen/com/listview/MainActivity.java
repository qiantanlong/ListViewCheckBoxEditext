package hongzhen.com.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private MyAdapter adapter;
    private ArrayList<String> list;
    private ArrayList<String> isSelected;

    /*
    这里比较简单，没有太多东西，只是初始化相关数据
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
        setData();//初始化数据
        adapter = new MyAdapter(getApplicationContext(), list, isSelected);
        listview.setAdapter(adapter);
        //注意这里不要给listview设置条目单击事件，条目单击由textview单击事件来实现。要给checkbox、textview、editext分别处理点击事件
    }

    //初始化相关的数据
    private void setData() {
        list = new ArrayList<String>();
        isSelected = new ArrayList<String>();
        list.add("语文");
        list.add("数学");
        list.add("英语");
        list.add("政治");
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        for (int i = 0; i < list.size(); i++) {
            isSelected.add("false");
        }
    }
}
