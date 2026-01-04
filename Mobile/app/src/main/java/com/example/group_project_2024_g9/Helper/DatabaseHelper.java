package com.example.group_project_2024_g9.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food_recognition";
    private static final int DATABASE_VERSION = 10;
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_roles = "CREATE TABLE roles (" +
                "id TEXT PRIMARY KEY," +
                "role_name TEXT UNIQUE NOT NULL," +
                "description TEXT" +
                ");";

        String create_table_users = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY," +
                "email TEXT UNIQUE," +
                "user_name TEXT UNIQUE," +
                "password TEXT," +
                "oauth_provider TEXT," +
                "oauth_uid TEXT," +
                "full_name TEXT," +
                "birth_date TEXT," +
                "profile_picture TEXT," +
                "phone_number TEXT," +
                "role_id TEXT," +
                "created_at TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL" +
                ");";


        String create_table_meals = "CREATE TABLE meals (" +
                "id INTEGER PRIMARY KEY," +
                "food_name TEXT NOT NULL," +
                "category_food TEXT," +
                "description_food TEXT," +
                "image_url TEXT," +
                "created_at TEXT DEFAULT (datetime('now'))" +
                ");";

        String create_table_ingredient = "CREATE TABLE ingredient (" +
                "id INTEGER PRIMARY KEY," +
                "ingredient_name TEXT NOT NULL," +
                "description TEXT" +
                ");";

        String create_table_meal_ingredient = "CREATE TABLE meal_ingredient (" +
                "meal_id INTEGER," +
                "ingredient_id INTEGER," +
                "PRIMARY KEY (meal_id, ingredient_id)," +
                "FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE," +
                "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id) ON DELETE CASCADE" +
                ");";

        String create_table_carts = "CREATE TABLE carts (" +
                "id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "created_at TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                ");";

        String create_table_cart_items = "CREATE TABLE cart_items (" +
                "id INTEGER PRIMARY KEY," +
                "cart_id INTEGER," +
                "ingredient_id INTEGER," +
                "quantity INTEGER DEFAULT 0 CHECK (quantity <= 3 AND quantity >= 0)," +
                "added_at TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE," +
                "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id) ON DELETE CASCADE" +
                ");";

        String create_table_recipes = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY," +
                "meal_id INTEGER," +
                "description TEXT," +
                "FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE" +
                ");";

        String create_table_search_history = "CREATE TABLE search_history (" +
                "id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "search_term TEXT," +
                "search_date TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                ");";

        String create_table_favorite_meals = "CREATE TABLE favorite_meals (" +
                "id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "meal_id INTEGER," +
                "added_at TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE," +
                "FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE" +
                ");";

        String create_table_notifications = "CREATE TABLE notifications (" +
                "id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "notification_text TEXT," +
                "is_read BOOLEAN DEFAULT FALSE," +
                "sent_at TEXT DEFAULT (datetime('now'))," +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                ");";

        db.execSQL(create_table_roles);
        db.execSQL(create_table_users);
        db.execSQL(create_table_meals);
        db.execSQL(create_table_ingredient);
        db.execSQL(create_table_meal_ingredient);
        db.execSQL(create_table_carts);
        db.execSQL(create_table_cart_items);
        db.execSQL(create_table_recipes);
        db.execSQL(create_table_search_history);
        db.execSQL(create_table_favorite_meals);
        db.execSQL(create_table_notifications);

        ContentValues admin_role = new ContentValues();
        admin_role.put("id", "ADMIN");
        admin_role.put("role_name", "ADMIN");
        admin_role.put("description", "Administrator role");
        db.insert("roles", null, admin_role);

        ContentValues guest_role = new ContentValues();
        guest_role.put("id", "GUEST");
        guest_role.put("role_name", "GUEST");
        guest_role.put("description", "Guest role");
        db.insert("roles", null, guest_role);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notifications");
        db.execSQL("DROP TABLE IF EXISTS favorite_meals");
        db.execSQL("DROP TABLE IF EXISTS search_history");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS cart_items");
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS ingredient");
        db.execSQL("DROP TABLE IF EXISTS meals");
        db.execSQL("DROP TABLE IF EXISTS personality_test");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS roles");

        onCreate(db);

    }

    //---------------------------------------- Table roles -----------------------------------------
    // CREATE
    public boolean insert_role(String role_id, String role_name, String description){
        // Tạo biến db để chứa kết nối tới database
        SQLiteDatabase db = null;
        try{
            // Mở kết nối đến database ở chế độ ghi
            db = this.getWritableDatabase();
            // Tạo object ContentValues để lưu cặp giá trị (column_name, value)
            ContentValues values = new ContentValues();
            // Đặt giá trị là role_id cho cột id
            values.put("id", role_id);
            values.put("role_name", role_name);
            values.put("description", description);
            /*
            * Chèn bản ghi mới vào bảng 'roles' với các giá trị được lưu trong ContentValues.
            * Nếu thành công thì trả về ID của dòng mới được thêm, nếu gặp lỗi thì trả về -1
            */
            long result = db.insert("roles", null, values);
            // Kiếm tra nếu kết quả khác -1 thì sẽ trả về true, ngược lại là false
            return result != -1;
        } catch (SQLException e){
            // Bắt ngoại lệ trong SQLite và ghi log lỗi để xem và fix nếu có bug
            Log.e(TAG, "SQLiteException while inserting role: " + e.getMessage());
            return false;
        } catch (Exception e){
            Log.e(TAG, "Error inserting roles: " + e.getMessage());
            return false;
        } finally {
            // Đảm bảo cho việc đóng kết nối database sau khi hoàn thành thao tác, kể cả có bug
            if (db != null && db.isOpen()){
                // Đóng kết nối để tiết kiệm tài nguyên (giảm độ trễ)
                db.close();
            }
        }
    }

    // READ
    public Cursor get_all_role(){
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            // Truy vấn tất cả dữ liệu từ bảng 'roles' và trả về dưới dạn Cursor
            return db.rawQuery("SELECT * FROM roles", null);
        } catch (SQLException e){
            Log.e(TAG, "SQLiteException while reading role: " + e.getMessage());
            // Trả về null nếu gặp bug
            return null;
        } catch (Exception e){
            Log.e(TAG, "Error reading roles: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public boolean update_role(String role_id, String role_name, String description){
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("role_name", role_name);
            values.put("description", description);
            // Cập nhật bản ghi trong 'roles' với điều kiện id tương ứng
            int result = db.update("roles", values, "id = ?", new String[]{role_id});
            return result > 0;
        } catch (SQLException e){
            Log.e(TAG, "SQLiteException while updating role: " + e.getMessage());
            return false;
        } catch (Exception e){
            Log.e(TAG, "Error updating role: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()){
                db.close();
            }
        }
    }

    // DELETE
    public boolean delete_role(String role_id){
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            int result = db.delete("roles","id = ?", new String[]{role_id});
            return result > 0;
        } catch (SQLException e){
            Log.e(TAG, "SQLiteException while updating role: " + e.getMessage());
            return false;
        } catch (Exception e){
            Log.e(TAG, "Error updating role: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()){
                db.close();
            }
        }
    }

    //------------------------------------ Table users ---------------------------------------------
    // CREATE
    public boolean insert_user(String email, String user_name, String password, String phone_number, String role_id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            if (email != null){
                values.put("email", email);
            }
            if (user_name != null){
                values.put("user_name", user_name);
            }
            if (password != null) {
                values.put("password", password);
            }
            if (phone_number != null) {
                values.put("phone_number", phone_number);
            }
            if (role_id != null) {
                values.put("role_id", role_id);
            }
            long result = db.insert("users", null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while inserting user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error inserting user: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // READ
    public Cursor get_all_users() {
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM users", null);
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while reading users: " + e.getMessage());
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error reading users: " + e.getMessage());
            return null;
        }
    }
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user_name = ? AND password = ?", new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;  // Người dùng hợp lệ
        }

        if (cursor != null) {
            cursor.close();
        }

        return false;  // Người dùng không hợp lệ
    }


    // UPDATE
    public boolean update_user(int id, String email, String user_name, String password, String role_id, String phone_number) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            if (email != null){
                values.put("email", email);
            }
            if (user_name != null){
                values.put("user_name", user_name);
            }
            if (password != null) {
                values.put("password", password);
            }
            if (phone_number != null) {
                values.put("phone_number", phone_number);
            }
            if (role_id != null) {
                values.put("role_id", role_id);
            }
            int result = db.update("users", values, "id = ?", new String[]{String.valueOf(id)});
            return result > 0;
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while updating user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error updating user: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // DELETE
    public boolean delete_user(int id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            int result = db.delete("users", "id = ?", new String[]{String.valueOf(id)});
            return result > 0;
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while deleting user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting user: " + e.getMessage());
            return false;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public void showAllUsers() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();

            // Truy vấn tất cả dữ liệu từ bảng users
            cursor = db.rawQuery("SELECT * FROM users", null);

            if (cursor.moveToFirst()) {
                do {
                    // Lấy thông tin từng user
                    int user_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String user_name = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
                    String phone_number = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
                    String role_id = cursor.getString(cursor.getColumnIndexOrThrow("role_id"));

                    // In thông tin user ra Logcat
                    Log.d(TAG, "User ID: " + user_id + ", Email: " + email + ", Username: " + user_name + ", Phone: " + phone_number + ", Role: " + role_id);

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "No users found in the database.");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while retrieving users: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    //------------------------------------ Table meal_ingredient -----------------------------------

    // CREATE: Thêm món ăn mới vào bảng meals
    public long insert_meal(String food_name, String category_food, String description_food, String image_url) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("food_name", food_name);
            values.put("category_food", category_food);
            values.put("description_food", description_food);
            values.put("image_url", image_url);
            return db.insert("meals", null, values); // Trả về meal_id của món ăn vừa được thêm
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while inserting meal: " + e.getMessage());
            return -1;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // CREATE: Thêm nguyên liệu mới vào bảng ingredients
    public long insert_ingredient(String ingredient_name, String description) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("ingredient_name", ingredient_name);
            values.put("description", description);
            return db.insert("ingredient", null, values); // Trả về ingredient_id của nguyên liệu vừa được thêm
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while inserting ingredient: " + e.getMessage());
            return -1;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // CREATE: Liên kết giữa meal và ingredient vào bảng meal_ingredient
    public void insert_meal_ingredient(long meal_id, long ingredient_id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("meal_id", meal_id);
            values.put("ingredient_id", ingredient_id);
            long result = db.insert("meal_ingredient", null, values);
        } catch (SQLException e) {
            Log.e(TAG, "SQLiteException while inserting meal_ingredient: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public void add_meals_and_ingredients() {
        // Bước 1: Thêm riêng các món ăn
        long tofu_tomato_sauce_id = insert_meal("Đậu phụ sốt cà chua", "Dinner", "Món chay phổ biến", "url_to_image_tofu");
        long broken_rice_id = insert_meal("Cơm tấm", "Lunch", "Cơm tấm truyền thống", "url_to_image_rice");

        // Bước 2: Thêm riêng các nguyên liệu
        long apple_id = insert_ingredient("Apple", "Apple is a sweet and crunchy fruit, rich in fiber and vitamin C.");
        long banana_id = insert_ingredient("Banana", "Banana is a tropical fruit with a soft texture and high in potassium and carbohydrates.");
        long beetroot_id = insert_ingredient("Beetroot", "Beetroot is a root vegetable known for its deep red color and earthy flavor, rich in folate and fiber.");
        long bell_pepper_id = insert_ingredient("Bell Pepper", "Bell Pepper is a mild, sweet pepper often used in salads and cooking, rich in vitamins A and C.");
        long cabbage_id = insert_ingredient("Cabbage", "Cabbage is a leafy green vegetable often used in salads, soups, and stews, rich in fiber and vitamin K.");
        long capsicum_id = insert_ingredient("Capsicum", "Capsicum is another name for bell pepper, often referred to in Australia and South Asia.");
        long carrot_id = insert_ingredient("Carrot", "Carrot is a crunchy root vegetable known for its orange color and high vitamin A content.");
        long cauliflower_id = insert_ingredient("Cauliflower", "Cauliflower is a cruciferous vegetable often used in cooking or as a rice substitute, rich in fiber and vitamin C.");
        long chilli_pepper_id = insert_ingredient("Chilli Pepper", "Chilli Pepper is a spicy pepper used to add heat to dishes, rich in capsaicin and vitamin C.");
        long corn_id = insert_ingredient("Corn", "Corn is a starchy vegetable and grain that is often eaten fresh, boiled, or grilled.");
        long cucumber_id = insert_ingredient("Cucumber", "Cucumber is a refreshing, hydrating vegetable, often used in salads, with high water content.");
        long eggplant_id = insert_ingredient("Eggplant", "Eggplant is a purple-skinned vegetable often used in stews and grilling, rich in fiber and antioxidants.");
        long garlic_id = insert_ingredient("Garlic", "Garlic is a pungent bulb used to add flavor to dishes and known for its medicinal properties.");
        long ginger_id = insert_ingredient("Ginger", "Ginger is a spicy root used for its flavor and anti-inflammatory properties, often in teas or cooking.");
        long grapes_id = insert_ingredient("Grapes", "Grapes are small, sweet fruits that can be eaten fresh or dried as raisins, rich in vitamins C and K.");
        long jalapeno_id = insert_ingredient("Jalapeno", "Jalapeno is a medium-sized chili pepper known for its spicy flavor, often used in Mexican cuisine.");
        long kiwi_id = insert_ingredient("Kiwi", "Kiwi is a small, brown-skinned fruit with a green, tart flesh, rich in vitamin C and fiber.");
        long lemon_id = insert_ingredient("Lemon", "Lemon is a citrus fruit with a sour flavor, high in vitamin C and often used in beverages and cooking.");
        long lettuce_id = insert_ingredient("Lettuce", "Lettuce is a leafy green vegetable commonly used in salads or as a base in many dishes. It is low in calories and high in water content, making it a healthy addition to meals.");
        long mango_id = insert_ingredient("Mango", "Mango is a tropical stone fruit known for its sweet and aromatic flavor. Rich in vitamins A and C, it is often eaten fresh or used in smoothies and desserts.");
        long onion_id = insert_ingredient("Onion", "Onions are bulb vegetables with a pungent taste and are widely used for seasoning dishes. They can be eaten raw or cooked and provide a good amount of fiber and vitamin C.");
        long orange_id = insert_ingredient("Orange", "Oranges are citrus fruits rich in vitamin C and antioxidants. They are commonly consumed fresh, juiced, or added to salads and desserts.");
        long paprika_id = insert_ingredient("Paprika", "Paprika is a spice made from dried red peppers. It adds vibrant color and mild to spicy flavor to dishes and is often used in various cuisines.");
        long pear_id = insert_ingredient("Pear", "Pears are sweet, juicy fruits with a soft texture. They are rich in dietary fiber and vitamin C, making them a healthy snack or ingredient in desserts.");
        long peas_id = insert_ingredient("Peas", "Peas are a legume that is rich in protein and used in various dishes such as soups, salads, and stews. They can be eaten fresh, frozen, or dried.");
        long pineapple_id = insert_ingredient("Pineapple", "Pineapples are tropical fruits known for their sweet and tart taste. They are a good source of vitamin C and manganese and are often used in desserts, juices, or eaten fresh.");
        long pomegranate_id = insert_ingredient("Pomegranate", "Pomegranates are fruits filled with small edible seeds, rich in antioxidants and beneficial for heart health. They are commonly juiced or eaten fresh.");
        long potato_id = insert_ingredient("Potato", "Potatoes are starchy tubers that are commonly boiled, baked, or fried. They are a good source of carbohydrates, potassium, and vitamin C.");
        long radish_id = insert_ingredient("Radish", "Radishes are crunchy root vegetables with a slightly peppery flavor. They are often eaten raw in salads and provide vitamin C and antioxidants.");
        long soy_beans_id = insert_ingredient("Soy Beans", "Soybeans are a legume rich in plant-based protein and commonly used to make tofu, soy milk, and oil. They are also a good source of fiber and healthy fats.");
        long spinach_id = insert_ingredient("Spinach", "Spinach is a leafy green vegetable packed with iron, calcium, and vitamins A and K. It is often used in salads, soups, and stir-fries.");
        long sweetcorn_id = insert_ingredient("Sweetcorn", "Sweetcorn is a cereal grain that is high in natural sugars, making it sweet and starchy. It is rich in fiber, vitamins, and is often eaten boiled or grilled.");
        long sweet_potato_id = insert_ingredient("Sweet potato", "Sweet potatoes are root vegetables with a naturally sweet flavor. They are high in fiber, vitamins A and C, and are beneficial for digestion and vision.");
        long tomato_id = insert_ingredient("Tomato", "Tomatoes are juicy fruits that are often used in salads, sauces, and various dishes. They are rich in water and antioxidants, promoting heart health.");
        long turnip_id = insert_ingredient("Turnip", "Turnips are root vegetables with a mild, slightly sweet flavor. They are commonly boiled, roasted, or eaten raw in salads, providing vitamin C and fiber.");
        long watermelon_id = insert_ingredient("Watermelon", "Watermelon is a refreshing fruit with a high water content, making it a popular choice during the summer. It is rich in vitamins A and C and is often eaten fresh.");


        // Bước 3: Chỉ liên kết các nguyên liệu với món "Cơm tấm"
//        if (broken_rice_id != -1) {
//            if (rice_id != -1) {
//                insert_meal_ingredient(broken_rice_id, rice_id);  // Liên kết Cơm với Cơm tấm
//            }
//            if (pork_chop_id != -1) {
//                insert_meal_ingredient(broken_rice_id, pork_chop_id);  // Liên kết Sườn với Cơm tấm
//            }
//            if (pork_pie_id != -1) {
//                insert_meal_ingredient(broken_rice_id, pork_pie_id);  // Liên kết Bỉ chả với Cơm tấm
//            }
//        }
    }


}
