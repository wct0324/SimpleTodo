package com.codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_todo);

		this.lvItems = (ListView) findViewById(R.id.listView);
		this.items = new ArrayList<String>();
		this.readItems();
		this.itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		this.lvItems.setAdapter(itemsAdapter);
		this.items.add("First Item");
		this.items.add("Second Item");
		this.setupListViewListener();
	}

	public void addTodoItem(View view) {
		EditText etNewItem = (EditText) findViewById(R.id.editText);
		this.itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		this.saveItems();
	}

	private void setupListViewListener() {
		this.lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}

	private void readItems() {
		File f = this.getFilesDir();
		File todoFile = new File(f, "todo.txt");
		try {
			this.items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			this.items = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	private void saveItems() {
		File f = this.getFilesDir();
		File todoFile = new File(f, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, this.items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
