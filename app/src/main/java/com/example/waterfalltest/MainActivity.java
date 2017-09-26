package com.example.waterfalltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private List<String> lists;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initDate();//初始化列表

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置RecyclerView的布局管理
        // mRecyclerView.addItemDecoration();//设置RecyclerView中item的分割线，用的少，一般都用在item中设置margin分隔两个item
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置item的添加删除动画，采用默认的动画效果
        adapter = new MyAdapter(this,lists);
        mRecyclerView.setAdapter(adapter);//设置adapter
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {    //添加监听器
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"点击的是：" + position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"长按的是：" + position,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initDate() {
        lists = new ArrayList<String>();
        for(int i = 'A';i < 'z';i++){
            lists.add("" + (char)i);
        }
    }

    //将menu加载到toolbar上


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_add:
                lists.add(1,"add");
                //adapter.notifyDataSetChanged();//用这个则可以更新数据，但是没有动画效果
                adapter.notifyItemInserted(1);//注意：RecyclerView中添加用notifyItemInserted()；才有动画效果
                break;
            case R.id.action_delete:
                lists.remove(1);
                adapter.notifyItemRemoved(1);//RecyclerView中删除用notifyItemRemoved()；才有动画效果
                break;
            case R.id.action_linear:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置RecyclerView的
                // GridLayoutManager布局管理,默认垂直，还有一个设置水平排列的构造方法
                break;
            case R.id.action_grid:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));//设置RecyclerView的GridLayoutManager布局管理，默认为垂直
                break;
            case R.id.action_grid_horizontal:
                //设置RecyclerView的GridLayoutManager的水平布局管理，参数分别为context，列数或行数，排列方式，是否反转布局的内容
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
                break;
            case R.id.action_staggeredgrid:
                //设置RecyclerView的StaggeredGridLayoutManager的布局管理，它是GridLayout升级版，可以显示交错式网格布局，参数分别为列数或行数，排列方式
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
