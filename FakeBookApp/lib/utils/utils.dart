import 'package:flutter_secure_storage/flutter_secure_storage.dart';

final storage = new FlutterSecureStorage();

Future<void> saveData(String key, String value) async {
  await storage.write(key: key, value: value);
}

Future<void> deleteData(String data) async {
  await storage.delete(key: data);
}

Future<String?> getData(String data) async {
  return await storage.read(key: data);
}

Future<bool> checkToken() async {
  final storage = FlutterSecureStorage();
  String? token = await storage.read(key: 'token');
  return token != null;
}
