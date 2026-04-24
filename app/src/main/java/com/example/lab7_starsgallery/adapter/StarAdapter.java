package com.example.lab7_starsgallery.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.lab7_starsgallery.R;
import com.example.lab7_starsgallery.beans.Star;
import com.example.lab7_starsgallery.service.StarService;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private List<Star> allStars;
    private List<Star> filteredStars;
    private Context context;
    private StarFilter starFilter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.allStars = stars;
        this.filteredStars = new ArrayList<>(stars);
        this.starFilter = new StarFilter(this);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_star, parent, false);
        return new StarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star s = filteredStars.get(position);
        holder.starId.setText(String.valueOf(s.getId()));
        holder.starName.setText(s.getName());
        holder.starRating.setRating(s.getStar());

        Glide.with(context)
                .load(s.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .circleCrop())
                .into(holder.starImage);

        holder.itemView.setOnClickListener(view -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_ID) return;
            Star clicked = filteredStars.get(pos);
            showEditDialog(clicked, holder);
        });
    }

    private void showEditDialog(Star clicked, StarViewHolder holder) {
        View popup = LayoutInflater.from(context).inflate(R.layout.dialog_edit_star, null, false);
        CircleImageView popupImg = popup.findViewById(R.id.dialogStarImage);
        RatingBar popupBar       = popup.findViewById(R.id.dialogRatingBar);
        TextView popupId         = popup.findViewById(R.id.dialogStarId);

        popupId.setText(String.valueOf(clicked.getId()));
        popupBar.setRating(clicked.getStar());

        Glide.with(context)
                .load(clicked.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .circleCrop())
                .into(popupImg);

        new AlertDialog.Builder(context)
                .setTitle(clicked.getName())
                .setMessage("Select a rating from 1 to 5:")
                .setView(popup)
                .setPositiveButton("Save", (dialog, which) -> {
                    float newRating = popupBar.getRating();
                    int id = Integer.parseInt(popupId.getText().toString());
                    Star star = StarService.getInstance().findById(id);
                    if (star != null) {
                        star.setStar(newRating);
                        StarService.getInstance().update(star);
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override public int getItemCount() { return filteredStars.size(); }
    @Override public Filter getFilter() { return starFilter; }

    public static class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView starImage;
        TextView starId, starName;
        RatingBar starRating;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            starImage  = itemView.findViewById(R.id.starImage);
            starId     = itemView.findViewById(R.id.starId);
            starName   = itemView.findViewById(R.id.starName);
            starRating = itemView.findViewById(R.id.starRating);
        }
    }

    public class StarFilter extends Filter {
        private final RecyclerView.Adapter adapter;
        public StarFilter(RecyclerView.Adapter adapter) { this.adapter = adapter; }

        @Override
        protected FilterResults performFiltering(CharSequence query) {
            List<Star> result = new ArrayList<>();
            if (query == null || query.length() == 0) {
                result.addAll(allStars);
            } else {
                String pattern = query.toString().toLowerCase().trim();
                for (Star s : allStars)
                    if (s.getName().toLowerCase().startsWith(pattern))
                        result.add(s);
            }
            FilterResults fr = new FilterResults();
            fr.values = result;
            fr.count  = result.size();
            return fr;
        }

        @Override
        protected void publishResults(CharSequence query, FilterResults results) {
            filteredStars = (List<Star>) results.values;
            adapter.notifyDataSetChanged();
        }
    }
}
