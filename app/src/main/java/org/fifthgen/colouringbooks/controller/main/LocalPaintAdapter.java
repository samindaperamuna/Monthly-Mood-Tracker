package org.fifthgen.colouringbooks.controller.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.fifthgen.colouringbooks.MyApplication;
import org.fifthgen.colouringbooks.R;
import org.fifthgen.colouringbooks.controller.paint.PaintActivity;
import org.fifthgen.colouringbooks.model.AsynImageLoader;
import org.fifthgen.colouringbooks.model.bean.LocalImageBean;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by GameGFX Studio on 2015/9/1.
 */
public class LocalPaintAdapter extends RecyclerView.Adapter<LocalPaintAdapter.ViewHolder> {

    List<LocalImageBean> localImageListBean;
    Context context;

    public LocalPaintAdapter(Context context, List<LocalImageBean> localImageListBean) {
        if (localImageListBean == null) {
            localImageListBean = new ArrayList<>();
        }

        this.localImageListBean = localImageListBean;
        this.context = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.view_localimage_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AsynImageLoader.showImageAsynWithoutCache(holder.image, String.format(Locale.getDefault(),
                "file://%s", localImageListBean.get(position).getImageUrl()));

        holder.image.setLayoutParams(new LinearLayout.
                LayoutParams(MyApplication.getScreenWidth(context) / 5 * 3,
                (int) (MyApplication.getScreenWidth(context) / 5 * 3 / localImageListBean
                        .get(position).getWvHRadio())));

        holder.image.setOnClickListener(view -> gotoPaintActivity(String.format(Locale.getDefault(),
                "file://%s",
                localImageListBean.get(position).getImageUrl()),
                localImageListBean.get(position).getImageName()));

        holder.lastModifyTime.setText(String.format(Locale.getDefault(),
                "%s %s", context.getString(R.string.lastModifty),
                localImageListBean.get(position).getLastModDate()));
    }

    private void gotoPaintActivity(String uri, String filename) {
        int formatName = Integer.valueOf(filename.replace(".png", ""));

        Intent intent = new Intent(context, PaintActivity.class);
        intent.putExtra(MyApplication.BIGPICFROMUSER, uri);
        intent.putExtra(MyApplication.BIGPICFROMUSERPAINTNAME, formatName);

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return localImageListBean.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView lastModifyTime;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            lastModifyTime = itemView.findViewById(R.id.lastModify);
        }
    }
}
