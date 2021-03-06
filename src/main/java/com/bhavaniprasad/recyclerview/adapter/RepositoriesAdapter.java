package com.bhavaniprasad.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhavaniprasad.recyclerview.R;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nisrine on 19/01/2018.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Repository> repositories;
    private OnrepositoryListener Onrepositoryclick;

    public RepositoriesAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowlayout, parent, false);

        return new RepositoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                final RepositoryViewHolder RepositoryVH = (RepositoryViewHolder) holder;
                RepositoryVH.name.setText(repositories.get(position).getmName());
                RepositoryVH.description.setText(repositories.get(position).getmDescription());
                float starsNumber = repositories.get(position).getmStarsNumber(); // return the number of stars devided by 1000
                if (starsNumber > 1000) starsNumber /= 1000;
                repositories.get(position).setmStarsNumber(starsNumber);
                // get only the first digit afrer comma and then append the value with 'k'
                RepositoryVH.stars.setText(new DecimalFormat("##.#").format(starsNumber) + "k");
                RepositoryVH.username.setText(repositories.get(position).getOwner().getLogin());
                Picasso.with(holder.itemView.getContext()).load(repositories.get(position).getOwner()
                        .getAvatar_url()).resize(200, 200).centerCrop().onlyScaleDown()
                        .into(RepositoryVH.avatar);

    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }


    public static class RepositoryViewHolder extends  RecyclerView.ViewHolder {
        TextView name,description,username,stars;
        ImageView avatar;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
            stars = itemView.findViewById(R.id.stars);
        }
    }

    public interface OnrepositoryListener{
        void Onrepositoryclick(int index);
    }


}
