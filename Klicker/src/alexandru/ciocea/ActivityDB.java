package alexandru.ciocea;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
	MyFramework myFramework = new MyFramework();
	FontClass fontClass = new FontClass();
	UserInformation userInfo = UserInformation.getInstance();

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
					+ KEY_USERIDFOREIGN2 + " INTEGER, " + KEY_DEFINITION
					+ " TEXT NOT NULL, " + KEY_DURATION + " INTEGER, "
					+ KEY_SUBUNITS + " INTEGER);";
			db.execSQL(createTable2);
			String createTable3 = "CREATE TABLE " + DATABASE_TABLE3 + " ("
					+ KEY_EXECUTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_USERIDFOREIGN + " INTEGER NOT NULL, "
					+ KEY_ACTIVITYIDFOREIGN + " INTEGER NOT NULL, "
					+ KEY_EXECUTIONTIMESTAMP + " TIMESTAMP);";
			db.execSQL(createTable3);
			String createExecutionTrigger = "CREATE TRIGGER insert_timeStamp AFTER INSERT ON execution "
					+ "BEGIN UPDATE execution SET timestamp = DATETIME('NOW') WHERE _id = new._id; END";
			db.execSQL(createExecutionTrigger);

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

	// drop all tables inside the database
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

	/************************************
	 * \ | INSERT FROM DATABASE | \
	 ************************************/

	// create a new user and save parameters
	public void saveUser(String username, byte[] hashedPassword, String email) {
		// insert new user into database
		ContentValues cValues = new ContentValues();
		cValues.put(KEY_USERNAME, username);
		cValues.put(KEY_PASSWORD, hashedPassword);
		cValues.put(KEY_EMAIL, email);
		ourDatabase.insert(DATABASE_TABLE1, null, cValues);
	}

	// save activity with parameters the user set inside ActivityOverview form
	public void saveActivity(int userId, String definition, int duration,
			int subunits) {

		ContentValues cValues = new ContentValues();
		cValues.put(KEY_USERIDFOREIGN2, userId);
		cValues.put(KEY_DEFINITION, definition);
		cValues.put(KEY_DURATION, duration);
		cValues.put(KEY_SUBUNITS, subunits);
		ourDatabase.insert(DATABASE_TABLE2, null, cValues);

	}

	// set execution when activity button has been pressed
	protected void saveExecution(int userId, String activityID) {

		ContentValues cValues = new ContentValues();
		cValues.put(KEY_USERIDFOREIGN, userId);
		cValues.put(KEY_ACTIVITYIDFOREIGN, activityID);
		ourDatabase.insert(DATABASE_TABLE3, null, cValues);

		// create entry in execution table with userid and activityid

	}

	/************************************
	 * \ | SELECT FROM DATABASE | \
	 ************************************/

	// get all activities from database and create for each entry a button
	public void getActivities(int string, ViewGroup linearLayout,
			Context context) {

		// remove child elements from layout, otherwise there are duplicate
		// buttons
		linearLayout.removeAllViews();
		int rowCount = 0;
		int lastButtonId = 0;
		String orderBy = "definition asc";
		String[] columns = new String[] { KEY_DEFINITION, KEY_ACTIVITYID };
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, "userIdForeign="
				+ string, null, null, null, orderBy);

		int iDef = c.getColumnIndex(KEY_DEFINITION);
		int iActID = c.getColumnIndex(KEY_ACTIVITYID);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			Button bt = new Button(context);
			bt.setText(c.getString(iDef));
			fontClass.setFont(bt);

			bt.setOnClickListener(new MyOnClickListener(context, c
					.getString(iActID)));

			// add button to layout
			linearLayout.addView(bt);
		}
	}

	// get all activities from database and create for each entry a button
	public ArrayList<Activities> getActivities(int userId) {

		ArrayList<Activities> activityArray = new ArrayList<Activities>();
		String orderBy = "definition asc";
		String[] columns = new String[] { KEY_ACTIVITYID, KEY_USERIDFOREIGN2,
				KEY_DEFINITION, KEY_DURATION, KEY_SUBUNITS };
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, "userIdForeign="
				+ userId, null, null, null, orderBy);

		int iActId = c.getColumnIndex(KEY_ACTIVITYID);
		int iUserId = c.getColumnIndex(KEY_USERIDFOREIGN2);
		int iDef = c.getColumnIndex(KEY_DEFINITION);
		int iDur = c.getColumnIndex(KEY_DURATION);
		int iSub = c.getColumnIndex(KEY_SUBUNITS);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			Activities a = new Activities(c.getInt(iActId), c.getInt(iUserId),
					c.getString(iDef), c.getInt(iDur), c.getInt(iSub));
			activityArray.add(a);
		}

		return activityArray;
	}

	public ArrayList<Execution> getSpecificExecutions(int userId, int activityId) {

		ArrayList<Execution> executionArray = new ArrayList<Execution>();
		String querySelection = "userIdForeign=\"" + userId
				+ "\" AND activityIdForeign=\"" + activityId + "\"";
		String orderBy = "timestamp desc";
		String[] columns = new String[] { KEY_EXECUTIONID, KEY_USERIDFOREIGN,
				KEY_ACTIVITYIDFOREIGN, KEY_EXECUTIONTIMESTAMP };
		Cursor c = ourDatabase.query(DATABASE_TABLE3, columns, querySelection,
				null, null, null, orderBy);

		int iExecId = c.getColumnIndex(KEY_EXECUTIONID);
		int iUserId = c.getColumnIndex(KEY_USERIDFOREIGN);
		int iActId = c.getColumnIndex(KEY_ACTIVITYIDFOREIGN);
		int iTimestamp = c.getColumnIndex(KEY_EXECUTIONTIMESTAMP);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			Execution e = new Execution();
			e.setActivityId(c.getInt(iActId));
			e.setId(c.getInt(iExecId));
			e.setUserId(c.getInt(iUserId));
			if (c.getString(iTimestamp) == null) {
				e.setTimestamp("Kann nicht abgerufen werden");
			}else{
				e.setTimestamp(c.getString(iTimestamp));
			}
			Log.w("DEF IN DB", c.getString(iActId));
			executionArray.add(e);
		}

		return executionArray;
	}

/*	
	// look for inserted definition name, if already exists return false
		protected boolean isActivityStillWorking(int activityId, int userId) {

			boolean definitionExist = false;
			String querySelection = "definition=\"" + definition
					+ "\" AND userIdForeign=\"" + userId + "\"";
			// check database_table2 for activity definition
			String[] columns = new String[] { KEY_DEFINITION };
			Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, querySelection,
					null, null, null, null);

			int iDef = c.getColumnIndex(KEY_DEFINITION);

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

				if (!c.getString(iDef).equals("")
						&& c.getString(iDef).equalsIgnoreCase(definition)) {
					definitionExist = true;
				}
			}

			return definitionExist;
		}
		
*/

	// look for inserted definition name, if already exists return false
	protected boolean isDefinitionExistent(String definition, int userId) {

		boolean definitionExist = false;
		String querySelection = "definition=\"" + definition
				+ "\" AND userIdForeign=\"" + userId + "\"";
		// check database_table2 for activity definition
		String[] columns = new String[] { KEY_DEFINITION };
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, querySelection,
				null, null, null, null);

		int iDef = c.getColumnIndex(KEY_DEFINITION);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

			if (!c.getString(iDef).equals("")
					&& c.getString(iDef).equalsIgnoreCase(definition)) {
				definitionExist = true;
			}
		}

		return definitionExist;
	}
}