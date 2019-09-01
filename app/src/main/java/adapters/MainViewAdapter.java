package adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sandbox.MainActivity;
import com.example.sandbox.R;
import com.example.sandbox.TodoListDetailActivity;

import java.util.List;

import models.TodoList;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.CustomViewHolder> {

    private List<TodoList> dataList;
    private View.OnClickListener onClickItemListener;
    private Context context;

    public MainViewAdapter(Context context,List<TodoList> dataList, View.OnClickListener onClickItemListener){
        this.context = context;
        this.dataList = dataList;
        this.onClickItemListener = onClickItemListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        int todolistid;
        TextView txtTitle;
        View.OnClickListener onClickItemListener;

        CustomViewHolder(View itemView, View.OnClickListener onClickItemListener) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.title);
            this.onClickItemListener = onClickItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context.getApplicationContext(), TodoListDetailActivity.class);
            intent.putExtra("todolistid", todolistid);
            intent.putExtra("todolistname", txtTitle.getText());
            context.startActivity(intent);
        }

        public void setTodolistid(int id) {
            this.todolistid = id;
        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todolist_item, parent, false);
        return new CustomViewHolder(view, onClickItemListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        TodoList currentTodoList = dataList.get(position);
        holder.txtTitle.setText(currentTodoList.getName());
        holder.setTodolistid(currentTodoList.getId());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}