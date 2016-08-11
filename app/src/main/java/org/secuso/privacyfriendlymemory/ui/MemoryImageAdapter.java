package org.secuso.privacyfriendlymemory.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.secuso.privacyfriendlymemory.common.MemoryLayoutProvider;

/**
 * Created by Hannes on 21.05.2016.
 */
public class MemoryImageAdapter extends BaseAdapter{

    private final Context context;
    private final MemoryLayoutProvider layoutProvider;

    public MemoryImageAdapter(Context context, MemoryLayoutProvider layoutProvider){
        this.context = context;
        this.layoutProvider = layoutProvider;
    }

    @Override
    public int getCount() {
        return layoutProvider.getDeckSize();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ImageView card;
        if(convertView == null){
            card = new ImageView(context);

            int cardSize = layoutProvider.getCardSizePixel();

            card.setLayoutParams(new GridView.LayoutParams(cardSize, cardSize));
            card.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            card = (ImageView) convertView;
        }
        card.setImageResource(layoutProvider.getImageResID(position));
        return card;
    }

    public int dpToPx(int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }
}
