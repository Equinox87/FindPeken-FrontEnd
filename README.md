# FindPeken Front Readme

1. Clone project
2. Buat API key di https://console.cloud.google.com/
3. Generate SHA 1 certificate fingerprint di termninal android studio:
   - For Linux or macOS: keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   - For Windows : keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
4. Copy API key ke res ---> values ---> google_maps_api.xml
5. Build


notes: kenapa harus membuat API key baru? karena jika tidak marker tidak akan muncul. hal tersebut disebabkan oleh SHA 1 key yang berbeda.
