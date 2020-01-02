package com.bhavaniprasad.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhavaniprasad.recyclerview.R;
//import com.bhavaniprasad.recyclerview.databinding.CategoryBinding;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomView> implements Filterable {


    private ArrayList<Repository> arrList;
    private ArrayList<Repository> arrListall;
    private Context cnt;
    private LayoutInflater layoutInflater;

    public CustomAdapter(Context context, ArrayList<Repository> arrayList) {
        this.cnt = context;
        this.arrList = arrayList;
        this.arrListall = new ArrayList<>(arrayList);
    }


    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.from(parent.getContext())
                .inflate(R.layout.rowlayout, parent, false);
        return new CustomView(view);
//        CategoryBinding categoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.rowlayout,parent,false);
//        return new CustomView(categoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        Repository repository = arrList.get(position);
        final CustomView RepositoryVH = (CustomView) holder;
        RepositoryVH.name.setText(arrList.get(position).getmName());
        RepositoryVH.description.setText(arrList.get(position).getmDescription());
        float starsNumber = arrList.get(position).getmStarsNumber(); // return the number of stars devided by 1000
        if (starsNumber > 1000) starsNumber /= 1000;
        arrList.get(position).setmStarsNumber(starsNumber);
        // get only the first digit afrer comma and then append the value with 'k'
        RepositoryVH.stars.setText(new DecimalFormat("##.#").format(starsNumber) + "k");
        RepositoryVH.username.setText(arrList.get(position).getOwner().getLogin());
        Picasso.with(holder.itemView.getContext()).load(arrList.get(position).getOwner()
                .getAvatar_url()).resize(200, 200).centerCrop().onlyScaleDown()
                .into(RepositoryVH.avatar);
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Repository> filteredList =  new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(arrListall);
            }
            else{
                try {
                    for(Repository list:arrListall){
                        if(list.getmDescription().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredList.add(list);
                        }
                    }
                }
                catch (Exception e){
                    Log.e("sdf","except"+e);
                    int t;
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrList.clear();
            arrList.addAll((Collection<? extends Repository>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class CustomView extends RecyclerView.ViewHolder {

        TextView name, description, username, stars;
        ImageView avatar;

        public CustomView(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
            stars = itemView.findViewById(R.id.stars);
        }
//        private CategoryBinding categoryBinding;
//        public CustomView(CategoryBinding categoryBinding) {
//            super(categoryBinding.getRoot());
//            this.categoryBinding=categoryBinding;
//        }
//
//        public void bind(Repository repository){
//            categoryBinding.setCategorymodel(repository);
//            categoryBinding.executePendingBindings();
//        }
//
//        public CategoryBinding getCategoryBinding(){
//            return categoryBinding;
//        }
//    }
    }
}
