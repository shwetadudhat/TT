package com.technlogi.tt.driver.ui.vehiclelist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.technlogi.tt.IntentActivity;
import com.technlogi.tt.R;
import com.technlogi.tt.common.utils.ClickEvent;
import com.technlogi.tt.driver.DriverActivity;
import com.technlogi.tt.driver.adapter.DriverAllotmentAdapter;
import com.technlogi.tt.driver.adapter.DriverAllotmentSpinnerAdapter;
import com.technlogi.tt.driver.adapter.DriverVehicleListAdapter;
import com.technlogi.tt.driver.adapter.FacilityAdapter;
import com.technlogi.tt.driver.adapter.PendingListAdapter;
import com.technlogi.tt.driver.adapter.VehicleListAdapter;
import com.technlogi.tt.driver.models.VehicleModel;
import com.technlogi.tt.driver.ui.driverlist.DriverList;
import com.technlogi.tt.user.MainActivity;
import com.technlogi.tt.user.adapters.BookingDetailsAdapter;
import com.technlogi.tt.user.models.BookingDetailsModel;
import com.technlogi.tt.user.models.DriversDetailsModel;
import com.technlogi.tt.user.models.LocationModel;

import java.net.StandardProtocolFamily;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static com.technlogi.tt.Constant.CONDITION_DOC;
import static com.technlogi.tt.Constant.HIDE_VIEW;
import static com.technlogi.tt.Constant.INSURANCE_DOC;
import static com.technlogi.tt.Constant.POLLUTION_DOC;


public class VehicleList extends Fragment implements VehicleListAdapter.IUser {

    private RecyclerView rvVehicleList;
    private ImageView vehicleListBack;
    private FloatingActionButton fabVehicleAdd;

    private Dialog addVehicleDialog;

    private Button addToListBtn;
    private ImageButton editBtn,delBtn,insuranceUpload,insuranceView,pollutionUpload,pollutionView,conditionUpload,conditionView;
    private ImageView vehiclePhoto,cutBtn;
    private TextInputEditText drVehicleName,drVehicleNumber,drVehicleRate,drVehicleModel,drVehicleRegistrationNo,drVehicleCapacity,drVehicleAvailableCapacity,drVehicleRegistrationExpire,drVehicleInsuranceExpire,drVehicleInsurance,drVehiclePollution,drVehicleCondition,drVehicleTypeEdit,driverAllotmentEdit;
    private TextView title,vhListForm_addFacilityTv;
    private ImageButton registrationExpiryDatePick,insuranceExpiryDatePick;
    private TextInputLayout driverAllotment,driverVehicleType;
    private MaterialTextView mtv_capacityUnit,mtv_availableCapacityUnit;
    private ImageView iv_capacity_unit,iv_availableCapacity_unit;

    private static final int PICK_IMAGE = 2;

    Uri imageUri;

    private ImageView imageView;

    private ArrayList<DriversDetailsModel> vehicleList;
    private int position;

    private Uri insuranceDoc,pollutionDoc,conditionDoc;

    private Dialog capacityUnitDialog,availableCapacityUnitDialog;

    private ArrayList<String> facilityList;

    private int selectedDate;
    private int selectedMonth;
    private int selectedYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar c = Calendar.getInstance();
        selectedDate =c.get(Calendar.DAY_OF_MONTH);
        selectedMonth = c.get(Calendar.MONTH) ;
        selectedYear = c.get(Calendar.YEAR);

//        facilityList = new ArrayList<>();
//        facilityList.add("Fuel");
//        facilityList.add("Electric");
//        facilityList.add("Wifi");
//        facilityList.add("AC");
//        facilityList.add("Music");
//        facilityList.add("Toilet");
//        facilityList.add("Pantry");
//        facilityList.add("Camera");
//        facilityList.add("Toll Tax");
//        facilityList.add("First Aid");

        capacityUnitDialog = new Dialog(getContext());
        capacityUnitDialog.setContentView(R.layout.dialog_capacity_unit_popup);
        capacityUnitDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        RadioGroup capacityUnitRadioGroup = capacityUnitDialog.findViewById(R.id.capacityUnit_radioGroup);
        capacityUnitDialog.findViewById(R.id.capacityUnit_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capacityUnitDialog.dismiss();
            }
        });
        capacityUnitDialog.findViewById(R.id.capacityUnit_select_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = ((MaterialRadioButton) capacityUnitDialog.findViewById(capacityUnitRadioGroup.getCheckedRadioButtonId())).getText().toString();
                    mtv_capacityUnit.setText(s);
                } catch (Exception e) {

                }
                capacityUnitDialog.dismiss();
            }
        });

        availableCapacityUnitDialog = new Dialog(getContext());
        availableCapacityUnitDialog.setContentView(R.layout.dialog_capacity_unit_popup);
        availableCapacityUnitDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        RadioGroup availableCapacityUnitRadioGroup = availableCapacityUnitDialog.findViewById(R.id.capacityUnit_radioGroup);
        availableCapacityUnitDialog.findViewById(R.id.capacityUnit_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableCapacityUnitDialog.dismiss();
            }
        });
        availableCapacityUnitDialog.findViewById(R.id.capacityUnit_select_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = ((MaterialRadioButton) availableCapacityUnitDialog.findViewById(availableCapacityUnitRadioGroup.getCheckedRadioButtonId())).getText().toString();
                    mtv_availableCapacityUnit.setText(s);
                } catch (Exception e) {

                }
                availableCapacityUnitDialog.dismiss();
            }
        });

        rvVehicleList =  view.findViewById(R.id.rvVehicleList);
        vehicleListBack =  view.findViewById(R.id.vehicleListBack);
        fabVehicleAdd = (FloatingActionButton) view.findViewById(R.id.fabVehicleAdd);

        vehicleListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEvent.getInstance().getClickEvent().onBackFrag();
//                ClickEvent.getInstance().getClickEvent().onBackFrag();
            }
        });

        InitializeAddVehicleDialog(view);

        fabVehicleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addVehicleDialog.show();
//                editBtn.setVisibility(View.GONE);

                AddVehicleDialog(1);

            }
        });

        DriversDetailsModel detailsModel = new DriversDetailsModel();
        detailsModel.setStrVehicleNo("DL01H0123");
        detailsModel.setStrVehicleName("Tesla Cyber Truck");
        detailsModel.setStrDriverName("DriverName");
        ArrayList<DriversDetailsModel> list = new  ArrayList();
        list.add(detailsModel);
        VehicleListAdapter adapter = new VehicleListAdapter(getContext(),list,this);

        view.findViewById(R.id.driverLayout_noList_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvVehicleList.setLayoutManager(new LinearLayoutManager(getContext()));
                rvVehicleList.setHasFixedSize(false);
                rvVehicleList.setAdapter(adapter);

                view.findViewById(R.id.vehicleList_addVehicleMsg).setVisibility(View.GONE);
                fabVehicleAdd.setVisibility(View.VISIBLE);
            }
        });

        view.findViewById(R.id.driverLayout_noList_addVehilceTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicleDialog(1);
            }
        });

        if (view.findViewById(R.id.vehicleList_addVehicleMsg).getVisibility() == View.VISIBLE) {
            AddVehicleDialog(1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClickEvent.getInstance().getClickEvent().onChangeFrag(HIDE_VIEW);
        View v = inflater.inflate(R.layout.fragment_driver_vehicle_list, container, false);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_BACK){
                        ClickEvent.getInstance().getClickEvent().onBackFrag();
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }

//    public void imageSelection() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(intent,"Pick an Image"),PICK_IMAGE);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            imageUri = data.getData();
//            cropImage();
////            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
////                user_profile_image.setImageBitmap(bitmap);
////            }catch (IOException e) {
////
////            }
//
//        } else if (requestCode == 1) {
//            if (data != null) {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");
////                imageView.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//    private void cropImage() {
//        try {
//            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//            cropIntent.setDataAndType(imageUri,"image/*");
//
//            cropIntent.putExtra("crop","true");
//            cropIntent.putExtra("outputX",180);
//            cropIntent.putExtra("outputY",180);
//            cropIntent.putExtra("aspectX",3);
//            cropIntent.putExtra("aspectY",3);
//            cropIntent.putExtra("scaleUpIfNeeded",true);
//            cropIntent.putExtra("return-data",true);
//
//            startActivityForResult(cropIntent,1);
//        }catch (ActivityNotFoundException ax) {
//
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1){
            if(data.hasExtra("data")){
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");
                addVehicleDialog.show();
                byte[] imageBytes = Base64.decode(data.getStringExtra("data"),Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                vehiclePhoto.setImageBitmap(decodedImage);
            }

        } else if (requestCode == INSURANCE_DOC) {
            insuranceDoc = data.getData();
            insuranceUpload.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            insuranceUpload.setBackgroundResource(R.drawable.border_rectangle_color_primary);
            insuranceView.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            insuranceView.setBackgroundResource(R.drawable.border_rectangle_color_primary);
        } else if (requestCode == POLLUTION_DOC) {
            pollutionDoc = data.getData();
            pollutionUpload.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            pollutionUpload.setBackgroundResource(R.drawable.border_rectangle_color_primary);
            pollutionView.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            pollutionView.setBackgroundResource(R.drawable.border_rectangle_color_primary);
        } else if (requestCode == CONDITION_DOC) {
            conditionDoc = data.getData();
            conditionUpload.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            conditionUpload.setBackgroundResource(R.drawable.border_rectangle_color_primary);
            conditionView.setImageTintList(getResources().getColorStateList(R.color.colorPrimary));
            conditionView.setBackgroundResource(R.drawable.border_rectangle_color_primary);
        }
    }

    public void InitializeAddVehicleDialog(View view) {
        addVehicleDialog = new Dialog(getContext());

        //        Form Dialog
        addVehicleDialog.setContentView(R.layout.dialog_vehicle_list_form);
        addVehicleDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);

        title = (TextView) addVehicleDialog.findViewById(R.id.dialog_vehicleListFormTitle);

        driverAllotmentEdit = (TextInputEditText) addVehicleDialog.findViewById(R.id.driverAllotmentEdit);
        driverAllotment = (TextInputLayout) addVehicleDialog.findViewById(R.id.driverAllotment);
        addToListBtn = (Button) addVehicleDialog.findViewById(R.id.addToListBtn);
        editBtn = (ImageButton) addVehicleDialog.findViewById(R.id.editBtn);
        delBtn = (ImageButton) addVehicleDialog.findViewById(R.id.delBtn);
        cutBtn = (ImageView) addVehicleDialog.findViewById(R.id.cutBtn);
        vehiclePhoto = (ImageView) addVehicleDialog.findViewById(R.id.vehiclePhoto);
        drVehicleTypeEdit = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleTypeEdit);
        driverVehicleType = (TextInputLayout) addVehicleDialog.findViewById(R.id.drVehicleType);
        drVehicleName = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleName);
        drVehicleNumber = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleNumber);
        drVehicleNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        drVehicleRate = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleRate);
        drVehicleModel = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleModel);
        drVehicleRegistrationNo = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleRegistrationNo);
        drVehicleRegistrationNo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        drVehicleCapacity = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleCapacity);
        drVehicleAvailableCapacity = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleAvailableCapacity);
        drVehicleRegistrationExpire = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleRegistrationExpire);
        drVehicleInsuranceExpire = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleInsuranceExpire);
        drVehicleInsurance = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleInsurance);
        drVehicleInsurance.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        drVehiclePollution = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehiclePollution);
        drVehiclePollution.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        drVehicleCondition = (TextInputEditText) addVehicleDialog.findViewById(R.id.drVehicleCondition);
        drVehicleCondition.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        insuranceUpload = (ImageButton) addVehicleDialog.findViewById(R.id.vhList_uploadinsuranceDocbtn);
        insuranceView = (ImageButton) addVehicleDialog.findViewById(R.id.vhList_viewInsuranceDocbtn);
        pollutionUpload = (ImageButton) addVehicleDialog.findViewById(R.id.vhList_uploadPollutionDocbtn);
        pollutionView = (ImageButton) addVehicleDialog.findViewById(R.id.vhList_viewPollutionDocbtn);
        conditionUpload = (ImageButton) addVehicleDialog.findViewById(R.id.vhList_uploadConditionDocbtn);
        conditionView = (ImageButton) addVehicleDialog.findViewById(R.id.vhList_viewConditionDocbtn);
        mtv_capacityUnit = (MaterialTextView) addVehicleDialog.findViewById(R.id.mtv_capacityUnit);
        mtv_availableCapacityUnit = (MaterialTextView) addVehicleDialog.findViewById(R.id.mtv_availableCapacityUnit);
        iv_capacity_unit = (ImageView) addVehicleDialog.findViewById(R.id.iv_capacity_unit);
        iv_availableCapacity_unit = (ImageView) addVehicleDialog.findViewById(R.id.iv_availableCapacity_unit);
        vhListForm_addFacilityTv = (TextView) addVehicleDialog.findViewById(R.id.vhListForm_addFacilityTv);
        registrationExpiryDatePick = (ImageButton) addVehicleDialog.findViewById(R.id.drVehicleRegExpDatePick);
        insuranceExpiryDatePick = (ImageButton) addVehicleDialog.findViewById(R.id.drVehicleInsExpDatePick);

//        DriversDetailsModel driversDetailsModel = new DriversDetailsModel();
//        driversDetailsModel.setStrDriverName("Own");
//        driversDetailsModel.setStrDriverMobile("9876543210");
//        List<DriversDetailsModel> list = new ArrayList<>();
//        list.add(driversDetailsModel);
//
//        DriversDetailsModel driversDetailsModel1 = new DriversDetailsModel();
//        driversDetailsModel1.setStrDriverName("DriverName");
//        driversDetailsModel1.setStrDriverMobile("9876543210");
//
//        list.add(driversDetailsModel1);
//        list.add(driversDetailsModel1);
//        list.add(driversDetailsModel1);
//        list.add(driversDetailsModel1);

//        DriverAllotmentSpinnerAdapter driverAllotmentSpinnerAdapter = new DriverAllotmentSpinnerAdapter(getContext(),R.layout.driver_allotment_layout,list);
//        driverAllotmentSpinner.setAdapter(driverAllotmentSpinnerAdapter);

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_confirm_layout);
        dialog.setCanceledOnTouchOutside(false);
        ((TextView)dialog.findViewById(R.id.dialog_confirm_msg)).setText("This driver is alloted to Maruti Car (DH02BR1234). Are you sure to allot this driver to new vehicle?");

        insuranceUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),INSURANCE_DOC);
            }
        });

        insuranceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, insuranceDoc);
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    //tel the user to install viewer to perform this action
                }
            }
        });

        pollutionUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),POLLUTION_DOC);
            }
        });

        pollutionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, pollutionDoc);
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    //tel the user to install viewer to perform this action
                }
            }
        });

        conditionUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getFileChooserIntentForImageAndPdf(),CONDITION_DOC);
            }
        });

        conditionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, conditionDoc);
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    //tel the user to install viewer to perform this action
                }
            }
        });

        vhListForm_addFacilityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog facilityDialog = new Dialog(getContext());
                facilityDialog.setContentView(R.layout.dialog_facility_list);
                facilityDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);

                facilityDialog.findViewById(R.id.facility_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        facilityDialog.dismiss();
                    }
                });

                facilityDialog.findViewById(R.id.facility_dialog_select_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        facilityDialog.dismiss();
                    }
                });

//                RecyclerView rvFacility = facilityDialog.findViewById(R.id.rv_dialog_facilityList);
//                FacilityAdapter adapter = new FacilityAdapter(getContext(),facilityList);
//                rvFacility.setLayoutManager(new LinearLayoutManager(getContext()));
//                rvFacility.setHasFixedSize(false);
//                rvFacility.setAdapter(adapter);

                facilityDialog.show();

            }
        });

        dialog.findViewById(R.id.confirmDialog_cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.confirmDialog_okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Driver Allotment Process
            }
        });

//        driverAllotmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                view.findViewById(R.id.driverAllotmentStatus).setVisibility(View.GONE);
//
//                if (position!=0) {
//                    dialog.show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        cutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicleDialog.dismiss();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Update Vehicle");
                addToListBtn.setVisibility(View.VISIBLE);
                addToListBtn.setText("Update");
                vehiclePhoto.setEnabled(true);
                driverAllotmentEdit.setEnabled(true);
                driverAllotment.setEnabled(true);
                drVehicleTypeEdit.setEnabled(true);
                driverVehicleType.setEnabled(true);
                drVehicleName.setEnabled(true);
                drVehicleNumber.setEnabled(true);
                drVehicleRate.setEnabled(true);
                drVehicleModel.setEnabled(true);
                drVehicleRegistrationNo.setEnabled(true);
                drVehicleCapacity.setEnabled(true);
                drVehicleAvailableCapacity.setEnabled(true);
                drVehicleRegistrationExpire.setEnabled(true);
                drVehicleInsuranceExpire.setEnabled(true);
                drVehicleInsurance.setEnabled(true);
                drVehiclePollution.setEnabled(true);
                drVehicleCondition.setEnabled(true);
                insuranceUpload.setEnabled(true);
                insuranceView.setEnabled(true);
                pollutionUpload.setEnabled(true);
                pollutionView.setEnabled(true);
                conditionUpload.setEnabled(true);
                conditionView.setEnabled(true);
                iv_capacity_unit.setEnabled(true);
                iv_availableCapacity_unit.setEnabled(true);
                vhListForm_addFacilityTv.setEnabled(true);
                insuranceExpiryDatePick.setEnabled(true);
                registrationExpiryDatePick.setEnabled(true);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog confirmationDialog = new Dialog(getContext());
                confirmationDialog.setContentView(R.layout.dialog_confirm_layout);
                ((TextView)confirmationDialog.findViewById(R.id.dialog_confirm_msg)).setText("Are you sure you want to delete Maruti car (DH12BH1234)?");
                confirmationDialog.show();
                confirmationDialog.findViewById(R.id.confirmDialog_cancelBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmationDialog.dismiss();
                    }
                });
            }
        });

        registrationExpiryDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(drVehicleRegistrationExpire);
            }
        });

        insuranceExpiryDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(drVehicleInsuranceExpire);
            }
        });

        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicleDialog.dismiss();
            }
        });

        iv_capacity_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capacityUnitDialog.show();
            }
        });

        iv_availableCapacity_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availableCapacityUnitDialog.show();
            }
        });

        Dialog driverAllotmentDialog = new Dialog(getContext());
        driverAllotmentDialog.setContentView(R.layout.dialog_driver_allotment);
        driverAllotmentDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        driverAllotmentDialog.findViewById(R.id.driverAllotment_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverAllotmentDialog.dismiss();
            }
        });

        DriversDetailsModel driversDetailsModel = new DriversDetailsModel();
        driversDetailsModel.setStrDriverName("Own");
        driversDetailsModel.setStrDriverMobile("9876543210");
        ArrayList<DriversDetailsModel> list = new ArrayList<>();
        list.add(driversDetailsModel);

        DriversDetailsModel driversDetailsModel1 = new DriversDetailsModel();
        driversDetailsModel1.setStrDriverName("DriverName");
        driversDetailsModel1.setStrDriverMobile("9876543210");

        list.add(driversDetailsModel1);
        list.add(driversDetailsModel1);
        list.add(driversDetailsModel1);
        list.add(driversDetailsModel1);

        DriverAllotmentAdapter adapter = new DriverAllotmentAdapter(getContext(),list);
        RecyclerView rvDriverAllotmentList = driverAllotmentDialog.findViewById(R.id.driverAllotment_rvDriverList);
        rvDriverAllotmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDriverAllotmentList.setHasFixedSize(false);
        rvDriverAllotmentList.setAdapter(adapter);

        driverAllotmentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverAllotmentDialog.show();
            }
        });

        addVehicleDialog.findViewById(R.id.drVehicleAddDriverIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVehicleDialog.dismiss();
                NavigationMenuItemView driverAdd = getActivity().findViewById(R.id.nav_driver_driver_list);
                driverAdd.callOnClick();
            }
        });

//        ArrayAdapter<CharSequence> typeList = ArrayAdapter.createFromResource(getContext(),R.array.passanger_vehicle_type,R.layout.support_simple_spinner_dropdown_item);
//        typeList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        drVehicleType.setAdapter(typeList);

        Dialog passangerVehicleTypeDialog = new Dialog(getContext());
        passangerVehicleTypeDialog.setContentView(R.layout.dialog_passanger_vehicle_type);
        passangerVehicleTypeDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        passangerVehicleTypeDialog.findViewById(R.id.passangerVehicleType_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passangerVehicleTypeDialog.dismiss();
            }
        });

        Dialog goodsVehicleTypeDialog = new Dialog(getContext());
        goodsVehicleTypeDialog.setContentView(R.layout.dialog_goods_vehicle_type);
        goodsVehicleTypeDialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
        goodsVehicleTypeDialog.findViewById(R.id.goodsVehicleType_dialog_cut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsVehicleTypeDialog.dismiss();
            }
        });

        drVehicleTypeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passangerVehicleTypeDialog.show();
            }
        });

        addVehicleDialog.findViewById(R.id.vhList_psgGoodsSwitch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Switch)addVehicleDialog.findViewById(R.id.vhList_psgGoodsSwitch)).isChecked()) {
//                    ((Switch)addVehicleDialog.findViewById(R.id.vhList_psgGoodsSwitch)).setText("Goods");

//                    ArrayAdapter<CharSequence> typeList = ArrayAdapter.createFromResource(getContext(),R.array.goods_vehicle_type,R.layout.support_simple_spinner_dropdown_item);
//                    typeList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                    drVehicleType.setAdapter(typeList);

                    drVehicleTypeEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goodsVehicleTypeDialog.show();
                        }
                    });
                } else {
//                    ((Switch)addVehicleDialog.findViewById(R.id.vhList_psgGoodsSwitch)).setText("Passanger");

//                    ArrayAdapter<CharSequence> typeList = ArrayAdapter.createFromResource(getContext(),R.array.passanger_vehicle_type,R.layout.support_simple_spinner_dropdown_item);
//                    typeList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                    drVehicleType.setAdapter(typeList);

                    drVehicleTypeEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            passangerVehicleTypeDialog.show();
                        }
                    });
                }
            }
        });

        vehiclePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(intent,"Pick an image"), PICK_IMAGE);

                startActivityForResult(new Intent(getContext(), IntentActivity.class),1);
            }
        });
    }

    @Override
    public void AddVehicleDialog(int index) {
        addVehicleDialog.show();
        addVehicleDialog.findViewById(R.id.vhList_psgGoodsSwitch).requestFocus();

        if (index == 1) {
            title.setText("Add Vehicle");
            editBtn.setVisibility(View.GONE);
            addToListBtn.setText("Add to List");
            addToListBtn.setVisibility(View.VISIBLE);
            addToListBtn.setEnabled(true);
            delBtn.setVisibility(View.GONE);
            vehiclePhoto.setEnabled(true);
            driverAllotmentEdit.setEnabled(true);
            driverAllotment.setEnabled(true);
            drVehicleTypeEdit.setEnabled(true);
            driverVehicleType.setEnabled(true);
            drVehicleName.setEnabled(true);
            drVehicleNumber.setEnabled(true);
            drVehicleRate.setEnabled(true);
            drVehicleModel.setEnabled(true);
            drVehicleRegistrationNo.setEnabled(true);
            drVehicleCapacity.setEnabled(true);
            drVehicleAvailableCapacity.setEnabled(true);
            drVehicleRegistrationExpire.setEnabled(true);
            drVehicleInsuranceExpire.setEnabled(true);
            drVehicleInsurance.setEnabled(true);
            drVehiclePollution.setEnabled(true);
            drVehicleCondition.setEnabled(true);
            insuranceUpload.setEnabled(true);
            insuranceView.setEnabled(true);
            pollutionUpload.setEnabled(true);
            pollutionView.setEnabled(true);
            conditionUpload.setEnabled(true);
            conditionView.setEnabled(true);
            iv_capacity_unit.setEnabled(true);
            iv_availableCapacity_unit.setEnabled(true);
            vhListForm_addFacilityTv.setEnabled(true);
            insuranceExpiryDatePick.setEnabled(true);
            registrationExpiryDatePick.setEnabled(true);
        } else if (index == 2) {

            title.setText("Vehicle Detail");

            addToListBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.VISIBLE);
            delBtn.setVisibility(View.VISIBLE);
            vehiclePhoto.setEnabled(false);
            driverAllotmentEdit.setEnabled(false);
            driverAllotment.setEnabled(false);
            drVehicleTypeEdit.setEnabled(false);
            driverVehicleType.setEnabled(false);
            drVehicleName.setEnabled(false);
            drVehicleNumber.setEnabled(false);
            drVehicleRate.setEnabled(false);
            drVehicleModel.setEnabled(false);
            drVehicleRegistrationNo.setEnabled(false);
            drVehicleCapacity.setEnabled(false);
            drVehicleAvailableCapacity.setEnabled(false);
            drVehicleRegistrationExpire.setEnabled(false);
            drVehicleInsuranceExpire.setEnabled(false);
            drVehicleInsurance.setEnabled(false);
            drVehiclePollution.setEnabled(false);
            drVehicleCondition.setEnabled(false);
            insuranceUpload.setEnabled(false);
            insuranceView.setEnabled(false);
            pollutionUpload.setEnabled(false);
            pollutionView.setEnabled(false);
            conditionUpload.setEnabled(false);
            conditionView.setEnabled(false);
            iv_capacity_unit.setEnabled(false);
            iv_availableCapacity_unit.setEnabled(false);
            vhListForm_addFacilityTv.setEnabled(false);
            insuranceExpiryDatePick.setEnabled(false);
            registrationExpiryDatePick.setEnabled(false);

//                drVehicleName.setText(vehicleList.get(position).getVehicleName());
//                drVehicleModel.setText(vehicleList.get(position).getVehicleModel());
//                drVehicleRegistrationNo.setText(vehicleList.get(position).getVehicleRegNumber());
//                drVehicleType.setText(vehicleList.get(position).getVehicleType());
//                drVehicleCapacity.setText(vehicleList.get(position).getVehicleMaxSeat());
//                drVehicleRegistrationExpire.setText(vehicleList.get(position).getVehicleMaxLoad());
        }
    }

    @Override
    public void setDataForVehicleDialog(ArrayList<DriversDetailsModel> vehicleList, int position) {
        this.vehicleList = vehicleList;
        this.position = position;
    }

    public Intent getFileChooserIntentForImageAndPdf() {
        String[] mimeTypes = {"image/*", "application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*|application/pdf")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        return intent;
    }

    private void DatePicker(TextInputEditText dateView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate =  dayOfMonth;
                selectedMonth = month;
                selectedYear =  year;
                dateView.setText(Integer.toString(dayOfMonth)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year));
            }
        },selectedYear, selectedMonth,selectedDate);
        datePickerDialog.show();
    }
}
