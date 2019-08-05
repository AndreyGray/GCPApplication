package candreyskakunenko.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import candreyskakunenko.myapplication.NavActivity;
import candreyskakunenko.myapplication.R;
import candreyskakunenko.myapplication.model.Menu;

public class DrawerAdapter  extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private  List<Menu> mMenuList;
    private Context mContext;



    public DrawerAdapter(List<Menu> menuList, Context context) {
        mMenuList = menuList;
        this.mContext  = context;
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_menu_item,parent,false);
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerViewHolder holder, final int position) {
        final Menu menu = mMenuList.get(position);
        if (menu != null){
            holder.menuName.setText(menu.getName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof NavActivity){
                    ((NavActivity)mContext).onItemSelected(menu);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    public void addDrawerMenuList(List<Menu> menuList){
        mMenuList = menuList;
        notifyDataSetChanged();
    }

     static class DrawerViewHolder extends RecyclerView.ViewHolder{
        TextView menuName;

         DrawerViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.tv_menu_item_name);
        }

    }

}
