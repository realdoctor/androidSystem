package com.real.doctor.realdoc.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.real.doctor.realdoc.model.SaveDocBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SAVE_DOC_BEAN".
*/
public class SaveDocBeanDao extends AbstractDao<SaveDocBean, String> {

    public static final String TABLENAME = "SAVE_DOC_BEAN";

    /**
     * Properties of entity SaveDocBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Ill = new Property(1, String.class, "ill", false, "ILL");
        public final static Property Hospital = new Property(2, String.class, "hospital", false, "HOSPITAL");
        public final static Property Doctor = new Property(3, String.class, "doctor", false, "DOCTOR");
        public final static Property DoctorUserId = new Property(4, String.class, "doctorUserId", false, "DOCTOR_USER_ID");
        public final static Property Time = new Property(5, String.class, "time", false, "TIME");
        public final static Property Folder = new Property(6, String.class, "folder", false, "FOLDER");
        public final static Property Imgs = new Property(7, String.class, "imgs", false, "IMGS");
        public final static Property Advice = new Property(8, String.class, "advice", false, "ADVICE");
        public final static Property OrgCode = new Property(9, String.class, "orgCode", false, "ORG_CODE");
        public final static Property PatientDiagId = new Property(10, String.class, "patientDiagId", false, "PATIENT_DIAG_ID");
        public final static Property PatientId = new Property(11, String.class, "patientId", false, "PATIENT_ID");
        public final static Property VisitDeptName = new Property(12, String.class, "visitDeptName", false, "VISIT_DEPT_NAME");
        public final static Property VisitWay = new Property(13, String.class, "visitWay", false, "VISIT_WAY");
        public final static Property IsSelect = new Property(14, boolean.class, "isSelect", false, "IS_SELECT");
        public final static Property IsPatient = new Property(15, String.class, "isPatient", false, "IS_PATIENT");
    }

    private DaoSession daoSession;


    public SaveDocBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SaveDocBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SAVE_DOC_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"ILL\" TEXT," + // 1: ill
                "\"HOSPITAL\" TEXT," + // 2: hospital
                "\"DOCTOR\" TEXT," + // 3: doctor
                "\"DOCTOR_USER_ID\" TEXT," + // 4: doctorUserId
                "\"TIME\" TEXT," + // 5: time
                "\"FOLDER\" TEXT," + // 6: folder
                "\"IMGS\" TEXT," + // 7: imgs
                "\"ADVICE\" TEXT," + // 8: advice
                "\"ORG_CODE\" TEXT," + // 9: orgCode
                "\"PATIENT_DIAG_ID\" TEXT," + // 10: patientDiagId
                "\"PATIENT_ID\" TEXT," + // 11: patientId
                "\"VISIT_DEPT_NAME\" TEXT," + // 12: visitDeptName
                "\"VISIT_WAY\" TEXT," + // 13: visitWay
                "\"IS_SELECT\" INTEGER NOT NULL ," + // 14: isSelect
                "\"IS_PATIENT\" TEXT);"); // 15: isPatient
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SAVE_DOC_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SaveDocBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String ill = entity.getIll();
        if (ill != null) {
            stmt.bindString(2, ill);
        }
 
        String hospital = entity.getHospital();
        if (hospital != null) {
            stmt.bindString(3, hospital);
        }
 
        String doctor = entity.getDoctor();
        if (doctor != null) {
            stmt.bindString(4, doctor);
        }
 
        String doctorUserId = entity.getDoctorUserId();
        if (doctorUserId != null) {
            stmt.bindString(5, doctorUserId);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(6, time);
        }
 
        String folder = entity.getFolder();
        if (folder != null) {
            stmt.bindString(7, folder);
        }
 
        String imgs = entity.getImgs();
        if (imgs != null) {
            stmt.bindString(8, imgs);
        }
 
        String advice = entity.getAdvice();
        if (advice != null) {
            stmt.bindString(9, advice);
        }
 
        String orgCode = entity.getOrgCode();
        if (orgCode != null) {
            stmt.bindString(10, orgCode);
        }
 
        String patientDiagId = entity.getPatientDiagId();
        if (patientDiagId != null) {
            stmt.bindString(11, patientDiagId);
        }
 
        String patientId = entity.getPatientId();
        if (patientId != null) {
            stmt.bindString(12, patientId);
        }
 
        String visitDeptName = entity.getVisitDeptName();
        if (visitDeptName != null) {
            stmt.bindString(13, visitDeptName);
        }
 
        String visitWay = entity.getVisitWay();
        if (visitWay != null) {
            stmt.bindString(14, visitWay);
        }
        stmt.bindLong(15, entity.getIsSelect() ? 1L: 0L);
 
        String isPatient = entity.getIsPatient();
        if (isPatient != null) {
            stmt.bindString(16, isPatient);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SaveDocBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String ill = entity.getIll();
        if (ill != null) {
            stmt.bindString(2, ill);
        }
 
        String hospital = entity.getHospital();
        if (hospital != null) {
            stmt.bindString(3, hospital);
        }
 
        String doctor = entity.getDoctor();
        if (doctor != null) {
            stmt.bindString(4, doctor);
        }
 
        String doctorUserId = entity.getDoctorUserId();
        if (doctorUserId != null) {
            stmt.bindString(5, doctorUserId);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(6, time);
        }
 
        String folder = entity.getFolder();
        if (folder != null) {
            stmt.bindString(7, folder);
        }
 
        String imgs = entity.getImgs();
        if (imgs != null) {
            stmt.bindString(8, imgs);
        }
 
        String advice = entity.getAdvice();
        if (advice != null) {
            stmt.bindString(9, advice);
        }
 
        String orgCode = entity.getOrgCode();
        if (orgCode != null) {
            stmt.bindString(10, orgCode);
        }
 
        String patientDiagId = entity.getPatientDiagId();
        if (patientDiagId != null) {
            stmt.bindString(11, patientDiagId);
        }
 
        String patientId = entity.getPatientId();
        if (patientId != null) {
            stmt.bindString(12, patientId);
        }
 
        String visitDeptName = entity.getVisitDeptName();
        if (visitDeptName != null) {
            stmt.bindString(13, visitDeptName);
        }
 
        String visitWay = entity.getVisitWay();
        if (visitWay != null) {
            stmt.bindString(14, visitWay);
        }
        stmt.bindLong(15, entity.getIsSelect() ? 1L: 0L);
 
        String isPatient = entity.getIsPatient();
        if (isPatient != null) {
            stmt.bindString(16, isPatient);
        }
    }

    @Override
    protected final void attachEntity(SaveDocBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public SaveDocBean readEntity(Cursor cursor, int offset) {
        SaveDocBean entity = new SaveDocBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ill
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // hospital
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // doctor
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // doctorUserId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // time
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // folder
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // imgs
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // advice
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // orgCode
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // patientDiagId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // patientId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // visitDeptName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // visitWay
            cursor.getShort(offset + 14) != 0, // isSelect
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // isPatient
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SaveDocBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIll(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHospital(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDoctor(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDoctorUserId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFolder(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setImgs(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAdvice(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setOrgCode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPatientDiagId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPatientId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setVisitDeptName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setVisitWay(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsSelect(cursor.getShort(offset + 14) != 0);
        entity.setIsPatient(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    @Override
    protected final String updateKeyAfterInsert(SaveDocBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(SaveDocBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SaveDocBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
