package com.awelawi.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var to hold onclick
        val onLongClickListener = object : TaskItemAdapter.onLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //when item is longclicked
                //1. remove item from the list
                listOfTasks.removeAt(position)

                //2. notify adapter something has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }
        //Detect when the user clicks the add button
        //        findViewById<Button>(R.id.AddTaskButton)
        loadItems()
        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Initialize contacts
        // Create adapter passing in the sample user data
        adapter =TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)
        // That's all!
        val inputTextField = findViewById<EditText>(R.id.AddTaskView)
        //Set up the btn and input field so user can manage task
        findViewById<Button>(R.id.AddTaskButton).setOnClickListener {
            //1. Grab the text the user has inputed into taskfield
            val userInput = inputTextField.text.toString()
            //2 add string to list of tasks: listOfTasks
            listOfTasks.add(userInput)
            //Notify adapter that data has updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Clear out text field for new text to be added
            inputTextField.setText("")

            saveItems()
        }
    }
    //Save the data the user has implemented
    //save data by reading and writing from a file

    //create a method to get the file we need
    fun getDataFile(): File{
        // each line represents specific task
        return File(filesDir, "data.txt")
    }

    //Load the items by readin every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
    }
    }
    //Save items by writing them into our data file
    fun saveItems(){
       try {
           FileUtils.writeLines(getDataFile(), listOfTasks)
       } catch (ioException: IOException){
           ioException.printStackTrace()
       }
    }
}