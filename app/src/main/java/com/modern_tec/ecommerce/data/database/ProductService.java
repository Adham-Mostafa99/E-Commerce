package com.modern_tec.ecommerce.data.database;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.modern_tec.ecommerce.core.models.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductService {
    private static final String PRODUCT_IMAGES_FOLDER_NAME = "Product Images";
    private static final String PRODUCT_REF_NAME = "Products";
    private final DatabaseReference productReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(PRODUCT_IMAGES_FOLDER_NAME);


    private MutableLiveData<Boolean> isProductUpload = new MutableLiveData<>();
    private MutableLiveData<Boolean> isProductApproved = new MutableLiveData<>();
    private MutableLiveData<Boolean> isProductDeleted = new MutableLiveData<>();
    private MutableLiveData<List<Product>> productMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> singleProductMutableLiveData = new MutableLiveData<>();


    private Context context;
    private Product product;

    public ProductService(Context context) {
        this.context = context;
    }

    public void storeProductInformation(Product product1, boolean isPhotoUpdated) {
        this.product = product1;
        if (isPhotoUpdated)
            storeProductImage(product.getProductId(), Uri.parse(product.getProductImageUrl()));
        else
            storeProductInfoToDatabase(product);
    }

    private void storeProductImage(String imageId, Uri imageUri) {
        StorageReference filePath = storageReference.child(imageId + ".jpg");

        UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask
                .addOnSuccessListener(taskSnapshot -> {
                    getImageDownloadImageUrl(uploadTask, filePath);
                    Toast.makeText(context, "Product Image Upload Successfully...", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });


    }

    private void getImageDownloadImageUrl(UploadTask uploadTask, StorageReference filePath) {
        uploadTask
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            product.setProductImageUrl(task.getResult().toString());
                            storeProductInfoToDatabase(product);
                        }
                    }
                });
    }

    private void storeProductInfoToDatabase(Product product) {


        productReference
                .child(PRODUCT_REF_NAME)
                .child(product.getProductId())
                .setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            isProductUpload.setValue(true);
                            Toast.makeText(context, "Product is successfully uploaded...", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void getUnApprovedProducts() {
        productReference
                .child(PRODUCT_REF_NAME)
                .orderByChild("productState")
                .equalTo("Not Approved")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Product> productArrayList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                Product currentProduct = dataSnapshot.getValue(Product.class);
                                productArrayList.add(currentProduct);
                            }
                        }
                        productMutableLiveData.setValue(productArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getProducts() {
        productReference
                .child(PRODUCT_REF_NAME)
                .orderByChild("productState")
                .equalTo("Approved")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Product> productArrayList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                Product currentProduct = dataSnapshot.getValue(Product.class);
                                productArrayList.add(currentProduct);
                            }
                        }
                        productMutableLiveData.setValue(productArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }


    public void getSellerProducts(String sellerId) {
        productReference
                .child(PRODUCT_REF_NAME)
                .orderByChild("sellerId")
                .equalTo(sellerId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        ArrayList<Product> productArrayList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                Product currentProduct = dataSnapshot.getValue(Product.class);
                                productArrayList.add(currentProduct);
                            }
                        }
                        productMutableLiveData.setValue(productArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void getProductById(String id) {
        productReference
                .child(PRODUCT_REF_NAME)
                .child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Product product = snapshot.getValue(Product.class);
                            singleProductMutableLiveData.setValue(product);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public void deleteProductById(String id) {
        productReference
                .child(PRODUCT_REF_NAME)
                .child(id)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            isProductDeleted.setValue(true);
                        }

                    }
                });
    }


    public void approveProduct(String id) {
        productReference
                .child(PRODUCT_REF_NAME)
                .child(id)
                .child("productState")
                .setValue("Approved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        isProductApproved.setValue(true);
                    }
                });
    }

    public void getProductByName(String productName) {
        productReference
                .child(PRODUCT_REF_NAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        List<Product> productList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                if (product.getProductName().equals(productName))
                                    productList.add(product);
                            }
                        }
                        productMutableLiveData.setValue(productList);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }

    public LiveData<Boolean> getIsProductUpload() {
        return isProductUpload;
    }

    public LiveData<List<Product>> getProductMutableLiveData() {
        return productMutableLiveData;
    }

    public LiveData<Product> getSingleProductMutableLiveData() {
        return singleProductMutableLiveData;
    }

    public LiveData<Boolean> getIsProductApproved() {
        return isProductApproved;
    }

    public LiveData<Boolean> getIsProductDeleted() {
        return isProductDeleted;
    }
}
