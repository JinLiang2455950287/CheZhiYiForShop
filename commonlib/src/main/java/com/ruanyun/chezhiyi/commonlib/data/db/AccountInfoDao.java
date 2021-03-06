package com.ruanyun.chezhiyi.commonlib.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ruanyun.chezhiyi.commonlib.model.AccountInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCOUNT_INFO".
*/
public class AccountInfoDao extends AbstractDao<AccountInfo, Long> {

    public static final String TABLENAME = "ACCOUNT_INFO";

    /**
     * Properties of entity AccountInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property LoginName = new Property(1, String.class, "loginName", false, "LOGIN_NAME");
        public final static Property PassWord = new Property(2, String.class, "passWord", false, "PASS_WORD");
        public final static Property UserNum = new Property(3, String.class, "userNum", false, "USER_NUM");
    }


    public AccountInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AccountInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LOGIN_NAME\" TEXT," + // 1: loginName
                "\"PASS_WORD\" TEXT," + // 2: passWord
                "\"USER_NUM\" TEXT UNIQUE );"); // 3: userNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AccountInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(2, loginName);
        }
 
        String passWord = entity.getPassWord();
        if (passWord != null) {
            stmt.bindString(3, passWord);
        }
 
        String userNum = entity.getUserNum();
        if (userNum != null) {
            stmt.bindString(4, userNum);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AccountInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(2, loginName);
        }
 
        String passWord = entity.getPassWord();
        if (passWord != null) {
            stmt.bindString(3, passWord);
        }
 
        String userNum = entity.getUserNum();
        if (userNum != null) {
            stmt.bindString(4, userNum);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AccountInfo readEntity(Cursor cursor, int offset) {
        AccountInfo entity = new AccountInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // loginName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // passWord
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // userNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AccountInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLoginName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassWord(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserNum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AccountInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AccountInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AccountInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
