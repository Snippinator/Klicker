package alexandru.ciocea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

public class ActivityDB {

	// vars for the users table
	public static final String KEY_USERID = "_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_EMAIL = "email";

	// vars for the activities table
	public static final String KEY_ACTIVITYID = "_id";
	public static final String KEY_USERIDFOREIGN2 = "userIdForeign";
	public static final String KEY_DEFINITION = "definition";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_SUBUNITS = "subunits";

	// vars for the execution table
	public static final String KEY_EXECUTIONID = "_id";
	public static final String KEY_USERIDFOREIGN = "userIdForeign";
	public static final String KEY_ACTIVITYIDFOREIGN = "activityIdForeign";
	public static final String KEY_EXECUTIONTIMESTAMP = "timestamp";

	// vars for database and its tables
	private static final String DATABASE_NAME = "klicker";
	private static final String DATABASE_TABLE1 = "users";
	private static final String DATABASE_TABLE2 = "activities";
	private static final String DATABASE_TABLE3 = "execution";
	private static final int DATABASE_VERSION = 1;

	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	public static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createTable1 = "CREATE TABLE " + DATABASE_TABLE1 + " ("
					+ KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_USERNAME + " TEXT NOT NULL, " + KEY_PASSWORD
					+ " TEXT NOT NULL, " + KEY_EMAIL + " TEXT NOT NULL);";
			db.execSQL(createTable1);
			String createTable2 = "CREATE TABLE " + DATABASE_TABLE2 + " ("
					+ KEY_ACTIVITYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_USERIDFOREIGN2 + " TEXT NOT NULL, " + KEY_DEFINITION
					+ " TEXT NOT NULL, " + KEY_DURATION + " TEXT NOT NULL, "
					+ KEY_SUBUNITS + " TEXT NOT NULL);";
			db.execSQL(createTable2);
			String createTable3 = "CREATE TABLE " + DATABASE_TABLE3 + " ("
					+ KEY_EXECUTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_USERIDFOREIGN + " INTEGER NOT NULL, "
					+ KEY_ACTIVITYIDFOREIGN + " INTEGER NOT NULL, "
					+ KEY_EXECUTIONTIMESTAMP + " TIMESTAMP);";
			db.execSQL(createTable3);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// delete tables from database
			String deleteTable1 = "DROP TABLE IF EXISTS " + DATABASE_TABLE1;
			db.execSQL(deleteTable1);
			String deleteTable2 = "DROP TABLE IF EXISTS " + DATABASE_TABLE2;
			db.execSQL(deleteTable2);
			String deleteTable3 = "DROP TABLE IF EXISTS " + DATABASE_TABLE3;
			db.execSQL(deleteTable3);
			onCreate(db);
		}

	}

	public ActivityDB(Context c) {
		ourContext = c;
	}

	public ActivityDB open() {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public ActivityDB update() {
		ourHelper.onUpgrade(ourDatabase, 0, 1);
		return this;
	}

	private void dropTables(SQLiteDatabase db) {

		String deleteTable1 = "DROP TABLE IF EXISTS " + DATABASE_TABLE1;
		db.execSQL(deleteTable1);
		String deleteTable2 = "DROP TABLE IF EXISTS " + DATABASE_TABLE2;
		db.execSQL(deleteTable2);
		String deleteTable3 = "DROP TABLE IF EXISTS " + DATABASE_TABLE3;
		db.execSQL(deleteTable3);
	}

	public void close() {
		ourHelper.close();
	}

	public void saveUser(String username, byte[] hashedPassword, String email) {
		// insert new user into database
		ContentValues cValues = new ContentValues();
		cValues.put(KEY_USERNAME, username);
		cValues.put(KEY_PASSWORD, hashedPassword);
		cValues.put(KEY_EMAIL, email);
		ourDatabase.insert(DATABASE_TABLE1, null, cValues);
	}

	public void saveActivity(String string, String definition, String duration,
			String subunits) {

		ContentValues cValues = new ContentValues();
		cValues.put(KEY_USERIDFOREIGN2, string);
		cValues.put(KEY_DEFINITION, definition);
		cValues.put(KEY_DURATION, duration);
		cValues.put(KEY_SUBUNITS, subunits);
		ourDatabase.insert(DATABASE_TABLE2, null, cValues);

	}

	public void getActivities(String string, ViewGroup linerLayout,
			Context context) {

		String[] columns = new String[] { KEY_DEFINITION };
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, string, null,
				null, null, null);

		int iDef = c.getColumnIndex(KEY_DEFINITION);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Button bt = new Button(context);
			bt.setText(c.getString(iDef));
			bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			bt.layout(l, t, r, b)
			linerLayout.addView(bt);
			// result = result + c.getString(iRow) + " " + c.getString(iName) +
			// " " + c.getString(iHotness) + "\n";

		}

	}

}
