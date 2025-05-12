package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etName, etAge, etMajor, etId;
    private ListView listView;
    private Component adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DatabaseHelper(MainActivity.this);
        listView = (ListView) findViewById(R.id.list);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnShow = findViewById(R.id.btnShow);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etMajor = findViewById(R.id.etMajor);
        etId = findViewById(R.id.etId);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String ageStr = etAge.getText().toString().trim();
                String major = etMajor.getText().toString().trim();

                if (name.isEmpty() || ageStr.isEmpty() || major.isEmpty() || id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int age = Integer.parseInt(ageStr);
                    long result = dbHelper.addStudent(id, name, age, major, R.drawable.person);
                    if (result > 0) {
                        Toast.makeText(MainActivity.this, "Student saved with ID: " + id, Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etName.setText("");
                        etAge.setText("");
                        etMajor.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to save to SQLite", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Exception:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Student> students = dbHelper.getAllStudents();
                if (students.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No student found", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Yow", "Called here baby abcxyz");
                    adapter = new Component(MainActivity.this, students, null, null, null);
                    listView.setAdapter(adapter);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<Student> students = dbHelper.getAllStudents();
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                Bundle bundle = new Bundle();

                Student student = students.get(position);

                bundle.putInt("id", student.getId());
                bundle.putString("name", student.getName());
                bundle.putInt("age", student.getAge());
                bundle.putString("major", student.getMajor());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}