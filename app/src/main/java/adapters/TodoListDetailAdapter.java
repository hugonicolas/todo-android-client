package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sandbox.R;
import com.example.sandbox.TodoListDetailActivity;

import java.util.List;

import models.Task;
import models.TodoList;

public class TodoListDetailAdapter extends RecyclerView.Adapter<TodoListDetailAdapter.CustomViewHolder> {
    private List<Task> tasks;
    private View.OnClickListener onClickListener;
    private Context context;

    public TodoListDetailAdapter(List<Task> tasks, View.OnClickListener onClickListener, Context context) {
        this.tasks = tasks;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int id;
        public int status;
        public final View mView;
        public TextView title;
        public Button deleteButton;
        public Button doneButton;
        public String created;
        public View.OnClickListener onClickListener;

        public CustomViewHolder(@NonNull View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            mView = itemView;
            title = mView.findViewById(R.id.taskTitle);
            deleteButton = mView.findViewById(R.id.deleteButton);
            doneButton = mView.findViewById(R.id.doneButton);
            this.onClickListener = onClickListener;
            deleteButton.setOnClickListener(this);
            doneButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == deleteButton.getId()) { //DELETE
                ((TodoListDetailActivity)context).deleteTask(this.id);

            } else if (id == doneButton.getId()) { //DONE
                ((TodoListDetailActivity)context).toggleTask(this.id);
            }
        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todolist_details_task, parent, false);
        return  new CustomViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Task currentTask = tasks.get(position);
        holder.title.setText(currentTask.getTitle());
        holder.id = currentTask.getId();
        holder.status = currentTask.getStatus();
        if (holder.status == 1) {
            holder.title.setPaintFlags(holder.title.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    public int getItemCount() {
        return tasks.size();
    }


}
