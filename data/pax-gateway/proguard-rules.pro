# File: data/pax-gateway/proguard-rules.pro
# الحفاظ على واجهات JNI ومكتبات PAX NeptuneLite و GLPage
-keep class com.pax.** { *; }
-keep interface com.pax.** { *; }
-keep class com.ebe.** { *; }
-dontwarn com.pax.**
-dontwarn com.ebe.**

# تجريد أي أوامر طباعة سجلات حساسة في نسخ الإنتاج Release
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
}
