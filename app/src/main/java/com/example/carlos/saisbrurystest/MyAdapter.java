package com.example.carlos.saisbrurystest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carlos.saisbrurystest.model.ItemDetails;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{

    private static ArrayList<ItemDetails> mItemDetails;
private LayoutInflater mInflater;

    public MyAdapter(Context context, ArrayList<ItemDetails> data){

        mItemDetails = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mItemDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
		 *   ViewHolder : Your code might call findViewById() frequently during the scrolling of ListView,
		 *   which can slow down performance. Even when the Adapter returns an inflated view for
		 *   recycling, you still need to look up the elements and update them.
		 *   A way around repeated use of findViewById() is to use the "view holder" design pattern.
		 *
		 */

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txt_itemName = (TextView) convertView.findViewById(R.id.item_name);
            holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.item_price);
            holder.txt_itemNumber = (EditText) convertView.findViewById(R.id.item_number);
/*            holder.itemImage = (ImageView) convertView.findViewById(R.id.item_image);*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_itemName.setText(mItemDetails.get(position).getName());
        holder.txt_itemPrice.setText(mItemDetails.get(position).getPrice());
        holder.txt_itemNumber.setText("1");
        /*        holder.itemImage.setImageResource(imgid[mItemDetails.get(position).getImageNumber() - 1]);
		imageLoader.DisplayImage("http://192.168.1.28:8082/ANDROID/images/BEVE.jpeg", holder.itemImage);
  */

        return convertView;
    }

    static class ViewHolder {
        TextView txt_itemName;
        TextView txt_itemPrice;
        EditText txt_itemNumber;
        TextView itemImage;
    }
}
