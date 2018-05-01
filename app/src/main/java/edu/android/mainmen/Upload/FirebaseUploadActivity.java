package edu.android.mainmen.Upload;

import android.Manifest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import edu.android.mainmen.Controller.AllFoodDTO;
import edu.android.mainmen.R;

public class FirebaseUploadActivity extends AppCompatActivity {
    public static final String FOOD = "Food/";

    private static final String TAGSPINNER = "spinner";
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int GALLERY_CODE = 10;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private ImageView imageView;
    private EditText title , description , uploadStore;
    private Button upload_Button , cancelBtn ;
    private String imagePath;
    private TextView addLocation;
    private Spinner spinner;
    private RatingBar rb;
    private int spinnerPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_upload);
        hideActionBar();


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
        rb = findViewById(R.id.rb);
        cancelBtn = findViewById(R.id.cancelBtn);
        uploadStore = findViewById(R.id.upload_store);


        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {


                Toast.makeText(FirebaseUploadActivity.this, rating + "점!", Toast.LENGTH_SHORT).show();

            }
        });


        // 스피너
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

//        // 위치 설정(일단 사용 안함)
//        Intent intent = getIntent();
//        String address = intent.getStringExtra("getAddress");
//        addLocation.setText(address);

        // 업로드 버튼
        upload_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerPosition == 0) {

                    Toast.makeText(FirebaseUploadActivity.this, "음식 카테고리를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }else  if(imagePath == null) {
                    Toast.makeText(FirebaseUploadActivity.this, "사진을 넣어 주세요.", Toast.LENGTH_SHORT).show();

                }else if(("".equals(title.getText().toString()))) {
                    Toast.makeText(FirebaseUploadActivity.this, "음식 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();

                }else if(("버튼을 클릭하여 위치를 지정해주세요.".equals(addLocation.getText().toString()))){
                    Toast.makeText(FirebaseUploadActivity.this, "위치를 입력해주세요.", Toast.LENGTH_SHORT).show();

                }else if(("".equals(description.getText().toString()))){
                    Toast.makeText(FirebaseUploadActivity.this, "리뷰 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();


                }else if(rb.getRating() == 0){
                    Toast.makeText(FirebaseUploadActivity.this, "별점을 입력해주세요.", Toast.LENGTH_SHORT).show();


                }else{

                    upload(imagePath, spinnerPosition);
                    Log.i(TAGSPINNER, "position=" + spinnerPosition);
                    Toast.makeText(FirebaseUploadActivity.this, "업로드가 되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();


                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }// end onCreate()

//    ------------------------------------------------------------------------------------------------

    private void upload(String uri, int p) {

        // 우리 프로젝트 whattoeat 에서 Firebase storage 에 저장된것을 가져옴
        StorageReference storageRef = storage.getReferenceFromUrl("gs://whattoeat-9ce5d.appspot.com");

        final Uri file = Uri.fromFile(new File(uri));

        // 하위분류 메뉴

        // 키값 메뉴
        String foodmenu = null;
        if (p == 1) {
            foodmenu = "korea";
        } else if (p == 2) {
            foodmenu = "china";
        } else if (p == 3) {
            foodmenu = "western";
        } else if (p == 4) {
            foodmenu = "japan";
        }else if (p == 5) {
            foodmenu = "chiken";
        }else if (p == 6) {
            foodmenu = "snackbar";
        }else if (p == 7) {
            foodmenu = "fastfood";
        }else if (p == 8) {
            foodmenu = "bossam";
        }
        else {

        }

        //storage 저장소....
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());

        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        final String Foodmenu = foodmenu;

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

                AllFoodDTO allFoodDTO = new AllFoodDTO();
                allFoodDTO.imageUrl = downloadUrl.toString();
                allFoodDTO.title = title.getText().toString();
                allFoodDTO.description = description.getText().toString();
                allFoodDTO.uid = auth.getCurrentUser().getUid();
                allFoodDTO.userId = auth.getCurrentUser().getEmail();
                allFoodDTO.Location = addLocation.getText().toString();
                allFoodDTO.imageName = file.getLastPathSegment();
                allFoodDTO.ratingScore = rb.getRating();
                allFoodDTO.food = Foodmenu;
                allFoodDTO.storename = uploadStore.getText().toString();


//                allFoodDTO.usex = database.getReference().child("users").orderByChild("usersex").equalTo("남성").toString();
                database.getReference().child(FOOD).push().setValue(allFoodDTO);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_CODE) {


                if(data != null) {


                    imagePath = getPath(data.getData());        // 파일 경로저장
                    File f = new File(imagePath);               // File객체 생성
                    imageView.setImageURI(Uri.fromFile(f));     // view에 Uri로 가져와서 보여지게
                }else{
                    imageView.setImageURI(null);

                }




        } else if (requestCode == PLACE_PICKER_REQUEST) {

            if (data != null) {
                final Place place = PlacePicker.getPlace(this, data);
                //final CharSequence name = place.getName();

                final CharSequence address = place.getAddress();
                String attributions = (String) place.getAttributions();
                if (attributions == null) {
                    attributions = "";
                }
                addLocation.setText("");
                //addLocation.append(name);
                addLocation.append(address + "\n");
                addLocation.append(Html.fromHtml(attributions));

            } else {
                addLocation.setText("");
                //  super.onActivityResult(requestCode, resultCode, data);


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

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }


    // 맵 (일단 사용안함)

//    public void findByMyLocation(View view) {
//        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);
//    }
    // 맵

    public void findByMyLocation(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }


    public void findMarketLocation(View view) {

        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


    }


}// end Activity