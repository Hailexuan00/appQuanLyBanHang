package fpoly.hailxph49396.duan1_quanlybanhang.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.hailxph49396.duan1_quanlybanhang.DTO.SanPhamDTO;
import fpoly.hailxph49396.duan1_quanlybanhang.Database.DbHelper;

public class SanPhamDAo {
    private final DbHelper dbHelper;

    public SanPhamDAo(Context context) {
        dbHelper = new DbHelper(context);
    }

    @SuppressLint("Range")
    public ArrayList<SanPhamDTO> getAllProducts() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM SanPham", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SanPhamDTO product = new SanPhamDTO(
                            cursor.getInt(cursor.getColumnIndex("id_san_pham")),
                            cursor.getInt(cursor.getColumnIndex("id_danh_muc")),
                            cursor.getString(cursor.getColumnIndex("ten_san_pham")),
                            cursor.getInt(cursor.getColumnIndex("don_gia")),
                            cursor.getInt(cursor.getColumnIndex("ton_kho")),
                            cursor.getString(cursor.getColumnIndex("ma_vach")),
                            cursor.getString(cursor.getColumnIndex("mo_ta"))
                    );
                    list.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return list;
    }

    //tìm theo mã vạch
    @SuppressLint("Range")
    public SanPhamDTO findProductByBarcode(String barcode) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM SanPham WHERE ma_vach = ?";
            cursor = db.rawQuery(sql, new String[]{barcode});

            if (cursor != null && cursor.moveToFirst()) {
                return new SanPhamDTO(
                        cursor.getInt(cursor.getColumnIndex("id_san_pham")),
                        cursor.getInt(cursor.getColumnIndex("id_danh_muc")),
                        cursor.getString(cursor.getColumnIndex("ten_san_pham")),
                        cursor.getInt(cursor.getColumnIndex("don_gia")),
                        cursor.getInt(cursor.getColumnIndex("ton_kho")),
                        cursor.getString(cursor.getColumnIndex("ma_vach")),
                        cursor.getString(cursor.getColumnIndex("mo_ta"))
                );
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return null;
    }

    //kiểm tra tồn tại theo mã vạch
    public boolean isProductExists(String barcode) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean exists = false;

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM SanPham WHERE ma_vach = ?";
            cursor = db.rawQuery(sql, new String[]{barcode});
            exists = cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return exists;
    }

    // Thêm sản phẩm
    public long addProduct(SanPhamDTO product) {
        SQLiteDatabase db = null;
        long result = -1;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_danh_muc", product.getMaDanhMuc());
            values.put("ten_san_pham", product.getTenSanPham());
            values.put("don_gia", product.getGiaBan());
            values.put("ton_kho", product.getTonKho());
            values.put("ma_vach", product.getMaVach());
            values.put("mo_ta", product.getMoTa());

            result = db.insert("SanPham", null, values);
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (db != null && db.isOpen()) db.close();
        }

        return result;
    }

    // Sửa sản phẩm theo mã vạch
    public int updateProduct(SanPhamDTO product) {
        SQLiteDatabase db = null;
        int result = -1;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_danh_muc", product.getMaDanhMuc());
            values.put("ten_san_pham", product.getTenSanPham());
            values.put("don_gia", product.getGiaBan());
            values.put("ton_kho", product.getTonKho());
            values.put("ma_vach", product.getMaVach());
            values.put("mo_ta", product.getMoTa());

            result = db.update("SanPham", values, "ma_vach = ?", new String[]{product.getMaVach()});
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (db != null && db.isOpen()) db.close();
        }

        return result;
    }

    // Xóa sản phẩm theo mã vạch
    public int deleteProduct(String barcode) {
        SQLiteDatabase db = null;
        int result = -1;

        try {
            db = dbHelper.getWritableDatabase();
            result = db.delete("SanPham", "ma_vach = ?", new String[]{barcode});
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (db != null && db.isOpen()) db.close();
        }

        return result;
    }

    @SuppressLint("Range")
    public SanPhamDTO findProductById(int productId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM SanPham WHERE id_san_pham = ?";
            cursor = db.rawQuery(sql, new String[]{String.valueOf(productId)});

            if (cursor != null && cursor.moveToFirst()) {
                return new SanPhamDTO(
                        cursor.getInt(cursor.getColumnIndex("id_san_pham")),
                        cursor.getInt(cursor.getColumnIndex("id_danh_muc")),
                        cursor.getString(cursor.getColumnIndex("ten_san_pham")),
                        cursor.getInt(cursor.getColumnIndex("don_gia")),
                        cursor.getInt(cursor.getColumnIndex("ton_kho")),
                        cursor.getString(cursor.getColumnIndex("ma_vach")),
                        cursor.getString(cursor.getColumnIndex("mo_ta"))
                );
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return null; // Nếu không tìm thấy
    }
    @SuppressLint("Range")
    public ArrayList<SanPhamDTO> filterProductsByCategory(int categoryId) {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM SanPham WHERE id_danh_muc = ?";
            cursor = db.rawQuery(sql, new String[]{String.valueOf(categoryId)});

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SanPhamDTO product = new SanPhamDTO(
                            cursor.getInt(cursor.getColumnIndex("id_san_pham")),
                            cursor.getInt(cursor.getColumnIndex("id_danh_muc")),
                            cursor.getString(cursor.getColumnIndex("ten_san_pham")),
                            cursor.getInt(cursor.getColumnIndex("don_gia")),
                            cursor.getInt(cursor.getColumnIndex("ton_kho")),
                            cursor.getString(cursor.getColumnIndex("ma_vach")),
                            cursor.getString(cursor.getColumnIndex("mo_ta"))
                    );
                    list.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return list;
    }

    // Tìm sản phẩm theo một phần mã vạch
    @SuppressLint("Range")
    public ArrayList<SanPhamDTO> searchProductsByPartialBarcode(String partialBarcode) {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM SanPham WHERE ma_vach LIKE ?";
            cursor = db.rawQuery(sql, new String[]{"%" + partialBarcode + "%"});

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SanPhamDTO product = new SanPhamDTO(
                            cursor.getInt(cursor.getColumnIndex("id_san_pham")),
                            cursor.getInt(cursor.getColumnIndex("id_danh_muc")),
                            cursor.getString(cursor.getColumnIndex("ten_san_pham")),
                            cursor.getInt(cursor.getColumnIndex("don_gia")),
                            cursor.getInt(cursor.getColumnIndex("ton_kho")),
                            cursor.getString(cursor.getColumnIndex("ma_vach")),
                            cursor.getString(cursor.getColumnIndex("mo_ta"))
                    );
                    list.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ProductDAO", "Error: " + e);
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }

        return list;
    }


}
