package com.example.fuelisticv2client.fuelisticv2client.UI.ui.faq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelisticv2client.R;

import java.util.ArrayList;

public class faqAdapter extends RecyclerView.Adapter<faqAdapter.WordViewHolder>
{

    private final ArrayList<String> qList ;
    private final ArrayList<String> aList ;
    private final LayoutInflater mInflater;
    private final Context ct;

    class WordViewHolder extends RecyclerView.ViewHolder
                        implements View.OnClickListener
    {
        public TextView questionItemView;
        public TextView answerItemView;
        final faqAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, faqAdapter adapter)
        {
            super(itemView);
            questionItemView = itemView.findViewById(R.id.questionView);
            answerItemView = itemView.findViewById(R.id.answerView);
            answerItemView.setVisibility(View.GONE);
            this.mAdapter = adapter;
            questionItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = aList.get(mPosition);
            /*
                Change the word in the mWordList.
                aList.set(mPosition, "Clicked! " + element);
                Notify the adapter, that the data has changed so it can
                update the RecyclerView to display the data.
             */

            if(answerItemView.isShown())
            {
                Animate.slide_up(ct, answerItemView);
                answerItemView.setVisibility(View.GONE);
            }
            else
            {
                answerItemView.setVisibility(View.VISIBLE);
                Animate.slide_down(ct, answerItemView);
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    public faqAdapter(Context ctx, ArrayList<String> mList, ArrayList<String> nList)
    {
        mInflater = LayoutInflater.from(ctx);
        this.qList = mList;
        this.aList = nList;
        this.ct = ctx;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.faq_list_item,parent,
                false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position)
    {
        String qCurrent = qList.get(position);
        holder.questionItemView.setText(qCurrent);

        String aCurrent = aList.get(position);
        holder.answerItemView.setText(aCurrent);
        //holder.answerItemView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount()
    {
        return qList.size();
    }


    /*
    public static void slide_down(Context ctx, View v)
    {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null)
        {
            a.reset();
            if (v != null)
            {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_up(Context ctx, View v)
    {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null)
        {
            a.reset();
            if (v != null)
            {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

     */
}
