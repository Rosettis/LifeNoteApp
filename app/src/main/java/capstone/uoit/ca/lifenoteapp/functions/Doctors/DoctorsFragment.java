package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;

/**
 * DoctorsFragment
 *
 * @author Matthew Rosettis
 */
public class DoctorsFragment extends Fragment implements DoctorAddDialogListener { // implements DoctorDataListener for File, implements DoctorAddDialogListener for dialog return info
    private final String fileName = "doctors.txt";
    View view;
//    private String output = "";
    private RecyclerView rv;
    DoctorAdapter adapter;
    private FragmentManager fragmentManager;
    private List<Doctor> result = new ArrayList<Doctor>();
    //    DoctorAdapter.DoctorViewHolder.OnDoctorSelectedListener lsnr;
    ArrayList<Doctor> doctors;


    static String tempName;
    static String tempPhone;
    static String tempAddress;
    static String tempEmail;
    static String tempTitle;

    public static DoctorsFragment newInstance() {
        DoctorsFragment fragment = new DoctorsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Bundle bundle = this.getArguments();
        output = bundle.getString("msg_add_doctor");*/
        /*//Code for Doctors from File
        Log.d("File Name", fileName);
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadDocs(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onResume(){
        super.onResume();
        /*//Code for Doctors from File
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadDocs(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doctors, container, false);
        rv = (RecyclerView)view.findViewById(R.id.rv_doctor_view);
//        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        //using a Linear Layout manager
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());

        /*try {
            InputStream is = getActivity().getAssets().open(fileName);
            downloadDocs(is);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        adapter = new DoctorAdapter(createDoctorList(10));
        rv.setAdapter(adapter);
        //Button to add more doctors
        FloatingActionButton btnFab = (FloatingActionButton) view.findViewById(R.id.btnFloatingAddDoctor);
        btnFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Add A Doctor", Toast.LENGTH_SHORT).show();
                showDoctorAddDialog();
            }
        });

        return view;
    }

    private List<Doctor> createDoctorList(int size) {

        for (int i=1; i <= size; i++) {
            Doctor doctor = new Doctor.Builder("Name_"+i,"Phone_"+i)
                    .address("Address_" + i).email("Email_" + i).build();
            result.add(doctor);
        }
        return result;
    }


    public void addDoctor(Doctor doctor) {
        result.add(doctor);
        adapter.notifyDataSetChanged();
    }

    public void removeDoctor(int position){
        result.remove(position);
        rv.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, result.size());
    }

    private void showDoctorAddDialog(){
        /*fragmentManager = getActivity().getSupportFragmentManager();
        DoctorAddDialog editNameDialog = new DoctorAddDialog();
        editNameDialog.show(fragmentManager, "DoctorAddDialog");*/
        Intent addDoctor = new Intent(getActivity(),DoctorAddActivity.class);
        startActivityForResult(addDoctor, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == 1) {
                tempName = data.getExtras().getString("name");
                tempPhone = data.getExtras().getString("phone");
                tempAddress = data.getExtras().getString("address");
                tempEmail = data.getExtras().getString("email");
                tempTitle = data.getExtras().getString("title");

                doctors.add(new Doctor.Builder(tempName, tempPhone).address(tempAddress)
                        .email(tempEmail).title(tempTitle).build());
            }
        }
    }


    @Override
    public void onFinishAddDialog(String addName, String addPhone, String addAddress,
                                  String addEmail, String addTitle) {
        Toast.makeText(this.getContext(),"Hi, " + addName, Toast.LENGTH_SHORT).show();
        doctors.add(new Doctor.Builder(tempName, tempPhone).address(tempAddress)
                .email(tempEmail).title(tempTitle).build());
        /*doctors.add(new Doctor.Builder(addName, addPhone).address(addAddress)
                .email(addEmail).title(addTitle).build());*/
    }

    public static void store(String name, String phone, String address, String email, String title){
        tempName = name;
        tempPhone = phone;
        tempAddress = address;
        tempEmail = email;
        tempTitle = title;
    }

    /*//TODO: RecyclerView Integration
    //Populating Doctors from a text file
    public void downloadDocs(InputStream fileName){
        DownloadFeedTask task = new DownloadFeedTask(this);
        task.execute(fileName);
    }*/

    /*@Override
    public void showDoctors(ArrayList<Doctor> doctors) {
        DoctorDBHelper dbHelper = DoctorDBHelper.getInstance(this.getContext());
        dbHelper.deleteAllDoctors();
        for(int i = 0; i < doctors.size(); i++){
            dbHelper.createDoctor(doctors.get(i).getName(),doctors.get(i).getPhone(),doctors.get(i).getAddress(),doctors.get(i).getEmail(),doctors.get(i).getLocation().toString());
        }
        this.doctors = dbHelper.getAllDoctors();
    }*/
}
