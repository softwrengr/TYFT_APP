package com.squaresdevelopers.tyft.views.signup.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squaresdevelopers.tyft.R;
import com.squaresdevelopers.tyft.dataModels.customerSignUpModels.CustomerSignUpResponseModel;
import com.squaresdevelopers.tyft.networking.ApiClient;
import com.squaresdevelopers.tyft.networking.ApiInterface;
import com.squaresdevelopers.tyft.utilities.AlertUtils;
import com.squaresdevelopers.tyft.utilities.GeneralUtils;
import com.squaresdevelopers.tyft.utilities.GetLocation;
import com.squaresdevelopers.tyft.views.login.ui.LoginFragment;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TyftSignUpFragment extends Fragment {
    AlertDialog alertDialog;
    View view;
    @BindView(R.id.et_customer_email)
    EditText etCustomerEmail;
    @BindView(R.id.et_customer_password)
    EditText etCustomerPassword;
    @BindView(R.id.et_customer_username)
    EditText etCustomerUsername;
    @BindView(R.id.btn_customer_login)
    Button btnCustomerLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    String strEmail, strPassword, strUserType, strUsername;
    boolean valid = false;

    GetLocation getLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up_customer, container, false);

        getLocation = new GetLocation();
        getLocation.getLocation(getActivity());
        initUI();
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);
        strUserType = GeneralUtils.getUserType(getActivity());

        btnCustomerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    alertDialog = AlertUtils.createProgressDialog(getActivity());
                    alertDialog.show();
                    apiCallLogin();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragment(getActivity(), new LoginFragment());
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    private void apiCallLogin() {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<CustomerSignUpResponseModel> userLogin = services.customerSignUp(strUsername, strEmail, strPassword);
        userLogin.enqueue(new Callback<CustomerSignUpResponseModel>() {
            @Override
            public void onResponse(Call<CustomerSignUpResponseModel> call, Response<CustomerSignUpResponseModel> response) {
                alertDialog.dismiss();
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.body().getSuccess()) {
                    GeneralUtils.putBooleanValueInEditor(getContext(), "isLogin", true);
                    GeneralUtils.connectFragment(getContext(), new LoginFragment());

                }
            }

            @Override
            public void onFailure(Call<CustomerSignUpResponseModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate() {
        valid = true;
        strEmail = etCustomerEmail.getText().toString().trim();
        strPassword = etCustomerPassword.getText().toString().trim();
        strUsername = etCustomerUsername.getText().toString().trim();


        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            etCustomerEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etCustomerEmail.setError(null);
        }

        if (strUsername.isEmpty()) {
            etCustomerUsername.setError("Please enter a your username");
            valid = false;
        } else {
            etCustomerUsername.setError(null);
        }


        if (strPassword.isEmpty() || strPassword.length() < 6) {
            etCustomerPassword.setError("Please enter a strong password");
            valid = false;
        } else {
            etCustomerPassword.setError(null);
        }
        return valid;
    }
}
