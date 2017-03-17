package com.ruanyun.chezhiyi.commonlib.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ruanyun.chezhiyi.commonlib.model.HxUser;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HX_USER".
*/
public class HxUserDao extends AbstractDao<HxUser, Void> {

    public static final String TABLENAME = "HX_USER";

    /**
     * Properties of entity HxUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UserNick = new Property(0, String.class, "userNick", false, "USER_NICK");
        public final static Property UserPhoto = new Property(1, String.class, "userPhoto", false, "USER_PHOTO");
        public final static Property UserNum = new Property(2, String.class, "userNum", false, "USER_NUM");
        public final static Property GroupNum = new Property(3, String.class, "groupNum", false, "GROUP_NUM");
        public final static Property GroupName = new Property(4, String.class, "groupName", false, "GROUP_NAME");
        public final static Property UserType = new Property(5, int.class, "userType", false, "USER_TYPE");
    }


    public HxUserDao(DaoConfig config) {
        super(config);
    }
    
    public HxUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HX_USER\" (" + //
                "\"USER_NICK\" TEXT," + // 0: userNick
                "\"USER_PHOTO\" TEXT," + // 1: userPhoto
                "\"USER_NUM\" TEXT NOT NULL UNIQUE ," + // 2: userNum
                "\"GROUP_NUM\" TEXT," + // 3: groupNum
                "\"GROUP_NAME\" TEXT," + // 4: groupName
                "\"USER_TYPE\" INTEGER NOT NULL );"); // 5: userType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HX_USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HxUser entity) {
        stmt.clearBindings();
 
        String userNick = entity.getUserNick();
        if (userNick != null) {
            stmt.bindString(1, userNick);
        }
 
        String userPhoto = entity.getUserPhoto();
        if (userPhoto != null) {
            stmt.bindString(2, userPhoto);
        }
        stmt.bindString(3, entity.getUserNum());
 
        String groupNum = entity.getGroupNum();
        if (groupNum != null) {
            stmt.bindString(4, groupNum);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(5, groupName);
        }
        stmt.bindLong(6, entity.getUserType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HxUser entity) {
        stmt.clearBindings();
 
        String userNick = entity.getUserNick();
        if (userNick != null) {
            stmt.bindString(1, userNick);
        }
 
        String userPhoto = entity.getUserPhoto();
        if (userPhoto != null) {
            stmt.bindString(2, userPhoto);
        }
        stmt.bindString(3, entity.getUserNum());
 
        String groupNum = entity.getGroupNum();
        if (groupNum != null) {
            stmt.bindString(4, groupNum);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(5, groupName);
        }
        stmt.bindLong(6, entity.getUserType());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public HxUser readEntity(Cursor cursor, int offset) {
        HxUser entity = new HxUser( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userNick
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userPhoto
            cursor.getString(offset + 2), // userNum
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // groupNum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // groupName
            cursor.getInt(offset + 5) // userType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HxUser entity, int offset) {
        entity.setUserNick(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserPhoto(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserNum(cursor.getString(offset + 2));
        entity.setGroupNum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGroupName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserType(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(HxUser entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(HxUser entity) {
        return null;
    }

    @Override
    public boolean hasKey(HxUser entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
