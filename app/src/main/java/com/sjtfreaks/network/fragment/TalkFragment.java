package com.sjtfreaks.network.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.sjtfreaks.network.R;
import com.sjtfreaks.network.adapter.TalkAdapter;
import com.sjtfreaks.network.bean.TalkData;
import com.sjtfreaks.network.utils.L;
import com.sjtfreaks.network.view.WebActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TalkFragment extends Fragment {
    private ListView mListView;
    private List<TalkData> mList = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    public static TalkFragment newInstance(String name){
        Bundle args = new Bundle();
        args.putString("name", name);
        TalkFragment fragment = new TalkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talk,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);
        mList.clear();
        String url = "https://www.v2ex.com/api/topics/hot.json";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
                L.i("json:"+t);
            }
        });
        //点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position:"+position);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                //2 way chuan zhi BUNdle
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            //JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonList = new JSONArray(t);

            for (int i = 0; i<jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);

                TalkData data = new TalkData();

                String url = json.getJSONObject("node").getString("url");
                String title = json.getJSONObject("node").getString("title");
                String im_ava = "https:"+json.getJSONObject("node").getString("avatar_normal");

                data.setTitle(json.getJSONObject("node").getString("title"));
                data.setIm_ava(im_ava);

                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);
            }
            TalkAdapter adapter = new TalkAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

}
