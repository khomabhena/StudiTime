package com.aperturestudios.studytime;

public final class DBContract {

    public DBContract() {
    }

    static abstract class Subjects {
        static final String TABLE_NAME = "table_twelve";
        static final String ID = "_id";

        static final String NAME = "local_uid";
        static final String IS_SEEN = "col_1";
        static final String IS_MALE = "col_2";

        static final String KEY = "col_3";
        static final String AREA = "col_4";
        static final String DESCRIPTION = "col_5";
        static final String CHARGE = "col_6";
        static final String IS_AVAILABLE = "col_7";
        static final String IS_CLUB = "col_8";
        static final String POSITION = "col_9";
        static final String MEDICAL_AID = "col_10";
        static final String EMAIL = "col_11";
        static final String SUFFIX = "col_12";
        static final String NO = "col_13";
        static final String ADDRESS = "col_14";
        static final String EMPLOYER = "col_15";
        static final String PHONE = "col_16";
        static final String SPECIMEN_TYPE = "col_17";
        static final String MEDICAL_LINK = "col_18";
        static final String FORM_LINK = "col_19";
        static final String PATIENT_DOB = "col_20";
    }

    static abstract class Parents {
        static final String TABLE_NAME = "table_thirteen";
        static final String ID = "_id";
        static final String TYPE = "local_uid";
        static final String KEY = "col_1";
        static final String NAME = "col_2";
        static final String LINK = "col_3";
        static final String PRICE = "col_4";
        static final String DESCRIPTION = "col_6";
        static final String IS_AVAILABLE = "col_5";
        static final String TIMESTAMP = "col_7";
        static final String TAKEAWAY_CHARGE = "col_8";
        static final String LIMIT = "col_9";
        static final String SIZE = "col_10";
        static final String IS_HOME_DELIVERY = "col_11";
        static final String CATEGORY_KEY = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Teacher {
        static final String TABLE_NAME = "table_ordered_meals";
        static final String ID = "_id";
        static final String TYPE = "local_uid";
        static final String KEY = "col_1";
        static final String ORDER_KEY = "col_9";
        static final String NAME = "col_2";
        static final String LINK = "col_3";
        static final String PRICE = "col_4";
        static final String DISCOUNT = "col_6";
        static final String IS_AVAILABLE = "col_5";
        static final String TIMESTAMP = "col_7";
        static final String TAKEAWAY_CHARGE = "col_8";
        static final String LIMIT = "col_10";
        static final String SIZE = "col_11";
        static final String DESCRIPTION = "col_12";
        static final String IS_HOME_DELIVERY = "col_13";
        static final String CATEGORY_KEY = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Timetable {
        static final String TABLE_NAME = "table_questions";
        static final String ID = "_id";
        static final String UID = "user_uid";
        static final String SURNAME = "col_3";
        static final String SUBJECT = "local_uid";
        static final String STARTTIME = "col_1";
        static final String ENDTIME = "col_2";
        static final String DAY = "col_4";
        static final String ADDRESS = "col_5";
        static final String ORDER_CHARGE = "col_6";
        static final String DELIVERY_CHARGE = "col_7";
        static final String PROCESSING_CHARGE = "col_8";
        static final String SERVICE_CHARGE = "col_9";
        static final String TIMESTAMP = "col_10";
        static final String DELIVER_BEFORE = "col_11";
        static final String DELIVERED_ON = "col_12";
        static final String IS_PAID = "col_13";
        static final String IS_PREPARING = "col_14";
        static final String IS_CLUB = "col_15";
        static final String IS_DONE = "col_16";
        static final String IS_DELIVERED = "col_17";
        static final String IS_TAKEAWAY = "col_18";
        static final String TAKEAWAY_CHARGE = "col_19";
        static final String DELIVERY_START = "col_20";
        static final String NOTES = "col_21";
        static final String DELIVERY_AREA_KEY = "col_22";
    }

    static abstract class Notes {
        static final String TABLE_NAME = "table_responses_prep";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PHONE_NUMBER = "col_2";
        static final String STUDENT_NAME = "col_3";
        static final String LOCAL_URL = "col_4";
        static final String ONLINE_URL = "col_5";
        static final String SUBJECT = "col_6";
        static final String CHAPTER = "col_7";
        static final String TOPIC = "col_8";
        static final String TIMESTAMP = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Categories {
        static final String TABLE_NAME = "table_actions_prep";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PARENT_KEY = "col_2";
        static final String NAME = "col_3";
        static final String POSITION = "col_4";
        static final String ENABLED = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String IS_AVAILABLE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Keywords {
        static final String TABLE_NAME = "table_chats";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String NAME = "col_3";
        static final String TYPE = "col_2";
        static final String ENABLED = "col_4";
        static final String QUESTION = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String LANGUAGE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Actions1 {
        static final String TABLE_NAME = "table_actions_prep_one";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PARENT_KEY = "col_2";
        static final String NAME = "col_3";
        static final String ACTION = "col_4";
        static final String ENABLED = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String LANGUAGE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Actions2 {
        static final String TABLE_NAME = "table_actions_prep_two";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PARENT_KEY = "col_2";
        static final String NAME = "col_3";
        static final String ACTION = "col_4";
        static final String ENABLED = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String LANGUAGE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Actions3 {
        static final String TABLE_NAME = "table_actions_prep_three";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PARENT_KEY = "col_2";
        static final String NAME = "col_3";
        static final String ACTION = "col_4";
        static final String ENABLED = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String LANGUAGE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Actions4 {
        static final String TABLE_NAME = "table_actions_prep_four";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PARENT_KEY = "col_2";
        static final String NAME = "col_3";
        static final String ACTION = "col_4";
        static final String ENABLED = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String LANGUAGE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

    static abstract class Actions5 {
        static final String TABLE_NAME = "table_actions_prep_five";
        static final String ID = "_id";
        static final String LOCAL_UID = "local_uid";
        static final String KEY = "col_1";
        static final String PARENT_KEY = "col_2";
        static final String NAME = "col_3";
        static final String ACTION = "col_4";
        static final String ENABLED = "col_5";
        static final String TIMESTAMP = "col_6";
        static final String LANGUAGE = "col_7";
        static final String COl_8 = "col_8";
        static final String COl_9 = "col_9";
        static final String COl_10 = "col_10";
        static final String COl_11 = "col_11";
        static final String COl_12 = "col_12";
        static final String COl_13 = "col_13";
        static final String COl_14 = "col_14";
        static final String COl_15 = "col_15";
        static final String COl_16 = "col_16";
        static final String COl_17 = "col_17";
        static final String COl_18 = "col_18";
        static final String COl_19 = "col_19";
        static final String COl_20 = "col_20";
    }

}