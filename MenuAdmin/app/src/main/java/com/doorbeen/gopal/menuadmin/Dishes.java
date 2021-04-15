package com.doorbeen.gopal.menuadmin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;


public class Dishes extends AppCompatActivity {

    private List<DishesModel> listDish;
    private DatabaseReference db;
    String uid;
    private Uri image;
    private RecyclerView recyclerView;
    private Dialog dishDialogue;
    private FirebaseUser user;
    private DishesAdapter dishesAdapter;
    private CircleImageView addImage;
    private ProgressBar progressBar;
    private EditText enter_dish_name;
    //private DishesAdapter dishesAdapter;
    private EditText enter_type_name;
    private EditText enter_price_name;
    private String downloadUrl;
    public static final int cellCount = 4;
    Button enter_add_buttton_dish;
    private String keyRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dishes );
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        setDishDialogue();

        Toolbar toolbar = findViewById( R.id.toolbar_dishes );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( getIntent().getStringExtra( "title" ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        progressBar = findViewById( R.id.progressBar_dish );
        keyRes = getIntent().getStringExtra( "key" );
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottomNavigationView );

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dish_navigation:

                        break;

                    case R.id.photo_navigation:

                        Intent intent = new Intent( Dishes.this, PhotosNavigation.class );
                        intent.putExtra( "key", getIntent().getStringExtra( "key" ) );
                        intent.putExtra( "title", getIntent().getStringExtra( "title" ) );
                        startActivity( intent );


                        break;

                }
                return true;
            }
        } );

        recyclerView = findViewById( R.id.recyclerVewDishes );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( Dishes.this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );

        recyclerView.setLayoutManager( linearLayoutManager );


        progressBar.setVisibility( View.VISIBLE );
        db = FirebaseDatabase.getInstance().getReference().child( "MenuAdmins" ).child( "Restaurants" ).child( uid );
        db.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listDish = new ArrayList<>();
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.child( "Names" ).child( keyRes )
                            .child( "Sets" ).getChildren()) {

                        String price = (String) dataSnapshot2.child( ("price") ).getValue();
                        String dish = (String) dataSnapshot2.child( ("dish") ).getValue();
                        String type = (String) dataSnapshot2.child( ("type") ).getValue();
                        String url = (String) dataSnapshot2.child( ("url2") ).getValue();

                        listDish.add( new DishesModel( url, dish, type, price, dataSnapshot2.getKey(), keyRes ) );


                    }
                    dishesAdapter = new DishesAdapter( listDish, new DishesAdapter.DeleteListners() {
                        @Override
                        public void onDeltes(final String key, final int position) {

                            new AlertDialog.Builder( Dishes.this ).setTitle( "Delete?" ).setMessage( "Are you sure you want to delete this Dish?" ).
                                    setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // deleting
                                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference( "MenuAdmins" ).child( "Restaurants" );
                                            myRef.child( uid ).child( "Names" ).child( keyRes ).child( "Sets" )
                                                    .child( key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        //listDish.remove( position );
                                                        //dishesAdapter.notifyDataSetChanged();
                                                        Toast.makeText( Dishes.this, "Deleted", Toast.LENGTH_SHORT ).show();

                                                    } else {
                                                        Toast.makeText( Dishes.this, "Failed to delete", Toast.LENGTH_SHORT ).show();
                                                    }
                                                }
                                            } );

                                        }
                                    } ).setNegativeButton( "No", null ).setIcon( android.R.drawable.ic_dialog_alert ).
                                    show();


                        }
                    } );
                    recyclerView.setAdapter( dishesAdapter );
                    dishesAdapter.notifyDataSetChanged();
                    progressBar.setVisibility( View.GONE );

                } else {
                    progressBar.setVisibility( View.GONE );
                    Toast.makeText( Dishes.this, "Something Went Wrong /n Restart the App", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility( View.GONE );
                Toast.makeText( Dishes.this, "Something Went Wrong /n Restart the App", Toast.LENGTH_SHORT ).show();


            }
        } );
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addd) {
            dishDialogue.show();
        } else if (item.getItemId() == R.id.excelAddDish) {
            uploadExcelData();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.logOutt) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent( Dishes.this, MainActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );

        } else if (item.getItemId() == R.id.aboutUss) {
            startActivity( new Intent( Dishes.this, AboutUsActivity.class ) );

        } else if (item.getItemId() == R.id.shareAppp) {
            Intent shareIntent = new Intent( Intent.ACTION_SEND );
            shareIntent.setType( "text/plain" );
            String sharebody = "Download this amazing online menu app.\n" +
                    "App that shows all nearby restaurants menu:- https://play.google.com/store/apps/details?id=com.doorbeen.gopal.menuadmin&hl=en";
            String shareSub = "Doorbeen App";

            shareIntent.putExtra( Intent.EXTRA_SUBJECT, shareSub );
            shareIntent.putExtra( Intent.EXTRA_TEXT, sharebody );

            startActivity( Intent.createChooser( shareIntent, "Share using" ) );
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.dish_dialogue_menu, menu );

        return super.onCreateOptionsMenu( menu );

    }


    private void setDishDialogue() {

        dishDialogue = new Dialog( this );
        dishDialogue.setContentView( R.layout.add_dish_dialogue );
        dishDialogue.getWindow().setBackgroundDrawable( getDrawable( R.drawable.rounded_corner_categories ) );
        dishDialogue.getWindow().setLayout( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        dishDialogue.setCancelable( true );

        addImage = dishDialogue.findViewById( R.id.image_holder_dish );
        enter_dish_name = dishDialogue.findViewById( R.id.dish_name_enter );
        enter_add_buttton_dish = dishDialogue.findViewById( R.id.add_dish );
        enter_type_name = dishDialogue.findViewById( R.id.dish_type_enter );
        enter_price_name = dishDialogue.findViewById( R.id.dish_price_enter );


        addImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission( Dishes.this, android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( Dishes.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 21 );

                    } else {

                        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                        startActivityForResult( galleryIntent, 101 );

                    }

                } else {

                    Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                    startActivityForResult( galleryIntent, 101 );

                }

            }
        } );

        enter_add_buttton_dish.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enter_dish_name.getText().toString().isEmpty()) {
                    enter_dish_name.setError( "Required" );
                    enter_dish_name.requestFocus();
                    return;
                } else if (enter_type_name.getText().toString().isEmpty()) {
                    enter_type_name.setError( "Required" );
                    enter_type_name.requestFocus();
                    return;
                } else if (enter_price_name.getText().toString().isEmpty()) {
                    enter_price_name.setError( "Required" );
                    enter_price_name.requestFocus();
                    return;
                }
                if (image != null) {
                    dishDialogue.dismiss();
                    progressBar.setVisibility( View.VISIBLE );
                    uploadDish();
                } else {
                    Toast.makeText( Dishes.this, "Please Select your Image", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult( requestCode, resultCode, data );

       /* if (data != null) {
            Log.i("IMAGELOG",String.valueOf( requestCode+" R Code:"+ requestCode+" ")+data.toString());
        }else {
            Log.i("IMAGELOG",String.valueOf( requestCode+" R Code:"+ requestCode+" "));
        }*/

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {

                Uri imageUri = null;
                if (data != null) {
                    imageUri = data.getData();
                    CropImage.activity( imageUri )
                            .setAspectRatio( 1, 1 )
                            .setMinCropWindowSize( 500, 500 )
                            .start( this );
                } else {
                    Toast.makeText( this, "Something went wrong", Toast.LENGTH_SHORT ).show();
                }
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)
                    image = result.getUri();
                addImage.setImageURI( image );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }

        if (requestCode == 222) {
            if (resultCode == RESULT_OK) {
                String filePath = data.getData().getPath();
                Log.i( "PAath" , filePath );
                if (filePath.endsWith( ".xlsx" )) {
                    try {
                        readFile( data.getData() );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText( this, "please choose excel file", Toast.LENGTH_SHORT ).show();

                }
            }
        }
    }

    private void uploadDish() {

        File thumb_filePath = new File( image.getPath() );
        //   File actualPhoto = new File( image.getLastPathSegment() );
        Bitmap thumb_bitmap = null;

        try {
            thumb_bitmap = new Compressor( this )
                    .setMaxWidth( 200 )
                    .setMaxHeight( 200 )
                    .setQuality( 75 )
                    .compressToBitmap( thumb_filePath );

        } catch (IOException e) {
            e.printStackTrace();//nikal acticity yehi hai dishhh vaala
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream );
        byte[] final_Image = byteArrayOutputStream.toByteArray();

        final StorageReference storageReference1 = FirebaseStorage.getInstance().getReference();

        final StorageReference imagereference1 = storageReference1.child( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Name" )
                .child( keyRes ).child( image.getLastPathSegment() );

        UploadTask uploadTask = imagereference1.putBytes( final_Image );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagereference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadUrl = task.getResult().toString();
                            uploadCategoryName();
                        } else {
                            Toast.makeText( Dishes.this, "Something went Wrong", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( Dishes.this, "Something went Wrong", Toast.LENGTH_SHORT ).show();

            }
        } );


    }

    //done
    private void uploadCategoryName() {


        Map<String, Object> map = new HashMap<>();
        map.put( "dish", enter_dish_name.getText().toString() );
        map.put( "type", enter_type_name.getText().toString() );
        map.put( "price", enter_price_name.getText().toString() );
        map.put( "url2", downloadUrl );

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference().child( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Names" ).child( keyRes ).child( "Sets" ).
                push().setValue( map ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

//                    listDish.add( new DishesModel( downloadUrl, enter_dish_name.getText().toString(), enter_type_name.getText().toString(),
//                            enter_price_name.getText().toString(), "dish" + (listDish.size() + 1), keyRes ) );
//                    //final DishesAdapter dishesAdapter = new DishesAdapter( listDish );
//                    progressBar.setVisibility( View.GONE );
//
//                    //dishesAdapter.notifyDataSetChanged();
                    Toast.makeText( Dishes.this, "Done", Toast.LENGTH_SHORT ).show();


                } else {
                    Toast.makeText( Dishes.this, "Something Went Wrong", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }

    // to upload excel data of dish
    private void uploadExcelData() {
        //permission check mark
        if (ActivityCompat.checkSelfPermission( Dishes.this, Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
            selectFile();
        } else {
            ActivityCompat.requestPermissions( Dishes.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102 ); // 102 ko change kiya hai 101 se aur neeche bhi

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectFile();
            } else {
                Toast.makeText( this, "Please Grant Permission!", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private void selectFile() {
        Intent intent = new Intent( Intent.ACTION_OPEN_DOCUMENT );
        intent.setType( "*/*" );
        intent.addCategory( Intent.CATEGORY_OPENABLE );
        startActivityForResult( Intent.createChooser( intent, "Select File" ), 222 );
    }

    private void readFile(Uri fileUri) throws FileNotFoundException {

        HashMap<String, Object> parentMap = new HashMap<>();
        final List<DishesModel> tempList = new ArrayList<>();


        try {

            InputStream inputStream = getContentResolver().openInputStream( fileUri );
            XSSFWorkbook workbook = new XSSFWorkbook( inputStream );
            XSSFSheet sheet = workbook.getSheetAt( 0 );
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();

            int rowsCount = sheet.getPhysicalNumberOfRows();
            if (rowsCount > 0) {
                for (int r = 0; r < rowsCount; r++) {
                    Row row = sheet.getRow( r );
                    if (row.getPhysicalNumberOfCells() == cellCount) {
                        String regex = "[+]?([0-9]*[.])?[0-9]+";

                        String url = getCellData( row, 0, formulaEvaluator );
                        String dishName = getCellData( row, 1, formulaEvaluator );
                        String dishType = getCellData( row, 2, formulaEvaluator );
                        String dishPrice = getCellData( row, 3, formulaEvaluator );

                        if (dishPrice.matches( regex )) {

                            final HashMap<String, Object> dishMap = new HashMap<>();
                            dishMap.put( "url2", url );
                            dishMap.put( "dish", dishName );
                            dishMap.put( "price", dishPrice );
                            dishMap.put( "type", dishType );

                            final String idRandom = UUID.randomUUID().toString();

                            parentMap.put( idRandom, dishMap );
                            //tempList.add( new DishesModel( url, dishName, dishType, dishPrice, idRandom, keyRes ) );

                        } else {
                            Toast.makeText( this, "Price is incorrect at row no." + (r + 1), Toast.LENGTH_SHORT ).show();
                            return;
                        }

                    } else {
                        Toast.makeText( this, "Row no. " + (r + 1) + " has incorrect data", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }
                firebaseDatabase1.getReference().child( "MenuAdmins" ).child( "Restaurants" ).child( uid ).child( "Names" ).child( keyRes ).child( "Sets" ).
                        updateChildren( parentMap ).addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT ).show();
                        } else {
                            Toast.makeText( Dishes.this, "Something Went wrongs", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
            } else {
                Toast.makeText( this, "File is Empty", Toast.LENGTH_SHORT ).show();
            }
        } catch (Exception e) {
            String error = e.getMessage();
            Toast.makeText( this, error, Toast.LENGTH_SHORT ).show();
        }
    }

    private String getCellData(Row row, int cellPosition, FormulaEvaluator formulaEvaluator) {

        String value = "";
        Cell cell = row.getCell( cellPosition );
        switch (cell.getCellType()) {

            case Cell.CELL_TYPE_BOOLEAN:
                return value + cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return value + cell.getNumericCellValue();

            case Cell.CELL_TYPE_STRING:
                return value + cell.getStringCellValue();

            default:
                return value;
        }
    }
}
