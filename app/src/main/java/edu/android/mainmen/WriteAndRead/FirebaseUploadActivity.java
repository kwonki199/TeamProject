package edu.android.mainmen.WriteAndRead;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import edu.android.mainmen.R;

public class FirebaseUploadActivity extends AppCompatActivity {
    private static final String TAGSPINNER = "spinner";

    private static final int GALLERY_CODE = 10;
    private static final int CAMERA_REQUEST = 18;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private ImageView imageView;
    private EditText title;
    private EditText description;
    private Button upload_Button;
    private String imagePath;
    private TextView addLocation;
    private Spinner spinner;
    private int spinnerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_upload);

        // 뒤로가기 버튼 이거랑 아래꺼
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();


        imageView = findViewById(R.id.upload_imageView);
        title = findViewById(R.id.upload_title);
        description = findViewById(R.id.upload_Description);
        upload_Button = findViewById(R.id.upload_button);
        addLocation = findViewById(R.id.addLocation);
        spinner = findViewById(R.id.selectCategorySp);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array,
                android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 갤러리 권한
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        }

//        // 위치 설정
//        Intent intent = getIntent();
//        String address = intent.getStringExtra("getAddress");
//        addLocation.setText(address);

        // 업로드 버튼
        upload_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(imagePath, spinnerPosition);
                Log.i(TAGSPINNER, "position=" + spinnerPosition);
                finish();
            }
        });


    }// end onCreate()

//    ------------------------------------------------------------------------------------------------

    private void upload(String uri, int p) {

        // 우리 프로젝트 whattoeat 에서 Firebase storage 에 저장된것을 가져옴
        StorageReference storageRef = storage.getReferenceFromUrl("gs://whattoeat-9ce5d.appspot.com");

        final Uri file = Uri.fromFile(new File(uri));


        final String menu;
        if (p == 1) {
            menu = "images/korea";
        } else if (p == 2) {
            menu = "images/china";
        } else if (p == 3) {
            menu = "images/western";
        }else if (p == 4) {
            menu = "images/japan";
        }
        else {
            menu = "images/";
        }

        //storage 저장소....
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());

        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.imageUrl = downloadUrl.toString();
                imageDTO.title = title.getText().toString();
                imageDTO.description = description.getText().toString();
                imageDTO.uid = auth.getCurrentUser().getUid();
                imageDTO.userId = auth.getCurrentUser().getEmail();
//                imageDTO.Location = addLocation.getText().toString();
                imageDTO.imageName = file.getLastPathSegment();

                database.getReference().child(menu).push().setValue(imageDTO);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_CODE) {
            imagePath = getPath(data.getData());        // 파일 경로저장
            File f = new File(imagePath);               // File객체 생성
            imageView.setImageURI(Uri.fromFile(f));     // view에 Uri로 가져와서 보여지게
        }

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bitmap imagePath = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(imagePath);
                //  File f = new File(String.valueOf(imagePath));
            }
        }
    }


    // 경로 저장

    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }


    // 사진

    public void getGallery(View view) {

        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        };

        // 앨범에서 사진 선택
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }

        };

        // 취소
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        };
        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNegativeButton("앨범선택", albumListener)
                .setNeutralButton("취소", cancelListener)
                .show();
        //setNeutralButton
    }


    // 맵

    public void findByMyLocation(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    // 뒤로가기 버튼 이거
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}// end Activity