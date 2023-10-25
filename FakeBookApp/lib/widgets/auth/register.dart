import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../../utils/constants.dart';
import 'package:demo_app/widgets/auth/login.dart';

class RegisterScreen extends StatefulWidget {
  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  TextEditingController usernameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController passwordConfirmController = TextEditingController();

  Future<void> registerUser(String username, String email, String password,
      String passwordConfirm) async {
    try {
      String url = LOCAL_HOST + ":" + PORT.toString();
      String loginUrl = "http://" + url + "/api/v1/auth/signup";
      final response = await http.post(
        Uri.parse(loginUrl),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, String>{
          'username': username,
          'email': email,
          'password': password,
          'passwordConfirm': passwordConfirm,
        }),
      );

      if (response.statusCode == 200) {
        print("Register success");
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => LoginScreen()),
        );
      } else {
        showDialog(
          context: context,
          builder: (BuildContext context) {
            return AlertDialog(
              title: Text('Fail'),
              content: Text('Regsiter fail, Please try again!'),
              actions: <Widget>[
                TextButton(
                  child: Text('Closs'),
                  onPressed: () {
                    Navigator.of(context).pop(); // Đóng cửa sổ cảnh báo
                  },
                ),
              ],
            );
          },
        );
        ;
      }
    } catch (e) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Error, Please try again!'),
            actions: <Widget>[
              TextButton(
                child: Text('Closs'),
                onPressed: () {
                  Navigator.of(context).pop(); // Đóng cửa sổ cảnh báo
                },
              ),
            ],
          );
        },
      );
      ;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Register'),
      ),
      body: Stack(
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
            child: Padding(
              padding: EdgeInsets.all(16.0),
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
                    controller: emailController,
                    decoration: InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: 'Enter your email',
                      prefixIcon: Icon(Icons.mail),
                      labelText: 'Email',
                    ),
                  ),
                  SizedBox(height: 16.0),
                  TextField(
                    controller: passwordController,
                    obscureText: true,
                    decoration: InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: 'Enter your username',
                      prefixIcon: Icon(Icons.key),
                      labelText: 'Password',
                    ),
                  ),
                  SizedBox(height: 24.0),
                  TextField(
                    controller: passwordConfirmController,
                    obscureText: true,
                    decoration: InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: 'Enter your username',
                      prefixIcon: Icon(Icons.password),
                      labelText: 'Password Confirm',
                    ),
                  ),
                  SizedBox(height: 24.0),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      padding:
                          EdgeInsets.symmetric(horizontal: 40, vertical: 20),
                      textStyle: TextStyle(fontSize: 20),
                    ),
                    onPressed: () async {
                      String username = usernameController.text;
                      String email = emailController.text;
                      String password = passwordController.text;
                      String passwordConfirm = passwordConfirmController.text;
                      await registerUser(
                          username, email, password, passwordConfirm);
                    },
                    child: Text('Register'),
                  ),
                  Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        margin: EdgeInsets.only(top: 10.0),
                        child: Text(
                          "Already have an account?",
                          style:
                              TextStyle(fontSize: 18, color: Color(0xF3FFCB21)),
                        ),
                      ),
                      GestureDetector(
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => LoginScreen()),
                          );
                        },
                        child: Text(
                          'Login now',
                          style: TextStyle(
                            color: Colors.blue,
                            fontSize: 18,
                            decoration: TextDecoration.none,
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
