package capstone.uoit.ca.lifenoteapp.signup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import capstone.uoit.ca.lifenoteapp.R;

/** @author Matthew Rosettis */
public class SignUpActivity extends AppCompatActivity {

    ImageView viewImage;
    Button b;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Setting an onClick for changing the profile image
        b=(Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        //Setting an onClick for continuing with the sign up process
        continueButton = (Button)findViewById(R.id.btnContinueSignUp1);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Retrieving inputted values for username and password
                EditText username = (EditText) findViewById(R.id.username);
                EditText passOne = (EditText) findViewById(R.id.passInitial);
                EditText passTwo = (EditText) findViewById(R.id.passConfirm);
                //Check if the fields are valid
                boolean userCheck = validateUser(username.getText().toString());
                boolean passCheck = validatePass(passOne.getText().toString(), passTwo.getText().toString());
                //Conditional check to see if all requirements are met
                if (userCheck && passCheck) {
                    //TODO Assign the username, password, and picture to an account and add that account to the database
//                    Intent registered = new Intent(this, PersonalFeed.class);
//                    startActivity(registered);
                    Toast.makeText(getApplicationContext(),
                            "Started from the bottom, now we here",
                            Toast.LENGTH_SHORT).show();
                } else if (!passCheck) {
                    //display in short period of time
                    Toast.makeText(getApplicationContext(),
                            "Passwords do not match, try again",
                            Toast.LENGTH_SHORT).show();
                } else if (!userCheck) {
                    //display in short period of time
                    Toast.makeText(getApplicationContext(),
                            "Username is invalid, please use only lowercase " +
                                    "and uppercase letters, numbers, +, -, *, .",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Checking that the desired username does not contain
    private boolean validateUser(String username){
        boolean userVerify = false;
        //TODO Check the server if the username isn't already taken, and doesn't contain explicit words
        if(!username.contains("shit")){ //Add more disallowed words and maybe reference a table instead
            userVerify = true;
        }
        return userVerify;
    }
    private boolean validatePass(String passInit, String passCheck){
        Log.d("password 1", passInit);
        Log.d("password 2",passCheck);
        return passInit.equals(passCheck);
    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Change Profile Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
//                        File photoFile = null;
//                        try{
//                            photoFile = createImageFile();
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                        if(photoFile != null) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(intent, 1);
//                    }
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {
                    Bundle extra = data.getExtras();
                    Bitmap bitmap = (Bitmap) extra.get("data");
                    viewImage.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("pathOfImageFromGallery*", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }
    //Attempting to save thier photo to their phone and then display the Full-Sized photo
    String photoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = "file:" + image.getAbsolutePath();
        galleryAddPic();
        return image;
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
