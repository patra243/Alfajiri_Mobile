package com.example.alfajirimobileclassattendance.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alfajirimobileclassattendance.R;
import com.example.alfajirimobileclassattendance.constructor.SpinnerConstructor;

import java.util.List;
import java.util.Set;


public class SpinnerHelper<T> extends BaseAdapter {

    public static class SpinnerItem<T>{
        private String text;
        public T item;

        public SpinnerItem(T t, String s){
            item = t;
            text = s;
        }
    }

    public static class SpinnerCode<T>{
        private String code;
        public T tCode;

        public SpinnerCode(T tCode, String code){
            this.code = code;
            this.tCode = tCode;
        }
    }

    private Context context;
    private Set<T> selectItem;
    private Set<T> selectUnit;
    private List<SpinnerItem<T>> allItems;
    private List<SpinnerCode<T>> unitCode;
    private String headerText;

    public SpinnerHelper(Context context, String headerText, List<SpinnerItem<T>> allItems,
                         List<SpinnerCode<T>> unitCode, Set<T> selectItem, Set<T> selectUnit){
        this.context = context;
        this.headerText = headerText;
        this.allItems = allItems;
        this.unitCode = unitCode;
        this.selectItem = selectItem;
        this.selectUnit = selectUnit;
    }

    @Override
    public int getCount() {
        return allItems.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < 1){
            return null;
        }
        else{
            return allItems.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);

            holder = new ViewHolder();
            holder.linearLayout = convertView.findViewById(R.id.spinnerItem);
            holder.textView = convertView.findViewById(R.id.text1);
            holder.checkBox = convertView.findViewById(R.id.checkbox1);
            holder.code = convertView.findViewById(R.id.code1);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < 1){
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.code.setVisibility(View.INVISIBLE);
            holder.textView.setText(headerText);
        }
        else{
            final int listPosition = position - 1;
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.code.setVisibility(View.VISIBLE);
            holder.textView.setText(allItems.get(listPosition).text);
            holder.code.setText(unitCode.get(listPosition).code);

            final T item = allItems.get(listPosition).item;
            final T tCode = unitCode.get(listPosition).tCode;
            boolean isBool = selectItem.contains(item);

            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(isBool);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        selectItem.add(item);
                        selectUnit.add(tCode);
                    }
                    else{
                        selectItem.remove(item);
                        selectUnit.remove(tCode);
                    }
                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.checkBox.toggle();
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {

        private TextView textView;
        private CheckBox checkBox;
        private TextView code;
        private LinearLayout linearLayout;
    }
}
