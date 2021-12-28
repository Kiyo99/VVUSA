package com.example.vvusa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class SellerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText productName, productPrice,
             serviceName, servicePrice;
    Button upload, upload2;
    ImageView productImage, serviceImage;
    Uri imageUri, downloadUri;
    String str_downloadUri, image_check;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = auth.getCurrentUser();
    String userid = firebaseUser.getUid();
    DocumentReference docRef = db.collection("Users").document(userid);
    final String randomKey = UUID.randomUUID().toString();
    ProgressDialog pd;
    RelativeLayout productsPage, servicesPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        productsPage = findViewById(R.id.productsPage);
        servicesPage = findViewById(R.id.servicesPage);

        servicesPage.setVisibility(View.GONE);


        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productImage = findViewById(R.id.productImage);
        serviceName = findViewById(R.id.serviceName);
        servicePrice = findViewById(R.id.servicePrice);
        serviceImage = findViewById(R.id.serviceImage);
        upload = findViewById(R.id.upload);
        upload2 = findViewById(R.id.upload2);

        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinnerProduct = findViewById(R.id.spinnerProduct);
        Spinner spinnerService = findViewById(R.id.spinnerService);
        Spinner spinnerPC = findViewById(R.id.productCondition);
        Spinner spinnerSC= findViewById(R.id.serviceCondition);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.option, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterP = ArrayAdapter.createFromResource(this, R.array.productCategories, android.R.layout.simple_spinner_dropdown_item);
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(this, R.array.serviceCategories, android.R.layout.simple_spinner_dropdown_item);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterPC = ArrayAdapter.createFromResource(this, R.array.conditionSpinner, android.R.layout.simple_spinner_dropdown_item);
        adapterPC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapterSC = ArrayAdapter.createFromResource(this, R.array.conditionSpinner, android.R.layout.simple_spinner_dropdown_item);
        adapterSC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerProduct.setAdapter(adapterP);
        spinnerService.setAdapter(adapterS);
        spinnerPC.setAdapter(adapterPC);
        spinnerSC.setAdapter(adapterSC);

        spinner.setOnItemSelectedListener(this);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_check="products";
                choosePicture(image_check);
            }
        });

        serviceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_check = "service";
                choosePicture(image_check);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SellerActivity.this);
                pd.setMessage("Uploading...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                String imageChecker = str_downloadUri;

                String str_productName = productName.getText().toString();
                String str_productCondition = spinnerPC.getSelectedItem().toString();
                String str_productCategory = spinnerProduct.getSelectedItem().toString();
                String str_productPrice = productPrice.getText().toString();
//                double double_productPrice = Double.parseDouble(str_productPrice);
//                || TextUtils.isEmpty(productImage)

                if(TextUtils.isEmpty(str_productName) || TextUtils.isEmpty(str_productCondition) || TextUtils.isEmpty(str_productCategory)
                        || TextUtils.isEmpty(str_productPrice))
                {
                    pd.dismiss();
                    Toast.makeText(SellerActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if(imageChecker == null){
                    pd.dismiss();
                    Toast.makeText(SellerActivity.this, "Please upload an image first", Toast.LENGTH_SHORT).show();
                }
                else{
                    savetoDatabase(str_productName, str_productCondition, str_productCategory, str_productPrice, str_downloadUri);
                }
            }
        });


        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SellerActivity.this);
                pd.setMessage("Uploading...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                String imageChecker = str_downloadUri;

                String str_serviceName = serviceName.getText().toString();
                String str_serviceCondition = spinnerSC.getSelectedItem().toString();
                String str_serviceCategory = spinnerService.getSelectedItem().toString();
                String str_servicePrice = servicePrice.getText().toString();

                if(TextUtils.isEmpty(str_serviceName) || TextUtils.isEmpty(str_serviceCondition) || TextUtils.isEmpty(str_serviceCategory)
                        || TextUtils.isEmpty(str_servicePrice))
                {
                    pd.dismiss();
                    Toast.makeText(SellerActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if(imageChecker == null){
                    pd.dismiss();
                    Toast.makeText(SellerActivity.this, "Please upload an image first", Toast.LENGTH_SHORT).show();
                }
                else{
                    savetoDatabase2(str_serviceName, str_serviceCondition, str_serviceCategory, str_servicePrice, str_downloadUri);
                }
            }
        });
    }



    private void savetoDatabase(String str_productName, String str_productCondition, String str_productCategory, String str_productPrice, String str_downloadUri) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String ID = document.getString("Student ID");


                        Map<String, Object> product = new HashMap<>();
                        product.put("productName", str_productName);
                        product.put("productCondition", str_productCondition);
                        product.put("productPrice", str_productPrice);
                        product.put("productImage", str_downloadUri);
                        product.put("Student ID", ID);


                        if (str_productCategory.equalsIgnoreCase("clothes")){

                            db.collection("Clothes").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if (str_productCategory.equalsIgnoreCase("Shoes")){

                            db.collection("Shoes").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if (str_productCategory.equalsIgnoreCase("Bags")){

                            db.collection("Bags").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if (str_productCategory.equalsIgnoreCase("Gadgets")){

                            db.collection("Gadgets").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if (str_productCategory.equalsIgnoreCase("Cosmetics")){

                            db.collection("Cosmetics").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if (str_productCategory.equalsIgnoreCase("Stationary")){

                            db.collection("Stationary").add(product).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }


//                        db.collection("Products").document(ID)
//                                .set(product)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        pd.dismiss();
//                                        Toast.makeText(SellerActivity.this, "Successfully uploaded your product", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        pd.dismiss();
//                                        Toast.makeText(SellerActivity.this, "Error uploading your product", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    } else {
                        Toast.makeText(SellerActivity.this, "Cannot find your ID", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(SellerActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void savetoDatabase2(String str_serviceName, String str_serviceCondition, String str_serviceCategory, String str_servicePrice, String str_downloadUri) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String ID = document.getString("Student ID");


                        Map<String, Object> service = new HashMap<>();
                        service.put("serviceName", str_serviceName);
                        service.put("serviceCondition", str_serviceCondition);
                        service.put("servicePrice", str_servicePrice);
                        service.put("serviceImage", str_downloadUri);
                        service.put("Student ID", ID);

                        if (str_serviceCategory.equalsIgnoreCase("Gadget Repairs")){

                            db.collection("Gadget Repairs").add(service).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your service", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your service", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if (str_serviceCategory.equalsIgnoreCase("Hair Dressing")){

                            db.collection("Hair Dressing").add(service).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your service", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your service", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if (str_serviceCategory.equalsIgnoreCase("Barbering")){

                            db.collection("Barbering").add(service).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your service", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your service", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if (str_serviceCategory.equalsIgnoreCase("Catering")){

                            db.collection("Catering").add(service).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Successfully uploaded your service", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(SellerActivity.this, "Error uploading your service", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    } else {
                        Toast.makeText(SellerActivity.this, "Cannot find your ID", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(SellerActivity.this, "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void choosePicture(String image_check) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            if (image_check.equalsIgnoreCase("products")){
                productImage.setImageURI(imageUri);
                uploadPicture(image_check);

            }
            else if (image_check.equalsIgnoreCase("service")){
                serviceImage.setImageURI(imageUri);
                uploadPicture(image_check);
            }
        }
    }

    private void uploadPicture(String image_check) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        if (image_check.equalsIgnoreCase("products")){
            // Create a reference to "ProductImage.jpg"
            StorageReference productImage = storageReference.child("Product images");

            // Create a reference to 'Product Images'
            StorageReference productImageRef = storageReference.child("Product images/" + randomKey);

            // While the file names are the same, the references point to different files
            productImage.getName().equals(productImageRef.getName());    // true
            productImage.getPath().equals(productImageRef.getPath());    // false

            UploadTask uploadTask = productImageRef.putFile(imageUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    // Handle unsuccessful uploads
                    pd.dismiss();
                    Toast.makeText(SellerActivity.this, "Failed to upload, try again", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    pd.dismiss();
                    Snackbar.make(findViewById(R.id.content3), "Image uploaded", Snackbar.LENGTH_LONG).show();

                    productImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Uri> task) {
                            str_downloadUri = task.getResult().toString();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(SellerActivity.this, "Failed to get URL", Toast.LENGTH_LONG).show();
                        }
                    });
//                getDownloadUrl();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Progress: " + (int) progressPercent + "%");
                }
            });

        }
        else if (image_check.equalsIgnoreCase("service")){
            // Create a reference to "ProductImage.jpg"
            StorageReference serviceImage = storageReference.child("Service images");

            // Create a reference to 'Product Images'
            StorageReference serviceImageRef = storageReference.child("Service images/" + randomKey);

            // While the file names are the same, the references point to different files
            serviceImage.getName().equals(serviceImageRef.getName());    // true
            serviceImage.getPath().equals(serviceImageRef.getPath());    // false

            UploadTask uploadTask = serviceImageRef.putFile(imageUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    // Handle unsuccessful uploads
                    pd.dismiss();
                    Toast.makeText(SellerActivity.this, "Failed to upload, try again", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    pd.dismiss();
                    Snackbar.make(findViewById(R.id.content3), "Image uploaded", Snackbar.LENGTH_LONG).show();

                    serviceImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Uri> task) {
                            str_downloadUri = task.getResult().toString();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(SellerActivity.this, "Failed to get URL", Toast.LENGTH_LONG).show();
                        }
                    });
//                getDownloadUrl();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Progress: " + (int) progressPercent + "%");
                }
            });

        }



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedItem = adapterView.getItemAtPosition(i).toString();
        if (selectedItem.equalsIgnoreCase("Products")){
            productsPage.setVisibility(View.VISIBLE);
            servicesPage.setVisibility(View.GONE);

        }
        else if (selectedItem.equalsIgnoreCase("Services")){
            productsPage.setVisibility(View.GONE);
            servicesPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}