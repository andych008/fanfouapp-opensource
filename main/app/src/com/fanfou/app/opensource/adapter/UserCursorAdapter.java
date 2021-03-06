/*******************************************************************************
 * Copyright 2011, 2012, 2013 fanfou.com, Xiaoke, Zhang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.fanfou.app.opensource.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanfou.app.opensource.R;
import com.fanfou.app.opensource.api.bean.User;
import com.fanfou.app.opensource.ui.ActionManager;
import com.fanfou.app.opensource.util.DateTimeHelper;

/**
 * @author mcxiaoke
 * @version 1.0 2011.06.04
 * @version 1.5 2011.10.24
 * @version 1.6 2011.11.07
 * @version 1.7 2011.11.09
 * 
 */
public class UserCursorAdapter extends BaseCursorAdapter {
    private static class ViewHolder {

        final ImageView headIcon;
        final ImageView lockIcon;
        final TextView nameText;
        final TextView idText;
        final TextView dateText;
        final TextView genderText;
        final TextView locationText;

        ViewHolder(final View base) {
            this.headIcon = (ImageView) base.findViewById(R.id.item_user_head);
            this.lockIcon = (ImageView) base.findViewById(R.id.item_user_flag);
            this.nameText = (TextView) base.findViewById(R.id.item_user_name);
            this.idText = (TextView) base.findViewById(R.id.item_user_id);
            this.dateText = (TextView) base.findViewById(R.id.item_user_date);
            this.genderText = (TextView) base
                    .findViewById(R.id.item_user_gender);
            this.locationText = (TextView) base
                    .findViewById(R.id.item_user_location);

        }
    }

    private static final String tag = UserCursorAdapter.class.getSimpleName();

    public UserCursorAdapter(final Context context, final Cursor c) {
        super(context, c, false);
    }

    public UserCursorAdapter(final Context context, final Cursor c,
            final boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public void bindView(final View view, final Context context,
            final Cursor cursor) {
        final View row = view;
        final ViewHolder holder = (ViewHolder) row.getTag();
        final User u = User.parse(cursor);
        if (!isTextMode()) {
            holder.headIcon.setTag(u.profileImageUrl);
            this.mLoader.displayImage(u.profileImageUrl, holder.headIcon,
                    R.drawable.default_head);
            holder.headIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    if (u != null) {
                        ActionManager.doProfile(
                                UserCursorAdapter.this.mContext, u);
                    }
                }
            });
        }
        if (u.protect) {
            holder.lockIcon.setVisibility(View.VISIBLE);
        } else {
            holder.lockIcon.setVisibility(View.GONE);
        }
        holder.nameText.setText(u.screenName);
        holder.idText.setText("(" + u.id + ")");
        holder.dateText.setText(DateTimeHelper.formatDateOnly(u.createdAt));
        holder.genderText.setText(u.gender);
        holder.locationText.setText(u.location);

    }

    @Override
    int getLayoutId() {
        return R.layout.list_item_user;
    }

    @Override
    public View newView(final Context context, final Cursor cursor,
            final ViewGroup parent) {
        final View view = this.mInflater.inflate(getLayoutId(), null);
        final ViewHolder holder = new ViewHolder(view);
        setHeadImage(this.mContext, holder.headIcon);
        // setTextStyle(holder);
        view.setTag(holder);
        // bindView(view, context, cursor);
        return view;
    }

}
