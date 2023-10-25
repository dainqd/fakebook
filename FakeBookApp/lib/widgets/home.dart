import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:demo_app/widgets/list.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Image.network(
            'https://danangfantasticity.com/wp-content/uploads/2022/02/background-att.jpg',
            fit: BoxFit.cover,
            height: double.infinity,
            width:
                double.infinity,
          ),
          Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text(
                  'Trips Mountain',
                  style: TextStyle(fontSize: 36, color: Colors.white),
                ),
                SizedBox(height: 16),
                Text(
                  'We will build is step by step. We will also build the ui and do api request.',
                  style: TextStyle(fontSize: 18, color: Colors.white),
                  textAlign: TextAlign.left,
                ),
                SizedBox(height: 32),
                ElevatedButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => ListTourArea()),
                    );
                  },
                  child: Text('>>>>>>'),
                ),
              ],
            ),
          ),
          Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: Container(
              color: Colors.grey[300],
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [

                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
