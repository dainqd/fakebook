import 'dart:convert';

import 'package:customer_app/utils/.constants.example.dart';
import 'package:customer_app/utils/utils.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class NewBlogForm extends StatefulWidget {
  @override
  _NewBlogFormState createState() => _NewBlogFormState();
}

class _NewBlogFormState extends State<NewBlogForm> {
  TextEditingController _titleController = TextEditingController();

  Future<String> getUserDetail() async {
    String? id = await storage.read(key: "user_id");
    String? token = await storage.read(key: "accessToken");
    String url = '$LOCAL_HOST:$PORT';
    int userID = int.parse(id!);

    try {
      final response = await http.get(
        Uri.http(url, "/api/v1/user/$userID"),
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

  void _handleSubmit() {
    String title = _titleController.text;
  }

  Widget _buildAvatarAndTextField() {
    return FutureBuilder<String>(
      future: getUserDetail(),
      builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        }

        String userDetail = snapshot.data!;
        var res = json.decode(userDetail);

        return Row(
          children: [
            CircleAvatar(
              radius: 30.0,
              backgroundImage: NetworkImage(res['avt']),
              backgroundColor: Colors.transparent,
            ),
            SizedBox(width: 10.0),
            Expanded(
              child: TextField(
                controller: _titleController,
                maxLines: null,
                decoration: InputDecoration(
                  hintText: 'write something',
                  border: OutlineInputBorder(
                    borderSide: BorderSide(color: Colors.grey),
                  ),
                ),
              ),
            ),
          ],
        );
      },
    );
  }

  Widget _buildSubmitButton() {
    return Row(
      children: [
        ElevatedButton(
          onPressed: _handleSubmit,
          child: Text('Đăng'),
        ),
        SizedBox(width: 20.0),
        IconButton(
          icon: Icon(Icons.image),
          onPressed: () {
            Future<String?> thumbnail = pickImage();
            print('thumbnail: $thumbnail');
          },
          iconSize: 36,
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8.0),
      padding: EdgeInsets.all(8.0),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8.0),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 2,
            blurRadius: 3,
            offset: Offset(0, 2),
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            _buildAvatarAndTextField(),
            SizedBox(height: 20.0),
            _buildSubmitButton(),
          ],
        ),
      ),
    );
  }
}
