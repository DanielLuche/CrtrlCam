package com.dluche.crtrlcam.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dluche.crtrlcam.R;

import java.util.ArrayList;

public class GalleryThumbPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<byte[]> source;
    private int resouce;

    public GalleryThumbPagerAdapter(Context context, ArrayList<byte[]> source) {
        this.context = context;
        this.source = source;
        this.resouce = R.layout.gallery_cell;
    }


    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        //Bitmap pictue = BitmapFactory.decodeFile(source.get(position));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resouce,container,false);
        //
        ImageView iv_main = view.findViewById(R.id.gallery_cell_iv_main);
        if(source.get(position) != null ) {
            iv_main.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                            source.get(position),
                            0,
                            source.get(position).length
                    )
            );
        }else{
            iv_main.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
        }
        container.addView(view);
        //
        return view;
        /*LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(params);
        container.addView(ll);
        //
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(BitmapFactory.decodeFile(source.get(position)));
        ll.addView(imageView);
        //
        return ll;*/
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
