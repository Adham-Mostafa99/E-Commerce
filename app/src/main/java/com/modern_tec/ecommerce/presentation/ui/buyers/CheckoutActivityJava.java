package com.modern_tec.ecommerce.presentation.ui.buyers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentsClient;
import com.modern_tec.ecommerce.R;
import com.modern_tec.ecommerce.core.date.DateInfo;
import com.modern_tec.ecommerce.core.models.Address;
import com.modern_tec.ecommerce.core.models.CartProduct;
import com.modern_tec.ecommerce.core.models.Order;
import com.modern_tec.ecommerce.core.models.User;
import com.modern_tec.ecommerce.data.payment.Payment;
import com.modern_tec.ecommerce.databinding.ActivityCheckoutJavaBinding;
import com.modern_tec.ecommerce.presentation.adapters.CartAdapter;
import com.modern_tec.ecommerce.presentation.viewmodels.CartViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.OrderViewModel;
import com.modern_tec.ecommerce.presentation.viewmodels.UserViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.modern_tec.ecommerce.presentation.ui.buyers.BuyerAddressActivity.ADDRESS_EXTRA;
import static com.modern_tec.ecommerce.presentation.ui.buyers.PaymentActivity.CASH_PAYMENT;
import static com.modern_tec.ecommerce.presentation.ui.buyers.PaymentActivity.GOOGLE_PAYMENT;
import static com.modern_tec.ecommerce.presentation.ui.buyers.PaymentActivity.PAYMENT_METHOD_EXTRA;


public class CheckoutActivityJava extends AppCompatActivity {
    ActivityCheckoutJavaBinding binding;
    CartAdapter adapter;
    CartViewModel cartViewModel;
    UserViewModel userViewModel;
    OrderViewModel orderViewModel;
    PaymentsClient paymentsClient;

    List<CartProduct> cartProductList;
    Order order;
    User user;
    Address address = null;
    String paymentMethod = null;
    double subTotal = 0;
    double discount = 0;
    double shipping = 25;
    double total;
    String state = "not shipped";

    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initAdapter();
        initViewModels();
        initPayment();


        address = (Address) getIntent().getSerializableExtra(ADDRESS_EXTRA);

        if (address != null) {
            setAddressData();
        }

        paymentMethod = getIntent().getStringExtra(PAYMENT_METHOD_EXTRA);


        userViewModel.getUserInfo();
        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userInfo) {
                user = userInfo;
            }
        });


        cartViewModel.getUserProducts();

        cartViewModel.getCartUserProducts().observe(this, new Observer<ArrayList<CartProduct>>() {
            @Override
            public void onChanged(ArrayList<CartProduct> cartProducts) {

                if (cartProducts.size() == 0) {
                    //TODO handle null cart when delete all carts
                }

                adapter.submitList(cartProducts);

                cartProductList = cartProducts;

                subTotal = 0;//init subTotal every getting data

                for (CartProduct cartProduct : cartProducts) {
                    double productPrice = (cartProduct.getProductPrice() - (cartProduct.getProductPrice() * cartProduct.getProductDiscount())) * cartProduct.getProductQuantity();
                    subTotal = subTotal + productPrice;
                }

                total = subTotal - (subTotal * discount) + shipping;

                setPriceData();
            }
        });

        binding.checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateInfo dateInfo = new DateInfo();
                String id = dateInfo.getDate() + dateInfo.getTime();
                order = new Order(id, cartProductList, user.getName(), user.getEmail()
                        , address, dateInfo.getTime(), dateInfo.getDate(), total, state);

                order.setOrderPaymentMethod(paymentMethod);


                if (paymentMethod.equals(GOOGLE_PAYMENT)) {
                    binding.checkOutBtn.setClickable(false);

                    try {
                        Payment.loadPaymentData(activity, paymentsClient
                                , String.valueOf(total));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (paymentMethod.equals(CASH_PAYMENT)) {
                    orderViewModel.createOrder(order);

                }
            }
        });


        orderViewModel.getIsOrderAdded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    deleteCartContent();

                }
            }
        });

        cartViewModel.getIsAllProductDeletedFromCart().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startActivity(new Intent(CheckoutActivityJava.this, ConfirmationActivity.class));
                finish();
            }
        });


    }

    private void initPayment() {
        binding.checkOutBtn.setClickable(false);
        paymentsClient = Payment.getPaymentsClient(this);

        try {
            Payment.isReadyToPay(paymentsClient, this, new Payment.ReadyToPay() {
                @Override
                public void ready() {
                    binding.checkOutBtn.setClickable(true);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setPriceData() {

        binding.checkOutSubtotal.setText(subTotal + " EG");
        binding.checkOutDiscount.setText(discount + "%");
        binding.checkOutShipping.setText(shipping + " EG");
        binding.checkOutTotal.setText(total + " EG");

        binding.checkOutBtn.setText(total + " EG Buy");
    }

    private void setAddressData() {
        binding.checkOutAddressDetails.setText(address.getAddressName());
        binding.checkOutAddressCity.setText(address.getCity());
        binding.checkOutAddressPostalCode.setText(address.getPostalCode());
        binding.checkOutAddressPhone.setText(address.getPhone());
    }

    private void initViewModels() {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
    }

    private void initAdapter() {
        adapter = new CartAdapter(this);
        binding.checkOutRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.checkOutRecycler.setHasFixedSize(true);
        binding.checkOutRecycler.setAdapter(adapter);
        onChangeQuantity();
        onDeleteProduct();
    }

    private void onChangeQuantity() {
        adapter.setOnChangeQuantity(new CartAdapter.OnChangeQuantity() {
            @Override
            public void onChange(CartProduct cartProduct, int quantity) {
                cartProduct.setProductQuantity(quantity);
                cartViewModel.addProductToCart(cartProduct);
            }
        });
    }

    private void onDeleteProduct() {
        adapter.setOnDelete(new CartAdapter.OnDelete() {
            @Override
            public void onDelete(String id) {
                cartViewModel.deleteProductFromCart(id);
            }
        });
    }

    private void initBinding() {
        binding = ActivityCheckoutJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void deleteCartContent() {
        cartViewModel.deleteAllProducts();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case Payment.LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
//                        PaymentData paymentData = PaymentData.getFromIntent(data);
//                        handlePaymentSuccess(paymentData);

                        orderViewModel.createOrder(order);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        Log.v("Error:", status.getStatusCode() + "");
                        break;
                }

                // Re-enables the Google Pay payment button.
                binding.checkOutBtn.setClickable(true);
        }
    }
}