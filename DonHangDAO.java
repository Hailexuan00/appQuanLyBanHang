package fpoly.hailxph49396.duan1_quanlybanhang.DAO;

import static fpoly.hailxph49396.duan1_quanlybanhang.Database.DbHelper.TABLE_CHI_TIET_DON_HANG;
import static fpoly.hailxph49396.duan1_quanlybanhang.Database.DbHelper.TABLE_DON_HANG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.hailxph49396.duan1_quanlybanhang.DTO.DonHangDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.Database.DbHelper;

public class DonHangDAO {
    private final DbHelper dbHelper;
    DonHangDTO donHangDTO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DonHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    @SuppressLint("Range")
    public ArrayList<DonHangDTO> getListDonHang() {
        ArrayList<DonHangDTO> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;


        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM DonHang", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Date ngay = null;
                    try {
                        String ngayString = cursor.getString(cursor.getColumnIndex("ngay"));
                        if (ngayString != null) {
                            ngay = sdf.parse(ngayString);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    DonHangDTO donHang = new DonHangDTO(
                            cursor.getInt(cursor.getColumnIndex("id_don_hang")),
                            cursor.getString(cursor.getColumnIndex("username")),
                            cursor.getString(cursor.getColumnIndex("so_dien_thoai_kh")),
                            cursor.getInt(cursor.getColumnIndex("thanh_tien")),
                            ngay,
                            cursor.getString(cursor.getColumnIndex("gio")),
                            cursor.getInt(cursor.getColumnIndex("trang_thai"))
                    );

                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DonHang", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return list;
    }

    public long addDonHang(DonHangDTO donHang) {
        SQLiteDatabase db = null;
        long result = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", donHang.getUsername());
            values.put("so_dien_thoai_kh", donHang.getSoDienThoaiKH());
            values.put("thanh_tien", donHang.getThanhTien());
            values.put("ngay", sdf.format(donHang.getNgay()));
            values.put("gio", donHang.getGio());
            values.put("trang_thai", donHang.getTrangThai());

            result = db.insert("DonHang", null, values);
        } catch (Exception e) {
            Log.e("DonHang", "Error: " + e);
        } finally {
            if (db != null && db.isOpen()) db.close();
        }

        return result;
    }

    @SuppressLint("Range")
    public ArrayList<DonHangDTO> getListDonHangToday() {
        ArrayList<DonHangDTO> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(new Date());

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM DonHang WHERE ngay = ?";
            cursor = db.rawQuery(sql, new String[]{today});

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Date ngay = sdf.parse(cursor.getString(cursor.getColumnIndex("ngay")));

                    DonHangDTO donHang = new DonHangDTO(
                            cursor.getInt(cursor.getColumnIndex("id_don_hang")),
                            cursor.getString(cursor.getColumnIndex("username")),
                            cursor.getString(cursor.getColumnIndex("so_dien_thoai_kh")),
                            cursor.getInt(cursor.getColumnIndex("thanh_tien")),
                            ngay,
                            cursor.getString(cursor.getColumnIndex("gio")),
                            cursor.getInt(cursor.getColumnIndex("trang_thai"))
                    );

                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DonHang", "Error: " + e.toString());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return list;
    }

    public int updateTrangThaiDonHang(int maDonHang, int trangThaiMoi) {
        SQLiteDatabase db = null;
        int result = -1;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("trang_thai", trangThaiMoi);

            result = db.update("DonHang", values, "id_don_hang = ?", new String[]{String.valueOf(maDonHang)});
        } catch (Exception e) {
            Log.e("DonHang", "Error: " + e);
        } finally {
            if (db != null && db.isOpen()) db.close();
        }
        return result;
    }

    @SuppressLint("Range")
    public DonHangDTO getLatestDonHang() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        DonHangDTO donHang = null;

        try {
            db = dbHelper.getReadableDatabase();

            // Sử dụng câu truy vấn SQL để lấy đơn hàng gần nhất theo ngày và giờ
            String sql = "SELECT * FROM DonHang ORDER BY ngay DESC, gio DESC LIMIT 1";
            cursor = db.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                Date ngay = null;
                try {
                    String ngayString = cursor.getString(cursor.getColumnIndex("ngay"));
                    if (ngayString != null) {
                        ngay = sdf.parse(ngayString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                donHang = new DonHangDTO(
                        cursor.getInt(cursor.getColumnIndex("id_don_hang")),
                        cursor.getString(cursor.getColumnIndex("username")),
                        cursor.getString(cursor.getColumnIndex("so_dien_thoai_kh")),
                        cursor.getInt(cursor.getColumnIndex("thanh_tien")),
                        ngay,
                        cursor.getString(cursor.getColumnIndex("gio")),
                        cursor.getInt(cursor.getColumnIndex("trang_thai"))
                );
            }
        } catch (Exception e) {
            Log.e("DonHang", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return donHang;
    }

    public int updateOrder(DonHangDTO order) {
        SQLiteDatabase db = null;
        int result = -1;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Gán giá trị từ đối tượng DonHangDTO
            values.put("username", order.getUsername());
            values.put("so_dien_thoai_kh", order.getSoDienThoaiKH());
            values.put("thanh_tien", order.getThanhTien());
            values.put("ngay", sdf.format(order.getNgay()));
            values.put("gio", order.getGio());
            values.put("trang_thai", order.getTrangThai());

            // Thực hiện cập nhật dựa trên id_don_hang
            result = db.update(TABLE_DON_HANG, values, "id_don_hang = ?", new String[]{String.valueOf(order.getMaDonHang())});
        } catch (Exception e) {
            Log.e("OrderDAO", "Error updating order: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return result; // Trả về số dòng bị ảnh hưởng (1 nếu thành công, 0 nếu không có gì cập nhật)
    }

    public int deleteDonHang(int maDonHang) {
        SQLiteDatabase db = null;
        int result = -1;

        try {
            db = dbHelper.getWritableDatabase();
            db.delete(TABLE_CHI_TIET_DON_HANG, "id_don_hang = ?", new String[]{String.valueOf(maDonHang)});
            // Xóa đơn hàng dựa trên id_don_hang
            result = db.delete(TABLE_DON_HANG, "id_don_hang = ?", new String[]{String.valueOf(maDonHang)});
        } catch (Exception e) {
            Log.e("DonHang", "Error deleting order: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return result; // Trả về số dòng bị ảnh hưởng (1 nếu xóa thành công, 0 nếu thất bại)
    }

    @SuppressLint("Range")
    public int getTongThanhTien(String startDate, String endDate) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int tongThanhTien = 0;

        try {
            db = dbHelper.getReadableDatabase();
            String query;
            String[] args;

            if (endDate == null) {
                query = "SELECT SUM(thanh_tien) as total FROM DonHang WHERE ngay = ?";
                args = new String[]{startDate};
            }
            else {
                query = "SELECT SUM(thanh_tien) as total FROM DonHang WHERE ngay BETWEEN ? AND ?";
                args = new String[]{startDate, endDate};
            }

            cursor = db.rawQuery(query, args);

            if (cursor != null && cursor.moveToFirst()) {
                tongThanhTien = cursor.getInt(cursor.getColumnIndex("total"));
            }
        } catch (Exception e) {
            Log.e("DonHangDAO", "Error: " + e.toString());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return tongThanhTien;
    }


}
