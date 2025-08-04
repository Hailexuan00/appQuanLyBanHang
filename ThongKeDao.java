package fpoly.hailxph49396.duan1_quanlybanhang.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.hailxph49396.duan1_quanlybanhang.Database.DbHelper;

public class ThongKeDao {
    private SQLiteDatabase db;
    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ThongKeDao(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {

        Log.d("DoanhThuQuery", "Executing SQL: SELECT SUM(thanh_tien) as doanhThu FROM TABLE_DON_HANG WHERE ngay BETWEEN ? AND ? with dates: " + tuNgay + " to " + denNgay);


        String sqlDoanhThu = "SELECT SUM(thanh_tien) as doanhThu FROM DonHang WHERE ngay BETWEEN ? AND ? AND trang_thai = 1";
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        int doanhThu = 0;

        try {

            if (c != null && c.moveToFirst()) {

                doanhThu = c.getInt(c.getColumnIndex("doanhThu"));
            }
        } catch (Exception e) {

            Log.e("ThongKeDao", "Error calculating Doanh Thu", e);
            doanhThu = 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }


        return doanhThu;
    }
}
