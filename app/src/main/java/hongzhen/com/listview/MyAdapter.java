package hongzhen.com.listview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhongzhen on 2016/10/11.
 */

public class MyAdapter extends BaseAdapter {

    List<String> textViewList;//传递过来的textview的内容
    Context context;
    private int index;//条目id
    private String[] editTextList;//存储edittext中的内容，防止复用时内容重复
    private List<String> isSelected;//存储checkbox的选择状态，防止复用时重复
    private myWatcher mWatcher;//文本监听


    public MyAdapter(Context context,List<String> textViewList,List<String> isSelected) {
        this.textViewList = textViewList;
        this.context=context;
//        this.editTextList=new ArrayList<>();

/*
 如果保存editext内容的是list集合，myWatcher类中的
        @Override
        public void afterTextChanged(Editable s) {
            这会会报错
            editTextList[index]=s.toString();//为输入的位置内容设置数组管理器，防止item重用机制导致的上下内容一样的问题
        }
        只有用数组来存储
  */
        this.editTextList=new String[textViewList.size()];

        this.isSelected=isSelected;
    }

    @Override
    public int getCount() {
        return textViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return textViewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item,null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.textView = (TextView) convertView.findViewById(R.id.tv);
            holder.editText = (EditText) convertView.findViewById(R.id.edt);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        holder.editText.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_UP){
                    index =position;
                }
                return false;
            }
        });
        holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //设置焦点监听，当获取到焦点的时候才给它设置内容变化监听解决卡的问题

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et=(EditText) v;
                if(mWatcher ==null){
                    mWatcher =new myWatcher();
                }
                if(hasFocus){
                    et.addTextChangedListener(mWatcher);//设置edittext内容监听
                }else {
                    et.removeTextChangedListener(mWatcher);
                }

            }
        });
        holder.editText.clearFocus();//防止点击以后弹出键盘，重新getview导致的焦点丢失
        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            holder.editText.requestFocus();
        }
        // 监听checkBox并根据原来的状态来设置新的状态
        holder.cb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (Boolean.parseBoolean(isSelected.get(position))) {
                    isSelected.add(position, "false");

                } else {
                    isSelected.add(position,"true");
                }

            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("内容",holder.cb.isChecked()+holder.editText.getText().toString().trim());
            }
        });
        holder.editText.setText(editTextList[position]);//这一定要放在clearFocus()之后，否则最后输入的内容在拉回来时会消失
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(Boolean.parseBoolean(isSelected.get(position)));
        holder.editText.setSelection(holder.editText.getText().length());
        holder.textView.setText(textViewList.get(position));//设置textview的内容

        return convertView;
    }
    class myWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub


        }

        @Override
        public void afterTextChanged(Editable s) {
            editTextList[index]=s.toString();//为输入的位置内容设置数组管理器，防止item重用机制导致的上下内容一样的问题
        }

    }
    class ViewHolder {
        EditText editText;
        TextView textView;
        CheckBox cb;
    }
}
