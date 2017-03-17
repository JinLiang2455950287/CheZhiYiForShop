package com.ruanyun.chezhiyi.commonlib.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HX_USER_GROUP".
*/
public class HxUserGroupDao extends AbstractDao<HxUserGroup, Void> {

    public static final String TABLENAME = "HX_USER_GROUP";

    /**
     * Properties of entity HxUserGroup.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property GroupInfoNum = new Property(0, String.class, "groupInfoNum", false, "GROUP_INFO_NUM");
        public final static Property GroupName = new Property(1, String.class, "groupName", false, "GROUP_NAME");
        public final static Property GroupPath = new Property(2, String.class, "groupPath", false, "GROUP_PATH");
        public final static Property GroupTotalNum = new Property(3, int.class, "groupTotalNum", false, "GROUP_TOTAL_NUM");
        public final static Property HuanxinNum = new Property(4, String.class, "huanxinNum", false, "HUANXIN_NUM");
        public final static Property RelationType = new Property(5, int.class, "relationType", false, "RELATION_TYPE");
        public final static Property UserNum = new Property(6, String.class, "userNum", false, "USER_NUM");
    }


    public HxUserGroupDao(DaoConfig config) {
        super(config);
    }
    
    public HxUserGroupDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HX_USER_GROUP\" (" + //
                "\"GROUP_INFO_NUM\" TEXT NOT NULL UNIQUE ," + // 0: groupInfoNum
                "\"GROUP_NAME\" TEXT," + // 1: groupName
                "\"GROUP_PATH\" TEXT," + // 2: groupPath
                "\"GROUP_TOTAL_NUM\" INTEGER NOT NULL ," + // 3: groupTotalNum
                "\"HUANXIN_NUM\" TEXT," + // 4: huanxinNum
                "\"RELATION_TYPE\" INTEGER NOT NULL ," + // 5: relationType
                "\"USER_NUM\" TEXT);"); // 6: userNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HX_USER_GROUP\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HxUserGroup entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getGroupInfoNum());
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(2, groupName);
        }
 
        String groupPath = entity.getGroupPath();
        if (groupPath != null) {
            stmt.bindString(3, groupPath);
        }
        stmt.bindLong(4, entity.getGroupTotalNum());
 
        String huanxinNum = entity.getHuanxinNum();
        if (huanxinNum != null) {
            stmt.bindString(5, huanxinNum);
        }
        stmt.bindLong(6, entity.getRelationType());
 
        String userNum = entity.getUserNum();
        if (userNum != null) {
            stmt.bindString(7, userNum);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HxUserGroup entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getGroupInfoNum());
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(2, groupName);
        }
 
        String groupPath = entity.getGroupPath();
        if (groupPath != null) {
            stmt.bindString(3, groupPath);
        }
        stmt.bindLong(4, entity.getGroupTotalNum());
 
        String huanxinNum = entity.getHuanxinNum();
        if (huanxinNum != null) {
            stmt.bindString(5, huanxinNum);
        }
        stmt.bindLong(6, entity.getRelationType());
 
        String userNum = entity.getUserNum();
        if (userNum != null) {
            stmt.bindString(7, userNum);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public HxUserGroup readEntity(Cursor cursor, int offset) {
        HxUserGroup entity = new HxUserGroup( //
            cursor.getString(offset + 0), // groupInfoNum
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // groupName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // groupPath
            cursor.getInt(offset + 3), // groupTotalNum
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // huanxinNum
            cursor.getInt(offset + 5), // relationType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // userNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HxUserGroup entity, int offset) {
        entity.setGroupInfoNum(cursor.getString(offset + 0));
        entity.setGroupName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupPath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setGroupTotalNum(cursor.getInt(offset + 3));
        entity.setHuanxinNum(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRelationType(cursor.getInt(offset + 5));
        entity.setUserNum(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(HxUserGroup entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(HxUserGroup entity) {
        return null;
    }

    @Override
    public boolean hasKey(HxUserGroup entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
