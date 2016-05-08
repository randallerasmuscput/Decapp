package za.ac.cput.decapp.Repositories.Impl;

/**
 * Created by User on 2016/05/04.
 */
public class SuspectRepositoryImpl {
    public static final String TABLE_NAME = "Suspect";
    private SQLiteDatabase db;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SuspectID = "SuspectId";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastName";
    public static final String COLUMN_SuspectIMAGE = "SuspectImage";
    public static final String COLUMN_SYMBOLIMAGE = "symbolImage";
    public static final String COLUMN_ELECTIONTYPEID = "electionTypeId";

    // Database creation sql statement
    private static final String DATABASE_CREATE = " CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SuspectID + " TEXT UNIQUE NOT NULL , "
            + COLUMN_FIRSTNAME + " TEXT NOT NULL , "
            + COLUMN_LASTNAME + " TEXT NOT NULL , "
            + COLUMN_SuspectIMAGE + " BLOB , "
            + COLUMN_SYMBOLIMAGE + " BLOB , "
            + COLUMN_ELECTIONTYPEID + " TEXT NOT NULL );";


    public SuspectRepositoryImpl(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        this.close();
    }

    @Override
    public Suspect findById(Long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{
                        COLUMN_ID,
                        COLUMN_SuspectID,
                        COLUMN_FIRSTNAME,
                        COLUMN_LASTNAME,
                        COLUMN_SuspectIMAGE,
                        COLUMN_SYMBOLIMAGE,
                        COLUMN_ELECTIONTYPEID},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            final Suspect Suspect = new Suspect.Builder()
                    .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                    .symbolImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_SYMBOLIMAGE)))
                    .firstname(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)))
                    .lastName(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)))
                    .SuspectImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_SuspectIMAGE)))
                    .SuspectId(cursor.getString(cursor.getColumnIndex(COLUMN_SuspectID)))
                    .electionTypeId(cursor.getString(cursor.getColumnIndex(COLUMN_ELECTIONTYPEID)))
                    .build();

            return Suspect;
        } else {
            return null;
        }
    }

    @Override
    public Suspect save(Suspect entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_SuspectID, entity.getSuspectId());
        values.put(COLUMN_FIRSTNAME, entity.getFirstname());
        values.put(COLUMN_LASTNAME, entity.getLastName());
        values.put(COLUMN_SuspectIMAGE, entity.getSuspectImage());
        values.put(COLUMN_SYMBOLIMAGE, entity.getSymbolImage());
        values.put(COLUMN_ELECTIONTYPEID, entity.getElectionTypeId());
        long id = db.insertOrThrow(TABLE_NAME, null, values);
        Suspect insertedEntity = new Suspect.Builder()
                .copy(entity)
                .id(new Long(id))
                .build();
        return insertedEntity;
    }

    @Override
    public Suspect update(Suspect entity) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entity.getId());
        values.put(COLUMN_SuspectID, entity.getSuspectId());
        values.put(COLUMN_FIRSTNAME, entity.getFirstname());
        values.put(COLUMN_LASTNAME, entity.getLastName());
        values.put(COLUMN_SuspectIMAGE, entity.getSuspectImage());
        values.put(COLUMN_SYMBOLIMAGE, entity.getSymbolImage());
        values.put(COLUMN_ELECTIONTYPEID, entity.getElectionTypeId());
        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())}
        );
        return entity;
    }

    @Override
    public Suspect delete(Suspect entity) {
        open();
        db.delete(
                TABLE_NAME,
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(entity.getId())});
        return entity;
    }

    @Override
    public Set<Suspect> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Set<Suspect> Suspects = new HashSet<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                final Suspect Suspect = new Suspect.Builder()
                        .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                        .symbolImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_SYMBOLIMAGE)))
                        .firstname(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)))
                        .lastName(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)))
                        .SuspectImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_SuspectIMAGE)))
                        .SuspectId(cursor.getString(cursor.getColumnIndex(COLUMN_SuspectID)))
                        .electionTypeId(cursor.getString(cursor.getColumnIndex(COLUMN_ELECTIONTYPEID)))
                        .build();
                Suspects.add(Suspect);
            } while (cursor.moveToNext());
        }
        return Suspects;
    }

    @Override
    public int deleteAll() {
        open();
        int rowsDeleted = db.delete(TABLE_NAME,null,null);
        close();
        return rowsDeleted;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}

}