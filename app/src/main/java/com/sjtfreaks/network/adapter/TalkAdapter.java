package com.sjtfreaks.network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjtfreaks.network.R;
import com.sjtfreaks.network.bean.TalkData;
import com.sjtfreaks.network.utils.PicassoUtils;

import java.util.List;

public class TalkAdapter extends BaseAdapter {
    private Context mContext;
    private List<TalkData> mList;
    private TalkData data;
    private LayoutInflater inflater;

    private WindowManager windowManager;
    private int width;

    public TalkAdapter(Context mContext, List<TalkData> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get系统服务
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        width = windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_talk,null);
            viewHolder.tv_title_talk = convertView.findViewById(R.id.tv_title_talk);
            viewHolder.im_talk = convertView.findViewById(R.id.im_talk);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tv_title_talk.setText(data.getTitle());
        String im_talk = data.getIm_ava();
        //加载图片
        PicassoUtils.loadImageView(mContext,im_talk,viewHolder.im_talk);
        return convertView;
    }

    class ViewHolder{
        private TextView tv_title_talk;//标题
        private ImageView im_talk;//时间
    }
}
