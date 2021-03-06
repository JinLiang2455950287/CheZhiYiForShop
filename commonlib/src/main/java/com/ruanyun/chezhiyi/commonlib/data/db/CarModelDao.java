package com.ruanyun.chezhiyi.commonlib.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ruanyun.chezhiyi.commonlib.model.CarModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CAR_MODEL".
*/
public class CarModelDao extends AbstractDao<CarModel, Void> {

    public static final String TABLENAME = "CAR_MODEL";

    /**
     * Properties of entity CarModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CarModelId = new Property(0, int.class, "carModelId", false, "CAR_MODEL_ID");
        public final static Property CarModelNum = new Property(1, String.class, "carModelNum", false, "CAR_MODEL_NUM");
        public final static Property Pinyin = new Property(2, String.class, "pinyin", false, "PINYIN");
        public final static Property CarModelName = new Property(3, String.class, "carModelName", false, "CAR_MODEL_NAME");
        public final static Property ParentModelNum = new Property(4, String.class, "parentModelNum", false, "PARENT_MODEL_NUM");
        public final static Property CarModelAllName = new Property(5, String.class, "carModelAllName", false, "CAR_MODEL_ALL_NAME");
        public final static Property SortNum = new Property(6, int.class, "sortNum", false, "SORT_NUM");
    }


    public CarModelDao(DaoConfig config) {
        super(config);
    }
    
    public CarModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CAR_MODEL\" (" + //
                "\"CAR_MODEL_ID\" INTEGER NOT NULL ," + // 0: carModelId
                "\"CAR_MODEL_NUM\" TEXT NOT NULL UNIQUE ," + // 1: carModelNum
                "\"PINYIN\" TEXT," + // 2: pinyin
                "\"CAR_MODEL_NAME\" TEXT," + // 3: carModelName
                "\"PARENT_MODEL_NUM\" TEXT," + // 4: parentModelNum
                "\"CAR_MODEL_ALL_NAME\" TEXT," + // 5: carModelAllName
                "\"SORT_NUM\" INTEGER NOT NULL );"); // 6: sortNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CAR_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CarModel entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCarModelId());
        stmt.bindString(2, entity.getCarModelNum());
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(3, pinyin);
        }
 
        String carModelName = entity.getCarModelName();
        if (carModelName != null) {
            stmt.bindString(4, carModelName);
        }
 
        String parentModelNum = entity.getParentModelNum();
        if (parentModelNum != null) {
            stmt.bindString(5, parentModelNum);
        }
 
        String carModelAllName = entity.getCarModelAllName();
        if (carModelAllName != null) {
            stmt.bindString(6, carModelAllName);
        }
        stmt.bindLong(7, entity.getSortNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CarModel entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCarModelId());
        stmt.bindString(2, entity.getCarModelNum());
 
        String pinyin = entity.getPinyin();
        if (pinyin != null) {
            stmt.bindString(3, pinyin);
        }
 
        String carModelName = entity.getCarModelName();
        if (carModelName != null) {
            stmt.bindString(4, carModelName);
        }
 
        String parentModelNum = entity.getParentModelNum();
        if (parentModelNum != null) {
            stmt.bindString(5, parentModelNum);
        }
 
        String carModelAllName = entity.getCarModelAllName();
        if (carModelAllName != null) {
            stmt.bindString(6, carModelAllName);
        }
        stmt.bindLong(7, entity.getSortNum());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public CarModel readEntity(Cursor cursor, int offset) {
        CarModel entity = new CarModel( //
            cursor.getInt(offset + 0), // carModelId
            cursor.getString(offset + 1), // carModelNum
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pinyin
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // carModelName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // parentModelNum
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // carModelAllName
            cursor.getInt(offset + 6) // sortNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CarModel entity, int offset) {
        entity.setCarModelId(cursor.getInt(offset + 0));
        entity.setCarModelNum(cursor.getString(offset + 1));
        entity.setPinyin(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCarModelName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setParentModelNum(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCarModelAllName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSortNum(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(CarModel entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(CarModel entity) {
        return null;
    }

    @Override
    public boolean hasKey(CarModel entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
