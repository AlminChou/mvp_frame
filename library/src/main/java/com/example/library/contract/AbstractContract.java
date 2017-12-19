package com.example.library.contract;

/**
 * Created by almin on 2017/12/7.
 */

public interface AbstractContract {

    interface ViewRenderer{
        void showSpinner(); // progress loading view
        void hideSpinner();
        void goBack();
        void showToast(String msg);
        void showSnackBar(String msg);
    }

    interface Presenter{
        void start();
        void detach();

        // for simple ui not need presenter
        Presenter EMPTY = new Presenter() {
            @Override
            public void start() {
            }

            @Override
            public void detach() {
            }
        };
    }


    interface DataSource{
        //   saveToRP /getFromRP (Repository: DateBase, Manager)
        //   saveToSP /getFromSP (SharedPreferences)

    }

}
