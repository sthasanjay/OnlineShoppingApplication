package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.onlineshoppingapplication.ItemDetailActivity;
import com.e.onlineshoppingapplication.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import model.Items;
import reusable.Reusable;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    Context mContext;
    List<Items> itemLists;

    public ItemsAdapter(Context mContext, List<Items> itemLists) {
        this.mContext = mContext;
        this.itemLists = itemLists;
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_items, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final Items item = itemLists.get(i);
        final String imagePath = Reusable.BASE_URL+"uploads/"+item.getImage();
        StrictMode();
        try {
            URL url = new URL(imagePath);
            itemViewHolder.imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        itemViewHolder.tvName.setText(item.getName());
        itemViewHolder.tvPrice.setText(item.getDesc());

        itemViewHolder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra("image", imagePath);
                intent.putExtra("price", item.getPrice()+"");
                intent.putExtra("name", item.getName());
                intent.putExtra("description", item.getDesc());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgItem;
        TextView tvName, tvPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
