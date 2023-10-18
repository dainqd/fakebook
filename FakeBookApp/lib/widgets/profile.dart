import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:demo_app/widgets/home.dart';
import 'package:flutter/material.dart';
import './search.dart';
import './favorite.dart';

class ProfileScreen extends StatelessWidget {

  Widget buildAvatarAndName() {
    return
      Container(
        color: Colors.purple[50],
        padding: EdgeInsets.all(16),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            CircleAvatar(
              radius: 30,
              backgroundImage: NetworkImage('https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg'),
            ),
            SizedBox(width: 10),
            Text(
              'User',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
          ],
        ),
      );
  }

  Widget buildIconColumn(IconData icon, String text) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Icon(icon, size: 50, color: Colors.blue),
        Text(text, style: TextStyle(fontSize: 16)),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile Screen'),
      ),
      body: Column(
        children: <Widget>[
          buildAvatarAndName(),
          Expanded(
            child: GridView.count(
              shrinkWrap: true,
              crossAxisCount: 3,
              children: [
                buildIconColumn(Icons.book, 'Book a Trip'),
                buildIconColumn(Icons.group, 'My Trips'),
                buildIconColumn(Icons.favorite, 'Love'),
                buildIconColumn(Icons.settings, 'Setting'),
                buildIconColumn(Icons.receipt, 'Bills'),
                buildIconColumn(Icons.share, 'Share'),
              ],
            ),
          ),
          Container(
            color: Colors.grey[300],
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                TextButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => HomeScreen()),
                    );
                  },
                  child: Row(
                    children: [
                      Icon(Icons.home),
                      SizedBox(width: 8),
                      Text(
                        'Home',
                        style: TextStyle(
                          fontSize: 16,
                        ),
                      ),
                    ],
                  ),
                ),
                IconButton(
                  icon: Icon(Icons.search),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => SearchTourArea()),
                    );
                  },
                ),
                IconButton(
                  icon: Icon(Icons.favorite),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => FavoriteScreen()),
                    );
                  },
                ),
                IconButton(
                  icon: Icon(Icons.person),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => ProfileScreen()),
                    );
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

