package fpoly.hailxph49396.duan1_quanlybanhang.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.hailxph49396.duan1_quanlybanhang.Database.DbHelper;
import fpoly.hailxph49396.duan1_quanlybanhang.DTO.NhanVienDTO;

public class nhanvienDao {
    private final DbHelper dbHelper;

    public nhanvienDao(Context context) {
        // Thay thế dòng này bằng cách sử dụng context đúng
        this.dbHelper = new DbHelper(context); // DbHelper cần context từ Activity/Context để mở cơ sở dữ liệu
    }

    public boolean addEmployee(NhanVienDTO nhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nhanVien.getUsername());
        values.put("password", nhanVien.getPassword());
        values.put("ten", nhanVien.getName());
        values.put("ho_va_ten_dem", nhanVien.getMiddleName());
        values.put("gioi_tinh", nhanVien.getGender());
        values.put("so_dien_thoai", nhanVien.getPhone());
        values.put("email", nhanVien.getEmail());
        values.put("dia_chi", nhanVien.getAddress());
        long result = db.insert(DbHelper.TABLE_TAI_KHOAN, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateEmployee(NhanVienDTO nhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nhanVien.getUsername());
        values.put("password", nhanVien.getPassword());
        values.put("ten", nhanVien.getName());
        values.put("ho_va_ten_dem", nhanVien.getMiddleName());
        values.put("gioi_tinh", nhanVien.getGender());
        values.put("so_dien_thoai", nhanVien.getPhone());
        values.put("email", nhanVien.getEmail());
        values.put("dia_chi", nhanVien.getAddress());
        int rowsUpdated = db.update(DbHelper.TABLE_TAI_KHOAN, values, "username = ?", new String[]{nhanVien.getUsername()});
        db.close();
        return rowsUpdated > 0;
    }

    public boolean deleteEmployee(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(DbHelper.TABLE_TAI_KHOAN, "username = ?", new String[]{username});
        db.close();
        return rowsDeleted > 0;
    }

    public List<NhanVienDTO> getAllEmployees() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<NhanVienDTO> employeeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHelper.TABLE_TAI_KHOAN, null);
        if (cursor.moveToFirst()) {
            do {
                NhanVienDTO employee = new NhanVienDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ten")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ho_va_ten_dem")),
                        cursor.getString(cursor.getColumnIndexOrThrow("gioi_tinh")),
                        cursor.getString(cursor.getColumnIndexOrThrow("so_dien_thoai")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dia_chi"))
                );
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return employeeList;
    }
}
