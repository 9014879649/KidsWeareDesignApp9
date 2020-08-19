package com.trendyboys.kidsdresses.utill;



import androidx.fragment.app.Fragment;



public class NewGalleryFragment extends Fragment {

   /* private static final String TAG = "NewGalleryFragment";


    int currentOffset = 0;
    int mMaxDisplay_Size = 3;
    int mTotal_Size = 0;

    ArrayList<ItemImage> Pathitems = new ArrayList<>();
    private List<ItemList> mItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ParentAdapter mAdapter;


    Context context;
    Activity activity;
    MyClassBoardDB db = null;
    private Typeface fontMuseo;
    private TransparentProgressDialog mProgressbar;
    String mUserId, mStudentEnrollmentID, mStudentName;
    SharedPreferences mSharedPref;
    Editor mEditor;
    TextView mShowNodataText;
    int mRecordsCount = 20, mCurrentPageNo = 1;
    ConnectivityManager conMgr;
    public static ArrayList<GalleryModelClass> mGalleryList = new ArrayList<GalleryModelClass>();
    public static ArrayList<GalleryModelClass> mGalleryListDup = new ArrayList<GalleryModelClass>();
    private boolean isRefresh = false;
    private boolean isFirstHit = false;
    RelativeLayout tapBt;
    int listItemPosition = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_gallery, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpIds();
    }

    private void setUpIds(){
        setHasOptionsMenu(true);
        activity = getActivity();
        context = activity.getApplicationContext();
        this.fontMuseo = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Regular.ttf");

        mSharedPref = context.getSharedPreferences("", 0);
        mEditor = mSharedPref.edit();
        mUserId = mSharedPref.getString("UseridKey", mUserId);
        mStudentEnrollmentID = mSharedPref.getString("studentEnrollmentIdKey",
                mStudentEnrollmentID);

        db = new MyClassBoardDB(context);

        mProgressbar = new TransparentProgressDialog(activity,
                R.drawable.spinner_loading_imag);
        mShowNodataText = (TextView) getView().findViewById(
                R.id.alert_message_text);
        mShowNodataText.setText("No Data Available");
        mShowNodataText.setVisibility(View.GONE);
        mShowNodataText.setTypeface(fontMuseo);
        recyclerView = (RecyclerView)getView(). findViewById(R.id.new_gallery_recycler_view);
        isFirstHit =true;

        mGalleryList.clear();
        mGalleryListDup.clear();
        loadGalleryData();


        tapBt = (RelativeLayout)getView().findViewById(R.id.tapBt);

        tapBt.setVisibility(View.GONE);



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {

                Log.e(TAG,"+++++++++++Item Click Position : "+position+"  , Array size : "+mGalleryList.size());
                GalleryModelClass beenclass = mGalleryList.get(position );

                String id = beenclass.getIds();
                String date = beenclass.getDate();
                String heading = beenclass.getTitle();
                String description = beenclass.getDescription();
                String filepath = beenclass.getFilePath();
                Log.e(id, "Image");

                Intent i = new Intent(activity, DetailGalleryActivity.class);
                i.putExtra("id", id);
                i.putExtra("heading", heading);
                i.putExtra("date", date);
                i.putExtra("description", description);
                i.putExtra("filepath", filepath);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));



    }



    private void loadGalleryData() {
        mShowNodataText.setVisibility(View.GONE);
        conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            new GetGalleryResult().execute();
        } else {
            Toast.makeText(context, "Check your Network Connection",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public class GetGalleryResult extends AsyncTask<String, String, String> {
        SoapObject soapObject;

        @Override
        protected void onPreExecute() {

            if (isFirstHit) {
                if (mProgressbar != null && !mProgressbar.isShowing()) {
                    mProgressbar.show();
                }
            }else {
                tapBt.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {

                soapObject = SoapObjectProvider.GetEvents(context,
                        Integer.parseInt(mUserId), mCurrentPageNo,
                        mRecordsCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (soapObject != null) {
                try {
                    if (soapObject.getProperty("get_EventsForStudentNewResult") != null) {
                        String GetEventsResult = soapObject.getProperty(
                                "get_EventsForStudentNewResult").toString();
                        Log.e(TAG, "mUserId:: " + mUserId+", mCurrentPageNo:: " + mCurrentPageNo+", mRecordsCount:: " + mRecordsCount);
                        Log.e(TAG, "get_EventsForStudentNewResult:: "
                                + GetEventsResult);
                        JSONArray jsonArray;
                        try {
                            jsonArray = new JSONArray(GetEventsResult);

                            if (jsonArray!=null && jsonArray.length()==0){
                                tapBt.setVisibility(View.GONE);

                                if (isFirstHit){
                                    mShowNodataText.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }*//*else {
                                    Toast.makeText(getActivity(),"No new records found!",Toast.LENGTH_SHORT).show();
                                }*//*
                            }else {
                                mGalleryList.clear();
                                if (mGalleryListDup != null && mGalleryListDup.size()>=1) {
                                    for (int j = 0; j < mGalleryListDup.size(); j++) {
                                        GalleryModelClass beenclass = new GalleryModelClass();
                                        beenclass.setIDs(mGalleryListDup.get(j).getIds());
                                        beenclass.setTitle(mGalleryListDup.get(j).getTitle());
                                        beenclass.setDescription(mGalleryListDup.get(j).getDescription());
                                        beenclass.setDate(mGalleryListDup.get(j).getDate());
                                        beenclass.setFilePath(mGalleryListDup.get(j).getFilePath());

                                        mGalleryList.add(beenclass);
                                    }
                                    if (mGalleryList.size()>=4){
                                        listItemPosition = mGalleryList.size()-4;
                                    }
                                }
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        String description = "", id = "", title = "", date = "", fileStatus = "";
                                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                                        if (jsonObj.has("Description")) {
                                            description = jsonObj.getString("Description");
                                        }
                                        if (jsonObj.has("AchievementsID")) {
                                            id = jsonObj.getString("AchievementsID");
                                        }
                                        if (jsonObj.has("CoCurricularActivityName")) {
                                            title = jsonObj.getString("CoCurricularActivityName");
                                        }
                                        if (jsonObj.has("Date")) {
                                            date = jsonObj.getString("Date");
                                        }
                                        if (jsonObj.has("FileStatus")) {
                                            if (jsonObj.getString("FileStatus") != null) {
                                                fileStatus = jsonObj.getString("FileStatus");
                                                fileStatus = fileStatus.replace("\\", "/");
                                                fileStatus = fileStatus.replace(" ", "%20");
                                            } else {
                                                fileStatus = "";
                                            }
                                        }

                                        if (isFirstHit) {
                                            db.addGalleryData(id, title,
                                                    description, date, "", fileStatus, "", mUserId, mStudentEnrollmentID);
                                        }
                                        GalleryModelClass beenclass = new GalleryModelClass();
                                        beenclass.setIDs(id);
                                        beenclass.setTitle(title);
                                        beenclass.setDescription(description);
                                        beenclass.setDate(date);
                                        beenclass.setFilePath(fileStatus);
                                        mGalleryList.add(beenclass);
                                    }
                                    mShowNodataText.setVisibility(View.GONE);


                                } else {
                                    mShowNodataText.setVisibility(View.VISIBLE);

                                }


                                if (mGalleryList != null && mGalleryList.size() > 0) {
                                    mGalleryListDup.clear();
                                    mGalleryListDup.addAll(mGalleryList);

                                    Collections.sort(mGalleryList,
                                            new Comparator<GalleryModelClass>() {
                                                SimpleDateFormat f = new SimpleDateFormat(
                                                        "dd MMM yyyy", Locale.US);

                                                @Override
                                                public int compare(GalleryModelClass o1,
                                                                   GalleryModelClass o2) {
                                                    try {
                                                        return f.parse(o2.getDate())
                                                                .compareTo(
                                                                        f.parse(o1
                                                                                .getDate()));
                                                    } catch (ParseException e) {
                                                        throw new IllegalArgumentException(e);
                                                    }
                                                }

                                            });

                                    Log.e(TAG,"+++++++++++++++++"+"Hit number : "+mCurrentPageNo+" ,Total Display Records : "+mGalleryList.size());

                                    mCurrentPageNo += 1;
                                    parseData(mGalleryList);

                                    mTotal_Size = 10;
                                    LinearLayoutManager mLayoutManager =  new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    mAdapter = new ParentAdapter(getActivity(), mItemList, mMaxDisplay_Size, mTotal_Size,recyclerView);

                                    if (listItemPosition<mGalleryList.size()){
                                        Log.e(TAG,"+++++++++++Current displaying position"+listItemPosition);
                                        mLayoutManager.scrollToPosition(listItemPosition);
                                    }

                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(mAdapter);


                                    recyclerView.setVisibility(View.VISIBLE);

                                    mShowNodataText.setVisibility(View.GONE);
//                                mListView.setVisibility(View.VISIBLE);
//                                tapBt.setVisibility(View.VISIBLE);
                                } else {
                                    mShowNodataText.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
//                                mPullRefreshListView.setVisibility(View.GONE);
//                                tapBt.setVisibility(View.GONE);
                                }
                                isRefresh = false;
                                isFirstHit = false;}
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context,"Something went wrong, please try again!!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        showAlertDialog("A network error occurred while communicating with the server. Please check your internet connection!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Something went wrong, please try again!!", Toast.LENGTH_LONG).show();

                }
            } else {
                showAlertDialog("A network error occurred while communicating with the server. Please check your internet connection!");
            }


            if (mProgressbar != null && mProgressbar.isShowing()) {
                mProgressbar.dismiss();
            }

            tapBt.setVisibility(View.GONE);

            if (mAdapter != null) {

                mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {


                        loadGalleryData();


                    }
                });
            }

        }



    }

    private void parseData(ArrayList<GalleryModelClass> mGalleryList) {
        Log.e(TAG,"mGalleryList=============="+mGalleryList.size());
        mItemList.clear();
        try {
            for (int k=0;k<mGalleryList.size();k++){

                String filePath  = mGalleryList.get(k).getFilePath();
                String[] path = filePath.split(",");
                ArrayList<String> imagesList = new ArrayList<String>();
                imagesList.clear();
                if (path.length>0) {

                    for (int i = 0; i < path.length; i++) {
//                    Log.e("path[i]", path[i]);
                        path[i] = path[i].replace("\\", "/");
                        path[i] = path[i].replace(" ", "%20");
                        imagesList.add(path[i]);
                    }
                }else {
                    imagesList.add(mGalleryList.get(k).getFilePath());
                }
//                Log.e(TAG,"K position : "+k+" imagesList size: "+imagesList.size());
                prepareGalleryData(imagesList,k,mGalleryList.get(k).getTitle(),mGalleryList.get(k).getDate());

            }

        }catch (Exception e){
            e.printStackTrace();
        }




    }



    private void prepareGalleryData(ArrayList<String> imagesList, int k, String title, String date) {

        int size = imagesList.size();

        ArrayList<ItemImage> mPathitems = new ArrayList<>();
        boolean isCol2Avail = false;
        Pathitems.clear();
        if (size>0) {
            ItemImage i1 = new ItemImage(1, imagesList.get(0), imagesList.get(0));
            int colSpan1 = Math.random() < 0.2f ? 2 : 1;
            int rowSpan1 = colSpan1;
            if (colSpan1 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan1 == 2 && isCol2Avail)
                colSpan1 = 1;

            i1.setColumnSpan(colSpan1);
            i1.setRowSpan(rowSpan1);
            i1.setPosition(currentOffset + 0);
            Pathitems.add(i1);
        }


        if (size>1) {
            ItemImage i2 = new ItemImage(2, imagesList.get(1), imagesList.get(1));
            int colSpan2 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan2 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan2 == 2 && isCol2Avail)
                colSpan2 = 1;

            int rowSpan2 = colSpan2;
            i2.setColumnSpan(colSpan2);
            i2.setRowSpan(rowSpan2);
            i2.setPosition(currentOffset + 1);
            Pathitems.add(i2);
        }

        if (size>2) {
            ItemImage i3 = new ItemImage(3, imagesList.get(2), imagesList.get(2));
            int colSpan3 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan3 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan3 == 2 && isCol2Avail)
                colSpan3 = 1;

            int rowSpan3 = colSpan3;
            i3.setColumnSpan(colSpan3);
            i3.setRowSpan(rowSpan3);
            i3.setPosition(currentOffset + 2);
            Pathitems.add(i3);
        }

        if (size>3) {
            ItemImage i4 = new ItemImage(4, imagesList.get(3), imagesList.get(3));
            int colSpan4 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan4 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan4 == 2 && isCol2Avail)
                colSpan4 = 1;

            int rowSpan4 = colSpan4;
            i4.setColumnSpan(colSpan4);
            i4.setRowSpan(rowSpan4);
            i4.setPosition(currentOffset + 3);
            Pathitems.add(i4);
        }

        if (size>4) {
            ItemImage i5 = new ItemImage(5, imagesList.get(4), imagesList.get(4));
            int colSpan5 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan5 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan5 == 2 && isCol2Avail)
                colSpan5 = 1;

            int rowSpan5 = colSpan5;
            i5.setColumnSpan(colSpan5);
            i5.setRowSpan(rowSpan5);
            i5.setPosition(currentOffset + 4);
            Pathitems.add(i5);

        }

        if (size>5) {
            ItemImage i6 = new ItemImage(6, imagesList.get(5), imagesList.get(5));
            int colSpan6 = Math.random() < 0.2f ? 2 : 1;

            if (colSpan6 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan6 == 2 && isCol2Avail)
                colSpan6 = 1;

            int rowSpan6 = colSpan6;
            i6.setColumnSpan(colSpan6);
            i6.setRowSpan(rowSpan6);
            i6.setPosition(currentOffset + 5);
            Pathitems.add(i6);
        }
        if (size>6) {
            ItemImage i7 = new ItemImage(7, imagesList.get(6), imagesList.get(6));
            int colSpan7 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan7 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan7 == 2 && isCol2Avail)
                colSpan7 = 1;

            int rowSpan7 = colSpan7;
            i7.setColumnSpan(colSpan7);
            i7.setRowSpan(rowSpan7);
            i7.setPosition(currentOffset + 6);
            Pathitems.add(i7);
        }
        if (size>7) {
            ItemImage i8 = new ItemImage(8, imagesList.get(7), imagesList.get(7));
            int colSpan8 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan8 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan8 == 2 && isCol2Avail)
                colSpan8 = 1;

            int rowSpan8 = colSpan8;
            i8.setColumnSpan(colSpan8);
            i8.setRowSpan(rowSpan8);
            i8.setPosition(currentOffset + 7);
            Pathitems.add(i8);
        }
        if (size>8) {
            ItemImage i9 = new ItemImage(9, imagesList.get(8), imagesList.get(8));
            int colSpan9 = Math.random() < 0.2f ? 2 : 1;
            if (colSpan9 == 2 && !isCol2Avail)
                isCol2Avail = true;
            else if (colSpan9 == 2 && isCol2Avail)
                colSpan9 = 1;

            int rowSpan9 = colSpan9;
            i9.setColumnSpan(colSpan9);
            i9.setRowSpan(rowSpan9);
            i9.setPosition(currentOffset + 8);
            Pathitems.add(i9);
        }





        if (mMaxDisplay_Size<Pathitems.size()){
            for(int i = 0; i < mMaxDisplay_Size;i++)
            {
                mPathitems.add(Pathitems.get(i));
            }
        }else {
            for(int i = 0; i < Pathitems.size();i++)
            {
                mPathitems.add(Pathitems.get(i));
            }
        }




//        ItemList itemList = new ItemList(k,"User "+(k+1),mPathitems,size);
        ItemList itemList = new ItemList(k,title+"-"+date,mPathitems,size);
        itemList.setImgCount(size);
        mItemList.add(itemList);
        currentOffset += mPathitems.size();

    }

    private void showAlertDialog(String text) {

        new AlertDialog.Builder((new ContextThemeWrapper(activity, R.style.MyDialogTheme)))
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                activity.finish();
                            }
                        }).show();
    }
*/
}