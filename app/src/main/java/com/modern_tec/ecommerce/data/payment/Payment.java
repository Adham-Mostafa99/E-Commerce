package com.modern_tec.ecommerce.data.payment;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Payment {

    public static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;


    public static PaymentsClient getPaymentsClient(Context context) {


        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build();

        return Wallet.getPaymentsClient(context, walletOptions);
    }


    private static JSONObject getTokenizationSpec() throws JSONException {
        return new JSONObject()
                .put("type", "PAYMENT_GATEWAY")
                .put("parameters", new JSONObject()
                        .put("gateway", "easypay")
                        .put("gatewayMerchantId", "4174476383491093"));

    }


    private static JSONObject getCardPaymentMethod() throws JSONException {
        final String[] networks = new String[]{"AMEX",
                "DISCOVER",
                "JCB",
                "MASTERCARD",
                "VISA"};
        final String[] authMethods = new String[]{"PAN_ONLY", "CRYPTOGRAM_3DS"};

        JSONObject card = new JSONObject();

        card.put("type", "CARD");
        card.put("tokenizationSpecification", getTokenizationSpec());
        card.put("parameters", new JSONObject()
                .put("allowedAuthMethods", new JSONArray(authMethods))
                .put("allowedCardNetworks", new JSONArray(networks)));

        return card;

    }


    private static JSONObject baseConfigurationJson() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0)
                .put("allowedPaymentMethods",
                        new JSONArray().put(getCardPaymentMethod()));
    }

    public static void isReadyToPay(PaymentsClient paymentsClient, Activity activity, ReadyToPay readyToPay) throws JSONException {
        IsReadyToPayRequest readyToPayRequest =
                IsReadyToPayRequest.fromJson(baseConfigurationJson().toString());


        Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);

        task.addOnCompleteListener(activity, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    if (task.getResult()) {
                        readyToPay.ready();
                    }
                } else {

                }
            }
        });
    }


    private static JSONObject getMerchantInfo() throws JSONException {
        JSONObject merchantInfo = new JSONObject();
        merchantInfo.put("merchantId", "BCR2DN6TU6QIKUI");
        merchantInfo.put("merchantName", "Example Merchant");

        return merchantInfo;
    }

    private static JSONObject getTransactionInfo(String price) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("currencyCode", "EGP");

        return transactionInfo;
    }

    public static void loadPaymentData(Activity activity, PaymentsClient paymentsClient, String price) throws JSONException {

        final JSONObject paymentRequestJson = baseConfigurationJson();

        paymentRequestJson.put("transactionInfo", getTransactionInfo(price));

        paymentRequestJson.put("merchantInfo", getMerchantInfo());


        final PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentRequestJson.toString());


        AutoResolveHelper.resolveTask(paymentsClient.loadPaymentData(request)
                , activity, LOAD_PAYMENT_DATA_REQUEST_CODE);

    }

  public   interface ReadyToPay {
        void ready();
    }

}
