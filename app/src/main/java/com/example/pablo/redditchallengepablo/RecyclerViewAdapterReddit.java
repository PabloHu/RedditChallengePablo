package com.example.pablo.redditchallengepablo;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pablo.redditchallengepablo.RedditFragment.OnListFragmentInteractionListener;
import com.example.pablo.redditchallengepablo.data.modelreddit.Child;
import com.example.pablo.redditchallengepablo.data.modelreddit.RedditResponse;
//import com.example.pablo.redditchallengepablo.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

import static android.content.ContentValues.TAG;


public class RecyclerViewAdapterReddit extends RecyclerView.Adapter<RecyclerViewAdapterReddit.ViewHolder> {

    public static final String TAG = "Adapter";
    //private final List<DummyItem> mValues;
    //private final OnListFragmentInteractionListener mListener;
    List<Child> childList = new ArrayList<>();
    Context context;

    public RecyclerViewAdapterReddit(List<Child> childList) {
        this.childList = childList;
        // mValues = items;
        // mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


      //  holder.tvAuthor.setText(redditResponseList.get(0).getData().getChildren().get(position).getData().getAuthor());
       // holder.tvTitle.setText(redditResponseList.get(0).getData().getChildren().get(position).getData().getTitle());
        holder.tvAuthor.setText(childList.get(position).getData().getAuthor());
        holder.tvTitle.setText(childList.get(position).getData().getTitle());

       // holder.tvNumComments.setText(redditResponseList.get(0).getData().getChildren().get(position).getData().getNumComments().toString() + " Comments");
       // holder.tvUps.setText(redditResponseList.get(0).getData().getChildren().get(position).getData().getUps().toString() + " Ups");
      //  holder.tvDowns.setText(redditResponseList.get(0).getData().getChildren().get(position).getData().getDowns().toString() + " Downs");
        holder.tvNumComments.setText(childList.get(position).getData().getNumComments().toString() + " Comments");
        holder.tvUps.setText(childList.get(position).getData().getUps().toString() + " Ups");
        holder.tvDowns.setText(childList.get(position).getData().getDowns().toString() + " Downs");

        Glide.with(context)
             //   .load(redditResponseList.get(0).getData().getChildren().get(position).getData().getThumbnail())
                .load(childList.get(position).getData().getThumbnail())
                .error(R.mipmap.ic_launcher)
                .into(holder.ivUserPhoto);

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "" + holder.tvAuthor.getText(), Toast.LENGTH_SHORT).show();
                String messageToSend  = "Check out what " + holder.tvAuthor.getText()+ " said on Reddit: \""+ holder.tvTitle.getText()+"\"";

                try {


                    Resources resources = context.getResources();

                    Intent emailIntent = new Intent();
                    emailIntent.setAction(Intent.ACTION_SEND);
                    // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
                    emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(messageToSend));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.setType("message/rfc822");

                    PackageManager pm = context.getPackageManager();
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");


                    Intent openInChooser = Intent.createChooser(emailIntent, "Share this comment");

                    List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
                    List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
                    for (int i = 0; i < resInfo.size(); i++) {
                        // Extract the label, append it, and repackage it in a LabeledIntent
                        ResolveInfo ri = resInfo.get(i);
                        String packageName = ri.activityInfo.packageName;
                        if (packageName.contains("android.email")) {
                            emailIntent.setPackage(packageName);
                        } else if (packageName.contains("mms") || packageName.contains("android.gm")) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            if (packageName.contains("mms")) {
                                intent.putExtra(Intent.EXTRA_TEXT, messageToSend);
                            } else if (packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                                intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(messageToSend));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                                intent.setType("message/rfc822");
                            }

                            intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
                        }
                    }

                    // convert intentList to array
                    LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);

                    openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
                    context.startActivity(openInChooser);
                } catch (Exception e) {
                    Log.d(TAG, "intent error: " + e.toString());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        /*
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;
*/
        //private ImageView ivBookImg;
        private TextView tvAuthor;
        private TextView tvTitle;
        private ImageView ivUserPhoto;
        private TextView tvNumComments;
        private TextView tvUps;
        private TextView tvDowns;
        private LinearLayout llItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ivUserPhoto = (ImageView) view.findViewById(R.id.ivUserPhoto);
            tvNumComments = (TextView) view.findViewById(R.id.tvNumComments);
            tvUps = (TextView) view.findViewById(R.id.tvUps);
            tvDowns = (TextView) view.findViewById(R.id.tvDowns);
            llItem = (LinearLayout) view.findViewById(R.id.llItem);


            // mContentView = (TextView) view.findViewById(R.id.content);
        }

        /*
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
        */
    }


}
