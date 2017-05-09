package com.sheldon.mediashare;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.musicshare.sheldon.mediashare.user.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 17-4-13.
 */

public class NotificationFragment extends BaseFragment {
    private static final String TAG="NotificationFragment";

    private ListView mListview;
    private ListAdapter mListAdapter;
    private List<Notification> mListNotifications = new ArrayList<Notification>();

    private static final int[] NOTIFICATION_TYPES = new int[] {
            Notification.TYPE_SYSTEM,
            Notification.TYPE_GROUP,
            Notification.TYPE_FRIEND
    };

    private static final int[] NOTIFICATION_TITLES = new int[] {
            R.string.notification_category_system,
            R.string.notification_category_group,
            R.string.notification_category_friend
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_notification_fragment, null, false);
        mListview = (ListView) view.findViewById(R.id.lv_notification);
        mListAdapter = new ListAdapter();
        mListview.setAdapter(mListAdapter);

        if(Common.MOCK_MODE) {
            mockData();
        }

        return view;
    }

    private List<Notification> getNotificationsByType(final int type) {
        List<Notification> notifications = new ArrayList<Notification>();
        for(Notification noti : mListNotifications) {
            if(type == noti.type) {
                notifications.add(noti);
            }
        }
        return  notifications;
    }

    private class ListAdapter extends BaseAdapter {

        private LayoutInflater mListContainer;
        private final int colorPicker[] = {0xcc025683, 0xcc4d4e06, 0xcc095e01};

        public ListAdapter() {
            mListContainer = LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return NOTIFICATION_TYPES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListItemHolder holder;
            if(null == view) {
                view = mListContainer.inflate(R.layout.notification_list_item, null);
                LinearLayout layout_bg = (LinearLayout)view.findViewById(R.id.layout_bg);
                layout_bg.setBackgroundColor(colorPicker[i%colorPicker.length]);
                holder = new ListItemHolder();
                holder.tvTitle = (TextView) view.findViewById(R.id.tv_notif_title);
                //holder.tvUpdatetime = (TextView) view.findViewById(R.id.tv_notif_updatetime);
                holder.tvTop1 = (TextView) view.findViewById(R.id.tv_notif_item1);
                holder.tvTop2 = (TextView) view.findViewById(R.id.tv_notif_item2);
                holder.tvTop3 = (TextView) view.findViewById(R.id.tv_notif_item3);
                holder.tvTop4 = (TextView) view.findViewById(R.id.tv_notif_item4);
                holder.tvTop5 = (TextView) view.findViewById(R.id.tv_notif_item5);
                view.setTag(holder);
            } else {
                holder = (ListItemHolder) view.getTag();
            }

            List<Notification> list = getNotificationsByType(NOTIFICATION_TYPES[i]);
            Log.d(TAG, "getlist-"+list);
            int itemNum = list.size();
            if(list.size() > 0) {
                view.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(getContext().getText(NOTIFICATION_TITLES[i]));
                //holder.tvUpdatetime.setText();
                TextView textview=null;
                if(itemNum>0){textview=holder.tvTop1;textview.setText(list.get(0).content);textview.setVisibility(View.VISIBLE);}else{textview.setVisibility(View.GONE);}
                if(itemNum>1){textview=holder.tvTop2;textview.setText(list.get(1).content);textview.setVisibility(View.VISIBLE);}else{textview.setVisibility(View.GONE);}
                if(itemNum>2){textview=holder.tvTop3;textview.setText(list.get(2).content);textview.setVisibility(View.VISIBLE);}else{textview.setVisibility(View.GONE);}
                if(itemNum>3){textview=holder.tvTop4;textview.setText(list.get(3).content);textview.setVisibility(View.VISIBLE);}else{textview.setVisibility(View.GONE);}
                if(itemNum>4){textview=holder.tvTop5;textview.setText(list.get(4).content);textview.setVisibility(View.VISIBLE);}else{textview.setVisibility(View.GONE);}
            } else {
                view.setVisibility(View.GONE);
            }
            view.requestLayout();

            return view;
        }

        private class ListItemHolder {
            public TextView tvTitle;
            //public TextView tvUpdatetime;
            public TextView tvTop1;
            public TextView tvTop2;
            public TextView tvTop3;
            public TextView tvTop4;
            public TextView tvTop5;
        }
    }

    private void mockData() {
        mListNotifications.add(new Notification("you have new version of app.", "", Notification.TYPE_SYSTEM));
        mListNotifications.add(new Notification("你的帐号等级已经提升到了30级,非常感谢你对我们产品的支持.", "", Notification.TYPE_SYSTEM));
        mListNotifications.add(new Notification("尊敬的用户,你的在线时间过长.", "", Notification.TYPE_SYSTEM));
        mListNotifications.add(new Notification("你的好友summer找你", "", Notification.TYPE_SYSTEM));
        mListNotifications.add(new Notification("你的好友春天向你索取歌曲<<好日子>>", "", Notification.TYPE_FRIEND));
        mListNotifications.add(new Notification("用户goodsinger想你发起了好友申请.", "", Notification.TYPE_FRIEND));
        mListNotifications.add(new Notification("你的好友darknight向你索取歌曲<<rock you>>", "", Notification.TYPE_FRIEND));
        mListNotifications.add(new Notification("群<潮流风向标>正在讨论谁唱歌最好听,快来加入讨论吧", "", Notification.TYPE_GROUP));
        mListNotifications.add(new Notification("用户summer申请加入群<古典歌曲>,是否同意", "", Notification.TYPE_GROUP));
        mListNotifications.add(new Notification("你加入的群<good day>已经解散", "", Notification.TYPE_GROUP));
        mListNotifications.add(new Notification("好友summer邀请你加入群<最新歌曲>", "", Notification.TYPE_GROUP));
        mListNotifications.add(new Notification("用户spring退出群<这是一个无聊的春天>", "", Notification.TYPE_GROUP));
        mListNotifications.add(new Notification("你的群用户还有多少", "", Notification.TYPE_GROUP));

    }
}
