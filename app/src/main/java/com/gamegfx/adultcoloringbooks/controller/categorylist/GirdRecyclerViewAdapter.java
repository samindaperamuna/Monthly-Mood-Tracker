package com.gamegfx.adultcoloringbooks.controller.categorylist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.gamegfx.adultcoloringbooks.MyApplication;
import com.gamegfx.adultcoloringbooks.R;
import com.gamegfx.adultcoloringbooks.factory.MyDialogFactory;
import com.gamegfx.adultcoloringbooks.factory.SharedPreferencesFactory;
import com.gamegfx.adultcoloringbooks.listener.OnUnLockImageSuccessListener;
import com.gamegfx.adultcoloringbooks.model.GridViewActivityModel;
import com.gamegfx.adultcoloringbooks.model.OnRecycleViewItemClickListener;
import com.gamegfx.adultcoloringbooks.model.bean.PictureBean;
import com.gamegfx.adultcoloringbooks.util.L;

import java.util.List;

public class GirdRecyclerViewAdapter extends RecyclerView.Adapter<GirdRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<PictureBean.Picture> pictureBeans;
    private int categoryid;
    private String folderimage;
    private boolean isLocal;
    private OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public GirdRecyclerViewAdapter(Context context, List<PictureBean.Picture> pictureBeans, int categoryid, String folderimage, boolean isLocal) {
        mContext = context;
        this.pictureBeans = pictureBeans;
        this.isLocal = isLocal;
        this.folderimage = folderimage;
        this.categoryid = categoryid;
    }

    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.onRecycleViewItemClickListener = onRecycleViewItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.view_gridview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String url = null;
        Log.d("url", "is local: " + isLocal);
        if (isLocal) {
            holder.image.setLayoutParams(new LinearLayout.LayoutParams(MyApplication.getScreenWidth(mContext) / 2, MyApplication.getScreenWidth(mContext) / 2));
            /*url = MyApplication.SECRETGARDENLOCATION + pictureBeans.get(position).getUri();*/
            url = "assets://" + this.folderimage + "/" + pictureBeans.get(position).getUri();
            Log.d("url", url);
            GridViewActivityModel.getInstance().showGridLocalImageAsyn(holder.image, url);

            //search images

        } else {
            if (position < pictureBeans.size() && pictureBeans.get(position).getWvHradio() != 0) {
                holder.image.setLayoutParams(new FrameLayout.LayoutParams(MyApplication.getScreenWidth(mContext) / 2, (int) (MyApplication.getScreenWidth(mContext) / 2 / pictureBeans.get(position).getWvHradio())));
                holder.enableImage.setLayoutParams(new FrameLayout.LayoutParams(MyApplication.getScreenWidth(mContext) / 2, (int) (MyApplication.getScreenWidth(mContext) / 2 / pictureBeans.get(position).getWvHradio())));
            } else {
                holder.image.setLayoutParams(new FrameLayout.LayoutParams(MyApplication.getScreenWidth(mContext) / 2, (int) (MyApplication.getScreenWidth(mContext) / 2 / 0.71)));
                holder.enableImage.setLayoutParams(new FrameLayout.LayoutParams(MyApplication.getScreenWidth(mContext) / 2, (int) (MyApplication.getScreenWidth(mContext) / 2 / 0.71)));
            }
            if (position == pictureBeans.size()) {
                holder.image.setImageResource(R.mipmap.comingsoon);
                holder.enableImage.setVisibility(View.GONE);
                holder.image.setEnabled(false);
                holder.localtag.setVisibility(View.INVISIBLE);
            } else {
                holder.localtag.setVisibility(View.VISIBLE);
                if (pictureBeans.get(position).getStatus() <= SharedPreferencesFactory.getInteger(mContext, SharedPreferencesFactory.ImageStatus, 0)) {
                    holder.image.setEnabled(true);
                    holder.enableImage.setVisibility(View.GONE);
                } else {
                    holder.image.setEnabled(false);
                    holder.enableImage.setVisibility(View.VISIBLE);
                    if (pictureBeans.get(position).getStatus() == 1) {
                        holder.enableImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MyDialogFactory myDialogFactory = new MyDialogFactory(mContext);
                                myDialogFactory.showUnLockdialog(pictureBeans.get(position).getStatus(), new OnUnLockImageSuccessListener() {
                                    @Override
                                    public void UnlockImageSuccess() {
                                        SharedPreferencesFactory.saveInteger(mContext, SharedPreferencesFactory.ImageStatus, 1);
                                    }
                                });
                            }
                        });
                    } else if (pictureBeans.get(position).getStatus() == 2) {
                        holder.enableImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MyDialogFactory myDialogFactory = new MyDialogFactory(mContext);
                                myDialogFactory.showUnLockdialog(pictureBeans.get(position).getStatus(), new OnUnLockImageSuccessListener() {
                                    @Override
                                    public void UnlockImageSuccess() {
                                        SharedPreferencesFactory.saveInteger(mContext, SharedPreferencesFactory.ImageStatus, 2);
                                    }
                                });
                            }
                        });
                    } else {
                        holder.enableImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MyDialogFactory myDialogFactory = new MyDialogFactory(mContext);
                                myDialogFactory.showPleaseUpdateVersionDialog();
                            }
                        });
                    }
                }
                url = String.format(MyApplication.ImageThumbUrl, categoryid, pictureBeans.get(position).getId());
                L.e(url);
                GridViewActivityModel.getInstance().showGridInternetImageAsyn(holder.image, holder.localtag, url);
            }
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRecycleViewItemClickListener != null)
                    onRecycleViewItemClickListener.recycleViewItemClickListener(holder.image, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (isLocal) {
            return pictureBeans.size();
        } else {
            return pictureBeans.size() + 1;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public ImageView enableImage;
        public ImageView localtag;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.gridImage);
            enableImage = itemView.findViewById(R.id.gridenableImage);
            localtag = itemView.findViewById(R.id.localtag);
        }
    }

}
