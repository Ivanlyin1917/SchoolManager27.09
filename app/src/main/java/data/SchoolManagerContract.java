package data;

import android.provider.BaseColumns;

public final class SchoolManagerContract {

    public SchoolManagerContract() {
    }

    public static final class SubjectEntry implements BaseColumns {
        public static  final  String TABLE_NAME="Subjects";
        public static  final String KEY_ID="Subject_id";
        public static  final String KEY_NAME="Subject_name";
        public static  final  String KEY_TYPE_ID="Subject_type_id";
    }


}
