import 'dart:convert';
import 'dart:io';

import 'package:customer_app/utils/.constants.example.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';

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

Future<String> getUserDetail() async {
  String? id = await storage.read(key: "id");
  String? token = await storage.read(key: "accessToken");

  if (id == null || token == null) {
    throw Exception('ID or Token not available');
  }

  String url = '$LOCAL_HOST:$PORT';

  try {
    final response = await http.get(
      Uri.http(url, "/api/v1/user/$id"),
      headers: {'Authorization': 'Bearer $token'},
    );

    if (response.statusCode == 200) {
      print('This is user: ');
      print(response.body);
      return response.body;
    } else {
      throw Exception('Failed to load data');
    }
  } catch (e) {
    throw Exception('Failed to make the request: $e');
  }
}

Future<String> _pickImage() async {
  final picker = ImagePicker();
  XFile? pickedImage = await picker.pickImage(source: ImageSource.gallery);

  if (pickedImage != null) {
    return "";
  }
  throw Exception('Image error!');
}

Future<String> pickImage() async {
  final picker = ImagePicker();
  XFile? pickedImage = await picker.pickImage(source: ImageSource.gallery);

  if (pickedImage != null) {
    File imageFile = File(pickedImage.path);
    return await uploadImage(imageFile);
  }
  return "Error";
}

Future<String> uploadImage(File imageFile) async {
  var url = Uri.parse(
      'http://$LOCAL_HOST:8000/upload-image'); // Replace with your API endpoint
  var request = http.MultipartRequest('POST', url);
  request.headers.addAll({
    'Content-Type': 'multipart/form-data',
  });

  var image = await http.MultipartFile.fromPath('image', imageFile.path);
  request.files.add(image);

  try {
    var response = await request.send();
    // var res = response.body;
    if (response.statusCode == 200) {
      print('Image uploaded successfully');
      var responseData = await response.stream.bytesToString();
      print(responseData);
      return responseData;
    } else {
      print('Image upload failed with status ${response.statusCode}');
      throw Exception('Image error!');
    }
  } catch (e) {
    print('Error uploading image: $e');
    throw Exception('Error, please try again!!');
  }
}

Future<void> pickFile() async {
  FilePickerResult? result = await FilePicker.platform.pickFiles();

  if (result != null) {
    File file = File(result.files.single.path!);
    await uploadImage(file);
  } else {
    throw Exception('File error!');
  }
}
