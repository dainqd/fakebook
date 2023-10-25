import 'dart:convert';

import 'package:demo_app/widgets/auth/register.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../../utils/utils.dart';

import '../../utils/constants.dart';
import 'package:demo_app/widgets//home.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  TextEditingController usernameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  Future<void> loginUser(String username, String password) async {
    try {
      String url = LOCAL_HOST + ":" + PORT.toString();
      String loginUrl = "http://" + url + "/api/v1/auth/signin";
      final response = await http.post(
        Uri.parse(loginUrl),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, String>{
          'username': username,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        print('Đăng nhập thành công!');
        print('Token: ${json.decode(response.body)['token']}');
        String token = json.decode(response.body)['token'];
        String username = json.decode(response.body)['token'];
        String email = json.decode(response.body)['token'];
        String id = json.decode(response.body)['token'];

        await storage.write(key: 'accessToken', value: token);
        await storage.write(key: 'username', value: username);
        await storage.write(key: 'email', value: email);
        await storage.write(key: 'user_id', value: id);

        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => HomeScreen()),
        );
      } else {
        print('Đăng nhập thất bại. Mã lỗi: ${response.statusCode}');
      }
    } catch (e) {
      print('Đã xảy ra lỗi: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Login'),
      ),
      // backgroundColor: Color(0x80E0FDFD),
      body: Center(
        child: Padding(
          padding: EdgeInsets.all(16.0),
          child: Stack(
            children: <Widget>[
              Positioned(
                top: 0,
                left: 0,
                right: 0,
                child: Container(
                  padding: EdgeInsets.all(16.0),
                  child: Image.network('https://i.ibb.co/Qf7jrWN/logo.png',
                      height: 100, width: 100),
                ),
              ),
              Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    TextField(
                      controller: usernameController,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        hintText: 'Enter your username',
                        prefixIcon: Icon(Icons.person),
                        labelText: 'Username',
                      ),
                    ),
                    SizedBox(height: 16.0),
                    TextField(
                      controller: passwordController,
                      obscureText: true,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        hintText: 'Enter your password',
                        prefixIcon: Icon(Icons.key),
                        labelText: 'Password',
                      ),
                    ),
                    SizedBox(height: 16),
                    ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        padding:
                            EdgeInsets.symmetric(horizontal: 40, vertical: 20),
                        textStyle: TextStyle(fontSize: 20),
                      ),
                      onPressed: () async {
                        String username = usernameController.text;
                        String password = passwordController.text;
                        await loginUser(username, password);
                      },
                      child: Text('Login'),
                    ),
                    GestureDetector(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => RegisterScreen()),
                        );
                      },
                      child: Text(
                        'Register',
                        style: TextStyle(
                          color: Colors.blue, // Màu của liên kết
                          decoration:
                              TextDecoration.underline, // Gạch chân liên kết
                        ),
                      ),
                    )
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
